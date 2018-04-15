package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GameDisplay extends JFrame{
<<<<<<< HEAD
	

=======
	private static Board board;
>>>>>>> 08edca6a50dde1528b6945db1ba9612f353f9ffe
	public GameDisplay() {
		board = board.getInstance();
		board.setConfigFiles("ClueBoardLayout.csv", "Rooms.txt", "People.txt", "Weapons.txt");
		board.initialize();
		setLayout(new BorderLayout());
		GUIControlPanel controlPanel = new GUIControlPanel();
<<<<<<< HEAD
		//GameBoardPanel boardPanel = new GameBoardPanel();
		YourCardsPanel cardPanel = new YourCardsPanel();
		add(controlPanel, BorderLayout.SOUTH);
		//add(boardPanel, BorderLayout.CENTER);
		add(cardPanel, BorderLayout.EAST);	
=======
		GameBoardPanel boardPanel = new GameBoardPanel(board);
		//YourCardsPanel cardPanel = new YourCardsPanel();
		add(controlPanel, BorderLayout.SOUTH);
		add(boardPanel, BorderLayout.CENTER);
		//add(cardPanel, BorderLayout.EAST);	
>>>>>>> 08edca6a50dde1528b6945db1ba9612f353f9ffe
	}
	
	public static void main(String[] arg0) {
		GameDisplay frame = new GameDisplay();
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
}
