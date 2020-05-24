package it.uniba.chess.pieces.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.chess.Game;
import it.uniba.chess.pieces.Piece;
import it.uniba.chess.pieces.Queen;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.ParseFiles;

public class PieceTest {	
	@Test
	@DisplayName("Check if a piece is equal to itself")
	public void pieceSameReferenceTest() {
		Game.startGame();
		Piece tmppiece = Game.getBoard().getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('g')).getPiece();
		assertTrue(tmppiece.equals(tmppiece));
	}
	
	@Test
	@DisplayName("Check comparison with different class")
	public void pieceDifferentClassTest() {
		Game.startGame();
		Piece tmppiece = Game.getBoard().getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('g')).getPiece();
		assertFalse(tmppiece.equals(Game.getBoard()));
	}
	
	@Test
	@DisplayName("Check hashCode comparison")
	public void pieceHashCodeTest() {
		//if two objects are equal, then their hashcode MUST be the same
		Rook rookOne = new Rook(ChessColor.WHITE);
		Rook rookTwo = new Rook(ChessColor.WHITE);
		
		assertTrue(rookOne.hashCode() == rookTwo.hashCode());
	}
	
	@Test
	@DisplayName("Check that a Rook is not a Queen")
	public void pieceDifferentUnicodeTest() {
		//if two objects are equal, then their hashcode MUST be the same
		Rook rook = new Rook(ChessColor.WHITE);
		Queen queen = new Queen(ChessColor.WHITE);
		
		assertFalse(rook.equals(queen));
	}
	
	@Test
	@DisplayName("Check that a white Rook is not a black Rook")
	public void pieceDifferentColorTest() {
		//if two objects are equal, then their hashcode MUST be the same
		Rook rook = new Rook(ChessColor.WHITE);
		Rook differentColorRook = new Rook(ChessColor.BLACK);
		
		assertFalse(rook.equals(differentColorRook));
	}
	
	@Test
	@DisplayName("Check that a moved Rook is not equal to an umoved one")
	public void pieceDifferentMovedTest() {
		//if two objects are equal, then their hashcode MUST be the same
		Rook movedrook = new Rook(ChessColor.WHITE);
		movedrook.setHasMoved();
		Rook unmovedRook = new Rook(ChessColor.BLACK);
		
		assertFalse(movedrook.equals(unmovedRook));
	}

}
