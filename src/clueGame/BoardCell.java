package clueGame;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection direction;
	
	
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
	
	/**
	 * checks if current cell is a walkway
	 * @return true if it is a walkway, false otherwise
	 */
	public boolean isWalkway(){
		return false;
		/*if(initial == 'W'){
			return true;
		}
		return false;*/
	}
	
	/**
	 * checks if current cell is a room
	 * @return true if it is a room, false otherwise
	 */
	public boolean isRoom(){
		return false;
	}
	
	/**
	 * checks if current cell is a doorway
	 * @return true if it is a doorway, false otherwise
	 */
	public boolean isDoorway(){
		return false;
	}
	

}
