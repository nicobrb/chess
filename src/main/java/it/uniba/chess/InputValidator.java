package it.uniba.chess;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.uniba.chess.pieces.Piece;
import it.uniba.chess.pieces.King;
import it.uniba.chess.pieces.Pawn;
import it.uniba.chess.pieces.Knight;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.pieces.Bishop;
import it.uniba.chess.pieces.Queen;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.GameStatus;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParseFiles;

/**
 * Dato un input, lo elabora e determina se è una mossa di gioco o un'opzione del menù;
 * In caso di mossa, ne controlla la validità secondo le regole degli scacchi e della notazione algebrica abbreviata.
 *
 * «Control»
 */
public final class InputValidator {

	private static final int FIRST_GROUP = 1;
	private static final int SECOND_GROUP = 2;
	private static final int THIRD_GROUP = 3;
	private static final int FOURTH_GROUP = 4;
	private static final int FIFTH_GROUP = 5;
	private static Pattern regExpPattern;

	static {
		String regExp = "^(C|A|T|D|R|)([a-h]?|[1-8]?)(x?)([a-h][1-8])((e[.]p[.])?)$";
		regExpPattern = Pattern.compile(regExp);
	}

	private InputValidator() { }
	public static void parseCommand(final String command) throws IllegalMoveException {
		try {
			if (!checkMenu(command)) {
				Matcher regExpMatcher = regExpPattern.matcher(command);

				//if the command is a grammatically correct Pawn move
				if (regExpMatcher.matches() 
					&& Game.getGameStatus() == GameStatus.ACTIVE) {
					String pieceTypeString = regExpMatcher.group(FIRST_GROUP);
					String startRankOrFileString = regExpMatcher.group(SECOND_GROUP);
					String startRankString = getStartingRank(startRankOrFileString);
					String startFileString = getStartingFile(startRankOrFileString);
					String isCaptureString = regExpMatcher.group(THIRD_GROUP);
					String destString = regExpMatcher.group(FOURTH_GROUP);
					String isEnpassantString = regExpMatcher.group(FIFTH_GROUP);
					//this matches the whole string to be saved into
					//the moves list if it's not an Illegal Move
					String lastmove = regExpMatcher.group(0);
					//we will surely have destination square,
					//we just need to convert the algebraic notation to matrix coordinates
					Square destSquare = getDestinationSquare(destString);

					//we must always have a starting square too
					Square startSquare;
					int startRank;
					int startFile;
					if (startRankString.isEmpty()) {
						startRank = -1;
					} else {
						startRank = Integer.parseInt(startRankString) - 1;
					}
					if (startFileString.isEmpty()) {
						startFile = -1;
					} else {
						startFile = ParseFiles.getFileIntFromChar(startFileString.charAt(0));
					}
					if (isEnpassantString.isEmpty() && !pieceTypeString.equals("")) {
						switch (pieceTypeString) {
						case "C":
							startSquare = checkFlower(destSquare, startRank,
									startFile, Knight.class, Game.getTurn());

							Move.pieceMoveOrCapture(Knight.class, startSquare,
									destSquare, !isCaptureString.isEmpty());
							Game.addPrintableMove(lastmove);
							break;
						case "A":
							startSquare = checkCross(destSquare, startRank,
									startFile, Bishop.class, Game.getTurn());
							Move.pieceMoveOrCapture(Bishop.class, startSquare,
									destSquare, !isCaptureString.isEmpty());
							Game.addPrintableMove(lastmove);
							break;
						case "T":
							//if we have an empty rank string, we return -1 as control;
							startSquare = plusMovement(destSquare, startRank,
							startFile, Rook.class, Game.getTurn());
							Move.pieceMoveOrCapture(Rook.class, startSquare,
							destSquare, 
							!isCaptureString.isEmpty());
							Game.addPrintableMove(lastmove);
							break;
						case "D":
							startSquare = queenIntersectionControl(destSquare, startRank,
									startFile, Queen.class, Game.getTurn());
							Move.pieceMoveOrCapture(Queen.class, startSquare,
									destSquare, !isCaptureString.isEmpty());
							Game.addPrintableMove(lastmove);
							break;
						case "R":
							//fallthrough
						default:
							if (startRankOrFileString.isEmpty()) {
								if ((lastmove.equals("Rg1"))
										|| (lastmove.equals("Rg8"))) {

									if (Move.shortCastle(Game.getTurn())) {
										Game.addPrintableMove(lastmove);
										return;
									}
								} else if ((lastmove.equals("Rc1"))
										|| (lastmove.equals("Rc8"))) {

									if (Move.longCastle(Game.getTurn())) {
										Game.addPrintableMove(lastmove);
										return;
									}
								}
								startSquare = isThereAKingAroundThisSquare(
									destSquare, Game.getTurn());
									Move.pieceMoveOrCapture(King.class, startSquare,
										destSquare, !isCaptureString.isEmpty());
									Game.addPrintableMove(lastmove);
									break;
							} else {
								throw new IllegalMoveException();
							}
						}
					} else {
						if (startRankOrFileString.isEmpty() 
						&& isCaptureString.isEmpty()
						&& isEnpassantString.isEmpty()) {
							//this is a pawn move
							startSquare = getPawnStartingSquareMove(destSquare);
							Move.pawnMove(startSquare, destSquare);
							Game.addPrintableMove(lastmove);

						} else if (startRankString.isEmpty() 
								&& !startFileString.isEmpty()
								&& !isCaptureString.isEmpty()) {
							//this is a pawn capture (either simple or en-passant)
							startSquare = checkPawnCapture(destSquare, startFile,
									Pawn.class, Game.getTurn());
							//only way is to call it with startingY != -1
							Move.pawnCapture(startSquare, destSquare,
									!isEnpassantString.isEmpty());
							Game.addPrintableMove(lastmove);
						} else {
							throw new IllegalMoveException();
						}
					} 
					Game.nextTurn();
					return;
				} else {
					System.out.print("Comando non riconosciuto. "
					+ "Scrivi help per la lista dei comandi.\n");
				}
			}
			//end algebraic move default case;
			//if nothing did return until now, it's an invalid command (not exclusively an Illegal move)
			//throw new IllegalMoveException();
		} catch (IllegalMoveException illegalmove) {
			throw illegalmove;
		}
	}

	private static boolean checkMenu(final String command) throws IllegalMoveException {
		switch (command) {
		case "0-0":
			//fallthrough
		case "O-O":
			if (Game.getGameStatus() == GameStatus.ACTIVE) {
				if (Move.shortCastle(Game.getTurn())) {
					Game.addPrintableMove(command);
				} else {
					throw new IllegalMoveException();
				}
			} else {
				System.out.print("Gioco non ancora avviato.\n");
			}
			return true;
		case "0-0-0":
			//fallthrough
		case "O-O-O":
			if (Game.getGameStatus() == GameStatus.ACTIVE) {
				if (Move.longCastle(Game.getTurn())) {
					Game.addPrintableMove(command);
				} else {
				throw new IllegalMoveException();
				}
			} else {
				System.out.print("Gioco non ancora avviato.\n");
			}
			return true;
		case "board":
			if (Game.getGameStatus() == GameStatus.ACTIVE) {
				Game.getBoard().print();
			} else {
				System.out.print("Gioco non ancora avviato.\n");
			}
			return true;

		case "quit":
			return true;

		case "help":
			Game.printHelp();
			return true;

		case "captures":
			Game.capturedMaterial();
			return true;

		case "moves":
			Game.printListOfMoves();
			return true;

		case "play":
			return true;
		default:
			return false;
		}
	}

	private static Square getDestinationSquare(final String regExpGroupDest) {
			int destSquareFile = ParseFiles.getFileIntFromChar(regExpGroupDest.charAt(0));
			int destSquareRank = Character.getNumericValue(regExpGroupDest.charAt(1)) - 1;
			return Game.getBoard().getSquare(destSquareRank, destSquareFile);
	}

	/* We look for a plausible pawn to moved in the previous two ranks and the same file;
	 * There can be:
	 * 	A pawn just before the destination square: we move it;
	 * 	A pawn with a white square before its destination:
	 * we check if it's the pawn's first move, otherwise we don't move it;
	 *	A pawn with another piece in front of it before its destination: we don't ever move;
	 */
	private static Square getPawnStartingSquareMove(final Square destSquare) throws IllegalMoveException {
		try {
			ArrayList<Square> squaresToCheck = new ArrayList<Square>();
			Piece pieceToCheck;

			//we dinamically populate a list of squares to check adding the first and the second
			//square before the destination square of our Pawn

			if (Game.getTurn() == ChessColor.WHITE) {
				if (destSquare.getX() - 1 > 0) {
					squaresToCheck.add(Game.getBoard().getSquare(
							destSquare.getX() - 1, destSquare.getY()));
				}

				if (destSquare.getX() - 2 > 0) {
					squaresToCheck.add(Game.getBoard().getSquare(
							destSquare.getX() - 2, destSquare.getY()));
				}
			} else {
				if (destSquare.getX() + 1 < Board.CHESSBOARD_DIMENSION) {
					squaresToCheck.add(Game.getBoard().getSquare(
							destSquare.getX() + 1, destSquare.getY()));
				}

				if (destSquare.getX() + 2 < Board.CHESSBOARD_DIMENSION) {
					squaresToCheck.add(Game.getBoard().getSquare(
							destSquare.getX() + 2, destSquare.getY()));
				}
			}

			if (squaresToCheck.size() > 0 && squaresToCheck.get(0).isOccupied()) {
				pieceToCheck = squaresToCheck.get(0).getPiece();

				//if the square before destination is occupied by EXACTLY a pawn
				if ((pieceToCheck.getClass() == Pawn.class)
						&& (pieceToCheck.getColor() == Game.getTurn())) {
					return squaresToCheck.get(0);
				}

			} else {
				//first square was empty, let's check for a pawn in a starting position
				if (squaresToCheck.size() > 1 && squaresToCheck.get(1).isOccupied()) {
					pieceToCheck = squaresToCheck.get(1).getPiece();
					if ((pieceToCheck.getClass() == Pawn.class)
							&& (pieceToCheck.getColor() == Game.getTurn())) {
						return squaresToCheck.get(1);
					}
				}

			}
			//if the method hasn't returned by now, it must be an illegal move
			throw new IllegalMoveException();

		} catch (IllegalMoveException illegalmove) {
			/*
			 * we don't need to create another exception, just reuse what we already have
			 * most of the times this catch block will happen because we go check for an out-of-bounds-move
			 * example: white turn a2 or a1. White pawns can NEVER move
			 * there and getSquare() method will go check
			 * for a pawn in a0, which doesn't exist and will produce this exception
			 *
			*/
			throw illegalmove;
		}
	}

	private static Square checkPawnCapture(final Square destSquare, final int startingY,
			final Class<? extends Piece> piecetype, final ChessColor wantedColor)
					throws IllegalMoveException {
		ArrayList<Square> squaresToCheck = new ArrayList<Square>();

		if (startingY != -1) {
			if (Math.abs(destSquare.getY() - startingY) == 1) {
				//we have a true pawn capture
				if (wantedColor == ChessColor.WHITE) {
					offsetMovement(destSquare, -1, startingY - destSquare.getY(),
							piecetype, wantedColor, squaresToCheck);
				} else {
					offsetMovement(destSquare, +1, startingY - destSquare.getY(),
							piecetype, wantedColor, squaresToCheck);
				}
			}
		} else {
			//we are using it to check if king is in check
			if (wantedColor == ChessColor.WHITE) {

				offsetMovement(destSquare, -1, +1, piecetype, wantedColor, squaresToCheck);
				offsetMovement(destSquare, -1, -1, piecetype, wantedColor, squaresToCheck);
			} else {

				offsetMovement(destSquare, +1, +1, piecetype, wantedColor, squaresToCheck);
				offsetMovement(destSquare, +1, -1, piecetype, wantedColor, squaresToCheck);
			}
		}

		if (squaresToCheck.size() > 1) {
			throw new IllegalMoveException();
		} else if (squaresToCheck.size() == 0) {
			return null;
		} else {
			return squaresToCheck.get(0);
		}
	}

	private static String getStartingRank(final String startRankOrFile) {
		if (startRankOrFile.matches("[1-8]")) {
			return startRankOrFile;
		}
		return "";
	}

	private static String getStartingFile(final String startRankOrFile) {
		if (startRankOrFile.matches("[a-h]")) {
			return startRankOrFile;
		}
		return "";
	}

private static Square plusMovement(Square finalsquare, int startingX, int startingY, Class<? extends Piece> pieceClassToSearch, ChessColor wantedColor) throws IllegalMoveException {
		
		ArrayList<Square> squares_to_check = new ArrayList<Square>();
		
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
				else {
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
		else if(startingX != -1 && startingX == finalsquare.getX()) {
			westCheck(finalsquare, squares_to_check, pieceClassToSearch, wantedColor);
			eastCheck(finalsquare, squares_to_check, pieceClassToSearch, wantedColor);
			
			if(squares_to_check.size() > 1) {
				throw new IllegalMoveException();
			}
			else if(squares_to_check.size() == 0)
				return null;
			
			return squares_to_check.get(0);		
		}
		if(startingY != -1 && startingY != finalsquare.getY() &&  Game.getBoard().getSquare(finalsquare.getX(), startingY).isOccupied()) {
			
			if(Game.getBoard().getSquare(finalsquare.getX(), startingY).getPiece().getClass() == pieceClassToSearch &&
			Game.getBoard().getSquare(finalsquare.getX(), startingY).getPiece().getColor() == wantedColor ) {
				if(finalsquare.getY() > startingY) {
					for(int i = finalsquare.getY()-1; i > startingY; --i) {
						if(Game.getBoard().getSquare(finalsquare.getX(), i).isOccupied()) {
							throw new IllegalMoveException();
						}
					}	
				}
				else {
					for(int i = finalsquare.getY()+1; i < startingY; ++i) {
						if(Game.getBoard().getSquare(finalsquare.getX(), i).isOccupied()) {
							throw new IllegalMoveException();
						}
					}	
				}
				return Game.getBoard().getSquare(finalsquare.getX(), startingY);
			}
			else throw new IllegalMoveException();
			
		}
		else if(startingY != -1 && startingY == finalsquare.getY()){
			northCheck(finalsquare, squares_to_check, pieceClassToSearch, wantedColor);
			southCheck(finalsquare, squares_to_check, pieceClassToSearch, wantedColor);
		
			if(squares_to_check.size() > 1) {
				throw new IllegalMoveException();
			}
			else if(squares_to_check.size() == 0)
				return null;
			
			return squares_to_check.get(0);			
		}		
		
		northCheck(finalsquare, squares_to_check, pieceClassToSearch, wantedColor);
		southCheck(finalsquare, squares_to_check, pieceClassToSearch, wantedColor);
		westCheck(finalsquare, squares_to_check, pieceClassToSearch, wantedColor);
		eastCheck(finalsquare, squares_to_check, pieceClassToSearch, wantedColor);
	
	  
	      //if we have more than one of this piece-color combo
		if(squares_to_check.size() > 1) {
			throw new IllegalMoveException();
		}
		else if(squares_to_check.size() == 0)
			return null;
		
		return squares_to_check.get(0);	
	}

	private static void diagonalUpperRight(final Square finalsquare, final ArrayList<Square> checksquares,
	final Class<? extends Piece> piecetype, final ChessColor wantedColor) throws IllegalMoveException {
		int i = 1;
		Square tempSquare;
		while (finalsquare.getX() + i < Board.CHESSBOARD_DIMENSION
		&& finalsquare.getY() + i < Board.CHESSBOARD_DIMENSION) {
			tempSquare = Game.getBoard().getSquare(finalsquare.getX() + i, finalsquare.getY() + i);
			if (tempSquare.isOccupied()) {
				if (tempSquare.getPiece().getColor() == wantedColor
				&& tempSquare.getPiece().getClass() == piecetype) {
					checksquares.add(tempSquare);
					return;
				} else {
					return;
				}
			}
			++i;
		}
	}

	private static void diagonalLowerRight(final Square finalsquare, final ArrayList<Square> checksquares,
	final Class<? extends Piece> piecetype, final ChessColor wantedColor) throws IllegalMoveException {
		int i = 1;
		Square tempSquare;
		while (finalsquare.getX() - i >= 0 && finalsquare.getY() + i < Board.CHESSBOARD_DIMENSION) {
			tempSquare = Game.getBoard().getSquare(finalsquare.getX() - i, finalsquare.getY() + i);
			if (tempSquare.isOccupied()) {
				if (tempSquare.getPiece().getColor() == wantedColor
				&& tempSquare.getPiece().getClass() == piecetype) {
					checksquares.add(tempSquare);
					return;
				} else {
					return;
				}
			}
			++i;
		}
	}

	private static void diagonalUpperLeft(final Square finalsquare, final ArrayList<Square> checksquares,
	final Class<? extends Piece> piecetype, final ChessColor wantedColor) throws IllegalMoveException {
		int i = 1;
		Square tempSquare;
		while (finalsquare.getX() + i < Board.CHESSBOARD_DIMENSION && finalsquare.getY() - i >= 0) {
			tempSquare = Game.getBoard().getSquare(finalsquare.getX() + i, finalsquare.getY() - i);
			if (tempSquare.isOccupied()) {
				if (tempSquare.getPiece().getColor() == wantedColor
				&& tempSquare.getPiece().getClass() == piecetype) {
					checksquares.add(tempSquare);
					return;
				} else {
					return;
				}
			}
			++i;
		}
	}

	private static void diagonalLowerLeft(final Square finalsquare, final ArrayList<Square> checksquares,
	final Class<? extends Piece> piecetype, final ChessColor wantedColor) throws IllegalMoveException {
		int i = 1;
		Square tempSquare;
		while (finalsquare.getX() - i >= 0 && finalsquare.getY() - i >= 0) {
			tempSquare = Game.getBoard().getSquare(finalsquare.getX() - i, finalsquare.getY() - i);
			if (tempSquare.isOccupied()) {
				if (tempSquare.getPiece().getColor() == wantedColor
				&& tempSquare.getPiece().getClass() == piecetype) {
					checksquares.add(tempSquare);
					return;
				} else {
					return;
				}
			}
			++i;
		}
	}

	private static Square checkCross(final Square finalsquare, final int startingX, final int startingY,
	final Class<? extends Piece> piecetype, final ChessColor wantedColor) throws IllegalMoveException {

		ArrayList<Square> squaresToCheck = new ArrayList<Square>();
		diagonalUpperRight(finalsquare, squaresToCheck, piecetype, wantedColor);
		if (squaresToCheck.size() == 0) {
			diagonalLowerRight(finalsquare, squaresToCheck, piecetype, wantedColor);
			if (squaresToCheck.size() == 0) {
				diagonalLowerLeft(finalsquare, squaresToCheck, piecetype, wantedColor);
				if (squaresToCheck.size() == 0) {
					diagonalUpperLeft(finalsquare, squaresToCheck, piecetype, wantedColor);
				}
			}
		}
		if (squaresToCheck.size() == 0) {
            return null;
        } else {
        	if (startingX != -1) {
        		if (startingX != squaresToCheck.get(0).getX()) {
        			throw new IllegalMoveException();
        		}
        	} else if (startingY != -1) {
        		if (startingY != squaresToCheck.get(0).getY()) {
        			throw new IllegalMoveException();
        		}
        	}
        	return squaresToCheck.get(0);
        }
	}

	private static Square queenIntersectionControl(final Square finalsquare, final int startX, final int startY,
	final Class<? extends Piece> pieceClassToSearch, final ChessColor wantedColor) throws IllegalMoveException {
		Square plusSquare = plusMovement(finalsquare, startX, startY, pieceClassToSearch, wantedColor);
		Square crossSquare = checkCross(finalsquare, startX, startY, pieceClassToSearch, wantedColor);

		if (plusSquare == null && crossSquare == null) {
			return null;
		} else if (plusSquare != null && crossSquare != null) {
			throw new IllegalMoveException();
		} else {
			if (plusSquare != null) {
				return plusSquare;
			} else {
				return crossSquare;
			}
		}
	}

	private static void offsetMovement(final Square destinationSquare, final int offsetX, final int offsetY,
	final Class<? extends Piece> piecetype, final ChessColor wantedColor, final ArrayList<Square> squaresToCheck)
	throws IllegalMoveException {

		if ((destinationSquare.getX() + offsetX) >= 0
		&& (destinationSquare.getX() + offsetX) < Board.CHESSBOARD_DIMENSION
		&& (destinationSquare.getY() + offsetY) >= 0
		&& (destinationSquare.getY() + offsetY) < Board.CHESSBOARD_DIMENSION) {

			Square tempSquare = Game.getBoard().getSquare(destinationSquare.getX() + offsetX,
			destinationSquare.getY() + offsetY);
			if (tempSquare.isOccupied()) {
				if (tempSquare.getPiece().getClass() == piecetype
				&& tempSquare.getPiece().getColor() == wantedColor) {
					squaresToCheck.add(tempSquare);
				}
			}
		}
	}

	private static Square checkFlower(final Square destinationSquare, final int startX, final int startY,
	final Class<? extends Piece> piecetype, final ChessColor wantedColor) throws IllegalMoveException {
		ArrayList<Square> squaresToCheck = new ArrayList<Square>();
		final int longPositiveKnightMovement = 2;
		final int longNegativeKnightMovement = -2;
		final int shortPositiveKnightMovement = 1;
		final int shortNegativeKnightMovement = -1;

		if (startX != -1) {
			switch (Math.abs(startX - destinationSquare.getX())) {
				case 1:

					offsetMovement(destinationSquare, startX - destinationSquare.getX(),
					longPositiveKnightMovement, piecetype, wantedColor, squaresToCheck);
					offsetMovement(destinationSquare, startX - destinationSquare.getX(),
					longNegativeKnightMovement, piecetype, wantedColor, squaresToCheck);
					break;
				case 2:
				//fallthrough
				default:
					offsetMovement(destinationSquare, startX - destinationSquare.getX(),
					shortPositiveKnightMovement, piecetype, wantedColor, squaresToCheck);
					offsetMovement(destinationSquare, startX - destinationSquare.getX(),
					shortNegativeKnightMovement, piecetype, wantedColor, squaresToCheck);
					break;

			}
		} else if (startY != -1) {
			switch (Math.abs(destinationSquare.getY() - startY)) {
				case 1:

					offsetMovement(destinationSquare, longPositiveKnightMovement,
					startY - destinationSquare.getY(), piecetype, wantedColor, squaresToCheck);
					offsetMovement(destinationSquare, longNegativeKnightMovement,
					startY - destinationSquare.getY(), piecetype, wantedColor, squaresToCheck);
					break;
				case 2:
				//fallthrough
				default:
					offsetMovement(destinationSquare, shortPositiveKnightMovement,
					startY - destinationSquare.getY(), piecetype, wantedColor, squaresToCheck);
					offsetMovement(destinationSquare, shortNegativeKnightMovement,
					startY - destinationSquare.getY(), piecetype, wantedColor, squaresToCheck);
					break;
			}
		} else {

		offsetMovement(destinationSquare, longPositiveKnightMovement, shortPositiveKnightMovement,
		piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, longPositiveKnightMovement, shortNegativeKnightMovement,
		piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, shortPositiveKnightMovement, longPositiveKnightMovement,
		piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, shortPositiveKnightMovement, longNegativeKnightMovement,
		piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, shortNegativeKnightMovement, longPositiveKnightMovement,
		piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, shortNegativeKnightMovement, longNegativeKnightMovement,
		piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, longNegativeKnightMovement, shortPositiveKnightMovement,
		piecetype, wantedColor, squaresToCheck);
		offsetMovement(destinationSquare, longNegativeKnightMovement, shortNegativeKnightMovement,
		piecetype, wantedColor, squaresToCheck);
		}
		if (squaresToCheck.size() > 1) {
			throw new IllegalMoveException();
		} else if (squaresToCheck.size() == 0) {
			return null;
		} else {
			return squaresToCheck.get(0);
		}
	}

	private static Square isThereAKingAroundThisSquare(final Square destinationSquare, final ChessColor wantedColor)
	throws IllegalMoveException {

	        ArrayList<Square> squaresToCheck = new ArrayList<Square>();
	        offsetMovement(destinationSquare, +1, 0, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, +1, -1, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, 0, -1, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, -1, -1, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, -1, 0, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, -1, +1, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, 0, +1, King.class, wantedColor, squaresToCheck);
	        offsetMovement(destinationSquare, +1, +1, King.class, wantedColor, squaresToCheck);


	       if (squaresToCheck.size() == 0) {
	            return null;
	        } else {
	            return squaresToCheck.get(0);
	        }
	 }

	public static boolean isTurnKingNotInCheck(final Square finalsquare) throws IllegalMoveException {

		return (checkFlower(finalsquare, -1, -1, Knight.class, Game.getEnemyTurn()) == null
		&& queenIntersectionControl(finalsquare, -1, -1, Queen.class, Game.getEnemyTurn()) == null
		&& plusMovement(finalsquare, -1, -1, Rook.class, Game.getEnemyTurn()) == null
		&& checkCross(finalsquare, -1, -1, Bishop.class, Game.getEnemyTurn()) == null
		&& checkPawnCapture(finalsquare, -1, Pawn.class, Game.getEnemyTurn()) == null
		&& isThereAKingAroundThisSquare(finalsquare, Game.getEnemyTurn()) == null);

	}

	private static void northCheck(final Square finalsquare, final ArrayList<Square> checksquares,
	final Class<? extends Piece> piecetype, final ChessColor wantedColor) throws IllegalMoveException {

		int i = 1;
		Square tempSquare;
		while (finalsquare.getX() + i < Board.CHESSBOARD_DIMENSION) {
			tempSquare = Game.getBoard().getSquare(finalsquare.getX() + i, finalsquare.getY());
			if (tempSquare.isOccupied()) {
				if (tempSquare.getPiece().getColor() == wantedColor
				&& tempSquare.getPiece().getClass() == piecetype) {
					checksquares.add(tempSquare);
					return;
				} else {
					return;
				}
			}
			++i;
		}
	}

	private static void southCheck(final Square finalsquare, final ArrayList<Square> checksquares,
	final Class<? extends Piece> piecetype, final ChessColor wantedColor) throws IllegalMoveException {

		int i = 1;
		Square tempSquare;
		while (finalsquare.getX() - i >= 0) {
			tempSquare = Game.getBoard().getSquare(finalsquare.getX() - i, finalsquare.getY());
			if (tempSquare.isOccupied()) {
				if (tempSquare.getPiece().getColor() == wantedColor
				&& tempSquare.getPiece().getClass() == piecetype) {
					checksquares.add(tempSquare);
					return;
				} else {
					return;
				}
			}
			++i;
		}
	}

	private static void westCheck(final Square finalsquare, final ArrayList<Square> checksquares,
	final Class<? extends Piece> piecetype, final ChessColor wantedColor) throws IllegalMoveException {

		int i = 1;
		Square tempSquare;
		while (finalsquare.getY() + i < Board.CHESSBOARD_DIMENSION) {
			tempSquare = Game.getBoard().getSquare(finalsquare.getX(), finalsquare.getY() + i);
			if (tempSquare.isOccupied()) {
				if (tempSquare.getPiece().getColor() == wantedColor
				&& tempSquare.getPiece().getClass() == piecetype) {
					checksquares.add(tempSquare);
					return;
				} else {
					return;
				}
			}
			++i;
		}
	}

	private static void eastCheck(final Square finalsquare, final ArrayList<Square> checksquares,
	final Class<? extends Piece> piecetype, final ChessColor wantedColor) throws IllegalMoveException {

		int i = 1;
		Square tempSquare;
		while (finalsquare.getY() - i >= 0) {
			tempSquare = Game.getBoard().getSquare(finalsquare.getX(), finalsquare.getY() - i);
			if (tempSquare.isOccupied()) {
				if (tempSquare.getPiece().getColor() == wantedColor
				&& tempSquare.getPiece().getClass() == piecetype) {
					checksquares.add(tempSquare);
					return;
				} else {
					return;
				}
			}
			++i;
		}
	}
}
