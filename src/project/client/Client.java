package project.client;

import project.game.Location;
import project.game.Room;
import project.net.AcknowledgeEvent;
import project.net.DenyConnectionEvent;
import project.net.Event;
import project.net.GameWorldUpdateEvent;
import project.net.ObjectSerializeEvent;
import project.utils.GameUtils;

public class Client {

	private Connection connection;

	boolean connectionAcknowledged = false;

	// -- Connection buffer variable

	private String userName;

	private User user;

	public Client(String ip, String userName, User user){
		this.connection = new Connection(ip);
		this.userName = userName;
		this.user = user;
		connection.start();
	}

	public void update(){
		for(Event e : connection.pull()){

			// -- INITIAL CONNECTIION CHECKS.
			if (!connectionAcknowledged){
				// First we should check to see if it's an acknowledgment from the server saying that they cnba
				if(e instanceof AcknowledgeEvent){
					connectionAcknowledged = true;
					System.out.println(this + "ACK from Server recieved. Connection Accepted");
				}else if(e instanceof DenyConnectionEvent){
					System.out.println(((DenyConnectionEvent)e).toString());
				}else continue;
			}

			//TODO : add the event handling for the updates to the users player and room.
				
			if(e instanceof ObjectSerializeEvent){
				
				//first cast to correct class
				ObjectSerializeEvent event = ((ObjectSerializeEvent)e);
				
				//Then check to see if there is a player update
				//however need to check if it is the local player or not
				//TODO : Possibly easier way of implementing this. (don't do flood update with player updates?)
				if(user.getPlayer()==null || (event.getPlayer()!=null && event.getPlayer().equals(user.getPlayer()))){
					user.setPlayer(event.getPlayer());
				}
				
				//Now check to see if there is an update for the room.
				if(user.getRoom()==null || (event.getRoom()!=null && event.getRoom().equals(user.getRoom()))){
					user.setRoom(event.getRoom());
				}
				
			}


			// -- CLIENT SIDE EDITS.

			//TODO : remove this possibly...?
			
			if(e instanceof GameWorldUpdateEvent){
				
				
			}

			// TODO: Handle Client Side events HERE!

		}
	}

	public void push(Event e){
		connection.push(e);
		System.out.println(this + "Pushing Event");
	}

	public String toString(){
		return "[CLIENT: " + userName + " ]";
	}


}
