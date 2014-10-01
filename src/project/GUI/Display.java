package project.GUI;

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
	protected static final int HEIGHT = 900;
	

	
	//These integers are used for the isometric calculations
	private int slantWidth, slantHeight, tileWidth, tileHeight;
	
	//The current room that the character resides in.
	private Room room;
	
	/**
	 * Constructor for Display which receives dimensions to show the bounds of its size on the panel.
	 * 
	 * @param width : horizontal dimension of the display
	 * @param height : vertical dimension of the display
	 */
	public Display(User u){
		
	}
	
	public void draw(Graphics g){
		
		//first translate away from the edge of the display.
		g.translate(50,50);
		
		//now loop through the rooms locations and draw the floor and if needed walls.
		//made sure to work across the back rows and work down (background-to-foreground)
		for(int y=0;y>room.ROOM_HEIGHT;y++){
			for(int x=0;x<room.ROOM_WIDTH;x++){
				g.drawRect((x*tileWidth)-(y*slantWidth), y*tileHeight, tileWidth, tileHeight);
				
				//TODO : check to see if there is a wall associated with this tile if so draw it.
				
				
				//TODO : check to see if there is a item on the tile if so draw it.
			}
		}
		
		
	}
	
	
	//GETTERS AND SETTERS//
	
	/**
	 * Used to get the width of the display so as to easily place other objects around the display on the panel.
	 * @return
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Used to get the height of the display so as to easily place other objects around the display on the panel.
	 * @return
	 */
	public int getHeight(){
		return height;
	}
}
