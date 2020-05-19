package it.uniba.chess.utils;

/**
 *  IllegalMoveException si occupa di lanciare un messaggio di errore ogni volta che
 *  la mossa ricevuta in input dall'utente risulta illegale da parte della classe InputValidator
 */

public class IllegalMoveException extends Exception {

	public IllegalMoveException() {
		super("Mossa illegale");
	}
}
