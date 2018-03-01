package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	

	public IntBoard() {
		super();
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		initializeGrid();
		calcAdjacencies();
	}
	
	public IntBoard(Map<BoardCell, Set<BoardCell>> adjMtx) {
		this.adjMtx = adjMtx;
	}
	
	/**
	 * Creates the board cells for each position and initializes the row and col
	 */
	public void initializeGrid() {
		grid = new BoardCell[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				BoardCell temp = new BoardCell(i, j);
				grid[i][j] = temp;
			}
		}
	}

	
	/**
	 * Calculates the adjacency list
	 *  for each grid cell and stores the results as a Map in adjMtx
	 */
	public void calcAdjacencies(){
		for (BoardCell[] c_row: grid) {
			for (BoardCell c: c_row) {
				int row = c.getRow();
				int col = c.getCol();
				Set<BoardCell> temp = new HashSet<BoardCell>();
				
				if ((row - 1) >= 0) {
					temp.add(grid[row-1][col]);
				}
				if ((row + 1) <= 3) {
					temp.add(grid[row+1][col]);
				}
				if ((col - 1) >= 0) {
					temp.add(grid[row][col - 1]);
				}
				if ((col + 1) <= 3) {
					temp.add(grid[row][col + 1]);
				}
				
				adjMtx.put(c, temp);
			}
		}
	}
	
	/**
	 * Returns the adjacency list for one cell
	 * @param cell
	 * @return
	 */
	public Set<BoardCell> getAdjList(BoardCell cell){
		return adjMtx.get(cell);
	}
	
	/**
	 * Calculates the targets that are pathLength distance from the startCell. 
	 * The list of targets will be stored as a Set in an instance variable.
	 * @param startCell
	 * @param pathLength
	 */
	public void calcTargets(BoardCell startCell, int pathLength){
		visited.clear();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}
	
	/**
	 * recursive function to find all targets for a given cell
	 * @param startCell
	 * @param pathLength
	 */
	public void findAllTargets(BoardCell startCell, int pathLength) {
		for (BoardCell adjCell: adjMtx.get(startCell)) {
			if (!visited.contains(adjCell)) {
				visited.add(adjCell);
				if (pathLength == 1) {
					targets.add(adjCell);
				}
				else {
					findAllTargets(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);
			}
		}
	}
	
	/**
	 * Returns the list of targets as a set
	 * @return
	 */
	public Set<BoardCell> getTargets(){
		return targets;
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @return grid at i j
	 */
	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}

}
