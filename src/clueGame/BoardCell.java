/**
 * BoardCell stores data for one cell of the board, like its position, initial, and the type of board cell it is
 * 
 * @author Dakota Showman
 * @author Emma may
 */
package clueGame;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection direction;
	private CellType cellType;
	private static final int CELL_HEIGHT = 24;
	private static final int CELL_WIDTH = 24;
	private static final int SMALL_RECT = 5;
	private String roomName;
	
	
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
	public void setCellType(CellType type) {
		cellType = type;
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
		return (cellType == CellType.WALKWAY);
	}
	
	/**
	 * checks if current cell is a room
	 * @return true if it is a room, false otherwise
	 */
	public boolean isRoom(){
		return (cellType == CellType.ROOM);
	}
	
	public void setRoomName(String name) {
		roomName = name;
	}
	
	/**
	 * checks if current cell is a doorway
	 * @return true if it is a doorway, false otherwise
	 */
	public boolean isDoorway(){
		return (cellType == CellType.DOORWAY);
	}
	
	public void draw(Graphics g) {
		if (isRoom()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(column * CELL_WIDTH, row * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
		}
		else if (isDoorway()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(column * CELL_WIDTH, row * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
			if (direction == DoorDirection.UP) {
				g.setColor(Color.BLUE);
				g.fillRect(column * CELL_WIDTH, row * CELL_HEIGHT, CELL_WIDTH, SMALL_RECT);
			}
			else if (direction == DoorDirection.RIGHT) {
				g.setColor(Color.BLUE);
				g.fillRect(column * CELL_WIDTH + CELL_WIDTH - SMALL_RECT, row * CELL_HEIGHT, SMALL_RECT, CELL_HEIGHT);
			}
			else if (direction == DoorDirection.DOWN) {
				g.setColor(Color.BLUE);
				g.fillRect(column * CELL_WIDTH, row * CELL_HEIGHT + CELL_HEIGHT - SMALL_RECT, CELL_WIDTH, 5);
			}
			else if (direction == DoorDirection.LEFT) {
				g.setColor(Color.BLUE);
				g.fillRect(column * CELL_WIDTH, row * CELL_HEIGHT, SMALL_RECT, CELL_HEIGHT);
			}
			else {
				g.setFont(new Font("SansSerif", Font.BOLD, 12));
				g.setColor(Color.BLUE);
				g.drawString(roomName, column * CELL_WIDTH, row * CELL_HEIGHT);
			}
		}
		else {
			g.setColor(Color.BLACK);
			g.drawRect(column * CELL_WIDTH, row * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
		}
	}

}
