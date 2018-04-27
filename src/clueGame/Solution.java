/**
 * The solution class is representative of a bundle of 3 cards which makes up a solution. 
 * 
 * @author Dakota Showman
 * @author Emma May
 */
package clueGame;

public class Solution {
	public String person;
	public String weapon;
	public String room;
	
	public Solution(){
		super();
	}
	
	public Solution(String person, String weapon, String room) {
		super();
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}

	public boolean equals(Solution accusation) {
		return ((this.person.equals(accusation.person)) && (this.weapon.equals(accusation.weapon)) && (this.room.equals(accusation.room)));
	}

	public void setRoom(Card roomCard) {
		// TODO Auto-generated method stub
		
	}

	public String getWeapon() {
		return weapon;
	}

	public String getRoom() {
		return room;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	public void print() {
		System.out.println("room = " + room + "\nweapon = " + weapon + "\nperson = " + person);
	}
}
