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

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
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
			
			@Test
			public void testTarget(){
				
			}


}
