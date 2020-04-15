package it.uniba.chess;

public class IllegalMoveException extends Exception {	

	public IllegalMoveException() {
		super("Mossa illegale");
	}
}
