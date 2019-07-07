package Gui.EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Gui.GUI;
import code.Board;

/**
 * 
 * When new 3-team game is selected, a new game board is created with different 25 locations in each grid.
 * @author yeqianlin
 *
 */
public class NewThreeTeamGameActionHandler implements ActionListener{
	private Board b;
	private JPanel jp;
	private GUI gui;
	private JPanel m;
	private JPanel gp;
		
	public NewThreeTeamGameActionHandler(Board a, JPanel p, GUI g, JPanel main, JPanel gamePanel) {
		b = a;
		jp = p;
		gui = g;
		m = main;
		gp = gamePanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		b.newThreePlayerGame();
		jp.removeAll();
		gui.update();
		m.add(jp);
		m.add(gp);
		gui.update();
		gui.disableimage();
		gui.getUI().pack();
	}

}
