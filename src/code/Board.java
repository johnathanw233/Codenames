package code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;


public class Board{	
	/** 25 locations on board. */
	private ArrayList<Location> locations; 
	
	/** 25 randomly selected codenames. */
	private String[] listForCodenames;
	
	/** 25 Person with assigned roles place randomly in 25 locations. */
	private String[] listForRoles;
	
	/**
	 * Specify the team in each turn.
	 * team turns: 0 Red Spymaster		1 Red Team
	 * 			   2 Blue Spymaster		3 Blue Team
	 * 			   4 Green Spymaster	5 Green Team
	 */
	private int teamTurn;
	
	/** Version of game in which 1 is 2-teams game, 2 is 3-teams game*/
	private int currentGame;
	
	/** Number of locations on board*/
	private int _size;
	
	/** Number of revealed red agents. */
	private int redTeamRevealed;
	
	/** Number of revealed blue agents. */
	private int blueTeamRevealed;
	
	/** Number of revealed green agents. */
	private int greenTeamRevealed;
	
	/** Number of revealed assassin. */
	private int assassinRevealed;
	
	/** Number of innocent bystander. */
	private int ib;
	
	/** A list of observers. */
	private ArrayList<Observer> observers;
	
	/** Number of locations related to the given clue. */
	private int count;
	
	/** Whether red team loses or not. */
	private boolean redLoss;
	
	/** Whether blue team loses or not. */
	private boolean blueLoss;
	
	/** Whether green team loses or not. */
	private boolean greenLoss;
	
//	private boolean redTurn;
//	private boolean blueTurn;
//	private boolean spyTurn;
	
	/**
	 * Constructs a Board class that generates codenames and creates a board with 25 locations.
	 * it also sets _size to the size of the board and
	 * starts the game at red team's turn. (teamTurn = 1 is Red and teamTurn = 2 is Blue)
	 * 
	 */
	public Board() {
		selectCodenames();
		generateAssignments();
		locations = new ArrayList<Location>();
		for(int i = 0; i < 25; i++) {
			locations .add(new Location(listForCodenames[i], listForRoles[i]));
		}
		teamTurn = 0;
		_size = locations.size();
		observers = new ArrayList<Observer>();
		 redTeamRevealed = 0;
		 blueTeamRevealed = 0;
		 assassinRevealed = 0;
		 greenTeamRevealed = 0;
		 ib = 0;
		 redLoss = false;
		 blueLoss = false;
		 greenLoss = false;
	}
	
    /**
     * Sets up all the instance variables for a new game (two team version).
     */
	public void newGame() {
		currentGame = 1;
		selectCodenames();
		generateAssignments();
		locations = new ArrayList<Location>();
		for(int i = 0; i < 25; i++) {
			locations .add(new Location(listForCodenames[i], listForRoles[i]));
		}
		teamTurn = 0;
		_size = locations.size();
		 redTeamRevealed = 0;
		 blueTeamRevealed = 0;
		 assassinRevealed = 0;
		 count = 1;
		 notifyObservers();
	}
	
	/**
     * Sets up all the instance variables for a new game (three teams version).
     */
	public void newThreePlayerGame() {
		currentGame = 2;
		selectCodenames();
		generateAssignments();
		locations = new ArrayList<Location>();
		for(int i = 0; i < 25; i++) {
			locations .add(new Location(listForCodenames[i], listForRoles[i]));
		}
		teamTurn = 0;
		_size = locations.size();
		 redTeamRevealed = 0;
		 blueTeamRevealed = 0;
		 assassinRevealed = 0;
		 greenTeamRevealed = 0;
		 redLoss = false;
		 blueLoss = false;
		 greenLoss = false;
		 count = 1;
		 notifyObservers();
	}
	
	/**
	 * Returns whether spymaster or team's turn.
	 * @return	the current team's turn. 1 is Red team and 2 is Blue team.
	 */
	public String getTurn() {
		if(teamTurn == 1) {
			return "Red Team";
		}
		if (teamTurn == 0) {
			return "Red Spymaster";
		}
		else if(teamTurn == 2) {
			return "Blue Spymaster";
		}
		else if(teamTurn == 3)
			return "Blue Team";
		else if(teamTurn == 4)
			return "Green Spymaster";
		else return "Green Team";
		
	}
	
	/**
	 * Returns an ArrayList of Strings from GameWords.txt. This method will take
	 * each line from GameWords.txt and place them in a different index in the ArrayList.
	 * 
	 * @return      the ArrayList of strings in GameWords.txt
	 */
	public ArrayList<String> readcodename() {
		ArrayList<String> list=new ArrayList<String>();
		try {
			for(String line : Files.readAllLines(Paths.get("src\\Text\\GameWords.txt"))){
				list.add(line);
			}
		}
		catch (IOException e) {

		}
		return list;
	}

	/**
	 * Updation of each team's score.
	 */
	public void updatepoints() {
		redTeamRevealed = 0;
		blueTeamRevealed = 0;
		greenTeamRevealed=0;
		assassinRevealed = 0;
		ib=0;
		for(int i = 0; i < locations.size(); i++){
			if(locations.get(i).getRevealed() && locations.get(i).getTeam().equals("Red Agent")){
				redTeamRevealed++;
			}
			if(locations.get(i).getRevealed() && locations.get(i).getTeam().equals("Blue Agent")){
				blueTeamRevealed++;
			}
			if(locations.get(i).getRevealed() && locations.get(i).getTeam().equals("Green Agent")){
				greenTeamRevealed++;
			}
			if(locations.get(i).getRevealed() && locations.get(i).getTeam().equals("Assassin")){
				assassinRevealed++;
			}
			if(locations.get(i).getRevealed() && locations.get(i).getTeam().equals("Innocent Bystander")) {
				ib++;
			}
		}
	}
	
	/**
	 * 
	 * @return	Which team is `ning.
	 */
	public String winningTeam() {
		if(currentGame == 2) {
		if (blueTeamRevealed == 5 && blueLoss == false) {
			return "Blue Team won";
		}
		if (redTeamRevealed == 6 && redLoss == false) {
			return "Red Team won";
		}
		if (greenTeamRevealed == 5 && greenLoss == false) {
			return "Green Team won";
		}

		if(assassinRevealed == 2) {
			return AssassinMethod();
		}
	}
		else {
			if (blueTeamRevealed == 8) {
				return "Blue Team won";
			}// If BlueAgent revealed 8 BlueAgents, they win.
			if (redTeamRevealed == 9) {
				return "Red Team won";
			}
			if(assassinRevealed == 1) {
				return AssassinMethod();
			}
		}
		return "GJ guys you found a bug.";
	}
		
	/**
	 * Method defined which correctly returns whether or not the Board is in one of the winning states
	 * @return	Return true if winning or false.
	 */
	public boolean winning() {
		if(currentGame == 2) {
			if (blueTeamRevealed == 5 && blueLoss == false) {
				return true;
			}// If BlueAgents revealed 5 BlueAgents, they win.
			if (redTeamRevealed == 6 && redLoss == false ) {
				return true;
			}// If RedAgents revealed 6 RedAgents, they win.
			if (greenTeamRevealed == 5 && greenLoss == false) {
				return true;
			}// If GreenAgents revealed 5 RedAgents, they win.
	
			/////// NEEDS TO BE MODIFIED AS 2 ASSASSINS NOW
			
			if (assassinRevealed == 2) {
				return true;
			}//If BlueAgent or RedAgent revealed assassin, The other team wins.
		}
		else {
			if (blueTeamRevealed == 8) {
				return true;
			}// If BlueAgent revealed 8 BlueAgents, they win.
			if (redTeamRevealed == 9) {
				return true;
			}// If RedAgent revealed 9 RedAgents, they win.

			if (assassinRevealed == 1) {
				return true;
			}//If BlueAgent or RedAgent revealed assassin, The other team wins.
		}
			return false;
	}
	
	/**
	 * 2-teams version:
	 * Handles the case when the assassin is revealed by a team then the other team wins.
	 * 
	 * 3-teams version:
	 * Handles the case when first assassin is revealed by a team then that team loses.
	 * And, handles another case when second assassin is revealed by a team then one team wins.
	 * @return	Winning team.
	 */
	public String AssassinMethod(){
		if(currentGame == 2) {
		if(teamTurn == 1 && assassinRevealed == 1){
			redLoss = true;
			return "Red Team Loses";
		}
		else if(teamTurn == 3 && assassinRevealed == 1){
			blueLoss = true;
			return "Blue Team Loses";
		}
		else if(teamTurn == 5 && assassinRevealed == 1){
			greenLoss = true;
			return "Green Team Loses";
		}
		else if(assassinRevealed == 2 && !redLoss){
			return "Red Team Wins";
		}
		else if(assassinRevealed == 2 && !blueLoss) {
			return "Blue Team Wins";
		}
		else if(assassinRevealed == 2 && !greenLoss) {
			return "Green Team Wins";
		}
		else return "Blue Team Wins";
		}
		else {
			if(teamTurn == 1 && assassinRevealed == 1){
				return "Blue Team Wins";
			}
			else if(teamTurn == 3 && assassinRevealed == 1){
				return "Red Team Wins";
			}
			else{
				return "Assassin is not revealed";
			}
		}
		
		
	}

	
	/**
	 * selectCodenames() will store the array listForCodenames[] with codenames in GameWords.txt
	 * by calling the readcodenames() method. The codenames will also be randomized using the Random
	 * class.
	 */
	public void selectCodenames(){
		ArrayList<String> readcodename = readcodename();
		ArrayList<String> x = new ArrayList<String>();
		for(int i=0;i<readcodename.size();i++) {
			x.add(readcodename.get(i));
		}

		listForCodenames = new String[25];

		Random rand = new Random();
		int a = rand.nextInt(x.size());
		for(int i=0;i<25;i++) {
			a = rand.nextInt(x.size());
			listForCodenames[i]= x.get(a);
			x.remove(a);
		}
	}	

	/**
	 *  generateAssignments() will store the array listForRoles[] with
	 *  either "Red Agent". "Blue Agent", "Innocent Bystander", or "Assassin"
	 *  and randomizes the order in the array.
	 */
	public void generateAssignments(){

		listForRoles = new String[25];

		String [] y = new String[25];
		if(currentGame == 2) {
			for(int i=0;i<6;i++) 
			{
				y[i]="Red Agent";// 6 Red Agents
			}
			for(int i=6;i<11;i++) 
			{
				y[i]="Blue Agent";// 5 Blue Agents
			}
			for(int i=11;i<16;i++) 
			{
				y[i]="Green Agent";//5 Green Agents
			}
			for(int i=16;i<23;i++) {
				y[i]="Innocent Bystander";//7 Innocent Bystanders
			}
			y[23]= "Assassin";
			y[24]= "Assassin";
		}
		else {
			for(int i=0;i<9;i++) 
			{
				y[i]="Red Agent";
			}
			for(int i=9;i<17;i++) 
			{
				y[i]="Blue Agent";
			}
			for(int i=17;i<24;i++) 
			{
				y[i]="Innocent Bystander";
			}
			y[24]= "Assassin";
		}
		ArrayList<Integer> x = new ArrayList<Integer>();
		for(int i=0;i<25;i++) {
			x.add(i);
		}
		Random rand = new Random();
		int a = rand.nextInt(x.size());
		for(int i=0;i<25;i++) {
			a = rand.nextInt(x.size());
			listForRoles[i]= y[x.get(a)];
			x.remove(a);
		}
	}

	/**
	 * Decrements the count, updates a Location when the Location's codename was 
	 * selected, and returns if the Location contained the current team's Agent
	 * 
	 * @param  clue  the clue the super agent gives to each player
	 * @return true if the clue is legal.
	 *         false if the clue is illegial
	 */
	public boolean checkLegalClue(String clue) {
		boolean clues = true;
		for(int i = 0; i < locations.size(); i++) {
			if(locations.get(i).getPerson().equals(clue) || locations.get(i).getPerson().toLowerCase().equals(clue)) {
			//	JOptionPane.showMessageDialog(null, "The clue is illegal. Please enter another.");
				clues = false;
			}
			if(locations.get(i).getRevealed()) {
				clues = true;
			}
			if(clue == "") {
				clues = false;
			}
		}
		notifyObservers();
		return clues;
	}
	
	/**
	 * Method defined which decrements the count, 
	 * updates a Location when the Location's codename was selected,
	 * and ****** returns if the Location contained the current team's Agent 
	 * 
	 * @param selectedCodename the codename that the player selected
	 * @return true if the codename is on the current turn's team
	 *         false otherwise
	 */
	public boolean updateLocation(String selectedCodename) {
		boolean loc = false;
		AssassinMethod();
		for (int i = 0; i < locations.size(); i++) {
			if(locations.get(i).getPerson().equals(selectedCodename)) {
				locations.get(i).reveal();
				if((locations.get(i).getTeam().equals("Red Agent") && teamTurn == 1) 
						|| (locations.get(i).getTeam().equals("Blue Agent") && teamTurn == 3)
							|| (locations.get(i).getTeam().equals("Green Agent") && teamTurn == 5)){
					count--;
					loc = true;
				}
			}
		}
		notifyObservers();
		return loc;
	}
		
	/**
	 * Team switching after a certain turn has ended due to any circumstance.
	 */
	public void switchTeamMethod() {
		if(currentGame == 2) {
			if (teamTurn == 0) {
				teamTurn = 1;
			}
			else if (teamTurn == 1) {
				teamTurn = 2;
				count = 1;
			}	
			else if (teamTurn == 2) {
				teamTurn = 3;
			}
			else if(teamTurn == 3) {
				teamTurn = 4;
				count = 1;
			}
			else if (teamTurn == 4) {
				teamTurn = 5;
			}
			else if(teamTurn == 5) {
				teamTurn = 0;
				count = 1;
			}
			
			if(teamTurn == 0 && redLoss) {
				teamTurn = 2;
			}
			else if(teamTurn == 2 && blueLoss) {
				teamTurn = 4;
			}
			else if(teamTurn == 4 && greenLoss) {
				teamTurn = 0;
			}
		}
		else {
			if (teamTurn == 0) {
				teamTurn = 1;
			}
			else if (teamTurn == 1) {
				teamTurn = 2;
				count = 1;
			}	
			else if (teamTurn == 2) {
				teamTurn = 3;
			}
			else if(teamTurn == 3) {
				teamTurn = 0;
				count = 1;
			}
		}
		notifyObservers();
	}
		
	/**
	 * EASTER EGGGGGGG!!!!!!!!!!!!!!!
	 * @return	True if easter egg scenario.
	 */
	public boolean easterEgg() {
		if(ib == 7) {
			return true;
		}
		else 
			return false;
	}
	
	/**
	 * EASTER EGGGGGGG!!!!!!!!!!!!!!! PART TWO
	 * @return	True if easter egg scenario.
	 */		
	public boolean AssassinEasterEgg() {
		if(assassinRevealed == 1 && !redLoss && !blueLoss && !greenLoss) {
			AssassinMethod();
			return true;
		}
			return false;
	}
	
	public void addObserver(Observer obs) {
		observers.add(obs);
		notifyObservers();
	}
	
	public void notifyObservers() {
		for(Observer ob:observers) {
			ob.update();
		}
	}
	
	/** getSize() returns the size of the board */
	public int getSize() {
		return _size;
	}
	/** getLocation() returns an ArrayList of Locations containing the entire board of locations. */
	public ArrayList<Location> getLocation(){
		return locations;
	}	
	/** setTurn(int x) sets current turn to x (1 or 2) -- (red or blue) */
	public void setTurn(int x ) {
		teamTurn = x;
	}
	/** getListForCodenames() returns 25 random codenames in array */
	public String[] getListForCodenames() {
		return listForCodenames;
	}
	/** getListForRoles() returns 25 specified roles in array */
	public String[] getListForRoles() {
		return listForRoles;
	}
	/** getTeamTurn() returns which team's turn in integer */
	public int getTeamTurn() {
		return teamTurn;
	}
	/** set count to a number */
	public void setCount(int count) {
		this.count = count;
	}
	/** set loses*/
	public void setTeamlose(boolean red,boolean blue, boolean green) {
		redLoss = red;
		blueLoss = blue;
		greenLoss = green;
	}
	/** getCount() returns the count */
	public int getCount() {
		return count;
	}
	/** getCurrentGame() returns the game version either 3-team version or 2-team version */
	public int getCurrentGame() {
		return currentGame;
	}
}