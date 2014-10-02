package project.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import project.game.Direction;
import project.game.Item;
import project.game.Key;
import project.game.Location;
import project.game.Player;
import project.game.Room;
import project.game.Tile;

/**
 * Class of Static methods to serialize the objects required for the  
 * @author Jack
 *
 */

public class GameSerialize {
	
	private static Scanner scan;
	private static Player player = null;
	private static List<Room> roomsVisited = null;
	
	public GameSerialize() { } // Static Class, we don't want them to construct it!
	
	/**
	 * returns a string containing players fields
	 * example:
	 * "PLAYER{ITEMS{KEY{}TILE{true;false;false;true;}}LOCATION{
	 * @param player
	 * @return String of player and its fields
	 */
	public static String save(Player player){
		StringBuilder s = new StringBuilder();
		// -- PLAYER SAVE:
		s.append("PLAYER{");
		// -- ID  -- moved forward so I can construct player object first-Mike
		s.append("ID{" + player.getId() + "}");
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
		s.append("}");
		System.out.println(s.toString());
		return s.toString();
	}
	
	public static Player load(String input){
		scan = new Scanner(input);
		scan.useDelimiter("\\{");
		gobble("PLAYER");
		//parse ID and construct player
		gobble("ID");
		player = new Player(scan.nextInt());
		gobble("}");
		gobble("ITEMS{");
		parseItem();
		gobble("LOCATION{");
		parseLocation();
		gobble("ORIENTATION{");
		parseOrientation();
		gobble("ROOMSVISITED{");
		parseRoomsVisited();
		gobble("}}");
		return player;
	}
	
	private static void gobble(String string) {
		System.out.println(string);
		scan.next(string);
		scan = new Scanner(scan.nextLine());
	}

	private static void parseRoomsVisited() {
		gobble("ROOM{");
		Room room = parseRoom();
		roomsVisited = new ArrayList<Room>();
		roomsVisited.add(room);
		if(scan.findInLine(".").charAt(0)=='}'){return;}
		else{parseRoomsVisited();}
	}

	private static void parseOrientation() {
		scan.useDelimiter("}");
		String str = scan.next();
		Direction dir = null;
		switch(str){
			case "NORTH":
				dir = Direction.NORTH;
			case "EAST":
				dir = Direction.EAST;
			case "WEST":
				dir = Direction.WEST;
			case "SOUTH":
				dir = Direction.SOUTH;
		}
		player.setOrientation(dir);
		gobble("}");
		
	}

	private static void parseLocation() {
		gobble("x{");
		int x = scan.nextInt();
		gobble("}y{");
		int y = scan.nextInt();
		gobble("}ROOM{");
		Room room = parseRoom();
		gobble("}");
		player.setLocation(new Location(room,x,y));
	}

	private static Room parseRoom() {
		gobble("x{");
		int x = scan.nextInt();
		gobble("}y{");
		int y = scan.nextInt();
		gobble("}ISEND{");
		boolean isEnd = scan.nextBoolean();
		gobble("}}");
		return new Room(x,y,isEnd);
	}

	private static void parseItem() {
		scan.useDelimiter("{");
		String itemType = scan.next();
		switch(itemType){
			case "TILE":
				scan.useDelimiter(";");
				Tile tile = new Tile(scan.nextBoolean(),scan.nextBoolean(),scan.nextBoolean(),scan.nextBoolean());
				player.addItem(tile);
				gobble("}");
			//TODO other items
		}
		if(scan.findInLine(".").charAt(0)=='}'){return;}
		else{parseItem();}
	}

}
