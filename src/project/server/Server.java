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

import project.net.DummyEvent;

public class Server extends Thread {
	
	private ServerSocket socket;
	
	public Map<Integer, Player> clients;
	public BlockingQueue<Update> updates = new LinkedBlockingQueue<Update>();
	
	public Server(){
		try {
			this.socket = new ServerSocket();
			socket.bind(new InetSocketAddress(12608));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		clients = new HashMap<Integer, Player>();
	}
	
	public void run(){
		try{
			int id = 0;
			UpdateThread thread = new UpdateThread(this);
			thread.start();
			boolean done = false;
			while(!done){
				Socket client = socket.accept();
				clients.put(id,  new Player(client, new ObjectOutputStream(client.getOutputStream())));
				ServerThread serverThread = new ServerThread(this, id, client);
				serverThread.start();
				// ------- TODO: We want the server to send back the current state of the game world here! At the moment, it'll only just send out a dummy event!
				DummyEvent dumb = new DummyEvent();
				dumb.message = "SERVER RESPONSE";
				thread.sendClient(dumb, clients.get(id));
				id++;
			}
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
		
	public static void main(String[] args){
		Server server = new Server();
		System.out.println("[SERVER]: Server Started");
		server.start();
	}
}
