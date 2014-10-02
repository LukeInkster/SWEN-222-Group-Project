package project.client;

import project.game.Player;
import project.net.AcknowledgeEvent;
import project.net.DenyConnectionEvent;
import project.net.DummyEvent;
import project.net.Event;
import project.net.GameWorldUpdateEvent;
import project.utils.GameUtils;

public class Client {

	private Connection connection;

	boolean connectionAcknowledged = false;

	// -- Connection buffer variable
	private Player player;
	// --

	private String user;

	public Client(String ip, String user){
		this.connection = new Connection(ip);
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


			// -- CLIENT SIDE EDITS.

			if(e instanceof GameWorldUpdateEvent){
				this.player = GameUtils.load(((GameWorldUpdateEvent)e).data);
				System.out.println(this + "PLAYER UPDATE [ " + player.getId() + " ]");
			}

			// TODO: Handle Client Side events HERE!

		}
	}

	public void push(Event e){
		connection.push(e);
		System.out.println(this + "Pushing Event");
	}

	public String toString(){
		return "[CLIENT: " + user + " ]";
	}

	public static void main(String[] args){
		Client client = new Client("127.0.0.1", "Jack");
		client.push(new DummyEvent());
		client.update();
	}

}
