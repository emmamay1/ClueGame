package clueGame;

import java.awt.BorderLayout;
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
		JPanel nextPlayerButton = new JPanel();
		nextPlayerButton.add(createButton("Next Player"));
		nextPlayerButton.setSize(300, 125);
		add(nextPlayerButton);
		JPanel makeAccusationButton = new JPanel();
		makeAccusationButton.add(createButton("Make an accusation"));
		add(makeAccusationButton);
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
	
	private JPanel createThreePartTextPanel(String textOne, String textTwo, String textThree) {
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel(textOne);
		JLabel label2 = new JLabel(textTwo);
		JLabel label3 = new JLabel(textThree);
		label1.setSize(300, 125);
		label2.setSize(300, 125);
		label3.setSize(300, 125);
		label1.setBorder(new TitledBorder (new EtchedBorder(), textOne));
		label2.setBorder(new TitledBorder (new EtchedBorder(), textTwo));
		label3.setBorder(new TitledBorder (new EtchedBorder(), textThree));
		panel.add(label1);
		panel.add(label2);
		panel.add(label3);
		panel.setSize(900, 125);
		
		
		
		return panel;
		
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
