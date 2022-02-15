package Figures;

import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Moves.Coordinates;

import java.util.List;

/**
 * Figures.Empty represents method for figure of empty and his available moves, which is an empty tile.
 *
 * @author TimotejBarbus
 */
public class Empty extends AbstractFigures {

    /**
     * Constructor for Figures.Empty.
     *
     * @param type represents type of figure by Enums.EnumFiguresType
     * @param color represents color of figure by Enums.EnumFiguresColor
     */
    public Empty(EnumFiguresType type, EnumFiguresColor color) {
        super(type, color);
    }

    /**
     * Gets all available moves by current figure, which is null, because it is an empty tile.
     *
     * @param coordinates represents coordinates of current figure asked for accessible moves
     * @param board represents all figures on board
     * @param color represents color of current figure asked for accessible moves
     * @return returns list of coordinates of current figure asked for accessible moves
     */
    public List<Coordinates> getAccessibleMoves(Coordinates coordinates, AbstractFigures[][] board, EnumFiguresColor color) {
        return null;
    }
}
