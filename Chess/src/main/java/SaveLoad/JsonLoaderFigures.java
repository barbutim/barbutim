package SaveLoad;

import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Figures.*;

/**
 * SaveLoad.JsonLoaderFigures represents method for saving and loading figures on board, JSON file
 *
 * @author TimotejBarbus
 */
public class JsonLoaderFigures {
    public EnumFiguresType type;
    public EnumFiguresColor color;

    /**
     * Constructor for SaveLoad.JsonLoaderFigures.
     *
     * @param type represents type of figure
     * @param color represents color of figure
     */
    public JsonLoaderFigures(EnumFiguresType type, EnumFiguresColor color) {
        this.type = type;
        this.color = color;
    }

    /**
     * Default constructor.
     */
    public JsonLoaderFigures() {
        super();
    }

    /**
     * Converts saved figures from JSON file onto the board.
     *
     * @return returns new figure
     */
    public AbstractFigures setLoadedGameFigures() {
        if(type == EnumFiguresType.KING) {
            return new King(type, color);
        }
        else if(type == EnumFiguresType.BISHOP) {
            return new Bishop(type, color);
        }
        else if(type == EnumFiguresType.QUEEN) {
            return new Queen(type, color);
        }
        else if(type == EnumFiguresType.KNIGHT) {
            return new Knight(type, color);
        }
        else if(type == EnumFiguresType.ROOK) {
            return new Rook(type, color);
        }
        else if(type == EnumFiguresType.PAWN) {
            return new Pawn(type, color);
        }
        else {
            return new Empty(type, color);
        }
    }

    public EnumFiguresColor getColor() {
        return color;
    }

    public EnumFiguresType getType() {
        return type;
    }

    public void setColor(EnumFiguresColor color) {
        this.color = color;
    }

    public void setType(EnumFiguresType type) {
        this.type = type;
    }
}
