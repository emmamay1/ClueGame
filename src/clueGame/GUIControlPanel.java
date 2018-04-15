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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUIControlPanel extends JPanel{

	public GUIControlPanel() {
		setLayout(new GridLayout(2, 3));
		JPanel whoseTurnPanel = new JPanel();
		whoseTurnPanel.add(createTextLabel("Whose turn?"));
		whoseTurnPanel.add(createTextField(false, 20));
		whoseTurnPanel.setSize(300, 125);
		add(whoseTurnPanel);
		add(createButton("Next Player"));
		add(createButton("Make an accusation"));
		JPanel dieRollPanel = createLabeledBorderedTextField("Roll", "Die", 200, 125);
		add(dieRollPanel);
		JPanel guessPanel = createLabeledBorderedTextField("Guess", "Guess", 400, 125);
		add(guessPanel);
		JPanel guessResultPanel = createLabeledBorderedTextField("Response", "Guess Result", 300, 125);
		add(guessResultPanel);
		/*JPanel textPanel = new JPanel();
		textPanel.add(createTextLabel("Whose turn?"));
		add(textPanel);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(createButton("Next player"));
		buttonPanel.add(createButton("Make an accusation"));
		add(buttonPanel);
		JPanel panelOfThree = createThreePartTextPanel("Die", "Guess", "Guess Result");
		add(panelOfThree, BorderLayout.SOUTH);*/
		
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
	
	private JPanel createLabeledBorderedTextField(String text, String borderText, int width, int height) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(text);
		label.setSize(width, height);
		panel.setBorder(new TitledBorder (new EtchedBorder(), borderText));
		panel.add(label);
		panel.add(createTextField(false, width/50));
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

}
