package it.uniba.chess.pieces;

import it.uniba.chess.utils.*;

/**
 * Rappresenta ed istanzia un pedone di un determinato colore con il rispettivo codice unicode
 * 
 * <<Entity>>
 */
public class Pawn extends Piece{
	boolean hasMoved;
	
	public Pawn(ChessColor colorPiece){
		this.colorPiece= colorPiece;
		this.hasMoved = false;
		if (colorPiece == ChessColor.WHITE) {
			unicode = '\u2659';
		} else {
			unicode = '\u265F';
		}
	}
	
	public boolean move() {
		return false;
	}
	
	public void setHasMoved() {
		this.hasMoved=true;
	}
	
	public boolean getHasMoved() {
		return this.hasMoved;
	}
	
}
