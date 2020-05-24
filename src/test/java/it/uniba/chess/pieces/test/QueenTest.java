package it.uniba.chess.pieces.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.chess.Board;
import it.uniba.chess.Game;
import it.uniba.chess.InputValidator;
import it.uniba.chess.Square;
import it.uniba.chess.pieces.Queen;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParseFiles;

public class QueenTest {
	@Test
	@DisplayName("Check white Queen movement")
	public void queenWhiteMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Queen movedQueen = new Queen(ChessColor.WHITE);
		movedQueen.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Queen(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), movedQueen));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Da5");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Queen movement")
	public void queenBlackMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Queen movedQueen = new Queen(ChessColor.BLACK);
		movedQueen.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Queen(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), movedQueen));;
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Da5");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check white Queen movement")
	public void queenWhiteDiagonalMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Queen movedQueen = new Queen(ChessColor.WHITE);
		movedQueen.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Queen(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('f'), movedQueen));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Df3");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Queen movement")
	public void queenBlackDiagonalMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Queen movedQueen = new Queen(ChessColor.BLACK);
		movedQueen.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Queen(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('f'), movedQueen));;
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Df3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check white Queen movement")
	public void queenWhiteIllegalMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();

		Queen movedQueen = new Queen(ChessColor.WHITE);
		movedQueen.setHasMoved();
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Queen(ChessColor.WHITE)));		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Dd5"));
	}

	@Test
	@DisplayName("Check black Queen movement")
	public void queenBlackIllegalMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Queen movedQueen = new Queen(ChessColor.BLACK);
		movedQueen.setHasMoved();
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Queen(ChessColor.BLACK)));		
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Dd5"));
	}
	@Test
	@DisplayName("More than one white Queen, illegal move")
	public void queenWhiteIllegalMoveTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Queen(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('b'), new Queen(ChessColor.WHITE)));
		
		Game.testGame(startingPosition);
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Dd3"));
	}
	
	@Test
	@DisplayName("More than one black Queen, illegal move")
	public void queenBlackIllegalMoveTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Queen(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('b'), new Queen(ChessColor.BLACK)));
		
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Dd3"));
	}
	
	@Test
	@DisplayName("east Check test with white")
	public void eastWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Queen whiteQueen = new Queen(ChessColor.WHITE);
		Rook whiteRook = new Rook(ChessColor.WHITE);
		
		whiteQueen.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Queen(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('f'), new Rook(ChessColor.WHITE)));
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('d'), whiteQueen));
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('f'), whiteRook));
		
		Game.testGame(startingPosition);
		Board expectedBoard = new Board(expectedPosition);
		
		InputValidator.parseCommand("Dd3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("east Check test with white")
	public void eastBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook blackRook = new Rook(ChessColor.BLACK);
		Queen blackQueen = new Queen(ChessColor.BLACK);
		
		blackQueen.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('g'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Queen(ChessColor.BLACK)));
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('g'), blackRook));
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('d'), blackQueen));
		
		Game.testGame(startingPosition);
		Board expectedBoard = new Board(expectedPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Dd3");
		assertEquals(expectedBoard, Game.getBoard());
	}
}
