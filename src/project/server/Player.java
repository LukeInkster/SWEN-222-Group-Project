package project.server;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player {
	
	public Socket socket;
	public ObjectOutputStream out;
	
	public Player(Socket socket, ObjectOutputStream out){
		this.socket = socket;
		this.out = out;
	}

}
