package project.server;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class PlayerConnection {
	
	public Socket socket;
	public ObjectOutputStream out;
	
	public PlayerConnection(Socket socket, ObjectOutputStream out){
		this.socket = socket;
		this.out = out;
	}

}
