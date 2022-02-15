package NetworkGame;

import Game.Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * NetworkGame.NetworkPC represents method for otherPC, when creating multiplayer network game.
 *
 * @author TimotejBarbus
 */
public class NetworkPC {
    public Socket socket;

    /**
     * Constructor for otherPC.
     *
     * @param IP represents IP address
     * @param port represents port
     */
    public NetworkPC(InetAddress IP, int port) throws Exception {
        this.socket = new Socket(IP, port);
    }

    /**
     * Sends request for otherPC.
     *
     * @param coordinates represents sent coordinates
     */
    public void send(String coordinates) {
        PrintWriter printwriter;
        try {
            printwriter = new PrintWriter(this.socket.getOutputStream(), true);
            printwriter.println(coordinates);
            printwriter.flush();
        } catch (IOException error) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, error);
        }
    }

}