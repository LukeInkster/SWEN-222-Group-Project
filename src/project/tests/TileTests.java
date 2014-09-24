package project.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import project.game.Direction;
import project.game.Tile;

public class TileTests {
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
