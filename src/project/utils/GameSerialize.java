package project.utils;

import project.game.Item;
import project.game.Key;
import project.game.Player;
import project.game.Room;
import project.game.Tile;

/**
 * Class of Static methods to serialize the objects required for the  
 * @author Jack
 *
 */

public class GameSerialize {
	
	public GameSerialize() { } // Static Class, we don't want them to construct it!
	
	public static String save(Player player){
		StringBuilder s = new StringBuilder();
		// -- PLAYER SAVE:
		s.append("PLAYER{");
		// -- ITEMS
		s.append("ITEMS{");
		for(Item item : player.getItems()){
			// -- TILE
			if(item instanceof Tile){
				s.append("TILE{");
				for(boolean d : ((Tile)item).getDoors()){
					s.append(d + ";");
				}
				s.append("}");
			}
			// -- KEY
			if(item instanceof Key){
				s.append("KEY{}");
			}
		}
		s.append("}");
		// -- LOCATION
		s.append("LOCATION{");
			s.append("x{" + player.getLocation().getX() + "}");
			s.append("y{" + player.getLocation().getY() + "}");
			s.append("ROOM{");
				s.append("x{" + player.getLocation().getRoom().getX() + "}");
				s.append("y{" + player.getLocation().getRoom().getX() + "}");
				s.append("ISEND{" + player.getLocation().getRoom().isEnd() + "}");
			s.append("}");
		s.append("}");
		// -- ORIENTATION
		s.append("ORIENTATION{" + player.getOrientation().toString() + "}");
		// -- ROOMS VISITED
		s.append("ROOMSVISTED{");
		for(Room room : player.getRoomsVisited()){
			s.append("ROOM{");
				s.append("x{" + room.getX() + "}");
				s.append("y{" + room.getX() + "}");
				s.append("ISEND{" + room.isEnd() + "}");
			s.append("}");
		}
		s.append("}");
		// -- ID
		s.append("ID{" + player.getId() + "}");
		s.append("}");
		System.out.println(s.toString());
		return s.toString();
	}
	
	public static Player load(String input){
		Player res = null;
		
		return res;
	}

}
