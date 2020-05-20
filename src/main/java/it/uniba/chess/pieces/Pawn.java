package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

/**
 * Rappresenta ed istanzia un pedone di un determinato colore con il rispettivo codice unicode
 * 
 * <<Entity>>
 */
public class Pawn extends Piece {

	public Pawn(final ChessColor colorPiece) {
		super();
		this.setColor(colorPiece);
		if (colorPiece == ChessColor.WHITE) {
			this.setUnicode("\u2659");
		} else {
			this.setUnicode("\u265f");
		}
	}

}
