package it.uniba.main;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import java.util.Scanner;

import it.uniba.chess.Game;
import it.uniba.chess.InputValidator;
import it.uniba.chess.utils.GameStatus;
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
		System.out.print("Scrivi help per la lista dei comandi, play per iniziare una nuova partita."
		+ " Qualunque notazione algebrica prima di play non sarà accettata\n");
		Scanner sc = new Scanner(System.in, "UTF-8");
		System.setOut(new PrintStream(System.out, false, "UTF-8"));
		menu(sc);
		sc.close();
	}

	private static void menu(final Scanner sc) {
		String command = "";
		while (!command.equals("quit") && !command.equals("play")) {
			command = sc.nextLine();
			try {
				InputValidator.parseCommand(command.trim());
			} catch (IllegalMoveException ex) {
				System.out.print(ex.getMessage() + "\n");
			}
		}
		//IMPORTANT
		if (command.equals("quit")) {
			quitConfirmation(sc);
		} else if (command.equals("play")) {
			playGame(sc);
		}
		return;
	}

	private static void quitConfirmation(final Scanner sc) {
		System.out.print("Sei sicuro di voler uscire? S/N (default: N)\n");
		String confirm = sc.nextLine().toLowerCase().trim();

		if (confirm.equals("s")) {
			return;
		} else {
			System.out.print("Continuo la partita:\n");
			menu(sc);
		}
	}

	private static void playGame(final Scanner sc) {
		if (Game.getGameStatus() != GameStatus.ACTIVE) {
			Game.startGame();
		} else {
			System.out.print("Sei sicuro di voler ricominciare il gioco? S/[N]\n");
			String confirmation = sc.nextLine().toLowerCase().trim();
			if (confirmation.equals("s")) {
				Game.startGame();
			}
		}
		menu(sc);
		return;
	}
}
