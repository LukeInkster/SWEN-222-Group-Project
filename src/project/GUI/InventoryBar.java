package project.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import project.game.Item;
import project.game.Player;
import project.game.Room;

public class InventoryBar {
	
	private int numSlots = 10;//number of inventory slots per player
	private int width;
	private int slotSize = 60; //amount of space set aside for each item in inventory(max ten)
	private int buffX = 10;//space around each item
	private int buffY = 10;
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
			g.translate(0,Display.height); //translate by height of display(to draw below
			g.translate(0,MiniMap.getWidth()); //translate by height of display(to draw below
		for(Item item:items){
			//g.drawImage(img, x, y, width, height);
		}
	}

}
