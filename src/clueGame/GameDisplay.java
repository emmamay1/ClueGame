package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameDisplay extends JFrame{
	private static Board board;
	private GUIControlPanel controlPanel;
	private GameBoardPanel boardPanel;
	private YourCardsPanel cardPanel;
	
	public GameDisplay() {
		board = board.getInstance();
		board.setConfigFiles("ClueBoardLayout.csv", "Rooms.txt", "People.txt", "Weapons.txt");
		board.initialize();
		board.deal();
		setLayout(new BorderLayout());
		controlPanel = new GUIControlPanel();
		boardPanel = new GameBoardPanel(board);
		cardPanel = new YourCardsPanel();
		add(controlPanel, BorderLayout.SOUTH);
		add(boardPanel, BorderLayout.CENTER);
		add(cardPanel, BorderLayout.EAST);	
		
	}
	
	public GUIControlPanel getControlPanel(){
		return controlPanel;
	}
	
	/*public static void main(String[] arg0) {
		GameDisplay frame = new GameDisplay();
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		JOptionPane.showMessageDialog(frame, "You are Poor Student (red), press Next Player to begin", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}*/
}
