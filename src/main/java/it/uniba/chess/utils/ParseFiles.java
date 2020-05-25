package it.uniba.chess.utils;

/**
 * Associa al carattere di colonna nella notazione algebrica il rispettivo indice
 * nella matrice Game.board.
 * 
 * «NO-ECB»
 */
public enum ParseFiles {
	h, g, f, e, d, c, b, a;

	//we will check that this method only receives in input chars defining a chess column;
	public static int getFileIntFromChar(final char file) {

		switch (file) {
		case 'h':
			return h.ordinal();
		case 'g':
			return g.ordinal();
		case 'f':
			return f.ordinal();
		case 'e':
			return e.ordinal();
		case 'd':
			return d.ordinal();
		case 'c':
			return c.ordinal();
		case 'b':
			return b.ordinal();
		case 'a':
			return a.ordinal();
		default:
		}

		//necessary to make the code compile
		return -1;
	}
}
