/**
 * Board class stores every board cell in an array and allows for the functionality of the game board
 * 
 * @author Dakota Showman
 * @author Emma may
 */
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {

	private int numRows;
	private int numColumns;
	private static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> visited;
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
	 * @throws FileNotFoundException 
	 * @throws BadConfigFormatException 
	 */
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException f){
			f.printStackTrace();
		}
		calcAdjacencies();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
	}

	/**
	 * Reads the legend file
	 * @throws FileNotFoundException 
	 * @throws BadConfigFormatException 
	 */
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		legend = new HashMap<Character, String>();		
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNext()) {
			String temp = in.nextLine();
			Character tempchar = temp.charAt(0); //Gets char for room initial
			String value = temp.substring(3, temp.indexOf(',', 3)); //Gets string for room name
			String type = temp.substring(temp.indexOf(',', 3) + 2); //Gets room type (card or other)
			if (!((type.equalsIgnoreCase("Card")) || (type.equalsIgnoreCase("Other")))) {
				throw new BadConfigFormatException("Bad legend typing");
			}
			legend.put(tempchar, value); //Maps the room name to the room initial
		}
	}


	/**
	 * reads the excel file with our board
	 * @throws FileNotFoundException 
	 */
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
		int row = 0;
		int previousColumnNum = 0;
		while (in.hasNext()) {
			String tempLine = in.nextLine();
			String[] tempLineArr = tempLine.split(","); //Creates array of strings, breaking each string at a ','
			int column = 0;
			for (String s: tempLineArr) {
				if (!legend.containsKey(s.charAt(0))) {
					throw new BadConfigFormatException("Room intial not in legend.");
				}
				BoardCell tempBoardCell = new BoardCell();
				tempBoardCell.setInitial(s.charAt(0));
				tempBoardCell.setColumn(column);
				tempBoardCell.setRow(row);
				
				if(s.length() == 2){
					switch(s.charAt(1)){
					case 'U': 
						tempBoardCell.setDirection(DoorDirection.UP);
						tempBoardCell.setCellType(CellType.DOORWAY);
						break;
					case 'D': 
						tempBoardCell.setDirection(DoorDirection.DOWN);
						tempBoardCell.setCellType(CellType.DOORWAY);
						break;
					case 'L': 
						tempBoardCell.setDirection(DoorDirection.LEFT);
						tempBoardCell.setCellType(CellType.DOORWAY);
						break;
					case 'R': 
						tempBoardCell.setDirection(DoorDirection.RIGHT);
						tempBoardCell.setCellType(CellType.DOORWAY);
						break;
					case 'N':
						tempBoardCell.setDirection(DoorDirection.NONE);
						tempBoardCell.setCellType(CellType.DOORWAY);
						break;
					}
				}
				else {
					if (s.charAt(0) == 'W') {
						tempBoardCell.setCellType(CellType.WALKWAY);
					}
					else if(s.charAt(0) != 'X'){
						tempBoardCell.setCellType(CellType.ROOM);
					}
				}
				board[row][column] = tempBoardCell;
				column++;
				numColumns = column;
			}
			if (row == 0) {
				previousColumnNum = numColumns;
			}
			else {
				if (previousColumnNum != numColumns) {
					throw new BadConfigFormatException("Bad number of columns.");
				}
			}
			row++;
			numRows = row;
		}	
	}

	/**
	 * calculates adjacencies of each walkway
	 */
	public void calcAdjacencies() {
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		for (int row = 0; row <= numRows - 1; row++) {
			for (int column = 0; column <= numColumns - 1; column++) {
				BoardCell c = board[row][column];
				Set<BoardCell> temp = new HashSet<BoardCell>();
				if (c.isWalkway()) {
					//checks if cell above current exists, and if it does and isWalkway or door with direction DOWN, adds to temp
					if ((row - 1) >= 0 && (board[row-1][column].isWalkway() || 
							((board[row-1][column].isDoorway()) && (board[row-1][column].getDoorDirection() == DoorDirection.DOWN)))) {
						temp.add(board[row-1][column]);
					}
					//checks if cell below current exists, and if it does and isWalkway or door with direction UP, adds to temp
					if ((row + 1) < numRows && (board[row+1][column].isWalkway() || 
							((board[row+1][column].isDoorway()) && (board[row+1][column].getDoorDirection() == DoorDirection.UP)))) {
						temp.add(board[row+1][column]);
					}
					//checks if cell to the left of current exists, and if it does and isWalkway or door with direction RIGHT, adds to temp
					if ((column - 1) >= 0 && (board[row][column-1].isWalkway() || 
							((board[row][column-1].isDoorway()) && (board[row][column-1].getDoorDirection() == DoorDirection.RIGHT)))) {
						temp.add(board[row][column - 1]);
					}
					//checks if cell to the right of current exists, and if it does and isWalkway or door with direction LEFT, adds to temp
					if ((column + 1) < numColumns && (board[row][column+1].isWalkway() || 
							((board[row][column+1].isDoorway()) && (board[row][column+1].getDoorDirection() == DoorDirection.LEFT)))) {
						temp.add(board[row][column + 1]);
					}
					adjMatrix.put(c, temp);
				}
				else if (c.isDoorway()) { //put into a switch statement
					if (c.getDoorDirection() == DoorDirection.UP) {
						temp.add(board[row-1][column]);
					}
					if (c.getDoorDirection() == DoorDirection.DOWN) {
						temp.add(board[row+1][column]);
					}
					if (c.getDoorDirection() == DoorDirection.LEFT) {
						temp.add(board[row][column - 1]);
					}
					if (c.getDoorDirection() == DoorDirection.RIGHT) {
						temp.add(board[row][column + 1]);
					}
					adjMatrix.put(c, temp);
				}
				else {
					adjMatrix.put(c, temp);
				}
			}
		}
	}

	/**
	 * Driver for findAllTargets, which will find all targets for given cell
	 * @param cell starting cell
	 * @param pathLength number of steps to take
	 */
	public void calcTargets(BoardCell cell, int pathLength) {
		visited.clear();
		targets.clear();
		visited.add(cell);
		findAllTargets(cell, pathLength);
	}
	
	public void calcTargets(int row, int column, int pathLength) {
		calcTargets(board[row][column], pathLength);
	}

	/**
	 * Recursive function that will find all the targets for a given cell
	 * @param startCell
	 * @param pathLength
	 */
	public void findAllTargets(BoardCell startCell, int pathLength) {
		for (BoardCell adjCell: adjMatrix.get(startCell)) {
			if (!visited.contains(adjCell)) {
				visited.add(adjCell);
				if (pathLength == 1 || adjCell.isDoorway()) {
					targets.add(adjCell);
				}
				else {
					findAllTargets(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);
			}
		}
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
	public BoardCell getCellAt(int row, int column) {
		return board[row][column];
	}
	public void setConfigFiles(String boardLayout, String legend) {
		boardConfigFile = boardLayout;
		roomConfigFile = legend;
	}
	public Set<BoardCell> getAdjList(int row, int column) {
		return adjMatrix.get(board[row][column]);
	}

}
