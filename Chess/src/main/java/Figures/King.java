package Figures;

import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Moves.Coordinates;
import Moves.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Figures.King represents method for figure of king and his available moves.
 *
 * @author TimotejBarbus
 */
public class King extends AbstractFigures {

    /**
     * Constructor for Figures.King.
     *
     * @param type represents type of figure by Enums.EnumFiguresType
     * @param color represents color of figure by Enums.EnumFiguresColor
     */
    public King(EnumFiguresType type, EnumFiguresColor color) {
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

        if(a + 1 < 8) {
            if(board[a + 1][b].type == EnumFiguresType.EMPTY) {
                move.add(new Coordinates(a + 1, b));
            }
            else {
                if(board[a + 1][b].color != color) {
                    move.add(new Coordinates(a + 1, b));
                }
            }
        }

        if(a - 1 >= 0) {
            if(board[a - 1][b].type == EnumFiguresType.EMPTY) {
                move.add(new Coordinates(a - 1, b));
            }
            else {
                if(board[a - 1][b].color != color) {
                    move.add(new Coordinates(a - 1, b));
                }
            }
        }

        if(b + 1 < 8) {
            if(board[a][b + 1].type == EnumFiguresType.EMPTY) {
                move.add(new Coordinates(a, b + 1));
            }
            else {
                if(board[a][b + 1].color != color) {
                    move.add(new Coordinates(a, b + 1));
                }
            }
        }

        if(b - 1 >= 0) {
            if(board[a][b - 1].type == EnumFiguresType.EMPTY) {
                move.add(new Coordinates(a, b - 1));
            }
            else {
                if(board[a][b - 1].color != color) {
                    move.add(new Coordinates(a, b - 1));
                }
            }
        }

        if(a + 1 < 8 && b + 1 < 8) {
            if(board[a + 1][b + 1].type == EnumFiguresType.EMPTY) {
                move.add(new Coordinates(a + 1, b + 1));
            }
            else {
                if(board[a + 1][b + 1].color != color) {
                    move.add(new Coordinates(a + 1, b + 1));
                }
            }
        }

        if(a + 1 < 8 && b - 1 >= 0) {
            if(board[a + 1][b - 1].type == EnumFiguresType.EMPTY) {
                move.add(new Coordinates(a + 1, b - 1));
            }
            else {
                if(board[a + 1][b - 1].color != color) {
                    move.add(new Coordinates(a + 1, b - 1));
                }
            }
        }

        if(a - 1 >= 0 && b + 1 < 8) {
            if(board[a - 1][b + 1].type == EnumFiguresType.EMPTY) {
                move.add(new Coordinates(a - 1, b + 1));
            }
            else {
                if(board[a - 1][b + 1].color != color) {
                    move.add(new Coordinates(a - 1, b + 1));
                }
            }
        }

        if(a - 1 >= 0 && b - 1 >= 0) {
            if(board[a - 1][b - 1].type == EnumFiguresType.EMPTY) {
                move.add(new Coordinates(a - 1, b - 1));
            }
            else {
                if(board[a - 1][b - 1].color != color) {
                    move.add(new Coordinates(a - 1, b - 1));
                }
            }
        }

        if(color == EnumFiguresColor.WHITE) {
            if(!Move.whiteKingMoved) {
                if(!Move.leftWhiteRookMoved) {
                    if(board[1][7].type == EnumFiguresType.EMPTY && board[2][7].type == EnumFiguresType.EMPTY && board[3][7].type == EnumFiguresType.EMPTY) {
                        Move.castlingWhite = true;
                        move.add(new Coordinates(2, 7));
                    }
                }
                if(!Move.rightWhiteRookMoved) {
                    if(board[5][7].type == EnumFiguresType.EMPTY && board[6][7].type == EnumFiguresType.EMPTY) {
                        Move.castlingWhite = true;
                        move.add(new Coordinates(6, 7));
                    }
                }
            }
        }

        if(color == EnumFiguresColor.BLACK) {
            if(!Move.blackKingMoved) {
                if(!Move.leftBlackRookMoved) {
                    if(board[1][0].type == EnumFiguresType.EMPTY && board[2][0].type == EnumFiguresType.EMPTY && board[3][0].type == EnumFiguresType.EMPTY) {
                        Move.castlingBlack = true;
                        move.add(new Coordinates(2, 0));
                    }
                }
                if(!Move.rightBlackRookMoved) {
                    if(board[5][0].type == EnumFiguresType.EMPTY && board[6][0].type == EnumFiguresType.EMPTY) {
                        Move.castlingBlack = true;
                        move.add(new Coordinates(6, 0));
                    }
                }
            }
        }

        return move;
    }
}