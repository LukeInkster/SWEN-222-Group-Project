package project.GUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import project.client.User;
import project.game.Direction;
import project.game.Location;
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

	//The images to be used
	Image tile = GameUtils.loadImage(new File("assets"+File.separator+"TileTest2.png"));
	Image wall = GameUtils.loadImage(new File("assets"+File.separator+"Wall2.png"));
	Image wallIso = GameUtils.loadImage(new File("assets"+File.separator+"WallIso2.png"));
	Image door = GameUtils.loadImage(new File("assets"+File.separator+"Door.png"));
	Image doorIso = GameUtils.loadImage(new File("assets"+File.separator+"DoorIso.png"));
	

	/**
	 * Constructor for Display which receives dimensions to show the bounds of its size on the panel.
	 *
	 * @param width : horizontal dimension of the display
	 * @param height : vertical dimension of the display
	 */
	public Display(User user){
		this.user = user;
	}

	public void draw(Graphics g){

		//black background
		g.drawImage(GameUtils.loadImage(new File("assets"+File.separator+"Smoke.png")),0,0,WIDTH,HEIGHT,null);
		
		//translate to center the room
		g.translate((Display.WIDTH/2)-((Room.ROOM_WIDTH*tileWidth+Room.ROOM_HEIGHT*slantWidth)/2)+(slantWidth*Room.ROOM_HEIGHT),
				(Display.HEIGHT/2)-((tileHeight*Room.ROOM_HEIGHT)/2)+wallHeight/2);

		//EXPERIMENTAL draw room to the left
		//TODO check if adjacent room exists, may need to change save method in utils
		((Graphics2D) g).setComposite(makeComposite(0.5f)); //transparency ftw!
		g.translate(-user.getRoom().ROOM_WIDTH*tileWidth, 0); //translate by one room worth to the left
		drawRoom(g);
		g.translate(2*user.getRoom().ROOM_WIDTH*tileWidth, 0); //translate to the right twice
		drawRoom(g);
		g.translate(-user.getRoom().ROOM_WIDTH*tileWidth, 0); //translate to back to middle
		g.translate((user.getRoom().ROOM_HEIGHT*slantWidth), -user.getRoom().ROOM_HEIGHT*tileHeight); //translate up and a little to the right to adjust for iso
		drawRoom(g);
		g.translate(-2*user.getRoom().ROOM_HEIGHT*slantWidth, 2*user.getRoom().ROOM_HEIGHT*tileHeight); //translate down twice
		drawRoom(g);
		g.translate(user.getRoom().ROOM_HEIGHT*slantWidth, -user.getRoom().ROOM_HEIGHT*tileHeight); //back to middle

		((Graphics2D) g).setComposite(makeComposite(1.0f)); //now lets not draw everything else transparent

		//draw this room
		drawRoom(g);
		
		
		//now undo transitions back to original point (0,0)
		g.translate(-((Display.WIDTH/2)-((Room.ROOM_WIDTH*tileWidth+Room.ROOM_HEIGHT*slantWidth)/2)+(slantWidth*Room.ROOM_HEIGHT)),
				-((Display.HEIGHT/2)-((tileHeight*Room.ROOM_HEIGHT)/2)+wallHeight/2));
	}

	private void drawRoom(Graphics g) {
		//now loop through the rooms locations and draw the floor and if needed walls.
		//made sure to work across the back rows and work down (background-to-foreground)
		
		for(Location[] row : user.getRoom().getLocations()){
			for(Location location : row){

				//TODO : checks to see if there is a door above if so draws it.
				if(location.getY()==0){
							
					if(location.isDoor(Direction.NORTH)){
						g.drawImage(door, (location.getX()*tileWidth)-(location.getY()*slantWidth)+slantWidth, -wallHeight, tileWidth, wallHeight, null);
					}else{
						g.drawImage(wall, (location.getX()*tileWidth)-(location.getY()*slantWidth)+slantWidth, -wallHeight, tileWidth, wallHeight, null);
					}
							
				}

				//TODO : checks to see if there is a door on the side if so draws it
				if(location.getX()==0 ){
					
					if(location.isDoor(Direction.SOUTH)){ //TODO : ERROR!!!!!!!!
						g.drawImage(doorIso, (location.getX()*tileWidth)-(location.getY()*slantWidth), -wallHeight+(location.getY()*tileHeight), slantWidth, wallHeight+30, null);
					}else{
						g.drawImage(wallIso, (location.getX()*tileWidth)-(location.getY()*slantWidth), -wallHeight+(location.getY()*tileHeight), slantWidth, wallHeight+30, null);
					}
					
				}
				
				
				g.drawImage(tile,(location.getX()*tileWidth)-(location.getY()*slantWidth), location.getY()*tileHeight,60, tileHeight, null);

				//TODO : check to see if there is a item on the tile if so draw it.
				if(location.hasEntity()){
					g.drawImage(GameUtils.loadImage(new File(location.takeEntity().getFilename())),(location.getX()*tileWidth)-(location.getY()*slantWidth), location.getY()*tileHeight,60, tileHeight, null);
				}
					

			}
		}
	}
	
	/**
	 * draws interactable objects with special images with pure colors for mouse listener
	 * @param g
	 */
	public void drawInteractable(Graphics g){
		//translate to center the room
		g.translate((Display.WIDTH/2)-((Room.ROOM_WIDTH*tileWidth+Room.ROOM_HEIGHT*slantWidth)/2)+(slantWidth*Room.ROOM_HEIGHT),
		(Display.HEIGHT/2)-((tileHeight*Room.ROOM_HEIGHT)/2)+wallHeight/2);
		for(int y=0;y<Room.ROOM_HEIGHT;y++){
			for(int x=0;x<Room.ROOM_WIDTH;x++){

				//TODO : checks to see if there is a door above if so draws it.
				
				//TODO : checks to see if there is a door on the side if so draws it
								
				//Sets the color for the outline of the test tiles and then draws them.
				g.setColor(Color.CYAN);
				g.fillRect((x*tileWidth)-(y*slantWidth), y*tileHeight, tileWidth, tileHeight);

				//TODO : check to see if there is a item on the tile if so draw it.

			}
		}
	}
	
	 private AlphaComposite makeComposite(float alpha) {
		  int type = AlphaComposite.SRC_OVER;
		  return(AlphaComposite.getInstance(type, alpha));
	 }
}
