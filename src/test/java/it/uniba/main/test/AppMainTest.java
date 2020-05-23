package it.uniba.main.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.chess.Game;
import it.uniba.chess.utils.GameStatus;
import it.uniba.main.AppMain;

public class AppMainTest {
	private final InputStream stdIn = System.in;
	private final PrintStream stdOut = System.out;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@BeforeEach
	public void setUp(){
		Game.setStatus(GameStatus.INACTIVE);
	}
	
	@AfterEach
	public void tearDown() {
		System.setIn(stdIn);
		System.setOut(stdOut);
	}
	
	@Test
	@DisplayName("Check successful quit after start")
	public void successfulQuitCommandTest() throws UnsupportedEncodingException{
	    
		outContent.reset();
		System.setOut(new PrintStream(outContent));

		String expectedOutput = 
		"Scrivi help per la lista dei comandi, play per iniziare una nuova partita. "+
		"Qualunque notazione algebrica prima di play non sarà accettata\n"+
		"Sei sicuro di voler uscire? S/N (default: N)\n";

		String input = "play\nquit\ns\n";
		InputStream inContent = new ByteArrayInputStream(input.getBytes());
		System.setIn(inContent);
		
		AppMain.main(null);
		assertEquals(expectedOutput, outContent.toString());
		
	}
	
	@Test
	@DisplayName("Test unsuccessful quit after start")
	public void unsuccessfulQuitCommandTest() throws UnsupportedEncodingException{
	    
		outContent.reset();
		System.setOut(new PrintStream(outContent));

		String expectedOutput = 
		"Scrivi help per la lista dei comandi, play per iniziare una nuova partita. "+
		"Qualunque notazione algebrica prima di play non sarà accettata\n"+
		"Sei sicuro di voler uscire? S/N (default: N)\n"+
		"Continuo la partita:\n"+
		"Sei sicuro di voler uscire? S/N (default: N)\n";

		String input = 
				"play\n"
				+ "quit\n"
				+ "n\n"
				+ "quit\n"
				+ "s\n";
		InputStream inContent = new ByteArrayInputStream(input.getBytes());
		System.setIn(inContent);
		
		AppMain.main(null);
		assertEquals(expectedOutput, outContent.toString());
		
	}
	
	@Test
	@DisplayName("Check handling of illegal move")
	public void unsuccessfullQuitCommandTest() throws UnsupportedEncodingException{
	    
		outContent.reset();
		System.setOut(new PrintStream(outContent));

		String expectedOutput = 
		"Scrivi help per la lista dei comandi, play per iniziare una nuova partita. "+
		"Qualunque notazione algebrica prima di play non sarà accettata\n"+
		"Mossa illegale\n" +
		"Sei sicuro di voler uscire? S/N (default: N)\n";

		String input = 
				"play\n"
				+ "e8\n"
				+ "quit\n"
				+ "s\n";
		InputStream inContent = new ByteArrayInputStream(input.getBytes());
		System.setIn(inContent);
		
		AppMain.main(null);
		assertEquals(expectedOutput, outContent.toString());
	}
	
	@Test
	@DisplayName("Check successful re-playing")
	public void successfulRePlayCommandTest() throws UnsupportedEncodingException{
	    
		outContent.reset();
		System.setOut(new PrintStream(outContent));

		String expectedOutput = 
		"Scrivi help per la lista dei comandi, play per iniziare una nuova partita. "+
		"Qualunque notazione algebrica prima di play non sarà accettata\n"+
		"Sei sicuro di voler ricominciare il gioco? S/[N]\n" +
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
		"4 |   |   |   |   | ♙ |   |   |   | 4\n" + 
		"  --------------------------------\n" + 
		"3 |   |   |   |   |   |   |   |   | 3\n" + 
		"  --------------------------------\n" + 
		"2 | ♙ | ♙ | ♙ | ♙ |   | ♙ | ♙ | ♙ | 2\n" + 
		"  --------------------------------\n" + 
		"1 | ♖ | ♘ | ♗ | ♕ | ♔ | ♗ | ♘ | ♖ | 1\n" + 
		"  --------------------------------\n" + 
		"   a   b   c   d   e   f   g   h \n" +
		"Materiale del bianco: \n" + 
		"Materiale del nero: \n"+
		"\n" +
		"1. e4 \n"+
		"Sei sicuro di voler uscire? S/N (default: N)\n";

		String input = 
				"play\n"
				+ "e4\n"
				+ "play\n"
				+ "n\n"
				+ "board\n"
				+ "captures\n"
				+ "moves\n"
				+ "quit\n"
				+ "s\n";
		InputStream inContent = new ByteArrayInputStream(input.getBytes());
		System.setIn(inContent);
		
		AppMain.main(null);
		
		//after a move replaying we have the starting board
		assertEquals(expectedOutput, outContent.toString());
		
	}
	
	@Test
	@DisplayName("Check successful re-playing")
	public void unsuccessfulRePlayCommandTest() throws UnsupportedEncodingException{
	    
		outContent.reset();
		System.setOut(new PrintStream(outContent));

		String expectedOutput = 
		"Scrivi help per la lista dei comandi, play per iniziare una nuova partita. "+
		"Qualunque notazione algebrica prima di play non sarà accettata\n"+
		"Sei sicuro di voler ricominciare il gioco? S/[N]\n" +
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
		"   a   b   c   d   e   f   g   h \n" +
		"Materiale del bianco: \n" + 
		"Materiale del nero: \n"+
		"\n"+
		"Sei sicuro di voler uscire? S/N (default: N)\n";

		String input = 
				"play\n"
				+ "e4\n"
				+ "play\n"
				+ "s\n"
				+ "board\n"
				+ "captures\n"
				+ "moves\n"
				+ "quit\n"
				+ "s\n";
		InputStream inContent = new ByteArrayInputStream(input.getBytes());
		System.setIn(inContent);
		
		AppMain.main(null);
		
		//after a move replaying we have the starting board
		assertEquals(expectedOutput, outContent.toString());
		
	}

}