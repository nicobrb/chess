package it.uniba.chess.pieces;

import it.uniba.chess.utils.*;

public class Pawn extends Piece{
	boolean moved;
	
	public Pawn(ChessColor c){
		this.c = c;
		this.moved = false;
		if (c == ChessColor.WHITE) {
			unicode = '\u2659';
		} else {
			unicode = '\u265F';
		}
	}
	
	public boolean move() {
		return false;
	}
	
	public void setMoved() {
		moved=true;
	}
	
	public boolean hasMoved() {
		return moved;
	}
	
}
