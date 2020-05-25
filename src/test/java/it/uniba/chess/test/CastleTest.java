package it.uniba.chess.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.PrintStream;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.chess.Board;
import it.uniba.chess.Game;
import it.uniba.chess.InputValidator;
import it.uniba.chess.Square;
import it.uniba.chess.pieces.Bishop;
import it.uniba.chess.pieces.King;
import it.uniba.chess.pieces.Queen;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.GameStatus;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParseFiles;

public class CastleTest {
	private final PrintStream stdOut = System.out;

	@BeforeEach
	public void setUp(){
		Game.setStatus(GameStatus.INACTIVE);
	}
	
	@AfterEach
	public void tearDown() {

		System.setOut(stdOut);
	}
	@Test
	@DisplayName("Check successful white short castle with Rg1")
	public void castleShortWhiteWithRTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		//unmoved white rook
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
	
		Rook castledRook = new Rook(ChessColor.WHITE);
		castledRook.setHasMoved();
		
		King castledKing = new King(ChessColor.WHITE);
		castledKing.setHasMoved();
		
		expectedPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('f'), castledRook));
		
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('g')).setOccupied(true);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('g')).setPiece(castledKing);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('e')).setOccupied(false);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Rg1");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check successful black short castle with Rg8")
	public void castleShortBlackwithRmoveTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		//unmoved white rook
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		
		
		Rook castledRook = new Rook(ChessColor.BLACK);
		castledRook.setHasMoved();
		
		King castledKing = new King(ChessColor.BLACK);
		castledKing.setHasMoved();
		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('f'), castledRook));
		
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g')).setOccupied(true);
		expectedBoard.getSquare(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g')).setPiece(castledKing);
		expectedBoard.getSquare(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('e')).setOccupied(false);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Rg8");
		assertEquals(expectedBoard, Game.getBoard());
	}
	@Test
	@DisplayName("Check successful white short castle")
	public void castleShortWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		//unmoved white rook
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
	
		Rook castledRook = new Rook(ChessColor.WHITE);
		castledRook.setHasMoved();
		
		King castledKing = new King(ChessColor.WHITE);
		castledKing.setHasMoved();
		
		expectedPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('f'), castledRook));
		
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('g')).setOccupied(true);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('g')).setPiece(castledKing);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('e')).setOccupied(false);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("0-0");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check successful black short castle")
	public void castleShortBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		//unmoved white rook
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		
		
		Rook castledRook = new Rook(ChessColor.BLACK);
		castledRook.setHasMoved();
		
		King castledKing = new King(ChessColor.BLACK);
		castledKing.setHasMoved();
		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('f'), castledRook));
		
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g')).setOccupied(true);
		expectedBoard.getSquare(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g')).setPiece(castledKing);
		expectedBoard.getSquare(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('e')).setOccupied(false);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("0-0");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("short castle Wrong Color, is Black and not White")
	public void shortCastleWithWrongKingColorWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0, 3).setPiece(new King(ChessColor.BLACK));
		Game.getBoard().getSquare(7, 3).setPiece(new King(ChessColor.WHITE));
		
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
		
	}


	@Test
	@DisplayName("short castle Wrong Color, is White and not Black")
	public void shortCastleWithWrongKingColorBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7, 3).setPiece(new King(ChessColor.WHITE));
		Game.getBoard().getSquare(0, 3).setPiece(new King(ChessColor.BLACK));
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
	}
	
	@Test
	@DisplayName("short castle Wrong Piece, is a Bishop and not a King")
	public void shortCastleWithWrongPieceWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0, 3).setPiece(new Bishop(ChessColor.BLACK));
		
		
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
		
	}


	@Test
	@DisplayName("short castle Wrong Color, is White and not Black")
	public void shortCastleWithWrongPieceBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7, 3).setPiece(new Bishop(ChessColor.WHITE));
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
	}
	@Test
	@DisplayName("short castle but black king's square is not occupied")
	public void shortCastleNotOccupiedWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0,3).setOccupied(false);
	
		
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
		
	}


	@Test
	@DisplayName("short castle but black king's square is not occupied")
	public void shortCastleNotOccupiedBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7,3).setOccupied(false);
	
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
	}
	
	@Test
	@DisplayName("short castle with white but the king was previously moved")
	public void shortCastleWithMovedKingWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0, 3).getPiece().setHasMoved();
	
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
		
	}


	@Test
	@DisplayName("short castle with black but the king was previously moved")
	public void shortCastleWithMovedKingBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7, 3).getPiece().setHasMoved();
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
	}
	
	@Test
	@DisplayName("short castle with white but the king was previously moved with Rg1")
	public void shortCastleWithMovedKingWhiteWithRTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0, 3).getPiece().setHasMoved();
	
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rg1"));
		
	}


	@Test
	@DisplayName("short castle with black but the king was previously moved with Rg8")
	public void shortCastleWithMovedKingBlackWithRTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7, 3).getPiece().setHasMoved();
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rg8"));
	}
	
	@Test
	@DisplayName("short castle but white king is in check so is unsuccessful")
	public void shortCastleWhiteKingInCheckTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('e'), new Queen(ChessColor.BLACK)));
		
		Game.testGame(startingPosition);
	
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
		
	}


	@Test
	@DisplayName("short castle but black king is in check so is unsuccessful")
	public void shortCastleBlackKingInCheckTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('e'), new Queen(ChessColor.WHITE)));
		Game.testGame(startingPosition);

		Game.nextTurn();		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
	}
	
	@Test
	@DisplayName("short castle Wrong rook, is Black and not White")
	public void shortCastleWithWrongRookColorWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('g'), new Bishop(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
		
	}


	@Test
	@DisplayName("short castle Wrong rook, is White and not Black")
	public void shortCastleWithWrongRookColorBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), new Bishop(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
	}
	
	@Test
	@DisplayName("short castle Wrong Piece, is a Bishop and not a Rook")
	public void shortCastleWithWrongPieceRookWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Bishop(ChessColor.WHITE)));
		Game.testGame(startingPosition);
	
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
		
	}


	@Test
	@DisplayName("short castle Wrong Piece, is a Bishop and not a Rook")
	public void shortCastleWithWrongPieceRookBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Bishop(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
	}
	@Test
	@DisplayName("short castle but but black rook's square is not occupied")
	public void shortCastleNotOccupiedRookWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0,0).setOccupied(false);
		
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
		
	}


	@Test
	@DisplayName("short castle but but black rook's square is not occupied")
	public void shortCastleNotOccupiedRookBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7,0).setOccupied(false);
	
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
	}
	
	
	@Test
	@DisplayName("short castle with white but the rook was previously moved")
	public void shortCastleWithMovedRookTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0, 0).getPiece().setHasMoved();
	
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
		
	}


	@Test
	@DisplayName("short castle with black but the rook was previously moved")
	public void shortCastleWithMovedRookBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7, 0).getPiece().setHasMoved();
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
	}
	
	@Test
	@DisplayName("short castle with white but g1 is occupied")
	public void shortCastleWithg1OccupiedWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('g'), new Bishop(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
		
	}


	@Test
	@DisplayName("short castle with black but g8 is occupied")
	public void shortCastleWithg7OccupiedBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), new Bishop(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
	}
	
	@Test
	@DisplayName("short castle with white but f1 is occupied")
	public void shortCastleWithf1OccupiedWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('f'), new Bishop(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
		
	}


	@Test
	@DisplayName("short castle with black but f8 is occupied")
	public void shortCastleWithf7OccupiedBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('f'), new Bishop(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
	}
	
	@Test
	@DisplayName("short castle with white but g1 is check")
	public void shortCastleWithg1InCheckWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('g'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
		
	}


	@Test
	@DisplayName("short castle with black but g8 is in check")
	public void shortCastleWithg7inCheckBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('g'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
	}
	
	@Test
	@DisplayName("short castle with white but f1 is in check")
	public void shortCastleWithf1CheckWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('f'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
		
	}


	@Test
	@DisplayName("short castle with black but f8 is in check")
	public void shortCastleWithf7InCheckBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('f'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0"));
	}
	
	// LONG CASTLE
	@Test
	@DisplayName("Check successful white long castle with Rc1")
	public void castleLongWhitewithRTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		//unmoved white rook
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		
		Rook castledRook = new Rook(ChessColor.WHITE);
		castledRook.setHasMoved();
		
		King castledKing = new King(ChessColor.WHITE);
		castledKing.setHasMoved();
		
		expectedPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('d'), castledRook));
		
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('c')).setOccupied(true);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('c')).setPiece(castledKing);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('e')).setOccupied(false);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Rc1");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	
	
	@Test
	@DisplayName("Check successful black long castle with Rc8")
	public void castleLongBlackwithRTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		//unmoved white rook
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));

		Rook castledRook = new Rook(ChessColor.BLACK);
		castledRook.setHasMoved();
		
		King castledKing = new King(ChessColor.BLACK);
		castledKing.setHasMoved();
		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('d'), castledRook));
		
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('c')).setOccupied(true);
		expectedBoard.getSquare(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('c')).setPiece(castledKing);
		expectedBoard.getSquare(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('e')).setOccupied(false);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Rc8");
		assertEquals(expectedBoard, Game.getBoard());
	}
	@Test
	@DisplayName("Check successful white long castle")
	public void castleLongWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		//unmoved white rook
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		
		Rook castledRook = new Rook(ChessColor.WHITE);
		castledRook.setHasMoved();
		
		King castledKing = new King(ChessColor.WHITE);
		castledKing.setHasMoved();
		
		expectedPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('d'), castledRook));
		
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('c')).setOccupied(true);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('c')).setPiece(castledKing);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('e')).setOccupied(false);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("0-0-0");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	
	
	@Test
	@DisplayName("Check successful black long castle")
	public void castleLongBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		//unmoved white rook
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));

		Rook castledRook = new Rook(ChessColor.BLACK);
		castledRook.setHasMoved();
		
		King castledKing = new King(ChessColor.BLACK);
		castledKing.setHasMoved();
		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('d'), castledRook));
		
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('c')).setOccupied(true);
		expectedBoard.getSquare(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('c')).setPiece(castledKing);
		expectedBoard.getSquare(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('e')).setOccupied(false);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("0-0-0");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Wrong Color long castle, is Black and not White")
	public void longCastleWithWrongKingColorWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0, 3).setPiece(new King(ChessColor.BLACK));
		Game.getBoard().getSquare(7, 3).setPiece(new King(ChessColor.WHITE));
		
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
		
	}


	@Test
	@DisplayName("Wrong Color long castle, is White and not Black")
	public void longCastleWithWrongKingColorBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7, 3).setPiece(new King(ChessColor.WHITE));
		Game.getBoard().getSquare(0, 3).setPiece(new King(ChessColor.BLACK));
		
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	
	@Test
	@DisplayName("short castle Wrong Piece, is a Bishop and not a King")
	public void longCastleWithWrongPieceWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0, 3).setPiece(new Bishop(ChessColor.BLACK));
		Game.getBoard().getSquare(7, 3).setPiece(new King(ChessColor.WHITE));
		
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
		
	}


	@Test
	@DisplayName("short castle Wrong Color, is White and not Black")
	public void longCastleWithWrongPieceBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7, 3).setPiece(new Bishop(ChessColor.WHITE));
		Game.getBoard().getSquare(0, 3).setPiece(new King(ChessColor.BLACK));
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	
	@Test
	@DisplayName("long castle but but black king's square is not occupied")
	public void longCastleNotOccupiedWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0,3).setOccupied(false);
		Game.getBoard().getSquare(7, 3).setPiece(new King(ChessColor.WHITE));
		
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
		
	}


	@Test
	@DisplayName("long castle but but black king's square is not occupied")
	public void longCastleNotOccupiedBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7,3).setOccupied(false);
		Game.getBoard().getSquare(0, 3).setPiece(new King(ChessColor.BLACK));
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	
	@Test
	@DisplayName("long castle  with white but the king was previously moved")
	public void longCastleWithMovedKingWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0, 3).getPiece().setHasMoved();
	
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
		
	}


	@Test
	@DisplayName("long castle with black but the king was previously moved")
	public void longCastleWithMovedKingBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7, 3).getPiece().setHasMoved();
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	
	@Test
	@DisplayName("long castle  with white but the king was previously moved with Rc1")
	public void longCastleWithMovedKingWhiteWithRTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0, 3).getPiece().setHasMoved();
	
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rc1"));
		
	}


	@Test
	@DisplayName("long castle with black but the king was previously moved with Rc8")
	public void longCastleWithMovedKingBlackWithRTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7, 3).getPiece().setHasMoved();
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rc8"));
	}
	
	@Test
	@DisplayName("long castle but white king is in check so is unsuccessful")
	public void longCastleWhiteKingInCheckTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('e'), new Queen(ChessColor.BLACK)));
		
		Game.testGame(startingPosition);
	
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
		
	}


	@Test
	@DisplayName("long castle but black king is in check so is unsuccessful")
	public void longCastleBlackKingInCheckTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('e'), new Queen(ChessColor.WHITE)));
		Game.testGame(startingPosition);

		Game.nextTurn();		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	
	@Test
	@DisplayName("long castle Wrong rook, is Black and not White")
	public void longCastleWithWrongRookColorWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('c'), new Bishop(ChessColor.WHITE)));
		Game.testGame(startingPosition);
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	@Test
	@DisplayName("long castle Wrong rook, is White and not Black")
	public void longCastleWithWrongRookColorBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('b'), new Bishop(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	
	@Test
	@DisplayName("long castle Wrong Piece, is a Bishop and not a Rook")
	public void longCastleWithWrongPieceRookWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Bishop(ChessColor.WHITE)));
		Game.testGame(startingPosition);
	
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
		
	}


	@Test
	@DisplayName("long castle Wrong Piece, is a Bishop and not a Rook")
	public void longCastleWithWrongPieceRookBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Bishop(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	@Test
	@DisplayName("long castle but but black rook's square is not occupied")
	public void longCastleNotOccupiedRookWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0,7).setOccupied(false);
		
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
		
	}


	@Test
	@DisplayName("long castle but but black rook's square is not occupied")
	public void longCastleNotOccupiedRookBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7,7).setOccupied(false);
	
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	
	
	@Test
	@DisplayName("long castle with white but the rook was previously moved")
	public void longCastleWithMovedRookTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(0, 7).getPiece().setHasMoved();
	
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
		
	}


	@Test
	@DisplayName("long castle with black but the rook was previously moved")
	public void longCastleWithMovedRookBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.getBoard().getSquare(7, 7).getPiece().setHasMoved();
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	
	@Test
	@DisplayName("long castle with white but d1 is occupied")
	public void longCastleWithd1OccupiedWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
		
	}


	@Test
	@DisplayName("long castle with black but d8 is occupied")
	public void longCastleWithd8OccupiedBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	
	@Test
	@DisplayName("long castle with white but c1 is occupied")
	public void longCastleWithc1OccupiedWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('c'), new Bishop(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
		
	}


	@Test
	@DisplayName("long castle with black but c8 is occupied")
	public void longCastleWithc8OccupiedBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('c'), new Bishop(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	
	@Test
	@DisplayName("long castle with white but b1 is occupied")
	public void longCastleWithb1OccupiedWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('b'), new Bishop(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
		
	}


	@Test
	@DisplayName("long castle with black but b8 is occupied")
	public void longCastleWithb8OccupiedBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('b'), new Bishop(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	
	@Test
	@DisplayName("long castle with white but d1 is check")
	public void longCastleWithd1InCheckWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
		
	}


	@Test
	@DisplayName("long castle with black but d8 is in check")
	public void longCastleWithd8inCheckBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}
	
	@Test
	@DisplayName("long castle with white but c1 is in check")
	public void longCastleWithc1CheckWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
		
	}


	@Test
	@DisplayName("long castle with black but c8 is in check")
	public void longCastleWithc8InCheckBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		
		startingPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("0-0-0"));
	}


}
