package Moves;

import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Figures.AbstractFigures;
import View.GUI;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.List;

/**
 * Moves.Check represents method for controlling some game acts.
 *
 * @author TimotejBarbus
 */
public class Check extends Board {

    /**
     * Figures.King is in checkmate, game ends, winner is announced.
     *
     * @param winner represents color of figures who won by Enums.EnumFiguresColor
     */
    public static void checkMate(EnumFiguresColor winner) {
        Text winnerText = new Text();

        if(winner == EnumFiguresColor.BLACK) {
            winnerText.setText("Black player won!");
        }
        else {
            winnerText.setText("White player won!");
        }
        Move.inGame = false;

        winnerText.setFont(Font.font("Dialog",50));
        winnerText.setFill(Color.RED);
        winnerText.relocate(GUI.TILESIZE * 0.8, GUI.TILESIZE * 3.85);
        GUI.checkMateGroup.getChildren().add(winnerText);
    }

    /**
     * Checks if king is in checkmate.
     *
     * @param allyColor represents color of figure who is giving possible checkmate by Enums.EnumFiguresColor
     * @param board represents board of figures
     * @param kingCoordinates represents coordinates of king who may be in checkmate
     * @return returns true if king is in checkmate, false if it is not
     */
    public static boolean checkIfCheckMate(EnumFiguresColor allyColor, AbstractFigures[][] board, Coordinates kingCoordinates) {
        EnumFiguresColor enemyColor;
        EnumFiguresType enemyType;
        EnumFiguresColor accessColor;
        EnumFiguresType accessType;
        List<Coordinates> accessibleMoves;
        boolean check;

        if(allyColor == EnumFiguresColor.WHITE) {
            enemyColor = EnumFiguresColor.BLACK;
        }
        else {
            enemyColor = EnumFiguresColor.WHITE;
        }

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j].color == enemyColor) {
                    accessibleMoves = board[i][j].getAccessibleMoves(new Coordinates(i, j), board, enemyColor);

                    for(Coordinates accessibleMove : accessibleMoves) {
                        enemyType = board[i][j].type;
                        accessType = board[accessibleMove.a][accessibleMove.b].type;
                        accessColor = board[accessibleMove.a][accessibleMove.b].color;

                        board[i][j].type = EnumFiguresType.EMPTY;
                        board[i][j].color = EnumFiguresColor.NONE;
                        board[accessibleMove.a][accessibleMove.b].type = enemyType;
                        board[accessibleMove.a][accessibleMove.b].color = enemyColor;

                        if(enemyType == EnumFiguresType.KING) {
                            kingCoordinates = new Coordinates(accessibleMove.a, accessibleMove.b);
                        }

                        check = checkIfCheck(allyColor, board, kingCoordinates);

                        if(enemyType == EnumFiguresType.KING) {
                            kingCoordinates = new Coordinates(i, j);
                        }

                        board[i][j].type = enemyType;
                        board[i][j].color = enemyColor;
                        board[accessibleMove.a][accessibleMove.b].type = accessType;
                        board[accessibleMove.a][accessibleMove.b].color = accessColor;

                        if(!check) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if king is in check.
     *
     * @param allyColor represents color of figure who is giving possible check by Enums.EnumFiguresColor
     * @param board represents board of figures
     * @param kingCoordinates represents coordinates of king who may be in check
     * @return returns true if king is in check, false if it is not
     */
    public static boolean checkIfCheck(EnumFiguresColor allyColor, AbstractFigures[][] board, Coordinates kingCoordinates) {
        List<Coordinates> accessibleMoves;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j].color == allyColor) {
                    accessibleMoves = board[i][j].getAccessibleMoves(new Coordinates(i, j), board, allyColor);

                    for(Coordinates accessibleMove : accessibleMoves) {
                        if(accessibleMove.a == kingCoordinates.a && accessibleMove.b == kingCoordinates.b) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Calls accessible moves by current figure on coordinates.
     * Removes some accessible moves in case the move is illegal in meaning that king will be in check after that.
     *
     * @param coordinates represents coordinates of figure asked for available moves
     * @param board represents board of figures
     * @param allyColor represents color of figure who is giving possible check by Enums.EnumFiguresColor
     * @param kingCoordinates represents coordinates of king who may be in check
     * @return returns list of available moves by current figure
     */
    public static List<Coordinates> checkAccessibleMoves(Coordinates coordinates, AbstractFigures[][] board, EnumFiguresColor allyColor, Coordinates kingCoordinates) {
        EnumFiguresColor enemyColor;
        EnumFiguresType allyType;
        EnumFiguresColor accessColor;
        EnumFiguresType accessType;
        List<Coordinates> accessibleMoves;
        Coordinates oldKingCoordinates;
        boolean check;

        if(allyColor == EnumFiguresColor.WHITE) {
            enemyColor = EnumFiguresColor.BLACK;
        }
        else {
            enemyColor = EnumFiguresColor.WHITE;
        }

        accessibleMoves = board[coordinates.a][coordinates.b].getAccessibleMoves(new Coordinates(coordinates.a, coordinates.b), board, allyColor);
        for(int i = 0; i < accessibleMoves.size(); i++) {
            if(accessibleMoves.get(i).a != coordinates.a || accessibleMoves.get(i).b != coordinates.b) {
                allyType = board[coordinates.a][coordinates.b].type;
                accessType = board[accessibleMoves.get(i).a][accessibleMoves.get(i).b].type;
                accessColor = board[accessibleMoves.get(i).a][accessibleMoves.get(i).b].color;

                board[coordinates.a][coordinates.b].type = EnumFiguresType.EMPTY;
                board[coordinates.a][coordinates.b].color = EnumFiguresColor.NONE;
                board[accessibleMoves.get(i).a][accessibleMoves.get(i).b].type = allyType;
                board[accessibleMoves.get(i).a][accessibleMoves.get(i).b].color = allyColor;

                if(allyType == EnumFiguresType.KING) {
                    oldKingCoordinates = kingCoordinates;
                    kingCoordinates = accessibleMoves.get(i);
                    check = checkIfCheck(enemyColor, board, kingCoordinates);
                    kingCoordinates = oldKingCoordinates;
                }
                else {
                    check = checkIfCheck(enemyColor, board, kingCoordinates);
                }

                board[coordinates.a][coordinates.b].type = allyType;
                board[coordinates.a][coordinates.b].color = allyColor;
                board[accessibleMoves.get(i).a][accessibleMoves.get(i).b].type = accessType;
                board[accessibleMoves.get(i).a][accessibleMoves.get(i).b].color = accessColor;

                if(check) {
                    accessibleMoves.remove(i);
                    i--;
                }
            }
        }
        return accessibleMoves;
    }
}
