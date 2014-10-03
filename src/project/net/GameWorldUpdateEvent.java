package project.net;

/**
 * Sends a full update of the game to a client. As we are operating dumb clients, this class will be sent whenever there is a update in the game on the server side.
 * @author Jack
 *
 */
public class GameWorldUpdateEvent implements Event {
	
	public String data;
	//why can't we just have aplayer object as a field here? -Mike

	public GameWorldUpdateEvent(String data){
		this.data = data;
	}


}
