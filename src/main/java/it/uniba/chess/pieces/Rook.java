package it.uniba.chess.pieces;		

import it.uniba.chess.utils.ChessColor;

/**
 * Rappresenta ed istanzia una torre di un determinato colore con il rispettivo codice unicode
 *
 * <<Entity>>
 */
public class Rook extends Piece{
	
	
	public Rook(ChessColor colorPiece) {
		if(colorPiece == ChessColor.WHITE) {
			unicode = '\u2656';
		}
		else{
			unicode = '\u265c';
		}
			this.colorPiece = colorPiece;
			this.hasMoved = false;
	}



}