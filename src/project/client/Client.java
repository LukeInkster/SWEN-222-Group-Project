package project.client;

import project.net.DummyEvent;
import project.net.Event;

public class Client {
	
	private Connection connection;
	
	private String user;
	
	public Client(String ip, String user){
		this.connection = new Connection(ip);
		this.user = user;
		
		connection.start();
	}
	
	public void update(){
		for(Event e : connection.poll()){
			if(e instanceof DummyEvent){
				System.out.println(this + "Dummy Event Recieved!" +  (((DummyEvent)e).message));
			}else{
				System.out.println(this + "Unrecognised Packet Recieved!");
			}
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
	}

}
