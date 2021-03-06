/**
 * a subclass of Player, the computer player will have logic to play the game like a very simple human
 * 
 * @author Dakota Showman
 * @author Emma May
 */
package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.awt.Color;

public class ComputerPlayer extends Player{
	private char lastRoom;
	private ArrayList<Card> notSeenWeapons;
	private ArrayList<Card> notSeenPeople;
	private ArrayList<Card> allCards;
	private Map<Character, String> legend;
	private boolean guessIsCorrect = false;
	private Solution lastSolution;

	public ComputerPlayer() {
		super();
		lastRoom = 'x';
		notSeenWeapons = new ArrayList<Card>();
		notSeenPeople = new ArrayList<Card>();
		legend = new HashMap<Character, String>();
	}

	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		lastRoom = 'x';
		notSeenWeapons = new ArrayList<Card>();
		notSeenPeople = new ArrayList<Card>();
		legend = new HashMap<Character, String>();
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
					if (b.isDoorway()) {
						lastRoom = b.getInitial();
					}
					return b;
				}
				i++;
			}
		}
		return temp;
		
	}
	
	/**
	 * the computer picks a location and sets its location to the newly chosen location
	 */
	public void makeMove(Set<BoardCell> targets) {
		BoardCell newLocation = pickLocation(targets);
		this.setRow(newLocation.getRow());
		this.setColumn(newLocation.getColumn());
	}
	
	/**
	 * the last solution that set the flag that it is correct becomes the accusation
	 * @return
	 */
	public Solution makeAccusation(){
		return lastSolution;
	}
	
	/**
	 * creates a suggestion based on the computer players knowledge
	 * @param setNewNotSeenCards
	 * @return
	 */
	public Solution createSuggestion(boolean setNewNotSeenCards){
		if (setNewNotSeenCards) {
			setNotSeenCards();
		}
		
		Solution suggestion = new Solution();
		String room = legend.get(lastRoom);
		suggestion.setRoom(room);
		
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
		lastSolution = suggestion;
		return suggestion;
	}
	
	/**
	 * sets the cards the computer player hasn't seen so that it can compute a suggestion
	 */
	private void setNotSeenCards() {
		notSeenWeapons.clear();
		notSeenPeople.clear();
		for (Card c: allCards) {
			if (c.getType() == CardType.WEAPON && !(seenCards.contains(c) || myCards.contains(c))) {
				notSeenWeapons.add(c);
			}
			if (c.getType() == CardType.PLAYER && !(seenCards.contains(c) || myCards.contains(c))) {
				notSeenPeople.add(c);
			}
		}
		
	}
	
	public void setAllCards(ArrayList<Card> cards) {
		allCards = cards;
	}
	
	public boolean isHuman() {
		return false;
	}

	public ArrayList<Card> getNotSeenWeapons() {
		return notSeenWeapons;
	}

	public ArrayList<Card> getNotSeenPeople() {
		return notSeenPeople;
	}
	public void setLegend(Map<Character, String> legend) {
		this.legend = legend;
	}
	
	public String getRoom(){
		return legend.get(lastRoom);
	}
	
	public void setGuessIsCorrect(boolean guess) {
		guessIsCorrect = guess;
	}
	
	public boolean getGuessIsCorrect() {
		return guessIsCorrect;
	}
}
