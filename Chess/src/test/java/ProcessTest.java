import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Figures.*;
import Game.Clock;
import Moves.Board;
import Moves.Check;
import Moves.Coordinates;
import Moves.Move;
import SaveLoad.JsonLoaderFigures;
import SaveLoad.JsonLoaderVariables;
import View.Figure;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProcessTest {

    @BeforeEach
    public void SetDefaultBoardForTesting() {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Board.board[i][j] = new Empty(EnumFiguresType.EMPTY, EnumFiguresColor.NONE);
            }
        }
        Board.board[2][1] = new Knight(EnumFiguresType.KNIGHT, EnumFiguresColor.BLACK);
        Board.board[1][2] = new Rook(EnumFiguresType.ROOK, EnumFiguresColor.WHITE);
        Board.board[5][2] = new Bishop(EnumFiguresType.BISHOP, EnumFiguresColor.BLACK);
        Board.board[3][3] = new King(EnumFiguresType.KING, EnumFiguresColor.BLACK);
        Board.board[6][4] = new King(EnumFiguresType.KING, EnumFiguresColor.WHITE);
        Board.board[2][6] = new Queen(EnumFiguresType.QUEEN, EnumFiguresColor.WHITE);

        Figure.listOfFigures[2][1] = new Figure(new Coordinates(2,1));
        Figure.listOfFigures[1][2] = new Figure(new Coordinates(1,2));
        Figure.listOfFigures[5][2] = new Figure(new Coordinates(5,2));
        Figure.listOfFigures[3][3] = new Figure(new Coordinates(3,3));
        Figure.listOfFigures[6][4] = new Figure(new Coordinates(6,4));
        Figure.listOfFigures[2][6] = new Figure(new Coordinates(2,6));
    }

    @Test
    public void SetLoadedGameFigures_LoadingGameFromSave_AllFiguresOnCorrectPosition() {
        String fileName = "load_game_test";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonLoaderFigures[][] jsonLoaderFigures = new JsonLoaderFigures[8][8];
        AbstractFigures[][] result = new AbstractFigures[8][8];

        try {
            jsonLoaderFigures = objectMapper.readValue(new File("src/savedgames/" + fileName + "Figures.json"), JsonLoaderFigures[][].class);
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    result[i][j] = jsonLoaderFigures[i][j].setLoadedGameFigures();
                }
            }
        }
        catch (IOException ignored) {

        }

        assertEquals(jsonLoaderFigures[2][1].getType(), result[2][1].type);
        assertEquals(jsonLoaderFigures[1][2].getType(), result[1][2].type);
        assertEquals(jsonLoaderFigures[5][2].getType(), result[5][2].type);
        assertEquals(jsonLoaderFigures[3][3].getType(), result[3][3].type);
        assertEquals(jsonLoaderFigures[6][4].getType(), result[6][4].type);
        assertEquals(jsonLoaderFigures[2][6].getType(), result[2][6].type);

        assertEquals(jsonLoaderFigures[2][1].getColor(), result[2][1].color);
        assertEquals(jsonLoaderFigures[1][2].getColor(), result[1][2].color);
        assertEquals(jsonLoaderFigures[5][2].getColor(), result[5][2].color);
        assertEquals(jsonLoaderFigures[3][3].getColor(), result[3][3].color);
        assertEquals(jsonLoaderFigures[6][4].getColor(), result[6][4].color);
        assertEquals(jsonLoaderFigures[2][6].getColor(), result[2][6].color);
    }

    @Test
    public void SetLoadedGameVariables_LoadingGameFromSave_AllVariablesWithCorrectStatus() {
        String fileName = "load_game_test";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonLoaderVariables jsonLoaderVariables = new JsonLoaderVariables();

        try {
            jsonLoaderVariables = objectMapper.readValue(new File("src/savedgames/" + fileName + "Variables.json"), JsonLoaderVariables.class);
            JsonLoaderVariables.setLoadedGameVariables(jsonLoaderVariables);
        }
        catch (IOException ignored) {

        }

        assertEquals(jsonLoaderVariables.getWhiteTime(), Clock.whiteTime);
        assertEquals(jsonLoaderVariables.getBlackTime(), Clock.blackTime);
        assertEquals(jsonLoaderVariables.getPlayerColor(), Move.playerColor);
        assertEquals(jsonLoaderVariables.isWhiteKingMoved(), Move.whiteKingMoved);
        assertEquals(jsonLoaderVariables.isBlackKingMoved(), Move.blackKingMoved);
        assertEquals(jsonLoaderVariables.isLeftWhiteRookMoved(), Move.leftWhiteRookMoved);
        assertEquals(jsonLoaderVariables.isRightWhiteRookMoved(), Move.rightWhiteRookMoved);
        assertEquals(jsonLoaderVariables.isLeftBlackRookMoved(), Move.leftBlackRookMoved);
        assertEquals(jsonLoaderVariables.isRightBlackRookMoved(), Move.rightBlackRookMoved);
    }

    @Test
    public void Move_WhiteRookMovesFrom12AndBlackKingMovesFrom33_WhiteRookMovesAndBlackKingDoesNotMove() {
        Coordinates rookCoordinates = new Coordinates(1 , 2);
        Coordinates newRookCoordinates = new Coordinates(1 , 3);
        Coordinates kingCoordinates = new Coordinates(3 , 3);
        Coordinates newKingCoordinates = new Coordinates(2 , 3);
        boolean resultCheck;
        boolean resultCheckMate;
        List<Coordinates> expectedResultRook = new ArrayList<Coordinates>() {{
            add(new Coordinates(1,2)); add(new Coordinates(1,1)); add(new Coordinates(1,0)); add(new Coordinates(1,3)); add(new Coordinates(1,4));
            add(new Coordinates(1,5)); add(new Coordinates(1,6)); add(new Coordinates(1,7)); add(new Coordinates(0,2)); add(new Coordinates(2,2));
            add(new Coordinates(3,2)); add(new Coordinates(4,2)); add(new Coordinates(5,2));
        }};
        Move.inGame = true;

        Move.move(rookCoordinates);
        assertEquals(expectedResultRook, Move.accessibleMoves);

        Move.move(newRookCoordinates);

        resultCheck = Check.checkIfCheck(EnumFiguresColor.WHITE, Board.board, new Coordinates(3,3));
        resultCheckMate = Check.checkIfCheckMate(EnumFiguresColor.WHITE, Board.board, new Coordinates(3,3));

        assertEquals(true, resultCheck);
        assertEquals(false, resultCheckMate);

        Move.move(kingCoordinates);
        Move.move(newKingCoordinates);

        assertEquals(EnumFiguresType.KING, Board.board[kingCoordinates.a][kingCoordinates.b].type);
        assertEquals(EnumFiguresType.EMPTY, Board.board[newKingCoordinates.a][newKingCoordinates.b].type);
    }

    @Test
    public void CheckIfCheckAndCheckMate_WhitePlayerMakesCheckMateInThreeTurns_CheckMateIsTrueWhenMovedLastFigure() {
        Coordinates whitePawnCoordinates = new Coordinates(4 , 6);
        Coordinates newWhitePawnCoordinates = new Coordinates(4 , 4);
        Coordinates blackPawnCoordinates = new Coordinates(4 , 1);
        Coordinates newBlackPawnCoordinates = new Coordinates(4 , 3);
        Coordinates whiteQueenCoordinates = new Coordinates(3 , 7);
        Coordinates newWhiteQueenCoordinates = new Coordinates(7 , 3);
        Coordinates blackKingCoordinates = new Coordinates(4 , 0);
        Coordinates newBlackKingCoordinates = new Coordinates(4 , 1);
        Coordinates whiteQueenCheckCoordinates = new Coordinates(7 , 3);
        Coordinates newWhiteQueenCheckCoordinates = new Coordinates(4 , 3);
        boolean resultCheck;
        boolean resultCheckMate;
        Move.inGame = true;

        Board.generateBoard();
        Figure.generateFigures();

        // 1
        Move.move(whitePawnCoordinates);
        Move.move(newWhitePawnCoordinates);

        resultCheck = Check.checkIfCheck(EnumFiguresColor.WHITE, Board.board, new Coordinates(4,0));
        resultCheckMate = Check.checkIfCheckMate(EnumFiguresColor.WHITE, Board.board, new Coordinates(4,0));

        assertEquals(false, resultCheck);
        assertEquals(false, resultCheckMate);

        Move.move(blackPawnCoordinates);
        Move.move(newBlackPawnCoordinates);

        resultCheck = Check.checkIfCheck(EnumFiguresColor.BLACK, Board.board, new Coordinates(4,7));
        resultCheckMate = Check.checkIfCheckMate(EnumFiguresColor.BLACK, Board.board, new Coordinates(4,7));

        assertEquals(false, resultCheck);
        assertEquals(false, resultCheckMate);

        // 2
        Move.move(whiteQueenCoordinates);
        Move.move(newWhiteQueenCoordinates);

        resultCheck = Check.checkIfCheck(EnumFiguresColor.WHITE, Board.board, new Coordinates(4,0));
        resultCheckMate = Check.checkIfCheckMate(EnumFiguresColor.WHITE, Board.board, new Coordinates(4,0));

        assertEquals(false, resultCheck);
        assertEquals(false, resultCheckMate);

        Move.move(blackKingCoordinates);
        Move.move(newBlackKingCoordinates);

        resultCheck = Check.checkIfCheck(EnumFiguresColor.BLACK, Board.board, new Coordinates(4,7));
        resultCheckMate = Check.checkIfCheckMate(EnumFiguresColor.BLACK, Board.board, new Coordinates(4,7));

        assertEquals(false, resultCheck);
        assertEquals(false, resultCheckMate);

        // 3
        Move.move(whiteQueenCheckCoordinates);
        try {
            Move.move(newWhiteQueenCheckCoordinates);
        }
        catch(RuntimeException ignored){

        }

        resultCheck = Check.checkIfCheck(EnumFiguresColor.WHITE, Board.board, new Coordinates(4,1));
        resultCheckMate = Check.checkIfCheckMate(EnumFiguresColor.WHITE, Board.board, new Coordinates(4,1));

        assertEquals(true, resultCheck);
        assertEquals(true, resultCheckMate);
    }
}
