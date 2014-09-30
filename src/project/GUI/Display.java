package project.GUI;

import java.awt.Graphics;

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
	
	//the dimensions of the display for it to be shown on the panel.
	private int width, height;
	
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
	public Display(int width, int height){
		this.width=width;
		this.height=height;
	}
	
	public void draw(Graphics g){
		
		//first translate away from the edge of the display.
		g.translate(50,50);
		
		//now loop through the rooms locations and draw them.
		for(int x=0;x<room.ROOM_WIDTH;x++){
			for(int y=0;y>room.ROOM_HEIGHT;y++){
				g.drawRect((x*tileWidth)-(y*slantWidth), y*tileHeight, tileWidth, tileHeight);
			}
		}
		
	}
	
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
