package project.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private List<Item> items = new ArrayList<Item>();	
	private Location location;	
	private Direction orientation;
	
	public Player(Location location){
		this.setLocation(location);
		this.orientation = Direction.NORTH;
	}
	
	/**
	 * Adds the parameter item to the players items list
	 * @param item The item to add
	 */
	public void addItem(Item item){
		this.items.add(item);
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
		this.orientation = orientation.clockwise();
	}
	
	/**
	 * Rotates the player 90 degrees anticlockwise
	 */
	public void rotateAnticlockwise(){
		this.orientation = orientation.anticlockwise();
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
	}
}
