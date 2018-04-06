/**
 * @author Emma May
 * @author Dakota Showman
 */

package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class OurBoardAdjTargetTests {
	
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueBoardLayout.csv", "Rooms.txt", "People.txt", "Weapons.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test along the edge of a room
		Set<BoardCell> testList = board.getAdjList(15, 0);
		assertEquals(0, testList.size());
		// Test in the middle of a room
		testList = board.getAdjList(4, 12);
		assertEquals(0, testList.size());
		// Corner of room next to walkway and door
		testList = board.getAdjList(20, 13);
		assertEquals(0, testList.size());
		// corner of room next to walkway and door
		testList = board.getAdjList(12, 24);
		assertEquals(0, testList.size());
		// Test with walkway below
		testList = board.getAdjList(8, 28);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(29, 29);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(15, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 3)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(7, 23);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(7, 22)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(24, 19);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(25, 19)));
		//TEST DOORWAY UP
		testList = board.getAdjList(27, 4);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(26, 4)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(16, 3);
		assertTrue(testList.contains(board.getCellAt(16, 4)));
		assertTrue(testList.contains(board.getCellAt(16, 2)));
		assertTrue(testList.contains(board.getCellAt(15, 3)));
		assertTrue(testList.contains(board.getCellAt(17, 3)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(5, 2);
		assertTrue(testList.contains(board.getCellAt(4, 2)));
		assertTrue(testList.contains(board.getCellAt(6, 2)));
		assertTrue(testList.contains(board.getCellAt(5, 3)));
		assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(13, 10);
		assertTrue(testList.contains(board.getCellAt(12, 10)));
		assertTrue(testList.contains(board.getCellAt(14, 10)));
		assertTrue(testList.contains(board.getCellAt(13, 11)));
		assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(24, 2);
		assertTrue(testList.contains(board.getCellAt(23, 2)));
		assertTrue(testList.contains(board.getCellAt(25, 2)));
		assertTrue(testList.contains(board.getCellAt(24, 3)));
		assertEquals(3, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on left edge of the board
		Set<BoardCell> testList = board.getAdjList(7, 0);
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(8, 0)));
		assertTrue(testList.contains(board.getCellAt(7, 1)));
		assertEquals(3, testList.size());
		
		// Test right edge on room
		testList = board.getAdjList(6, 6);
		assertTrue(testList.contains(board.getCellAt(5, 6)));
		assertTrue(testList.contains(board.getCellAt(7, 6)));
		assertTrue(testList.contains(board.getCellAt(6, 5)));
		assertEquals(3, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(24, 4);
		assertTrue(testList.contains(board.getCellAt(23, 4)));
		assertTrue(testList.contains(board.getCellAt(25, 4)));
		assertTrue(testList.contains(board.getCellAt(24, 3)));
		assertTrue(testList.contains(board.getCellAt(24, 5)));
		assertEquals(4, testList.size());

		//Test surrounded by room and closet on right and left
		testList = board.getAdjList(23, 14);
		assertTrue(testList.contains(board.getCellAt(22, 14)));
		assertTrue(testList.contains(board.getCellAt(24, 14)));
		assertEquals(2, testList.size());
		
		// Test corner of closet
		testList = board.getAdjList(16, 14);
		assertTrue(testList.contains(board.getCellAt(15, 14)));
		assertTrue(testList.contains(board.getCellAt(16, 13)));
		assertEquals(2, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(19, 10, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 11)));
		assertTrue(targets.contains(board.getCellAt(20, 10)));	
		
		board.calcTargets(27, 20, 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(27, 21)));
		assertTrue(targets.contains(board.getCellAt(27, 19)));	
		assertTrue(targets.contains(board.getCellAt(26, 20)));
		assertTrue(targets.contains(board.getCellAt(28, 20)));		
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(19, 10, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 12)));
		assertTrue(targets.contains(board.getCellAt(21, 10)));	
		
		board.calcTargets(27, 20, 2);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(25, 20)));
		assertTrue(targets.contains(board.getCellAt(26, 19)));	
		assertTrue(targets.contains(board.getCellAt(26, 21)));
		assertTrue(targets.contains(board.getCellAt(27, 18)));
		assertTrue(targets.contains(board.getCellAt(28, 19)));
		assertTrue(targets.contains(board.getCellAt(29, 20)));
		assertTrue(targets.contains(board.getCellAt(28, 21)));
		assertTrue(targets.contains(board.getCellAt(27, 22)));
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(19, 10, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(23, 10)));
		assertTrue(targets.contains(board.getCellAt(19, 14)));
		assertTrue(targets.contains(board.getCellAt(20, 12)));
		
		board.calcTargets(27, 20, 4);
		targets= board.getTargets();
		assertEquals(17, targets.size());
		assertTrue(targets.contains(board.getCellAt(24, 19)));
		assertTrue(targets.contains(board.getCellAt(25, 18)));	
		assertTrue(targets.contains(board.getCellAt(25, 22)));
		assertTrue(targets.contains(board.getCellAt(26, 19)));
		assertTrue(targets.contains(board.getCellAt(26, 21)));
		assertTrue(targets.contains(board.getCellAt(26, 17)));
		assertTrue(targets.contains(board.getCellAt(26, 23)));
		assertTrue(targets.contains(board.getCellAt(27, 16)));
		assertTrue(targets.contains(board.getCellAt(27, 22)));
		assertTrue(targets.contains(board.getCellAt(27, 18)));
		assertTrue(targets.contains(board.getCellAt(28, 19)));
		assertTrue(targets.contains(board.getCellAt(28, 21)));
		assertTrue(targets.contains(board.getCellAt(28, 17)));
		assertTrue(targets.contains(board.getCellAt(29, 22)));
		assertTrue(targets.contains(board.getCellAt(29, 20)));
		assertTrue(targets.contains(board.getCellAt(29, 18)));
		assertTrue(targets.contains(board.getCellAt(25, 20)));
		
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(19, 10, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(25, 10)));
		assertTrue(targets.contains(board.getCellAt(21, 14)));	
		assertTrue(targets.contains(board.getCellAt(20, 12)));		
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 4 away, one is 5 away
		board.calcTargets(23, 26, 5);
		Set<BoardCell> targets= board.getTargets();
		//make sure it has the right number of targets
		assertEquals(14, targets.size());
		//test door into room 4 away
		assertTrue(targets.contains(board.getCellAt(27, 26)));
		//test door into room 5 away
		assertTrue(targets.contains(board.getCellAt(22, 28)));
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(22, 28, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(21, 28)));
		// Take two steps
		board.calcTargets(22, 28, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 28)));
		assertTrue(targets.contains(board.getCellAt(21, 29)));
		assertTrue(targets.contains(board.getCellAt(21, 27)));
	}

}
