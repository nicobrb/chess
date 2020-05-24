package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

/**
 * Rappresenta ed istanzia un re di un determinato colore con il rispettivo codice unicode
 *
 * «Entity»
 */

public class King extends Piece {

	public King(final ChessColor colorPiece) {
		super();
		this.setColor(colorPiece);
		if (colorPiece == ChessColor.WHITE) {
			this.setUnicode("\u2654");
		} else {
			this.setUnicode("\u265a");
		}
	}

}
