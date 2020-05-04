package it.uniba.chess;

import java.util.ArrayList;

import it.uniba.chess.pieces.*;
import it.uniba.chess.utils.*;

/**
 * Mantiene lo stato della partita corrente.
 *
 * <<Control>>
 */
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
		captures.clear();
		startingsquares.clear();
		destinationsquares.clear();
		printableMovesList.clear();
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
	
	public static void capturedMaterial(){
		//Note that material is the list of pieces captured by the opposite colour
		
		System.out.print("Materiale del bianco: ");
		for(int i=0; i<captures.size(); i++) {
			if(captures.get(i).getColor() == ChessColor.BLACK) {
				System.out.print(captures.get(i).getUnicode());
			}
		}
		System.out.print("\nMateriale del nero: ");
		for(int i=0; i<captures.size(); i++) {
			if(captures.get(i).getColor() == ChessColor.WHITE) {
				System.out.print(captures.get(i).getUnicode());
			}
		}
		System.out.println();
	}



	public static  void printListOfMoves(){
		
		/* we created move_counter to control the number of the actual move counter. WARNING: this is not
		 * a SEMIMOVES counter (which are the actual number of moves made (e4 is a semimove, 1.e4 e5 is a MOVE))
		 */
		
		int move_counter = 0;

		for (int i = 0; i < printableMovesList.size(); i++) {
			if(i%2 == 0) {
				System.out.print("\n");
				move_counter++;
				System.out.print(move_counter + ". ");
			}
			System.out.print(printableMovesList.get(i) + " ");
		}
		
		System.out.println();

	}
	
	public static void printHelp() {
		System.out.print("help: questo menu\n");
		System.out.print("board: mostra la scacchiera alla posizione attuale\n");
		System.out.print("play: inizia una nuova partita (anche a partità già iniziata)\n");
		System.out.print("quit: termina l'applicazione\n");
		System.out.print("captures: mostra il materiale catturato da ogni colore\n");
		System.out.print("moves: mostra la lista delle mosse giocate durante la partita\n");
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
	
	public static ChessColor getEnemyTurn(){
	    return Game.turn == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE; 
	}
}