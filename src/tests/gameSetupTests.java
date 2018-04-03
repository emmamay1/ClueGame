package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;

public class gameSetupTests {
	private static Board board;
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		// Board is singleton, get the only instance
		board = board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueBoardLayout.csv", "Rooms.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testLoadPeople() {
		
	}
	
	@Test
	public void testLoadDeckOfCards() {
		
	}
	
	@Test
	public void testDealCards() {
		
	}

}
