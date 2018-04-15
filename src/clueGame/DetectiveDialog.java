/**
 * @author Emma May
 * @author Dakota Showman
 * Creates Detective Note dialog
 */

package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveDialog extends JDialog{

	public DetectiveDialog() {
		setTitle("Detective Notes");
		setSize(500, 500);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new GridLayout(3, 2));
		ArrayList<String> people = new ArrayList<>(Arrays.asList("Mark Baldwin", "Cyndi Raider", 
				"Jeffrey Paone", "Christopher Painter-Wakefield", "Tracy Camp", "Poor Student"));
		ArrayList<String> rooms = new ArrayList<>(Arrays.asList("Darwin", "Mac", "FreeBSD", "Linux", "Windows",
				"NetBSD", "OpenBSD", "QNX", "Solaris"));
		ArrayList<String> weapons = new ArrayList<>(Arrays.asList("C++", "Java", 
				"Python", "MIPS", "HTML", "PHP"));
		addCheckboxes(people, "People");
		addCurrentGuess(people, "Person Guess");
		addCheckboxes(rooms, "Rooms");
		addCurrentGuess(rooms, "Room Guess");
		addCheckboxes(weapons, "Weapons");
		addCurrentGuess(weapons, "Weapon Guess");
	}
	
	public void addCheckboxes(ArrayList<String> items, String title){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		for(String s: items){
			JCheckBox checkbox = new JCheckBox(s);
			panel.add(checkbox);
		}
		panel.setVisible(true);
		panel.setBorder(new TitledBorder(new EtchedBorder(), title));
		add(panel);
	}
	
	public void addCurrentGuess(ArrayList<String> items, String title){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		JComboBox<String> comboBox = new JComboBox<String>();
		for(String s: items){
			comboBox.addItem(s);
		}
		panel.add(comboBox);
		panel.add(new JCheckBox("Unsure"));
		panel.setVisible(true);
		panel.setBorder(new TitledBorder(new EtchedBorder(), title));
		add(panel);
	}

}
