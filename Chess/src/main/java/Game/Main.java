package Game;

import Menus.SetupInCommandLine;
import Moves.Board;
import View.GUI;
import NetworkGame.HomePC;
import NetworkGame.NetworkPC;

/**
 * Game.Main represents main method for the whole chess game.
 *
 * @author TimotejBarbus
 */
public class Main {
    public static Boolean serverCreated = false;
    public static Boolean clientCreated = false;
    public static HomePC server;
    public static NetworkPC client;

    public static void main(String[] args) {
        String[] smthng = {"args", "more"};
        SetupInCommandLine.chooseGamemode();
        SetupInCommandLine.chooseFigures();
        SetupInCommandLine.chooseClock();
        Board.generateBoard();
        GUI.main(smthng);
    }
}
