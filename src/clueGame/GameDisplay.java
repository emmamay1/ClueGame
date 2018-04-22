package clueGame;

import java.awt.BorderLayout;


import javax.swing.JFrame;


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
		boardPanel.addMouseListener(null);
		
	}
	
	public GUIControlPanel getControlPanel(){
		return controlPanel;
	}
	
	
}
