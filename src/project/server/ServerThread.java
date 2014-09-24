package project.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import project.net.Event;

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
					e.printStackTrace();
				}
				
				if(!(obj instanceof Event)) continue; // <-- Means we have recieved an object we shouldn't have!
				
				Event evt = (Event) obj;
				Update update = new Update(id, evt);
				server.updates.add(update);
			}
	}


}
