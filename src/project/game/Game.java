package project.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Game implements Iterable<Player>{
	private final int maxPlayers = 4;
	private final int gameWidth = 6;
	private final int gameHeight = 6;
	
	private Room[][] rooms = new Room[gameWidth][gameHeight];
	
	private Map<Integer, Player> players = new HashMap<Integer, Player>();
	
	public Game(){
		
	}
	
	public boolean movePlayer(Player player, Direction direction){
		Location currentLoc = player.getLocation();
		Room currentRoom = currentLoc.getRoom();
		Location targetLoc = null;
		
		if(direction==Direction.NORTH){
			if(currentLoc.equals(currentRoom.getDoorLoc(Direction.NORTH))){
				if(currentRoom.getY()==0) return false;
				targetLoc = adjacentRoom(currentRoom, direction).getDoorLoc(Direction.SOUTH);
			}
			else{
				targetLoc = currentRoom.location(currentLoc.getX(), currentLoc.getY()-1);
			}
		}
		else if(direction==Direction.EAST){
			if(currentLoc.equals(currentRoom.getDoorLoc(Direction.EAST))){
				if(currentRoom.getY()==gameWidth-1) return false;
				targetLoc = adjacentRoom(currentRoom, direction).getDoorLoc(Direction.WEST);
			}
			else{
				targetLoc = currentRoom.location(currentLoc.getX()+1, currentLoc.getY());
			}			
		}
		else if(direction==Direction.SOUTH){
			if(currentLoc.equals(currentRoom.getDoorLoc(Direction.SOUTH))){
				if(currentRoom.getY()==gameHeight-1) return false;
				targetLoc = adjacentRoom(currentRoom, direction).getDoorLoc(Direction.NORTH);
			}
			else{
				targetLoc = currentRoom.location(currentLoc.getX(), currentLoc.getY()+1);
			}				
		}
		else{
			if(currentLoc.equals(currentRoom.getDoorLoc(Direction.WEST))){
				if(currentRoom.getX()==0) return false;
				targetLoc = adjacentRoom(currentRoom, direction).getDoorLoc(Direction.EAST);
			}
			else{
				targetLoc = currentRoom.location(currentLoc.getX()-1, currentLoc.getY());
			}				
		}
		if(targetLoc==null) return false;
		player.setLocation(targetLoc);
		return true;
	}
	
	private Room adjacentRoom(Room room, Direction direction){
		if(direction==Direction.NORTH)	return room(room.getY(),room.getX()-1);
		if(direction==Direction.EAST)	return room(room.getY()+1,room.getX());
		if(direction==Direction.SOUTH)	return room(room.getY(),room.getX()+1);
		else							return room(room.getY()-1,room.getX());
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
	public Room room(int x, int y){
		if(x<0 || y<0 || x>=gameWidth || y>=gameHeight) return null;
		return rooms[x][y];
	}

	/**
	 * Attempts to add the parameter player to the game, returns true if
	 * the player is added successfully or false if they are not
	 * @param player The player to add
	 * @return True if the add is successful
	 */
	public boolean addPlayer(int playerID, Player player){
		if(this.players.size()>=maxPlayers) return false;
		players.put(playerID, player);
		return true;
	}
	
	/**
	 * Returns the player associated with the parameter playerID
	 */
	public Player player(int playerID){
		return players.get(playerID);
	}
	
	/**
	 * Returns an iterator over the players in the game
	 */
	public Iterator<Player> iterator() {
		return players.values().iterator();
	}
}
