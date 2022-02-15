import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Figures.*;
import Moves.Board;
import Moves.Coordinates;
import Moves.Move;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RookTest {

    @BeforeEach
    public void SetDefaultBoardForTesting() {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Board.board[i][j] = new Empty(EnumFiguresType.EMPTY, EnumFiguresColor.NONE);
            }
        }
        Board.board[0][0] = new Rook(EnumFiguresType.ROOK, EnumFiguresColor.BLACK);
        Board.board[4][0] = new King(EnumFiguresType.KING, EnumFiguresColor.BLACK);
        Board.board[2][1] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.WHITE);
        Board.board[4][1] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.BLACK);
        Board.board[7][1] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.BLACK);
        Board.board[0][2] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.BLACK);
        Board.board[1][2] = new Knight(EnumFiguresType.KNIGHT, EnumFiguresColor.WHITE);
        Board.board[3][2] = new Queen(EnumFiguresType.QUEEN, EnumFiguresColor.BLACK);
        Board.board[5][2] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.BLACK);
        Board.board[7][2] = new Rook(EnumFiguresType.ROOK, EnumFiguresColor.WHITE);
        Board.board[1][3] = new Bishop(EnumFiguresType.BISHOP, EnumFiguresColor.BLACK);
        Board.board[3][3] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.BLACK);
        Board.board[4][3] = new Bishop(EnumFiguresType.BISHOP, EnumFiguresColor.BLACK);
        Board.board[5][3] = new Bishop(EnumFiguresType.BISHOP, EnumFiguresColor.WHITE);
        Board.board[6][3] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.BLACK);
        Board.board[2][4] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.WHITE);
        Board.board[4][4] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.WHITE);
        Board.board[5][4] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.WHITE);
        Board.board[6][4] = new Knight(EnumFiguresType.KNIGHT, EnumFiguresColor.WHITE);
        Board.board[7][4] = new Knight(EnumFiguresType.KNIGHT, EnumFiguresColor.BLACK);
        Board.board[0][5] = new Knight(EnumFiguresType.KNIGHT, EnumFiguresColor.BLACK);
        Board.board[4][5] = new Queen(EnumFiguresType.QUEEN, EnumFiguresColor.WHITE);
        Board.board[5][5] = new Rook(EnumFiguresType.ROOK, EnumFiguresColor.BLACK);
        Board.board[6][5] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.WHITE);
        Board.board[0][6] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.BLACK);
        Board.board[1][6] = new Bishop(EnumFiguresType.BISHOP, EnumFiguresColor.WHITE);
        Board.board[3][6] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.WHITE);
        Board.board[7][6] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.WHITE);
        Board.board[4][7] = new King(EnumFiguresType.KING, EnumFiguresColor.WHITE);
        Board.board[7][7] = new Rook(EnumFiguresType.ROOK, EnumFiguresColor.WHITE);
        Move.leftWhiteRookMoved = true;
        Move.rightBlackRookMoved = true;
    }

    @Test
    public void AccessibleMoves_WhiteRookMovesFrom77_WhiteRookHas3AccessibleMoves() {
        Coordinates coordinates = new Coordinates(7 , 7);
        List<Coordinates> result;
        List<Coordinates> expectedResult = new ArrayList<Coordinates>() {{
            add(new Coordinates(7,7)); add(new Coordinates(6,7)); add(new Coordinates(5,7));
        }};

        result = Board.board[coordinates.a][coordinates.b].getAccessibleMoves(new Coordinates(coordinates.a, coordinates.b), Board.board, EnumFiguresColor.WHITE);

        assertEquals(expectedResult, result);
    }

    @Test
    public void AccessibleMoves_BlackRookMovesFrom55_RookHas6AccessibleMoves() {
        Coordinates coordinates = new Coordinates(5 , 5);
        List<Coordinates> result;
        List<Coordinates> expectedResult = new ArrayList<Coordinates>() {{
            add(new Coordinates(5,5)); add(new Coordinates(4,5)); add(new Coordinates(6,5)); add(new Coordinates(5,4)); add(new Coordinates(5,6));
            add(new Coordinates(5,7));
        }};

        result = Board.board[coordinates.a][coordinates.b].getAccessibleMoves(new Coordinates(coordinates.a, coordinates.b), Board.board, EnumFiguresColor.BLACK);

        assertEquals(expectedResult, result);
    }
}
