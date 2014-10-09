package project.game;

import java.io.Serializable;

public class Location implements Serializable{
	// X & Y coordinates of this Location within the Room grid
	private final int x;
	private final int y;

	private Entity entity;

	private Room room;

	public Location(Room room, int x, int y){
		this.room = room;
		this.x = x;
		this.y = y;
	}

	/**
	 * @return True if there is an Entity at this location
	 */
	public boolean hasEntity(){
		return entity != null;
	}

	/**
	 * Returns the Entity on this location. DOES NOT remove it.
	 * @return The Entity on this location, null if there is no Entity
	 */
	public Entity peekItem(){
		return entity;
	}

	/**
	 * Returns the Entity on this location and removes it from this location
	 * @return The Entity on this location, null if there is no Entity
	 */
	public Entity takeEntity(){
		Entity result = this.entity;
		this.entity = null;
		return result;
	}

	/**
	 * Sets the Entity on this location to the parameter Entity and
	 * returns the Entity that was already on the location
	 * @param Entity The Entity to put on this location
	 * @return The Entity that was already on the location, null if there was none
	 */
	public Entity setEntity(Entity entity){
		Entity result = this.entity;
		this.entity = entity;
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
		if(direction == Direction.SOUTH && x == Room.ROOM_WIDTH/2 && y==Room.ROOM_HEIGHT-1) return true;
		if(direction == Direction.WEST && x == 0 && y==Room.ROOM_HEIGHT/2) return true;
		return false;
	}
}
