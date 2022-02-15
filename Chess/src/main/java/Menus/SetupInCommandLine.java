package Menus;

import Enums.EnumFiguresColor;
import Game.Clock;
import NetworkGame.HomePC;
import NetworkGame.NetworkPC;
import Game.Main;

import java.net.InetAddress;
import java.util.Random;
import java.util.Scanner;

/**
 * Menus.SetupInCommandLine represents method for setting up the game requirements before the game starts.
 *
 * @author TimotejBarbus
 */
public class SetupInCommandLine {
    public static EnumFiguresColor player1;
    public static EnumFiguresColor player2;
    public static int intGameMode = 0;
    public static int test;
    public static boolean passed = false;
    public static String gameMode;
    public static String playerColor;
    public static int intPlayerColor = 0;
    public static String time;
    public static int intTime = 0;
    public static String homePC;
    public static String networkPC;
    public static String networkPort;

    /**
     * Checks if input is a number.
     *
     * @param input represents controlled input
     * @return returns true if it is a number, false if it is not
     */
    public static boolean checkIfNumber(String input) {
        try {
            test = Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e){
            System.out.println(test + " is not a number, only numbers are allowed as an input!");
            return false;
        }
    }

    /**
     * Moves.Check if number is between 1 and 3.
     *
     * @param number represents controlled number
     * @return returns true if it is between 1 and 3, false if it is not
     */
    public static boolean checkAllowedNumber3(int number) {
        if(number == 1 || number == 2 || number == 3) {
            return true;
        }
        System.out.println("Only numbers between 1 and 3 are allowed as an input!");
        return false;
    }

    /**
     * Moves.Check if number is between 1 and 4.
     *
     * @param number represents controlled number
     * @return returns true if it is between 1 and 4, false if it is not
     */
    public static boolean checkAllowedNumber4(int number) {
        if(number == 1 || number == 2 || number == 3 || number == 4) {
            return true;
        }
        System.out.println("Only numbers between 1 and 4 are allowed as an input!");
        return false;
    }

    /**
     * Let player choose game mode.
     */
    public static void chooseGamemode() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose game mode:");
        System.out.println("1: Singleplayer");
        System.out.println("2: Multiplayer local");
        System.out.println("3: Multiplayer network\n");

        while(!passed) {
            gameMode = sc.nextLine();
            if(checkIfNumber(gameMode)) {
                intGameMode = Integer.parseInt(gameMode);
                if(checkAllowedNumber3(intGameMode)) {
                    passed = true;
                }
            }
        }

        passed = false;

        if(intGameMode == 3) {
            for(int i = 0; i < 5; i++) {
                try {
                    System.out.println("Type IP address of your PC: ");
                    homePC = sc.nextLine();

                    Main.server = new HomePC(homePC);
                    Main.server.start();
                    Main.serverCreated = true;
                    i = 5;
                } catch (Exception ex) {
                    System.out.println("IP address could not be connected!");
                }
            }

            System.out.println("Your IP address: " + Main.server.getHomePCAddress().getHostAddress());
            System.out.println("Your Port: " + Main.server.getHomePCPort());

            for(int i = 0; i < 999; i++) {
                try {
                    System.out.println("Type IP address of other PC: ");
                    networkPC = sc.nextLine();

                    System.out.println("Type PORT of other PC");
                    networkPort = sc.nextLine();

                    Main.client = new NetworkPC(InetAddress.getByName(networkPC), Integer.parseInt(networkPort));

                    Main.clientCreated = true;
                    i = 1000;
                } catch (Exception ex) {
                    System.out.println("IP address could not be connected!");
                }
            }
            System.out.println("Connected.");
        }
    }

    /**
     * Let player choose color of figures.
     */
    public static void chooseFigures() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Player 1, do you want to play with white or black figures?");
        System.out.println("1: White figures");
        System.out.println("2: Black figures");
        System.out.println("3: Random\n");

        while(!passed) {
            playerColor = sc.nextLine();
            if(checkIfNumber(playerColor)) {
                intPlayerColor = Integer.parseInt(playerColor);
                if(checkAllowedNumber3(intPlayerColor)) {
                    passed = true;
                }
            }
        }

        passed = false;

        if(intPlayerColor == 3) {
            Random random = new Random();
            intPlayerColor = random.nextInt(2);
            intPlayerColor++;
        }

        if(intPlayerColor == 1) {
            player1 = EnumFiguresColor.WHITE;
            player2 = EnumFiguresColor.BLACK;
        }

        if(intPlayerColor == 2) {
            player1 = EnumFiguresColor.BLACK;
            player2 = EnumFiguresColor.WHITE;
        }
    }

    /**
     * Let player choose how much time he wants to play the game.
     */
    public static void chooseClock() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose how much time you want to play the game (in seconds):\n");

        while(!passed) {
            time = sc.nextLine();
            if(checkIfNumber(time)) {
                intTime = Integer.parseInt(time);
                if(intTime > 0) {
                    passed = true;
                }
                else {
                    System.out.println("Only number higher than 0 are allowed as an input!");
                }
            }
        }

        passed = false;
        Clock.whiteTime = intTime;
        Clock.blackTime = Clock.whiteTime;
    }
}
