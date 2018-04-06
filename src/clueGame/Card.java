/**
 * contains data for a card for the deck
 * 
 * @author Dakota Showman
 * @author Emma May
 */
package clueGame;

public class Card {
	private String name;
	private CardType type;
	
	public Card(String name, CardType type) {
		super();
		this.name = name;
		this.type = type;
	}
	
	public CardType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
}
