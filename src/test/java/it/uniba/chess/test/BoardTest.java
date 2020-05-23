package it.uniba.chess.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.chess.Board;
import it.uniba.chess.Game;
import it.uniba.chess.Square;
import it.uniba.chess.pieces.Bishop;
import it.uniba.chess.pieces.Knight;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.ParseFiles;

public class BoardTest {

	@Test
	@DisplayName("Check if a board is equal to itself")
	public void sameReferenceTest() {
		Game.startGame();
		assertTrue(Game.getBoard().equals(Game.getBoard()));
	}
	
	@Test
	@DisplayName("Check comparison with different classes")
	public void differentClassTest() {
		Game.startGame();
		assertFalse(Game.getBoard().equals(Game.getCapturesList()));
	}
	
	@Test
	@DisplayName("Check comparison with a different position (edge of rank)")
	public void differentPositionRankEdgeTest() {
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), new Knight(ChessColor.WHITE)));
		Board expectedBoard = new Board(expectedPosition);
		Board startingBoard = new Board(startingPosition);
		
		
		assertFalse(expectedBoard.equals(startingBoard));
	}
	
	@Test
	@DisplayName("Check comparison with a different position (middle of rank)")
	public void differentPositionGenericRankTest() {
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('b'), new Knight(ChessColor.WHITE)));
		Board expectedBoard = new Board(expectedPosition);
		Board startingBoard = new Board(startingPosition);
		
		
		assertFalse(expectedBoard.equals(startingBoard));
	}
	
	@Test
	@DisplayName("Check comparison with a different position (different piece)")
	public void differentPositionDifferentPieceTest() {
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('b'), new Bishop(ChessColor.WHITE)));
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('b'), new Knight(ChessColor.WHITE)));
		Board expectedBoard = new Board(expectedPosition);
		Board startingBoard = new Board(startingPosition);
		
		
		assertFalse(expectedBoard.equals(startingBoard));
	}
	
	@Test
	@DisplayName("Check hashCode comparison")
	public void hashCodeTest() {
		//if two objects are equal, then their hashcode MUST be the same
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		Board expectedBoard = new Board(expectedPosition);
		Board startingBoard = new Board(startingPosition);
		
		assertTrue(expectedBoard.hashCode() == startingBoard.hashCode());
	}
}