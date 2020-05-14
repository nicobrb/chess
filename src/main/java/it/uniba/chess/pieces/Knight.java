package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

/**
 * Rappresenta ed istanzia un cavallo di un determinato colore con il rispettivo codice unicode
 *
 * <<Entity>>
 */
public class Knight extends Piece{
	
	public Knight(ChessColor colorPiece) {
		if(colorPiece == ChessColor.WHITE) {
			unicode = '\u2658';
		} else{
			unicode = '\u265e';
		}
			this.colorPiece = colorPiece;
	}
}