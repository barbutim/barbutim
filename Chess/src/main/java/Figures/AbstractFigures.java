package Figures;

import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Moves.Coordinates;

import java.util.List;

/**
 * Figures.AbstractFigures represents abstract method for each figure on board.
 *
 * @author TimotejBarbus
 */
public abstract class AbstractFigures {
    public EnumFiguresType type;
    public EnumFiguresColor color;

    /**
     * Constructor for Figures.AbstractFigures.
     *
     * @param type respresents type of figures by Enums.EnumFiguresType
     * @param color represents color of figures by Enums.EnumFiguresColor
     */
    public AbstractFigures(EnumFiguresType type, EnumFiguresColor color) {
        this.type = type;
        this.color = color;
    }

    /**
     * Abstract method for each figure on board, gives coordinates where can current figure move.
     *
     * @param coordinates represents coordinates of current figure asked for accessible moves
     * @param board represents all figures on board
     * @param color represents color of current figure asked for accessible moves
     * @return returns list of coordinates of current figure asked for accessible moves
     */
    public abstract List<Coordinates> getAccessibleMoves(Coordinates coordinates, AbstractFigures[][] board, EnumFiguresColor color);

    public EnumFiguresType getType() {
        return type;
    }

    public EnumFiguresColor getColor() {
        return color;
    }

    public void setType(EnumFiguresType type) {
        this.type = type;
    }

    public void setColor(EnumFiguresColor color) {
        this.color = color;
    }
}
