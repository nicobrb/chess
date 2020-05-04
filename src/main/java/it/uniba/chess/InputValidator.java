package it.uniba.chess;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.uniba.chess.pieces.*;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.GameStatus;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParserColumns;

/**
 * Dato un input, lo elabora e determina se è una mossa di gioco o un'opzione del menù;
 * In caso di mossa, ne controlla la validità secondo le regole degli scacchi e della notazione algebrica abbreviata.
 *
 * <<Control>>
 */
public class InputValidator {
	
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
			if(Game.getStatus() != GameStatus.ACTIVE) {
				Game.startGame();	
			} else {
				Scanner input2 = new Scanner(System.in);
				System.out.println("Sei sicuro di voler ricominciare il gioco? S/[N]");
				String confirmation = input2.nextLine().toLowerCase();
				if(confirmation.equals("s")) {
					Game.startGame();
				}
			}
					
			return;
		case "0-0":
			//fallthrough
		case "O-O":
			//arrocco corto
		case "0-0-0":
			//fallthrough
		case "O-O-O":
			//arrocco lungo
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
				//^([a-h]?)(x?)([a-h][1-8])((e[.]p[.])?)$
				//n
				
				//String RegExp_of_pawn_moves= "^([a-h]?)(x?)([a-h][1-8])$";
				String RegExp_of_pawn_moves = "^(C|A|T|D|R|)([a-h]?|[1-8]?)(x?)([a-h][1-8])((e[.]p[.])?)$";
				
				//followed this tutorial http://tutorials.jenkov.com/java-regex/matcher.html#group-method
				//the pattern is the actual regexp, the matcher expects the keyboard command as input
				
				Pattern regExpPattern = Pattern.compile(RegExp_of_pawn_moves);
				
				Matcher regExpMatcher = regExpPattern.matcher(command);
				
				//if the command is a grammatically correct Pawn move
				if(regExpMatcher.matches()) {
					String pieceType_string = regExpMatcher.group(1);
					String startingRowOrColumn_string = regExpMatcher.group(2);
					String startingRow_string= getStartingRow(startingRowOrColumn_string);
					String startingColumn_string = getStartingColumn(startingRowOrColumn_string);
					String isCapture_string = regExpMatcher.group(3);
					String destination_string = regExpMatcher.group(4);
					String isEnpassant_string = regExpMatcher.group(5);
					
					try {
						//this matches the whole string to be saved into the moves list if it's not an Illegal Move
						String lastmove = regExpMatcher.group(0);
						//we will surely have destination square, we just need to convert the algebraic notation to matrix coordinates
						Square destination_square = getDestinationSquare(destination_string);
						
						//we must always have a starting square too
						Square starting_square;
						
						switch(pieceType_string) {
						case "C":
							if(isEnpassant_string.isEmpty()) {
								int startRow =  startingRow_string.isEmpty() ? -1 : (Integer.parseInt(startingRow_string) - 1); //from 0 to 7
								int startColumn = startingColumn_string.isEmpty() ? -1 :ParserColumns.getColumnIntegerFromChar(startingColumn_string.charAt(0)); //from 0 to 7
								starting_square = checkFlower(destination_square, startRow, startColumn, Knight.class, Game.turn);
								Move.knightMoveOrCapture(starting_square,destination_square, !isCapture_string.isEmpty());
								Game.printableMovesList.add(lastmove);
							} else {
								throw new IllegalMoveException();
							}
							break;
						case "A":
						case "T":
						case "D":
						case "R":
						case "":
							if(startingRowOrColumn_string.isEmpty() && isCapture_string.isEmpty() && isEnpassant_string.isEmpty()){
								//this is a pawn move
								starting_square = getPawnStartingSquare_Move(destination_square);
								Move.pawnMove(starting_square, destination_square); 
								Game.printableMovesList.add(lastmove);
								
								
								} else if (startingRow_string.isEmpty() && !startingColumn_string.isEmpty() && !isCapture_string.isEmpty()) {
								//this is a pawn capture (either simple or en-passant)
								//starting_square = getPawnStartingSquare_Capture(startingColumn_string, destination_square);
								int startColumn = startingColumn_string.isEmpty() ? -1 :ParserColumns.getColumnIntegerFromChar(startingColumn_string.charAt(0));

								starting_square = checkPawn_Capture(destination_square, startColumn, Pawn.class, Game.turn);
								Move.pawnCapture(starting_square, destination_square, !isEnpassant_string.isEmpty()); // <-------- only way is to call it with startingY != -1
								Game.printableMovesList.add(lastmove);
							 } else {
								throw new IllegalMoveException();
							}
							break;
						} //end inner switch
						Game.nextTurn();
						return;
					} catch (IllegalMoveException illegalmove) {
						throw illegalmove;
					}
				}
				//end algebraic move default case;			
			}	
			//if nothing did return until now, it's an invalid command (not exclusively an Illegal move)
			System.out.println("Comando non riconosciuto. Scrivi help per la lista dei comandi.");
			//throw new IllegalMoveException();
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
			throw new IllegalMoveException();
			
		}catch(IllegalMoveException illegalmove) {
			/*
			 * we don't need to create another exception, just reuse what we already have
			 * most of the times this catch block will happen because we go check for an out-of-bounds-move
			 * example: white turn a2 or a1. White pawns can NEVER move there and getSquare() method will go check
			 * for a pawn in a0, which doesn't exist and will produce this exception
			 *
			*/
			throw illegalmove;
		}
	}
	
	private static Square checkPawn_Capture(Square destination_square, int startingY, Class<? extends Piece> piecetype, ChessColor wantedColor) throws IllegalMoveException{
		ArrayList<Square> squares_to_check = new ArrayList<Square>();
		if(startingY != -1){
			if(Math.abs(destination_square.getY() - startingY) == 1){
				//we have a true pawn capture
				if(wantedColor == ChessColor.WHITE) {
					offsetMovement(destination_square, -1, startingY - destination_square.getY(), piecetype, wantedColor, squares_to_check);
				}				
				else
					offsetMovement(destination_square, +1, startingY - destination_square.getY(), piecetype, wantedColor, squares_to_check);
			}	
		}else{
			//we are using it to check if king is in check
			if(wantedColor == ChessColor.WHITE){
				offsetMovement(destination_square, -1, +1, piecetype, wantedColor, squares_to_check);
				offsetMovement(destination_square, -1, -1, piecetype, wantedColor, squares_to_check);
			} else {
				offsetMovement(destination_square, +1, +1, piecetype, wantedColor, squares_to_check);
				offsetMovement(destination_square, +1, -1, piecetype, wantedColor, squares_to_check);
			}
		}


		if(squares_to_check.size() > 1) {
			throw new IllegalMoveException();
		} else if(squares_to_check.size() == 0) {
			return null;
		} else {
			return squares_to_check.get(0);
		}		
	}
	
	private static String getStartingRow(String startRowOrColumn ) {
		if(startRowOrColumn.matches("[1-8]")) {
			return startRowOrColumn;
		}
		return "";
	}
	
	private static String getStartingColumn(String startRowOrColumn ) {
		if(startRowOrColumn.matches("[a-h]")) {
			return startRowOrColumn;
		}
		return "";
	}
	
	private static void offsetMovement(Square destination_square, int x_offset, int y_offset, Class<? extends Piece> piecetype, ChessColor wantedColor, ArrayList<Square> squares_to_check) throws IllegalMoveException{
		
		
		if( (destination_square.getX() + x_offset) >=0 && (destination_square.getX() + x_offset) < 8 
			&& (destination_square.getY() + y_offset) >=0 && (destination_square.getY() + y_offset) < 8){

			Square temp_square = Game.getBoard().getSquare(destination_square.getX() + x_offset, destination_square.getY() + y_offset);
			if(temp_square.isOccupied()){
				if(temp_square.getPiece().getClass() == piecetype && temp_square.getPiece().getColor() == wantedColor){
					squares_to_check.add(temp_square);
				}
			}
		}
	}
	
	public static Square checkFlower(Square destination_square, int startX, int startY, Class<? extends Piece> piecetype, ChessColor wantedColor) throws IllegalMoveException{
		ArrayList<Square> squares_to_check = new ArrayList<Square>();
		
		if(startX != -1) {
			switch(Math.abs(startX-destination_square.getX())) { 
				case 1:
					
					offsetMovement(destination_square, startX-destination_square.getX(), +2, piecetype, wantedColor, squares_to_check);
					offsetMovement(destination_square, startX-destination_square.getX(), -2, piecetype, wantedColor, squares_to_check);
					if(squares_to_check.size() != 1) {
						throw new IllegalMoveException();
					}
					else {
						return squares_to_check.get(0);
					}
				case 2:
					
					offsetMovement(destination_square, startX-destination_square.getX(), +1, piecetype, wantedColor, squares_to_check);
					offsetMovement(destination_square, startX-destination_square.getX(), -1, piecetype, wantedColor, squares_to_check);
					if(squares_to_check.size() != 1) {
						throw new IllegalMoveException();
					}
					else {
						return squares_to_check.get(0);
					}
			}
		}
		if(startY != -1) {
			switch(Math.abs(destination_square.getY() - startY)) { 
				case 1:
					
					offsetMovement(destination_square, +2, /*!!*/ startY - destination_square.getY(), piecetype, wantedColor, squares_to_check);
					offsetMovement(destination_square, -2, startY - destination_square.getY(), piecetype, wantedColor, squares_to_check);
					if(squares_to_check.size() != 1) {
						throw new IllegalMoveException();
					}
					else {
						return squares_to_check.get(0);
					}
				case 2:
					
					offsetMovement(destination_square, +1, startY - destination_square.getY(), piecetype, wantedColor, squares_to_check);
					offsetMovement(destination_square, -1, startY - destination_square.getY(), piecetype, wantedColor, squares_to_check);
					if(squares_to_check.size() != 1) {
						throw new IllegalMoveException();
					}
					else {
						return squares_to_check.get(0);
					}
			}
		}
		
		offsetMovement(destination_square, +2, +1, piecetype, wantedColor, squares_to_check);
		offsetMovement(destination_square, +2, -1, piecetype, wantedColor, squares_to_check);
		offsetMovement(destination_square, +1, +2, piecetype, wantedColor, squares_to_check);
		offsetMovement(destination_square, +1, -2, piecetype, wantedColor, squares_to_check);
		offsetMovement(destination_square, -1, +2, piecetype, wantedColor, squares_to_check);
		offsetMovement(destination_square, -1, -2, piecetype, wantedColor, squares_to_check);
		offsetMovement(destination_square, -2, +1, piecetype, wantedColor, squares_to_check);
		offsetMovement(destination_square, -2, -1, piecetype, wantedColor, squares_to_check);

		if(squares_to_check.size() > 1) {
			throw new IllegalMoveException();
		}
		else if(squares_to_check.size() == 0) {
			return null;
		}
		else {
			return squares_to_check.get(0);
		}
	}
}