package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

public class Knight extends Piece{
	
	public Knight(ChessColor color) {
		if(color == ChessColor.WHITE) {
			unicode = '\u2658';
		}
		else{
			unicode = '\u265e';
		}
			this.c = color;
	}
}