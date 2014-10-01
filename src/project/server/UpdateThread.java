package project.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import project.net.DummyEvent;
import project.net.Event;

public class UpdateThread extends Thread {
	
	
	private Server server;
	
	public UpdateThread(Server server){
		this.server = server;
		setDaemon(true);
	}
	
	public void run(){
		Update update = null;
		while(true){
			try {
				update = server.getUpdates().take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Event evt = update.event;
			
			// TODO: Process Updates through here
			
			if(evt instanceof DummyEvent){
				System.out.println(this + "Processing Dummy Event:: " + ((DummyEvent)evt).message);
				System.out.println(this + "Connected Clients: " + server.getClients().size());
				sendAllClients(new DummyEvent());
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
