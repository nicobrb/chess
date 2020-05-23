package it.uniba.chess.test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.chess.Board;
import it.uniba.chess.Game;
import it.uniba.chess.InputValidator;
import it.uniba.chess.Square;
import it.uniba.chess.pieces.Knight;
import it.uniba.chess.pieces.Pawn;
import it.uniba.chess.pieces.Rook;
import it.uniba.chess.pieces.King;
import it.uniba.chess.utils.ChessColor;
import it.uniba.chess.utils.GameStatus;
import it.uniba.chess.utils.IllegalMoveException;
import it.uniba.chess.utils.ParseFiles;


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
		assertEquals(expectedOutput, outContent.toString());
	}
	
	@Test
	@DisplayName("Check 'board' command with active game")
	public void boardActiveGameTest() throws Exception{	
		outContent.reset();
		System.setOut(new PrintStream(outContent));

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
		assertEquals(expectedOutput, outContent.toString());
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
	@DisplayName("Check successful white short castle")
	public void castleShortWhiteTest() throws Exception{
		LinkedList<Square> startingPosition = new LinkedList<>();
		LinkedList<Square> expectedPosition = new LinkedList<>();
		
		//unmoved white rook
		startingPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('h'), new Rook(ChessColor.WHITE)));		
		
		Rook castledRook = new Rook(ChessColor.WHITE);
		castledRook.setHasMoved();
		
		King castledKing = new King(ChessColor.WHITE);
		castledKing.setHasMoved();
		
		expectedPosition.add(new Square(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('f'), castledRook));
		
		Board expectedBoard = new Board(expectedPosition);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('g')).setOccupied(true);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('g')).setPiece(castledKing);
		expectedBoard.getSquare(Integer.parseInt("0"), ParseFiles.getFileIntFromChar('e')).setOccupied(false);
		
		Game.testGame(startingPosition);
		InputValidator.parseCommand("0-0");
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
		assertEquals(expectedOutput, outContent.toString());
	}
	
	@Test
	@DisplayName("Check captures (Scandinavian defense)")
	public void capturesPrintTest() throws Exception{
		
		outContent.reset();
		System.setOut(new PrintStream(outContent, true, "UTF-8"));
		
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
		assertEquals(expectedOutput, outContent.toString());
	
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
	@DisplayName("Test help menu")
	public void helpMenuTest() throws Exception{
		outContent.reset();
		System.setOut(new PrintStream(outContent));

		String expectedOutput = 
				"help: questo menu\n" + 
				"board: mostra la scacchiera alla posizione attuale\n" + 
				"play: inizia una nuova partita (anche a partità già iniziata)\n" + 
				"quit: termina l'applicazione\n" + 
				"captures: mostra il materiale catturato da ogni colore\n" + 
				"moves: mostra la lista delle mosse giocate durante la partita\n";

		Game.startGame();
		InputValidator.parseCommand("help");
		assertEquals(expectedOutput, outContent.toString());	
	}
}