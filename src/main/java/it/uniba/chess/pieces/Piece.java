package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

/**
 * Definisce le caratteristiche comuni a tutti i pezzi
 *
 * <<Entity>>
 */
public abstract class Piece {
	protected ChessColor colorPiece;
	protected char unicode; //unicode of the piece
	protected boolean hasMoved;
	
	public ChessColor getColor() {
		return this.colorPiece;
	}
	
	public void setColor(ChessColor colorPiece) {
		this.colorPiece  = colorPiece;
	}
	
	public char getUnicode() {
		return this.unicode;
	}
	
	public char setUnicode() {
		return this.unicode;
	}
	
	public void setHasMoved() {
		this.hasMoved = true;
	}
	public boolean getHasMoved() {
		return hasMoved;
	}
	
    @Override
    public boolean equals(Object o) {
    	
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Piece)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Piece comparedPiece = (Piece) o; 
          
        // Compare the data members and return accordingly  
        if(this.unicode == comparedPiece.unicode
        && this.colorPiece == comparedPiece.colorPiece
        && this.hasMoved == comparedPiece.hasMoved) {
        	return true;
        }
        
        return false;
    } 
	
}
