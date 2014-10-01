package project.net;

public class DenyConnectionEvent implements Event {
	
	private String message;
	
	public DenyConnectionEvent(String message){
		this.message = message;
	}
	
	public String toString(){
		return "[DENY CONNECTION EVENT] " + message;
	}
}
