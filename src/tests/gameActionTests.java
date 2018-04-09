/**
 * @author Emma May
 * @author Dakota Showman
 */

package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class gameActionTests {
	private static Board board;
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		// Board is singleton, get the only instance
		board = board.getInstance();
		// set the file names to use my config files
		//board.setConfigFiles("ClueBoardLayout.csv", "Rooms.txt");	
		board.setConfigFiles("ClueBoardLayout.csv", "Rooms.txt", "People.txt", "Weapons.txt");
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Test
	//Tests when there are no rooms in the target list that a target is chosen randomly 
	public void testTargetNoRooms() {
		ComputerPlayer player = new ComputerPlayer();
		board.calcTargets(10, 19, 3);
		boolean loc7_19 = false;
		boolean loc8_18 = false;
		boolean loc8_20 = false;
		boolean loc9_19 = false;
		boolean loc9_17 = false;
		boolean loc9_21 = false;
		boolean loc10_18 = false;
		boolean loc10_20 = false;
		boolean loc12_18 = false;
		boolean loc10_16 = false;
		boolean loc10_22 = false;
		
		for(int i = 0; i < 1000; i++){
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(7,  19)) {
				loc7_19 = true;
			}
			else if (selected == board.getCellAt(8,  18)) {
				loc8_18 = true;
			}
			else if (selected == board.getCellAt(8,  20)) {
				loc8_20 = true;
			}
			else if (selected == board.getCellAt(9,  19)) {
				loc9_19 = true;
			}
			else if (selected == board.getCellAt(9,  17)) {
				loc9_17 = true;
			}
			else if (selected == board.getCellAt(9,  21)) {
				loc9_21 = true;
			}
			else if (selected == board.getCellAt(10,  18)) {
				loc10_18 = true;
			}
			else if (selected == board.getCellAt(10,  20)) {
				loc10_20 = true;
			}
			else if (selected == board.getCellAt(12,  18)) {
				loc12_18 = true;
			}
			else if (selected == board.getCellAt(10,  16)) {
				loc10_16 = true;
			}
			else if (selected == board.getCellAt(10,  22)) {
				loc10_22 = true;
			}
			else {
				fail("Invalid target selected");
			}
		}
		
		assertTrue(loc7_19);
		assertTrue(loc8_18);
		assertTrue(loc8_20);
		assertTrue(loc9_19);
		assertTrue(loc9_17);
		assertTrue(loc9_21);
		assertTrue(loc10_18);
		assertTrue(loc10_20);
		assertTrue(loc12_18);
		assertTrue(loc10_16);
		assertTrue(loc10_22);	
	}
	
	@Test
	//Tests if there is a room in targets that has not been recently visited, the computer player goes there.
	//Also test if there is a room in targets that has been visited, it has an equally likely chance of being selected
	public void testTargetWithRoom() {
		ComputerPlayer player = new ComputerPlayer();
		board.calcTargets(21, 27, 2);
		BoardCell selected = player.pickLocation(board.getTargets());
		assertEquals(selected, board.getCellAt(22, 28));
		
		boolean loc19_27 = false;
		boolean loc20_26 = false;
		boolean loc20_28 = false;
		boolean loc21_29 = false;
		boolean loc22_26 = false;
		boolean loc22_28 = false;
		
		for(int i = 0; i < 1000; i++){
			BoardCell choice = player.pickLocation(board.getTargets());
			if (choice == board.getCellAt(19,  27)) {
				loc19_27 = true;
			}
			else if (choice == board.getCellAt(20, 26)) {
				loc20_26 = true;
			}
			else if (choice == board.getCellAt(20, 28)) {
				loc20_28 = true;
			}
			else if (choice == board.getCellAt(21, 29)) {
				loc21_29 = true;
			}
			else if (choice == board.getCellAt(22, 26)) {
				loc22_26 = true;
			}
			else if (choice == board.getCellAt(22, 28)) {
				loc22_28 = true;
			}
		}
		
		assertTrue(loc19_27);
		assertTrue(loc20_26);
		assertTrue(loc20_28);
		assertTrue(loc21_29);
		assertTrue(loc22_26);
		assertTrue(loc22_28);
	}
	
	@Test
	/**
	 * tests that the board can correctly check if solutions work or not
	 */
	public void testCheckAccusation() {
		Solution correctSolution = new Solution("Mark Baldwin", "C++", "Solaris");
		board.setTrueSolution(correctSolution);
		Solution incorrectPerson = new Solution("Cyndi Raider", "C++", "Solaris");
		Solution incorrectWeapon = new Solution("Mark Baldwin", "Java", "Solaris");
		Solution incorrectRoom = new Solution("Mark Baldwin", "C++", "Mac");
		
		assertTrue(board.checkAccusation(correctSolution));
		assertTrue(!board.checkAccusation(incorrectPerson));
		assertTrue(!board.checkAccusation(incorrectWeapon));
		assertTrue(!board.checkAccusation(incorrectRoom));
	}
	
	@Test
	/**
	 * tests that a computer player can disprove a suggestion
	 */
	public void testDisproveSuggestion() {
		ComputerPlayer player = new ComputerPlayer();
		Card weaponCard = new Card("C++", CardType.WEAPON);
		Card playerCard = new Card("Mark Baldwin", CardType.PLAYER);
		Card roomCard = new Card("Solaris", CardType.ROOM);
		player.getMyCards().add(weaponCard);
		player.getMyCards().add(playerCard);
		player.getMyCards().add(roomCard);
		
		Solution allCardsMatch = new Solution("Mark Baldwin", "C++", "Solaris");
		Solution twoCardsMatch = new Solution("Mark Baldwin", "C++", "Mac");
		Solution personOnlyMatch = new Solution("Mark Baldwin", "Java", "Mac");
		Solution nothingMatch = new Solution("Cyndi Raider", "Java", "Mac");
		
		boolean mBald = false;
		boolean cPlus = false;
		boolean Solaris = false;
		
		
		for (int i = 0; i < 1000; i++) {
			Card temp = player.disproveSuggestion(allCardsMatch);
			if (temp.getName().equals("C++")) {
				cPlus = true;
			}
			if (temp.getName().equals("Mark Baldwin")) {
				mBald = true;
			}
			if (temp.getName().equals("Solaris")) {
				Solaris = true;
			}
		}
		assertTrue(cPlus);
		assertTrue(mBald);
		assertTrue(Solaris);
		
		mBald = false;
		cPlus = false;
		for (int i = 0; i < 1000; i++) {
			Card temp = player.disproveSuggestion(allCardsMatch);
			if (temp.getName().equals("C++")) {
				cPlus = true;
			}
			if (temp.getName().equals("Mark Baldwin")) {
				mBald = true;
			}
		}
		assertTrue(cPlus);
		assertTrue(mBald);
		
		Card temp = player.disproveSuggestion(personOnlyMatch);
		assertTrue(temp.getName().equals("Mark Baldwin"));
		
		temp = player.disproveSuggestion(nothingMatch);
		assertNull(temp);
	}
	@Test
	public void testMakeAccusation(){
		ComputerPlayer player = new ComputerPlayer();
		Solution answer = board.getTrueSolution();
		Solution guess = player.makeAccusation();
		
		assertEquals(answer.person, guess.person);
		assertEquals(answer.weapon, guess.weapon);
		assertEquals(answer.room, guess.room);
	}
	
	
	//@Test
	/**
	 * Tests if room matches current location. If 
	 */
	/*
	public void testCreateSuggestion(){
		Solution accusation = new Solution();
		
	}*/

}
