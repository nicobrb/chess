package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

public abstract class Piece {
	ChessColor c;
	char unicode; //unicode of the piece
	
	public ChessColor getColor() {
		return this.c;
	}
	
	public void setColor(ChessColor c) {
		this.c  = c;
	}
	
	public char getUnicode() {
		return this.unicode;
	}
	
	public char setUnicode() {
		return this.unicode;
	}
}
