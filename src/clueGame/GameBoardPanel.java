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
				if (board.getCellAt(row, column).isWalkway() || board.getCellAt(row, column).isDoorway()) {
					if (board.getTargets().contains(board.getCellAt(row, column)) && !board.getPlayerMoveStatus()) {
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
	/**
	 * if the mouse is pressed in a target, it moves the human player, repaints, does a human turn, increments the player turn count, and sets the players move status to true
	 */
	public void mousePressed(MouseEvent e){
		BoardCell newHumanLocation = null;
		boolean clickInTarget = false;
		for (BoardCell b: board.getTargets()) {
			if (b.containsClick(e.getX(), e.getY())) {
				newHumanLocation = b;
				clickInTarget = true;
				break;
			}
		}
		if (clickInTarget) {
			board.getHumanPlayer().setRow(newHumanLocation.getRow());
			board.getHumanPlayer().setColumn(newHumanLocation.getColumn());
			board.repaint();
			board.doHumanTurn();
			board.incrementPlayerTurn();
			board.setPlayerHasMoved(true);
		}
		else {
			JOptionPane.showMessageDialog(this, "Not a valid target.");
		}
	}
}

