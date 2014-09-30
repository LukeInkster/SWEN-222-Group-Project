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

import project.net.AcknowledgeEvent;

/**
 * Server object that extends Thread. Class handles all new connections to the Server, and outputs them to ServerThread and UpdateThreads.
 * @author Jack
 *
 */
public class Server extends Thread {
	
	private ServerSocket socket;
	
	private Map<Integer, PlayerConnection> clients;
	private BlockingQueue<Update> updates = new LinkedBlockingQueue<Update>();
	
	public Server(){
		try {
			this.socket = new ServerSocket();
			socket.bind(new InetSocketAddress(12608)); // Thank yoooou NWEN 243.
		} catch (IOException e) {
			e.printStackTrace();
		}
		clients = new HashMap<Integer, PlayerConnection>();
	}
	
	private boolean newConnection = false;
	
	public void run(){
		try{
			int id = 0;
			UpdateThread thread = new UpdateThread(this);
			thread.start();
			boolean done = false;
			while(!done){
				if(id >= 4) continue;
				Socket client = socket.accept();
				getClients().put(id,  new PlayerConnection(client, new ObjectOutputStream(client.getOutputStream())));
				ServerThread serverThread = new ServerThread(this, id, client);
				serverThread.start();
				// ------- TODO: We want the server to send back the current state of the game world here! At the moment, it'll only just send out an ACK event!
				AcknowledgeEvent ack = new AcknowledgeEvent();
				thread.sendClient(ack, getClients().get(id));
				id++;
			}
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void start(){
		System.out.println("[SERVER] Server Started on port " + socket.getLocalPort() );	
		super.start();
	}
		
	public Map<Integer, PlayerConnection> getClients() {
		return clients;
	}

	public BlockingQueue<Update> getUpdates() {
		return updates;
	}

	public static void main(String[] args){
		Server server = new Server();
		server.start();
	}
}
