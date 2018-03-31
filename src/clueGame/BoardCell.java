/**
 * BoardCell stores data for one cell of the board, like its position, initial, and the type of board cell it is
 * 
 * @author Dakota Showman
 * @author Emma may
 */
package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection direction;
	private Boolean isWalkway = false;
	private Boolean isRoom = false;
	private Boolean isDoorway = false;
	
	
	public BoardCell() {
		super();
		row = 0;
		column = 0;
	}
	
	public BoardCell(int row, int column, char initial) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
	}
	
	//The following set the boolean instance variables based
	public void setIsWalkway(Boolean isWalkway) {
		this.isWalkway = isWalkway;
	}

	public void setIsRoom(Boolean isRoom) {
		this.isRoom = isRoom;
	}

	public void setIsDoorway(Boolean isDoorway) {
		this.isDoorway = isDoorway;
	}
	
	//The following are all getters and setters
	public char getInitial() {
		return initial;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	
	public DoorDirection getDoorDirection() {
		return direction;
	}
	
	public void setDirection(DoorDirection direction) {
		this.direction = direction;
	}

	/**
	 * checks if current cell is a walkway
	 * @return true if it is a walkway, false otherwise
	 */
	public boolean isWalkway(){
		return isWalkway;
	}
	
	/**
	 * checks if current cell is a room
	 * @return true if it is a room, false otherwise
	 */
	public boolean isRoom(){
		return isRoom;
	}
	
	/**
	 * checks if current cell is a doorway
	 * @return true if it is a doorway, false otherwise
	 */
	public boolean isDoorway(){
		return isDoorway;
	}
	

}
