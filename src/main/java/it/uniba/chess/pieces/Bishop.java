package it.uniba.chess.pieces;
import it.uniba.chess.utils.ChessColor;

/**
 * Rappresenta ed istanzia un alfiere di un determinato colore con il rispettivo codice unicode
 *
 * «Entity»
 */
public class Bishop extends Piece {

	public Bishop(final ChessColor colorPiece) {

		super();
		this.setColor(colorPiece);
		if (colorPiece == ChessColor.WHITE) {
			this.setUnicode("\u2657");
		} else {
			this.setUnicode("\u265d");
		}
	}
}
