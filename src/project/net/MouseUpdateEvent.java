package project.net;

/**
 * update event for clientsending mouse clicks to server
 * @author Mike
 *
 */
public class MouseUpdateEvent implements Event{
	
	public int x;
	public int y;

	public MouseUpdateEvent(int x, int y){
		this.x = x;
		this.y = y;
	}
}
