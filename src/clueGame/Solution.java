package clueGame;

public class Solution {
	public String person;
	public String weapon;
	public String room;
	
	
	
	public Solution(String person, String weapon, String room) {
		super();
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}



	public boolean equals(Solution accusation) {
		return ((this.person.equals(accusation.person)) && (this.weapon.equals(accusation.weapon)) && (this.room.equals(accusation.room)));
	}
}
