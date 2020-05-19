package it.uniba.chess;

import java.util.ArrayList;
import java.util.LinkedList;

import it.uniba.chess.pieces.Piece;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.GameStatus;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParseFiles;

/**
 * Mantiene lo stato della partita corrente.
 *
 * <<Control>>
 */

public final class Game {

	private static Board board;
	private static ChessColor turn;
	private static GameStatus status;
	private static final int MAX_CAPTURES = 32;
	private static final int KING_SIZE = 2;
	private static final int INITIAL_MOVE_SIZE = 30;
	private static final int CHESSBOARD_EDGE = 7;


	private static ArrayList<Piece> captures = new ArrayList<Piece>(MAX_CAPTURES); //can't capture kings


	//each semi-move adds to both this list respectively
	private static ArrayList<Square> moveStartingSquaresList = new ArrayList<Square>();
	private static ArrayList<Square> moveDestinationSquaresList = new ArrayList<Square>();

	private static ArrayList<Square> kingSquares = new ArrayList<Square>(KING_SIZE);

	//each semi-move will be added by the parser to this list for ease of printing
	private static ArrayList<String> printableMovesList
	= new ArrayList<String>(INITIAL_MOVE_SIZE); //can't capture kings

	public static void testGame(final LinkedList<Square> chessPosition) throws IllegalMoveException {
		//initialization function to be called on "play"

		board = new Board(chessPosition);

		captures.clear();
		moveStartingSquaresList.clear();
		moveDestinationSquaresList.clear();
		printableMovesList.clear();
		kingSquares.add(Game.getBoard().getSquare(0, ParseFiles.getFileIntFromChar('e')));
		kingSquares.add(Game.getBoard().getSquare(CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('e')));

		status =  GameStatus.ACTIVE;
		turn = ChessColor.WHITE;
	}

	public static void startGame() {
		//initialization function to be called on "play"

		board = new Board();

		captures.clear();
		moveStartingSquaresList.clear();
		moveDestinationSquaresList.clear();
		printableMovesList.clear();
		kingSquares.add(Game.getBoard().getSquare(0, ParseFiles.getFileIntFromChar('e')));
		kingSquares.add(Game.getBoard().getSquare(CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('e')));

		status = GameStatus.ACTIVE;
		turn = ChessColor.WHITE;
	}

	public static void capturedMaterial() {
		//Note that material is the list of pieces captured by the opposite colour

		System.out.print("Materiale del bianco: ");
		for (int i = 0; i < captures.size(); i++) {
			if (captures.get(i).getColor() == ChessColor.BLACK) {
				System.out.print(captures.get(i).getUnicode());
			}
		}
		System.out.print("\nMateriale del nero: ");
		for (int i = 0; i < captures.size(); i++) {
			if (captures.get(i).getColor() == ChessColor.WHITE) {
				System.out.print(captures.get(i).getUnicode());
			}
		}
		System.out.println();
	}

	public static void printListOfMoves() {
		/* we created move_counter to control the number of the actual move counter. WARNING: this is not
		 * a SEMIMOVES counter (which are the actual number of moves made (e4 is a semimove, 1.e4 e5 is a MOVE))
		 */
		int moveCounter = 0;

		for (int i = 0; i < printableMovesList.size(); i++) {
			if (i % 2 == 0) {
				System.out.print("\n");
				moveCounter++;
				System.out.print(moveCounter + ". ");
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

	public static GameStatus getGameStatus() {
		return status;
	}

	public static void setStatus(final GameStatus newstatus) {
		status = newstatus;
	}

	public static Board getBoard() {
		return board;
	}

	public static ChessColor getTurn() {
		return turn;
	}

	public static void nextTurn() {
		if (turn == ChessColor.WHITE) {
			turn = ChessColor.BLACK;
		} else {
			turn = ChessColor.WHITE;
		}
	}

	public static ChessColor getEnemyTurn() {
		if (Game.turn == ChessColor.WHITE) {
			return ChessColor.BLACK;
		} else {
			return ChessColor.WHITE;
		}
	}

	public static Square getKingPosition(final ChessColor wantedColor) {
		if (wantedColor == ChessColor.WHITE) {
			return kingSquares.get(0);
		} else {
			return kingSquares.get(1);
		}
	}

	public static void setKingPosition(final ChessColor wantedColor, final Square destinationSquare) {
		if (wantedColor == ChessColor.WHITE) {
			kingSquares.set(0, destinationSquare);
		} else {
			kingSquares.set(1, destinationSquare);
		}
	}

	public static ArrayList<Piece> getCapturesList() {
		return captures;
	}

	public static void addNewStartingSquare(final Square lastSquare) {
		moveStartingSquaresList.add(lastSquare);
	}

	public static void addNewDestinationSquare(final Square lastSquare) {
		moveDestinationSquaresList.add(lastSquare);
	}

	public static Square getLatestStartingSquare() {
		return moveStartingSquaresList.get(moveStartingSquaresList.size() - 1);
	}

	public static Square getLatestDestinationSquare() {
		return moveDestinationSquaresList.get(moveDestinationSquaresList.size() - 1);
	}

	public static void addPrintableMove(final String lastMove) {
		printableMovesList.add(lastMove);
	}
}
