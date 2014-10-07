package project.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import project.client.User;
import project.game.Item;
import project.game.Player;
import project.game.Room;
import project.utils.GameUtils;

public class InventoryBar {

	private int WIDTH = 600;
	private int HEIGHT = 200;
	private int numSlots = 10;//number of inventory slots perplayer
	private int width;
	private int slotSize = 80; //amount of space set aside for each item in inventory(max ten)
	private int edgeBuff = 20;
	private int buffX = 10;//space around each item
	private int buffY = 10;
	private int imageSize = slotSize-2*buffY;
	private User user;

	public InventoryBar(User user){
		this.user = user;
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
			g.drawImage(GameUtils.loadImage(new File("assets"+File.separator+"InventoryBack.png")),0,0,WIDTH,HEIGHT,null);
			if(user.getPlayer()==null){return;}
			List<Item> items = user.getPlayer().getItems();
			g.translate(edgeBuff, edgeBuff);
		for(int i = 0;i<items.size();i++){
			StringBuilder sb = new StringBuilder();
			//sb.append("assets/");
			sb.append(items.get(i).getFilename());
			Image img = GameUtils.loadImage(new File(sb.toString()));
			int row = 0;
			if(i>6){row = 1;};
			g.drawImage(img, (i-7*row)*(slotSize)+buffX, buffY+slotSize*row, imageSize, imageSize,null);
		}
	}


}
