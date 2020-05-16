package it.uniba.chess;

import it.uniba.chess.pieces.*;
import it.uniba.chess.utils.*;

/**
 * Definisce ed istanzia una matrice 8x8 di oggetti di tipo Square, che rappresenterà la nostra scacchiera
 *
 * <<Entity>>
 */
public class Board {
	private Square[][] chessboard;
	private final int chessboardDimension = 8;
	private final int startingBlankRank = 2;
	private final int finalBlankRank = 6;
	private final int chessboardEdge = 7;
	private final int whitePawnRank = 1;
	private final int blackPawnRank = 6;

	public Board() {

		this.chessboard = new Square[chessboardDimension][chessboardDimension];
		initPieces(ChessColor.WHITE);
		initPieces(ChessColor.BLACK);

		//init null chessboard squares
		for (int i = startingBlankRank; i < finalBlankRank; ++i) {
			for (int j = 0; j < chessboardDimension; j++) {
				chessboard[i][j] = new Square(i, j);
			}
		}
	}

	public Square getSquare(int x, int y) throws IllegalMoveException {
		if (x < 0 || x > chessboardEdge
				|| y < ParserColumns.getFileIntFromChar('h')
				|| y > ParserColumns.getFileIntFromChar('a')) {
			throw new IllegalMoveException();
		}

		return (chessboard[x][y]);
	}

	//first square in local matrix is h1, white rook

	private void initPieces(final ChessColor pieceColor) {
		int valueRow;
		int pawnRow;

		if (pieceColor == ChessColor.WHITE) {
			valueRow = 0;
			pawnRow = whitePawnRank;
		} else {
			valueRow = chessboardEdge;
			pawnRow = blackPawnRank;
		}

		//ROOK
		this.chessboard[valueRow][ParserColumns.getFileIntFromChar('h')] = new Square(valueRow, ParserColumns.getFileIntFromChar('h'), new Rook(pieceColor));
		this.chessboard[valueRow][ParserColumns.getFileIntFromChar('a')] = new Square(valueRow, ParserColumns.getFileIntFromChar('a'), new Rook(pieceColor));
		//KNIGHTS
		this.chessboard[valueRow][ParserColumns.getFileIntFromChar('g')] = new Square(valueRow, ParserColumns.getFileIntFromChar('g'), new Knight(pieceColor));
		this.chessboard[valueRow][ParserColumns.getFileIntFromChar('b')] = new Square(valueRow, ParserColumns.getFileIntFromChar('b'), new Knight(pieceColor));

		//BISHOPS
		this.chessboard[valueRow][ParserColumns.getFileIntFromChar('f')] = new Square(valueRow, ParserColumns.getFileIntFromChar('f'), new Bishop(pieceColor));
		this.chessboard[valueRow][ParserColumns.getFileIntFromChar('c')] = new Square(valueRow, ParserColumns.getFileIntFromChar('c'), new Bishop(pieceColor));

		//QUEEN
		this.chessboard[valueRow][ParserColumns.getFileIntFromChar('d')] = new Square(valueRow, ParserColumns.getFileIntFromChar('d'), new Queen(pieceColor));

		//KING
		this.chessboard[valueRow][ParserColumns.getFileIntFromChar('e')] = new Square(valueRow, ParserColumns.getFileIntFromChar('e'), new King(pieceColor));


		//PAWNS
		for (int i = 0; i < chessboardDimension; i++) {
			this.chessboard[pawnRow][i] = new Square(pawnRow, i, new Pawn(pieceColor));
		}
	}

	public void print() {
		System.out.print("   a   b   c   d   e   f   g   h \n");
		System.out.println("  --------------------------------");
		for (int i = chessboardEdge; i >= 0; --i) {
			System.out.print((i + 1) + " | ");
			for (int j = chessboardEdge; j >= 0; --j) {
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
}
