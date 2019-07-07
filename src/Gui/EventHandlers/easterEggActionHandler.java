package Gui.EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class easterEggActionHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(new URL("https://www.cse.buffalo.edu//~mhertz/MatthewPhoto.jpg"));
		} catch (MalformedURLException e) {
			
		}
		 UIManager UI=new UIManager();
		 UI.put("OptionPane.background",new ColorUIResource(102,178,255));
		 UI.put("Panel.background",new ColorUIResource(102,178,255));
        JOptionPane.showMessageDialog( null, "", "The Man Who Made Us Do This Project", JOptionPane.INFORMATION_MESSAGE, icon);
		
	}

	
}
