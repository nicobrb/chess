package it.uniba.main;


import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import java.util.Scanner;

import it.uniba.chess.InputValidator;
import it.uniba.chess.utils.IllegalMoveException;


/**
 * The main class for the project. It must be customized to meet the project
 * assignment specifications.
 * 
 * <<Boundary>>
 * 
 * <b>DO NOT RENAME</b>
 */
public final class AppMain {

	/**
	 * Private constructor. Change if needed.
	 */
	private AppMain() {

	}

	/**
	 * 	 * This is the main entry of the application.
	 *
	 * @param args The command-line arguments.
	 */
	public static void main(final String[] args) throws UnsupportedEncodingException {
		System.out.println("Scrivi help per la lista dei comandi, play per iniziare una nuova partita. Qualunque notazione algebrica prima di play non sarà accettata");
		Scanner sc = new Scanner(System.in);
		System.setOut(new PrintStream(System.out, false, "UTF-8"));
		menu(sc);

	}
	
	private static void menu(Scanner sc) {
		String command;
		do {
			command = sc.nextLine();
			try {
			InputValidator.parseCommand(command.trim());
			} catch(IllegalMoveException ex) {
				System.out.println(ex.getMessage());
			}
			
		} while (!command.equals("quit"));
		System.out.println("Sei sicuro di voler uscire? S/N (default: N)");
		String confirmation = sc.nextLine().toLowerCase();
		if (confirmation.equals("s")) {
			sc.close();
			System.exit(0);
		}
		System.out.println("Continuo la partita:");
		menu(sc);
		return;		
	}

}
