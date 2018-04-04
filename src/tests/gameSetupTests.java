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
	/**
	 * The following test creates a fake mark baldwin, poor student, and cpw player to test that they are loaded in correctly
	 * if any aspect of the character is loaded wrong, it won't be equal to the ones fabricated here
	 */
	public void testLoadPeople() {
		Player baldwinTest = new Player();
		baldwinTest.setColor(Color.blue);
		baldwinTest.setColumn(21);
		baldwinTest.setRow(0);
		baldwinTest.setPlayerName("Mark Baldwin");
		
		Player studentTest = new Player();
		studentTest.setColor(Color.pink);
		studentTest.setRow(9);
		studentTest.setColumn(0);
		studentTest.setPlayerName("Poor Student");
		
		Player cpwTest = new Player();
		cpwTest.setColor(Color.yellow);
		cpwTest.setRow(29);
		cpwTest.setColumn(19);
		cpwTest.setPlayerName("Christopher Painter-Wakefield");
		
		Set<Player> playerList = board.getPlayers();
		
		assertTrue(playerList.contains(baldwinTest));
		assertTrue(playerList.contains(studentTest));
		assertTrue(playerList.contains(cpwTest));
		
	}
	
	@Test
	/**
	 * this test checks that the deck is 21 cards, that there are 6 players, 9 rooms, and 6 weapons, and that c++, SOLARIS, and  Tracy Camp loaded correctly
	 */
	public void testLoadDeckOfCards() {
		Set<Card> cardList = board.getCards();
		
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
		
		Card tempWeaponCard = new Card("C++", CardType.WEAPON);
		Card tempRoomCard = new Card("Solaris", CardType.ROOM);
		Card tempPlayerCard = new Card("Tracy Camp", CardType.PLAYER);
		
		assertTrue(cardList.contains(tempRoomCard));
		assertTrue(cardList.contains(tempWeaponCard));
		assertTrue(cardList.contains(tempPlayerCard));
		
	}
	
	@Test
	public void testDealCards() {
		Set<Player> playerList = board.getPlayers();
		int numCardsPerPlayer = -1;
		for (Player p: playerList) {
			if (numCardsPerPlayer == -1) {
				numCardsPerPlayer = p.getMyCards().size();
			}
			else if((p.getMyCards().size() < (numCardsPerPlayer - 1)) || (p.getMyCards().size() > (numCardsPerPlayer + 1))) {
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
		
		assertEquals(numInstancesOfPlayer, 1);
		assertEquals(numInstancesOfRoom, 1);
		assertEquals(numInstancesOfWeapon, 1);
		
	}

}
