package project.server;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection {
	
	public Socket socket;
	public ObjectOutputStream out;
	
	public ClientConnection(Socket socket, ObjectOutputStream out){
		this.socket = socket;
		this.out = out;
	}

}
