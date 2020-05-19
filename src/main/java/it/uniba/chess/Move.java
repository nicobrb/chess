package it.uniba.chess;


import it.uniba.chess.pieces.King;
import it.uniba.chess.pieces.Pawn;
import it.uniba.chess.pieces.Piece;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParseFiles;

/**
 * Esegue l'azione di movimento o cattura di ogni pezzo.
 *
 * <<Control>>
 */
public class Move {

	public static void pawnMove(final Square initialsquare, final Square finalsquare) throws IllegalMoveException {

		if (finalsquare.isOccupied()) {
			throw new IllegalMoveException();
		}

		if (((Math.abs(finalsquare.getX() - initialsquare.getX()) == 2)
				&& !((Pawn) initialsquare.getPiece()).getHasMoved())
				|| ((Math.abs(finalsquare.getX() -  initialsquare.getX()) == 1))) {
			//explicitly cast to Pawn to check for moved
				finalsquare.setOccupied(true);
				finalsquare.setPiece(initialsquare.getPiece());
				initialsquare.setOccupied(false);

				if (InputValidator.isTurnKingInCheck(Game.getKingPosition(Game.getTurn()))) {

					((Pawn) finalsquare.getPiece()).setHasMoved();
					Game.addNewStartingSquare(initialsquare);
					Game.addNewDestinationSquare(finalsquare);
					return;
				} else {
					initialsquare.setOccupied(true);
					initialsquare.setPiece(finalsquare.getPiece());
					finalsquare.setOccupied(false);

					//pinned piece
					throw new IllegalMoveException();
			}
		}
		//in any other case
		throw new IllegalMoveException();

	}

	public static void pawnCapture(final Square initialsquare, final Square finalsquare,
			final boolean enPassantFlag) throws IllegalMoveException {
		if (initialsquare == null) {
			throw new IllegalMoveException();
		}

		try {
			//if we can capture en-passant we are done
			if (captureEnPassant(initialsquare, finalsquare)) {
				return;
			} else {
				if (enPassantFlag) {
					throw new IllegalMoveException();
				}
			}
		} catch (IllegalMoveException ex) {
			throw ex;
		}

		//otherwise we check for a normal capture
		if (initialsquare.getPiece().getClass() == Pawn.class) {
			if (Math.abs(finalsquare.getY() - initialsquare.getY()) == 1) {
				if (finalsquare.getX() > initialsquare.getX() && initialsquare.getPiece().getColor()
						== ChessColor.WHITE) {
					if (finalsquare.getPiece() != null && finalsquare.getPiece().getColor()
							!= initialsquare.getPiece().getColor()) {
						finalsquare.setOccupied(true);

						//captured piece
						Piece tmpPiece = finalsquare.getPiece();

						finalsquare.setPiece(initialsquare.getPiece());
						initialsquare.setOccupied(false);

						if (InputValidator.isTurnKingInCheck(Game
								.getKingPosition(Game.getTurn()))) {

							Game.getCapturesList().add(tmpPiece);
							Game.addNewStartingSquare(initialsquare);
							Game.addNewDestinationSquare(finalsquare);
							return;
						} else {
							initialsquare.setOccupied(true);
							initialsquare.setPiece(finalsquare.getPiece());
							finalsquare.setOccupied(true);
							finalsquare.setPiece(tmpPiece);
							throw new IllegalMoveException();
						}
					}
				} else if (finalsquare.getX() < initialsquare.getX()
						&& initialsquare.getPiece().getColor() == ChessColor.BLACK) {
					if (finalsquare.getPiece() != null && finalsquare.getPiece().getColor()
							!= initialsquare.getPiece().getColor()) {


						finalsquare.setOccupied(true);

						//captured piece
						Piece tmpPiece = finalsquare.getPiece();

						finalsquare.setPiece(initialsquare.getPiece());
						initialsquare.setOccupied(false);

						if (InputValidator.isTurnKingInCheck(Game
								.getKingPosition(Game.getTurn()))) {

							Game.getCapturesList().add(tmpPiece);
							Game.addNewStartingSquare(initialsquare);
							Game.addNewDestinationSquare(finalsquare);
							return;
						} else {
							initialsquare.setOccupied(true);
							initialsquare.setPiece(finalsquare.getPiece());
							finalsquare.setOccupied(true);
							finalsquare.setPiece(tmpPiece);
							throw new IllegalMoveException();
						}
					} //there is no piece inside the final square or
					//there is but is not capturable due to a pin or
					//due to being the same color as the pawn in initial square
				} //there is a discrepancy between initial-final square and actual pawn movement
			} //exit the function with break because there is
			//no pawn inside the initial square, thus the capture can't fisically happen
		}
		throw new IllegalMoveException();
	}

	private static boolean captureEnPassant(final Square initialsquare, final Square finalsquare)
			throws IllegalMoveException {
		if ((initialsquare.getPiece().getClass() == Pawn.class)) {
			if (Game.getBoard().getSquare(initialsquare.getX(), finalsquare.getY()).isOccupied()) {
				if (Game.getBoard().getSquare(initialsquare.getX(),
						finalsquare.getY()).getPiece().getClass()
						== Pawn.class && Game.getBoard().getSquare(initialsquare.getX(),
								finalsquare.getY()).getPiece().getColor()
						!= initialsquare.getPiece().getColor()
						&& isCapturableEnPassant(initialsquare, finalsquare)) {

					finalsquare.setOccupied(true);
					finalsquare.setPiece(initialsquare.getPiece());

					Piece tmpPiece = Game.getBoard()
							.getSquare(initialsquare.getX(), finalsquare.getY()).getPiece();
					Game.getBoard().getSquare(initialsquare.getX(), finalsquare.getY())
					.setOccupied(false);

					initialsquare.setOccupied(false);

					if (InputValidator.isTurnKingInCheck(Game.getKingPosition(Game.getTurn()))) {

						((Pawn) finalsquare.getPiece()).setHasMoved();
						Game.getCapturesList().add(Game.getBoard()
								.getSquare(initialsquare.getX(), finalsquare.getY())
								.getPiece());
						Game.addNewStartingSquare(initialsquare);
						Game.addNewDestinationSquare(finalsquare);
						return true;
					} else {
						initialsquare.setOccupied(true);
						initialsquare.setPiece(finalsquare.getPiece());
						finalsquare.setOccupied(false);
						Game.getBoard().getSquare(initialsquare.getX(), finalsquare.getY())
						.setOccupied(true);
						Game.getBoard().getSquare(initialsquare.getX(), finalsquare.getY())
						.setPiece(tmpPiece);

						//pinned piece
						throw new IllegalMoveException();
					}
				}
			}
		}
		return false;
	}

	private static boolean isCapturableEnPassant(final Square initialsquare, final Square finalsquare) {
		//if last move was a pawn push
		if (Game.getLatestDestinationSquare().getPiece().getClass() == Pawn.class) {
			//if the push was exactly a 2-push
			int previousMoveInitialX = Game.getLatestStartingSquare().getX();
			int previousMoveFinalX = Game.getLatestDestinationSquare().getX();

			if (Math.abs(previousMoveInitialX - previousMoveFinalX) == 2) {
				//if the capture happens exactly on the pawn that moved during the last turn

				return (Game.getLatestDestinationSquare().getY() == finalsquare.getY()
				&& Game.getLatestDestinationSquare().getX() == initialsquare.getX());
			}
		}

		return false;
	}

	public static void pieceMoveOrCapture(final Class<? extends Piece> piecetype,
			final Square startingsquare, final Square finalsquare, final boolean isCapture)
					throws IllegalMoveException {

		if (startingsquare == null) {
			throw new IllegalMoveException();
		}

		if (finalsquare.isOccupied()) {
			if (finalsquare.getPiece().getColor() != Game.getTurn() && isCapture) {
				swapIfNotInCheck(piecetype, startingsquare, finalsquare, isCapture);
				return;
			}
		} else {
			if (!isCapture) {
				swapIfNotInCheck(piecetype, startingsquare, finalsquare, isCapture);
				return;
			}
		}
		throw new IllegalMoveException();
	}

	private static void swapIfNotInCheck(final Class<? extends Piece> piecetype, final Square startingsquare,
			final Square finalsquare, final boolean isCapture)
			throws IllegalMoveException {

		Piece tmpPiece = null;

		finalsquare.setOccupied(true);

		if (isCapture) {
			tmpPiece = finalsquare.getPiece();
		}

		finalsquare.setPiece(startingsquare.getPiece());
		startingsquare.setOccupied(false);

		if (InputValidator.isTurnKingInCheck(Game.getKingPosition(Game.getTurn()))) {

			if (isCapture) {
				Game.getCapturesList().add(tmpPiece);
			}

			Game.addNewStartingSquare(startingsquare);
			Game.addNewDestinationSquare(finalsquare);

			finalsquare.getPiece().setHasMoved();

			if (piecetype == King.class) {
				Game.setKingPosition(Game.getTurn(), finalsquare);
			}

			return;
		} else {

			//we reset the board status to the previous move

			startingsquare.setOccupied(true);
			startingsquare.setPiece(finalsquare.getPiece());

			if (isCapture) {
				finalsquare.setOccupied(true);
				finalsquare.setPiece(tmpPiece);
			} else {
				finalsquare.setOccupied(false);
			}

			//it's a pinned piece
			throw new IllegalMoveException();
		}
	}

	public static boolean shortCastle(final ChessColor wantedColor)
			throws IllegalMoveException {
		if (wantedColor == ChessColor.WHITE) {
			Square kingInitialSquare = Game.getBoard().getSquare(0, ParseFiles.getFileIntFromChar('e'));

			if (kingInitialSquare.isOccupied() && kingInitialSquare.getPiece().getClass()
					== King.class && kingInitialSquare.getPiece().getColor() == wantedColor) {
				if (!((King) kingInitialSquare.getPiece()).getHasMoved()) {
				//make the king disappear

				kingInitialSquare.setOccupied(false);

				if (InputValidator.isTurnKingInCheck(kingInitialSquare)) {
				//there is an unmoved white king and it's not under check
			 		kingInitialSquare.setOccupied(true);
			 		kingInitialSquare.setPiece(new King(wantedColor));

			 		Square rookInitialSquare = Game.getBoard().getSquare(0, 0);

					if (rookInitialSquare.isOccupied()
							&& rookInitialSquare.getPiece().getClass() == Rook.class
							&& rookInitialSquare.getPiece().getColor() == wantedColor) {

						if (!((Rook) rookInitialSquare.getPiece()).getHasMoved()) {
						//there is an unmoved white rook


							if (!Game.getBoard().getSquare(0, 2).isOccupied()
									&& InputValidator.isTurnKingInCheck(Game
											.getBoard().getSquare(0, 2))) {
								if (!Game.getBoard().getSquare(0, 1).isOccupied()
										&& InputValidator.isTurnKingInCheck(Game
												.getBoard()
												.getSquare(0, 1))) {

									((King) kingInitialSquare.getPiece())
									.setHasMoved();

									((Rook) rookInitialSquare.getPiece())
									.setHasMoved();

									Game.getBoard().getSquare(0, 2)
									.setOccupied(true);

									Game.getBoard().getSquare(0, 2).setPiece(Game
											.getBoard().getSquare(0, 0)
											.getPiece());

									Game.getBoard().getSquare(0, 0)
									.setOccupied(false);

									Game.getBoard().getSquare(0, 1)
									.setOccupied(true);

									Game.getBoard().getSquare(0, 1)
									.setPiece(Game.getBoard()
											.getSquare(0, ParseFiles
											.getFileIntFromChar('e'))
											.getPiece());

									Game.getBoard().getSquare(0, ParseFiles
											.getFileIntFromChar('e'))
									.setOccupied(false);

									Game.setKingPosition(Game
											.getTurn(), Game.getBoard()
											.getSquare(0, 1));

									Game.addNewStartingSquare(Game
											.getBoard()
											.getSquare(0, ParseFiles
											.getFileIntFromChar('e')));

									Game.addNewDestinationSquare(Game
											.getBoard().getSquare(0, 2));

									Game.nextTurn();
									return true;
								}
							}
						}
					}
				 	} else {
				 		//make the piece appear again if we can't castle
				 		kingInitialSquare.setOccupied(true);
				 		kingInitialSquare.setPiece(new King(wantedColor));
				 	}
				}
			}
		} else {
			Square kingInitialSquare
			= Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('e'));

			if (kingInitialSquare.isOccupied()
					&& kingInitialSquare.getPiece().getClass() == King.class
					&& kingInitialSquare.getPiece().getColor() == wantedColor) {
				if (!((King) kingInitialSquare.getPiece()).getHasMoved()) {
				//make the king disappear

					kingInitialSquare.setOccupied(false);

				if (InputValidator.isTurnKingInCheck(kingInitialSquare)) {
				//there is an unmoved white king and it's not under check
					kingInitialSquare.setOccupied(true);
					kingInitialSquare.setPiece(new King(wantedColor));

			 		Square rookInitialSquare = Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, 0);

					if (rookInitialSquare.isOccupied()
							&& rookInitialSquare.getPiece().getClass() == Rook.class
							&& rookInitialSquare.getPiece().getColor() == wantedColor) {

						if (!((Rook) rookInitialSquare.getPiece()).getHasMoved()) {
						//there is an unmoved white rook

							if (!Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, 2)
									.isOccupied()
									&& InputValidator.isTurnKingInCheck(
											Game.getBoard()
											.getSquare(Board
											.CHESSBOARD_EDGE, 2))) {
								if (!Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, 1)
										.isOccupied()
										&& InputValidator
										.isTurnKingInCheck(Game.getBoard()
										.getSquare(Board.CHESSBOARD_EDGE, 1))) {

									((King) kingInitialSquare.getPiece())
									.setHasMoved();
									((Rook) rookInitialSquare.getPiece())
									.setHasMoved();

									Game.getBoard().getSquare(Board
										.CHESSBOARD_EDGE, 2).setOccupied(true);
									Game.getBoard().getSquare(Board
										.CHESSBOARD_EDGE, 2).setPiece(
										Game.getBoard().getSquare(Board
										.CHESSBOARD_EDGE, 0).getPiece());
									Game.getBoard().getSquare(Board
											.CHESSBOARD_EDGE, 0)
									.setOccupied(false);

									Game.getBoard().getSquare(Board
										.CHESSBOARD_EDGE, 1).setOccupied(true);
									Game.getBoard().getSquare(Board
										.CHESSBOARD_EDGE, 1).setPiece(Game
										.getBoard().getSquare(Board
										.CHESSBOARD_EDGE, ParseFiles
										.getFileIntFromChar('e')).getPiece());
									Game.getBoard().getSquare(Board.CHESSBOARD_EDGE,
										ParseFiles.getFileIntFromChar('e'))
										.setOccupied(false);

									Game.setKingPosition(Game.getTurn(), Game
										.getBoard().getSquare(Board
										.CHESSBOARD_EDGE, 1));
									Game.addNewStartingSquare(Game.getBoard()
										.getSquare(Board.CHESSBOARD_EDGE,
										ParseFiles.getFileIntFromChar('e')));
									Game.addNewDestinationSquare(Game.getBoard()
										.getSquare(Board.CHESSBOARD_EDGE, 2));

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

	public static boolean longCastle(final ChessColor wantedColor) throws IllegalMoveException {
		if (wantedColor == ChessColor.WHITE) {
			Square kingInitialSquare = Game.getBoard().getSquare(0, ParseFiles.getFileIntFromChar('e'));

			if (kingInitialSquare.isOccupied()
					&& kingInitialSquare.getPiece().getClass() == King.class
					&& kingInitialSquare.getPiece().getColor() == wantedColor) {
				if (!((King) kingInitialSquare.getPiece()).getHasMoved()) {
				//make the king disappear

				kingInitialSquare.setOccupied(false);

				if (InputValidator.isTurnKingInCheck(kingInitialSquare)) {
				//there is an unmoved white king and it's not under check
			 		kingInitialSquare.setOccupied(true);
			 		kingInitialSquare.setPiece(new King(wantedColor));

			 		Square rookInitialSquare = Game.getBoard()
			 			.getSquare(0, Board.CHESSBOARD_EDGE);

					if (rookInitialSquare.isOccupied()
						&& rookInitialSquare.getPiece().getClass() == Rook.class
						&& rookInitialSquare.getPiece().getColor() == wantedColor) {

						if (!((Rook) rookInitialSquare.getPiece()).getHasMoved()) {
						//there is an unmoved white rook

							if (!Game.getBoard().getSquare(0, ParseFiles
									.getFileIntFromChar('d')).isOccupied()
									&& InputValidator.isTurnKingInCheck(Game
										.getBoard().getSquare(0, ParseFiles
											.getFileIntFromChar('d')))) {
								if (!Game.getBoard().getSquare(0, ParseFiles
										.getFileIntFromChar('c')).isOccupied()
										&& InputValidator.isTurnKingInCheck(Game
										.getBoard().getSquare(0, ParseFiles
										.getFileIntFromChar('c')))) {
									if (!Game.getBoard().getSquare(0, ParseFiles
										.getFileIntFromChar('b'))
											.isOccupied()) {
										((King) kingInitialSquare.getPiece())
										.setHasMoved();
										((Rook) rookInitialSquare.getPiece())
										.setHasMoved();

										Game.getBoard().getSquare(0, ParseFiles
										.getFileIntFromChar('d'))
										.setOccupied(true);
										Game.getBoard().getSquare(0, ParseFiles
										.getFileIntFromChar('d')).setPiece(
											rookInitialSquare.getPiece());
										rookInitialSquare.setOccupied(false);

										Game.getBoard().getSquare(0, ParseFiles
										.getFileIntFromChar('c'))
										.setOccupied(true);
										Game.getBoard().getSquare(0, ParseFiles
										.getFileIntFromChar('c'))
										.setPiece(kingInitialSquare.getPiece());
										kingInitialSquare.setOccupied(false);

										Game.setKingPosition(Game.getTurn(), Game.getBoard().getSquare(0, ParseFiles.getFileIntFromChar('c')));
										Game.addNewStartingSquare(Game.getBoard().getSquare(0, ParseFiles.getFileIntFromChar('e')));
										Game.addNewDestinationSquare(Game.getBoard().getSquare(0, ParseFiles.getFileIntFromChar('c')));

										Game.nextTurn();
										return true;
									}
								}
							}
						}
					}
				 	} else {
				 		//make the piece appear again if we can't castle
				 		kingInitialSquare.setOccupied(true);
				 		kingInitialSquare.setPiece(new King(wantedColor));
				 	}
				}
			}
		} else {
			Square kingInitialSquare = Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('e'));

			if (kingInitialSquare.isOccupied() && kingInitialSquare.getPiece().getClass() == King.class && kingInitialSquare.getPiece().getColor() == wantedColor) {
				if (!((King) kingInitialSquare.getPiece()).getHasMoved()) {
				//make the king disappear

				kingInitialSquare.setOccupied(false);

				if (InputValidator.isTurnKingInCheck(kingInitialSquare)) {
				//there is an unmoved white king and it's not under check
			 		kingInitialSquare.setOccupied(true);
			 		kingInitialSquare.setPiece(new King(wantedColor));

			 		Square rookInitialSquare = Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, Board.CHESSBOARD_EDGE);

					if (rookInitialSquare.isOccupied() && rookInitialSquare.getPiece().getClass() == Rook.class
						&& rookInitialSquare.getPiece().getColor() == wantedColor) {

						if (!((Rook) rookInitialSquare.getPiece()).getHasMoved()) {
						//there is an unmoved white rook

							if (!Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('d')).isOccupied()
									&& InputValidator.isTurnKingInCheck(Game.getBoard().getSquare(
											Board.CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('d')))) {
								if (!Game.getBoard().getSquare(Board.CHESSBOARD_EDGE,
										ParseFiles.getFileIntFromChar('c')).isOccupied()
									&& InputValidator.isTurnKingInCheck(Game.getBoard().getSquare(
											Board.CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('c')))) {
									if (!Game.getBoard().getSquare(Board.CHESSBOARD_EDGE,
											ParseFiles.getFileIntFromChar('b')).isOccupied()) {

										((King) kingInitialSquare.getPiece()).setHasMoved();
										((Rook) rookInitialSquare.getPiece()).setHasMoved();

										Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('d')).setOccupied(true);
										Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('d')).setPiece(rookInitialSquare.getPiece());
										rookInitialSquare.setOccupied(false);

										Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('c')).setOccupied(true);
										Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('c')).setPiece(kingInitialSquare.getPiece());
										kingInitialSquare.setOccupied(false);

										Game.setKingPosition(Game.getTurn(), Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('c')));
										Game.addNewStartingSquare(Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('e')));
										Game.addNewDestinationSquare(Game.getBoard().getSquare(Board.CHESSBOARD_EDGE, ParseFiles.getFileIntFromChar('c')));

										Game.nextTurn();
										return true;
									}
								}
							}
						}
					}
				 	} else {
				 		//make the piece appear again if we can't castle
				 		kingInitialSquare.setOccupied(true);
				 		kingInitialSquare.setPiece(new King(wantedColor));
				 	}
			}
		}
	}
	 //can't castle
	return false;
	}
}

