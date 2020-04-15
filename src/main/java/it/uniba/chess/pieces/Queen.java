package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

public class Queen extends Piece{
	
	public Queen(ChessColor color) {
		if(color == ChessColor.WHITE) {
			unicode = '\u2655';
		}
		else{
			unicode = '\u265b';
		}
			this.c = color;
	}
}