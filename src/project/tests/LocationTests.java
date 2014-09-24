package project.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import project.game.Door;
import project.game.Location;

public class LocationTests {
	@Test
	public void testConstructor() {
		int x = 1;
		int y = 2;
		Location loc = new Location(x,y);
		assertFalse(loc.hasItem());
		assertTrue(loc.getX()==x);
		assertTrue(loc.getY()==y);
	}	
	
	@Test
	public void testSetItem() {
		Location loc = dummyLocation();
		loc.setItem(new Door());
		assertTrue(loc.hasItem());
	}
	
	@Test
	public void testPeekItem() {
		Location loc = dummyLocation();
		Door d = new Door();
		loc.setItem(d);
		assertTrue(loc.peekItem()==d);
		assertTrue(loc.hasItem());
	}
	
	@Test
	public void testTakeItem() {
		Location loc = dummyLocation();
		Door d = new Door();
		loc.setItem(d);
		assertTrue(loc.takeItem()==d);
		assertFalse(loc.hasItem());
	}
	
	private Location dummyLocation(){
		return new Location(0,0);
	}
}
