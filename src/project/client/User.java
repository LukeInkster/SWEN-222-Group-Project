package project.client;

import project.game.*;

/**
 * The user class is used for maintaining the current state for the game world on the client side of the game. This means it only contains
 * the information about the game related to the player associated with the user. Basically used to manage local copy of the 
 * @author Falco
 *
 */
public class User {

	//The player the User controls.
	private Player player;
	
	//The room the player is currently in.
	private Room room;
	
	/**
	 * Constructor : Creates an instance of a User in respect to a player in the game.
	 * @param p : The player the user controls in the game.
	 */
	public User(Player p){
		player = p;
	}
	
	
	
	//GETTER AND SETTER METHODS//
	
	/**
	 * Returns the player associated with this User.
	 * @return
	 */
	public Player getPlayer(){
		return player;
	}
	
	/**
	 * Sets the player associated with the User to the player given as an argument.
	 * SOULDN'T EVER BE NEEEDED IN RUNTIME.
	 * @param p : The player now to be associated with this User.
	 */
	public void setPlayer(Player p){
		player = p;
	}
	
	/**
	 * Returns the room that the player associated with this User is currently in.
	 * @return
	 */
	public Room getRoom(){
		return room;
	}
	
	/**
	 * Sets the room the player is currently in to the on given as an argument.
	 * @param r : The room the Users player is now currently in.
	 */
	public void setRoom(Room r){
		room = r;
	}
}
