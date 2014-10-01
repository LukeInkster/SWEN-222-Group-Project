package project.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import project.net.Event;
import project.server.Server;

public class Connection extends Thread{
	
	public static int worldID = 1;
	private int localID;
	
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	private boolean running = true;
	
	private final LinkedBlockingQueue<Event> events = new LinkedBlockingQueue<Event>();
	
	public Connection(String addr){
		
		localID = worldID;
		worldID++;

		try{
			InetAddress ip = InetAddress.getByName(addr);
			this.socket = new Socket(ip, Server.PORT_NUMBER);
			System.out.println(this + "[NOTIFY] Connection on " + ip.toString() + ":" + Server.PORT_NUMBER);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e){
			//e.printStackTrace();
			System.out.println("Connection Refused! Terminating Program");
			System.exit(0); //TODO: Redirect to a pane that announces that the server cannot be reached.
		}
	}
	
	public List<Event> poll(){
		int items = events.size();
		List<Event> ret = new ArrayList<Event>(items);
		events.drainTo(ret, items);
		return ret;
	}
	
	public void push(Event evt){
		try{
			output.writeObject(evt);
			output.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		while (running){
			Event currentEvent = nextEvent();
			if(currentEvent == null){
				//System.err.println(this + "[ERROR] Client Recieved null!");
				continue;
			}
			events.offer(currentEvent);
		}			
	}
	
	private int i = 0;
	
	public Event nextEvent(){
		Object in = null;
		
		try {
			in = input.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			if(++i == 10) {
				System.out.println(this + "[ERROR] " + e.getMessage().toString() + " Closing Connection");
				 running = false;
			}else{
				System.out.println(this + "[ERROR] " + e.getMessage().toString());
			}
			return null;
		}
		
		if(!(in instanceof Event)){
			System.err.println(this + "[ERROR] Client Recieved Error that is not an event!");
			return null;
		}
		
		return (Event) in;
	}
	
	public String toString(){
		return "[CLIENT ID: " + localID + "]";
	}

}
