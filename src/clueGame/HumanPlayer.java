/**
 * This will be the subclass of player that represents a human player, and allows for movement choices to be made
 * 
 * @author Dakota Showman
 * @author Emma May
 */
package clueGame;

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player{

	public HumanPlayer() {
		super();
	}

	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}
	
	public boolean isHuman() {
		return true;
	}

	@Override
	public void makeMove(Set<BoardCell> targets) {
		// TODO Auto-generated method stub
		
	}

}
