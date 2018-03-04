package clueGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

	private int numRows;
	private int numColumns;
	private static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/**
	 * Calls all the functions to set all the instance variables
	 */
	public void initialize() {

	}

	/**
	 * Reads the legend file
	 */
	public void loadRoomConfig() {

	}

		
	/**
	 * reads the excel file with our board
	 */
	public void loadBoardConfig() {

	}

	/**
	 * calculates adjacencies of each walkway
	 */
	public void calcAdjacencies() {

	}

	/**
	 * Figures out the targets for a given cell
	 * @param cell starting cell
	 * @param pathLength number of steps to take
	 */
	public void calcTargets(BoardCell cell, int pathLength) {

	}
	
	/*
	 * The following are all getters or setters
	 */
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public Map<Character, String> getLegend() {
		return legend;
	}
	public Map<BoardCell, Set<BoardCell>> getAdjMatrix() {
		return adjMatrix;
	}
	public Set<BoardCell> getTargets() {
		return targets;
	}
	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}
	public void setConfigFiles(String boardLayout, String legend) {
		
	}

}
