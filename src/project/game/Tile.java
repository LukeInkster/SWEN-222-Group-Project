package project.game;

public class Tile extends Item {
	private boolean[] doors;
	
	public Tile(boolean hasDoorNorth, boolean hasDoorEast, 
				boolean hasDoorSouth, boolean hasDoorWest){
		doors = new boolean[4];
		doors[0] = hasDoorNorth;
		doors[1] = hasDoorEast;
		doors[2] = hasDoorSouth;
		doors[3] = hasDoorWest;
	}
	
	/**
	 * Returns true if the tile has a door in on the wall
	 * specified by the parameter direction.
	 */
	public boolean hasDoor(Direction direction){
		return doors[direction.ordinal()];
	}
	
	/**
	 * Adds a door to the tile
	 * @param direction The wall on which to add the door
	 */
	public void addDoor(Direction direction){
		doors[direction.ordinal()] = true;
	}
}
