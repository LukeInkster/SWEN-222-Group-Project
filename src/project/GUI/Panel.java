package project.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import project.client.User;
import project.game.Player;

public class Panel extends JPanel{

	//the frame the panel is contained within.
	JFrame frame;
	
	//The display for the frame
	Display display;
	
	//The map for the game to be shown on the panel.
	MiniMap map;
	
	//The inventory to be shown on the panel.
	InventoryBar inventory;
	
	//The user using the panel
	User user;
	
	public Panel(User u){
		
		//Assigns the user.
		user = u;
		
		//crates the frame and add the current panel.
		frame = new JFrame("Game Changer");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(this);
		
		//sets the size of the frame.
		setPreferredSize(new Dimension(800,600));
		
		//creates a new display to be drawn.
		display = new Display(user);
		
		//TODO : send player to MiniMap.
		map = new MiniMap(user.getPlayer());
		
		//TODO : send player to inventory.
		inventory = new InventoryBar(user.getPlayer());
		
		//packs the frame to its contents.
		frame.pack();
		
		//makes the frame visible on the players screen.
		frame.setVisible(true);
		
		//prevents the player from resizing the frame.
		frame.setResizable(false);
		
		//Creates and starts the graphics refresher.
		new GUIUpdater(this).start();
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
		
		//Draws a black background
		offgc.setColor(Color.BLACK);
		offgc.fillRect(0, 0, getWidth(), getHeight());
		
		//TODO : Draws the layout of panel distribution
		offgc.setColor(Color.BLUE);
		offgc.fillRect(0, 0, Display.WIDTH, Display.HEIGHT);
		
		offgc.setColor(Color.RED);
		offgc.fillRect(0, Display.HEIGHT, MiniMap.getWidth(), MiniMap.getWidth());
		
		offgc.setColor(Color.GREEN);
		offgc.fillRect(MiniMap.getWidth(), Display.HEIGHT, 600, 200);
		
		//draws the display.
		display.draw(offgc);
		
		//now translate to the new object to be drawn
		offgc.translate( 0, Display.HEIGHT);
		
		//draws the map.
		map.draw(offgc);
		
		//now translate to the new object to be drawn
		offgc.translate( MiniMap.getWidth(), 0);
		
		//draws the inventory.
		inventory.draw(offgc);
		
		//draws the finished image onto the visible display.
		g.drawImage(offset, 0, 0, null);
		
	}
	
	private class GUIUpdater extends Thread{
		
		//The panel the GUIUpdater updates and refreshes.
		Panel panel;
		
		public GUIUpdater(Panel p){
			panel = p;
		}
		
		/**
		 * This overrided version of run() will call the repaint method at regular intervals to refresh the graphics the user
		 * can see.
		 */
		@Override
		public void run(){
			
			//Catches thread exceptions from thread.sleep();
			try{
				
				//keeps looping while it exists.
				while(true){
					
					//updates the graphical display
					panel.repaint();
					
					//Makes the Thread wait for a small period of time before updating again
					Thread.sleep(100);
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		User u = new User(new Player(69));
		new Panel(u);
	}
}
