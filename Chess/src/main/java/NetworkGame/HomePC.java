package NetworkGame;

import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Figures.Bishop;
import Figures.Knight;
import Figures.Queen;
import Figures.Rook;
import Moves.Board;
import Moves.Coordinates;
import Menus.Menu;
import Menus.SetupInCommandLine;
import Moves.Move;
import Moves.MoveDefine;
import View.Figure;
import View.Tile;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import com.fasterxml.jackson.module.paranamer.ParanamerModule;

/**
 * NetworkGame.HomePC represents method for firstPC, when creating multiplayer network game.
 *
 * @author TimotejBarbus
 */
public class HomePC implements Runnable{
    public ServerSocket serverSocket;
    public Thread server;
    public BufferedReader bufferedreader;
    public String message = null;
    public static boolean messageReceived = false;
    public static Coordinates previousCoordinates;
    public static EnumFiguresColor otherPlayer;

    /**
     * Constructor for NetworkGame.HomePC.
     *
     * @param IP represents IP address
     */
    public HomePC(String IP) throws Exception {
        if (IP != null && !IP.isEmpty())
            this.serverSocket = new ServerSocket(0, 1, InetAddress.getByName(IP));
        else
            this.serverSocket = new ServerSocket(0, 1, InetAddress.getLocalHost());
    }

    /**
     * Runs Thread, which is receiving messages from the other PC.
     */
    @Override
    public void run(){
        try {
            Socket socket = this.serverSocket.accept();
            System.out.println("Connection from IP address: " + socket.getInetAddress().getHostAddress());

            bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while((message = bufferedreader.readLine()) != null ) {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        networkPCMove(message);
                    }
                });
            }
        }
        catch (IOException ignored) {
            System.out.println("Disconnected.");
        }

    }

    /**
     * Starts Thread.
     */
    public void start() {
        server = new Thread(this);
        server.start();
    }

    /**
     * Stops Thread.
     */
    public void stop() {
        try {
            serverSocket.close();
        }
        catch (IOException error) {
            Logger.getLogger(HomePC.class.getName()).log(Level.SEVERE, "Error with server socket", error);
        }
        server = null;
    }

    /**
     * Gets NetworkGame.HomePC IP address.
     *
     * @return represents IP address of NetworkGame.HomePC
     */
    public InetAddress getHomePCAddress() {
        return this.serverSocket.getInetAddress();
    }

    /**
     * Gets NetworkGame.HomePC port.
     *
     * @return represents port of NetworkGame.HomePC
     */
    public int getHomePCPort() {
        return this.serverSocket.getLocalPort();
    }

    /**
     * Decided what to do with received message/coordinates.
     * Either starts the game if coordinates are 'wrong' or moves figure as usual.
     *
     * @param stringCoordinates represents message received from the other PC
     */
    public void networkPCMove(String stringCoordinates) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.registerModule(new ParanamerModule());
            Coordinates coordinates = objectMapper.readValue(stringCoordinates, Coordinates.class);
            if(coordinates.a == -1 && coordinates.b == -1) {
                if(!Move.inGame) {
                    Move.clickedOnFigure = false;
                    Move.freeMove = false;
                    Tile.generateBoard();

                    Menu.clock.start();
                    Move.inGame = true;

                    if(SetupInCommandLine.intGameMode == 3) {
                        if(SetupInCommandLine.player1 == EnumFiguresColor.BLACK) {
                            MoveDefine.waiting = true;
                        }
                    }
                }
            }
            else if(coordinates.a == -2) {
                if(Move.playerColor == EnumFiguresColor.WHITE) {
                    otherPlayer = EnumFiguresColor.BLACK;
                }
                else{
                    otherPlayer = EnumFiguresColor.WHITE;
                }

                Board.removeFigure(previousCoordinates);

                if(coordinates.b == 1) {
                    Board.board[previousCoordinates.a][previousCoordinates.b] = new Queen(EnumFiguresType.QUEEN, otherPlayer);
                }
                else if(coordinates.b == 2) {
                    Board.board[previousCoordinates.a][previousCoordinates.b] = new Rook(EnumFiguresType.ROOK, otherPlayer);
                }
                else if(coordinates.b == 3) {
                    Board.board[previousCoordinates.a][previousCoordinates.b] = new Knight(EnumFiguresType.KNIGHT, otherPlayer);
                }
                else {
                    Board.board[previousCoordinates.a][previousCoordinates.b] = new Bishop(EnumFiguresType.BISHOP, otherPlayer);
                }

                Figure.upgradeFigureMultiplayerGUI(previousCoordinates, coordinates.b);
                Move.upgrade = false;
            }
            else {
                previousCoordinates = coordinates;
                messageReceived = true;
                Move.move(coordinates);
                Tile.generateBoard();
                if(Move.change) {
                    MoveDefine.waiting = false;
                }
            }
        }
        catch (IOException error) {
            Logger.getLogger(HomePC.class.getName()).log(Level.SEVERE, null, error);
        }
    }
}