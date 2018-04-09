/**
 * a subclass of Player, the computer player will have logic to play the game like a very simple human
 * 
 * @author Dakota Showman
 * @author Emma May
 */
package clueGame;

import java.util.ArrayList;
import java.util.Set;
import java.awt.Color;

public class ComputerPlayer extends Player{
	char lastRoom;
	ArrayList<Card> notSeenWeapons;
	ArrayList<Card> notSeenPeople;

	public ComputerPlayer() {
		super();
		lastRoom = 'x';
		notSeenWeapons = new ArrayList<Card>();
		notSeenPeople = new ArrayList<Card>();
	}

	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		lastRoom = 'x';
		notSeenWeapons = new ArrayList<Card>();
		notSeenPeople = new ArrayList<Card>();
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
	
	public Solution makeAccusation(){
		return null;
	}
	
	public Solution createSuggestion(/*tbd*/){
		Solution suggestion = new Solution();
		//suggestion.setRoom(this.getRoom());
		
		if(notSeenWeapons.size() == 1){
			suggestion.setWeapon(notSeenWeapons.get(0).getName());
		}
		else{
			int rand = (int)(Math.random()*(notSeenWeapons.size()-1));
			suggestion.setWeapon(notSeenWeapons.get(rand).getName());
		}
		
		if(notSeenPeople.size() == 1){
			suggestion.setPerson(notSeenPeople.get(0).getName());
		}
		else{
			int rand = (int)(Math.random()*notSeenPeople.size());
			suggestion.setPerson(notSeenPeople.get(rand).getName());
		}
		return suggestion;
	}

	public ArrayList<Card> getNotSeenWeapons() {
		return notSeenWeapons;
	}

	public ArrayList<Card> getNotSeenPeople() {
		return notSeenPeople;
	}
	/*
	public String getRoom(){
		BoardCell current = Board.board[this.getRow()][this.getColumn()];
		return null;
	}*/
}
