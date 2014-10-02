package project.net;

import project.game.Direction;

public class PlayerMoveEvent implements Event{

	public Direction dir;
	public int id;

	public PlayerMoveEvent(Direction dir, int id){
		this.dir = dir;
		this.id = id;
	}


}
