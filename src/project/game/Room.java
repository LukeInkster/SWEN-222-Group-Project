package project.game;

import java.io.Serializable;

public class Room implements Serializable{
	// ------ CONSTANTS ------ //
	public static final int ROOM_WIDTH = 9;
	public static final int ROOM_HEIGHT = 9;

	// ------ VARIABLES ------ //
	// Grid of locations within the room
	private Location[][] locations = new Location[ROOM_WIDTH][ROOM_HEIGHT];

	// Tile controls the placement of doors in the room
	private Tile tile;

	// X & Y coordinates of this room within the game grid
	private final int x;
	private final int y;

	// True if this is the end of the maze
	private final boolean isEnd;

	public Room(int x, int y, boolean isEnd){
		this.x = x;
		this.y = y;
		this.isEnd = isEnd;
		this.generateLocations();
	}

	private void generateLocations(){
		for(int x=0;x<ROOM_WIDTH;x++){
			for(int y=0;y<ROOM_HEIGHT;y++){
				locations[x][y] = new Location(this,x,y);
			}
		}
	}

	/**
	 * Returns the Location at the coordinates (x,y) in the room or null
	 * if the x or y coordinate is outside the bounds of the room
	 */
	public Location location(int x, int y){
		if(x<0 || y<0 || x>=ROOM_WIDTH || y>=ROOM_HEIGHT) return null;
		return locations[x][y];
	}

	/**
	 * Returns the grid of locations in the Room
	 */
	public Location[][] getLocations(){
		return locations;
	}

	/**
	 * @return the x coordinate of this room in the game
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y coordinate of this room in the game
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return True if this room is the end of the maze
	 */
	public boolean isEnd() {
		return isEnd;
	}

	/**
	 * Returns true if the tile on this room has a door in on
	 * the wall specified by the parameter direction.
	 */
	public boolean hasDoor(Direction direction){
		return this.getTile().hasDoor(direction);
	}

	/**
	 * @return the Tile on this Room
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * @param tile the Tile to set
	 */
	public void setTile(Tile tile) {
		this.tile = tile;
	}

	/**
	 * Returns the Location in front of the door on the wall
	 * specified by the parameter direction
	 */
	public Location getDoorLocation(Direction dir){
		if(dir == Direction.NORTH)	return location(Room.ROOM_WIDTH/2, 0);
		if(dir == Direction.EAST)	return location(Room.ROOM_WIDTH-1, Room.ROOM_HEIGHT/2);
		if(dir == Direction.SOUTH)	return location(Room.ROOM_WIDTH/2, Room.ROOM_HEIGHT-1);
		else						return location(0, Room.ROOM_HEIGHT/2);
	}

	/**
	 * Returns the Location adjacent to the parameter Location
	 * in the parameter Direction, null if there is no adjacent
	 * Location in the given Direction.
	 */
	public Location adjecentLocation(Location loc, Direction dir){
		if(dir==Direction.NORTH)	return location(loc.getY(),loc.getX()-1);
		if(dir==Direction.EAST)		return location(loc.getY()+1,loc.getX());
		if(dir==Direction.SOUTH)	return location(loc.getY(),loc.getX()+1);
		else						return location(loc.getY()-1,loc.getX());
	}
}
