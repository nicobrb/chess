package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

/**
 * Definisce le caratteristiche comuni a tutti i pezzi
 *
 * <<Entity>>
 */


public abstract class Piece {

	private ChessColor colorPiece;
	private String unicode; //unicode of the piece
	private boolean hasMoved;

	public Piece() {
		this.colorPiece = null;
		this.hasMoved = false;
	}

	/**
	 * Restituisce il colore del pezzo
	 * 
	 */
	public ChessColor getColor() {
		return this.colorPiece;
	}

	/**
	 * Imposta il colore del pezzo
	 * 
	 */
	public void setColor(final ChessColor newColorPiece) {
		this.colorPiece = newColorPiece;
	}

	/**
	 * Restituisce il carattere Unicode del pezzo
	 * 
	 */
	public String getUnicode() {
		return this.unicode;
	}

	/**
	 * Imposta il carattere Unicode del pezzo
	 * 
	 */
	public void setUnicode(final String newUnicode) {
		this.unicode = newUnicode;
	}

	/**
	 * Imposta il valore del booleano hasMoved per il pezzo
	 * 
	 */
	public void setHasMoved() {
		this.hasMoved = true;
	}

	/**
	 * Restituisce il valore del booleano hasMoved per il pezzo
	 * 
	 */
	public boolean getHasMoved() {
		return hasMoved;
	}

	/**
	 * Override della funzione equals, apposito per il confronto dei pezzi
	 * 
	 */
    @Override
    public boolean equals(final Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Piece)) {
            return false;
        }
        Piece comparedPiece = (Piece) o;

        // Compare the data members and return accordingly
        if (this.unicode.equals(comparedPiece.unicode)
        && this.colorPiece == comparedPiece.colorPiece
        && this.hasMoved == comparedPiece.hasMoved) {
        	return true;
        }
        return false;
    }

	/**
	 * Restituisce il valore hashCode del pezzo
	 * 
	 */
    @Override
    public int hashCode() {
    	return super.hashCode();
    }

}
