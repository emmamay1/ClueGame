/**
 * @author Emma May
 * @author Dakota Showman
 * Creates Card panel to show player's hand and button for option to use Detective Notes
 */
package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class YourCardsPanel extends JPanel{
	private DetectiveDialog dialog;
	Player humanPlayer;
	Board board;

	public YourCardsPanel(){
		board = board.getInstance();
		humanPlayer = board.getHumanPlayer();
		setLayout(new GridLayout(0, 1));
		setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		addDisplay("People");
		addDisplay("Rooms");
		addDisplay("Weapons");
		JButton detectiveNotes = new JButton("Detective Notes");
		add(detectiveNotes);
		dialog = new DetectiveDialog();
		detectiveNotes.addActionListener(new ButtonListener());
		
	}
	
	public void addDisplay(String title){
		JTextArea header = new JTextArea(8, 8);
		if (title.equals("People")) {
			String temp = "";
			for (Card c: humanPlayer.getMyCards()) {
				if (c.getType() == CardType.PLAYER) {
					if (temp.equals("")) {
						temp = temp + c.getName();
					}
					else {
						temp = temp + "\n" + c.getName();
					}
					
				}
			}
			header.setText(temp);
		}
		if (title.equals("Rooms")) {
			String temp = "";
			for (Card c: humanPlayer.getMyCards()) {
				if (c.getType() == CardType.ROOM) {
					if (temp.equals("")) {
						temp = temp + c.getName();
					}
					else {
						temp = temp + "\n" + c.getName();
					}
				}
			}
			header.setText(temp);
		}
		if (title.equals("Weapons")) {
			String temp = "";
			for (Card c: humanPlayer.getMyCards()) {
				if (c.getType() == CardType.WEAPON) {
					if (temp.equals("")) {
						temp = temp + c.getName();
					}
					else {
						temp = temp + "\n" + c.getName();
					}
				}
			}
			header.setText(temp);
		}
		header.setEditable(false);
		JLabel nameLabel = new JLabel(title);
		JPanel panel = new JPanel();
		panel.setSize(200, 250);
		panel.setVisible(true);
		panel.setBorder(new TitledBorder(new EtchedBorder(), title));
		panel.add(header);
		add(panel);
		
	}
	//Calls Detective Note dialog
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			dialog.setVisible(true);
		}
	}
}
