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
	
}
