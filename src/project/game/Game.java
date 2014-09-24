package project.game;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Game implements Iterable<Player>{
	private final int maxPlayers = 6;
	private final int gameWidth = 6;
	private final int gameHeight = 6;
	
	private Room[][] rooms = new Room[gameWidth][gameHeight];
	
	private Set<Player> players = new HashSet<Player>();
	
	public Game(){
		
	}

	/**
	 * @return The Room grid
	 */
	public Room[][] getRooms(){
		return rooms;
	}
	
	/**
	 * Returns the Room at the coordinates (x,y) in the Game or null
	 * if the x or y coordinate is outside the bounds of the Game
	 */
	public Room getRoom(int x, int y){
		if(x<0 || y<0 || x>=gameWidth || y>=gameHeight) return null;
		return rooms[x][y];
	}

	/**
	 * Attempts to add the parameter player to the game, returns true if
	 * the player is added successfully or false if they are not
	 * @param player The player to add
	 * @return True if the add is successful
	 */
	public boolean addPlayer(Player player){
		if(this.players.size()>=maxPlayers) return false;
		players.add(player);
		return true;
	}
	
	/**
	 * Returns an iterator over the players in the game
	 */
	public Iterator<Player> iterator() {
		return players.iterator();
	}
}
