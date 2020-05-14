package it.uniba.chess;

import it.uniba.chess.pieces.*;
import it.uniba.chess.utils.*;

/**
 * Definisce ed istanzia una matrice 8x8 di oggetti di tipo Square, che rappresenterà la nostra scacchiera
 *
 * <<Entity>>
 */
public class Board{
	private Square[][] chessboard;
	
	public Board(){
		this.chessboard = new Square[8][8];
		initPieces(ChessColor.WHITE);
		initPieces(ChessColor.BLACK);

		//init null chessboard squares
		for(int i = 2; i<6; ++i) {
			for (int j=0; j<8; j++) {
				chessboard[i][j] = new Square(i,j);
			}
		}
		
	}
	
	public Square getSquare(int x, int y) throws IllegalMoveException {
		if(x<0 || x>7 || y<0 || y>7) {
			throw new IllegalMoveException();
		}
		
		return(chessboard[x][y]);
	}

	//first square in local matrix is h1, white rook
	
	private void initPieces(ChessColor pieceColor) {
		int valueRow;
		int pawnRow;
		
		if(pieceColor == ChessColor.WHITE) {
			valueRow = 0;
			pawnRow = 1;
		} else {
			valueRow = 7;
			pawnRow = 6;
		}
		
		//ROOK
		this.chessboard[valueRow][0] = new Square(valueRow, 0, new Rook(pieceColor));
		this.chessboard[valueRow][7] = new Square(valueRow, 7, new Rook(pieceColor));
		//KNIGHTS
		this.chessboard[valueRow][1] = new Square(valueRow, 1, new Knight(pieceColor));
		this.chessboard[valueRow][6] = new Square(valueRow, 6, new Knight(pieceColor));
		
		//BISHOPS
		this.chessboard[valueRow][2] = new Square(valueRow, 2, new Bishop(pieceColor));
		this.chessboard[valueRow][5] = new Square(valueRow, 5, new Bishop(pieceColor));
		
		//QUEEN
		this.chessboard[valueRow][4] = new Square(valueRow, 4, new Queen(pieceColor));
		
		//KING
		this.chessboard[valueRow][3] = new Square(valueRow, 3, new King(pieceColor));
		
		
		//PAWNS
		for (int i=0; i<8; i++) {
			this.chessboard[pawnRow][i] = new Square(pawnRow, i, new Pawn(pieceColor));
		}
		
		
	}
	
	public void print() {
		System.out.print("   a   b   c   d   e   f   g   h \n");
		System.out.println("  --------------------------------");
		for(int i=7; i>=0; --i) {
			System.out.print((i+1) + " | ");
			for (int j=7; j>=0; --j) {
				if(chessboard[i][j].isOccupied()) {
					System.out.print(chessboard[i][j].getPiece().getUnicode() + " | ");
				}else {
					System.out.print("  | ");
				}
			}
			System.out.print(i+1);
			System.out.print("\n");
			System.out.println("  --------------------------------");
		}
		System.out.print("   a   b   c   d   e   f   g   h \n");
	}
	
}