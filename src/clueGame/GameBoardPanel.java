package clueGame;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameBoardPanel extends JPanel implements MouseListener{
	private static Board board;
	public GameBoardPanel(Board board) {
		super();
		this.board = board;
		this.setSize(750, 750);
		addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);;
		for (int row = 0; row < board.getNumRows(); row++) {
			for (int column = 0; column < board.getNumColumns(); column++) {
				if (!board.getCellAt(row, column).isWalkway())
				board.getCellAt(row, column).draw(g);
			}
		}
		
		for (int row = 0; row < board.getNumRows(); row++) {
			for (int column = 0; column < board.getNumColumns(); column++) {
				if (board.getCellAt(row, column).isWalkway()) {
					if (board.getTargets().contains(board.getCellAt(row, column)) && board.isHumanPlayersTurn()) {
						board.getCellAt(row, column).highlight(g);
					}
					else {
						board.getCellAt(row, column).draw(g);
					}
					
				}
				
			}
		}
		
		for (Player p: board.getPlayers()) {
			p.draw(g);
		}
	}
	
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		BoardCell[][] pressed = new BoardCell[e.getX()][e.getY()];
		if(board.getTargets().contains(pressed)){
			board.doHumanTurn(e.getX(), e.getY());
		}
		else{
			JOptionPane.showMessageDialog(this, "Not a valid target.");
		}
	}
}
