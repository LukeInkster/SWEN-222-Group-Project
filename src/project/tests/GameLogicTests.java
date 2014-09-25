package project.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import project.game.Direction;
import project.game.Door;
import project.game.Location;
import project.game.Player;
import project.game.Room;
import project.game.Tile;

public class GameLogicTests {
	
	// ============================================================= //
	// == DIRECTION TESTS == //
	// ============================================================= //
	
	@Test
	public void testClockwise() {
		assertTrue(Direction.NORTH.clockwise() == Direction.EAST);
		assertTrue(Direction.EAST.clockwise() == Direction.SOUTH);
		assertTrue(Direction.SOUTH.clockwise() == Direction.WEST);
		assertTrue(Direction.WEST.clockwise() == Direction.NORTH);
	}
	
	@Test
	public void testAnticlockwise() {
		assertTrue(Direction.NORTH.anticlockwise() == Direction.WEST);
		assertTrue(Direction.WEST.anticlockwise() == Direction.SOUTH);
		assertTrue(Direction.SOUTH.anticlockwise() == Direction.EAST);
		assertTrue(Direction.EAST.anticlockwise() == Direction.NORTH);
	}
	
	@Test
	public void testOpposite() {
		assertTrue(Direction.NORTH.opposite() == Direction.SOUTH);
		assertTrue(Direction.WEST.opposite() == Direction.EAST);
		assertTrue(Direction.SOUTH.opposite() == Direction.NORTH);
		assertTrue(Direction.EAST.opposite() == Direction.WEST);
	}
	
	// ============================================================= //
	// == LOCATION TESTS == //
	// ============================================================= //
	
	@Test
	public void testConstructor() {
		int x = 1;
		int y = 2;
		Location loc = new Location(null, x,y);
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
		return new Location(null, 0,0);
	}
	
	// ============================================================= //
	// == PLAYER TESTS == //
	// ============================================================= //
	
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
		p.setLocation(new Location(null, 1,2));
		Location newLoc = p.getLocation();
		assertTrue(newLoc.getX()==1 && newLoc.getY()==2);
	}
	
	private Player dummyPlayer(){
		return new Player(new Location(null, 0,0));
	}
	
	// ============================================================= //
	// == TILE TESTS == //
	// ============================================================= //
	
	@Test
	public void testConstructor1() {
		Tile t = new Tile(false,false,false,false);
		assertFalse(t.hasDoor(Direction.NORTH));
		assertFalse(t.hasDoor(Direction.EAST));
		assertFalse(t.hasDoor(Direction.SOUTH));
		assertFalse(t.hasDoor(Direction.WEST));
	}
	
	@Test
	public void testConstructor2() {
		Tile t = new Tile(true,false,false,false);
		assertTrue(t.hasDoor(Direction.NORTH));
		assertFalse(t.hasDoor(Direction.EAST));
		assertFalse(t.hasDoor(Direction.SOUTH));
		assertFalse(t.hasDoor(Direction.WEST));
	}
	
	@Test
	public void testConstructor3() {
		Tile t = new Tile(true,false,true,false);
		assertTrue(t.hasDoor(Direction.NORTH));
		assertFalse(t.hasDoor(Direction.EAST));
		assertTrue(t.hasDoor(Direction.SOUTH));
		assertFalse(t.hasDoor(Direction.WEST));
	}
	
	@Test
	public void testConstructor4() {
		Tile t = new Tile(true,true,true,true);
		assertTrue(t.hasDoor(Direction.NORTH));
		assertTrue(t.hasDoor(Direction.EAST));
		assertTrue(t.hasDoor(Direction.SOUTH));
		assertTrue(t.hasDoor(Direction.WEST));
	}
	
	@Test
	public void testAddDoor() {
		Tile t = new Tile(true,false,false,false);
		t.addDoor(Direction.EAST);
		assertTrue(t.hasDoor(Direction.NORTH));
		assertTrue(t.hasDoor(Direction.EAST));
		assertFalse(t.hasDoor(Direction.SOUTH));
		assertFalse(t.hasDoor(Direction.WEST));
	}
	
	@Test
	public void testRemoveDoor() {
		Tile t = new Tile(true,true,false,false);
		t.removeDoor(Direction.EAST);
		assertTrue(t.hasDoor(Direction.NORTH));
		assertFalse(t.hasDoor(Direction.EAST));
		assertFalse(t.hasDoor(Direction.SOUTH));
		assertFalse(t.hasDoor(Direction.WEST));
	}
	
	@Test
	public void testCombine1() {
		Tile t1 = new Tile(true,false,false,false);
		Tile t2 = new Tile(false,false,true,false);
		Tile t3 = t1.combineWith(t2);
		
		assertTrue(t3.hasDoor(Direction.NORTH));
		assertFalse(t3.hasDoor(Direction.EAST));
		assertTrue(t3.hasDoor(Direction.SOUTH));
		assertFalse(t3.hasDoor(Direction.WEST));
	}
	
	@Test
	public void testCombine2() {
		Tile t1 = new Tile(false,true,false,false);
		Tile t2 = new Tile(false,true,false,false);
		Tile t3 = t1.combineWith(t2);
		
		assertFalse(t3.hasDoor(Direction.NORTH));
		assertTrue(t3.hasDoor(Direction.EAST));
		assertFalse(t3.hasDoor(Direction.SOUTH));
		assertFalse(t3.hasDoor(Direction.WEST));
	}
	
	
	
	

}
