package it.uniba.chess.pieces;		

import it.uniba.chess.utils.ChessColor;



public class Rook extends Piece{
	
	private boolean moved;
	
	public Rook(ChessColor color) {
		if(color == ChessColor.WHITE) {
			unicode = '\u2656';
		}
		else{
			unicode = '\u265c';
		}
			this.c = color;
			moved = false;
	}


	public void setMoved() {
		moved = true;
	}
	public boolean getMoved() {
		return moved;
	}
}