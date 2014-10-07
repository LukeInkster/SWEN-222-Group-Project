package project.net;

import project.game.Player;
import project.game.Room;

public class ObjectSerializeEvent implements Event {

	private Room room;
	private Player player;

	public ObjectSerializeEvent(Room room, Player player){
		this.room = room;
		this.player = player;
	}
	
	public Room getRoom(){
		return room;
	}

	public Player getPlayer(){
		return player;
	}
}
