package Moves;

import Enums.EnumFiguresColor;
import Game.Main;
import Menus.SetupInCommandLine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Moves.MoveDefine represents method for move definition.
 *
 * @author TimotejBarbus
 */
public class MoveDefine {
    public static boolean waiting = false;

    /**
     * Requests move from other PC, when in multiplayer network game.
     *
     * @param coordinates represents sent coordinates
     */
    public static void moveRequest(Coordinates coordinates) {
        ObjectMapper objectMapper = new ObjectMapper();
        String stringCoordinates;
        try {
            stringCoordinates = objectMapper.writeValueAsString(coordinates);
            Main.client.send(stringCoordinates);
        } catch (JsonProcessingException error) {
            Logger.getLogger(MoveDefine.class.getName()).log(Level.SEVERE, null, error);
        }
    }

    /**
     * Defines which move function will be called at which situation.
     *
     * @param coordinates represents clicked coordinates
     */
    public static void move(Coordinates coordinates){
        if(SetupInCommandLine.intGameMode == 2) {
            Move.move(coordinates);
        }
        else if(SetupInCommandLine.intGameMode == 1) {
            Move.move(coordinates);
            if((Move.playerColor == EnumFiguresColor.BLACK && SetupInCommandLine.player1 == EnumFiguresColor.WHITE) ||
                    (Move.playerColor == EnumFiguresColor.WHITE && SetupInCommandLine.player1 == EnumFiguresColor.BLACK)) {
                if(Move.inGame) {
                    AI.move();
                }
            }
        }
        else if(SetupInCommandLine.intGameMode == 3 && !waiting){
            Move.move(coordinates);
            moveRequest(coordinates);
            if(Move.upgrade) {
                moveRequest(new Coordinates(-2, Board.intFigureType));
                Move.upgrade = false;
            }
            if(Move.inGame && Move.change) {
                waiting = true;
            }
        }
    }
}
