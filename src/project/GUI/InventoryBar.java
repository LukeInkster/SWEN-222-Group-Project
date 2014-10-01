package project.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import project.game.Item;
import project.game.Player;
import project.game.Room;

public class InventoryBar {
	
	private int numSlots = 10;//number of inventory slots perplayer
	private int width;
	private int slotSize = 100; //amount of space set aside for each item in inventory(max ten)
	private int buffX = 20;//space around each item
	private int buffY = 20;
	private int imageSize = slotSize-2*buffY;
	private Player player;
	
	public InventoryBar(Player player){
		this.player = player;
	}
	
	/**
	 * @return width of inventory bar
	 */
	public int getWidth(){
		return numSlots*slotSize;
	}
	
	/**
	 * 
	 * @param g
	 */
	public void draw(Graphics g){
			List<Item> items = player.getItems();
		for(int i = 0;i<items.size();i++){
			StringBuilder sb = new StringBuilder();
			sb.append("assets\\");
			sb.append(items.get(i).getFilename());
			Image img = loadImage(new File(sb.toString()));
			int row = 0;
			if(i>6){row = 1;};
			g.drawImage(img, (i-7*row)*(slotSize)+buffX, buffY+slotSize*row, imageSize, imageSize,null);
		}
	}
	
	/**
	 * Load an image from the file system, using a given filename.
	 * 
	 * @param filename
	 * @return
	 */
	public Image loadImage(File filename) {
	
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
