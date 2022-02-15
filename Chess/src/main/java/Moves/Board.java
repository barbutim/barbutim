package Moves;

import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Figures.*;
import Menus.SetupInCommandLine;
import View.Figure;
import View.GUI;

import java.util.Random;
import java.util.Scanner;

/**
 * Moves.Board represents method for figures on board.
 *
 * @author TimotejBarbus
 */
public class Board {
    public static AbstractFigures[][] board = new AbstractFigures[8][8];
    public static boolean passed = false;
    public static String figureType;
    public static int intFigureType = 0;

    /**
     * Upgrades pawn, when he gets onto the last tile on board.
     * By Moves.AI means that the replacement figure of pawn is chosen randomly.
     *
     * @param coordinates represents coordinates of pawn
     */
    public static void upgradeFigureByAI(Coordinates coordinates) {
        Random random = new Random();

        intFigureType = random.nextInt(4);
        intFigureType++;

        removeFigure(coordinates);

        if(intFigureType == 1) {
            board[coordinates.a][coordinates.b] = new Queen(EnumFiguresType.QUEEN, Move.playerColor);
        }
        else if(intFigureType == 2) {
            board[coordinates.a][coordinates.b] = new Rook(EnumFiguresType.ROOK, Move.playerColor);
        }
        else if(intFigureType == 3) {
            board[coordinates.a][coordinates.b] = new Knight(EnumFiguresType.KNIGHT, Move.playerColor);
        }
        else {
            board[coordinates.a][coordinates.b] = new Bishop(EnumFiguresType.BISHOP, Move.playerColor);
        }

        Figure.upgradeFigureGUI(coordinates, intFigureType);
    }

    /**
     * Upgrades pawn, when he gets onto the last tile on board.
     * By standard player means that the replacement figure of pawn is chosen by player.
     *
     * @param coordinates represents coordinates of pawn
     */
    public static void upgradeFigure(Coordinates coordinates) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose type of figure you want to add onto the board:");
        System.out.println("1: Figures.Queen");
        System.out.println("2: Figures.Rook");
        System.out.println("3: Figures.Knight");
        System.out.println("4: Figures.Bishop\n");

        passed = false;

        while(!passed) {
            figureType = sc.nextLine();
            if(SetupInCommandLine.checkIfNumber(figureType)) {
                intFigureType = Integer.parseInt(figureType);
                if(SetupInCommandLine.checkAllowedNumber4(intFigureType)) {
                    passed = true;
                }
            }
        }

        removeFigure(coordinates);

        if(intFigureType == 1) {
            board[coordinates.a][coordinates.b] = new Queen(EnumFiguresType.QUEEN, Move.playerColor);
        }
        else if(intFigureType == 2) {
            board[coordinates.a][coordinates.b] = new Rook(EnumFiguresType.ROOK, Move.playerColor);
        }
        else if(intFigureType == 3) {
            board[coordinates.a][coordinates.b] = new Knight(EnumFiguresType.KNIGHT, Move.playerColor);
        }
        else {
            board[coordinates.a][coordinates.b] = new Bishop(EnumFiguresType.BISHOP, Move.playerColor);
        }

        Figure.upgradeFigureGUI(coordinates, intFigureType);
    }

    /**
     * Removes figure from GUI.GUI list of figures.
     *
     * @param coordinates represents coordinates of removed figure
     */
    public static void removeFigure(Coordinates coordinates) {
        GUI.figuresGroup.getChildren().remove(Figure.listOfFigures[coordinates.a][coordinates.b]);
    }

    /**
     * Removes figure from board.
     *
     * @param coordinates represents coordinates of removed figure
     */
    public static void removeFigureOnCoordinate(Coordinates coordinates) {
        board[coordinates.a][coordinates.b].type = EnumFiguresType.EMPTY;
        board[coordinates.a][coordinates.b].color = EnumFiguresColor.NONE;
    }

    /**
     * Adds figure on board.
     *
     * @param coordinates represents coordinates of added figure
     * @param color represents color of added figure by Enums.EnumFiguresColor
     * @param type represents type of added figure by Enums.EnumFiguresType
     */
    public static void addFigureOnCoordinate(Coordinates coordinates, EnumFiguresColor color, EnumFiguresType type) {
        board[coordinates.a][coordinates.b].type = type;
        board[coordinates.a][coordinates.b].color = color;
    }

    /**
     * Generates default board layout.
     */
    public static void generateBoard() {
        board[0][0] = new Rook(EnumFiguresType.ROOK, EnumFiguresColor.BLACK);
        board[1][0] = new Knight(EnumFiguresType.KNIGHT, EnumFiguresColor.BLACK);
        board[2][0] = new Bishop(EnumFiguresType.BISHOP, EnumFiguresColor.BLACK);
        board[3][0] = new Queen(EnumFiguresType.QUEEN, EnumFiguresColor.BLACK);
        board[4][0] = new King(EnumFiguresType.KING, EnumFiguresColor.BLACK);
        board[5][0] = new Bishop(EnumFiguresType.BISHOP, EnumFiguresColor.BLACK);
        board[6][0] = new Knight(EnumFiguresType.KNIGHT, EnumFiguresColor.BLACK);
        board[7][0] = new Rook(EnumFiguresType.ROOK, EnumFiguresColor.BLACK);

        for(int i = 0; i < 8; i++) {
            board[i][1] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.BLACK);
        }

        for(int i = 0; i < 8; i++) {
            for(int j = 2; j < 6; j++) {
                board[i][j] = new Empty(EnumFiguresType.EMPTY, EnumFiguresColor.NONE);
            }
        }

        for(int i = 0; i < 8; i++) {
            board[i][6] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.WHITE);
        }

        board[0][7] = new Rook(EnumFiguresType.ROOK, EnumFiguresColor.WHITE);
        board[1][7] = new Knight(EnumFiguresType.KNIGHT, EnumFiguresColor.WHITE);
        board[2][7] = new Bishop(EnumFiguresType.BISHOP, EnumFiguresColor.WHITE);
        board[3][7] = new Queen(EnumFiguresType.QUEEN, EnumFiguresColor.WHITE);
        board[4][7] = new King(EnumFiguresType.KING, EnumFiguresColor.WHITE);
        board[5][7] = new Bishop(EnumFiguresType.BISHOP, EnumFiguresColor.WHITE);
        board[6][7] = new Knight(EnumFiguresType.KNIGHT, EnumFiguresColor.WHITE);
        board[7][7] = new Rook(EnumFiguresType.ROOK, EnumFiguresColor.WHITE);
    }
}
