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
import it.uniba.chess.pieces.Pawn;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParseFiles;

public class RookTest {
	@Test
	@DisplayName("Check white Rook movement")
	public void rookWhiteMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Ta5");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Rook movement")
	public void rookBlackMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Ta5");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check white Rook capture")
	public void rookWhiteCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Txa5");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Rook capture")
	public void rookBlackCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Txa5");
		assertEquals(expectedBoard, Game.getBoard());
	}
	@Test
	@DisplayName("Check white rook movement when rank disambiguation is passed")
	public void rookWhiteMovementWithRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("T5e5");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black rook movement when rank disambiguation is passed")
	public void rookBlackMovementwithRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("T5e5");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	@Test
	@DisplayName("Check white rook movement when file disambiguation is passed")
	public void rookWhiteMovementWithFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('d'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Tdd1");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black rook movement when file disambiguation is passed")
	public void rookBlackMovementwithFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('d'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Tdd1");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	@Test
	@DisplayName("Check white rook movement when rank disambiguation is passed but is different from the final rank")
	public void rookWhiteMovementWithDifferentRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('d'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("T5d1");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black rook movement when rank disambiguation is passed but is different from the final rank")
	public void rookBlackMovementwithDifferentRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('d'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("T5d1");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	
	@Test
	@DisplayName("Check white rook movement when rank disambiguation is passed but is different from the final rank no2")
	public void rookWhiteMovementWithDifferentRank2Test() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("T5d7");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black rook movement when rank disambiguation is passed but is different from the final rank no2")
	public void rookBlackMovementwithDifferentRank2Test() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("T5d7");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	
	@Test
	@DisplayName("Check white rook movement when file disambiguation is passed but is different from the final file")
	public void rookWhiteMovementWithDifferentFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Tde5");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black rook movement when file disambiguation is passed but is different from the final file")
	public void rookBlackMovementwithDifferentFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Tde5");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	
	@Test
	@DisplayName("Check white rook movement when file disambiguation is passed but is different from the final file no2")
	public void rookWhiteMovementWithDifferentFile2Test() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Tdc5");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black rook movement when file disambiguation is passed but is different from the final file no2")
	public void rookBlackMovementwithDifferentFile2Test() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Tdc5");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	
	@Test
	@DisplayName("Check white rook movement when file disambiguation is passed but there is a piece inbetween")
	public void rookWhiteMovementWithDifferentFileErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Pawn(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdb5"));
	}

	@Test
	@DisplayName("Check black rook movement when file disambiguation is passed but there is a piece inbetween")
	public void rookBlackMovementwithDifferentFileErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Pawn(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdb5"));
	
	}
	
	@Test
	@DisplayName("Check white rook movement when rank disambiguation is passed but there is no Rook there")
	public void rookWhiteMovementWithDifferentRankNoRookErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T3b5"));
	}

	@Test
	@DisplayName("Check black rook movement when rank disambiguation is passed but there is no Rook there")
	public void rookBlackMovementwithDifferentRankNoRookErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T3b5"));
	
	}
	
	@Test
	@DisplayName("Check white rook movement when file disambiguation is passed but there is no Rook there")
	public void rookWhiteMovementWithDifferentFileNoRookErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tac5"));
	}

	@Test
	@DisplayName("Check black rook movement when file disambiguation is passed but there is no Rook there")
	public void rookBlackMovementwithDifferentFileNoRookErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tac5"));
	
	}
	
	@Test
	@DisplayName("There is a rook but is black and not white")
	public void wrongRookWhiteColorYErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
	
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdb5"));
	}

	@Test
	@DisplayName("There is a rook but is white and not black")
	public void wrongRookBlackColorYErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdb5"));
	}
	
	@Test
	@DisplayName("There is a white piece but it's not a rook")
	public void itIsNotAWhiteRookYErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));
	
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdb5"));
	}

	@Test
	@DisplayName("There is a black piece but it's not a rook")
	public void itIsNotABlackRookYErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdb5"));
	}
	@Test
	@DisplayName("There is a rook but is black and not white")
	public void wrongRookWhiteColorXErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
	
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d3"));
	}

	@Test
	@DisplayName("There is a rook but is white and not black")
	public void wrongRookBlackColorXErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d3"));
	}
	
	@Test
	@DisplayName("There is a white piece but it's not a rook")
	public void itIsNotAWhiteRookXErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));
	
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d3"));
	}

	@Test
	@DisplayName("There is a black piece but it's not a rook")
	public void itIsNotABlackRookXErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d3"));
	}
	
	@Test
	@DisplayName("White Rook no1 but final square is occupied")
	public void rookWhitePieceInbetweenLowerErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('d'), new Pawn(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d1"));
	}

	@Test
	@DisplayName("Black Rook no1 but final square is occupied")
	public void rookBlackPieceInbetweenLowerErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('d'), new Pawn(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d1"));
	}
	@Test
	@DisplayName("White Rook no2 but final square is occupied")
	public void rookWhitePieceInbetweenUpperErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('d'), new Pawn(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d8"));
	}

	@Test
	@DisplayName("Black Rook no2 but final square is occupied")
	public void rookBlackPieceInbetweenUpperErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('d'), new Pawn(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d8"));
	}
	
	@Test
	@DisplayName("White Rook no2 but final square is occupied")
	public void rookWhitePieceInbetweenWesternErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), new Pawn(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tda5"));
	}

	@Test
	@DisplayName("Black Rook no3 but final square is occupied")
	public void rookBlackPieceInbetweeWesternErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), new Pawn(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tda5"));
	}
	
	@Test
	@DisplayName("White Rook no3 but final square is occupied")
	public void rookWhitePieceInbetweenEasternErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), new Pawn(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdh5"));
	}

	@Test
	@DisplayName("Black Rook no2 but final square is occupied")
	public void rookBlackPieceInbetweenEasternErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), new Pawn(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdh5"));
	}
	
	@Test
	@DisplayName("no white rooks in this rank ")
	public void noWhiteRooksXTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T2d2"));
	}

	@Test
	@DisplayName("no black rooks in this rank")
	public void noBlackRooksXTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();

	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T2d2"));
	}
	
	@Test
	@DisplayName("no white rooks in this rank ")
	public void noWhiteRooksYTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdd2"));
	}

	@Test
	@DisplayName("no black rooks in this rank")
	public void noBlackRooksYTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();

	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdd2"));
	}
	@Test
	@DisplayName("Two White Rooks One Rank")
	public void whiteRooksWithXDisambiguationErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('g'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T3d3"));
	}

	@Test
	@DisplayName("two Black Rooks One Rank")
	public void blackRooksWithXDisambiguationErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('g'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T3d3"));
	}
	
	@Test
	@DisplayName("Two White Rooks One File")
	public void whiteRooksWithYDisambiguationErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('b'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('b'), new Rook(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tbb5"));
	}

	@Test
	@DisplayName("two Black Rooks One File")
	public void blackRooksWithYDisambiguationErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('b'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('b'), new Rook(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tbb5"));
	}
	@Test
	@DisplayName("Two White Rooks ")
	public void whiteRooksErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('g'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Td3"));
	}

	@Test
	@DisplayName("two Black Rooks")
	public void blackRooksErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('g'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Td3"));
	}

}
