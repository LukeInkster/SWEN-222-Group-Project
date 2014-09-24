package project.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import project.game.Direction;

public class DirectionTests {
	
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
}
