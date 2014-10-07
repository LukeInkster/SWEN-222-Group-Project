package project.net;

import project.game.Room;

public class ObjectSerializeEvent implements Event {

	public Room room;

	public ObjectSerializeEvent(Room room){
		this.room = room;
	}

}
