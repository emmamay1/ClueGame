package clueGame;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class GameBoardPanel extends JPanel{
	private static Board board;
	public GameBoardPanel(Board board) {
		super();
		this.board = board;
		this.setSize(750, 750);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);;
		for (int row = 0; row < board.getNumRows(); row++) {
			for (int column = 0; column < board.getNumColumns(); column++) {
				board.getCellAt(row, column).draw(g);
			}
		}
		
		for (Player p: board.getPlayers()) {
			p.draw(g);
		}
	}
}
