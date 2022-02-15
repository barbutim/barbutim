package SaveLoad;

import Enums.EnumFiguresColor;
import Game.Clock;
import Moves.Move;

/**
 * SaveLoad.JsonLoaderVariables represents method for saving and loading all important variables, JSON file.
 *
 * @author TimotejBarbus
 */
public class JsonLoaderVariables {
    public int whiteTime;
    public int blackTime;
    public EnumFiguresColor playerColor;
    public boolean whiteKingMoved;
    public boolean blackKingMoved;
    public boolean leftWhiteRookMoved;
    public boolean rightWhiteRookMoved;
    public boolean leftBlackRookMoved;
    public boolean rightBlackRookMoved;

    /**
     * Constructor for SaveLoad.JsonLoaderVariables.
     *
     * @param whiteTime represents time for white player
     * @param blackTime represents time for black player
     * @param playerColor represents color of player, who is playing right now
     * @param whiteKingMoved represents status if white king has moved from its default position
     * @param blackKingMoved represents status if black king has moved from its default position
     * @param leftWhiteRookMoved represents status if left white rook has moved from its default position
     * @param rightWhiteRookMoved represents status if right white rook has moved from its default position
     * @param leftBlackRookMoved represents status if left black rook has moved from its default position
     * @param rightBlackRookMoved represents status if right black rook has moved from its default position
     */
    public JsonLoaderVariables(int whiteTime, int blackTime, EnumFiguresColor playerColor, boolean whiteKingMoved, boolean blackKingMoved,
                               boolean leftWhiteRookMoved, boolean rightWhiteRookMoved, boolean leftBlackRookMoved, boolean rightBlackRookMoved) {
        this.whiteTime = whiteTime;
        this.blackTime = blackTime;
        this.playerColor = playerColor;
        this.whiteKingMoved = whiteKingMoved;
        this.blackKingMoved = blackKingMoved;
        this.leftWhiteRookMoved = leftWhiteRookMoved;
        this.rightWhiteRookMoved = rightWhiteRookMoved;
        this.leftBlackRookMoved = leftBlackRookMoved;
        this.rightBlackRookMoved = rightBlackRookMoved;
    }

    /**
     * Default constructor.
     */
    public JsonLoaderVariables() {
        super();
    }

    /**
     * Converts saved variables from JSON file into standard variables.
     *
     * @return returns updated variables
     */
    public static void setLoadedGameVariables(JsonLoaderVariables jsonLoaderVariables) {
        Clock.whiteTime = jsonLoaderVariables.getWhiteTime();
        Clock.blackTime = jsonLoaderVariables.getBlackTime();
        Move.playerColor = jsonLoaderVariables.getPlayerColor();
        Move.whiteKingMoved = jsonLoaderVariables.isWhiteKingMoved();
        Move.blackKingMoved = jsonLoaderVariables.isBlackKingMoved();
        Move.leftWhiteRookMoved = jsonLoaderVariables.isLeftWhiteRookMoved();
        Move.rightWhiteRookMoved = jsonLoaderVariables.isRightWhiteRookMoved();
        Move.leftBlackRookMoved = jsonLoaderVariables.isLeftBlackRookMoved();
        Move.rightBlackRookMoved = jsonLoaderVariables.isRightBlackRookMoved();
    }

    public int getWhiteTime() {
        return whiteTime;
    }

    public int getBlackTime() {
        return blackTime;
    }

    public EnumFiguresColor getPlayerColor() {
        return playerColor;
    }

    public boolean isWhiteKingMoved() {
        return whiteKingMoved;
    }

    public boolean isBlackKingMoved() {
        return blackKingMoved;
    }

    public boolean isLeftWhiteRookMoved() {
        return leftWhiteRookMoved;
    }

    public boolean isRightWhiteRookMoved() {
        return rightWhiteRookMoved;
    }

    public boolean isLeftBlackRookMoved() {
        return leftBlackRookMoved;
    }

    public boolean isRightBlackRookMoved() {
        return rightBlackRookMoved;
    }

    public void setWhiteTime(int whiteTime) {
        this.whiteTime = whiteTime;
    }

    public void setBlackTime(int blackTime) {
        this.blackTime = blackTime;
    }

    public void setPlayerColor(EnumFiguresColor playerColor) {
        this.playerColor = playerColor;
    }

    public void setWhiteKingMoved(boolean whiteKingMoved) {
        this.whiteKingMoved = whiteKingMoved;
    }

    public void setBlackKingMoved(boolean blackKingMoved) {
        this.blackKingMoved = blackKingMoved;
    }

    public void setLeftWhiteRookMoved(boolean leftWhiteRookMoved) {
        this.leftWhiteRookMoved = leftWhiteRookMoved;
    }

    public void setRightWhiteRookMoved(boolean rightWhiteRookMoved) {
        this.rightWhiteRookMoved = rightWhiteRookMoved;
    }

    public void setLeftBlackRookMoved(boolean leftBlackRookMoved) {
        this.leftBlackRookMoved = leftBlackRookMoved;
    }

    public void setRightBlackRookMoved(boolean rightBlackRookMoved) {
        this.rightBlackRookMoved = rightBlackRookMoved;
    }
}