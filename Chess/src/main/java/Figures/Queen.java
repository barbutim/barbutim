package Figures;

import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Moves.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Figures.Queen represents method for figure of queen and his available moves.
 *
 * @author TimotejBarbus
 */
public class Queen extends AbstractFigures {

    /**
     * Constructor for Figures.Queen.
     *
     * @param type represents type of figure by Enums.EnumFiguresType
     * @param color represents color of figure by Enums.EnumFiguresColor
     */
    public Queen(EnumFiguresType type, EnumFiguresColor color) {
        super(type, color);
    }

    /**
     * Gets all available moves by current figure.
     *
     * @param coordinates represents coordinates of current figure asked for accessible moves
     * @param board represents all figures on board
     * @param color represents color of current figure asked for accessible moves
     * @return returns list of coordinates of current figure asked for accessible moves
     */
    public List<Coordinates> getAccessibleMoves(Coordinates coordinates, AbstractFigures[][] board, EnumFiguresColor color) {
        List<Coordinates> move = new ArrayList<>();

        int a = coordinates.a;
        int b = coordinates.b;

        move.add(new Coordinates(a, b));

        for(int i = 0; i < 8; i++) {
            if(a + 1 + i < 8) {
                if(board[a + 1 + i][b].type == EnumFiguresType.EMPTY) {
                    move.add(new Coordinates(a + 1 + i, b));
                }
                else {
                    if(board[a + 1 + i][b].color != color) {
                        move.add(new Coordinates(a + 1 + i, b));
                    }
                    i = 8;
                }
            }
        }

        for(int i = 0; i < 8; i++) {
            if(a - 1 - i >= 0) {
                if(board[a - 1 - i][b].type == EnumFiguresType.EMPTY) {
                    move.add(new Coordinates(a - 1 - i, b));
                }
                else {
                    if(board[a - 1 - i][b].color != color) {
                        move.add(new Coordinates(a - 1 - i, b));
                    }
                    i = 8;
                }
            }
        }

        for(int i = 0; i < 8; i++) {
            if(b + 1 + i < 8) {
                if(board[a][b + 1 + i].type == EnumFiguresType.EMPTY) {
                    move.add(new Coordinates(a, b + 1 + i));
                }
                else {
                    if(board[a][b + 1 + i].color != color) {
                        move.add(new Coordinates(a, b + 1 + i));
                    }
                    i = 8;
                }
            }
        }

        for(int i = 0; i < 8; i++) {
            if(b - 1 - i >= 0) {
                if(board[a][b - 1 - i].type == EnumFiguresType.EMPTY) {
                    move.add(new Coordinates(a, b - 1 - i));
                }
                else {
                    if(board[a][b - 1 - i].color != color) {
                        move.add(new Coordinates(a, b - 1 - i));
                    }
                    i = 8;
                }
            }
        }

        for(int i = 0; i < 8; i++) {
            if(a + 1 + i < 8 && b - 1 - i >= 0) {
                if(board[a + 1 + i][b - 1 - i].type == EnumFiguresType.EMPTY) {
                    move.add(new Coordinates(a + 1 + i, b - 1 - i));
                }
                else {
                    if(board[a + 1 + i][b - 1 - i].color != color) {
                        move.add(new Coordinates(a + 1 + i, b - 1 - i));
                    }
                    i = 8;
                }
            }
        }

        for(int i = 0; i < 8; i++) {
            if(a + 1 + i < 8 && b + 1 + i < 8) {
                if(board[a + 1 + i][b + 1 + i].type == EnumFiguresType.EMPTY) {
                    move.add(new Coordinates(a + 1 + i, b + 1 + i));
                }
                else {
                    if(board[a + 1 + i][b + 1 + i].color != color) {
                        move.add(new Coordinates(a + 1 + i, b + 1 + i));
                    }
                    i = 8;
                }
            }
        }

        for(int i = 0; i < 8; i++) {
            if(a - 1 - i >= 0 && b - 1 - i >= 0) {
                if(board[a - 1 - i][b - 1 - i].type == EnumFiguresType.EMPTY) {
                    move.add(new Coordinates(a - 1 - i, b - 1 - i));
                }
                else {
                    if(board[a - 1 - i][b - 1 - i].color != color) {
                        move.add(new Coordinates(a - 1 - i, b - 1 - i));
                    }
                    i = 8;
                }
            }
        }

        for(int i = 0; i < 8; i++) {
            if(a - 1 - i >= 0 && b + 1 + i < 8) {
                if(board[a - 1 - i][b + 1 + i].type == EnumFiguresType.EMPTY) {
                    move.add(new Coordinates(a - 1 - i, b + 1 + i));
                }
                else {
                    if(board[a - 1 - i][b + 1 + i].color != color) {
                        move.add(new Coordinates(a - 1 - i, b + 1 + i));
                    }
                    i = 8;
                }
            }
        }

        return move;
    }
}