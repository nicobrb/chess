package it.uniba.chess.pieces;

import it.uniba.chess.utils.ChessColor;

public class Bishop extends Piece{
	
	public Bishop(ChessColor color) {
		if(color == ChessColor.WHITE) {
			unicode = '\u2657';
		}
		else{
			unicode = '\u265d';
		}
			this.c = color;
	}
}