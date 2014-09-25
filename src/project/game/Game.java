package project.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Game implements Iterable<Player>{
	private static final int MAX_PLAYERS = 4;
	private static final int GAME_WIDTH = 6;
	private static final int GAME_HEIGHT = 6;
	
	private Room[][] rooms = new Room[GAME_WIDTH][GAME_HEIGHT];
	
	private Map<Integer, Player> players = new HashMap<Integer, Player>();
	
	public Game(){
		generateRooms();
	}
	
	private void generateRooms() {
		int endX = (int)(Math.random()*GAME_WIDTH);
		int endY = (int)(Math.random()*GAME_HEIGHT);
		for(int x=0;x<GAME_WIDTH;x++){
			for(int y=0;y<GAME_HEIGHT;y++){
				rooms[x][y] = new Room(x,y,(x==endX && y==endY));
			}
		}		
	}

	/**
	 * Moves the player associated with the parameter playerID in the
	 * direction specified by the parameter direction
	 * @param playerID The ID of the player to move
	 * @param dir The direction to move
	 * @return True if the move is successful, false otherwise
	 */
	public boolean movePlayer(int playerID, Direction dir){
		return movePlayer(player(playerID),dir);
	}

	private boolean movePlayer(Player player, Direction dir){
		Location currentLoc = player.getLocation();
		Room currentRoom = currentLoc.getRoom();
		Location targetLoc = null;

		// If the player is at a door and is trying to move through it, get the
		// room on the other side of the door and move the player to the location
		// on the other side of the door
		if(currentLoc.equals(currentRoom.getDoorLocation(dir))){
			Room targetRoom = adjacentRoom(currentRoom,dir);

			// targetRoom will be null if the player is attempting to 
			// move off the Room grid
			if(targetRoom==null) return false;
			
			// If either currentRoom or targetRoom does not have a door
			// on their adjoining wall, return false.
			if(!currentRoom.hasDoor(dir)) return false;
			if(!targetRoom.hasDoor(dir.opposite())) return false;
			
			targetLoc = targetRoom.getDoorLocation(dir.opposite());
		}
		// Otherwise get the Location one step in the given direction
		else{
			targetLoc = currentRoom.adjecentLocation(currentLoc, dir);
		}
		
		// targetLoc will be null if the player is attempting to move out of the
		// room through any path other than an open door
		if(targetLoc==null) return false;
		
		// Success state: move the player and return true
		player.setLocation(targetLoc);
		return true;
	}
	
	/**
	 * Returns the Room adjacent to the parameter Room 
	 * in the parameter Direction
	 */
	public Room adjacentRoom(Room room, Direction direction){
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
		if(x<0 || y<0 || x>=GAME_WIDTH || y>=GAME_HEIGHT) return null;
		return rooms[x][y];
	}

	/**
	 * Attempts to add the parameter player to the game, returns true if
	 * the player is added successfully or false if they are not
	 * @param player The player to add
	 * @return True if the add is successful
	 */
	public boolean addPlayer(int playerID, Player player){
		if(this.players.size()>=MAX_PLAYERS) return false;
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
