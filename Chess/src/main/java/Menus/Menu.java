package Menus;

import Enums.EnumFiguresColor;
import Moves.Board;
import Game.Clock;
import Moves.Coordinates;
import Moves.AI;
import Moves.Check;
import View.GUI;
import Moves.Move;
import Moves.MoveDefine;
import SaveLoad.SaveLoadGame;
import View.Tile;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

/**
 * Menus.Menu represents method for in-game menu.
 *
 * @author TimotejBarbus
 */
public class Menu extends Text {
    public static Clock clock = new Clock(0,0, EnumFiguresColor.BLACK);
    public static EnumFiguresColor playerColorSave = EnumFiguresColor.WHITE;

    /**
     * Constructor for Menus.Menu.
     * Mouse event, when clicked starts action.
     *
     * @param number represents clicked button
     */
    public Menu(int number) {
        setOnMousePressed((MouseEvent click) -> Menu.menuFunctions(number));
    }

    /**
     * Generates menu, GUI.GUI.
     */
    public static void generateMenu() {
        Color color = Color.rgb(175, 198, 220);
        for(int i = 0; i < 4; i++) {
            Menu menu = new Menu(i);
            menu.setScaleX(2);
            menu.setScaleY(2);
            menu.setStroke(color);
            menu.setFont(Font.font("Dialog",10));
            menu.setFill(Color.BLACK);

            if(i == 0) {
                menu.setText("Play");
            }
            if(i == 1) {
                menu.setText("Move");
            }
            if(i == 2) {
                menu.setText("Load");
            }
            if(i == 3) {
                menu.setText("Save");
            }

            menu.relocate(GUI.TILESIZE * 2 * i + GUI.MENU - 2, GUI.MENU / 2.5);
            GUI.menuGroup.getChildren().add(menu);
        }
    }

    /**
     * Actions of buttons in menu.
     *
     * @param number represents clicked button
     */
    public static void menuFunctions(int number) {
        if(!Check.checkIfCheck(EnumFiguresColor.BLACK, Board.board, Move.whiteKingCoordinates) && !Check.checkIfCheck(EnumFiguresColor.WHITE, Board.board, Move.blackKingCoordinates)) {
            if(number == 0) {
                if(!Move.inGame) {
                    Move.clickedOnFigure = false;
                    Move.freeMove = false;
                    Move.playerColor = playerColorSave;
                    Tile.generateBoard();

                    clock.start();
                    Move.inGame = true;
                    if(SetupInCommandLine.intGameMode == 1 && Move.playerColor == SetupInCommandLine.player2) {
                        AI.move();
                    }

                    if(SetupInCommandLine.intGameMode == 3) {
                        if(SetupInCommandLine.player1 == EnumFiguresColor.BLACK) {
                            MoveDefine.waiting = true;
                        }
                        MoveDefine.moveRequest(new Coordinates(-1, -1));
                    }
                }
            }

            if(number == 1) {
                if(SetupInCommandLine.intGameMode != 3) {
                    if (!Move.freeMove) {
                        playerColorSave = Move.playerColor;
                        Move.clickedOnFigure = false;
                        Move.freeMove = true;
                        Move.inGame = false;
                        Tile.generateBoard();
                    }
                }
                else {
                    System.out.println("Move function is not available in multiplayer network game.");
                }
            }

            if(number == 3) {
                if(SetupInCommandLine.intGameMode != 3) {
                    Move.inGame = false;
                    Move.freeMove = false;
                    SaveLoadGame.saveGame();
                }
                else {
                    System.out.println("Save game is not available in multiplayer network game.");
                }
            }
        }
        else {
            if(number == 0) {
                System.out.println("You cannot play the game while any of kings is in check!");
            }
            if(number == 1) {
                System.out.println("You cannot move any of the figures while any of kings is in check!");
            }
            if(number == 3) {
                System.out.println("You cannot save the game while any of kings is in check!");
            }
        }
        if(number == 2) {
            if(SetupInCommandLine.intGameMode != 3) {
                Move.inGame = false;
                Move.freeMove = false;
                SaveLoadGame.loadGame();
            }
            else {
                System.out.println("Load game is not available in multiplayer network game.");
            }
        }
    }
}
