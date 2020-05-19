package it.uniba.chess;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.uniba.chess.pieces.Piece;
import it.uniba.chess.pieces.Pawn;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.pieces.Bishop;
import it.uniba.chess.pieces.Knight;
import it.uniba.chess.pieces.Queen;
import it.uniba.chess.pieces.King;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.GameStatus;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParseFiles;

/**
 * Dato un input, lo elabora e determina se è una mossa di gioco o un'opzione del menù;
 * In caso di mossa, ne controlla la validità secondo le regole degli scacchi e della notazione algebrica abbreviata.
 *
 * <<Control>>
 */
public class InputValidator {

	private static final int FIRST_GROUP = 1;
	private static final int SECOND_GROUP = 2;
	private static final int THIRD_GROUP = 3;
	private static final int FOURTH_GROUP = 4;
	private static final int FIFTH_GROUP = 5;


	public static void parseCommand(final String command) throws IllegalMoveException {
		switch (command) {
		case "board":
			if (Game.getGameStatus() == GameStatus.ACTIVE) {
				Game.getBoard().print();
			} else {
				System.out.println("Gioco non ancora avviato.");
				}
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
			if (Game.getGameStatus() != GameStatus.ACTIVE) {
				Game.startGame();
			} else {
				Scanner input2 = new Scanner(System.in);
				System.out.println("Sei sicuro di voler ricominciare il gioco? S/[N]");
				String confirmation = input2.nextLine().toLowerCase();
				if (confirmation.equals("s")) {
					Game.startGame();
				}
			}
			return;
		case "0-0":
			//fallthrough
		case "O-O":
			if (Game.getGameStatus() == GameStatus.ACTIVE) {
				if (Move.shortCastle(Game.getTurn())) {
					Game.addPrintableMove(command);
					return;
				} else {
					throw new IllegalMoveException();
				}
			} else {
				System.out.println("Gioco non ancora avviato.");
			}
			return;
		case "0-0-0":
			//fallthrough
		case "O-O-O":
			if (Game.getGameStatus() == GameStatus.ACTIVE) {
				if (Move.longCastle(Game.getTurn())) {
					Game.addPrintableMove(command);
					return;
				} else {
					throw new IllegalMoveException();
				}
			} else {
				System.out.println("Gioco non ancora avviato.");
			}
			return;
		default:
			if (Game.getGameStatus() == GameStatus.ACTIVE) {
				/*
				 * In java the pattern for "check" and "mate" (the + and # sign)
				 * generates an invalid escape error because it tries to escape this symbols
				 * with the " \ " character much like printf(). We apply the solution given
				*/
				String regExpOfPawnMoves = "^(C|A|T|D|R|)([a-h]?|[1-8]?)(x?)([a-h][1-8])((e[.]p[.])?)$";
				//the pattern is the actual regexp, the matcher expects the keyboard command as input
				Pattern regExpPattern = Pattern.compile(regExpOfPawnMoves);
				Matcher regExpMatcher = regExpPattern.matcher(command);
				//if the command is a grammatically correct Pawn move
				if (regExpMatcher.matches()) {
					String pieceTypeString = regExpMatcher.group(FIRST_GROUP);
					String startingRowOrColumnString = regExpMatcher.group(SECOND_GROUP);
					String startingRowString = getStartingRow(startingRowOrColumnString);
					String startingColumnString = getStartingColumn(startingRowOrColumnString);
					String isCaptureString = regExpMatcher.group(THIRD_GROUP);
					String destinationString = regExpMatcher.group(FOURTH_GROUP);
					String isEnpassantString = regExpMatcher.group(FIFTH_GROUP);
					try {
						//this matches the whole string to be saved into the moves list
						//(if it's not an Illegal Move)
						String lastmove = regExpMatcher.group(0);
						//we will surely have destination square, we just need to convert 
						//the algebraic notation to matrix coordinates
						Square destinationSquare = getDestinationSquare(destinationString);
						//we must always have a starting square too
						Square startingSquare;
						switch (pieceTypeString) {
						case "C":
							if (isEnpassantString.isEmpty()) {
								int startRow =  startingRowString.isEmpty() ? -1 :
									(Integer.parseInt(startingRowString) - 1);
								int startColumn = startingColumnString.isEmpty() ? -1 :
									ParseFiles.getFileIntFromChar(
										startingColumnString.charAt(0));
								startingSquare = checkFlower(destinationSquare, startRow,
									startColumn, Knight.class, Game.getTurn());
								Move.pieceMoveOrCapture(Knight.class, startingSquare,
									destinationSquare, !isCaptureString.isEmpty());
								Game.addPrintableMove(lastmove);
							} else {
								throw new IllegalMoveException();
							}
							break;
						case "A":
							if (isEnpassantString.isEmpty()) {
							    int startRow = startingRowString.isEmpty() ? -1 :
							    	(Integer.parseInt(startingRowString) - 1);
							    int startColumn = startingColumnString.isEmpty() ? -1 :
							    	ParseFiles.getFileIntFromChar(
							    		startingColumnString.charAt(0));
							    startingSquare = checkCross(destinationSquare, startRow,
							    	startColumn, Bishop.class, Game.getTurn());
							    Move.pieceMoveOrCapture(Bishop.class, startingSquare,
							    	destinationSquare, !isCaptureString.isEmpty());
							    Game.addPrintableMove(lastmove);
							} else {
								throw new IllegalMoveException();
							}
							break;
						case "T":
							if (isEnpassantString.isEmpty()) {
								//if we have an empty row string, we return -1 as control;
								int startRow = startingRowString.isEmpty() ? -1 : (Integer.parseInt(startingRowString) - 1);
								int startColumn = startingColumnString.isEmpty() ? -1 :ParseFiles.getFileIntFromChar(startingColumnString.charAt(0));
								startingSquare = plusMovement(destinationSquare, startRow, startColumn, Rook.class, Game.getTurn());			
								Move.pieceMoveOrCapture(Rook.class, startingSquare,destinationSquare, !isCaptureString.isEmpty());
								Game.addPrintableMove(lastmove);
							} else {
								throw new IllegalMoveException();
							}
							break;
						case "D":
							if (isEnpassantString.isEmpty()) {
							    int startRow = startingRowString.isEmpty() ? -1 : (Integer.parseInt(startingRowString) - 1); //from 0 to 7
							    int startColumn = startingColumnString.isEmpty() ? -1 :ParseFiles.getFileIntFromChar(startingColumnString.charAt(0)); //from 0 to 7
							    startingSquare = queenIntersectionControl(destinationSquare, startRow, startColumn, Queen.class, Game.getTurn());
							    Move.pieceMoveOrCapture(Queen.class, startingSquare,destinationSquare, !isCaptureString.isEmpty());
							    Game.addPrintableMove(lastmove);
							} else {
								throw new IllegalMoveException();
							}
							break;
						case "R":
							if (isEnpassantString.isEmpty() 
							&& startingRowOrColumnString.isEmpty()) {
								if ((lastmove.equals("Rg1") ) || (lastmove.equals("Rg8"))){
								
									if (Move.shortCastle(Game.getTurn())) {
										Game.addPrintableMove(lastmove);
										return;
									}
								} else if ((lastmove.equals("Rc1")) || (lastmove.equals("Rc8")) ) {
								
									if (Move.longCastle(Game.getTurn())) {
										Game.addPrintableMove(lastmove);
										return;
									}
								}
								startingSquare = isThereAKingAroundThisSquare(destinationSquare, Game.getTurn());
								if (isTurnKingNotInCheck(destinationSquare)) {
									Move.pieceMoveOrCapture(King.class, startingSquare,
										destinationSquare, !isCaptureString.isEmpty());
									Game.addPrintableMove(lastmove);
									break;
								}
							}
							throw new IllegalMoveException();
						case "":
							if(startingRowOrColumnString.isEmpty() && isCaptureString.isEmpty() && isEnpassantString.isEmpty()){
								//this is a pawn move
								startingSquare = getPawnStartingSquare_Move(destinationSquare);
								Move.pawnMove(startingSquare, destinationSquare); 
								Game.addPrintableMove(lastmove);
								} else if (startingRowString.isEmpty() && !startingColumnString.isEmpty() && !isCaptureString.isEmpty()) {
								//this is a pawn capture (either simple or en-passant)
								//startingSquare = getPawnStartingSquare_Capture(startingColumnString, destinationSquare);
								int startColumn = startingColumnString.isEmpty() ? -1 :ParseFiles.getFileIntFromChar(startingColumnString.charAt(0));

								startingSquare = checkPawn_Capture(destinationSquare, startColumn, Pawn.class, Game.getTurn());
								Move.pawnCapture(startingSquare, destinationSquare, !isEnpassantString.isEmpty());
								Game.addPrintableMove(lastmove);
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
		}
	}


	private static Square getDestinationSquare(String regExpGroup_destination) {
			int destinationSquare_column = ParseFiles.getFileIntFromChar(regExpGroup_destination.charAt(0));
			int destinationSquare_row = Character.getNumericValue(regExpGroup_destination.charAt(1)) - 1;
			return Game.getBoard().getSquare(destinationSquare_row, destinationSquare_column);
	}

	/* We look for a plausible pawn to moved in the previous two rows and the same column;
	 * There can be:
	 * 	A pawn just before the destination square: we move it;
	 * 	A pawn with a white square before its destination: we check if it's the pawn's first move, otherwise we don't move it;
	 *	A pawn with another piece in front of it before its destination: we don't ever move; 
	 */
	private static Square getPawnStartingSquare_Move(Square destinationSquare) throws IllegalMoveException{
		try {
			ArrayList<Square> squaresToCheck = new ArrayList<Square>();
			Piece piece_to_check;
			
			//we dinamically populate a list of squares to check adding the first and the second
			//square before the destination square of our Pawn
			
			if(Game.getTurn() == ChessColor.WHITE) {
				if(destinationSquare.getX()-1 > 0) {
					squaresToCheck.add(Game.getBoard().getSquare(destinationSquare.getX()-1, destinationSquare.getY()));
				}
				
				if(destinationSquare.getX()-2 > 0) {
					squaresToCheck.add(Game.getBoard().getSquare(destinationSquare.getX()-2, destinationSquare.getY()));
				}	
			} else {
				if(destinationSquare.getX()+1 < Board.CHESSBOARD_DIMENSION) {
					squaresToCheck.add(Game.getBoard().getSquare(destinationSquare.getX()+1, destinationSquare.getY()));
				}
				
				if(destinationSquare.getX()+2 < Board.CHESSBOARD_DIMENSION) {
					squaresToCheck.add(Game.getBoard().getSquare(destinationSquare.getX()+2, destinationSquare.getY()));
				}
			}
			
			if(squaresToCheck.size() > 0 && squaresToCheck.get(0).isOccupied()) {
				piece_to_check = squaresToCheck.get(0).getPiece();
				
				//if the square before destination is occupied by EXACTLY a pawn
				if( (piece_to_check.getClass() == Pawn.class) && (piece_to_check.getColor() == Game.getTurn()) )			
					return squaresToCheck.get(0);
				
			} else {					
				//first square was empty, let's check for a pawn a starting position
				if(squaresToCheck.size() > 1 && squaresToCheck.get(1).isOccupied()) {
					piece_to_check = squaresToCheck.get(1).getPiece();
					if( (piece_to_check.getClass() == Pawn.class) && (piece_to_check.getColor() == Game.getTurn())) 
						return squaresToCheck.get(1);
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
	
	private static Square checkPawn_Capture(Square destinationSquare, int startingY, Class<? extends Piece> piecetype, ChessColor wantedColor) throws IllegalMoveException{
		ArrayList<Square> squaresToCheck = new ArrayList<Square>();
		
		if(startingY != -1){
			if(Math.abs(destinationSquare.getY() - startingY) == 1){
				//we have a true pawn capture
				if(wantedColor == ChessColor.WHITE) {
					offsetMovement(destinationSquare, -1, startingY - destinationSquare.getY(), piecetype, wantedColor, squaresToCheck);
				}				
				else
					offsetMovement(destinationSquare, +1, startingY - destinationSquare.getY(), piecetype, wantedColor, squaresToCheck);
			}	
		}else{
			//we are using it to check if king is in check
			if(wantedColor == ChessColor.WHITE){
				
				offsetMovement(destinationSquare, -1, +1, piecetype, wantedColor, squaresToCheck);
				offsetMovement(destinationSquare, -1, -1, piecetype, wantedColor, squaresToCheck);
			} else {
				
				offsetMovement(destinationSquare, +1, +1, piecetype, wantedColor, squaresToCheck);
				offsetMovement(destinationSquare, +1, -1, piecetype, wantedColor, squaresToCheck);
			}
		}


		if(squaresToCheck.size() > 1) {
			throw new IllegalMoveException();
		} else if(squaresToCheck.size() == 0) {
			return null;
		} else {
			return squaresToCheck.get(0);
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

	private static Square plusMovement(Square finalsquare, int startingX, int startingY, Class<? extends Piece> pieceClassToSearch, ChessColor wantedColor) throws IllegalMoveException {

		ArrayList<Square> squaresToCheck = new ArrayList<Square>();

		if(finalsquare.isOccupied() && finalsquare.getPiece().getColor() == wantedColor && wantedColor == Game.getTurn()) {
			throw new IllegalMoveException();
		}

		if(startingX != -1 && startingX != finalsquare.getX() &&  Game.getBoard().getSquare(startingX, finalsquare.getY()).isOccupied()) {
			if(Game.getBoard().getSquare(startingX, finalsquare.getY()).getPiece().getClass() == pieceClassToSearch &&
			Game.getBoard().getSquare(startingX, finalsquare.getY()).getPiece().getColor() == wantedColor ) {
				if(finalsquare.getX() > startingX) {
					for(int i = finalsquare.getX()-1; i > startingX; --i) {
						if(Game.getBoard().getSquare(i, finalsquare.getY()).isOccupied()) {
							throw new IllegalMoveException();
						}
					}
				}
				else if(finalsquare.getX() < startingX) {
					for(int i = finalsquare.getX()+1; i < startingX; ++i) {
						if(Game.getBoard().getSquare(i, finalsquare.getY()).isOccupied()) {
							throw new IllegalMoveException();
						}
					}
				}
				return Game.getBoard().getSquare(startingX, finalsquare.getY());
			}
			else throw new IllegalMoveException();

		}
		else if (startingX != -1 && startingX == finalsquare.getX()) {
			westCheck(finalsquare, squaresToCheck, pieceClassToSearch, wantedColor);
			eastCheck(finalsquare, squaresToCheck, pieceClassToSearch, wantedColor);

			if (squaresToCheck.size() > 1) {
				throw new IllegalMoveException();
			}
			else if(squaresToCheck.size() == 0)
				return null;

			return squaresToCheck.get(0);
		}
		if(startingY != -1 && startingY != finalsquare.getY() &&  Game.getBoard().getSquare(finalsquare.getX(), startingY).isOccupied()) {

			if (Game.getBoard().getSquare(finalsquare.getX(), startingY).getPiece().getClass() == pieceClassToSearch &&
			Game.getBoard().getSquare(finalsquare.getX(), startingY).getPiece().getColor() == wantedColor ) {
				if(finalsquare.getY() > startingY) {
					for(int i = finalsquare.getY()-1; i > startingY; --i) {
						if (Game.getBoard().getSquare(finalsquare.getX(), i).isOccupied()) {
							throw new IllegalMoveException();
						}
					}
				}
				else if (finalsquare.getY() < startingY) {
					for(int i = finalsquare.getY()+1; i < startingY; ++i) {
						if (Game.getBoard().getSquare(finalsquare.getX(), i).isOccupied()) {
							throw new IllegalMoveException();
						}
					}
				}
				return Game.getBoard().getSquare(finalsquare.getX(), startingY);
			}
			else throw new IllegalMoveException();

		}
		else if (startingY != -1 && startingY == finalsquare.getY()){
			northCheck(finalsquare, squaresToCheck, pieceClassToSearch, wantedColor);
			southCheck(finalsquare, squaresToCheck, pieceClassToSearch, wantedColor);
		
			if (squaresToCheck.size() > 1) {
				throw new IllegalMoveException();
			}
			else if (squaresToCheck.size() == 0)
				return null;
			
			return squaresToCheck.get(0);			
		}		
		
		northCheck(finalsquare, squaresToCheck, pieceClassToSearch, wantedColor);
		southCheck(finalsquare, squaresToCheck, pieceClassToSearch, wantedColor);
		westCheck(finalsquare, squaresToCheck, pieceClassToSearch, wantedColor);
		eastCheck(finalsquare, squaresToCheck, pieceClassToSearch, wantedColor);
	
	  
	      //if we have more than one of this piece-colour combo
		if(squaresToCheck.size() > 1) {
			throw new IllegalMoveException();
		}
		else if(squaresToCheck.size() == 0)
			return null;
		
		return squaresToCheck.get(0);	
	}
	
	private static void diagonalUpperRight(Square finalsquare, ArrayList<Square> checksquares, Class<? extends Piece> piecetype, ChessColor wantedColor) throws IllegalMoveException{
		int i = 1;
		Square temp_square;
		while(finalsquare.getX()+i <Board.CHESSBOARD_DIMENSION && finalsquare.getY()+i < Board.CHESSBOARD_DIMENSION) {
			temp_square = Game.getBoard().getSquare(finalsquare.getX()+i, finalsquare.getY()+i);
			if(temp_square.isOccupied()) {
				if(temp_square.getPiece().getColor() == wantedColor && temp_square.getPiece().getClass() == piecetype) {
					checksquares.add(temp_square);
					return;
				}
				else {
					return;
				}
			}
			++i;
		}
	}
	
	private static void diagonalLowerRight(Square finalsquare, ArrayList<Square> checksquares, Class<? extends Piece> piecetype, ChessColor wantedColor) throws IllegalMoveException{
		int i = 1;
		Square temp_square;
		while(finalsquare.getX()-i >= 0 && finalsquare.getY()+i < Board.CHESSBOARD_DIMENSION) {
			temp_square = Game.getBoard().getSquare(finalsquare.getX()-i, finalsquare.getY()+i);
			if(temp_square.isOccupied()) {
				if(temp_square.getPiece().getColor() == wantedColor && temp_square.getPiece().getClass() == piecetype) {
					checksquares.add(temp_square);
					return;
				}
				else {
					return;
				}
			}
			++i;
		}
	}
	
	private static void diagonalUpperLeft(Square finalsquare, ArrayList<Square> checksquares, Class<? extends Piece> piecetype, ChessColor wantedColor) throws IllegalMoveException{
		int i = 1;
		Square temp_square;
		while(finalsquare.getX()+i < Board.CHESSBOARD_DIMENSION && finalsquare.getY()-i >= 0) {
			temp_square = Game.getBoard().getSquare(finalsquare.getX()+i, finalsquare.getY()-i);
			if(temp_square.isOccupied()) {
				if(temp_square.getPiece().getColor() == wantedColor && temp_square.getPiece().getClass() == piecetype) {
					checksquares.add(temp_square);
					return;
				}
				else {
					return;
				}
			}
			++i;
		}
	}
	
	private static void diagonalLowerLeft(Square finalsquare, ArrayList<Square> checksquares, Class<? extends Piece> piecetype, ChessColor wantedColor) throws IllegalMoveException{
		int i = 1;
		Square temp_square;
		while(finalsquare.getX()-i >= 0 && finalsquare.getY()-i >= 0) {
			temp_square = Game.getBoard().getSquare(finalsquare.getX()-i, finalsquare.getY()-i);
			if(temp_square.isOccupied()) {
				if(temp_square.getPiece().getColor() == wantedColor && temp_square.getPiece().getClass() == piecetype) {
					checksquares.add(temp_square);
					return;
				}
				else {
					return;
				}
			}
			++i;
		}
	}
	
	private static Square checkCross(Square finalsquare, int startingX, int startingY, Class<? extends Piece> piecetype, ChessColor wantedColor) throws IllegalMoveException{

		ArrayList<Square> squaresToCheck = new ArrayList<Square>();
		diagonalUpperRight(finalsquare, squaresToCheck, piecetype, wantedColor);
		if(squaresToCheck.size() == 0){
			diagonalLowerRight(finalsquare, squaresToCheck, piecetype, wantedColor);
			if(squaresToCheck.size() == 0){
				diagonalLowerLeft(finalsquare, squaresToCheck, piecetype, wantedColor);
				if(squaresToCheck.size() == 0){
					diagonalUpperLeft(finalsquare, squaresToCheck, piecetype, wantedColor);
				}
			}
		}
		if(squaresToCheck.size() > 1){
				throw new IllegalMoveException();
		}
		else if(squaresToCheck.size() == 0) {
			return null;
		}
		else {
        		if(startingX != -1) {
        		    if(startingX == squaresToCheck.get(0).getX()) {
        			return squaresToCheck.get(0);
        		    }
        		    else {
        			throw new IllegalMoveException();
        		    }
        		}
        		if(startingY != -1) {
        		    if(startingY == squaresToCheck.get(0).getY()) {
        			return squaresToCheck.get(0);
        		    }
        		    else {
        			throw new IllegalMoveException();
        		    }
        		}
        		return squaresToCheck.get(0);
		}
	}
	
	private static Square queenIntersectionControl(Square finalsquare, int startX, int startY, Class<? extends Piece> pieceClassToSearch, ChessColor wantedColor) throws IllegalMoveException {
		Square plusSquare = plusMovement(finalsquare, startX, startY, pieceClassToSearch, wantedColor);
		Square crossSquare = checkCross(finalsquare, startX, startY, pieceClassToSearch, wantedColor);

		if(plusSquare == null && crossSquare == null)
			return null;
		else if(plusSquare != null && crossSquare != null)
			throw new IllegalMoveException();
		else {
			if(plusSquare != null)
				return plusSquare;
			else return crossSquare;
			
		}
	}
	
	private static void offsetMovement(final Square destinationSquare,final int x_offset, final int y_offset, final Class<? extends Piece> piecetype, final ChessColor wantedColor, final ArrayList<Square> squaresToCheck) throws IllegalMoveException{

		if ((destinationSquare.getX() + x_offset) >= 0 && (destinationSquare.getX() + x_offset) < Board.CHESSBOARD_DIMENSION 
			&& (destinationSquare.getY() + y_offset) >= 0 && (destinationSquare.getY() + y_offset) < Board.CHESSBOARD_DIMENSION){

			Square temp_square = Game.getBoard().getSquare(destinationSquare.getX() + x_offset, destinationSquare.getY() + y_offset);
			if(temp_square.isOccupied()){
				if(temp_square.getPiece().getClass() == piecetype && temp_square.getPiece().getColor() == wantedColor){
					squaresToCheck.add(temp_square);
				}
			}
		}
	}
	
	private static Square checkFlower(Square destinationSquare, int startX, int startY, Class<? extends Piece> piecetype, ChessColor wantedColor) throws IllegalMoveException{
		ArrayList<Square> squaresToCheck = new ArrayList<Square>();
		final int LONG_POSITIVE_KNIGHT_MOVEMENT = 2;
		final int LONG_NEGATIVE_KNIGHT_MOVEMENT = -2;
		final int SHORT_POSITIVE_KNIGHT_MOVEMENT = 1;
		final int SHORT_NEGATIVE_KNIGHT_MOVEMENT = -1;
		
		if (startX != -1) {
			switch (Math.abs(startX-destinationSquare.getX())) { 
				case 1:
					
					offsetMovement(destinationSquare, startX-destinationSquare.getX(), LONG_POSITIVE_KNIGHT_MOVEMENT, piecetype, wantedColor, squaresToCheck);
					offsetMovement(destinationSquare, startX-destinationSquare.getX(), LONG_NEGATIVE_KNIGHT_MOVEMENT, piecetype, wantedColor, squaresToCheck);
					if(squaresToCheck.size() != 1) {
						throw new IllegalMoveException();
					} else {
						return squaresToCheck.get(0);
					}
				case 2:

					offsetMovement(destinationSquare, startX-destinationSquare.getX(), SHORT_POSITIVE_KNIGHT_MOVEMENT, piecetype, wantedColor, squaresToCheck);
					offsetMovement(destinationSquare, startX-destinationSquare.getX(), SHORT_NEGATIVE_KNIGHT_MOVEMENT, piecetype, wantedColor, squaresToCheck);
					if(squaresToCheck.size() != 1) {
						throw new IllegalMoveException();
					}
					else {
						return squaresToCheck.get(0);
					}
			}
		}
		if(startY != -1) {
			switch(Math.abs(destinationSquare.getY() - startY)) { 
				case 1:
					
					offsetMovement(destinationSquare, LONG_POSITIVE_KNIGHT_MOVEMENT, /*!!*/ startY - destinationSquare.getY(), piecetype, wantedColor, squaresToCheck);
					offsetMovement(destinationSquare, LONG_NEGATIVE_KNIGHT_MOVEMENT, startY - destinationSquare.getY(), piecetype, wantedColor, squaresToCheck);
					if(squaresToCheck.size() != 1) {
						throw new IllegalMoveException();
					}
					else {
						return squaresToCheck.get(0);
					}
				case 2:
					
					offsetMovement(destinationSquare, SHORT_POSITIVE_KNIGHT_MOVEMENT, startY - destinationSquare.getY(), piecetype, wantedColor, squaresToCheck);
					offsetMovement(destinationSquare, SHORT_NEGATIVE_KNIGHT_MOVEMENT, startY - destinationSquare.getY(), piecetype, wantedColor, squaresToCheck);
					if(squaresToCheck.size() != 1) {
						throw new IllegalMoveException();
					}
					else {
						return squaresToCheck.get(0);
					}
			}
		}
		
		offsetMovement(destinationSquare, LONG_POSITIVE_KNIGHT_MOVEMENT, SHORT_POSITIVE_KNIGHT_MOVEMENT, piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, LONG_POSITIVE_KNIGHT_MOVEMENT, SHORT_NEGATIVE_KNIGHT_MOVEMENT, piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, SHORT_POSITIVE_KNIGHT_MOVEMENT, LONG_POSITIVE_KNIGHT_MOVEMENT, piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, SHORT_POSITIVE_KNIGHT_MOVEMENT, LONG_NEGATIVE_KNIGHT_MOVEMENT, piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, SHORT_NEGATIVE_KNIGHT_MOVEMENT, LONG_POSITIVE_KNIGHT_MOVEMENT, piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, SHORT_NEGATIVE_KNIGHT_MOVEMENT, LONG_NEGATIVE_KNIGHT_MOVEMENT, piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, LONG_NEGATIVE_KNIGHT_MOVEMENT, SHORT_POSITIVE_KNIGHT_MOVEMENT, piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, LONG_NEGATIVE_KNIGHT_MOVEMENT, SHORT_NEGATIVE_KNIGHT_MOVEMENT, piecetype, wantedColor, squaresToCheck);

		if(squaresToCheck.size() > 1) {
			throw new IllegalMoveException();
		}
		else if(squaresToCheck.size() == 0) {
			return null;
		}
		else {
			return squaresToCheck.get(0);
		}
	}

	private static Square isThereAKingAroundThisSquare(Square destinationSquare, ChessColor wantedColor) throws IllegalMoveException{

	        ArrayList<Square> squaresToCheck = new ArrayList<Square>();
	        offsetMovement(destinationSquare, +1, 0, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, +1, -1, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, 0, -1, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, -1, -1, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, -1, 0, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, -1, +1, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, 0, +1, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, +1, +1, King.class, wantedColor, squaresToCheck);

	        if(squaresToCheck.size() > 1) {
	        	
	            throw new IllegalMoveException();
	        }
	        else if(squaresToCheck.size() == 0) {
	            return null;
	        }
	        else {
	            return squaresToCheck.get(0);
	        }
	 }

	public static boolean isTurnKingNotInCheck(Square finalsquare) throws IllegalMoveException {
		if(finalsquare.isOccupied() && finalsquare.getPiece().getColor() == Game.getTurn() && finalsquare.getPiece().getClass() != King.class) {
			throw new IllegalMoveException();
		}

		if(checkFlower(finalsquare,-1,-1,Knight.class, Game.getEnemyTurn()) == null && queenIntersectionControl(finalsquare, -1, -1, Queen.class, Game.getEnemyTurn()) == null 
			&& plusMovement(finalsquare, -1,-1,Rook.class, Game.getEnemyTurn()) == null && checkCross(finalsquare,-1,-1, Bishop.class, Game.getEnemyTurn()) == null
			&& checkPawn_Capture(finalsquare, -1, Pawn.class, Game.getEnemyTurn()) == null && isThereAKingAroundThisSquare(finalsquare, Game.getEnemyTurn()) == null){

				return true;
		}
		else{

			return false;
		}	
	}

	private static void northCheck(Square finalsquare, ArrayList<Square> checksquares, Class<? extends Piece> piecetype, ChessColor wantedColor) throws IllegalMoveException{

		int i = 1;
		Square temp_square;
		while(finalsquare.getX()+i < Board.CHESSBOARD_DIMENSION) {
			temp_square = Game.getBoard().getSquare(finalsquare.getX()+i, finalsquare.getY());
			if(temp_square.isOccupied()) {
				if(temp_square.getPiece().getColor() == wantedColor && temp_square.getPiece().getClass() == piecetype) {
					checksquares.add(temp_square);
					return;
				}
				else {
					return;
				}
			}
			++i;
		}
	}

	private static void southCheck(Square finalsquare, ArrayList<Square> checksquares, Class<? extends Piece> piecetype, ChessColor wantedColor) throws IllegalMoveException{

		int i = 1;
		Square temp_square;
		while(finalsquare.getX()-i >=0) {
			temp_square = Game.getBoard().getSquare(finalsquare.getX()-i, finalsquare.getY());
			if(temp_square.isOccupied()) {
				if(temp_square.getPiece().getColor() == wantedColor && temp_square.getPiece().getClass() == piecetype) {
					checksquares.add(temp_square);
					return;
				}
				else {
					return;
				}
			}
			++i;
		}
	}

	private static void westCheck(Square finalsquare, ArrayList<Square> checksquares, Class<? extends Piece> piecetype, ChessColor wantedColor) throws IllegalMoveException{

		int i = 1;
		Square temp_square;
		while(finalsquare.getY()+i < Board.CHESSBOARD_DIMENSION) {
			temp_square = Game.getBoard().getSquare(finalsquare.getX(), finalsquare.getY()+i);
			if(temp_square.isOccupied()) {
				if(temp_square.getPiece().getColor() == wantedColor && temp_square.getPiece().getClass() == piecetype) {
					checksquares.add(temp_square);
					return;
				}
				else {
					return;
				}
			}
			++i;
		}
	}

	private static void eastCheck(Square finalsquare, ArrayList<Square> checksquares, Class<? extends Piece> piecetype, ChessColor wantedColor) throws IllegalMoveException{
	
		int i = 1;
		Square temp_square;
		while(finalsquare.getY()-i >= 0) {
			temp_square = Game.getBoard().getSquare(finalsquare.getX(), finalsquare.getY()-i);
			if(temp_square.isOccupied()) {
				if(temp_square.getPiece().getColor() == wantedColor && temp_square.getPiece().getClass() == piecetype) {
					checksquares.add(temp_square);
					return;
				}
				else {
					return;
				}
			}
			++i;
		}
	}
}
