package it.uniba.chess.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.chess.Board;
import it.uniba.chess.Game;
import it.uniba.chess.InputValidator;
import it.uniba.chess.Square;
import it.uniba.chess.pieces.Piece;
import it.uniba.chess.pieces.Bishop;
import it.uniba.chess.pieces.Pawn;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParseFiles;

public class PawnTest {
	
	@Test
    @DisplayName("Check successful pawn 1-square push")
    public void pawnOneSquarePushOKTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();
        LinkedList<Square> expectedPosition = new LinkedList<>();

        Pawn movedPawn = new Pawn(ChessColor.WHITE);
        movedPawn.setHasMoved();

        startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('e'), new Pawn(ChessColor.WHITE)));
        expectedPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('e'), movedPawn));
        
        Board expectedBoard = new Board(expectedPosition);
        Game.testGame(startingPosition);
        
        InputValidator.parseCommand("e4");
        assertEquals(expectedBoard, Game.getBoard());
    }
	
	@Test
    @DisplayName("Check successful pawn 2-squares push")
    public void pawnTwoSquarePushOKTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();
        LinkedList<Square> expectedPosition = new LinkedList<>();

        Pawn movedPawn = new Pawn(ChessColor.WHITE);
        movedPawn.setHasMoved();

        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('e'), new Pawn(ChessColor.WHITE)));
        expectedPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('e'), movedPawn));
        
        Board expectedBoard = new Board(expectedPosition);
        Game.testGame(startingPosition);
        
        InputValidator.parseCommand("e4");
        assertEquals(expectedBoard, Game.getBoard());
    }
	
	@Test
    @DisplayName("Check already moved pawn 2-squares push")
    public void pawnTwoSquarePushPieceMovedTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn movedPawn = new Pawn(ChessColor.WHITE);
        movedPawn.setHasMoved();

        startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('e'), movedPawn));
        //expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), movedPawn));
        Game.testGame(startingPosition);
        
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("e6");});
    }
	
	@Test
    @DisplayName("Check out of bounds white pawn push")
    public void pawnWhitePushOutOfBoundsTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawn = new Pawn(ChessColor.WHITE);

        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('f'), unmovedPawn));
        //expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), movedPawn));
        Game.testGame(startingPosition);
        
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("f1");});
    }
	
	@Test
    @DisplayName("Check out of bounds black pawn push")
    public void pawnBlackPushOutOfBoundsTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawn = new Pawn(ChessColor.BLACK);

        startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('f'), unmovedPawn));
        //expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), movedPawn));
        Game.testGame(startingPosition);
        Game.nextTurn();
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("f8");});
    }
	
	@Test
    @DisplayName("Check not found 1-square push")
    public void pawnOneSquarePushPieceNotFoundTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawn = new Pawn(ChessColor.WHITE);

        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('e'), unmovedPawn));
        Game.testGame(startingPosition);
        Game.nextTurn();
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("e5");});
    }
	
	@Test
    @DisplayName("Check wrong piece 1-square push")
    public void pawnOneSquarePushWrongPieceTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Rook unmovedRook = new Rook(ChessColor.WHITE);

        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('e'), unmovedRook));
        Game.testGame(startingPosition);
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("e3");});
    }
	
	@Test
    @DisplayName("Check wrong piece 2-square push")
    public void pawnTwoSquarePushWrongPieceTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Rook unmovedRook = new Rook(ChessColor.WHITE);

        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('e'), unmovedRook));
        //expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), movedPawn));
        Game.testGame(startingPosition);
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("e4");});
    }
	
	@Test
    @DisplayName("Check wrong color 2-square push")
    public void pawnTwoSquarePushWrongColorTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn wrongColorPawn = new Pawn(ChessColor.BLACK);
        //we cannot ever have a wrong colored AND unmoved pawn
        wrongColorPawn.setHasMoved();

        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('e'), wrongColorPawn));
        Game.testGame(startingPosition);
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("e4");});
    }
	
	@Test
    @DisplayName("Check correct color pawn behind wrong color pawn")
    public void pawnPushBehindWrongColorTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawn = new Pawn(ChessColor.WHITE);
        Pawn wrongColorPawn = new Pawn(ChessColor.BLACK);
        wrongColorPawn.setHasMoved();

        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('e'), unmovedPawn));
        startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('e'), wrongColorPawn));
        Game.testGame(startingPosition);
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("e4");});
    }
	
	@Test
    @DisplayName("Check correct color pawn in front of wrong color pawn")
    public void pawnPushInFrontOfWrongColorTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();
        LinkedList<Square> expectedPosition = new LinkedList<>();

        Pawn movedPawn = new Pawn(ChessColor.WHITE);
        movedPawn.setHasMoved();
        Pawn wrongColorPawn = new Pawn(ChessColor.BLACK);
        wrongColorPawn.setHasMoved();
        
        expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), movedPawn));
        expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('e'), wrongColorPawn));

        startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('e'), movedPawn));
        startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('e'), wrongColorPawn));
        
        Board expectedBoard = new Board(expectedPosition);
        Game.testGame(startingPosition);
        
        InputValidator.parseCommand("e5");
        assertEquals(expectedBoard, Game.getBoard());
    }
	
	@Test
	@DisplayName("Check doubled pawns")
	public void pawnPushDoubledOKTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();
        LinkedList<Square> expectedPosition = new LinkedList<>();

        Pawn movedPawn = new Pawn(ChessColor.WHITE);
        movedPawn.setHasMoved();

        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('e'), new Pawn(ChessColor.WHITE)));
        startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('e'), movedPawn));
        
        expectedPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('e'), new Pawn(ChessColor.WHITE)));
        expectedPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('e'), movedPawn));
        
        Board expectedBoard = new Board(expectedPosition);
        Game.testGame(startingPosition);
        
        InputValidator.parseCommand("e4");
        assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check pawn push when it should be a capture")
	public void pawnPushCaptureTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawn = new Pawn(ChessColor.WHITE);
        
        startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('e'), new Rook(ChessColor.BLACK)));
        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('e'), unmovedPawn));
        Game.testGame(startingPosition);
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("e4");});
	}

	@Test
	@DisplayName("Check pawn push when it is already occupied")
	public void pawnPushOccupiedTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawn = new Pawn(ChessColor.WHITE);
        
        startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('e'), new Rook(ChessColor.WHITE)));
        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('e'), unmovedPawn));
        Game.testGame(startingPosition);
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("e4");});
	}
	
	@Test
    @DisplayName("Check wrong piece trying a pawn push")
    public void pawnPushWrongPieceTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Rook fakePiece =  new Rook(ChessColor.WHITE);
        
        startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), fakePiece));
        Game.testGame(startingPosition);
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("d6");});
    }
	
	@Test
	@DisplayName("Check absolute-pinned pawn push")
	public void pawnPushAbsolutePinnedTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawn = new Pawn(ChessColor.WHITE);
        Bishop pinningBishop = new Bishop(ChessColor.BLACK);
        
        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), unmovedPawn));
        startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('c'), pinningBishop));
        Game.testGame(startingPosition);

        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("d3");});
	}
	
	@Test
	@DisplayName("Check white normal pawn capture")
	public void pawnWhiteCaptureNormalOKTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();
        LinkedList<Square> expectedPosition = new LinkedList<>();
        
        Pawn unmovedPawn = new Pawn(ChessColor.WHITE);
        Pawn movedPawn = new Pawn(ChessColor.WHITE);
        movedPawn.setHasMoved();
        
        Bishop pinningBishop = new Bishop(ChessColor.BLACK);

        ArrayList<Piece> expectedCaptures = new ArrayList<Piece>();
        expectedCaptures.add(pinningBishop);
        
        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), unmovedPawn));
        startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('c'), pinningBishop));
        
        expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('c'), movedPawn));
        
        Board expectedBoard = new Board(expectedPosition);
        Game.testGame(startingPosition);
        
        InputValidator.parseCommand("dxc3");

        assertAll("Board states and captures must be the same", 
        	() -> {
        			assertEquals(expectedBoard, Game.getBoard());
        			assertEquals(expectedCaptures, Game.getCapturesList());
        		}
        	);
	}
	
	@Test
	@DisplayName("Check black normal pawn capture")
	public void pawnBlackCaptureNormalOKTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();
        LinkedList<Square> expectedPosition = new LinkedList<>();
        
        Pawn unmovedPawn = new Pawn(ChessColor.BLACK);
        Pawn movedPawn = new Pawn(ChessColor.BLACK);
        movedPawn.setHasMoved();
        
        Bishop pinningBishop = new Bishop(ChessColor.WHITE);

        ArrayList<Piece> expectedCaptures = new ArrayList<Piece>();
        expectedCaptures.add(pinningBishop);
        
        startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), unmovedPawn));
        startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), pinningBishop));
        
        expectedPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), movedPawn));
        
        Board expectedBoard = new Board(expectedPosition);
        Game.testGame(startingPosition);
        Game.nextTurn();
        
        InputValidator.parseCommand("dxc6");

        assertAll("Board states and captures must be the same", 
        	() -> {
        			assertEquals(expectedBoard, Game.getBoard());
        			assertEquals(expectedCaptures, Game.getCapturesList());
        		}
        	);
	}
	
	@Test
	@DisplayName("Check en-passant pawn capture")
	public void pawnCaptureEnPassantOKTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();
        LinkedList<Square> expectedPosition = new LinkedList<>();
        
        Pawn movedPawn = new Pawn(ChessColor.WHITE);
        movedPawn.setHasMoved();
        
        Pawn enPassantCapturablePawn = new Pawn(ChessColor.BLACK);

        ArrayList<Piece> expectedCaptures = new ArrayList<Piece>();
        expectedCaptures.add(enPassantCapturablePawn);
        
        startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), movedPawn));
        startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('c'), enPassantCapturablePawn));
        
        expectedPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), movedPawn));
        
        Board expectedBoard = new Board(expectedPosition);
        Game.testGame(startingPosition);
        Game.nextTurn();
        
        InputValidator.parseCommand("c5");
        InputValidator.parseCommand("dxc6");

        assertAll("Board states and captures must be the same", 
        	() -> {
        			assertEquals(expectedBoard, Game.getBoard());
        			assertEquals(expectedCaptures, Game.getCapturesList());
        		}
        	);
	}
	
	@Test
	@DisplayName("Check absolute-pinned en-passant capture")
	public void pawnCaptureEnPassantPinnedTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();
        
        Pawn movedPawn = new Pawn(ChessColor.WHITE);
        movedPawn.setHasMoved();
        
        Rook pinningRook = new Rook(ChessColor.BLACK);
        pinningRook.setHasMoved();
        
        Pawn enPassantCapturablePawn = new Pawn(ChessColor.BLACK);
        
        startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), movedPawn));
        startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('c'), enPassantCapturablePawn));
        startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), pinningRook));  
        
        Game.testGame(startingPosition);
        
        //move the king to e5
        Game.getBoard().getSquare(4,3).setOccupied(true);
        Game.getBoard().getSquare(4,3).setPiece(Game.getBoard().getSquare(0, 3).getPiece());
        Game.getBoard().getSquare(0,3).setOccupied(false);
        Game.getBoard().getSquare(4,3).getPiece().setHasMoved();
        Game.setKingPosition(ChessColor.WHITE, Game.getBoard().getSquare(4,3));
        
        Game.nextTurn();
        
        InputValidator.parseCommand("c5");
       

        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("dxc6");});
	}
	
	@Test
    @DisplayName("Check piece not found normal capture")
    public void pawnCapturePieceNotFoundTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawn = new Pawn(ChessColor.WHITE);
        Rook uncapturableRook =  new Rook(ChessColor.BLACK);
        
        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), unmovedPawn));
        startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('b'), uncapturableRook));
        Game.testGame(startingPosition);
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("cxb4");});
    }
	
	@Test
    @DisplayName("Check abs(startY - endY) > 1 normal capture")
    public void pawnCaptureFileTooFarTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawn = new Pawn(ChessColor.WHITE);
        Rook uncapturableRook =  new Rook(ChessColor.BLACK);
        
        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), unmovedPawn));
        startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('b'), uncapturableRook));
        Game.testGame(startingPosition);
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("dxb3");});
    }
	
	@Test
    @DisplayName("Check wrong piece trying a pawn capture")
    public void pawnCaptureWrongPieceTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Rook fakePiece =  new Rook(ChessColor.WHITE);
        Pawn capturablePiece = new Pawn(ChessColor.BLACK);
        
        startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), fakePiece));
        startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), capturablePiece));
        Game.testGame(startingPosition);
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("dxc6");});
    }
	
	@Test
    @DisplayName("Check white pawn capture behind it")
    public void pawnCaptureWhiteBehindTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawn = new Pawn(ChessColor.WHITE);
        Bishop uncapturableBishop =  new Bishop(ChessColor.BLACK);
        
        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('f'), unmovedPawn));
        startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('g'), uncapturableBishop));
        Game.testGame(startingPosition);
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("fxg1");});
    }
	
	@Test
    @DisplayName("Check black pawn capture behind it")
    public void pawnCaptureBlackBehindTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn movedPawn = new Pawn(ChessColor.BLACK);
        movedPawn.setHasMoved();
        Bishop uncapturableBishop =  new Bishop(ChessColor.WHITE);
        
        startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), movedPawn));
        startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('b'), uncapturableBishop));
        Game.testGame(startingPosition);
        Game.nextTurn();
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("cxb6");});
    }
	
	@Test
    @DisplayName("Check normal capture with en-passant conditions")
    public void pawnCaptureNotEnpassantTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();
        LinkedList<Square> expectedPosition = new LinkedList<>();
        ArrayList<Piece> expectedCaptures = new ArrayList<>();
        
        Pawn movedPawn = new Pawn(ChessColor.BLACK);
        movedPawn.setHasMoved();
        Pawn unmovedPawn = new Pawn(ChessColor.BLACK);
        Rook capturableRook =  new Rook(ChessColor.WHITE);
        Pawn capturingPawn = new Pawn(ChessColor.WHITE);
        capturingPawn.setHasMoved();
        
        expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), movedPawn));
        expectedPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), capturingPawn));
        Board expectedBoard = new Board(expectedPosition);
        
        expectedCaptures.add(capturableRook);
        expectedCaptures.add(unmovedPawn);
        
        
        startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('b'), unmovedPawn));
        startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), capturableRook));
        startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), movedPawn));
        startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), capturingPawn));
        Game.testGame(startingPosition);
        Game.nextTurn();
        
        InputValidator.parseCommand("bxc6");
        InputValidator.parseCommand("dxc6");
        
        assertAll("Board states and captures must be the same", 
            	() -> {
            			assertEquals(expectedBoard, Game.getBoard());
            			assertEquals(expectedCaptures, Game.getCapturesList());
            		}
            	);
    }
	
	@Test
    @DisplayName("Check pawn trying en-passant on a non-pawn piece")
    public void pawnCaptureEnPassantWrongCapturedPieceTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn movedPawn = new Pawn(ChessColor.WHITE);
        movedPawn.setHasMoved();
        Rook uncapturableRook =  new Rook(ChessColor.BLACK);
        
        startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), movedPawn));
        startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('c'), uncapturableRook));
        Game.testGame(startingPosition);
        Game.nextTurn();
        
        InputValidator.parseCommand("Tc5");
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("dxc6");});
    }
	
	@Test
    @DisplayName("Check white en-passant on non-subsequent move with 2-square black push edge case")
    public void pawnCaptureEnPassantLatePawnPushEdgeCaseMoveTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawnOne = new Pawn(ChessColor.BLACK);
        Pawn unmovedPawnTwo = new Pawn(ChessColor.BLACK);      
        Pawn randomPawn = new Pawn(ChessColor.WHITE);
        Pawn capturingPawn = new Pawn(ChessColor.WHITE);
        
        startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('c'), unmovedPawnOne));
        startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('b'), unmovedPawnTwo));
        startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), capturingPawn));
        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('f'), randomPawn));
        
        Game.testGame(startingPosition);
        Game.nextTurn();
        
        InputValidator.parseCommand("c5");
        InputValidator.parseCommand("f3");
        //we don't capture so we lose the possibility of en-passant
        InputValidator.parseCommand("b5");
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("dxc6");});
    }
	
	@Test
    @DisplayName("Check white en-passant on non-subsequent move")
    public void pawnCaptureEnPassantLateMoveTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawn = new Pawn(ChessColor.BLACK);
        Rook randomRook = new Rook(ChessColor.BLACK);      
        Pawn randomPawn = new Pawn(ChessColor.WHITE);
        Pawn capturingPawn = new Pawn(ChessColor.WHITE);
        
        startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('c'), unmovedPawn));
        startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('b'), randomRook));
        startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), capturingPawn));
        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('f'), randomPawn));
        
        Game.testGame(startingPosition);
        Game.nextTurn();
        
        InputValidator.parseCommand("c5");
        InputValidator.parseCommand("f3");
        //we don't capture so we lose the possibility of en-passant
        InputValidator.parseCommand("Tb4");
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("dxc6");});
    }
	
	@Test
    @DisplayName("Check en-passant on doubled pawns and wrong rank")
    public void pawnCaptureEnPassantDoubledTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawn = new Pawn(ChessColor.BLACK);   
        Pawn uncapturableDoubledPawn = new Pawn(ChessColor.BLACK);
        Pawn capturingPawn = new Pawn(ChessColor.WHITE);
        
        startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('f'), unmovedPawn));
        startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('f'), uncapturableDoubledPawn));
        startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('g'), capturingPawn));
        
        Game.testGame(startingPosition);
        Game.nextTurn();
        
        InputValidator.parseCommand("f5");
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("gxf5e.p.");});
    }
	
	
	@Test
    @DisplayName("Check en-passant on own color")
    public void pawnCaptureEnPassantOwnColorTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();

        Pawn unmovedPawn = new Pawn(ChessColor.BLACK);   
        Pawn uncapturableDoubledPawn = new Pawn(ChessColor.WHITE);
        Pawn capturingPawn = new Pawn(ChessColor.WHITE);
        
        startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('f'), unmovedPawn));
        startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('f'), uncapturableDoubledPawn));
        startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('g'), capturingPawn));
        
        Game.testGame(startingPosition);
        Game.nextTurn();
        
        InputValidator.parseCommand("f5");
        
        assertThrows(IllegalMoveException.class, () -> {InputValidator.parseCommand("gxf5e.p.");});
    }
	
	@Test
	@DisplayName("Check en-passant pawn capture with e.p. grammar ")
	public void pawnCaptureEnPassantWithWhiteTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();
        LinkedList<Square> expectedPosition = new LinkedList<>();
        
        Pawn movedPawn = new Pawn(ChessColor.WHITE);
        movedPawn.setHasMoved();
        
        Pawn enPassantCapturablePawn = new Pawn(ChessColor.BLACK);

        ArrayList<Piece> expectedCaptures = new ArrayList<Piece>();
        expectedCaptures.add(enPassantCapturablePawn);
        
        startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), movedPawn));
        startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('c'), enPassantCapturablePawn));
        
        expectedPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), movedPawn));
        
        Board expectedBoard = new Board(expectedPosition);
        Game.testGame(startingPosition);
        Game.nextTurn();
        
        InputValidator.parseCommand("c5");
        InputValidator.parseCommand("dxc6e.p.");

       assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check en-passant pawn capture with e.p. grammar with black")
	public void pawnCaptureEnPassantWithBlackTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();
        LinkedList<Square> expectedPosition = new LinkedList<>();
        
        Pawn movedPawn = new Pawn(ChessColor.BLACK);
        movedPawn.setHasMoved();
        
        Pawn enPassantCapturablePawn = new Pawn(ChessColor.WHITE);

        ArrayList<Piece> expectedCaptures = new ArrayList<Piece>();
        expectedCaptures.add(enPassantCapturablePawn);
        
        startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('d'), movedPawn));
        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('c'), enPassantCapturablePawn));
        
        expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('c'), movedPawn));
        
        Board expectedBoard = new Board(expectedPosition);
        Game.testGame(startingPosition);
        
        InputValidator.parseCommand("c4");
        InputValidator.parseCommand("dxc3e.p.");

       assertEquals(expectedBoard, Game.getBoard());
	}
	
	
	@Test
    @DisplayName("Check if a string like 'xe3' is accepted")
    public void pawnCaptureErrorTest() throws Exception{
        Game.startGame();
        assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("xe3"));
    }
	
	@Test
    @DisplayName("Check if a string like 'e3e.p.' is accepted")
    public void pawnEnPassantErrorTest() throws Exception{
        Game.startGame();
        assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("e3e.p."));
    }
	
	@Test
    @DisplayName("Check if a string like 'e3xd4' is accepted")
    public void pawnRankDisambiguationErrorTest() throws Exception{
        Game.startGame();
        assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("3xd4"));
    }
	
	@Test
    @DisplayName("no x in pawn capture")
    public void pawnXCharachterMissingErrorTest() throws Exception{
        Game.startGame();
        assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("ed4"));
    }
}
