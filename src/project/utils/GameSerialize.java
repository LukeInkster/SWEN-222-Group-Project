package project.utils;

import project.game.Item;
import project.game.Key;
import project.game.Player;
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
		
		return s.toString();
	}

}
