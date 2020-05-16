package it.uniba.chess.pieces;

import it.uniba.chess.utils.*;

/**
 * Rappresenta ed istanzia un pedone di un determinato colore con il rispettivo codice unicode
 * 
 * <<Entity>>
 */
public class Pawn extends Piece {

	
	public Pawn(ChessColor colorPiece) {
		this.colorPiece = colorPiece;
		this.hasMoved = false;
		if (colorPiece == ChessColor.WHITE) {
			unicode = '\u2659';
		} else {
			unicode = '\u265F';
		}
	}
	
}
