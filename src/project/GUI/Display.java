package project.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import project.client.User;
import project.game.Room;
import project.utils.GameUtils;

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
	private int slantWidth = 15, slantHeight = 0, tileWidth = 45, tileHeight = 30;
	private int wallHeight = 60;

	//The user that the display belongs to. -- do we need this? -Mike
	private User user;
	
	//The Room being displayed, added to enable drawing faded adjacent rooms -Mike
	private Room room;

	//The images to be used
	Image tile = GameUtils.loadImage(new File("assets//TileTest2.png"));
	Image wall = GameUtils.loadImage(new File("assets//Wall2.png"));
	Image wallIso = GameUtils.loadImage(new File("assets//WallIso2.png"));
	

	/**
	 * Constructor for Display which receives dimensions to show the bounds of its size on the panel.
	 *
	 * @param width : horizontal dimension of the display
	 * @param height : vertical dimension of the display
	 */
	public Display(Room room){
		this.room = room;
	}

	public void draw(Graphics g){

		//black background
		g.drawImage(GameUtils.loadImage(new File("assets\\Smoke.png")),0,0,WIDTH,HEIGHT,null);
		
		//translate to center the room
		g.translate((Display.WIDTH/2)-((Room.ROOM_WIDTH*tileWidth+Room.ROOM_HEIGHT*slantWidth)/2)+(slantWidth*Room.ROOM_HEIGHT),
				(Display.HEIGHT/2)-((tileHeight*Room.ROOM_HEIGHT)/2)+wallHeight/2);


		//now loop through the rooms locations and draw the floor and if needed walls.
		//made sure to work across the back rows and work down (background-to-foreground)
		for(int y=0;y<Room.ROOM_HEIGHT;y++){
			for(int x=0;x<Room.ROOM_WIDTH;x++){

				//TODO : checks to see if there is a door above if so draws it.
				if(y==0){
					g.drawImage(wall, (x*tileWidth)-(y*slantWidth)+slantWidth, -wallHeight, tileWidth, wallHeight, null);
				}

				//TODO : checks to see if there is a door on the side if so draws it
				if(x==0 ){
					g.drawImage(wallIso, (x*tileWidth)-(y*slantWidth), -wallHeight+(y*tileHeight), slantWidth, wallHeight+30, null);
				}
				
				//Sets the color for the test tiles and then draws them
				g.drawImage(tile,(x*tileWidth)-(y*slantWidth), y*tileHeight, tileWidth, tileHeight, null);
				g.drawImage(tile,(x*tileWidth)-(y*slantWidth)+slantWidth, y*tileHeight, tileWidth, tileHeight, null);

				//Sets the color for the outline of the test tiles and then draws them.
				g.setColor(Color.CYAN);
				//g.drawRect((x*tileWidth)-(y*slantWidth), y*tileHeight, tileWidth, tileHeight);

				//TODO : check to see if there is a item on the tile if so draw it.


			}
		}

		//now undo transitions back to original point
		g.translate(-((Display.WIDTH/2)-((Room.ROOM_WIDTH*tileWidth+Room.ROOM_HEIGHT*slantWidth)/2)+(slantWidth*Room.ROOM_HEIGHT)),
				-((Display.HEIGHT/2)-((tileHeight*Room.ROOM_HEIGHT)/2)+wallHeight/2));
	}
}
