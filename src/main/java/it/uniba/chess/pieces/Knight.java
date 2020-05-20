package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

/**
 * Rappresenta ed istanzia un cavallo di un determinato colore con il rispettivo codice unicode
 *
 * <<Entity>>
 */
public class Knight extends Piece {

	public Knight(final ChessColor colorPiece) {
		super();
		this.setColor(colorPiece);
		if (colorPiece == ChessColor.WHITE) {
			this.setUnicode("\u2658");
		} else {
			this.setUnicode("\u265e");
		}
	}
}
