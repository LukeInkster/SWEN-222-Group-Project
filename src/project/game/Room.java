package project.game;

public class Room {
	public static final int ROOM_WIDTH = 9;
	public static final int ROOM_HEIGHT = 9;
	private Location[][] grid = new Location[ROOM_WIDTH][ROOM_HEIGHT];
	
	// Tile controls the placement of doors in the room
	private Tile tile;
	
	// X & Y coordinates of this room within the game grid
	private final int x;
	private final int y;
	
	//True if this is the end of the maze
	private final boolean isEnd;
	
	public Room(int x, int y, boolean isEnd){
		this.x = x;
		this.y = y;
		this.isEnd = isEnd;
	}
	
	/**
	 * Returns the Location at the coordinates (x,y) in the room or null
	 * if the x or y coordinate is outside the bounds of the room
	 */
	public Location location(int x, int y){
		if(x<0 || y<0 || x>=ROOM_WIDTH || y>=ROOM_HEIGHT) return null;
		return grid[x][y];
	}
	
	/**
	 * Returns the grid of locations in the Room
	 */
	public Location[][] getGrid(){
		return grid;
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
	
	public Location getDoorLoc(Direction direction){
		if(direction == Direction.NORTH) return location(Room.ROOM_WIDTH/2, 0);
		if(direction == Direction.EAST)  return location(Room.ROOM_WIDTH-1, Room.ROOM_HEIGHT/2);
		if(direction == Direction.SOUTH) return location(Room.ROOM_WIDTH/2, Room.ROOM_HEIGHT-1);
		else							 return location(0, Room.ROOM_HEIGHT/2);
	}
}
