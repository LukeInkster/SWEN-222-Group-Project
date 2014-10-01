package project.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import project.net.Event;

/**
 * ServerThread: Takes incoming objects, and sends it to the change queue on the server.
 * Based on the Multithreaded Server tutorial by Jakob Jenkov: http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
 * @author Jack
 *
 */
public class ServerThread extends Thread {
	
	private Server server;
	private int id;
	private Socket socket;
	
	public ServerThread(Server server, int id, Socket socket){
		this.socket = socket;
		this.server = server;
		this.id = id;
		setDaemon(true);
	}
	
	private long lastIncoming;
	
	public static final long MAX_TIMEOUT = 10000; // Server will kick any player it cannot connect for longer than 10 seconds.
	
	public void run(){
		Object obj = null;
		ObjectInputStream in = null;
			try {
				in = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			boolean done = false;
			while(!done){
				try {
					obj = in.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					//e.printStackTrace();
					long time = (System.currentTimeMillis() - lastIncoming);
					if(time >= MAX_TIMEOUT){
						server.removeClient(id);
						System.out.println(this + "Connection Lost - Removing Player");
						System.out.println(server.status());
						return;
					}
					continue;
				}
				
				if(!(obj instanceof Event)) continue; 						// Means we have recieved an object we shouldn't have...
				else server.getUpdates().add(new Update(id, (Event)obj));	// Else, add it to the change queue on the server thread!
				
				lastIncoming = System.currentTimeMillis();
			}
	}
	
	public String toString(){
		return "[ServerThread # " + id + "] ";
	}


}
