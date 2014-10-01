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

	/**
	 * Removes a door from the tile
	 * @param direction The wall from which to remove the door
	 */
	public void removeDoor(Direction direction){
		doors[direction.ordinal()] = false;
	}

	/**
	 * Returns a new Tile object which has a Door wherever either this
	 * Tile or the parameter Tile has a Door.
	 * @param other The Tile to combine this tile with
	 */
	public Tile combineWith(Tile other){
		return new Tile(
			doors[0]||other.doors[0],
			doors[1]||other.doors[1],
			doors[2]||other.doors[2],
			doors[3]||other.doors[3]);
	}

	/**
	 * returns the name of the map, "mapX.png" wherex is up to four ints signifying a door (0=north)
	 * @return filename
	 */
	public String getFilename(){
		StringBuilder sb = new StringBuilder();
		sb.append("maps/Map");
		if(doors[0]){sb.append("0");}
		if(doors[1]){sb.append("1");}
		if(doors[2]){sb.append("2");}
		if(doors[3]){sb.append("3");}
		sb.append(".png");
		return sb.toString();
	}

	public boolean[] getDoors(){
		return doors;
	}
}
