package project.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import project.client.User;
import project.game.Direction;
import project.game.Door;
import project.game.Key;
import project.game.Location;
import project.game.Player;
import project.game.Room;
import project.game.Tile;
import project.net.PlayerMoveEvent;

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
		//TODO : Windows & Linux GUI change
		setPreferredSize(new Dimension(800-10,600-10));

		//creates a new display to be drawn.
		display = new Display(user);

		//TODO : send player to MiniMap.
		map = new MiniMap(user.getPlayer());

		//TODO : send player to inventory.
		inventory = new InventoryBar(user.getPlayer());

		//create Listeners
		createListeners();

		//packs the frame to its contents.
		frame.pack();

		//makes the frame visible on the players screen.
		frame.setVisible(true);

		//prevents the player from resizing the frame.
		frame.setResizable(false);

		//Creates and starts the graphics refresher.
		new GUIUpdater(this).start();


	}

	private void createListeners() {
		//Menu Bar
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;

		menuBar = new JMenuBar();

		menu = new JMenu("File");
		menuBar.add(menu);

		menuItem = new JMenuItem("Find game");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO : something
			}
		});
		menu.add(menuItem);
		menuItem = new JMenuItem("Quit");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(menuItem);
		frame.setJMenuBar(menuBar);

		//key listener
		InputMap iMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap aMap = getActionMap();
		iMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
		iMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
		iMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
		iMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
		aMap.put("moveLeft", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int id = user.getPlayer().getId();
				PlayerMoveEvent evt = new PlayerMoveEvent(Direction.WEST, id);
				user.getClient().push(evt);
			}
		});
		aMap.put("moveRight", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int id = user.getPlayer().getId();
				PlayerMoveEvent evt = new PlayerMoveEvent(Direction.EAST, id);
				user.getClient().push(evt);
			}
		});
		aMap.put("moveUp", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int id = user.getPlayer().getId();
				PlayerMoveEvent evt = new PlayerMoveEvent(Direction.NORTH, id);
				user.getClient().push(evt);
			}
		});
		aMap.put("moveDown", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int id = user.getPlayer().getId();
				PlayerMoveEvent evt = new PlayerMoveEvent(Direction.SOUTH, id);
				user.getClient().push(evt);
			}
		});

		//mouse Listener
		addMouseListener(new MouseListener(){
			@Override
			public void mousePressed(MouseEvent e){
				System.out.printf("X: %d, Y: %d\n",e.getX(),e.getY());
				//TODO find object clicked on and send info to server
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseReleased(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
							}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
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
		offgc.setColor(new Color( 35, 35 ,29));
		offgc.fillRect(0, 0, Display.WIDTH, Display.HEIGHT);

		offgc.setColor(new Color( 115, 115, 95));
		offgc.fillRect(0, Display.HEIGHT, MiniMap.WIDTH, MiniMap.WIDTH);

		offgc.setColor(new Color( 183, 183, 165));
		offgc.fillRect(MiniMap.WIDTH, Display.HEIGHT, 600, 200);

		//draws the display.
		display.draw(offgc);

		//now translate to the new object to be drawn
		offgc.translate( 0, Display.HEIGHT);

		//draws the map.
		map.draw(offgc);

		//now translate to the new object to be drawn
		offgc.translate( MiniMap.WIDTH, 0);

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
					//Updates everything in the client.
					user.getClient().update();

					//Makes the Thread wait for a small period of time before updating again
					Thread.sleep(100);
				}

			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args){
		Player p = new Player(80085);
		//testing hud drawing
		p.addItem(new Door());
		p.addItem(new Tile(true,true,true,true));
		p.addItem(new Tile(true,false,true,false));
		p.addItem(new Tile(false,true,true,true));
		p.addItem(new Tile(true,false,true,false));
		p.addItem(new Tile(false,true,true,true));
		p.addItem(new Tile(true,false,true,false));
		p.addItem(new Tile(false,true,true,true));
		p.addItem(new Key());
		p.setLocation(new Location(new Room(0, 0, false), 1, 1));
		p.setLocation(new Location(new Room(8, 8, false), 1, 1));
		p.setLocation(new Location(new Room(4, 3, false), 1, 1));
		p.setLocation(new Location(new Room(3, 3, false), 1, 1));
		p.setLocation(new Location(new Room(5, 4, false), 1, 1));

		User u = new User(p);
		new Panel(u);
	}
}
