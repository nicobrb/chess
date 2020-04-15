package it.uniba.chess;

import java.util.ArrayList;

import it.uniba.chess.pieces.*;
import it.uniba.chess.utils.*;



public class Game{
	
	private static Board board;
	public static int movescounter;
	public static String lastmove;
	public static ChessColor turn;
	public static GameStatus status;
	
	
	public static ArrayList<Piece> captures =  new ArrayList<Piece>(30); //can't capture kings
	
	//each semi-move adds to both this list respectively
	public static ArrayList<Square> startingsquares = new ArrayList<Square>();
	public static ArrayList<Square> destinationsquares = new ArrayList<Square>();
	
	//each semi-move will be added by the parser to this list for ease of printing
	public static ArrayList<String> printableMovesList = new ArrayList<String>(30); //can't capture kings
	
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
	
	public static void nextTurn() {
		if(turn == ChessColor.WHITE) {
			turn = ChessColor.BLACK;
		} else {
			turn = ChessColor.WHITE;
		}
	}
}