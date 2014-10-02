package project.server;

import project.net.Event;

/**
 * Wrapper class for a Game World Update
 * @author robinsjack1
 *
 */
public class Update {

	int client;
	Event event;

	public Update(int client, Event event){
		this.client = client;
		this.event = event;
	}

}
