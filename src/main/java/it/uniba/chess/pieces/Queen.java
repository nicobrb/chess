package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

/**
 * Rappresenta ed istanzia una regina di un determinato colore con il rispettivo codice unicode
 *
 * <<Entity>>
 */
public class Queen extends Piece{
	
	public Queen(ChessColor colorPiece) {
		if(colorPiece == ChessColor.WHITE) {
			unicode = '\u2655';
		}
		else{
			unicode = '\u265b';
		}
			this.colorPiece = colorPiece;
	}
}