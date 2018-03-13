/**
 *Tests for our board
 * 
 * @author Dakota Showman
 * @author Emma may
 */
package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class BoardLayoutTests {

	/**
	 * Our legend is 11 entries long, and our board is a 30x30 board
	 */
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 30;
	public static final int NUM_COLUMNS = 30;
	
	//the board used for testing
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
	public void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		// from the hash, including the first and last in the file and a few others
		assertEquals("Darwin", legend.get('D'));
		assertEquals("Mac", legend.get('M'));
		assertEquals("FreeBSD", legend.get('B'));
		assertEquals("Linux", legend.get('L'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(13, 13);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(4, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(13, 11);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(23, 0);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getCellAt(0, 0);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(0, 2);
		assertFalse(cell.isDoorway());		

	}
	
	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(20, numDoors);
	}
	
	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRoomInitials() {
		// Test first cell in room
		assertEquals('D', board.getCellAt(0, 0).getInitial());
		assertEquals('L', board.getCellAt(13, 0).getInitial());
		assertEquals('I', board.getCellAt(28, 0).getInitial());
		// Test last cell in room
		assertEquals('M', board.getCellAt(0, 17).getInitial());
		assertEquals('N', board.getCellAt(28, 15).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(0, 5).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(16, 8).getInitial());
	}

}
