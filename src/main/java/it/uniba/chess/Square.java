package it.uniba.chess;

import it.uniba.chess.pieces.*;

/**
 * Definisce lo stato di una singola casa nella scacchiera. In particolare:
 * 	- Indica se è occupato o meno e, in caso positivo, da quale pezzo;
 *	- Contiene le proprie coordinate all'interno della scacchiera.
 *
 * <<Entity>>
 */
public class Square {
		
	private Piece piece;
	private boolean occupied;
	private final int x, y; //this will never change after initialization
	
	public Square(int x, int y, Piece p){
		this.x = x;
		this.y = y;
		this.piece = p;
		this.occupied = true;
	}
	
	public Square(int row, int col) {
		this.x = row;
		this.y = col;
		this.piece = null;
		this.occupied = false;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece p) {
		this.piece = p;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public void setOccupied(boolean b) {
		this.occupied = b;
		if(!b)
			this.setPiece(null);
	}
}