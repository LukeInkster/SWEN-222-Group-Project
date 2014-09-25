package project.game;

public class Location {
	// X & Y coordinates of this Location within the Room grid
	private final int x;
	private final int y;
	
	private Item item;
	
	private Room room;
	
	public Location(Room room, int x, int y){
		this.room = room;
		this.x = x;
		this.y = y;
	}

	/**
	 * @return True if there is an item at this location
	 */
	public boolean hasItem(){
		return item != null;
	}
	
	/**
	 * Returns the item on this location. DOES NOT remove it.
	 * @return The item on this location, null if there is no item
	 */
	public Item peekItem(){
		return item;
	}
	
	/**
	 * Returns the item on this location and removes it from this location	 
	 * @return The item on this location, null if there is no item
	 */
	public Item takeItem(){
		Item result = this.item;
		this.item = null;
		return result;
	}
	
	/**
	 * Sets the item on this location to the parameter item and
	 * returns the item that was already on the location	 
	 * @param item The item to put on this location
	 * @return The item that was already on the location, null if there was none
	 */
	public Item setItem(Item item){
		Item result = this.item;
		this.item = item;
		return result;
	}

	/**
	 * @return the x coordinate of this Location in the Room
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y coordinate of this Location in the Room
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * Returns true if this location is in front of a door on the
	 * wall specified by the parameter
	 * @param direction The wall to check
	 */
	public boolean isDoor(Direction direction) {
		if(direction == Direction.NORTH && x == Room.ROOM_WIDTH/2 && y==0) return true;
		if(direction == Direction.EAST && x == Room.ROOM_WIDTH-1 && y==Room.ROOM_HEIGHT/2) return true;
		if(direction == Direction.SOUTH && x == 0 && y==Room.ROOM_HEIGHT/2) return true;
		if(direction == Direction.WEST && x == Room.ROOM_WIDTH/2 && y==Room.ROOM_HEIGHT-1) return true;
		return false;
	}
}
