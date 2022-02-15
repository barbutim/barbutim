package Moves;

import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Menus.SetupInCommandLine;
import View.Tile;

import java.util.List;
import java.util.Random;

/**
 * Moves.AI represents method for moving figures by Moves.AI, which is player who's moves are made randomly.
 *
 * @author TimotejBarbus
 */
public class AI {
    public static EnumFiguresColor allyColor = SetupInCommandLine.player2;
    public static boolean repeat = true;
    public static List<Coordinates> accessibleMoves;
    public static int a = 0;
    public static int b = 0;
    public static int size = 0;

    /**
     * Moves figure from coordinates to other coordinates, both are randomly chosen.
     * Method also controls if king is in check or checkmate.
     */
    public static void move() {
        Random random = new Random();

        repeat = true;

        while(repeat) {
            a = random.nextInt(8);
            b = random.nextInt(8);

            if(Board.board[a][b].color != allyColor) {
                repeat = true;
            }

            else {
                Move.clickedFigureCoordinates = new Coordinates(a, b);
                if(Board.board[a][b].type != EnumFiguresType.EMPTY && Board.board[a][b].color == allyColor) {

                    if(allyColor == EnumFiguresColor.WHITE) {
                        accessibleMoves = Check.checkAccessibleMoves(Move.clickedFigureCoordinates, Board.board, allyColor, Move.whiteKingCoordinates);
                    } else {
                        accessibleMoves = Check.checkAccessibleMoves(Move.clickedFigureCoordinates, Board.board, allyColor, Move.blackKingCoordinates);
                    }

                    if(accessibleMoves.size() < 2) {
                        repeat = true;
                    }
                    else {
                        repeat = false;
                        size = random.nextInt(accessibleMoves.size() - 1);
                        size++;

                        if(Board.board[accessibleMoves.get(size).a][accessibleMoves.get(size).b].color != EnumFiguresColor.NONE) {
                            Board.removeFigure(accessibleMoves.get(size));
                        }

                        Move.controlVariables(accessibleMoves.get(size));
                        Move.changePosition(accessibleMoves.get(size), Move.clickedFigureCoordinates);
                        Tile.generateBoard();

                        if(Board.board[accessibleMoves.get(size).a][accessibleMoves.get(size).b].type == EnumFiguresType.PAWN) {
                            if(Move.playerColor == EnumFiguresColor.WHITE && accessibleMoves.get(size).b == 0) {
                                Board.upgradeFigureByAI(accessibleMoves.get(size));
                            }
                            if(Move.playerColor == EnumFiguresColor.BLACK && accessibleMoves.get(size).b == 7) {
                                Board.upgradeFigureByAI(accessibleMoves.get(size));
                            }
                        }

                        if(Move.playerColor == EnumFiguresColor.WHITE) {
                            if(Check.checkIfCheck(Move.playerColor, Board.board, Move.blackKingCoordinates)) {
                                if(Check.checkIfCheckMate(Move.playerColor, Board.board, Move.blackKingCoordinates)) {
                                    Check.checkMate(Move.playerColor);
                                }
                            }
                        }

                        if(Move.playerColor == EnumFiguresColor.BLACK) {
                            if(Check.checkIfCheck(Move.playerColor, Board.board, Move.whiteKingCoordinates)) {
                                if(Check.checkIfCheckMate(Move.playerColor, Board.board, Move.whiteKingCoordinates)) {
                                    Check.checkMate(Move.playerColor);
                                }
                            }
                        }

                        if(Move.playerColor == EnumFiguresColor.BLACK) {
                            Move.playerColor = EnumFiguresColor.WHITE;
                        }
                        else {
                            Move.playerColor = EnumFiguresColor.BLACK;
                        }
                    }
                }
                else {
                    repeat = true;
                }
            }
        }
    }
}
