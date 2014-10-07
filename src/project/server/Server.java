package project.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import project.game.Door;
import project.game.Game;
import project.game.Key;
import project.game.Player;
import project.game.Tile;
import project.net.AcknowledgeEvent;
import project.net.DenyConnectionEvent;
import project.net.GameWorldUpdateEvent;
import project.net.ObjectSerializeEvent;
import project.utils.GameUtils;

/**
 * Server object that extends Thread. Class handles all new connections to the Server, and outputs them to ServerThread and UpdateThreads.
 * Based on the Multithreaded Server tutorial by Jakob Jenkov: http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
 * And ZA JAVA on Youtube: https://www.youtube.com/watch?v=U0bH6aJgmA8
 * @author Jack
 *
 */
public class Server extends Thread {

	public static final int PORT_NUMBER = 12608;
	public static final long MAX_TIMEOUT = 10000; // Server will kick any player it cannot connect for longer than 10 seconds.

	private ServerSocket socket;
	private Game game;

	private boolean done = false;
	// Map of connected clients to the Server.
	private Map<Integer, ClientConnection> clients;
	// Blocking Queue only allows a single thread to access it at once.
	private BlockingQueue<Update> updates = new LinkedBlockingQueue<Update>();

	public Server(Game game){
		try {
			this.socket = new ServerSocket();
			socket.bind(new InetSocketAddress(PORT_NUMBER)); // Thank yoooou NWEN 243.
		} catch (IOException e) {
			e.printStackTrace();
		}
		clients = new HashMap<Integer, ClientConnection>();
		this.game = game;
	}


	public void run(){
			try{
				int id = 0;
				UpdateThread thread = new UpdateThread(this, game);
				thread.start();
				done = false;
				while(!done){
					Socket client = socket.accept();
					getClients().put(id,  new ClientConnection(client, new ObjectOutputStream(client.getOutputStream())));
					// If there are too many clients (no more than 4) we should send a DenyConnectionEvent to let them know that the server is full.
					if(clients.size() > 4){
						thread.sendClient(new DenyConnectionEvent("Server Full!"), getClients().get(id));
						getClients().remove(id);
						continue;
					}
					// And then we should start the Worker Thread for the server to handle incoming events from the Client
					ServerThread serverThread = new ServerThread(this, id, client);
					serverThread.start();

					// -- EVENTS TO SEND ONCE CONNECTION IS MADE:

					// First, we'll send an Acknowledgement Event, to determine that the connection was successfully made to the server
					AcknowledgeEvent ack = new AcknowledgeEvent();
					thread.sendClient(ack, getClients().get(id));
					// We'll add the client as a player to the game.
					game.addPlayer(new Player(id));
					//TODO delete these dummy items
					game.getPlayer(id).addItem(new Key());
					game.getPlayer(id).addItem(new Door());
					game.getPlayer(id).addItem(new Tile(true,true,true,true));

					// Then we should send a GameWorldUpdate to the player, giving the data required for rendering.
					GameWorldUpdateEvent update = new GameWorldUpdateEvent(GameUtils.save(game.getPlayer(id)));
					thread.sendClient(update, getClients().get(id));

					// -- OBJECT SERIALIZE TEST
					ObjectSerializeEvent test = new ObjectSerializeEvent(game.getRooms()[Game.GAME_WIDTH/2][Game.GAME_HEIGHT/2]);
					thread.sendClient(test, getClients().get(id));

					// Then we should update status.
					System.out.println("[SERVER] Client Connected: [ " + client.getInetAddress().toString() + " ] -//- ClientID [ " + id + " ] Currrent Connections: [ " + clients.size() + " ] Current Players: [ " + game.getPlayers().size() + " ]");
					id++;
				}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Removes a client from the game, along with the player Object. If a new connection is added, we can just recreate a player.
	 * @param id the id of the player and the client (should be the same value)
	 */
	public synchronized void removeClient(int id){
		clients.remove(id);
		game.removePlayer(id);
	}

	/**
	 * Prints out the status of a Server, such as connected clients and number of players. Should be a constant value.
	 * @return
	 */
	public synchronized String status(){
		return "[SERVER] Currrent Connections: [ " + clients.size() + " ] Current Players: [ " + game.getPlayers().size() + " ]";
	}

	/**
	 * While Thread.stop() is a deprecated and unsafe operation, this will close the socket, set done to true, then join the threads,
	 * and so, shut down the server. This is the more safe option to run.
	 */
	public synchronized void shutdown(){
		done = true;
		try { socket.close();} catch (IOException e) { e.printStackTrace(); }
		try{ this.join(); } catch (InterruptedException e) { e.printStackTrace();}
	}

	/**
	 * Start wrapper, notifying the port that the server is running on.
	 */
	public void start(){
		System.out.println("[SERVER] Server Started on port " + socket.getLocalPort() );
		super.start();
	}

	public Map<Integer, ClientConnection> getClients() {
		return clients;
	}

	public BlockingQueue<Update> getUpdates() {
		return updates;
	}

	//public static void main(String[] args){
	//	Server server = new Server(new Game());
	//	server.start();
	//}
}
