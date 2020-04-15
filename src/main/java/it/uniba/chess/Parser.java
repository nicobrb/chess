package it.uniba.chess;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.uniba.chess.pieces.Pawn;
import it.uniba.chess.pieces.Piece;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.GameStatus;
import it.uniba.chess.utils.ParserColumns;

public class Parser {
	
	public static void parseCommand(String command) throws IllegalMoveException {
		switch(command) {
		case "board":
			if(Game.getStatus() == GameStatus.ACTIVE)
				Game.getBoard().print();
			else
				System.out.println("Gioco non ancora avviato.");
			return;
			
		case "quit":
			return;
			
		case "help":
			Game.printHelp();
			return;
			
		case "captures":
		Game.capturedMaterial();
			return;
			
		case "moves":
			Game.printListOfMoves();
			return;
			
		case "play":
		Game.startGame();
			return;
			 
		default:
			if(Game.status == GameStatus.ACTIVE) {
				/*full escape sequence 
				 * 
				 *"^(C|A|T|D|R|)([a-h]?)([1-8]?)(x?)([a-h][1-8])(=?[CATD]?)(\+?)(\#?)$" 
				 *
				 *to be implemented
				*/
				
				//we will now reduce it to only allow Pawn moves,captures and checks
				
				/*
				 * https://www.ibm.com/support/pages/invalid-escape-sequence-regular-expression
				 * 
				 *In java the pattern for "check" and "mate" (the + and # sign) generates an invalid escape error because
				 * it tries to escape this symbols with the " \ " character much like printf(). We apply the solution given in the link above and we get the following
				*/
				
				String RegExp_of_pawn_moves= "^([a-h]?)(x?)([a-h][1-8])$";
				
				//followed this tutorial http://tutorials.jenkov.com/java-regex/matcher.html#group-method
				//the pattern is the actual regexp, the matcher expects the keyboard command as input
				
				Pattern regExpPattern = Pattern.compile(RegExp_of_pawn_moves);
				
				Matcher regExpMatcher = regExpPattern.matcher(command);
				
				//if the command is a grammatically correct Pawn move
				if(regExpMatcher.matches()) {
					try {
						//this matches the whole string to be saved into the moves list if it's not an Illegal Move
						String lastmove = regExpMatcher.group(0);
						//we will surely have destination square, we just need to convert the algebraic notation to matrix coordinates
						Square destination_square = getDestinationSquare(regExpMatcher.group(3));
						
						//we must always have a starting square too
						Square starting_square;
						
						if((regExpMatcher.group(1).isEmpty()) && regExpMatcher.group(2).isEmpty()){
							//this is a pawn move
							starting_square = getPawnStartingSquare_Move(destination_square);
							Move.pawnMove(starting_square, destination_square);
						Game.printableMovesList.add(lastmove);
							
						} else if ( !regExpMatcher.group(1).isEmpty() && !regExpMatcher.group(2).isEmpty()) {
							//this is a pawn capture (either simple or en-passant)
							starting_square = getPawnStartingSquare_Capture(regExpMatcher.group(1), destination_square);
							Move.pawnCapture(starting_square, destination_square);
							Game.printableMovesList.add(lastmove);
						} else {
							throw new IllegalMoveException();
						}
						
						Game.nextTurn();
						return;
	
					} catch (IllegalMoveException illegalmove) {
						throw illegalmove;
					}
				}
				//end default case;
			
			}
			
			//if nothing did return until now, it's an invalid command (not exclusively an Illegal move)
			
			throw new IllegalMoveException();
		}
	}
	
	private static Square getDestinationSquare(String regExpGroup_destination) throws IllegalMoveException {
		try {
			int destination_square_column = ParserColumns.getColumnIntegerFromChar(regExpGroup_destination.charAt(0));
			int destination_square_row = Character.getNumericValue(regExpGroup_destination.charAt(1)) - 1;
			return Game.getBoard().getSquare(destination_square_row, destination_square_column);
		} catch (IllegalMoveException illegalmove) {
			throw illegalmove;
		}
	}
	
	/* We look for a plausible pawn to moved in the previous two rows and the same column;
	 * There can be:
	 * 	A pawn just before the destination square: we move it;
	 * 	A pawn with a white square before its destination: we check if it's the pawn's first move, otherwise we don't move it;
	 *	A pawn with another piece in front of it before its destination: we don't ever move; 
	 */
	private static Square getPawnStartingSquare_Move(Square destination_square) throws IllegalMoveException{
		try {
			ArrayList<Square> squares_to_check = new ArrayList<Square>();
			Piece piece_to_check;
			
			//we dinamically populate a list of squares to check adding the first and the second
			//square before the destination square of our Pawn
			
			if(Game.turn == ChessColor.WHITE) {
				squares_to_check.add(Game.getBoard().getSquare(destination_square.getX()-1, destination_square.getY()));
				squares_to_check.add(Game.getBoard().getSquare(destination_square.getX()-2, destination_square.getY()));
			} else {
				squares_to_check.add(Game.getBoard().getSquare(destination_square.getX()+1, destination_square.getY()));
				squares_to_check.add(Game.getBoard().getSquare(destination_square.getX()+2, destination_square.getY()));
			}
			
			if(squares_to_check.get(0).isOccupied()) {
				piece_to_check = squares_to_check.get(0).getPiece();
				
				//if the square before destination is occupied by EXACTLY a pawn
				if( (piece_to_check.getClass() == Pawn.class) && (piece_to_check.getColor() == Game.turn) )			
					return squares_to_check.get(0);
				
			} else {					
				//first square was empty, let's check for a pawn a starting position
				if(squares_to_check.get(1).isOccupied()) {
					piece_to_check = squares_to_check.get(1).getPiece();
					if( (piece_to_check.getClass() == Pawn.class) && (piece_to_check.getColor() == Game.turn)) 
						return squares_to_check.get(1);
				}
				
			}
			
			//if the method hasn't returned by now, it must be an illegal move
			//System.out.println("error 6");
			throw new IllegalMoveException();
			
		}catch(IllegalMoveException illegalmove) {
			/*
			 * we don't need to create another exception, just reuse what we already have
			 * most of the times this catch block will happen because we go check for an out-of-bounds-move
			 * example: white turn a2 or a1. White pawns can NEVER move there and getSquare() method will go check
			 * for a pawn in a0, which doesn't exist and will produce this exception
			 *
			*/
			//System.out.println("error 7");
			throw illegalmove;
		}
	}
	
	private static Square getPawnStartingSquare_Capture(String starting_column, Square destination_square) throws IllegalMoveException{
		try {
			Square square_to_check;
			Piece piece_to_check;
			
			if(Game.turn == ChessColor.WHITE) {
				square_to_check = Game.getBoard().getSquare(destination_square.getX()-1, ParserColumns.getColumnIntegerFromChar(starting_column.charAt(0)));
			} else {
				square_to_check = Game.getBoard().getSquare(destination_square.getX()+1, ParserColumns.getColumnIntegerFromChar(starting_column.charAt(0)));
			}
			
			if(square_to_check.isOccupied()) {
				piece_to_check = square_to_check.getPiece();
				if( (piece_to_check.getClass() == Pawn.class) && (piece_to_check.getColor() == Game.turn) ) {
					return square_to_check;
				}
			}
			
			//otherwise
			throw new IllegalMoveException();
			
		}catch(IllegalMoveException illegalmove) {
			throw illegalmove;
		}
	}
}
