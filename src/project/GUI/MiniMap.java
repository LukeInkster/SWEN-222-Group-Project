package project.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.Set;

import project.game.Player;
import project.game.Room;
import project.utils.GameUtils;

/**
 * The minimap showing the rooms visited and their locations
 * Will be in the bottom left corner below the display.
 */
public class MiniMap {
	
	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;
	private static int numRooms = 9;
	private static int roomSize = 22;
	
	//private String[][] mapGrid = new String[numRooms][numRooms]; //string for now, maybe image to represent rooms doors later
	private Player player;
	
	public MiniMap(Player player){
		this.player = player;
	}
	
	/**
	 * Draws a white square for every room visited
	 * Will add borders and lines for room doors if it looks good
	 * @params g Graphics
	 *
	 */
	public void draw(Graphics g){
		
		//center the map
		g.translate((WIDTH%numRooms)/2, (HEIGHT%numRooms)/2);
		
		//draw backdrop
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//draws the gray lined grid
		for(int x=0;x<numRooms;x++){
			for(int y=0;y<numRooms;y++){
				g.setColor(Color.darkGray);
				g.drawRect(x*roomSize, y*roomSize, roomSize, roomSize);
			}
		}
		
		if(player==null){return;}
		Set<Room> roomsVisited = player.getRoomsVisited();
		for(Room room:roomsVisited){
			g.setColor(Color.white);
			g.fillRect(room.getX()*roomSize, room.getY()*roomSize, roomSize, roomSize);
			Image img = GameUtils.loadImage(new File(room.getTile().getFilename()));
			g.drawImage(img, room.getX()*roomSize, room.getY()*roomSize, roomSize, roomSize,null);
			g.setColor(Color.black);
			g.drawRect(room.getX()*roomSize, room.getY()*roomSize, roomSize, roomSize);
		}
		
		
	}
}
