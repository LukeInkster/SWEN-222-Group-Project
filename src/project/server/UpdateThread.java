package project.server;

import java.io.IOException;
import java.util.List;

import project.game.Direction;
import project.game.Game;
import project.net.AcknowledgeEvent;
import project.net.DummyEvent;
import project.net.Event;
import project.net.PlayerMoveEvent;

/**
 * Thread that controls all updates to the Game World. Set as a Daemon thread, as it is controlled by the Server. Should we shutdown the server, and the only threads
 * operating in the JVM are daemons, the JVM will stop.
 * Based on the Multithreaded Server tutorial by Jakob Jenkov: http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
 * And ZA JAVA on Youtube: https://www.youtube.com/watch?v=U0bH6aJgmA8
 * @author robinsjack1
 *
 */
public class UpdateThread extends Thread {

	private Server server;
	private Game game;


	public UpdateThread(Server server, Game game){
		this.server = server;
		this.game = game;
		setDaemon(true);
	}

	public void run(){
		Update update = null;
		while(true){
			try { update = server.getUpdates().take(); } catch (InterruptedException e) { e.printStackTrace(); }
			Event evt = update.event;
			// TODO: Process Updates through here

			if(evt instanceof DummyEvent){
				System.out.println(this + "Dummy Event Recieved");
			}

			else if(evt instanceof PlayerMoveEvent){
				switch(((PlayerMoveEvent)evt).dir){
					case NORTH:
						game.movePlayer(((PlayerMoveEvent)evt).id, Direction.NORTH);
						break;
					case SOUTH:
						game.movePlayer(((PlayerMoveEvent)evt).id, Direction.SOUTH);
						break;
					case EAST:
						game.movePlayer(((PlayerMoveEvent)evt).id, Direction.EAST);
						break;
					case WEST:
						game.movePlayer(((PlayerMoveEvent)evt).id, Direction.SOUTH);
						break;
				}

				sendClient(new AcknowledgeEvent(), server.getClients().get(((PlayerMoveEvent)evt).id));

			}




		}
	}

	// -- METHODS FOR SENDING INFORMATION TO CLIENTS

	/**
	 *  Sends an event to a specified players client
	 * @param event Event to send
	 * @param player Player wrapper containing the client information
	 */
	public void sendClient(Event event, ClientConnection player){
		try {
			player.out.writeObject(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends event to every client.
	 * @param event
	 */
	public void sendAllClients(Event event){
		for(ClientConnection player : server.getClients().values()){
			sendClient(event, player);
		}
	}

	/**
	 * Sends events to every client, except certain players.
	 * @param event Event to send
	 * @param p Players to be ignored.
	 */

	public void sendAllClients(Event event, List<ClientConnection> exceptions){
		for(ClientConnection player : server.getClients().values()){
			if(exceptions.contains(player)) continue;
			sendClient(event, player);
		}
	}

	public String toString(){
		return "[UpdateThread] ";
	}

}
