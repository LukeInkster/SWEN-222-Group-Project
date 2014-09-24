package project.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private List<Item> items = new ArrayList<Item>();	
	private Location location;	
	private Direction orientation;
	
	public Player(Location location){
		this.setLocation(location);
	}
	
	/**
	 * Adds the parameter item to the players items list
	 * @param item The item to add
	 */
	public void addItem(Item item){
		this.items.add(item);
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
		orientation = orientation.clockwise();
	}
	
	/**
	 * Rotates the player 90 degrees anticlockwise
	 */
	public void rotateAnticlockwise(){
		orientation = orientation.anticlockwise();
	}

	/**
	 * @return the Location of the Player
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the Location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
}
