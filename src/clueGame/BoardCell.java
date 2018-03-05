package clueGame;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection direction;
	private Boolean isWalkway = false;
	private Boolean isRoom = false;
	private Boolean isDoorway = false;
	
	
	public BoardCell() {
		super();
		row = 0;
		col = 0;
	}
	
	public BoardCell(int row, int col, char initial) {
		super();
		this.row = row;
		this.col = col;
		this.initial = initial;
	}
	
	
	
	public void setIsWalkway(Boolean isWalkway) {
		this.isWalkway = isWalkway;
	}

	public void setIsRoom(Boolean isRoom) {
		this.isRoom = isRoom;
	}

	public void setIsDoorway(Boolean isDoorway) {
		this.isDoorway = isDoorway;
	}

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

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
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
