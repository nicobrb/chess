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
import it.uniba.chess.pieces.Bishop;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParseFiles;

public class BishopTest {

	@Test
	@DisplayName("Check white Bishop movement")
	public void bishopWhiteMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), movedBishop));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Ag8");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Bishop movement")
	public void bishopBlackMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), movedBishop));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Ag8");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check white Bishop movement when rank disambiguation is passed")
	public void bishopWhiteMovementWithRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), movedBishop));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("A5g8");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Bishop movement when rank disambiguation is passed")
	public void bishopBlackMovementwithRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), movedBishop));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("A5g8");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	
	@Test
	@DisplayName("Check white Bishop movement when file disambiguation is passed")
	public void bishopWhiteMovementWithFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), movedBishop));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Adg8");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Bishop movement when file disambiguation is passed")
	public void bishopBlackMovementwithFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), movedBishop));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Adg8");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	
	@Test
	@DisplayName("Check white Bishop movement when wrong rank disambiguation is passed")
	public void bishopWhiteMovementWithWrongRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));		
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("A6g8"));
	}

	@Test
	@DisplayName("Check black Bishop movement when wrong rank disambiguation is passed")
	public void bishopBlackMovementwithWrongRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("A6g8"));
	
	}
	@Test
	@DisplayName("Check white Bishop movement when wrong file disambiguation is passed")
	public void bishopWhiteMovementWithWrongFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));		
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Abg8"));
	}

	@Test
	@DisplayName("Check black Bishop movement when wrong file disambiguation is passed")
	public void bishopBlackMovementwithWrongFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Abg8"));
	
	}
	
	
	@Test
	@DisplayName("White Bishop puts his king in check thus it's illegal move")
	public void whitePinTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("3"),ParseFiles.getFileIntFromChar('e'), new Bishop(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("6"),ParseFiles.getFileIntFromChar('e'), new Rook(ChessColor.BLACK)));
		
		Game.testGame(startingPosition);

		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Ad3"));
		
	}
	
	@Test
	@DisplayName("Black Bishop puts his king in check thus it's illegal move")
	public void blackPinTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("4"),ParseFiles.getFileIntFromChar('e'), new Bishop(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"),ParseFiles.getFileIntFromChar('e'), new Rook(ChessColor.WHITE)));
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Ad6"));
		
	}
	
	
	@Test
	@DisplayName("White Bishop puts his king in check while capturing thus it's illegal move")
	public void whitePinCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("3"),ParseFiles.getFileIntFromChar('e'), new Bishop(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("6"),ParseFiles.getFileIntFromChar('e'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"),ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);

		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Axd3"));
		
	}
	
	@Test
	@DisplayName("Black Bishop puts his king in check thus while capturingit's illegal move")
	public void blackPinCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("4"),ParseFiles.getFileIntFromChar('e'), new Bishop(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"),ParseFiles.getFileIntFromChar('e'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("5"),ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Axd6"));
		
	}
}
