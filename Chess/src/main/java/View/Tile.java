package View;

import Moves.Coordinates;
import Moves.MoveDefine;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.List;

/**
 * View.Tile represents method for each tile on board.
 *
 * @author TimotejBarbus
 */
public class Tile extends Rectangle {
    public Coordinates coordinatesOfFigure;
    public static Color white = Color.rgb(247, 231, 204);
    public static Color black = Color.rgb(123, 58, 52);
    public static Color highlightStart = Color.rgb(193, 249, 252);
    public static Color highlightElseOdd = Color.rgb(50, 165, 35);
    public static Color highlightElseEven = Color.rgb(100, 200, 100);
    public static int newRow = 0;
    public static Color color = black;

    /**
     * Constructor for View.Tile.
     * Mouse event, when clicked moves figure on current tile.
     *
     * @param coordinates represents coordinates of clicked tile
     */
    public Tile(Coordinates coordinates) {
        this.coordinatesOfFigure = coordinates;
        setOnMousePressed((MouseEvent click) -> MoveDefine.move(coordinatesOfFigure));
    }

    /**
     * Generates tiles of board, GUI.GUI.
     */
    public static void generateBoard() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile tile = new Tile(new Coordinates(i, j));
                if(newRow == i) {
                    if(color == white) {
                        color = black;
                    }
                    else {
                        color = white;
                    }
                }
                else {
                    newRow = i;
                }

                tile.setWidth(GUI.TILESIZE);
                tile.setHeight(GUI.TILESIZE);
                tile.setFill(color);
                tile.relocate(GUI.TILESIZE * i, GUI.TILESIZE * j + GUI.MENU);
                GUI.tileGroup.getChildren().add(tile);
            }
        }
    }

    /**
     * Highlights tiles, where can clicked figure move, by accessible moves.
     *
     * @param coordinates represents list of coordinates to highlight
     */
    public static void highlightTiles(List<Coordinates> coordinates) {
        for(int i = 0; i < coordinates.size(); i++) {
            Tile tile = new Tile(new Coordinates(coordinates.get(i).a, coordinates.get(i).b));
            if(i == 0) {
                tile.setFill(highlightStart);
            }
            else {
                if((coordinates.get(i).a + coordinates.get(i).b) % 2 == 0) {
                    tile.setFill(highlightElseEven);
                }
                else {
                    tile.setFill(highlightElseOdd);
                }
            }
            tile.setWidth(GUI.TILESIZE);
            tile.setHeight(GUI.TILESIZE);
            tile.relocate(GUI.TILESIZE * coordinates.get(i).a, GUI.TILESIZE * coordinates.get(i).b + GUI.MENU);
            GUI.tileGroup.getChildren().add(tile);
        }
    }
}
