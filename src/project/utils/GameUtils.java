package project.utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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

public class GameUtils {

	private static Scanner scan;
	private static Player player = null;
	private static Set<Room> roomsVisited = new HashSet<Room>();

	public GameUtils() { } // Static Class, we don't want them to construct it!

	/**
	 * returns a string containing players fields
	 * example:
	 * PLAYER<ID<69>ITEMS<KEY<>>LOCATION<x<4>y<4>ROOM<x<3>y<3>ISEND<false>>TILE<true;true;false;true;>>ORIENTATION<NORTH>ROOMSVISITED<ROOM<x<3>y<3>ISEND<false>>>
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
			// -- KEY
			if(item instanceof Door){
				s.append("DOOR<>");
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
			s.append("TILE<");
			for(boolean d : player.getLocation().getRoom().getTile().getDoors()){
				s.append(d + ";");
			}
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
		s.append(">");
		System.out.printf("saveString: %s\n", s.toString());
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
		player.setRoomsVisited(roomsVisited);
		return player;
	}

	private static void gobble(String string) {
		scan.skip(string);
	}
	
	private static String peek(){
		String str = scan.nextLine();
		Scanner temp = new Scanner (str);
		scan = new Scanner(str);
		str = temp.next();
		return str;
	}

	private static void parseRoomsVisited() {
		gobble("ROOM<");
		Room room = parseRoom();
		roomsVisited.add(room);
		//System.out.printf("Hey: %s\n",scan.nextLine());
		if(peek().charAt(0)=='>'){
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
		gobble("TILE");
		Tile tile = parseTile();
		gobble(">");
		room.setTile(tile);
		player.setLocation(new Location(room,x,y));
	}

	private static Room parseRoom() {
		gobble("x<");
		scan.useDelimiter(">");
		int x = Integer.parseInt(scan.next());
		gobble(">y<");
		int y = scan.nextInt();
		gobble(">ISEND<");
		boolean isEnd = scan.nextBoolean();
		gobble(">>");
		return new Room(x,y,isEnd);
	}

	private static void parseItem() {
		if(peek().charAt(0)=='>'){
			gobble(">");
			return;}
		scan.useDelimiter("<");
		String itemType = scan.next();
		switch(itemType){
			case "TILE":
				Tile tile = parseTile();
				player.addItem(tile);
				break;
			case "KEY":
				player.addItem(new Key());
				gobble("<>");
				break;
			case "DOOR":
				player.addItem(new Door());
				gobble("<>");
				break;
		}
		if(peek().charAt(0)=='>'){
			gobble(">");
			return;}
		else{parseItem();}
	}
	
	private static Tile parseTile(){
		scan.useDelimiter(";");
		gobble("<");
		Tile tile = new Tile(scan.nextBoolean(),scan.nextBoolean(),scan.nextBoolean(),scan.nextBoolean());
		gobble(";>");
		return tile;
	}
	
	/**
	 * prints Hello World to console
	 */
	public static void shout(){
		System.out.println("Hello World!");
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
