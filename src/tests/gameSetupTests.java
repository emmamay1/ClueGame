/**
 * @author Emma May
 * @author Dakota Showman
 */

package tests;
import clueGame.Board;
import clueGame.Player;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.ComputerPlayer;

import static org.junit.Assert.*;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Player;

public class gameSetupTests {
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
	public void testLoadDeckOfCards() {
		ArrayList<Card> cardList = board.getCards();
		
		assertEquals(cardList.size(), 21);
		
		int numWeapons = 0;
		int numPlayers = 0;
		int numRooms = 0;
		for (Card c: cardList) {
			if (c.getType() == CardType.PLAYER) {
				numPlayers++;
			}
			else if (c.getType() == CardType.ROOM) {
				numRooms++;
			}
			else if (c.getType() == CardType.WEAPON) {
				numWeapons++;
			}
		}
		assertEquals(numWeapons, 6);
		assertEquals(numPlayers, 6);
		assertEquals(numRooms, 9);
		
		Card tempPlayerCard = cardList.get(4);
		Card tempWeaponCard = cardList.get(8);
		Card tempRoomCard = cardList.get(17);
		
		assertTrue(cardList.contains(tempRoomCard));
		assertTrue(cardList.contains(tempWeaponCard));
		assertTrue(cardList.contains(tempPlayerCard));
		
	}
	
	@Test
	/**
	 * The following tests Mark Baldwin, Poor Student, and CPW player to test that they are loaded in correctly
	 * Checks the name, color and location are correct
	 */
	public void testLoadPeople() {
		ArrayList<Player> players = board.getPlayers();
		
		Player baldwinTest = players.get(0);
		assertEquals(baldwinTest.getPlayerName(), "Mark Baldwin");
		assertTrue(baldwinTest.getColor() == Color.blue);
		assertEquals(baldwinTest.getRow(), 0);
		assertEquals(baldwinTest.getColumn(), 21);
		
		Player studentTest = players.get(5);
		assertEquals(studentTest.getPlayerName(), "Poor Student");
		assertTrue(studentTest.getColor() == Color.pink);
		assertEquals(studentTest.getRow(), 9);
		assertEquals(studentTest.getColumn(), 0);
		
		
		Player cpwTest = players.get(3);
		assertEquals(cpwTest.getPlayerName(), "Christopher Painter-Wakefield");
		assertTrue(cpwTest.getColor() == Color.yellow);
		assertEquals(cpwTest.getRow(), 29);
		assertEquals(cpwTest.getColumn(), 19);
		
	}
	
	
	
	/**
	 * This test checks that the deck is 21 cards, that there are 6 players, 9 rooms, and 6 weapons, and that Python, SOLARIS, and  Tracy Camp loaded correctly
	 */
	
	
	
	@Test
	/**
	 * Tests that all cards are dealt, that every player has about the same number of cards (within 1 of everyone) and that no cards are dealt more than once
	 */
	public void testDealCards() {
		board.deal();
		ArrayList<Player> playerList = board.getPlayers();
		assertEquals(board.getCards().size(), 21);
		
		int numCardsLastPlayer = playerList.get(0).getMyCards().size();
		for (int i = 1; i < playerList.size(); i++) {
			int numCardsPlayer = playerList.get(i).getMyCards().size();
			if (!(numCardsPlayer < (numCardsLastPlayer - 1)) || !(numCardsPlayer > (numCardsLastPlayer + 1))) {
				numCardsLastPlayer = playerList.get(i).getMyCards().size();
			}
			else {
				assertEquals(1, 0);
			}
		}
		
		Card tempWeaponCard = new Card("C++", CardType.WEAPON);
		Card tempRoomCard = new Card("Solaris", CardType.ROOM);
		Card tempPlayerCard = new Card("Tracy Camp", CardType.PLAYER);
		
		Set<ArrayList<Card>> allPlayerCards = new HashSet<ArrayList<Card>>();
		int numInstancesOfWeapon = 0;
		int numInstancesOfRoom = 0;
		int numInstancesOfPlayer = 0;
		for (Player p: playerList) {
			allPlayerCards.add(p.getMyCards());
		}
		for (ArrayList<Card> playerCards: allPlayerCards) {
			if (playerCards.contains(tempWeaponCard)) {
				numInstancesOfWeapon++;
			}
			if (playerCards.contains(tempRoomCard)){
				numInstancesOfRoom++;
			}
			if (playerCards.contains(tempPlayerCard)) {
				numInstancesOfPlayer++;
			}
		}
		
		assertTrue(numInstancesOfPlayer < 2);
		assertTrue(numInstancesOfRoom  < 2);
		assertTrue(numInstancesOfWeapon  < 2);
	}
}	
