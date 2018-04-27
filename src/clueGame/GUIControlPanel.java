/**
 * 
 * video refs:
 * 1: intro to GUI
 * 2: adding actionlistener, joptionpane
 * 3: JPanel, flow layout
 * 4: grid layout, different display options, drop downs, fonts
 * 5: menus
 * 6: communication between objects, focus listener
 * 7: drawing
 * 8: custom dialogs
 * 9: mouse listeners
 * 10: determining location of mouse click (which rectangle you clicked in)
 * 
 * 
 * @author Dakota Showman
 * @author Emma may
 */
package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUIControlPanel extends JPanel{
	Board board;
	private JPanel whoseTurnPanel;
	private JPanel dieRollPanel;
	private JPanel guessPanel;
	private JPanel guessResultPanel;
	private JTextField whoseTurnField;
	private JTextField rollField;
	private JTextField guessField;
	private JTextField responseField;
	
	public GUIControlPanel() {
		board = board.getInstance();
		setLayout(new GridLayout(2, 3));
		
		whoseTurnPanel = new JPanel();
		whoseTurnPanel.add(createTextLabel("Whose turn?"));
		whoseTurnField = createTextField(false, 20);
		whoseTurnPanel.add(whoseTurnField);
		whoseTurnPanel.setSize(300, 125);
		add(whoseTurnPanel);
		
		JButton nextPlayerButton = createButton("Next Player");
		nextPlayerButton.addActionListener(new ButtonListener());
		add(nextPlayerButton);
		JButton accusationButton = createButton("Make an accusation");
		add(accusationButton);
		accusationButton.addActionListener(new AccusationListener());
		
		
		rollField = createTextField(false, 20);
		dieRollPanel = createLabeledBorderedTextField("Roll", "Die", 200, 125, rollField);
		add(dieRollPanel);
		guessField = createTextField(false, 20);
		guessPanel = createLabeledBorderedTextField("Guess", "Guess", 800, 125, guessField);
		add(guessPanel);
		responseField = createTextField(false, 20);
		guessResultPanel = createLabeledBorderedTextField("Response", "Guess Result", 300, 125, responseField);
		add(guessResultPanel);
	}
	
	private class AccusationListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			board.handleAccusation();
			
		}
		
	}
	
	private JButton createButton(String title){
		JButton nextButton = new JButton(title);
		nextButton.setSize(500, 125);
		return nextButton;
	}
	
	private JLabel createTextLabel(String text) {
		JLabel  label = new JLabel(text);
		return label;
	}
	
	private JPanel createLabeledBorderedTextField(String text, String borderText, int width, int height, JTextField textField) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(text);
		label.setSize(width, height);
		panel.setBorder(new TitledBorder (new EtchedBorder(), borderText));
		panel.add(label);
		panel.add(textField);
		return panel;
	}
	
	private JTextField createTextField(boolean canEdit, int size) {
		JTextField textField = new JTextField(size);
		textField.setEditable(canEdit);
		return textField;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 250);
		frame.setTitle("GUI Control Panel");
		
		GUIControlPanel guiControlPanel = new GUIControlPanel();
		frame.add(guiControlPanel);
		frame.setVisible(true);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			board.makeNextMove();
		}
	}
	
	public void updateDisplay(int dieRoll, String whoseTurn){
		whoseTurnField.setText(whoseTurn);
		rollField.setText(Integer.toString(dieRoll));
	}
	
	public void setGuess(Solution solution) {
		guessField.setText(solution.room + ", " + solution.weapon + ", " + solution.person);
	}
	
	public void setResponse(String response) {
		responseField.setText(response);
	}
	

}
