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
	private static ChessColor turn;
	private static GameStatus status;
	
	
	private static ArrayList<Piece> captures =  new ArrayList<Piece>(32); //can't capture kings
	
	
	//each semi-move adds to both this list respectively
	private static ArrayList<Square> moveStartingSquaresList = new ArrayList<Square>();
	private static ArrayList<Square> moveDestinationSquaresList = new ArrayList<Square>();
	
	private static ArrayList<Square> kingSquares = new ArrayList<Square>(2);
	
	//each semi-move will be added by the parser to this list for ease of printing
	private static ArrayList<String> printableMovesList = new ArrayList<String>(30); //can't capture kings
	
	public static void startGame() throws IllegalMoveException{
		//initialization function to be called on "play"

		board = new Board();
		
		captures.clear();
		moveStartingSquaresList.clear();
		moveDestinationSquaresList.clear();
		printableMovesList.clear();
		kingSquares.add(Game.getBoard().getSquare(0, 3));
		kingSquares.add(Game.getBoard().getSquare(7, 3));	
		
		status =  GameStatus.ACTIVE;
		turn = ChessColor.WHITE;
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
	
	public static ChessColor getTurn() {
		return turn;
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
	
	public static Square getKingPosition(ChessColor wantedColor) {
		if(wantedColor == ChessColor.WHITE) {
			return kingSquares.get(0);
		} else {
			return kingSquares.get(1);
		}
	}
	
	public static void setKingPosition(ChessColor wantedColor, Square destinationSquare) {
		if(wantedColor == ChessColor.WHITE) {
			kingSquares.set(0, destinationSquare);
		} else {
			kingSquares.set(1, destinationSquare);
		}
	}
	
	public static ArrayList<Piece> getCapturesList(){
		return captures;
	}
	
	public static void addNewStartingSquare(Square lastSquare) {
		moveStartingSquaresList.add(lastSquare);
	}
	
	public static void addNewDestinationSquare(Square lastSquare) {
		moveDestinationSquaresList.add(lastSquare);
	}
	
	public static Square getLatestStartingSquare() {
		return moveStartingSquaresList.get(moveStartingSquaresList.size()-1);
	}
	
	public static Square getLatestDestinationSquare() {
		return moveDestinationSquaresList.get(moveDestinationSquaresList.size()-1);
	}
	
	public static void addPrintableMove(String lastMove) {
		printableMovesList.add(lastMove);
	}
}