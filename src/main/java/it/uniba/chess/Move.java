package it.uniba.chess;

import it.uniba.chess.pieces.King;
import it.uniba.chess.pieces.Pawn;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.IllegalMoveException;

public class Move {
	
	public static void pawnMove(Square initialsquare, Square finalsquare) throws IllegalMoveException {
		
		if(finalsquare.isOccupied()) {
			throw new IllegalMoveException();
		}
		
		if(Math.abs( finalsquare.getX() - initialsquare.getX() ) == 2){
			//explicitly cast to Pawn to check for moved
			if( ! ((Pawn) initialsquare.getPiece()).getHasMoved() ){
				finalsquare.setPiece(initialsquare.getPiece());
				
				//remember to set moved after moving the piece
				((Pawn) finalsquare.getPiece()).setHasMoved();
				initialsquare.setPiece(null);
				finalsquare.setOccupied(true);
				initialsquare.setOccupied(false);
				
				//if this does not throw IllegalMove
				Game.startingsquares.add(initialsquare);
				Game.destinationsquares.add(finalsquare);
				return;
			}
		} else if (Math.abs( finalsquare.getX() -  initialsquare.getX() ) == 1) {
			finalsquare.setPiece( initialsquare.getPiece());
				//remember to set moved after moving the piece
				( (Pawn) finalsquare.getPiece()).setHasMoved();
				initialsquare.setPiece(null);
				finalsquare.setOccupied(true);
				initialsquare.setOccupied(false);
				
				//if this does not throw IllegalMove
				Game.startingsquares.add(initialsquare);
				Game.destinationsquares.add(finalsquare);
				
				return;
		}
		
		//in any other case
		throw new IllegalMoveException();	

	}
	
	public static void pawnCapture(Square initialsquare, Square finalsquare, boolean enPassantFlag) throws IllegalMoveException {
		if(initialsquare == null) 
			throw new IllegalMoveException();
		
		try{
			//if we can capture en-passant we are done
			if(captureEnPassant(initialsquare, finalsquare)) {
				return;
			} else {
				if(enPassantFlag) {
					throw new IllegalMoveException();
				}
			}
		} catch (IllegalMoveException ex) {
			throw ex;
		}

		//otherwise we check for a normal capture
		if(initialsquare.getPiece().getClass() == Pawn.class) {
			if(Math.abs(finalsquare.getY()-initialsquare.getY()) == 1) { 
				if(finalsquare.getX() > initialsquare.getX() && initialsquare.getPiece().getColor() == ChessColor.WHITE) {	
					if(finalsquare.getPiece() != null && finalsquare.getPiece().getColor() != initialsquare.getPiece().getColor())//&&!initialsquare.piece.pinned must be added in the future
					{
						Game.captures.add(finalsquare.getPiece());
						finalsquare.setPiece(initialsquare.getPiece());
						finalsquare.setOccupied(true);
						
						initialsquare.setOccupied(false);
						//initialsquare.setPiece(null);
						
						//if this does not throw IllegalMove
						Game.startingsquares.add(initialsquare);
						Game.destinationsquares.add(finalsquare);
						return;
					} //else {there is no piece inside the final square or there is but is not capturable due to a pin or due to being the same color as the pawn in initial square
				}else if (finalsquare.getX() < initialsquare.getX() && initialsquare.getPiece().getColor() == ChessColor.BLACK) {
					if(finalsquare.getPiece() != null && finalsquare.getPiece().getColor() != initialsquare.getPiece().getColor()) {//&&!initialsquare.piece.pinned must be added in the future			
						
						Game.captures.add(finalsquare.getPiece());
						finalsquare.setPiece(initialsquare.getPiece());
						finalsquare.setOccupied(true);
						
						initialsquare.setOccupied(false);
						//initialsquare.setPiece(null);
						
						//if this does not throw IllegalMove
						Game.startingsquares.add(initialsquare);
						Game.destinationsquares.add(finalsquare);
						return;
					}//there is no piece inside the final square or there is but is not capturable due to a pin or due to being the same color as the pawn in initial square
				} //there is a discrepancy between initial-final square and actual pawn movement
			} //exit the function with break because there is no pawn inside the initial square, thus the capture can't fisically happen
		}
		throw new IllegalMoveException();
	}
	
	private static boolean captureEnPassant(Square initialsquare, Square finalsquare) throws IllegalMoveException{
		if( (initialsquare.getPiece().getClass() == Pawn.class)) {
			if(Game.getBoard().getSquare(initialsquare.getX(), finalsquare.getY()).isOccupied()) {
				if(Game.getBoard().getSquare(initialsquare.getX(), finalsquare.getY()).getPiece().getClass() == Pawn.class && Game.getBoard().getSquare(initialsquare.getX(), finalsquare.getY()).getPiece().getColor() != initialsquare.getPiece().getColor()
					&& isCapturableEnPassant(initialsquare, finalsquare))
				{
					
					Game.captures.add(Game.getBoard().getSquare(initialsquare.getX(), finalsquare.getY()).getPiece());
					
					Game.getBoard().getSquare(initialsquare.getX(), finalsquare.getY()).setOccupied(false);
					//Game.getBoard().getSquare(initialsquare.getX(), finalsquare.getY()).setPiece(null);
					
					finalsquare.setPiece(initialsquare.getPiece());			
					finalsquare.setOccupied(true);
					
					initialsquare.setOccupied(false);
					//initialsquare.setPiece(null);
					
					//if this does not throw IllegalMove
					Game.startingsquares.add(initialsquare);
					Game.destinationsquares.add(finalsquare);
					
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean isCapturableEnPassant(Square initialsquare, Square finalsquare) {
		//if last move was a pawn push
		if(Game.destinationsquares.get(Game.destinationsquares.size()-1).getPiece().getClass() == Pawn.class ) {
			//if the push was exactly a 2-push
			int previous_move_initial_x =  Game.startingsquares.get(Game.startingsquares.size() -1).getX();
			int previous_move_final_x =  Game.destinationsquares.get(Game.destinationsquares.size() -1).getX();
			
			if(Math.abs( previous_move_initial_x - previous_move_final_x ) == 2) {
				//if the capture happens exactly on the pawn that moved during the last turn

				return (Game.destinationsquares.get(Game.destinationsquares.size() -1).getY() == finalsquare.getY()
				&& Game.destinationsquares.get(Game.destinationsquares.size() -1).getX() == initialsquare.getX());
			}
		}
		
		return false;
	}
	
	public static void knightMoveOrCapture(Square startingsquare, Square finalsquare, boolean isCapture) throws IllegalMoveException {
		if(startingsquare == null) 
			throw new IllegalMoveException();
		if(finalsquare.isOccupied()) {
			if(finalsquare.getPiece().getColor() != Game.turn && isCapture) {
				Game.captures.add(finalsquare.getPiece());
				finalsquare.setOccupied(true);
				finalsquare.setPiece(startingsquare.getPiece());
				startingsquare.setOccupied(false);				
				
				Game.startingsquares.add(startingsquare);
				Game.destinationsquares.add(finalsquare);
				//startingsquare.setPiece(NULL);
				return;
			}
		}
		else {
			if(!isCapture) {
				finalsquare.setOccupied(true);
				finalsquare.setPiece(startingsquare.getPiece());
				startingsquare.setOccupied(false);
				//startingsquare.setPiece(NULL);
				
				Game.startingsquares.add(startingsquare);
				Game.destinationsquares.add(finalsquare);
				return;
			}
		}
		throw new IllegalMoveException();
	}
	
	public static void bishopMoveOrCapture(Square startingsquare, Square finalsquare, boolean isCapture) throws IllegalMoveException {
		if(startingsquare == null) 
			throw new IllegalMoveException();
		if(finalsquare.isOccupied()) {
			if(finalsquare.getPiece().getColor() != Game.turn && isCapture ) {
				Game.captures.add(finalsquare.getPiece());
				finalsquare.setOccupied(true);
				finalsquare.setPiece(startingsquare.getPiece());
				startingsquare.setOccupied(false);
				//startingsquare.setPiece(NULL);
				
				Game.startingsquares.add(startingsquare);
				Game.destinationsquares.add(finalsquare);
				return;
			}
		}
		else {
			if(!isCapture) {
				finalsquare.setOccupied(true);
				finalsquare.setPiece(startingsquare.getPiece());
				startingsquare.setOccupied(false);
				//startingsquare.setPiece(NULL);
				
				Game.startingsquares.add(startingsquare);
				Game.destinationsquares.add(finalsquare);
				return;
			}
		}
		throw new IllegalMoveException();
	}
	
	public static void rookMoveOrCapture(Square startingsquare, Square finalsquare, boolean isCapture) throws IllegalMoveException {
		
		if(startingsquare == null) 
			throw new IllegalMoveException();
		if(finalsquare.isOccupied()) {
			if(finalsquare.getPiece().getColor() != Game.turn && isCapture) {
				
				Game.captures.add(finalsquare.getPiece());
				finalsquare.setOccupied(true);
				finalsquare.setPiece(startingsquare.getPiece());
				startingsquare.setOccupied(false);
				( (Rook) finalsquare.getPiece()).setHasMoved();
			
				Game.startingsquares.add(startingsquare);
				Game.destinationsquares.add(finalsquare);
				//startingsquare.setPiece(NULL);
				return;
			}
		}
		else {
			if(!isCapture) {
				finalsquare.setOccupied(true);
				finalsquare.setPiece(startingsquare.getPiece());
				startingsquare.setOccupied(false);
				( (Rook) finalsquare.getPiece()).setHasMoved();
				//startingsquare.setPiece(NULL);
				
				Game.startingsquares.add(startingsquare);
				Game.destinationsquares.add(finalsquare);
				return;
			}
		}
		throw new IllegalMoveException();
	}
	
	public static void queenMoveOrCapture(Square startingsquare, Square finalsquare, boolean isCapture) throws IllegalMoveException {
		if(startingsquare == null) 
			throw new IllegalMoveException();
		if(finalsquare.isOccupied()) {
			if(finalsquare.getPiece().getColor() != Game.turn && isCapture) {
				Game.captures.add(finalsquare.getPiece());
				finalsquare.setOccupied(true);
				finalsquare.setPiece(startingsquare.getPiece());
				startingsquare.setOccupied(false);
				//startingsquare.setPiece(NULL);
				
				Game.startingsquares.add(startingsquare);
				Game.destinationsquares.add(finalsquare);
				return;
			}
		}
		else {
			if(!isCapture) {
				finalsquare.setOccupied(true);
				finalsquare.setPiece(startingsquare.getPiece());
				startingsquare.setOccupied(false);
				//startingsquare.setPiece(NULL);
				
				Game.startingsquares.add(startingsquare);
				Game.destinationsquares.add(finalsquare);
				return;
			}
		}
		throw new IllegalMoveException();
	}
	
public static void kingMoveOrCapture(Square startingsquare, Square finalsquare, boolean isCapture) throws IllegalMoveException {
		
		if(startingsquare == null) 
			throw new IllegalMoveException();
		
		if(finalsquare.isOccupied()) {
			if(finalsquare.getPiece().getColor() != Game.turn && isCapture) {
				
				Game.captures.add(finalsquare.getPiece());
				finalsquare.setOccupied(true);
				finalsquare.setPiece(startingsquare.getPiece());
				startingsquare.setOccupied(false);
				( (King) finalsquare.getPiece()).setHasMoved();
			
				Game.startingsquares.add(startingsquare);
				Game.destinationsquares.add(finalsquare);
				//startingsquare.setPiece(NULL);
				return;
			}
		}
		else {
			if(!isCapture) {
				finalsquare.setOccupied(true);
				finalsquare.setPiece(startingsquare.getPiece());
				startingsquare.setOccupied(false);
				( (King) finalsquare.getPiece()).setHasMoved();
				//startingsquare.setPiece(NULL);
				
				Game.startingsquares.add(startingsquare);
				Game.destinationsquares.add(finalsquare);
				return;
			}
		}
		throw new IllegalMoveException();
	}
	
}
