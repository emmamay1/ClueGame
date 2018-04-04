/**
 * a subclass of Player, the computer player will have logic to play the game like a very simple human
 * 
 * @author Dakota Showman
 * @author Emma may
 */
package clueGame;

import java.util.Set;
import java.awt.Color;

public class ComputerPlayer extends Player{

	public ComputerPlayer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccusation(){
		
	}
	
	public void createSuggestion(/*tbd*/){
		
	}
}
