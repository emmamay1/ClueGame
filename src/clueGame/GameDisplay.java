package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameDisplay extends JFrame{
	private static Board board;
	public GameDisplay() {
		board = board.getInstance();
		board.setConfigFiles("ClueBoardLayout.csv", "Rooms.txt", "People.txt", "Weapons.txt");
		board.initialize();
		setLayout(new BorderLayout());
		GUIControlPanel controlPanel = new GUIControlPanel();
		GameBoardPanel boardPanel = new GameBoardPanel(board);
		YourCardsPanel cardPanel = new YourCardsPanel();
		add(controlPanel, BorderLayout.SOUTH);
		add(boardPanel, BorderLayout.CENTER);
		add(cardPanel, BorderLayout.EAST);	
		
	}
	
	public static void main(String[] arg0) {
		GameDisplay frame = new GameDisplay();
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		JOptionPane.showMessageDialog(frame, "You are Poor Student, press Next Player to begin", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
}
