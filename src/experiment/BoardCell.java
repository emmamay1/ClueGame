package experiment;

public class BoardCell {
	private int row;
	private int col;
	
	public BoardCell() {
		super();
		row = 0;
		col = 0;
	}
	
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
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
	

}
