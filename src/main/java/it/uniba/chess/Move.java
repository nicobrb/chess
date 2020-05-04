package it.uniba.chess;

import it.uniba.chess.pieces.King;
import it.uniba.chess.pieces.Pawn;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.IllegalMoveException;

/**
 * Esegue l'azione di movimento o cattura di ogni pezzo.
 *
 * <<Control>>
 */
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

	public static boolean shortCastle(ChessColor wantedColor) throws IllegalMoveException {
		if(wantedColor == ChessColor.WHITE) {
			Square king_initial_square = Game.getBoard().getSquare(0, 3);
			
			if(king_initial_square.isOccupied() && king_initial_square.getPiece().getClass() == King.class && king_initial_square.getPiece().getColor() == wantedColor) {
				if( ! ((King) king_initial_square.getPiece()).getHasMoved()) {
				//make the king disappear	
				
				king_initial_square.setOccupied(false);
				
				if(InputValidator.canTheKingMoveToThatSquare(king_initial_square)) {
				//there is an unmoved white king and it's not under check
			 		king_initial_square.setOccupied(true);
			 		king_initial_square.setPiece(new King(wantedColor));
			 		
			 		Square rook_initial_square = Game.getBoard().getSquare(0, 0);
			 		
					if(rook_initial_square.isOccupied() && rook_initial_square.getPiece().getClass() == Rook.class 
						&& rook_initial_square.getPiece().getColor() == wantedColor) {
						
						if( ! ((Rook) rook_initial_square.getPiece()).getHasMoved()) {
						//there is an unmoved white rook		
							
							
							if(!Game.getBoard().getSquare(0, 2).isOccupied() && InputValidator.canTheKingMoveToThatSquare(Game.getBoard().getSquare(0, 2))) {
								if(!Game.getBoard().getSquare(0, 1).isOccupied() && InputValidator.canTheKingMoveToThatSquare(Game.getBoard().getSquare(0, 1))){
									
									( (King) king_initial_square.getPiece()).setHasMoved();
									( (Rook) rook_initial_square.getPiece()).setHasMoved();
									
									Game.getBoard().getSquare(0, 2).setOccupied(true);
									Game.getBoard().getSquare(0, 2).setPiece(Game.getBoard().getSquare(0, 0).getPiece());
									Game.getBoard().getSquare(0, 0).setOccupied(false);
									
									//startingsquare.setPiece(NULL);
									
									Game.getBoard().getSquare(0, 1).setOccupied(true);
									Game.getBoard().getSquare(0, 1).setPiece(Game.getBoard().getSquare(0, 3).getPiece());
									Game.getBoard().getSquare(0, 3).setOccupied(false);
									//startingsquare.setPiece(NULL);
									
									Game.startingsquares.add(Game.getBoard().getSquare(0, 3));
									Game.destinationsquares.add(Game.getBoard().getSquare(0, 2));
									
									Game.nextTurn();
									return true;
								}
							}
						}		
					}	
				 	} else {
				 		//make the piece appear again if we can't castle
				 		king_initial_square.setOccupied(true);
				 		king_initial_square.setPiece(new King(wantedColor));
				 	}
				}
			}
		} else {
			Square king_initial_square = Game.getBoard().getSquare(7, 3);
			
			if(king_initial_square.isOccupied() && king_initial_square.getPiece().getClass() == King.class && king_initial_square.getPiece().getColor() == wantedColor) {
				if( ! ((King) king_initial_square.getPiece()).getHasMoved()) {
				//make the king disappear	
				
					king_initial_square.setOccupied(false);
				
				if(InputValidator.canTheKingMoveToThatSquare(king_initial_square)) {
				//there is an unmoved white king and it's not under check
					king_initial_square.setOccupied(true);
					king_initial_square.setPiece(new King(wantedColor));
			 		
			 		Square rook_initial_square = Game.getBoard().getSquare(7, 0);
			 		
					if(rook_initial_square.isOccupied() && rook_initial_square.getPiece().getClass() == Rook.class 
						&& rook_initial_square.getPiece().getColor() == wantedColor) {
						
						if( ! ((Rook) rook_initial_square.getPiece()).getHasMoved()) {
						//there is an unmoved white rook		
							
							
							if(!Game.getBoard().getSquare(7, 2).isOccupied() && InputValidator.canTheKingMoveToThatSquare(Game.getBoard().getSquare(7, 2))) {
								if(!Game.getBoard().getSquare(7, 1).isOccupied() && InputValidator.canTheKingMoveToThatSquare(Game.getBoard().getSquare(7, 1))){
									
									( (King) king_initial_square.getPiece()).setHasMoved();
									( (Rook) rook_initial_square.getPiece()).setHasMoved();
									
									Game.getBoard().getSquare(7, 2).setOccupied(true);
									Game.getBoard().getSquare(7, 2).setPiece(Game.getBoard().getSquare(7, 0).getPiece());
									Game.getBoard().getSquare(7, 0).setOccupied(false);
									//startingsquare.setPiece(NULL);
									
									Game.getBoard().getSquare(7, 1).setOccupied(true);
									Game.getBoard().getSquare(7, 1).setPiece(Game.getBoard().getSquare(7, 3).getPiece());
									Game.getBoard().getSquare(7, 3).setOccupied(false);
									//startingsquare.setPiece(NULL);
									
									Game.startingsquares.add(Game.getBoard().getSquare(7, 3));
									Game.destinationsquares.add(Game.getBoard().getSquare(7, 2));
									
									Game.nextTurn();
									return true;
								}
							}
						}
					}
				}
			}
		}
	}	
	 //can't castle
	return false;
	}
	
	public static boolean longCastle(ChessColor wantedColor) throws IllegalMoveException {
		if(wantedColor == ChessColor.WHITE) {
			Square king_initial_square = Game.getBoard().getSquare(0, 3);
			
			if(king_initial_square.isOccupied() && king_initial_square.getPiece().getClass() == King.class && king_initial_square.getPiece().getColor() == wantedColor) {
				if( ! ((King) king_initial_square.getPiece()).getHasMoved()) {
				//make the king disappear	
				
				king_initial_square.setOccupied(false);
				
				if(InputValidator.canTheKingMoveToThatSquare(king_initial_square)) {
				//there is an unmoved white king and it's not under check
			 		king_initial_square.setOccupied(true);
			 		king_initial_square.setPiece(new King(wantedColor));
			 		
			 		Square rook_initial_square = Game.getBoard().getSquare(0, 7);
			 		
					if(rook_initial_square.isOccupied() && rook_initial_square.getPiece().getClass() == Rook.class 
						&& rook_initial_square.getPiece().getColor() == wantedColor) {
						
						if( ! ((Rook) rook_initial_square.getPiece()).getHasMoved()) {
						//there is an unmoved white rook		
							
							
							if(!Game.getBoard().getSquare(0, 4).isOccupied() && InputValidator.canTheKingMoveToThatSquare(Game.getBoard().getSquare(0, 4))) {
								if(!Game.getBoard().getSquare(0, 5).isOccupied() && InputValidator.canTheKingMoveToThatSquare(Game.getBoard().getSquare(0, 5))){
									if(!Game.getBoard().getSquare(0, 6).isOccupied()) {
										
										( (King) king_initial_square.getPiece()).setHasMoved();
										( (Rook) rook_initial_square.getPiece()).setHasMoved();
										
										Game.getBoard().getSquare(0, 4).setOccupied(true);
										Game.getBoard().getSquare(0, 4).setPiece(rook_initial_square.getPiece());
										rook_initial_square.setOccupied(false);
										
										//startingsquare.setPiece(NULL);
										
										Game.getBoard().getSquare(0, 5).setOccupied(true);
										Game.getBoard().getSquare(0, 5).setPiece(king_initial_square.getPiece());
										king_initial_square.setOccupied(false);
										//startingsquare.setPiece(NULL);
										
										Game.startingsquares.add(Game.getBoard().getSquare(0, 3));
										Game.destinationsquares.add(Game.getBoard().getSquare(0, 5));
										
										Game.nextTurn();
										return true;
									}
								}
							}
						}		
					}	
				 	} else {
				 		//make the piece appear again if we can't castle
				 		king_initial_square.setOccupied(true);
				 		king_initial_square.setPiece(new King(wantedColor));
				 	}
				}
			}
		} else {
			Square king_initial_square = Game.getBoard().getSquare(7, 3);
			
			if(king_initial_square.isOccupied() && king_initial_square.getPiece().getClass() == King.class && king_initial_square.getPiece().getColor() == wantedColor) {
				if( ! ((King) king_initial_square.getPiece()).getHasMoved()) {
				//make the king disappear	
				
				king_initial_square.setOccupied(false);
				
				if(InputValidator.canTheKingMoveToThatSquare(king_initial_square)) {
				//there is an unmoved white king and it's not under check
			 		king_initial_square.setOccupied(true);
			 		king_initial_square.setPiece(new King(wantedColor));
			 		
			 		Square rook_initial_square = Game.getBoard().getSquare(7, 7);
			 		
					if(rook_initial_square.isOccupied() && rook_initial_square.getPiece().getClass() == Rook.class 
						&& rook_initial_square.getPiece().getColor() == wantedColor) {
						
						if( ! ((Rook) rook_initial_square.getPiece()).getHasMoved()) {
						//there is an unmoved white rook		
							
							
							if(!Game.getBoard().getSquare(7, 4).isOccupied() && InputValidator.canTheKingMoveToThatSquare(Game.getBoard().getSquare(7, 4))) {
								if(!Game.getBoard().getSquare(7, 5).isOccupied() && InputValidator.canTheKingMoveToThatSquare(Game.getBoard().getSquare(7, 5))){
									if(!Game.getBoard().getSquare(7, 6).isOccupied()) {
										
										( (King) king_initial_square.getPiece()).setHasMoved();
										( (Rook) rook_initial_square.getPiece()).setHasMoved();
										
										Game.getBoard().getSquare(7, 4).setOccupied(true);
										Game.getBoard().getSquare(7, 4).setPiece(rook_initial_square.getPiece());
										rook_initial_square.setOccupied(false);
										
										//startingsquare.setPiece(NULL);
										
										Game.getBoard().getSquare(7, 5).setOccupied(true);
										Game.getBoard().getSquare(7, 5).setPiece(king_initial_square.getPiece());
										king_initial_square.setOccupied(false);
										//startingsquare.setPiece(NULL);
										
										Game.startingsquares.add(Game.getBoard().getSquare(7, 3));
										Game.destinationsquares.add(Game.getBoard().getSquare(7, 5));
										
										Game.nextTurn();
										return true;
									}
								}
							}
						}		
					}	
				 	} else {
				 		//make the piece appear again if we can't castle
				 		king_initial_square.setOccupied(true);
				 		king_initial_square.setPiece(new King(wantedColor));
				 	}
			}
		}
	}	
	 //can't castle
	return false;
	}
}
