package Gui.EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Gui.GUI;
import code.Board;

/**
 * When spymaster' click this button. Their clue and count is inputed and stored.
 * @author urjit
 *
 */
public class EnterActionHandler implements ActionListener{
	private Board b;
	private JTextField countfield;
	private int count;
	private JTextField clues;
	private GUI gui;
	public EnterActionHandler(Board bo, JTextField tf, JTextField ct, GUI g) {
		b = bo;
		clues = tf;
		countfield=ct;
		gui = g;
	}
	public void actionPerformed (ActionEvent e) {
		b.setCount(Integer.valueOf(countfield.getText()));
		count = b.getCount();
		String clue = clues.getText();
		gui.setClue(clue);
		if(b.checkLegalClue(clue)) {
			gui.setHint(true);
			b.notifyObservers();
		}
		else {
			gui.setHint(false);
			JOptionPane.showMessageDialog(null, "The clue is illegal. Please enter another.");
			b.notifyObservers();
		}
		
		if(b.checkLegalClue(clue) && count > 0 && count <= 25){
			b.switchTeamMethod();
			b.notifyObservers();
		}
	}
}
