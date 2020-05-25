package it.uniba.chess.utils;

/**
 *  IllegalMoveException si occupa di lanciare un messaggio di errore ogni volta che
 *  la mossa ricevuta in input dall'utente risulta illegale da parte della classe InputValidator
 *
 * 	«NO-ECB»
 */

public class IllegalMoveException extends Exception {

	/**
	 *  To fix serialization warning
	 */
	private static final long serialVersionUID = -101434881562366505L;

	public IllegalMoveException() {
		super("Mossa illegale");
	}
}
