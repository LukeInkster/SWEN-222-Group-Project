package project.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import project.game.*;

public class PlayerTests {
	
	@Test
	public void testAddItem() {
		Player p = dummyPlayer();
		assertTrue(p.getItems().size()==0);
		p.addItem(new Door());
		assertTrue(p.getItems().size()==1);
	}
	
	@Test
	public void testRemoveItem() {
		Player p = dummyPlayer();
		Door d = new Door();
		p.addItem(d);
		assertTrue(p.getItems().size()==1);
		p.removeItem(d);
		assertTrue(p.getItems().size()==0);
	}
	
	@Test
	public void testSetLocation() {
		Player p = dummyPlayer();
		p.setLocation(new Location(1,2));
		Location newLoc = p.getLocation();
		assertTrue(newLoc.getX()==1 && newLoc.getY()==2);
	}
	
	private Player dummyPlayer(){
		return new Player(new Location(0,0));
	}
}
