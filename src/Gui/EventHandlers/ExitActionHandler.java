package Gui.EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.Board;

/**
 * Exiting the game.
 * @author urjit
 *
 */
public class ExitActionHandler implements ActionListener {

	public void actionPerformed (ActionEvent e) {
		System.exit(0);
	}
	

}
