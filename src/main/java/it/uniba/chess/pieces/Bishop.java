package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

/**
 * Rappresenta ed istanzia un alfiere di un determinato colore con il rispettivo codice unicode
 *
 * <<Entity>>
 */
public class Bishop extends Piece{
	
	public Bishop(ChessColor colorPiece) {
		if(colorPiece == ChessColor.WHITE) {
			unicode = '\u2657';
		}
		else{
			unicode = '\u265d';
		}
			this.colorPiece = colorPiece;
	}
}