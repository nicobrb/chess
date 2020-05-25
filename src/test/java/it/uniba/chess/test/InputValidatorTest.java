package it.uniba.chess.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import it.uniba.chess.Game;
import it.uniba.chess.InputValidator;
import it.uniba.chess.utils.IllegalMoveException;
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
	@DisplayName("Check printing of moves in active game")
	public void movesActiveGamePrintTest() throws Exception{
		
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
	@DisplayName("Check captures (Scandinavian defense) in active game")
	public void capturesPrintActiveGameTest() throws Exception{
		
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
	@DisplayName("Check 'captures' command with inactive game")
	public void capturesInactiveGameTest() throws Exception{
		
		outContent.reset();
		System.setOut(new PrintStream(outContent, true, "UTF-8"));
		
		String expectedOutput = "Gioco non ancora avviato.\n";
		InputValidator.parseCommand("captures");
		assertEquals(expectedOutput, outContent.toString("UTF-8"));
	}
	
	@Test
	@DisplayName("Check 'moves' command with inactive game")
	public void movesInactiveGameTest() throws Exception{
		
		outContent.reset();
		System.setOut(new PrintStream(outContent, true, "UTF-8"));
		
		String expectedOutput = "Gioco non ancora avviato.\n";
		InputValidator.parseCommand("moves");
		assertEquals(expectedOutput, outContent.toString("UTF-8"));
	}
}