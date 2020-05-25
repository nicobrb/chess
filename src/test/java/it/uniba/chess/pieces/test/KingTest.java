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
import it.uniba.chess.pieces.King;
import it.uniba.chess.pieces.Knight;
import it.uniba.chess.pieces.Pawn;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParseFiles;

public class KingTest {

	@Test
	@DisplayName("Check white King movement")
	public void kingWhiteMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		King movedKing = new King(ChessColor.WHITE);
		movedKing.setHasMoved();
		
		expectedPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), movedKing));
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(0, 3).setOccupied(false);
		Game.setKingPosition(ChessColor.WHITE, Game.getBoard().getSquare(1, 4));
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Rd2");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black King movement")
	public void kingBlackMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		King movedKing = new King(ChessColor.BLACK);
		movedKing.setHasMoved();
		
		expectedPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), movedKing));
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(7, 3).setOccupied(false);
		Game.setKingPosition(ChessColor.BLACK, Game.getBoard().getSquare(6, 4));
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Rd7");
		assertEquals(expectedBoard, Game.getBoard());
	}
	@Test
	@DisplayName("Check white King capture")
	public void kingWhiteCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		King movedKing = new King(ChessColor.WHITE);
		movedKing.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), new King(ChessColor.BLACK)));
		expectedPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), movedKing));
		
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(0, 3).setOccupied(false);
		Game.setKingPosition(ChessColor.WHITE, Game.getBoard().getSquare(1, 4));
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Rxd2");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black King capture")
	public void kingBlackCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		King movedKing = new King(ChessColor.BLACK);
		movedKing.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), new King(ChessColor.WHITE)));
		expectedPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), movedKing));
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(7, 3).setOccupied(false);
		Game.setKingPosition(ChessColor.BLACK, Game.getBoard().getSquare(6, 4));
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Rxd7");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("White King can't capture a White Rook")
	public void whiteKingVsBlackKingCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd2"));
		
	}
	
	@Test
	@DisplayName("Black King can't capture a Black Rook")
	public void blackKingVsWhiteKingCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);

		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd7"));
		
	}
	
	@Test
	@DisplayName("White King can't capture that Rook because is protected by a knight")
	public void whiteKingCaptureDefendedByKnightTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd2"));
		
	}
	
	@Test
	@DisplayName("Black King can't capture that Rook because is protected by a knight")
	public void blackKingCaptureDefendedByKnightTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);

		Game.nextTurn();

		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd7"));
		
	}
	

	@Test
	@DisplayName("White King can't capture that Rook because is protected by a pawn")
	public void whiteKingCaptureDefendedByPawnTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('c'), new Pawn(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd2"));
		
	}
	
	@Test
	@DisplayName("Black King can't capture that Rook because is protected by a pawn")
	public void blackKingCaptureDefendedByPawnTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), new Pawn(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);

		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd7"));
		
	}
	
	@Test
	@DisplayName("White King can't capture that Rook because is protected by a King")
	public void whiteKingCaptureDefendedByKingTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('c'), new King(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd2"));
		
	}
	
	@Test
	@DisplayName("Black King can't capture that Rook because is protected by a King")
	public void blackKingCaptureDefendedByKingTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), new King(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);

		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd7"));
		
	}
	@Test
	@DisplayName("White King can't move because there are 2 pawns defending")
	public void whiteKingMoveDefendedByPawnsTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('c'), new Pawn(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('d'), new Pawn(ChessColor.BLACK)));
		Game.testGame(startingPosition);
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rd2"));
		
	}
	
	@Test
	@DisplayName("Black King can't move because there are 2 pawns defending")
	public void blackKingMoveDefendedByPawnsTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), new Pawn(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('e'), new Pawn(ChessColor.WHITE)));
		Game.testGame(startingPosition);

		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rd7"));
		
	}
	
	@Test
	@DisplayName("White King disambiguation")
	public void whiteKingDisambiguationErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();

		Game.testGame(startingPosition);
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("R1d2"));
		
	}
	
	@Test
	@DisplayName("Black King disambiguation")
	public void blackKingDisambiguationErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		Game.testGame(startingPosition);

		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("R8d7"));
		
	}
}
