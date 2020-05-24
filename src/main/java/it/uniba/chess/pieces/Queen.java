package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

/**
 * Rappresenta ed istanzia una regina di un determinato colore con il rispettivo codice unicode
 *
 * «Entity»
 */
public class Queen extends Piece {

	public Queen(final ChessColor colorPiece) {
		super();
		this.setColor(colorPiece);
		if (colorPiece == ChessColor.WHITE) {
			this.setUnicode("\u2655");
		} else {
			this.setUnicode("\u265b");
		}
	}
}

