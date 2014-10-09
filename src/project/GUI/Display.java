@@ -8,6 +8,8 @@ import java.awt.Image;
import java.io.File;

import project.client.User;
import project.game.Direction;
import project.game.Location;
import project.game.Room;
import project.utils.GameUtils;

@@ -29,14 +31,13 @@ public class Display {

	//The user that the display belongs to. -- do we need this? -Mike
	private User user;
	
	//The Room being displayed, added to enable drawing faded adjacent rooms -Mike
	private Room room;

	//The images to be used
	Image tile = GameUtils.loadImage(new File("assets"+File.separator+"TileTest2.png"));
	Image wall = GameUtils.loadImage(new File("assets"+File.separator+"Wall2.png"));
	Image wallIso = GameUtils.loadImage(new File("assets"+File.separator+"WallIso2.png"));
	Image door = GameUtils.loadImage(new File("assets"+File.separator+"Door.png"));
	Image doorIso = GameUtils.loadImage(new File("assets"+File.separator+"DoorIso.png"));
	

	/**
@@ -45,8 +46,8 @@ public class Display {
	 * @param width : horizontal dimension of the display
	 * @param height : vertical dimension of the display
	 */
	public Display(Room room){
		this.room = room;
	public Display(User user){
		this.user = user;
	}

	public void draw(Graphics g){
@@ -61,16 +62,16 @@ public class Display {
		//EXPERIMENTAL draw room to the left
		//TODO check if adjacent room exists, may need to change save method in utils
		((Graphics2D) g).setComposite(makeComposite(0.5f)); //transparency ftw!
		g.translate(-room.ROOM_WIDTH*tileWidth, 0); //translate by one room worth to the left
		g.translate(-user.getRoom().ROOM_WIDTH*tileWidth, 0); //translate by one room worth to the left
		drawRoom(g);
		g.translate(2*room.ROOM_WIDTH*tileWidth, 0); //translate to the right twice
		g.translate(2*user.getRoom().ROOM_WIDTH*tileWidth, 0); //translate to the right twice
		drawRoom(g);
		g.translate(-room.ROOM_WIDTH*tileWidth, 0); //translate to back to middle
		g.translate((room.ROOM_HEIGHT*slantWidth), -room.ROOM_HEIGHT*tileHeight); //translate up and a little to the right to adjust for iso
		g.translate(-user.getRoom().ROOM_WIDTH*tileWidth, 0); //translate to back to middle
		g.translate((user.getRoom().ROOM_HEIGHT*slantWidth), -user.getRoom().ROOM_HEIGHT*tileHeight); //translate up and a little to the right to adjust for iso
		drawRoom(g);
		g.translate(-2*room.ROOM_HEIGHT*slantWidth, 2*room.ROOM_HEIGHT*tileHeight); //translate down twice
		g.translate(-2*user.getRoom().ROOM_HEIGHT*slantWidth, 2*user.getRoom().ROOM_HEIGHT*tileHeight); //translate down twice
		drawRoom(g);
		g.translate(room.ROOM_HEIGHT*slantWidth, -room.ROOM_HEIGHT*tileHeight); //back to middle
		g.translate(user.getRoom().ROOM_HEIGHT*slantWidth, -user.getRoom().ROOM_HEIGHT*tileHeight); //back to middle

		((Graphics2D) g).setComposite(makeComposite(1.0f)); //now lets not draw everything else transparent

@@ -85,33 +86,44 @@ public class Display {

	private void drawRoom(Graphics g) {
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
						g.drawImage(tile,(x*tileWidth)-(y*slantWidth), y*tileHeight,60, tileHeight, null);
						//g.drawImage(tile,(x*tileWidth)-(y*slantWidth)+slantWidth, y*tileHeight, tileWidth, tileHeight, null);

						//Sets the color for the outline of the test tiles and then draws them.
						g.setColor(Color.CYAN);
						//g.drawRect((x*tileWidth)-(y*slantWidth), y*tileHeight, tileWidth, tileHeight);

						//TODO : check to see if there is a item on the tile if so draw it.
						
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

