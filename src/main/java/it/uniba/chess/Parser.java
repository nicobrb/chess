package it.uniba.chess;


import it.uniba.chess.utils.GameStatus;

public class Parser {
	
	public static void parseCommand(String command) throws IllegalMoveException {
		switch(command) {
		case "board":
			if(Game.getStatus() == GameStatus.ACTIVE)
				Game.getBoard().print();
			else
				System.out.println("Gioco non ancora avviato.");
			return;
			
		case "quit":
			return;
			
		case "help":
			//Game.printHelp();
			return;
			
		case "captures":
			//Game.capturedMaterial();
			return;
			
		case "moves":
			//Game.printListOfMoves();
			return;
			
		case "play":
			Game.startGame();
			return;
			 
		default:
			throw new IllegalMoveException();
		}
	}
}
