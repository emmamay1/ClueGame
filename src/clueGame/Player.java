/**
 * The parent class for humanplayer and computerplayer, this contains most data for the players, along with basic player functionality that both humans and computers need
 * 
 * @author Dakota Showman
 * @author Emma May
 */
package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.Color;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards;
	private ArrayList<Card> seenCards;

	public Player() {
		super();
		myCards = new ArrayList<Card>();
	}

	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		myCards = new ArrayList<Card>();
	}

	public Card disproveSuggestion(Solution suggestion) {
		Set<Integer> matchingCards = new HashSet<Integer>();
		Integer index = new Integer(0);
		for (Card c: myCards) {
			if (c.getType() == CardType.PLAYER) {
				if (suggestion.person.equals(c.getName())) {
					matchingCards.add(index);
				}
			}
			if (c.getType() == CardType.WEAPON) {
				if (suggestion.weapon.equals(c.getName())) {
					matchingCards.add(index);
				}
			}
			if (c.getType() == CardType.ROOM) {
				if (suggestion.room.equals(c.getName())) {
					matchingCards.add(index);
				}
			}
			index++;
		}
		
		if (matchingCards.size() == 0) {
			return null;
		}
		else if (matchingCards.size() == 1){
			int i = 0;
			for (Integer correctIndex: matchingCards) {
				i = correctIndex;
			}
			return myCards.get(i);
		}
		else {
			int randValue = (int)(Math.random() * matchingCards.size());
			int i = 0;
			for (Integer randomIndex: matchingCards) {
				if (i == randValue) {
					return myCards.get(randomIndex);
				}
				i++;
			}
			return null;
		}
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + column;
		result = prime * result + ((myCards == null) ? 0 : myCards.hashCode());
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + row;
		result = prime * result + ((seenCards == null) ? 0 : seenCards.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Player other = (Player) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;

		if (column != other.column)
			return false;

		if (myCards == null) {
			if (other.myCards != null)
				return false;
		} else if (!myCards.equals(other.myCards))
			return false;

		if (playerName == null) {
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;

		if (row != other.row)
			return false;

		if (seenCards == null) {
			if (other.seenCards != null)
				return false;
		} else if (!seenCards.equals(other.seenCards))
			return false;

		return true;
	}

}
