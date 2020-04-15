package it.uniba.chess.pieces;		

import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.Board;


public class King extends Piece{
	
	private boolean moved;
	
	public King(ChessColor color) {
		if(color == ChessColor.WHITE) {
			unicode = '\u2654';
		}
		else{
			unicode = '\u265a';
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

// TODO canCastle(Board): a method that verifies if the king can castle (short or long castling).
// Not necessary for the current Sprint.