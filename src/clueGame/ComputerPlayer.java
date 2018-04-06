/**
 * a subclass of Player, the computer player will have logic to play the game like a very simple human
 * 
 * @author Dakota Showman
 * @author Emma May
 */
package clueGame;

import java.util.Set;
import java.awt.Color;

public class ComputerPlayer extends Player{
	char lastRoom;

	public ComputerPlayer() {
		super();
		lastRoom = 'x';
	}

	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		lastRoom = 'x';
	}

		/**
		 * Picks location. If no room in targets, will select randomly. If there is a room in targets that the player has not just visited, will select that room
		 * If there is a room that the player has just visited, will randomly select target
		 * @param targets
		 * @return
		 */
	public BoardCell pickLocation(Set<BoardCell> targets) {
		int target;
		boolean containsRoom = false;
		BoardCell temp = null;
		for(BoardCell b : targets){
			if(b.isDoorway() && (b.getInitial() != lastRoom)){
				containsRoom = true;
				lastRoom = b.getInitial();
				temp = b;
			}
		}
		if(!containsRoom){
			target = (int)(Math.random()*targets.size()); 
			int i = 0;
			for (BoardCell b: targets) {
				if (i == target) {
					return b;
				}
				i++;
			}
		}
		return temp;
		
	}
	
	public void makeAccusation(){
		
	}
	
	public void createSuggestion(/*tbd*/){
		
	}
}
