package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GameDisplay extends JFrame{

	public GameDisplay() {
		setLayout(new BorderLayout());
		GUIControlPanel controlPanel = new GUIControlPanel();
		//GameBoardPanel boardPanel = new GameBoardPanel();
		//YourCardsPanel cardPanel = new YourCardsPanel();
		add(controlPanel, BorderLayout.SOUTH);
		//add(boardPanel, BorderLayout.CENTER);
		//add(cardPanel, BorderLayout.EAST);	
	}
	
	public static void main(String[] arg0) {
		GameDisplay frame = new GameDisplay();
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
}
