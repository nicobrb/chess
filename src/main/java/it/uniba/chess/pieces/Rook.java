package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

/**
 * Rappresenta ed istanzia una torre di un determinato colore con il rispettivo codice unicode
 *
 * <<Entity>>
 */
public class Rook extends Piece {

	public Rook(final ChessColor colorPiece) {
		super();
		this.setColor(colorPiece);
		if (colorPiece == ChessColor.WHITE) {
			this.setUnicode("\u2656");
		} else {
			this.setUnicode("\u265c");
		}
	}

}

