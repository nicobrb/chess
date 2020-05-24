package it.uniba.chess.test;



import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.chess.Board;
import it.uniba.chess.Game;
import it.uniba.chess.InputValidator;
import it.uniba.chess.Square;
import it.uniba.chess.pieces.Knight;
import it.uniba.chess.pieces.Pawn;
import it.uniba.chess.pieces.Piece;
import it.uniba.chess.pieces.Queen;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.pieces.Bishop;
import it.uniba.chess.pieces.King;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParseFiles;
import it.uniba.chess.utils.GameStatus;



public class InputValidatorTest {
	private final PrintStream stdOut = System.out;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp(){
		Game.setStatus(GameStatus.INACTIVE);
	}
	
	@AfterEach
	public void tearDown() {
	
		System.setOut(stdOut);
	}
	
	
	@Test
	@DisplayName("Check 'board' command with inactive game")
	public void boardInactiveGameTest() throws Exception{
		
		outContent.reset();
		System.setOut(new PrintStream(outContent, true, "UTF-8"));
		
		String expectedOutput = "Gioco non ancora avviato.\n";
		InputValidator.parseCommand("board");
		assertEquals(expectedOutput, outContent.toString("UTF-8"));
	}
	
	@Test
	@DisplayName("Check 'board' command with active game")
	public void boardActiveGameTest() throws Exception{	
		outContent.reset();
		System.setOut(new PrintStream(outContent, true, "UTF-8"));

		String expectedOutput = 
				"   a   b   c   d   e   f   g   h \n" + 
				"  --------------------------------\n" + 
				"8 | ♜ | ♞ | ♝ | ♛ | ♚ | ♝ | ♞ | ♜ | 8\n" + 
				"  --------------------------------\n" + 
				"7 | ♟ | ♟ | ♟ | ♟ | ♟ | ♟ | ♟ | ♟ | 7\n" + 
				"  --------------------------------\n" + 
				"6 |   |   |   |   |   |   |   |   | 6\n" + 
				"  --------------------------------\n" + 
				"5 |   |   |   |   |   |   |   |   | 5\n" + 
				"  --------------------------------\n" + 
				"4 |   |   |   |   |   |   |   |   | 4\n" + 
				"  --------------------------------\n" + 
				"3 |   |   |   |   |   |   |   |   | 3\n" + 
				"  --------------------------------\n" + 
				"2 | ♙ | ♙ | ♙ | ♙ | ♙ | ♙ | ♙ | ♙ | 2\n" + 
				"  --------------------------------\n" + 
				"1 | ♖ | ♘ | ♗ | ♕ | ♔ | ♗ | ♘ | ♖ | 1\n" + 
				"  --------------------------------\n" + 
				"   a   b   c   d   e   f   g   h \n";

		Game.startGame();
		//System.out.print(expectedOutput);
		InputValidator.parseCommand("board");
		assertEquals(expectedOutput, outContent.toString("UTF-8"));
	}
	@Test
    @DisplayName("Test help menu")
    public void helpMenuTest() throws Exception{
        outContent.reset();
        System.setOut(new PrintStream(outContent, true, "UTF-8"));

        String expectedOutput = 
                "help: questo menu\n" + 
                "board: mostra la scacchiera alla posizione attuale\n" + 
                "play: inizia una nuova partita (anche a partità già iniziata)\n" + 
                "quit: termina l'applicazione\n" + 
                "captures: mostra il materiale catturato da ogni colore\n" + 
                "moves: mostra la lista delle mosse giocate durante la partita\n";

        Game.startGame();
        InputValidator.parseCommand("help");
        assertEquals(expectedOutput, outContent.toString("UTF-8"));
    }

	@Test
    @DisplayName("Test grammatically wrong move")
    public void grammaticallyWrongMoveTest() throws Exception{
        outContent.reset();
        System.setOut(new PrintStream(outContent, true, "UTF-8"));

        String expectedOutput = 
        	"Comando non riconosciuto. " +
        	"Scrivi help per la lista dei comandi.\n";

        Game.startGame();
        InputValidator.parseCommand("Se3");
        assertEquals(expectedOutput, outContent.toString("UTF-8"));
    }
	
	@Test
    @DisplayName("0-0 but game is inactive")
    public void inactiveShortCastlingTest() throws Exception{
        outContent.reset();
        System.setOut(new PrintStream(outContent, true, "UTF-8"));

        String expectedOutput = "Gioco non ancora avviato.\n";
        InputValidator.parseCommand("0-0");
        assertEquals(expectedOutput, outContent.toString("UTF-8"));
    }
	@Test
    @DisplayName("0-0-0 but game is inactive")
    public void inactiveLongCastlingTest() throws Exception{
        outContent.reset();
        System.setOut(new PrintStream(outContent, true, "UTF-8"));

        String expectedOutput = "Gioco non ancora avviato.\n";
        InputValidator.parseCommand("0-0-0");
        assertEquals(expectedOutput, outContent.toString("UTF-8"));
    }
	@Test
    @DisplayName("move during inactive game test")
    public void inactiveGameMoveTest() throws Exception{
        outContent.reset();
        System.setOut(new PrintStream(outContent, true, "UTF-8"));

        String expectedOutput = 
        	"Comando non riconosciuto. " +
        	"Scrivi help per la lista dei comandi.\n";

        InputValidator.parseCommand("e4");
        assertEquals(expectedOutput, outContent.toString("UTF-8"));
    }
	@Test
	@DisplayName("Check white knight movement")
	public void knightWhiteMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.WHITE);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('b'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Cb3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black knight movement")
	public void knightBlackMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.BLACK);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('b'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Cb3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	
	@Test
	@DisplayName("Check printing of moves")
	public void movesPrintTest() throws Exception{
		
		outContent.reset();
		System.setOut(new PrintStream(outContent, true, "UTF-8"));
		
		Game.startGame();
		InputValidator.parseCommand("e4");
		InputValidator.parseCommand("e5");
		InputValidator.parseCommand("Cf3");
		InputValidator.parseCommand("Cbc6");
		InputValidator.parseCommand("Ac4");
		InputValidator.parseCommand("Ac5");
		
		InputValidator.parseCommand("moves");
		
		String expectedOutput =
				"\n" +
				"1. e4 e5 \n" + 
				"2. Cf3 Cbc6 \n" + 
				"3. Ac4 Ac5 \n";
		assertEquals(expectedOutput, outContent.toString("UTF-8"));
	}
	
	@Test
	@DisplayName("Check captures (Scandinavian defense)")
	public void capturesPrintTest() throws Exception{
		
		outContent.reset();
		PrintStream ciccio = new PrintStream(outContent, true, "UTF-8");
		System.setOut(ciccio);
		
		Game.startGame();
		InputValidator.parseCommand("e4");
		InputValidator.parseCommand("d5");
		InputValidator.parseCommand("exd5");
		InputValidator.parseCommand("Dxd5");
		InputValidator.parseCommand("Cc3");
		InputValidator.parseCommand("Cf6");
		InputValidator.parseCommand("Cxd5");
		InputValidator.parseCommand("Cxd5");
		
		InputValidator.parseCommand("captures");
		
		String expectedOutput = 
				"Materiale del bianco: ♟♛\n" + 
				"Materiale del nero: ♙♘\n";
		assertEquals(expectedOutput, outContent.toString("UTF-8"));
	
	}
	
	@Test
	@DisplayName("Test illegal pawn move")
	public void pawnIllegalMoveTest() throws Exception{
		Game.startGame();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("e8"));
		//assertEquals("Mossa illegale", illegalmove.getMessage());
	}
	@Test
	@DisplayName("Check white Rook movement")
	public void rookWhiteMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Ta5");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Rook movement")
	public void rookBlackMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Ta5");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check white Rook capture")
	public void rookWhiteCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Txa5");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Rook capture")
	public void rookBlackCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Txa5");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check white Bishop movement")
	public void bishopWhiteMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), movedBishop));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Ag8");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Bishop movement")
	public void bishopBlackMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), movedBishop));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Ag8");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check white Queen movement")
	public void queenWhiteMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Queen movedQueen = new Queen(ChessColor.WHITE);
		movedQueen.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Queen(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), movedQueen));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Da5");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Queen movement")
	public void queenBlackMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Queen movedQueen = new Queen(ChessColor.BLACK);
		movedQueen.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Queen(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), movedQueen));;
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Da5");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check white Queen movement")
	public void queenWhiteDiagonalMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Queen movedQueen = new Queen(ChessColor.WHITE);
		movedQueen.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Queen(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('f'), movedQueen));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Df3");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Queen movement")
	public void queenBlackDiagonalMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Queen movedQueen = new Queen(ChessColor.BLACK);
		movedQueen.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Queen(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('f'), movedQueen));;
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Df3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check white Queen movement")
	public void queenWhiteIllegalMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();

		Queen movedQueen = new Queen(ChessColor.WHITE);
		movedQueen.setHasMoved();
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Queen(ChessColor.WHITE)));		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Dd5"));
	}

	@Test
	@DisplayName("Check black Queen movement")
	public void queenBlackIllegalMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Queen movedQueen = new Queen(ChessColor.BLACK);
		movedQueen.setHasMoved();
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Queen(ChessColor.BLACK)));		
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Dd5"));
	}
	@Test
	@DisplayName("Check white knight movement")
	public void knightWhiteMovementWithFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.WHITE);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('b'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Ccb3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black knight movement")
	public void knightBlackMovementWithFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.BLACK);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('b'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Ccb3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	@Test
	@DisplayName("Check white knight movement")
	public void knightWhiteMovementWithOneRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.WHITE);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('b'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("C5b3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black knight movement")
	public void knightBlackMovementWithOneRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.BLACK);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('b'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("C5b3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check white knight movement")
	public void knightWhiteMovementWithOneFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.WHITE);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('a'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Cca4");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black knight movement")
	public void knightBlackMovementWithOneFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.BLACK);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('a'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Cca4");
		assertEquals(expectedBoard, Game.getBoard());
		
	
	}
	@Test
	@DisplayName("Check white knight movement")
	public void knightWhiteMovementWithRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.WHITE);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('a'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("C5a4");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black knight movement")
	public void knightBlackMovementWithRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Knight movedKnight = new Knight(ChessColor.BLACK);
		movedKnight.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('a'), movedKnight));
		Board expectedBoard = new Board(expectedPosition);
		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("C5a4");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check white Knight movement")
	public void knightWhiteIllegalMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();

		Knight movedKnight = new Knight(ChessColor.WHITE);
		movedKnight.setHasMoved();
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Knight(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('f'), new Knight(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("C5e3"));
	}

	@Test
	@DisplayName("Check black Knight movement")
	public void knightBlackIllegalMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();

		Knight movedKnight = new Knight(ChessColor.BLACK);
		movedKnight.setHasMoved();
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Knight(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('f'), new Knight(ChessColor.BLACK)));
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("C5e3"));
	}

	@Test
	@DisplayName("Check white Bishop movement when rank disambiguation is passed")
	public void bishopWhiteMovementWithRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), movedBishop));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("A5g8");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Bishop movement when rank disambiguation is passed")
	public void bishopBlackMovementwithRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), movedBishop));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("A5g8");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	
	@Test
	@DisplayName("Check white Bishop movement when file disambiguation is passed")
	public void bishopWhiteMovementWithFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), movedBishop));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Adg8");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black Bishop movement when file disambiguation is passed")
	public void bishopBlackMovementwithFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("7"), ParseFiles.getFileIntFromChar('g'), movedBishop));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Adg8");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	
	@Test
	@DisplayName("Check white Bishop movement when wrong rank disambiguation is passed")
	public void bishopWhiteMovementWithWrongRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));		
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("A6g8"));
	}

	@Test
	@DisplayName("Check black Bishop movement when wrong rank disambiguation is passed")
	public void bishopBlackMovementwithWrongRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("A6g8"));
	
	}
	@Test
	@DisplayName("Check white Bishop movement when wrong file disambiguation is passed")
	public void bishopWhiteMovementWithWrongFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));		
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Abg8"));
	}

	@Test
	@DisplayName("Check black Bishop movement when wrong file disambiguation is passed")
	public void bishopBlackMovementwithWrongFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));		
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Abg8"));
	
	}
	@Test
	@DisplayName("Check white rook movement when rank disambiguation is passed")
	public void rookWhiteMovementWithRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("T5e5");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black rook movement when rank disambiguation is passed")
	public void rookBlackMovementwithRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("T5e5");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	@Test
	@DisplayName("Check white rook movement when file disambiguation is passed")
	public void rookWhiteMovementWithFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('d'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Tdd1");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black rook movement when file disambiguation is passed")
	public void rookBlackMovementwithFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('d'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Tdd1");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	@Test
	@DisplayName("Check white rook movement when rank disambiguation is passed but is different from the final rank")
	public void rookWhiteMovementWithDifferentRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('d'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("T5d1");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black rook movement when rank disambiguation is passed but is different from the final rank")
	public void rookBlackMovementwithDifferentRankTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('d'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("T5d1");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	
	@Test
	@DisplayName("Check white rook movement when rank disambiguation is passed but is different from the final rank no2")
	public void rookWhiteMovementWithDifferentRank2Test() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("T5d7");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black rook movement when rank disambiguation is passed but is different from the final rank no2")
	public void rookBlackMovementwithDifferentRank2Test() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("T5d7");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	
	@Test
	@DisplayName("Check white rook movement when file disambiguation is passed but is different from the final file")
	public void rookWhiteMovementWithDifferentFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Tde5");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black rook movement when file disambiguation is passed but is different from the final file")
	public void rookBlackMovementwithDifferentFileTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Tde5");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	
	@Test
	@DisplayName("Check white rook movement when file disambiguation is passed but is different from the final file no2")
	public void rookWhiteMovementWithDifferentFile2Test() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Tdc5");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("Check black rook movement when file disambiguation is passed but is different from the final file no2")
	public void rookBlackMovementwithDifferentFile2Test() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));		
		expectedPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), movedRook));
		Board expectedBoard = new Board(expectedPosition);
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Tdc5");
		assertEquals(expectedBoard, Game.getBoard());
	
	}
	
	@Test
	@DisplayName("Check white rook movement when file disambiguation is passed but there is a piece inbetween")
	public void rookWhiteMovementWithDifferentFileErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Pawn(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdb5"));
	}

	@Test
	@DisplayName("Check black rook movement when file disambiguation is passed but there is a piece inbetween")
	public void rookBlackMovementwithDifferentFileErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Pawn(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdb5"));
	
	}
	
	@Test
	@DisplayName("Check white rook movement when rank disambiguation is passed but there is no Rook there")
	public void rookWhiteMovementWithDifferentRankNoRookErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T3b5"));
	}

	@Test
	@DisplayName("Check black rook movement when rank disambiguation is passed but there is no Rook there")
	public void rookBlackMovementwithDifferentRankNoRookErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T3b5"));
	
	}
	
	@Test
	@DisplayName("Check white rook movement when file disambiguation is passed but there is no Rook there")
	public void rookWhiteMovementWithDifferentFileNoRookErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tac5"));
	}

	@Test
	@DisplayName("Check black rook movement when file disambiguation is passed but there is no Rook there")
	public void rookBlackMovementwithDifferentFileNoRookErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tac5"));
	
	}
	
	@Test
	@DisplayName("There is a rook but is black and not white")
	public void wrongRookWhiteColorYErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
	
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdb5"));
	}

	@Test
	@DisplayName("There is a rook but is white and not black")
	public void wrongRookBlackColorYErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdb5"));
	}
	
	@Test
	@DisplayName("There is a white piece but it's not a rook")
	public void itIsNotAWhiteRookYErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));
	
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdb5"));
	}

	@Test
	@DisplayName("There is a black piece but it's not a rook")
	public void itIsNotABlackRookYErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdb5"));
	}
	@Test
	@DisplayName("There is a rook but is black and not white")
	public void wrongRookWhiteColorXErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
	
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d3"));
	}

	@Test
	@DisplayName("There is a rook but is white and not black")
	public void wrongRookBlackColorXErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d3"));
	}
	
	@Test
	@DisplayName("There is a white piece but it's not a rook")
	public void itIsNotAWhiteRookXErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Bishop movedBishop = new Bishop(ChessColor.WHITE);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.WHITE)));
	
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d3"));
	}

	@Test
	@DisplayName("There is a black piece but it's not a rook")
	public void itIsNotABlackRookXErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Bishop movedBishop = new Bishop(ChessColor.BLACK);
		movedBishop.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Bishop(ChessColor.BLACK)));
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d3"));
	}
	
	@Test
	@DisplayName("White Rook no1 but final square is occupied")
	public void rookWhitePieceInbetweenLowerErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('d'), new Pawn(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d1"));
	}

	@Test
	@DisplayName("Black Rook no1 but final square is occupied")
	public void rookBlackPieceInbetweenLowerErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('d'), new Pawn(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d1"));
	}
	@Test
	@DisplayName("White Rook no2 but final square is occupied")
	public void rookWhitePieceInbetweenUpperErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('d'), new Pawn(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d8"));
	}

	@Test
	@DisplayName("Black Rook no2 but final square is occupied")
	public void rookBlackPieceInbetweenUpperErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('d'), new Pawn(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T5d8"));
	}
	
	@Test
	@DisplayName("White Rook no2 but final square is occupied")
	public void rookWhitePieceInbetweenWesternErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), new Pawn(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tda5"));
	}

	@Test
	@DisplayName("Black Rook no3 but final square is occupied")
	public void rookBlackPieceInbetweeWesternErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('a'), new Pawn(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tda5"));
	}
	
	@Test
	@DisplayName("White Rook no3 but final square is occupied")
	public void rookWhitePieceInbetweenEasternErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), new Pawn(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdh5"));
	}

	@Test
	@DisplayName("Black Rook no2 but final square is occupied")
	public void rookBlackPieceInbetweenEasternErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('e'), new Pawn(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdh5"));
	}
	
	@Test
	@DisplayName("no white rooks in this rank ")
	public void noWhiteRooksXTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T2d2"));
	}

	@Test
	@DisplayName("no black rooks in this rank")
	public void noBlackRooksXTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();

	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T2d2"));
	}
	
	@Test
	@DisplayName("no white rooks in this rank ")
	public void noWhiteRooksYTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdd2"));
	}

	@Test
	@DisplayName("no black rooks in this rank")
	public void noBlackRooksYTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();

	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tdd2"));
	}
	@Test
	@DisplayName("Two White Rooks One Rank")
	public void whiteRooksWithXDisambiguationErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('g'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T3d3"));
	}

	@Test
	@DisplayName("two Black Rooks One Rank")
	public void blackRooksWithXDisambiguationErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('g'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("T3d3"));
	}
	
	@Test
	@DisplayName("Two White Rooks One File")
	public void whiteRooksWithYDisambiguationErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('b'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('b'), new Rook(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tbb5"));
	}

	@Test
	@DisplayName("two Black Rooks One File")
	public void blackRooksWithYDisambiguationErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('b'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('b'), new Rook(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Tbb5"));
	}
	@Test
	@DisplayName("Two White Rooks ")
	public void whiteRooksErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		Rook movedRook = new Rook(ChessColor.WHITE);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('g'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.WHITE)));	
		
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Td3"));
	}

	@Test
	@DisplayName("two Black Rooks")
	public void blackRooksErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
	
		
		Rook movedRook = new Rook(ChessColor.BLACK);
		movedRook.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('g'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Rook(ChessColor.BLACK)));	
	
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Td3"));
	}
	
	@Test
	@DisplayName("White Bishop puts his king in check thus it's illegal move")
	public void whitePinTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("3"),ParseFiles.getFileIntFromChar('e'), new Bishop(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("6"),ParseFiles.getFileIntFromChar('e'), new Rook(ChessColor.BLACK)));
		
		Game.testGame(startingPosition);

		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Ad3"));
		
	}
	
	@Test
	@DisplayName("Black Bishop puts his king in check thus it's illegal move")
	public void blackPinTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("4"),ParseFiles.getFileIntFromChar('e'), new Bishop(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"),ParseFiles.getFileIntFromChar('e'), new Rook(ChessColor.WHITE)));
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Ad6"));
		
	}
	
	@Test
	@DisplayName("Check white King movement")
	public void kingWhiteMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		King movedKing = new King(ChessColor.WHITE);
		movedKing.setHasMoved();
		
		expectedPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), movedKing));
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(0, 3).setOccupied(false);
		Game.setKingPosition(ChessColor.WHITE, Game.getBoard().getSquare(1, 4));
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Rd2");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black King movement")
	public void kingBlackMovementTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		King movedKing = new King(ChessColor.BLACK);
		movedKing.setHasMoved();
		
		expectedPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), movedKing));
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(7, 3).setOccupied(false);
		Game.setKingPosition(ChessColor.BLACK, Game.getBoard().getSquare(6, 4));
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Rd7");
		assertEquals(expectedBoard, Game.getBoard());
	}

	@Test
	@DisplayName("White Bishop puts his king in check while capturing thus it's illegal move")
	public void whitePinCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("3"),ParseFiles.getFileIntFromChar('e'), new Bishop(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("6"),ParseFiles.getFileIntFromChar('e'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"),ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);

		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Axd3"));
		
	}
	
	@Test
	@DisplayName("Black Bishop puts his king in check thus while capturingit's illegal move")
	public void blackPinCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("4"),ParseFiles.getFileIntFromChar('e'), new Bishop(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"),ParseFiles.getFileIntFromChar('e'), new Rook(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("5"),ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Axd6"));
		
	}
	
	@Test
	@DisplayName("Check white King capture")
	public void kingWhiteCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		King movedKing = new King(ChessColor.WHITE);
		movedKing.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), new King(ChessColor.BLACK)));
		expectedPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), movedKing));
		
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(0, 3).setOccupied(false);
		Game.setKingPosition(ChessColor.WHITE, Game.getBoard().getSquare(1, 4));
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("Rxd2");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("Check black King capture")
	public void kingBlackCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		King movedKing = new King(ChessColor.BLACK);
		movedKing.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), new King(ChessColor.WHITE)));
		expectedPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), movedKing));
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(7, 3).setOccupied(false);
		Game.setKingPosition(ChessColor.BLACK, Game.getBoard().getSquare(6, 4));
		
		Game.testGame(startingPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Rxd7");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("White King can't capture a White Rook")
	public void whiteKingVsBlackKingCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd2"));
		
	}
	
	@Test
	@DisplayName("Black King can't capture a Black Rook")
	public void blackKingVsWhiteKingCaptureTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);

		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd7"));
		
	}
	
	@Test
	@DisplayName("White King can't capture that Rook because is protected by a knight")
	public void whiteKingCaptureDefendedByKnightTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd2"));
		
	}
	
	@Test
	@DisplayName("Black King can't capture that Rook because is protected by a knight")
	public void blackKingCaptureDefendedByKnightTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('c'), new Knight(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);

		Game.nextTurn();

		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd7"));
		
	}
	

	@Test
	@DisplayName("White King can't capture that Rook because is protected by a pawn")
	public void whiteKingCaptureDefendedByPawnTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('c'), new Pawn(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd2"));
		
	}
	
	@Test
	@DisplayName("Black King can't capture that Rook because is protected by a pawn")
	public void blackKingCaptureDefendedByPawnTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), new Pawn(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);

		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd7"));
		
	}
	
	@Test
	@DisplayName("White King can't capture that Rook because is protected by a King")
	public void whiteKingCaptureDefendedByKingTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('c'), new King(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.BLACK)));
		Game.testGame(startingPosition);
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd2"));
		
	}
	
	@Test
	@DisplayName("Black King can't capture that Rook because is protected by a King")
	public void blackKingCaptureDefendedByKingTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), new King(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("6"), ParseFiles.getFileIntFromChar('d'), new Rook(ChessColor.WHITE)));
		Game.testGame(startingPosition);

		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rxd7"));
		
	}
	
	@Test
	@DisplayName("More than one white Queen, illegal move")
	public void queenWhiteIllegalMoveTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Queen(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('b'), new Queen(ChessColor.WHITE)));
		
		Game.testGame(startingPosition);
		
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Dd3"));
	}
	
	@Test
	@DisplayName("More than one black Queen, illegal move")
	public void queenBlackIllegalMoveTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Queen(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("4"), ParseFiles.getFileIntFromChar('b'), new Queen(ChessColor.BLACK)));
		
		Game.testGame(startingPosition);
		
		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Dd3"));
	}
	
	@Test
	@DisplayName("east Check test with white")
	public void eastWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Queen whiteQueen = new Queen(ChessColor.WHITE);
		Rook whiteRook = new Rook(ChessColor.WHITE);
		
		whiteQueen.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Queen(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('f'), new Rook(ChessColor.WHITE)));
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('d'), whiteQueen));
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('f'), whiteRook));
		
		Game.testGame(startingPosition);
		Board expectedBoard = new Board(expectedPosition);
		
		InputValidator.parseCommand("Dd3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("east Check test with white")
	public void eastBlackTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		Rook blackRook = new Rook(ChessColor.BLACK);
		Queen blackQueen = new Queen(ChessColor.BLACK);
		
		blackQueen.setHasMoved();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('g'), new Rook(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('a'), new Queen(ChessColor.BLACK)));
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('g'), blackRook));
		expectedPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('d'), blackQueen));
		
		Game.testGame(startingPosition);
		Board expectedBoard = new Board(expectedPosition);
		Game.nextTurn();
		InputValidator.parseCommand("Dd3");
		assertEquals(expectedBoard, Game.getBoard());
	}
	
	@Test
	@DisplayName("White King can't move because there are 2 pawns defending")
	public void whiteKingMoveDefendedByPawnsTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('c'), new Pawn(ChessColor.BLACK)));
		startingPosition.add(new Square(Integer.parseInt("2"), ParseFiles.getFileIntFromChar('d'), new Pawn(ChessColor.BLACK)));
		Game.testGame(startingPosition);
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rd2"));
		
	}
	
	@Test
	@DisplayName("Black King can't move because there are 2 pawns defending\"")
	public void blackKingMoveDefendedByPawnsTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('c'), new Pawn(ChessColor.WHITE)));
		startingPosition.add(new Square(Integer.parseInt("5"), ParseFiles.getFileIntFromChar('e'), new Pawn(ChessColor.WHITE)));
		Game.testGame(startingPosition);

		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("Rd7"));
		
	}
	
	@Test
	@DisplayName("White King disambiguation")
	public void whiteKingDisambiguationErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();

		Game.testGame(startingPosition);
	
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("R1d2"));
		
	}
	
	@Test
	@DisplayName("Black King disambiguation")
	public void blackKingDisambiguationErrorTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		Game.testGame(startingPosition);

		Game.nextTurn();
		assertThrows(IllegalMoveException.class, () -> InputValidator.parseCommand("R8d7"));
		
	}
	
	@Test
	@DisplayName("Check knight (generic) capture")
	public void pawnBlackCaptureNormalOKTest() throws Exception{
        LinkedList<Square> startingPosition = new LinkedList<>();
        LinkedList<Square> expectedPosition = new LinkedList<>();
        
        Knight capturingKnight = new Knight(ChessColor.WHITE);
        capturingKnight.setHasMoved();
        Queen capturableQueen = new Queen(ChessColor.BLACK);
        capturableQueen.setHasMoved();

        ArrayList<Piece> expectedCaptures = new ArrayList<Piece>();
        expectedCaptures.add(capturableQueen);
        
        startingPosition.add(new Square(Integer.parseInt("1"), ParseFiles.getFileIntFromChar('d'), capturingKnight));
        startingPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('c'), capturableQueen));
        
        expectedPosition.add(new Square(Integer.parseInt("3"), ParseFiles.getFileIntFromChar('c'), capturingKnight));
        
        Board expectedBoard = new Board(expectedPosition);
        Game.testGame(startingPosition);
        
        InputValidator.parseCommand("Cxc4");

        assertAll("Board states and captures must be the same", 
        	() -> {
        			assertEquals(expectedBoard, Game.getBoard());
        			assertEquals(expectedCaptures, Game.getCapturesList());
        		}
        	);
	}
}
