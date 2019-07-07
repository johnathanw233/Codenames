package Gui.EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Gui.GUI;

/**
 * When selects to end turn and not guess anymore. The game switches to the other team's spymaster' turn.
 * @author urjit
 *
 */
public class EndTurnActionHandler implements ActionListener{

	private int teamTurn;
		private GUI gui;

		public EndTurnActionHandler(GUI x) {
			gui = x;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
		//	GUI.teamSwitch();
			gui.teamSwitch();
			// switches team and displays message
			
		}
	
}
