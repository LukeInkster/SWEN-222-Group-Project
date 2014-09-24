package project.server;

import project.net.Event;

public class Update {
	
	int client;
	Event event;
	
	public Update(int client, Event event){
		this.client = client;
		this.event = event;
	}

}
