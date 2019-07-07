package Gui.EventHandlers;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import Gui.GUI;
import code.Board;
/**
 * Action Handler when team selects/guesses a certain codename. 
 * @author urjit
 *
 */
public class codeWordsActionHandler implements ActionListener {

	private Board x;
	private GUI g;

	
	public codeWordsActionHandler(Board _x, GUI _g ) {
		x=_x;
		g=_g;
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean ass=x.updateLocation(e.getActionCommand());
		if(!x.winning()) {
		if(x.getCount() <= 0) {
			g.teamSwitch();
		}else if(!ass) {
			g.teamSwitch();
		}
		}
	}
	

}