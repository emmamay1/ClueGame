package experiment;

import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	

	public IntBoard() {
		super();
		initializeGrid();
	}

	public void initializeGrid() {
		grid = new BoardCell[4][4];
	}
	
	public IntBoard(Map<BoardCell, Set<BoardCell>> adjMtx) {
		this.adjMtx = adjMtx;
	}
	
	/**
	 * Calculates the adjacency list
	 *  for each grid cell and stores the results as a Map in adjMtx
	 */
	public void calcAdjacencies(){
		
	}
	
	/**
	 * Returns the adjacency list for one cell
	 * @param cell
	 * @return
	 */
	public Set<BoardCell> getAdjList(BoardCell cell){
		return null;
	}
	
	/**
	 * Calculates the targets that are pathLength distance from the startCell. 
	 * The list of targets will be stored as a Set in an instance variable.
	 * @param startCell
	 * @param pathLength
	 */
	public void calcTargets(BoardCell startCell, int pathLength){
		
	}
	
	/**
	 * Returns the list of targets as a set
	 * @return
	 */
	public Set<BoardCell> getTargets(){
		return null;
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public BoardCell getCell(int i, int j) {
		return null;
	}

}
