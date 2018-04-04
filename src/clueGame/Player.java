package clueGame;

import com.sun.prism.paint.Color;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	public Player() {
		super();
	}
	
	
	
	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}



	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
}
