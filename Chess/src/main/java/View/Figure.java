package View;

import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Moves.Board;
import Moves.Coordinates;
import Game.Main;
import Moves.Move;
import Moves.MoveDefine;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * View.Figure represents method for figures.
 *
 * @author TimotejBarbus
 */
public class Figure extends StackPane {
    public Coordinates coordinatesOfFigure;
    public Color color = Color.BLACK;
    public static Figure[][] listOfFigures = new Figure[8][8];

    /**
     * Mouse event, when clicked on figure.
     *
     * @param coordinates represents coordinates of clicked figure
     */
    public Figure(Coordinates coordinates) {
        coordinatesOfFigure = new Coordinates(coordinates.a, coordinates.b);
        setOnMousePressed((MouseEvent click) -> MoveDefine.move(coordinatesOfFigure));
    }

    /**
     * Moves figure on coordinates, GUI.GUI.
     *
     * @param coordinates represents coordinates where it will be moved
     */
    public void move(Coordinates coordinates) {
        relocate(coordinates.a * GUI.TILESIZE, coordinates.b * GUI.TILESIZE + GUI.MENU);
        coordinatesOfFigure = new Coordinates(coordinates.a, coordinates.b);
    }

    /**
     * Removes figures from list of figures and then adds them into the list again, basically resets.
     */
    public static void removeLoadFiguresGUI() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                GUI.figuresGroup.getChildren().remove(listOfFigures[i][j]);
                if(Board.board[i][j].type != EnumFiguresType.EMPTY) {
                    Figure piece = new Figure(new Coordinates(i, j));
                    listOfFigures[i][j] = piece;
                    GUI.figuresGroup.getChildren().add(piece);
                }
            }
        }
    }

    /**
     * Upgrades pawn when he gets onto the last tile, GUI.GUI.
     *
     * @param coordinates represents coordinates of upgraded figure
     * @param type represents number of which type of figure is going to replace pawn
     */
    public static void upgradeFigureGUI(Coordinates coordinates, int type) {
        Figure figure = new Figure(new Coordinates(coordinates.a, coordinates.b));
        figure.relocate(GUI.TILESIZE * coordinates.a, GUI.TILESIZE * coordinates.b + GUI.MENU);

        Image figureImage = null;
        ImageView selectedImage = new ImageView();

        if(Move.playerColor == EnumFiguresColor.WHITE) {
            if(type == 1) {
                figureImage = new Image(Main.class.getResourceAsStream("queen_white.png"));
            }
            if(type == 2) {
                figureImage = new Image(Main.class.getResourceAsStream("rook_white.png"));
            }
            if(type == 3) {
                figureImage = new Image(Main.class.getResourceAsStream("knight_white.png"));
            }
            if(type == 4) {
                figureImage = new Image(Main.class.getResourceAsStream("bishop_white.png"));
            }
        }

        else {
            if(type == 1) {
                figureImage = new Image(Main.class.getResourceAsStream("queen_black.png"));
            }
            if(type == 2) {
                figureImage = new Image(Main.class.getResourceAsStream("rook_black.png"));
            }
            if(type == 3) {
                figureImage = new Image(Main.class.getResourceAsStream("knight_black.png"));
            }
            if(type == 4) {
                figureImage = new Image(Main.class.getResourceAsStream("bishop_black.png"));
            }
        }

        selectedImage.setImage(figureImage);
        figure.getChildren().add(selectedImage);
        listOfFigures[coordinates.a][coordinates.b] = figure;
        GUI.figuresGroup.getChildren().add(figure);
    }

    /**
     * Upgrades pawn when he gets onto the last tile, in multiplayer network game only, GUI.GUI.
     *
     * @param coordinates represents coordinates of upgraded figure
     * @param type represents number of which type of figure is going to replace pawn
     */
    public static void upgradeFigureMultiplayerGUI(Coordinates coordinates, int type) {
        Figure figure = new Figure(new Coordinates(coordinates.a, coordinates.b));
        figure.relocate(GUI.TILESIZE * coordinates.a, GUI.TILESIZE * coordinates.b + GUI.MENU);

        Image figureImage = null;
        ImageView selectedImage = new ImageView();

        if(Move.playerColor == EnumFiguresColor.BLACK) {
            if(type == 1) {
                figureImage = new Image(Main.class.getResourceAsStream("queen_white.png"));
            }
            if(type == 2) {
                figureImage = new Image(Main.class.getResourceAsStream("rook_white.png"));
            }
            if(type == 3) {
                figureImage = new Image(Main.class.getResourceAsStream("knight_white.png"));
            }
            if(type == 4) {
                figureImage = new Image(Main.class.getResourceAsStream("bishop_white.png"));
            }
        }

        else {
            if(type == 1) {
                figureImage = new Image(Main.class.getResourceAsStream("queen_black.png"));
            }
            if(type == 2) {
                figureImage = new Image(Main.class.getResourceAsStream("rook_black.png"));
            }
            if(type == 3) {
                figureImage = new Image(Main.class.getResourceAsStream("knight_black.png"));
            }
            if(type == 4) {
                figureImage = new Image(Main.class.getResourceAsStream("bishop_black.png"));
            }
        }

        selectedImage.setImage(figureImage);
        figure.getChildren().add(selectedImage);
        listOfFigures[coordinates.a][coordinates.b] = figure;
        GUI.figuresGroup.getChildren().add(figure);
    }

    /**
     * Creates figures by default, GUI.GUI.
     */
    public static void generateFiguresAfterLoad() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                GUI.figuresGroup.getChildren().remove(listOfFigures[i][j]);
                if(Board.board[i][j].type != EnumFiguresType.EMPTY) {
                    Figure figure = new Figure(new Coordinates(i, j));
                    figure.relocate(GUI.TILESIZE * i, GUI.TILESIZE * j + GUI.MENU);

                    Image figureImage = null;
                    ImageView selectedImage = new ImageView();

                    if(Board.board[i][j].color == EnumFiguresColor.BLACK) {
                        if(Board.board[i][j].type == EnumFiguresType.ROOK) {
                            figureImage = new Image(Main.class.getResourceAsStream("../rook_black.png"));
                        }
                        if(Board.board[i][j].type == EnumFiguresType.KNIGHT) {
                            figureImage = new Image(Main.class.getResourceAsStream("../knight_black.png"));
                        }
                        if(Board.board[i][j].type == EnumFiguresType.BISHOP) {
                            figureImage = new Image(Main.class.getResourceAsStream("../bishop_black.png"));
                        }
                        if(Board.board[i][j].type == EnumFiguresType.QUEEN) {
                            figureImage = new Image(Main.class.getResourceAsStream("../queen_black.png"));
                        }
                        if(Board.board[i][j].type == EnumFiguresType.KING) {
                            figureImage = new Image(Main.class.getResourceAsStream("../king_black.png"));
                        }
                        if(Board.board[i][j].type == EnumFiguresType.PAWN) {
                            figureImage = new Image(Main.class.getResourceAsStream("../pawn_black.png"));
                        }
                    }

                    if(Board.board[i][j].color == EnumFiguresColor.WHITE) {
                        if(Board.board[i][j].type == EnumFiguresType.ROOK) {
                            figureImage = new Image(Main.class.getResourceAsStream("../rook_white.png"));
                        }
                        if(Board.board[i][j].type == EnumFiguresType.KNIGHT) {
                            figureImage = new Image(Main.class.getResourceAsStream("../knight_white.png"));
                        }
                        if(Board.board[i][j].type == EnumFiguresType.BISHOP) {
                            figureImage = new Image(Main.class.getResourceAsStream("../bishop_white.png"));
                        }
                        if(Board.board[i][j].type == EnumFiguresType.QUEEN) {
                            figureImage = new Image(Main.class.getResourceAsStream("../queen_white.png"));
                        }
                        if(Board.board[i][j].type == EnumFiguresType.KING) {
                            figureImage = new Image(Main.class.getResourceAsStream("../king_white.png"));
                        }
                        if(Board.board[i][j].type == EnumFiguresType.PAWN) {
                            figureImage = new Image(Main.class.getResourceAsStream("../pawn_white.png"));
                        }
                    }

                    selectedImage.setImage(figureImage);
                    figure.getChildren().add(selectedImage);
                    listOfFigures[i][j] = figure;
                    GUI.figuresGroup.getChildren().add(figure);
                }
            }
        }
    }

    /**
     * Creates figures images by default, GUI.GUI.
     */
    public static void generateFigures() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(Board.board[i][j].type != EnumFiguresType.EMPTY) {
                    Figure figure = new Figure(new Coordinates(i, j));
                    figure.relocate(GUI.TILESIZE * i, GUI.TILESIZE * j + GUI.MENU);

                    Image figureImage = null;
                    ImageView selectedImage = new ImageView();

                    if (j == 0) {
                        if (i == 0 || i == 7) {
                            figureImage = new Image(Main.class.getResourceAsStream("../rook_black.png"));
                        }
                        if (i == 1 || i == 6) {
                            figureImage = new Image(Main.class.getResourceAsStream("../knight_black.png"));
                        }
                        if (i == 2 || i == 5) {
                            figureImage = new Image(Main.class.getResourceAsStream("../bishop_black.png"));
                        }
                        if (i == 3) {
                            figureImage = new Image(Main.class.getResourceAsStream("../queen_black.png"));
                        }
                        if (i == 4) {
                            figureImage = new Image(Main.class.getResourceAsStream("../king_black.png"));
                        }
                    }

                    if (j == 1) {
                        figureImage = new Image(Main.class.getResourceAsStream("../pawn_black.png"));
                    }
                    if (j == 6) {
                        figureImage = new Image(Main.class.getResourceAsStream("../pawn_white.png"));
                    }

                    if (j == 7) {
                        if (i == 0 || i == 7) {
                            figureImage = new Image(Main.class.getResourceAsStream("../rook_white.png"));
                        }
                        if (i == 1 || i == 6) {
                            figureImage = new Image(Main.class.getResourceAsStream("../knight_white.png"));
                        }
                        if (i == 2 || i == 5) {
                            figureImage = new Image(Main.class.getResourceAsStream("../bishop_white.png"));
                        }
                        if (i == 3) {
                            figureImage = new Image(Main.class.getResourceAsStream("../queen_white.png"));
                        }
                        if (i == 4) {
                            figureImage = new Image(Main.class.getResourceAsStream("../king_white.png"));
                        }
                    }

                    selectedImage.setImage(figureImage);
                    figure.getChildren().add(selectedImage);
                    listOfFigures[i][j] = figure;
                    GUI.figuresGroup.getChildren().add(figure);
                }
            }
        }
    }
}
