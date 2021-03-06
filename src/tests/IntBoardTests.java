/**
 * @author Emma May
 * @author Dakota Showman
 */

package tests;
import experiment.BoardCell;
import experiment.IntBoard;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class IntBoardTests {
	private IntBoard board;
	
	@Before
    public void beforeAll() {
       board = new IntBoard();  // constructor should call calcAdjacencies() so you can test them
    }
	
	  /*
		 * Test adjacencies for top left corner
		 */
		@Test
		public void testAdjacency00()
		{
			BoardCell cell = board.getCell(0,0);
			Set<BoardCell> testList = board.getAdjList(cell);
			assertTrue(testList.contains(board.getCell(1, 0)));
			assertTrue(testList.contains(board.getCell(0, 1)));
			assertEquals(2, testList.size());
		}
		
		  /*
			 * Test adjacencies for bottom right corner
			 */
			@Test
			public void testAdjacency33()
			{
				BoardCell cell = board.getCell(3,3);
				Set<BoardCell> testList = board.getAdjList(cell);
				assertTrue(testList.contains(board.getCell(3, 2)));
				assertTrue(testList.contains(board.getCell(2, 3)));
				assertEquals(2, testList.size());
			}
			
			  /*
			 * Test adjacencies for right edge
			 */
			@Test
			public void testAdjacency13()
			{
				BoardCell cell = board.getCell(1,3);
				Set<BoardCell> testList = board.getAdjList(cell);
				assertTrue(testList.contains(board.getCell(0, 3)));
				assertTrue(testList.contains(board.getCell(1, 2)));
				assertTrue(testList.contains(board.getCell(2, 3)));
				assertEquals(3, testList.size());
			}
			
			  /*
			 * Test adjacencies for top left edge
			 */
			@Test
			public void testAdjacency30()
			{
				BoardCell cell = board.getCell(3,0);
				Set<BoardCell> testList = board.getAdjList(cell);
				assertTrue(testList.contains(board.getCell(2, 0)));
				assertTrue(testList.contains(board.getCell(3, 1)));
				assertEquals(2, testList.size());
			}
			
			  /*
			 * Test adjacencies for (1, 1)
			 */
			@Test
			public void testAdjacency11()
			{
				BoardCell cell = board.getCell(1,1);
				Set<BoardCell> testList = board.getAdjList(cell);
				assertTrue(testList.contains(board.getCell(1, 0)));
				assertTrue(testList.contains(board.getCell(0, 1)));
				assertTrue(testList.contains(board.getCell(1, 2)));
				assertTrue(testList.contains(board.getCell(2, 1)));
				assertEquals(4, testList.size());
			}
			
			  /*
			 * Test adjacencies for (2,2)
			 */
			@Test
			public void testAdjacency22()
			{
				BoardCell cell = board.getCell(2,2);
				Set<BoardCell> testList = board.getAdjList(cell);
				assertTrue(testList.contains(board.getCell(1, 2)));
				assertTrue(testList.contains(board.getCell(2, 1)));
				assertTrue(testList.contains(board.getCell(3, 2)));
				assertTrue(testList.contains(board.getCell(2, 3)));
				assertEquals(4, testList.size());
			}
			
			/**
			 * tests all targets for (0,0) with roll 3
			 */
			@Test
			public void testTarget00Roll3(){
				BoardCell cell = board.getCell(0, 0);
				board.calcTargets(cell, 3);
				Set targets = board.getTargets();
				assertEquals(6, targets.size());
				assertTrue(targets.contains(board.getCell(3, 0)));
				assertTrue(targets.contains(board.getCell(2, 1)));
				assertTrue(targets.contains(board.getCell(0, 1)));
				assertTrue(targets.contains(board.getCell(1, 2)));
				assertTrue(targets.contains(board.getCell(0, 3)));
				assertTrue(targets.contains(board.getCell(1, 0)));
			}
			
			/**
			 * tests all targets for (0,0) with roll 4
			 */
			@Test
			public void testTarget00Roll4() {
				BoardCell cell = board.getCell(0, 0);
				board.calcTargets(cell, 4);
				Set targets = board.getTargets();
				assertEquals(6, targets.size());
				assertTrue(targets.contains(board.getCell(2, 0)));
				assertTrue(targets.contains(board.getCell(1, 1)));
				assertTrue(targets.contains(board.getCell(0, 2)));
				assertTrue(targets.contains(board.getCell(2, 2)));
				assertTrue(targets.contains(board.getCell(1, 3)));
				assertTrue(targets.contains(board.getCell(3, 1)));
			}
			
			/**
			 * tests all targets for (2,2) with roll 2
			 */
			@Test
			public void testTarget22Roll2() {
				BoardCell cell = board.getCell(2, 2);
				board.calcTargets(cell, 2);
				Set targets = board.getTargets();
				assertEquals(6, targets.size());
				assertTrue(targets.contains(board.getCell(2, 0)));
				assertTrue(targets.contains(board.getCell(1, 1)));
				assertTrue(targets.contains(board.getCell(3, 1)));
				assertTrue(targets.contains(board.getCell(0, 2)));
				assertTrue(targets.contains(board.getCell(1, 3)));
				assertTrue(targets.contains(board.getCell(3, 3)));
			}
			
			/**
			 * tests all targets for (2,2) with roll 5
			 */
			@Test
			public void testTarget22Roll5() {
				BoardCell cell = board.getCell(2, 2);
				board.calcTargets(cell, 5);
				Set targets = board.getTargets();
				assertEquals(8, targets.size());
				assertTrue(targets.contains(board.getCell(1, 0)));
				assertTrue(targets.contains(board.getCell(3, 0)));
				assertTrue(targets.contains(board.getCell(0, 1)));
				assertTrue(targets.contains(board.getCell(2, 1)));
				assertTrue(targets.contains(board.getCell(1, 2)));
				assertTrue(targets.contains(board.getCell(3, 2)));
				assertTrue(targets.contains(board.getCell(0, 3)));
				assertTrue(targets.contains(board.getCell(2, 3)));
			}


}
