package Game;

import Enums.EnumFiguresColor;
import Moves.Check;
import View.GUI;
import Moves.Move;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Game.Clock represents method for chess clock, made with Thread.
 *
 * @author TimotejBarbus
 */
public class Clock extends Text implements Runnable {
    public Thread clock;
    public EnumFiguresColor color;
    public int a;
    public int b;
    public static Clock[] listOfClock = new Clock[2];
    public static int whiteTime = 0;
    public static int blackTime = 0;
    public static int split;
    public static int hours;
    public static int minutes;
    public static int seconds;
    public static String output;
    public static boolean stopThread;

    /**
     * Constructor for Game.Clock.
     *
     * @param a represents 'coordinates a' where clock is located in GUI.GUI
     * @param b represents 'coordinates b' where clock is located in GUI.GUI
     * @param color represents player color, whose time it is by Enums.EnumFiguresColor
     */
    public Clock(int a, int b, EnumFiguresColor color) {
        this.a = a;
        this.b = b;
        this.color = color;
    }

    /**
     * Thread, which runs with sleep each second, then updates clock.
     */
    @Override
    public void run() {
        try {
            while (Move.inGame && !stopThread) {
                Platform.runLater(() -> {
                    if (Move.playerColor == EnumFiguresColor.WHITE) {
                        listOfClock[0].update();
                    }
                    if (Move.playerColor == EnumFiguresColor.BLACK) {
                        listOfClock[1].update();
                    }
                });
                Thread.sleep(1000);
            }
        }
        catch(InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Starts Thread.
     */
    public void start() {
        clock = new Thread(this);
        clock.start();
    }

    /**
     * Stops Thread.
     */
    public void stop() {
        stopThread = true;
    }

    /**
     * Updates clock by -1 sec.
     */
    public void update() {
        if (Move.playerColor == EnumFiguresColor.WHITE) {
            whiteTime--;
            setText(toString(whiteTime));
        }
        if (Move.playerColor == EnumFiguresColor.BLACK) {
            blackTime--;
            setText(toString(blackTime));
        }

        if (whiteTime == 0) {
            Check.checkMate(EnumFiguresColor.BLACK);
            Move.inGame = false;
        }
        if (blackTime == 0) {
            Check.checkMate(EnumFiguresColor.WHITE);
            Move.inGame = false;
        }
    }

    /**
     * Generates clock in GUI.GUI, by 'coordinates' of constructor.
     */
    public static void generateClock() {
        Color color = Color.rgb(175, 198, 220);
        for (int i = 0; i < 2; i++) {
            GUI.clockGroup.getChildren().remove(listOfClock[i]);
            Clock clock = new Clock(100, 8 * GUI.TILESIZE + 20, EnumFiguresColor.WHITE);
            clock.setScaleX(2);
            clock.setScaleY(2);
            clock.setStroke(color);
            clock.setFont(Font.font("Dialog", 10));
            clock.setFill(Color.BLACK);

            if (i == 0) {
                clock.setText(toString(whiteTime));
                clock.relocate(GUI.TILESIZE * 2 - 30, 8 * GUI.TILESIZE + GUI.MENU + GUI.CLOCK / 2.5);
            }
            if (i == 1) {
                clock.setText(toString(blackTime));
                clock.relocate(GUI.TILESIZE * 6, 8 * GUI.TILESIZE + GUI.MENU + GUI.CLOCK / 2.5);
            }

            listOfClock[i] = clock;
            GUI.clockGroup.getChildren().add(clock);
        }
    }

    /**
     * Converts time
     *
     * @param time represents current time of player
     * @return returns hours:minutes:seconds
     */
    public static String toString(int time) {
        hours = time / 3600;
        split = time - hours * 3600;
        minutes = split / 60;
        split = split - minutes * 60;
        seconds = split;

        output = hours + ":" + minutes + ":" + seconds;
        return output;
    }
}