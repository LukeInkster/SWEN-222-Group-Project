package project.client;

import project.net.AcknowledgeEvent;
import project.net.DummyEvent;
import project.net.Event;

public class Client {
	
	private Connection connection;
	
	boolean connectionAcknowledged = false;
	
	private String user;
	
	public Client(String ip, String user){
		this.connection = new Connection(ip);
		this.user = user;
		
		connection.start();
	}
	
	public void update(){
		for(Event e : connection.poll()){
			if (!connectionAcknowledged)
				if(e instanceof AcknowledgeEvent){
				connectionAcknowledged = true;
				System.out.println(this + "ACK from Server recieved. Connection Accepted");
			}else{
				continue;
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
