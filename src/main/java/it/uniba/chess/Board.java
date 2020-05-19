package it.uniba.chess;

import java.util.LinkedList;

import it.uniba.chess.pieces.Bishop;
import it.uniba.chess.pieces.King;
import it.uniba.chess.pieces.Knight;
import it.uniba.chess.pieces.Pawn;
import it.uniba.chess.pieces.Queen;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.ParseFiles;
/**
 * Definisce ed istanzia una matrice 8x8 di oggetti di tipo Square, che rappresenterà la nostra scacchiera
 *
 * <<Entity>>
 */
public final class Board {
	private Square[][] chessboard;
	public static final int CHESSBOARD_DIMENSION = 8;
	private static final int STARTING_BLANK_RANK = 2;
	private static final int FINAL_BLANK_RANK = 6;
	public static final int CB_EDGE = 7;
	private static final int WHITE_PAWN_RANK = 1;
	private static final int BLACK_PAWN_RANK = 6;


	public Board() {

        this.chessboard = new Square[CHESSBOARD_DIMENSION][CHESSBOARD_DIMENSION];
        initPieces(ChessColor.WHITE);
        initPieces(ChessColor.BLACK);

        //init null chessboard squares
        for (int i = STARTING_BLANK_RANK; i < FINAL_BLANK_RANK; ++i) {
        	for (int j = 0; j < CHESSBOARD_DIMENSION; j++) {
                chessboard[i][j] = new Square(i, j);
            }
        }
    }

	public Board(final LinkedList<Square> chessPosition) {
		this.chessboard = new Square[CHESSBOARD_DIMENSION][CHESSBOARD_DIMENSION];

		for (int i = 0; i < CHESSBOARD_DIMENSION; ++i) {
			for (int j = 0; j < CHESSBOARD_DIMENSION; j++) {
				chessboard[i][j] = new Square(i, j);
			}
		}

		for (Square sq : chessPosition) {
			this.chessboard[sq.getX()][sq.getY()] = sq;
		}

		int file = Integer.parseInt("0");
		int rank = ParseFiles.getFileIntFromChar('e');

		chessboard[file][rank] = new Square(file, rank, new King(ChessColor.WHITE));

		file = Integer.parseInt("7");
		chessboard[file][rank] = new Square(file, rank, new King(ChessColor.BLACK));

	}

	public Square getSquare(final int x, final int y) {

		return (chessboard[x][y]);
	}

	//first square in local matrix is h1, white rook

	private void initPieces(final ChessColor pieceColor) {
		int valueRow;
		int pawnRow;

		if (pieceColor == ChessColor.WHITE) {
			valueRow = 0;
			pawnRow = WHITE_PAWN_RANK;
		} else {
			valueRow = CB_EDGE;
			pawnRow = BLACK_PAWN_RANK;
		}

		//ROOK
		this.chessboard[valueRow][ParseFiles.getFileIntFromChar('h')]
		= new Square(valueRow, ParseFiles.getFileIntFromChar('h'), new Rook(pieceColor));
		this.chessboard[valueRow][ParseFiles.getFileIntFromChar('a')]
		= new Square(valueRow, ParseFiles.getFileIntFromChar('a'), new Rook(pieceColor));
		//KNIGHTS
		this.chessboard[valueRow][ParseFiles.getFileIntFromChar('g')]
		= new Square(valueRow, ParseFiles.getFileIntFromChar('g'), new Knight(pieceColor));
		this.chessboard[valueRow][ParseFiles.getFileIntFromChar('b')]
		= new Square(valueRow, ParseFiles.getFileIntFromChar('b'), new Knight(pieceColor));

		//BISHOPS
		this.chessboard[valueRow][ParseFiles.getFileIntFromChar('f')]
		= new Square(valueRow, ParseFiles.getFileIntFromChar('f'), new Bishop(pieceColor));
		this.chessboard[valueRow][ParseFiles.getFileIntFromChar('c')]
		= new Square(valueRow, ParseFiles.getFileIntFromChar('c'), new Bishop(pieceColor));

		//QUEEN
		this.chessboard[valueRow][ParseFiles.getFileIntFromChar('d')]
		= new Square(valueRow, ParseFiles.getFileIntFromChar('d'), new Queen(pieceColor));

		//KING
		this.chessboard[valueRow][ParseFiles.getFileIntFromChar('e')]
		= new Square(valueRow, ParseFiles.getFileIntFromChar('e'), new King(pieceColor));


		//PAWNS
		for (int i = 0; i < CHESSBOARD_DIMENSION; i++) {
			this.chessboard[pawnRow][i] = new Square(pawnRow, i, new Pawn(pieceColor));
		}
	}

	public void print() {
		System.out.print("   a   b   c   d   e   f   g   h \n");
		System.out.println("  --------------------------------");
		for (int i = CB_EDGE; i >= 0; --i) {
			System.out.print((i + 1) + " | ");
			for (int j = CB_EDGE; j >= 0; --j) {
				if (chessboard[i][j].isOccupied()) {
					System.out.print(chessboard[i][j].getPiece().getUnicode() + " | ");
				} else {
					System.out.print("  | ");
				}
			}
			System.out.print(i + 1);
			System.out.print("\n");
			System.out.println("  --------------------------------");
		}
		System.out.print("   a   b   c   d   e   f   g   h \n");
	}

	   @Override
	    public boolean equals(final Object o) {

	    	boolean isEqual = true;

	        // If the object is compared with itself then return true
	        if (o == this) {
	            return true;
	        }

	        /* Check if o is an instance of Complex or not
	          "null instanceof [type]" also returns false */
	        if (!(o instanceof Board)) {
	            return false;
	        }

	        // typecast o to Complex so that we can compare data members
	        Board comparedBoard = (Board) o;

	        // Compare the data members and return accordingly
	        for (int i = 0; i < CHESSBOARD_DIMENSION && isEqual; ++i) {
	        	for (int j = 0; j < CHESSBOARD_DIMENSION && isEqual; ++j) {
	        		if (this.chessboard[i][j].isOccupied() != comparedBoard.chessboard[i][j].isOccupied()) {
	        			isEqual = false;
	        		} else {
	        			if (this.chessboard[i][j].isOccupied()) {
	        				if (!this.chessboard[i][j].getPiece()
	        				.equals(comparedBoard.chessboard[i][j].getPiece())) {
	        					isEqual = false;
	        				}
	        			}
	        		}
	        	}
	        }

	        return isEqual;
	    }

	   @Override
	   public int hashCode() {
		   return super.hashCode();
	   }
}

