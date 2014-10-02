package project.GUI;

import java.awt.Color;
import java.awt.Graphics;

import project.client.User;
import project.game.*;

/**
 * The display class is used for showing the game world which will display the current room the player is in with the rooms current state.
 * 
 * @author Falco
 *
 */
public class Display {

	//Used by Map and Inventory, can change to private with getter if preferred
	protected static final int HEIGHT = 400;
	protected static final int WIDTH = 800;
	
	//These integers are used for the isometric calculations
	private int slantWidth = 20, slantHeight = 0, tileWidth = 30, tileHeight = 30;
	
	//The user that the display belongs to.
	private User user;
	
	/**
	 * Constructor for Display which receives dimensions to show the bounds of its size on the panel.
	 * 
	 * @param width : horizontal dimension of the display
	 * @param height : vertical dimension of the display
	 */
	public Display(User u){
		user = u;
	}
	
	public void draw(Graphics g){

		//translate to center the room
		g.translate((Display.WIDTH/2)-((Room.ROOM_WIDTH*tileWidth+Room.ROOM_HEIGHT*slantWidth)/2)+(slantWidth*Room.ROOM_HEIGHT), (Display.HEIGHT/2)-((tileHeight*Room.ROOM_HEIGHT)/2));
		
		//now loop through the rooms locations and draw the floor and if needed walls.
		//made sure to work across the back rows and work down (background-to-foreground)
		for(int y=0;y<Room.ROOM_HEIGHT;y++){
			for(int x=0;x<Room.ROOM_WIDTH;x++){
				
				//Sets the color for the test tiles and then draws them
				g.setColor(Color.GRAY);
				g.fillRect((x*tileWidth)-(y*slantWidth), y*tileHeight, tileWidth, tileHeight);
				
				//Sets the color for the outline of the test tiles and then draws them.
				g.setColor(Color.BLACK);
				g.drawRect((x*tileWidth)-(y*slantWidth), y*tileHeight, tileWidth, tileHeight);
				
				//TODO : checks to see if there is a wall above if so draws it.
				if(y==0){
					
				}
				
				//TODO : checks to see if there is a wall on the side if so draws it
				if(x==0){
					
				}
				
				//TODO : check to see if there is a item on the tile if so draw it.
				
				
			}
		}
		
		//now undo transitions back to original point
		g.translate(-((Display.WIDTH/2)-((Room.ROOM_WIDTH*tileWidth+Room.ROOM_HEIGHT*slantWidth)/2)+(slantWidth*Room.ROOM_HEIGHT)), -((Display.HEIGHT/2)-((tileHeight*Room.ROOM_HEIGHT)/2)));
	}
}
