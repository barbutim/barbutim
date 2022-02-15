package Figures;

import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Moves.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Figures.Pawn represents method for figure of pawn and his available moves.
 *
 * @author TimotejBarbus
 */
public class Pawn extends AbstractFigures {

    /**
     * Constructor for Figures.Pawn.
     *
     * @param type represents type of figure by Enums.EnumFiguresType
     * @param color represents color of figure by Enums.EnumFiguresColor
     */
    public Pawn(EnumFiguresType type, EnumFiguresColor color) {
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

        if(color == EnumFiguresColor.BLACK) {
            if(b + 1 < 8) {
                if(board[a][b + 1].type == EnumFiguresType.EMPTY) {
                    move.add(new Coordinates(a,b + 1));
                    if(b == 1) {
                        if(board[a][b + 2].type == EnumFiguresType.EMPTY) {
                            move.add(new Coordinates(a,b + 2));
                        }
                    }
                }
            }

            if(a + 1 < 8 && b + 1 < 8) {
                if(board[a + 1][b + 1].type != EnumFiguresType.EMPTY) {
                    if(board[a + 1][b + 1].color != color) {
                        move.add(new Coordinates(a + 1,b + 1));
                    }
                }
            }

            if(a - 1 >= 0 && b + 1 < 8) {
                if(board[a - 1][b + 1].type != EnumFiguresType.EMPTY) {
                    if(board[a - 1][b + 1].color != color) {
                        move.add(new Coordinates(a - 1,b + 1));
                    }
                }
            }
        }

        else {
            if(b - 1 >= 0) {
                if(board[a][b - 1].type == EnumFiguresType.EMPTY) {
                    move.add(new Coordinates(a,b - 1));
                    if(b == 6) {
                        if(board[a][b - 2].type == EnumFiguresType.EMPTY) {
                            move.add(new Coordinates(a,b - 2));
                        }
                    }
                }
            }

            if(a + 1 < 8 && b - 1 >= 0) {
                if(board[a + 1][b - 1].type != EnumFiguresType.EMPTY) {
                    if(board[a + 1][b - 1].color != color) {
                        move.add(new Coordinates(a + 1,b - 1));
                    }
                }
            }

            if(a - 1 >= 0 && b - 1 >= 0) {
                if(board[a - 1][b - 1].type != EnumFiguresType.EMPTY) {
                    if(board[a - 1][b - 1].color != color) {
                        move.add(new Coordinates(a - 1,b - 1));
                    }
                }
            }
        }

        return move;
    }
}
