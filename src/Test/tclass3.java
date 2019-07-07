package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import code.Board;
import code.Location;

public class tclass3 {
	
	@Test
	public void selectCodenamestest3() {
		Board a = new Board();
		a.selectCodenames();
		assertEquals(25,a.getListForCodenames().length);
	
	}
	
	@Test
	public void generateAssignmentstest3() {	
		int Ra,Ba,Ib,A,Ga;
		Ra = 0;
		Ba =0;
		Ib =0;
		Ga=0;
		A =0;
		Board a = new Board();
		a.newThreePlayerGame();
		a.generateAssignments();
		
		for(int i=0; i<a.getListForRoles().length;i++) {
			if(a.getListForRoles()[i] != null && a.getListForRoles()[i].equals("Red Agent")) {
				Ra+= 1;
			}
			else if(a.getListForRoles()[i] != null && a.getListForRoles()[i].equals("Blue Agent")) {
				Ba+=1;
			}
			else if(a.getListForRoles()[i] != null && a.getListForRoles()[i].equals("Innocent Bystander")) {
				Ib+=1;
			}else if(a.getListForRoles()[i] != null && a.getListForRoles()[i].equals("Green Agent")) {
				Ga+=1;
			}else {
				A+=1;
			}
		}
		assertEquals(6,Ra);
		assertEquals(5,Ba);
		assertEquals(7,Ib);
		assertEquals(5,Ga);
		assertEquals(2,A);
	}
	
	/*Wining States test*/
	@Test
	public void winningTest3RevealRed() {
		Board x = new Board();
		x.newThreePlayerGame();
		ArrayList<Location> locations = new ArrayList<Location>();
		locations = x.getLocation();

		for(int i =0;i<locations.size();i++) {
			if(locations.get(i).getTeam() == "Red Agent") {
				locations.get(i).reveal();
				x.updatepoints();
			}
		}
		assertTrue(x.winning());
	}
	@Test
	public void winningTest3RevealBlue() {
		Board x = new Board();
		x.newThreePlayerGame();
		ArrayList<Location> locations = new ArrayList<Location>();
		locations = x.getLocation();
		for(int i =0;i<locations.size();i++) {
			if(locations.get(i).getTeam() == "Blue Agent") {
				locations.get(i).reveal();
				x.updatepoints();
			}
		}
		assertTrue(x.winning());
	
}
	@Test
	public void winningTest3RevealGreen() {
		Board x = new Board();
		x.newThreePlayerGame();
		ArrayList<Location> locations = new ArrayList<Location>();
		locations = x.getLocation();

		for(int i =0;i<locations.size();i++) {
			if(locations.get(i).getTeam() == "Green Agent") {
				locations.get(i).reveal();
				x.updatepoints();
			}
		}
		assertTrue(x.winning());
}
	@Test 
	public void winingTest3Assassin() {
		Board x = new Board();
		x.newThreePlayerGame();
		ArrayList<Location> locations = new ArrayList<Location>();
		locations = x.getLocation();
		for(int i =0;i<locations.size();i++) {
			if(locations.get(i).getTeam() == "Assassin") {
				locations.get(i).reveal();
				x.updatepoints();
			}
		}
		assertTrue(x.winning());
	}
/** Tests for Method defined which correctly returns which team wins once the 2nd Assassin is revealed*/
	@Test 
	public void RedwiningAssassinTest() {
		String hahaha = "Red Team Wins";
		Board x = new Board();
		x.newThreePlayerGame();
		ArrayList<Location> locations = new ArrayList<Location>();
		locations = x.getLocation();
		x.setTeamlose(false, true, true);
		for(int i =0;i<locations.size();i++) {
			if(locations.get(i).getTeam() == "Assassin") {
				locations.get(i).reveal();
				x.updatepoints();
			}
		}
		assertEquals(hahaha,x.winningTeam());
	}
	@Test 
	public void BluewiningAssassinTest() {
		String hahaha = "Blue Team Wins";
		Board x = new Board();
		x.newThreePlayerGame();
		ArrayList<Location> locations = new ArrayList<Location>();
		locations = x.getLocation();
		x.setTeamlose(true, false, true);
		for(int i =0;i<locations.size();i++) {
			if(locations.get(i).getTeam() == "Assassin") {
				locations.get(i).reveal();
				x.updatepoints();
			}
		}
		assertEquals(hahaha,x.winningTeam());
	}
	
/** Tests for Method which returns which is used at the end of a turn to determine the team whose move is
 *  next (this must include the possibility that the next team in the 3-team rotation had previously Revealed 
 *  an Assassin)*/
	@Test 
	public void BlueTeamTurnTest() {
		Board x = new Board();
		x.newThreePlayerGame();
		x.setTurn(0);
		x.switchTeamMethod();
		x.switchTeamMethod();
		x.switchTeamMethod();
		assertEquals(3, x.getTeamTurn());
	}
	@Test
	public void BlueTurnSkipTest() {
		Board x = new Board();
		x.newThreePlayerGame();
		x.setTeamlose(false, true, false);
		x.setTurn(0);
		x.switchTeamMethod();
		x.switchTeamMethod();
		x.switchTeamMethod();
		assertEquals(5, x.getTeamTurn());
	}
}
