package project.utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import project.game.Direction;
import project.game.Door;
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
	private static List<Room> roomsVisited = new ArrayList<Room>();
	
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
		s.append("PLAYER<");
		// -- ID  -- moved forward so I can construct player object first-Mike
		s.append("ID<" + player.getId() + ">");
		// -- ITEMS
		s.append("ITEMS<");
		for(Item item : player.getItems()){
			// -- TILE
			if(item instanceof Tile){
				s.append("TILE<");
				for(boolean d : ((Tile)item).getDoors()){
					s.append(d + ";");
				}
				s.append(">");
			}
			// -- KEY
			if(item instanceof Key){
				s.append("KEY<>");
			}
		}
		s.append(">");
		// -- LOCATION
		s.append("LOCATION<");
			s.append("x<" + player.getLocation().getX() + ">");
			s.append("y<" + player.getLocation().getY() + ">");
			s.append("ROOM<");
				s.append("x<" + player.getLocation().getRoom().getX() + ">");
				s.append("y<" + player.getLocation().getRoom().getX() + ">");
				s.append("ISEND<" + player.getLocation().getRoom().isEnd() + ">");
			s.append(">");
		s.append(">");
		// -- ORIENTATION
		s.append("ORIENTATION<" + player.getOrientation().toString() + ">");
		// -- ROOMS VISITED
		s.append("ROOMSVISITED<");
		for(Room room : player.getRoomsVisited()){
			s.append("ROOM<");
				s.append("x<" + room.getX() + ">");
				s.append("y<" + room.getX() + ">");
				s.append("ISEND<" + room.isEnd() + ">");
			s.append(">");
		}
		s.append("<");
		s.append(">");
		System.out.println(s.toString());
		return s.toString();
	}
	
	public static Player load(String input){
		//TODO errorhandling
		scan = new Scanner(input);
	//	scan.useDelimiter("<");
		gobble("PLAYER<");
		//parse ID and construct player
		gobble("ID<");
		scan.useDelimiter(">");
		player = new Player(scan.nextInt());
		gobble(">");
		gobble("ITEMS<");
		parseItem();
		gobble("LOCATION<");
		parseLocation();
		gobble("ORIENTATION<");
		parseOrientation();
		gobble("ROOMSVISITED<");
		parseRoomsVisited();
		gobble(">>");
		return player;
	}
	
	private static void gobble(String string) {
		scan.skip(string);
	}

	private static void parseRoomsVisited() {
		gobble("ROOM<");
		Room room = parseRoom();
		roomsVisited.add(room);
		Scanner copy = scan;
		System.out.println(copy.next());
		//TODO fix this bit
		if(copy.next().charAt(0)=='>'){
			gobble(">");
			return;}
		else{parseRoomsVisited();}
	}

	private static void parseOrientation() {
		scan.useDelimiter(">");
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
		gobble(">");
		
	}

	private static void parseLocation() {
		gobble("x<");
		scan.useDelimiter(">");
		int x = scan.nextInt();
		gobble(">y<");
		int y = scan.nextInt();
		gobble(">ROOM<");
		Room room = parseRoom();
		gobble(">");
		player.setLocation(new Location(room,x,y));
	}

	private static Room parseRoom() {
		gobble("x<");
		int x = scan.nextInt();
		gobble(">y<");
		int y = scan.nextInt();
		gobble(">ISEND<");
		boolean isEnd = scan.nextBoolean();
		gobble(">>");
		return new Room(x,y,isEnd);
	}

	private static void parseItem() {
		scan.useDelimiter("<");
		String itemType = scan.next();
		switch(itemType){
			case "TILE":
				scan.useDelimiter(";");
				gobble("<");
				Tile tile = new Tile(scan.nextBoolean(),scan.nextBoolean(),scan.nextBoolean(),scan.nextBoolean());
				player.addItem(tile);
				gobble(">");
			case "KEY":
				player.addItem(new Key());
				gobble("<>");
			case "DOOR":
				player.addItem(new Door());
				//gobble("<>");
		}
		if(scan.findInLine(".").charAt(0)=='>'){return;}
		else{parseItem();}
	}

	/**
	 * Load an image from the file system, using a given filename.
	 *
	 * @param filename
	 * @return
	 */
	public static Image loadImage(File filename) {

		try {
			Image img = ImageIO.read(filename);
			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}
	}
}
