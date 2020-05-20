package it.uniba.chess;

import it.uniba.chess.pieces.Piece;

/**
 * Definisce lo stato di una singola casa nella scacchiera. In particolare:
 * 	- Indica se è occupato o meno e, in caso positivo, da quale pezzo;
 *	- Contiene le proprie coordinate all'interno della scacchiera.
 *
 * <<Entity>>
 */
public final class Square {

	private Piece piece;
	private boolean occupied;
	private final int x, y; //this will never change after initialization

	public Square(final int newX, final int newY, final Piece p) {
		this.x = newX;
		this.y = newY;
		this.piece = p;
		this.occupied = true;
	}

	public Square(final int rank, final int file) {
		this.x = rank;
		this.y = file;
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

	public void setPiece(final Piece p) {
		this.piece = p;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(final boolean b) {
		this.occupied = b;
		if (!b) {
			this.setPiece(null);
		}
	}
}
