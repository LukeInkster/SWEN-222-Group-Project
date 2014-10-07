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

			if(e instanceof ObjectSerializeEvent){
				Room room = ((ObjectSerializeEvent)e).room;
				Location loc = room.getLocations()[Room.ROOM_WIDTH/2][Room.ROOM_HEIGHT/2];
				if(loc == null){ System.out.println("[ERROR] DIDNT WORK"); }
				else { System.out.println(loc.getX() + " " + loc.getY() + " ");}
			}


			// -- CLIENT SIDE EDITS.

			if(e instanceof GameWorldUpdateEvent){
				user.setPlayer(GameUtils.load((((GameWorldUpdateEvent) e).data)));
				System.out.printf("Player inventory: %s\n",user.getPlayer().getItems().get(0).getFilename());
				System.out.println("[CLIENT: "+userName+"] "+((GameWorldUpdateEvent)e).data);
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
