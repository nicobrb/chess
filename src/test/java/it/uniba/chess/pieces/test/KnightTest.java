package it.uniba.chess.pieces.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.chess.Board;
import it.uniba.chess.Game;
import it.uniba.chess.InputValidator;
import it.uniba.chess.Square;
import it.uniba.chess.pieces.Knight;
import it.uniba.chess.pieces.Piece;
import it.uniba.chess.pieces.Queen;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParseFiles;

public class KnightTest {
	@Test
	@DisplayName("Check white knight movement")
	public void knightWhiteMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.WHITE);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('b'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Cb3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black knight movement")
	public void knightBlackMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.BLACK);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('b'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Cb3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check white knight movement with file")
	public void knightWhiteMovementWithFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.WHITE);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('b'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Ccb3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black knight movement with file")
	public void knightBlackMovementWithFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.BLACK);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('b'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Ccb3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	@Test
	@DisplayName("Check white knight movement with rank")
	public void knightWhiteMovementWithOneRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.WHITE);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('b'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("C5b3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black knight movement with rank")
	public void knightBlackMovementWithOneRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.BLACK);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('b'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("C5b3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check white knight movement with one file")
	public void knightWhiteMovementWithOneFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.WHITE);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('a'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Cca4");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black knight movement with one file")
	public void knightBlackMovementWithOneFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.BLACK);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('a'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Cca4");
		assertEquals(expectedBoard, Game.getBoard());
		
	
	}
	@Test
	@DisplayName("Check white knight movement with one rank")
	public void knightWhiteMovementWithRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.WHITE);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('a'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("C5a4");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black knight movement with one rank")
	public void knightBlackMovementWithRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.BLACK);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('a'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("C5a4");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check white Knight illegal move")
	public void knightWhiteIllegalMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();

		Knight movedKnight = new Knight(ChessColor.WHITE);
		movedKnight.setHasMoved();
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Knight(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('f'), new Knight(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("C5e3"));
	}

	@Test
	@DisplayName("Check black Knight illegal move")
	public void knightBlackIllegalMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();

		Knight movedKnight = new Knight(ChessColor.BLACK);
		movedKnight.setHasMoved();
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Knight(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('f'), new Knight(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("C5e3"));
	}
	
	@Test
	@DisplayName("Check knight (generic) capture")
	public void pawnBlackCaptureNormalOKTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();
        LinkedList<Square> expectedPosition = new LinkedList<>();
        
        Knight capturingKnight = new Knight(ChessColor.WHITE);
        capturingKnight.setHasMoved();
        Queen capturableQueen = new Queen(ChessColor.BLACK);
        capturableQueen.setHasMoved();

        ArrayList<Piece> expectedCaptures = new ArrayList<Piece>();
        expectedCaptures.add(capturableQueen);
        
        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), capturingKnight));
        startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('c'), capturableQueen));
        
        expectedPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('c'), capturingKnight));
        
        Board expectedBoard = new Board(expectedPosition);
        Game.testGame(startingPosition);
        
        InputValidator.parseCommand("Cxc4");

        assertAll("Board states and captures must be the same", 
        	() -> {
        			assertEquals(expectedBoard, Game.getBoard());
        			assertEquals(expectedCaptures, Game.getCapturesList());
        		}
        	);
	}
}
