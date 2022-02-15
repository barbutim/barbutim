package Moves;

import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Figures.Empty;
import NetworkGame.HomePC;
import View.Figure;
import View.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Moves.Move represents method for moving figures by standard player.
 *
 * @author TimotejBarbus
 */
public class Move extends Board {
    public static EnumFiguresColor playerColor = EnumFiguresColor.WHITE;
    public static boolean change = false;
    public static boolean freeMove = false;
    public static boolean clickedOnFigure = false;
    public static boolean inGame = false;
    public static boolean whiteKingMoved = false;
    public static boolean blackKingMoved = false;
    public static boolean leftWhiteRookMoved = false;
    public static boolean rightWhiteRookMoved = false;
    public static boolean leftBlackRookMoved = false;
    public static boolean rightBlackRookMoved = false;
    public static boolean castlingWhite = false;
    public static boolean castlingBlack = false;
    public static boolean upgrade = false;
    public static List<Coordinates> accessibleMoves = new ArrayList<>();
    public static Coordinates clickedFigureCoordinates;
    public static Coordinates whiteKingCoordinates = new Coordinates(4,7);
    public static Coordinates blackKingCoordinates = new Coordinates(4,0);

    /**
     * Updates all important variables.
     *
     * @param coordinates represents coordinates of moved figure
     */
    public static void controlVariables(Coordinates coordinates) {
        if(Board.board[clickedFigureCoordinates.a][clickedFigureCoordinates.b].type == EnumFiguresType.KING) {
            if(Board.board[clickedFigureCoordinates.a][clickedFigureCoordinates.b].color == EnumFiguresColor.WHITE) {
                whiteKingCoordinates = coordinates;
                whiteKingMoved = true;
            }
            else {
                blackKingCoordinates = coordinates;
                blackKingMoved = true;
            }
        }

        if(Board.board[clickedFigureCoordinates.a][clickedFigureCoordinates.b].type == EnumFiguresType.ROOK) {
            if(Board.board[clickedFigureCoordinates.a][clickedFigureCoordinates.b].color == EnumFiguresColor.WHITE) {
                if(clickedFigureCoordinates.a == 0 && clickedFigureCoordinates.b == 7) {
                    leftWhiteRookMoved = true;
                }
                if(clickedFigureCoordinates.a == 7 && clickedFigureCoordinates.b == 7) {
                    rightWhiteRookMoved = true;
                }
            }
            else {
                if(clickedFigureCoordinates.a == 0 && clickedFigureCoordinates.b == 0) {
                    leftBlackRookMoved = true;
                }
                if(clickedFigureCoordinates.a == 7 && clickedFigureCoordinates.b == 0) {
                    rightBlackRookMoved = true;
                }
            }
        }

        if(Board.board[coordinates.a][coordinates.b].type == EnumFiguresType.ROOK) {
            if(Board.board[coordinates.a][coordinates.b].color == EnumFiguresColor.WHITE) {
                if(coordinates.a == 0 && coordinates.b == 7) {
                    leftWhiteRookMoved = true;
                }
                if(coordinates.a == 7 && coordinates.b == 7) {
                    rightWhiteRookMoved = true;
                }
            }
            else {
                if(coordinates.a == 0 && coordinates.b == 0) {
                    leftBlackRookMoved = true;
                }
                if(coordinates.a == 7 && coordinates.b == 0) {
                    rightBlackRookMoved = true;
                }
            }
        }

        if(castlingWhite && Board.board[clickedFigureCoordinates.a][clickedFigureCoordinates.b].type == EnumFiguresType.KING) {
            if(coordinates.a == 2) {
                changePosition(new Coordinates(3, 7), new Coordinates(0, 7));
            }
            if(coordinates.a == 6) {
                changePosition(new Coordinates(5, 7), new Coordinates(7, 7));
            }
            castlingWhite = false;
        }

        if(castlingBlack && Board.board[clickedFigureCoordinates.a][clickedFigureCoordinates.b].type == EnumFiguresType.KING) {
            if(coordinates.a == 2) {
                changePosition(new Coordinates(3, 0), new Coordinates(0, 0));
            }
            if(coordinates.a == 6) {
                changePosition(new Coordinates(5, 0), new Coordinates(7, 0));
            }
            castlingBlack = false;
        }
    }

    /**
     * Changes position from one coordinates to the other.
     *
     * @param coordinates represents new coordinates
     * @param clickedFigure represents old coordinates
     */
    public static void changePosition(Coordinates coordinates, Coordinates clickedFigure) {
        Board.board[coordinates.a][coordinates.b] = Board.board[clickedFigure.a][clickedFigure.b];
        Board.board[clickedFigure.a][clickedFigure.b] = new Empty(EnumFiguresType.EMPTY, EnumFiguresColor.NONE);

        Figure.listOfFigures[clickedFigure.a][clickedFigure.b].move(coordinates);
        Figure.listOfFigures[coordinates.a][coordinates.b] = Figure.listOfFigures[clickedFigure.a][clickedFigure.b];
        Figure.listOfFigures[clickedFigure.a][clickedFigure.b] = null;
    }

    /**
     * Moves figure from coordinates to other coordinates, both are randomly chosen. (function calls twice)
     * Method also controls if king is in check or checkmate.
     *
     * @param coordinates represents coordinates of clicked figure
     */
    public static void move(Coordinates coordinates) {
        change = false;
        if(freeMove) {
            if(!clickedOnFigure) {

                if(Board.board[coordinates.a][coordinates.b].color == EnumFiguresColor.WHITE) {
                    playerColor = EnumFiguresColor.WHITE;
                    accessibleMoves = Board.board[coordinates.a][coordinates.b].getAccessibleMoves(coordinates, Board.board, EnumFiguresColor.WHITE);
                    for(int i = 0; i < accessibleMoves.size(); i++) {
                        if(i != 0) {
                            accessibleMoves.remove(i);
                            i--;
                        }
                    }
                    Tile.highlightTiles(accessibleMoves);
                    clickedFigureCoordinates = coordinates;
                    clickedOnFigure = true;
                }

                if(Board.board[coordinates.a][coordinates.b].color == EnumFiguresColor.BLACK) {
                    playerColor = EnumFiguresColor.BLACK;
                    accessibleMoves = Board.board[coordinates.a][coordinates.b].getAccessibleMoves(coordinates, Board.board, EnumFiguresColor.BLACK);
                    for(int i = 1; i < accessibleMoves.size(); i++) {
                        if(i != 0) {
                            accessibleMoves.remove(i);
                            i--;
                        }
                    }
                    Tile.highlightTiles(accessibleMoves);
                    clickedFigureCoordinates = coordinates;
                    clickedOnFigure = true;
                }
            }

            else {
                if(clickedFigureCoordinates != coordinates) {
                    if(Board.board[coordinates.a][coordinates.b].color != EnumFiguresColor.NONE) {
                        if(Board.board[coordinates.a][coordinates.b].type == EnumFiguresType.KING) {
                            System.out.println("You cannot remove any of kings!");
                        }
                        else {
                            Board.removeFigure(coordinates);
                            controlVariables(coordinates);
                            changePosition(coordinates, clickedFigureCoordinates);
                        }
                    }
                    else {
                        controlVariables(coordinates);
                        changePosition(coordinates, clickedFigureCoordinates);

                    }
                    if(Board.board[coordinates.a][coordinates.b].type == EnumFiguresType.PAWN) {
                        if(playerColor == EnumFiguresColor.WHITE && coordinates.b == 0) {
                            Board.upgradeFigure(coordinates);
                        }
                        if(playerColor == EnumFiguresColor.BLACK && coordinates.b == 7) {
                            Board.upgradeFigure(coordinates);
                        }
                    }
                }

                Tile.generateBoard();
                clickedOnFigure = false;
            }
        }

        if(inGame) {
            if(!clickedOnFigure) {
                if(Board.board[coordinates.a][coordinates.b].color == playerColor) {
                    if(playerColor == EnumFiguresColor.WHITE) {
                        accessibleMoves = Check.checkAccessibleMoves(coordinates, Board.board, playerColor, whiteKingCoordinates);
                    }
                    else {
                        accessibleMoves = Check.checkAccessibleMoves(coordinates, Board.board, playerColor, blackKingCoordinates);
                    }

                    Tile.highlightTiles(accessibleMoves);
                    clickedFigureCoordinates = coordinates;
                    clickedOnFigure = true;
                }
            }
            else {
                if(clickedFigureCoordinates.a == coordinates.a && clickedFigureCoordinates.b == coordinates.b) {
                    Tile.generateBoard();
                    clickedOnFigure = false;
                }
                else {
                    for(int i = 1; i < accessibleMoves.size(); i++) {
                        if(coordinates.a == accessibleMoves.get(i).a && coordinates.b == accessibleMoves.get(i).b) {
                            if(Board.board[accessibleMoves.get(i).a][accessibleMoves.get(i).b].color != EnumFiguresColor.NONE) {
                                Board.removeFigure(accessibleMoves.get(i));
                            }

                            controlVariables(accessibleMoves.get(i));
                            changePosition(accessibleMoves.get(i), clickedFigureCoordinates);

                            Tile.generateBoard();
                            clickedOnFigure = false;
                            change = true;

                            if(Board.board[coordinates.a][coordinates.b].type == EnumFiguresType.PAWN) {
                                if(playerColor == EnumFiguresColor.WHITE && coordinates.b == 0 && !HomePC.messageReceived) {
                                    upgrade = true;
                                    Board.upgradeFigure(accessibleMoves.get(i));
                                }
                                if(playerColor == EnumFiguresColor.BLACK && coordinates.b == 7 && !HomePC.messageReceived) {
                                    upgrade = true;
                                    Board.upgradeFigure(accessibleMoves.get(i));
                                }
                            }
                            i = accessibleMoves.size();
                        }
                    }

                    if(playerColor == EnumFiguresColor.WHITE) {
                        if(Check.checkIfCheck(playerColor, Board.board, blackKingCoordinates)) {
                            if(Check.checkIfCheckMate(playerColor, Board.board, blackKingCoordinates)) {
                                Check.checkMate(playerColor);
                                inGame = false;
                            }
                        }
                    }

                    if(playerColor == EnumFiguresColor.BLACK) {
                        if(Check.checkIfCheck(playerColor, Board.board, whiteKingCoordinates)) {
                            if(Check.checkIfCheckMate(playerColor, Board.board, whiteKingCoordinates)) {
                                Check.checkMate(playerColor);
                                inGame = false;
                            }
                        }
                    }

                    if(!clickedOnFigure) {
                        if(playerColor == EnumFiguresColor.BLACK) {
                            playerColor = EnumFiguresColor.WHITE;
                        }
                        else {
                            playerColor = EnumFiguresColor.BLACK;
                        }
                    }
                    HomePC.messageReceived = false;
                }
            }
        }
    }
}
