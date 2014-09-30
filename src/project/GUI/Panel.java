package project.GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Panel extends JPanel{

	//the frame the panel is contained within.
	JFrame frame;
	
	//The display for the frame
	Display display;
	
	//The map for the game to be shown on the panel.
	MiniMap map;
	
	//The inventory to be shown on the panel.
	InventoryBar inventory;
	
	public Panel(){
		
		//crates the frame and add the current panel.
		frame = new JFrame("Game Changer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		
		//sets the size of the frame.
		setPreferredSize(new Dimension(800,600));
		
		//creates a new display to be drawn.
		display = new Display(800,400);
		
		//TODO : send player to MiniMap.
		map = new MiniMap(null);
		
		//TODO : send player to inventory.
		inventory = new InventoryBar(null);
		
		//packs the frame to its contents.
		frame.pack();
		
		//makes the frame visible on the players screen.
		frame.setVisible(true);
		
		//prevents the player from resizing the frame.
		frame.setResizable(false);
	}
	
	/**
	 * This method is used for updating the display with the current state of the game.
	 */
	@Override
	public void paintComponent(Graphics g){
		
		//buffers the image
		Image offset = createImage(getWidth(),getHeight());
		
		//Retrieves the graphics of the buffered image
		Graphics offgc = offset.getGraphics();
		
		//draws the display.
		display.draw(offgc);
		
		//now translate to the new item to be drawn
		offgc.translate( 0, display.getHeight());
		
		//draws the map.
		map.draw(offgc);
		
		//now translate to the new item to be drawn
		offgc.translate( MiniMap.getWidth(), 0);
		
		//draws the inventory.
		inventory.draw(offgc);
		
		//draws the finished image onto the visible display.
		g.drawImage(offset, 0, 0, null);
		
	}
	
	
}
