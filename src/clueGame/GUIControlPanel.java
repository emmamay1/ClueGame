package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIControlPanel extends JPanel{

	public GUIControlPanel() {
		setLayout(new GridLayout(2, 3));
		JPanel panel = createButtonPanel("Next Player");
		add(panel);
		panel = createButtonPanel("Make an Accusation");
		add(panel);
	}
	
	private JPanel createButtonPanel(String title){
		JButton nextButton = new JButton(title);
		JPanel panel = new JPanel();
		panel.add(nextButton);
		return panel;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(250, 150);
		
		GUIControlPanel guiControlPanel = new GUIControlPanel();
		frame.add(guiControlPanel);
		frame.setVisible(true);

	}

}
