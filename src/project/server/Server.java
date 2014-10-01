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

import project.game.Game;
import project.game.Player;
import project.net.AcknowledgeEvent;
import project.net.DenyConnectionEvent;

/**
 * Server object that extends Thread. Class handles all new connections to the Server, and outputs them to ServerThread and UpdateThreads.
 * Based on the Multithreaded Server tutorial by Jakob Jenkov: http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
 * @author Jack
 *
 */
public class Server extends Thread {
	
	public static final int PORT_NUMBER = 12608;
	
	private ServerSocket socket;
	private Game game;	
	
	private boolean done = false;
	
	private Map<Integer, ClientConnection> clients;
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
				UpdateThread thread = new UpdateThread(this);
				thread.start();
				done = false;
				while(!done){
				Socket client = socket.accept();
				getClients().put(id,  new ClientConnection(client, new ObjectOutputStream(client.getOutputStream())));
				if(clients.size() >= 4){
					thread.sendClient(new DenyConnectionEvent("Server Full!"), getClients().get(id));
					getClients().remove(id);
					continue;
				}
				ServerThread serverThread = new ServerThread(this, id, client);
				serverThread.start();
				// ------- TODO: We want the server to send back the current state of the game world here! At the moment, it'll only just send out an ACK event!
				AcknowledgeEvent ack = new AcknowledgeEvent();
				thread.sendClient(ack, getClients().get(id));
				// We'll add the client as a player to the game.
				game.addPlayer(new Player(id));
				System.out.println("[SERVER] Client Connected! ClientID [ " + id + " ] Currrent Connections: [ " + clients.size() + " ] Current Players: [ " + game.getPlayers().size() + " ]");
				id++;
			}
			//socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public synchronized void removeClient(int id){
		clients.remove(id);
		game.getPlayers().remove(game.player(id));
	}
	
	public synchronized String status(){
		return "[SERVER] Currrent Connections: [ " + clients.size() + " ] Current Players: [ " + game.getPlayers().size() + " ]";
	}
	
	public synchronized void shutdown(){
		done = true;
		try { socket.close();} catch (IOException e) { e.printStackTrace(); }
	}
	
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

	public static void main(String[] args){
		Server server = new Server(new Game());
		server.start();
	}
}
