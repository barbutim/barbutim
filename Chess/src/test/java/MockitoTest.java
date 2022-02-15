import Enums.EnumFiguresColor;
import Enums.EnumFiguresType;
import Figures.*;
import Moves.Board;
import Moves.Check;
import Moves.Coordinates;
import Moves.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockitoTest {

    @BeforeEach
    public void SetDefaultBoardForTesting() {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Board.board[i][j] = new Empty(EnumFiguresType.EMPTY, EnumFiguresColor.NONE);
            }
        }
        Board.board[1][0] = new Knight(EnumFiguresType.KNIGHT, EnumFiguresColor.BLACK);
        Board.board[3][0] = new Queen(EnumFiguresType.QUEEN, EnumFiguresColor.BLACK);
        Board.board[5][0] = new Bishop(EnumFiguresType.BISHOP, EnumFiguresColor.BLACK);
        Board.board[6][0] = new Knight(EnumFiguresType.KNIGHT, EnumFiguresColor.BLACK);
        Board.board[7][0] = new Rook(EnumFiguresType.ROOK, EnumFiguresColor.BLACK);
        for(int i = 0; i < 8; i++) {
            Board.board[i][1] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.BLACK);
        }
        Board.board[0][3] = new Rook(EnumFiguresType.ROOK, EnumFiguresColor.WHITE);
        Board.board[1][3] = new Rook(EnumFiguresType.ROOK, EnumFiguresColor.BLACK);
        Board.board[4][3] = new King(EnumFiguresType.KING, EnumFiguresColor.BLACK);
        Board.board[4][4] = new Bishop(EnumFiguresType.BISHOP, EnumFiguresColor.BLACK);
        Board.board[1][5] = new Bishop(EnumFiguresType.BISHOP, EnumFiguresColor.WHITE);
        Board.board[4][5] = new Queen(EnumFiguresType.QUEEN, EnumFiguresColor.WHITE);
        for(int i = 0; i < 8; i++) {
            Board.board[i][6] = new Pawn(EnumFiguresType.PAWN, EnumFiguresColor.WHITE);
        }
        Board.board[1][7] = new Knight(EnumFiguresType.KNIGHT, EnumFiguresColor.WHITE);
        Board.board[2][7] = new Bishop(EnumFiguresType.BISHOP, EnumFiguresColor.WHITE);
        Board.board[4][7] = new King(EnumFiguresType.KING, EnumFiguresColor.WHITE);
        Board.board[6][7] = new Knight(EnumFiguresType.KNIGHT, EnumFiguresColor.WHITE);
        Board.board[7][7] = new Rook(EnumFiguresType.ROOK, EnumFiguresColor.WHITE);
        Move.leftWhiteRookMoved = true;
        Move.leftBlackRookMoved = true;
    }

    @Test
    public void CheckAccessibleMoves_WhiteBishopMovesFrom15_WhiteBishopHas6CheckAccessibleMoves() {
        Coordinates coordinates = new Coordinates(1 , 5);
        Bishop mockBishop = mock(Figures.Bishop.class);
        Board.board[coordinates.a][coordinates.b] = mockBishop;
        List<Coordinates> result;
        List<Coordinates> expectedResult = new ArrayList<Coordinates>() {{
            add(new Coordinates(1,5)); add(new Coordinates(0,4)); add(new Coordinates(2,4)); add(new Coordinates(3,3)); add(new Coordinates(4,2));
            add(new Coordinates(5,1));
        }};

        when(mockBishop.getAccessibleMoves(coordinates, Board.board, EnumFiguresColor.WHITE)).thenReturn(new ArrayList<Coordinates>() {{
            add(new Coordinates(1,5)); add(new Coordinates(0,4)); add(new Coordinates(2,4)); add(new Coordinates(3,3)); add(new Coordinates(4,2));
            add(new Coordinates(5,1));
        }});

        result = Check.checkAccessibleMoves(coordinates, Board.board, EnumFiguresColor.WHITE, new Coordinates(4,7));

        assertEquals(expectedResult, result);
    }

    @Test
    public void CheckAccessibleMoves_BlackBishopMovesFrom44_BlackBishopHas1CheckAccessibleMove() {
        Coordinates coordinates = new Coordinates(4 , 4);
        Bishop mockBishop = mock(Figures.Bishop.class);
        Board.board[coordinates.a][coordinates.b] = mockBishop;
        List<Coordinates> result;
        List<Coordinates> expectedResult = new ArrayList<Coordinates>() {{
            add(new Coordinates(4,4));
        }};

        when(mockBishop.getAccessibleMoves(coordinates, Board.board, EnumFiguresColor.BLACK)).thenReturn(new ArrayList<Coordinates>() {{
            add(new Coordinates(4,4)); add(new Coordinates(3,5)); add(new Coordinates(2,6)); add(new Coordinates(5,5)); add(new Coordinates(6,6));
            add(new Coordinates(3,3)); add(new Coordinates(2,2)); add(new Coordinates(5,3)); add(new Coordinates(6,2));
        }});

        result = Check.checkAccessibleMoves(coordinates, Board.board, EnumFiguresColor.BLACK, new Coordinates(4,3));

        assertEquals(expectedResult, result);
    }

    @Test
    public void CheckAccessibleMoves_BlackRookMovesFrom13_BlackRookHas3CheckAccessibleMoves() {
        Coordinates coordinates = new Coordinates(1 , 3);
        Rook mockRook = mock(Figures.Rook.class);
        Board.board[coordinates.a][coordinates.b] = mockRook;
        List<Coordinates> result;
        List<Coordinates> expectedResult = new ArrayList<Coordinates>() {{
            add(new Coordinates(1,3)); add(new Coordinates(0,3)); add(new Coordinates(2,3)); add(new Coordinates(3,3));
        }};

        when(mockRook.getAccessibleMoves(coordinates, Board.board, EnumFiguresColor.BLACK)).thenReturn(new ArrayList<Coordinates>() {{
            add(new Coordinates(1,3)); add(new Coordinates(0,3)); add(new Coordinates(2,3)); add(new Coordinates(3,3)); add(new Coordinates(1,2));
            add(new Coordinates(1,4)); add(new Coordinates(1,5));
        }});

        result = Check.checkAccessibleMoves(coordinates, Board.board, EnumFiguresColor.BLACK, new Coordinates(4,3));

        assertEquals(expectedResult, result);
    }
}
