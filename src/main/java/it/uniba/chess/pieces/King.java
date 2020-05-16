package it.uniba.chess.pieces;		

import it.uniba.chess.utils.ChessColor;

/**
 * Rappresenta ed istanzia un re di un determinato colore con il rispettivo codice unicode
 *
 * <<Entity>>
 */

public class King extends Piece{

	private final int kingStartingColumn = 3;
	//private final int king


	public King(ChessColor colorPiece) {
		if(colorPiece == ChessColor.WHITE) {
			unicode = '\u2654';
		}
		else{
			unicode = '\u265a';
		}
		this.colorPiece = colorPiece;
		this.hasMoved = false;
	}


	public int getKingStartingColumn() {
		return kingStartingColumn;
	}
}
