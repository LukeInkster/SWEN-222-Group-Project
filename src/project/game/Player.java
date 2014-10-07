package project.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player implements Entity{
	private static final int maxItems = 12;
	private List<Item> items = new ArrayList<Item>();
	private Location location = new Location(null,0,0);	//TODO
	private Direction orientation;
	private Set<Room> roomsVisited = new HashSet<Room>();
	private int id;

	public Player(int id){
		this.setId(id);
		this.setOrientation(Direction.NORTH);
	}

	/**
	 * Adds the parameter item to the players items list if they
	 * have room in their inventory for the item, returns true if
	 * the item is added or false if it is not.
	 * @param item The item to add
	 * @return True if the item is added to the players inventory
	 */
	public boolean addItem(Item item){
		if(items.size()>=maxItems) return false;
		this.items.add(item);
		return true;
	}

	/**
	 * @return The list of items held by the player
	 */
	public List<Item> getItems(){
		return this.items;
	}

	/**
	 * Removes the parameter item from the players items list
	 * @param item The item to remove
	 */
	public void removeItem(Item item){
		this.items.remove(item);
	}

	/**
	 * Rotates the player 90 degrees clockwise
	 */
	public void rotateClockwise(){
		this.setOrientation(getOrientation().clockwise());
	}

	/**
	 * Rotates the player 90 degrees anticlockwise
	 */
	public void rotateAnticlockwise(){
		this.setOrientation(getOrientation().anticlockwise());
	}

	/**
	 * @return the orientation
	 */
	public Direction getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(Direction orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return the Location of the Player
	 */
	public Location getLocation() {
		return this.location;
	}

	/**
	 * @param location the Location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
		roomsVisited.add(location.getRoom());
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return location.getRoom();
	}

	/**
	 *  Used for drawing minimap
	 *  @return set of rooms visited by player
	 */
	public Set<Room> getRoomsVisited() {
		return roomsVisited;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public void setRoomsVisited(Set<Room> roomsVisited) {
		this.roomsVisited = roomsVisited;
	}
}
