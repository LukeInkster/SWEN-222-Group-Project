package project.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Graphics;
import java.util.List;

import org.junit.Test;

import project.GUI.InventoryBar;
import project.GUI.MiniMap;
import project.game.Direction;
import project.game.Door;
import project.game.Game;
import project.game.Item;
import project.game.Key;
import project.game.Location;
import project.game.Player;
import project.game.Room;
import project.game.Tile;
import project.utils.GameUtils;

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
		assertFalse(loc.hasEntity());
		assertTrue(loc.getX()==x);
		assertTrue(loc.getY()==y);
	}

	@Test
	public void testSetItem() {
		Location loc = dummyLocation();
		loc.setEntity(new Door());
		assertTrue(loc.hasEntity());
	}

	@Test
	public void testPeekItem() {
		Location loc = dummyLocation();
		Door d = new Door();
		loc.setEntity(d);
		assertTrue(loc.peekItem()==d);
		assertTrue(loc.hasEntity());
	}

	@Test
	public void testTakeItem() {
		Location loc = dummyLocation();
		Door d = new Door();
		loc.setEntity(d);
		assertTrue(loc.takeEntity()==d);
		assertFalse(loc.hasEntity());
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
		return new Player(1);
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

	@Test
	public void testGetFilename() {
		Tile t1 = new Tile(false,true,false,false);
		Tile t2 = new Tile(true,true,true,true);

		assertTrue(t1.getFilename().equals("assets\\maps\\Map1.png"));
		assertTrue(t2.getFilename().equals("assets\\maps\\Map0123.png"));
		assertFalse(t2.getFilename().equals("assets\\maps\\Map123.png"));
	}

	// ============================================================= //
	// == MOVEMENT TESTS == //
	// ============================================================= //

	@Test
	public void testStandardMove() {
		Game game = new Game();
		Player p = new Player(1);
		game.addPlayer(p);
		assertTrue(p.getLocation().equals(game.startLocation()));

		// Move one step EAST and ensure the player is in the correct location
		assertTrue(game.movePlayer(p, Direction.EAST));
		Location oneStepEast = game.room(Game.GAME_WIDTH/2, Game.GAME_HEIGHT/2)
							   .location(Room.ROOM_WIDTH/2+1, Room.ROOM_HEIGHT/2);
		assertTrue(p.getLocation().equals(oneStepEast));
	}

	@Test
	public void testDoorMove() {
		Game game = new Game();
		Player p = new Player(1);
		game.addPlayer(p);

		Room zeroZero = game.room(0, 0);
		zeroZero.setTile(new Tile(true,true,true,true));

		Room zeroOne = game.room(0, 1);
		zeroOne.setTile(new Tile(true,true,true,true));

		p.setLocation(zeroZero.getDoorLocation(Direction.SOUTH));
		assertTrue(game.movePlayer(p, Direction.SOUTH));

		Location oneStepSouth = zeroOne.getDoorLocation(Direction.NORTH);
		assertTrue(p.getLocation().equals(oneStepSouth));
	}

	@Test
	public void testInvalidMove() {
		Game game = new Game();
		Player p = new Player(1);
		game.addPlayer(p);
		Location start = game.startLocation().getRoom().location(0, 0); //Top left of start room
		p.setLocation(start);

		assertTrue(p.getLocation().equals(start));
		assertFalse(game.movePlayer(p, Direction.NORTH));
		assertTrue(p.getLocation().equals(start));
	}

	// ============================================================= //
	// == GAMESERIALIZE TESTS == //
	// ============================================================= //

	@Test
	public void testSave() {
		Player player= new Player(69);
		player.addItem(new Key());
		Room room = new Room(3,3,false);
		room.setTile(new Tile(true,true,false,true));
		player.setLocation(new Location(room,4,4));
		player.setOrientation(Direction.NORTH);
		String save = GameUtils.save(player);
		assertTrue(save.equals("PLAYER<ID<69>ITEMS<KEY<>>LOCATION<x<4>y<4>ROOM<x<3>y<3>ISEND<false>>TILE<true;true;false;true;>>ORIENTATION<NORTH>ROOMSVISITED<ROOM<x<3>y<3>ISEND<false>>>"));
	}


	@Test
	public void testLoad() {
		Player player = new Player(69);
		player.addItem(new Key());
		player.addItem(new Tile(true,true,false,true));
		player.setLocation(new Location(new Room(4,4,false),6,6));
		Room room = new Room(3,3,false);
		room.setTile(new Tile(true,true,false,true));
		player.setLocation(new Location(room,5,5));
		player.setOrientation(Direction.NORTH);
		String save = GameUtils.save(player);
		player = GameUtils.load(save);
		assertEquals(player.getId(),69);
		List<Item> items = player.getItems();
		assertTrue(player.getItems().size()==2);
		assertTrue(player.getItems().get(1).getFilename().equals("assets\\maps\\Map013.png"));
		assertTrue(player.getItems().get(0).getFilename().equals("assets\\Key.png"));
		assertTrue(player.getLocation().getX()==5);
		assertTrue(player.getRoomsVisited().size()==2);
		assertTrue(player.getLocation().getRoom().hasDoor(Direction.NORTH));

	}


}
