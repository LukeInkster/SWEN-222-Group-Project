package project.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;

import project.game.Player;
import project.game.Room;

/**
 * The minimap showing the rooms visited and their locations
 * Will be in the bottom left corner below the display.
 */
public class MiniMap {
	
	private static int numRooms = 9;
	private String[][] mapGrid = new String[numRooms][numRooms]; //string for now, maybe image to represent rooms doors later
	private static int gridSize = 10;
	private Player player;
	
	public MiniMap(Player player){
		this.player = player;
	}
	
	/**
	 * @return width of Map, to help draw inventory next to it
	 */
	public static int getWidth(){
		return numRooms*gridSize;
	}
	
	/**
	 * Draws a white square for every room visited
	 * Will add borders and lines for room doors if it looks good
	 * @params g Graphics
	 *
	 */
	public void draw(Graphics g){
			Set<Room> roomsVisited = player.getRoomsVisited();
			g.translate(0,Display.height); //translate by height of display(to draw below
		for(Room room:roomsVisited){
			g.setColor(Color.white);
			g.drawRect(room.getX(), room.getY(), gridSize, gridSize);
		}
	}
}