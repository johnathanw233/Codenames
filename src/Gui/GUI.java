package Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;
import Driver.Driver;
import Gui.EventHandlers.EndTurnActionHandler;
import Gui.EventHandlers.EnterActionHandler;
import Gui.EventHandlers.ExitActionHandler;
import Gui.EventHandlers.NewGameActionHandler;
import Gui.EventHandlers.NewThreeTeamGameActionHandler;
import Gui.EventHandlers.codeWordsActionHandler;
import Gui.EventHandlers.easterEggActionHandler;
import code.Board;
import code.Observer;

public class GUI implements Observer	{
	
	/** The main board containing 25 locations. */
	private Board board;
	
	private Driver d;
	
	/** The main UI containing our menu and all our panels. */
	private JFrame UI;
	
	/** The buttons that the 25 locations will be placed on. */
	private JPanel button;
	
	/** The messageBoard displaying what is happening in-game (current team's turn, count, and clue) */
	private JLabel messageBoard;
	
	/** The panel which has the messageBoard, and the submit, end turn button, and the input text box will be located. */
	private JPanel gamePanel;
	
	/** The submit button that will submit the values taken in my the spymaster. */
	private JButton submit;
	
	/** The button will end each of the team's turn. */
	private JButton endTurn;
	
	/** The menu containing the new game and exit button. */
	private JMenuBar menu;
	
	/** Menu options */
	private JMenu File;
	
	/** Contains button and gamepanel panels. */
	private JPanel main;
	
	/** TextField for clue. */
	private JTextField textfield;
	
	/** TextField for count. */
	private JTextField countTextField;
	
	/** Message on whose turn it is. */
	private JLabel currentMessage;
	
	/** Jpanel displays the messages "enterCount" or "Invalid count" when count is invalid. */
	private JLabel enterCount;
	
	/** create image for main panel. */
	private JLabel image;
	
	/** True when clue is valid. */
	private boolean hint;
	
	/** Clue for helping to find the team agents. */
	private String clue;
	
	// create GUI application with menu and menu items.
	public GUI(Board x, JFrame ui, Driver driver) {
		
		d = driver;
		board = x;
		board.newGame();
		UI = ui;
		menu = new JMenuBar();
		File = new JMenu("File");
		JMenuItem newgame = new JMenuItem("New Two Team Game");
		JMenuItem newThreeGame = new JMenuItem("New Three Team Game");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem easterEgg = new JMenuItem("Surprise!");
		main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		button = new JPanel();
		gamePanel = new JPanel();
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.X_AXIS));
		File.add(newgame);
		File.add(newThreeGame);
		File.add(exit);
		File.add(easterEgg);
		menu.add(File);
		UI.setJMenuBar(menu);
		exit.addActionListener(new ExitActionHandler());
		newgame.addActionListener(new NewGameActionHandler(board, button, this, main, gamePanel));
		newThreeGame.addActionListener(new NewThreeTeamGameActionHandler(board, button, this, main, gamePanel));
		easterEgg.addActionListener(new easterEggActionHandler());
		// create image for main JPanel, replace URL with image you like
		image = new JLabel();
		ImageIcon imgThisImg = null;
		try {
			imgThisImg = new ImageIcon(new URL("https://image.ibb.co/cQsern/1483725927645_jpg.png"));
		} catch (MalformedURLException e) {
		}
		image.setIcon(imgThisImg);
		main.add(image);
		
		// create 25 Words 
		
		board.newGame();
		board.addObserver(this);
		
		//doesn't show but must have
		hint = true;
		currentMessage = new JLabel("Enter a clue: ");
		enterCount = new JLabel("   Enter a count: ");
		submit = new JButton("Enter");
		endTurn = new JButton("End Turn");
		submit.addActionListener(new EnterActionHandler(board, textfield, countTextField, this));
		
		messageBoard = new JLabel("   It is currently " + board.getTurn() + "'s turn:                        ");
		textfield = new JTextField(5);
		endTurn.addActionListener(new EndTurnActionHandler(this));
		endTurn.setVisible(false);
		countTextField = new JTextField(5);
		
		
		endTurn.addActionListener(new EndTurnActionHandler(this));
		endTurn.setVisible(false);
		
		UI.pack();
	}

	/**
	 * All updation takes place here. Creation of different boards for the spymaster' and team players'
	 */
	public void update() {
		
		//updates how many points blue team and red team has.
		board.updatepoints();
		
		//Easter Egg - Bystanders
		if(board.easterEgg()) {
			JLabel harhar = new JLabel("              HOW COULD YOU??!?!??!?!?!?");
			menu.add(harhar);
			menu.add(harhar);
		}
		
		//Easter Egg- Gif
		if(board.AssassinEasterEgg()) {
			ImageIcon icon = null;
			try {
				icon = new ImageIcon(new URL("https://image.ibb.co/nBPTHS/pic.gif"));
			} catch (MalformedURLException e) {
				
			}
            JOptionPane.showMessageDialog( null, "YOU LOSE", "NOOO", JOptionPane.INFORMATION_MESSAGE, icon);
		}
		
		//bystanders easter egg	
		//if this teams turn, if role.revealed < 6 then joptionpane
		//if (board.getTeamTurn() == 0 || board.getTeamTurn() == 2 || board.getTeamTurn() == 4) {
		
		
		
		//resets the board
		button.removeAll();
		gamePanel.removeAll();
		
		//remakes the board with the new information.
		button.setLayout(new GridLayout(5, 5));	
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.X_AXIS));
		
		
		messageBoard = new JLabel("   It is currently " + board.getTurn() + "'s turn:                        ");
		textfield = new JTextField(5);
		countTextField = new JTextField(5);
		submit = new JButton("Enter");
		endTurn = new JButton("End Turn");
		
		endTurn.addActionListener(new EndTurnActionHandler(this));
		submit.addActionListener(new EnterActionHandler(board, textfield, countTextField, this));
		endTurn.setVisible(false);
		
		//checks if the clue and count the spymaster gave was valid. 
		//(hint is only changed to false in EnterEventHandler)
		if(hint) {
			currentMessage = new JLabel("Enter a clue: ");
		}else {
			currentMessage = new JLabel("Invalid clue");
		}
		if(board.getCount() > 0 && board.getCount() <= 25) {
			enterCount = new JLabel("   Enter a count: ");
		} else {
			enterCount = new JLabel ("  Invalid count");
		}
		
		
		//creates the end turn button for the rest of the team so they can end turn early.
		endTurn = new JButton("End Turn");
		endTurn.addActionListener(new EndTurnActionHandler(this));
		endTurn.setVisible(false);
		
		
		//adds all the player commands to the game.
		gamePanel.add(messageBoard, JLabel.CENTER);
		gamePanel.add(currentMessage);
		gamePanel.add(textfield);
		gamePanel.add(enterCount);
		gamePanel.add(countTextField);
		gamePanel.add(submit);
		gamePanel.add(endTurn);
		
		
		//Spymaster's turn. (Both blue and red team)
		if(board.getTeamTurn() == 0 || board.getTeamTurn() == 2 || board.getTeamTurn() == 4) {
			for(int i = 0; i < 25; i++) {
				String text = String.valueOf(board.getLocation().get(i).getPerson());
				String role = board.getLocation().get(i).getTeam();
				if(board.getLocation().get(i).getRevealed()) {
					JButton Button = new JButton("<html>"+ role);//+text +"<br>"
					Button.setPreferredSize(new Dimension(150,80));
					if(role.equals("Red Agent")) {
						Button.setBackground(Color.red);
					} else if(role.equals("Blue Agent")) {
						Button.setBackground(Color.blue);
					} else if(role.equals("Innocent Bystander")) {
						Button.setBackground(Color.white);
						
					
					} else if(role.equals("Green Agent")) {
						Button.setBackground(Color.green);
					} else Button.setBackground(Color.MAGENTA);					
					button.add(Button);
					
				}else{
					JButton Button = new JButton("<html>"+text +"<br>"+ role);
					Button.setPreferredSize(new Dimension(150, 80));
					button.add(Button);
				}	
			}	
		}		


		//rest of team's turn (both blue and red team and green)
		else if(board.getTeamTurn() == 1 || board.getTeamTurn() == 3 || board.getTeamTurn() == 5){
			messageBoard.setText("   It is currently " + board.getTurn() + "'s turn---> You have- " + board.getCount() + " guesses and the clue is: " + clue + "      ");
			for(int i = 0; i < 25; i++) {
				String text = String.valueOf(board.getLocation().get(i).getPerson());
				String role = board.getLocation().get(i).getTeam();
				
				if(board.getLocation().get(i).getRevealed()) {
					JButton Button = new JButton(role);
					Button.setPreferredSize(new Dimension(150,80));
					if(role.equals("Red Agent")) {
						Button.setBackground(Color.red);
					} else if(role.equals("Blue Agent")) {
						Button.setBackground(Color.blue);
					} else if(role.equals("Innocent Bystander")) {
						Button.setBackground(Color.white);
					} else if(role.equals("Green Agent")) {
						Button.setBackground(Color.green);
					}else Button.setBackground(Color.MAGENTA);
					button.add(Button);
					
				}
				else {
					JButton Button = new JButton(text);
					Button.setPreferredSize(new Dimension(150, 80));
					Button.addActionListener(new codeWordsActionHandler(board,this));
					button.add(Button);
					
				}
			}
			endTurn.setVisible(true);
			submit.setVisible(false);
			textfield.setVisible(false);
			countTextField.setVisible(false);
			currentMessage.setVisible(false);
			enterCount.setVisible(false);
			
			UI.pack();
		}


		
		// checks if the board is in one of the winning states. 
		// If so, stops the game and brings you to a "Start New Game?"
		if(board.winning()) {
			JOptionPane.showMessageDialog(null, board.winningTeam());
			messageBoard.setText(board.winningTeam()); //make something in board to determine which team won.
			main.removeAll();
			gamePanel = new JPanel();
			JLabel startOver = new JLabel("Start New Game?   ");
			JButton yes = new JButton("Yes");
			JButton no = new JButton("No");
			if(board.getCurrentGame() == 1)
				yes.addActionListener(new NewGameActionHandler(board, button, this, main, gamePanel));
			else {
				yes.addActionListener(new NewThreeTeamGameActionHandler(board, button, this, main, gamePanel));
			}
			no.addActionListener(new ExitActionHandler());
			gamePanel.setLayout(new FlowLayout());
			gamePanel.add(startOver);
			gamePanel.add(yes);
			gamePanel.add(no);
			main.add(gamePanel);
		}
		
		
		UI.add(main);
		UI.pack(); 
		UI.setLocationRelativeTo(null);
		
		
		updateJFrameIfNotHeadless();

	}

	public void updateJFrameIfNotHeadless() {
		if(d != null) {
			d.updateJFrame();
		}
	}

	/**
	 * switch teams, then message that teams switched
	 */
	public void teamSwitch() {
		messageSwitchTeam();
	}
	
	/**
	 * Message to display when a turn changes.
	 */
	private void messageSwitchTeam() {
		String team = "";
		String nextTeam = "";
		team = board.getTurn() + " ";
		board.switchTeamMethod();
		nextTeam = board.getTurn() + " ";

		JOptionPane.showMessageDialog(null, team + "'s turn has ended. Now it is "
				+ nextTeam + "'s turn.");
	}
		
	/**
	 * check if spy turn, then switch turn, reset board.
	 */
	public void endSpyTurn() {
		if (board.getTurn().equals("Spy Turn")) {
			board.updatepoints();
		}
	}
	
	/**Get main JFrame.*/
	public JFrame getUI() {
		return UI;
	}

	/**Set the hint to true/false value. */
	public void setHint(boolean h) {
		hint = h;
	}
	
	/**Set the clue to a specified word. */
	public void setClue(String c) {
		clue = c;
	}
	
	/**To make the image invisible in the main panel. */
	public void disableimage() {
		this.image.setVisible(false);
	}
}
