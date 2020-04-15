package it.uniba.chess;

import it.uniba.chess.utils.*;



public class Game{
	
	private static Board board;
	public static int movescounter;
	public static String lastmove;
	public static ChessColor turn;
	public static GameStatus status;

	public static void startGame() {
		//initialization function to be called on "play"
		
		board = new Board();
		status =  GameStatus.ACTIVE;
		turn = ChessColor.WHITE;
		movescounter = 0;
	}
	
	public void movesCounterIncrement() {
		++movescounter;
	}
	
	public int getMovesCounter() {
		return movescounter;
	}
	
	public void printBoard() {
		board.print();
	}
	
	public static GameStatus getStatus() {
		return status;
	}
	
	public static void setStatus(GameStatus newstatus) {
		status = newstatus;
	}
	
	public static Board getBoard() {
		return board;
	}
}