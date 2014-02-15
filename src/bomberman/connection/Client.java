package bomberman.connection;

import java.util.ArrayList;

public class Client implements Runnable {
	/** Peer-to-peer client **/
	
	// This shall be deleted and replaced with config file later on
	String SERVERIP = "78.91.15.30";
	
	// Config file too
	int PORT = 4078;
	
	Connection server;
	ArrayList<Connection> clients;
	
	
	public Client() {
		clients = new ArrayList<Connection>();
		// Make a connection to game server
	}
	
	private void send(Object obj) {
		this.server.send(obj);
	}
	
	protected void printMsg(String message) {
		System.out.println(message);
	}


	@Override
	public void run() {
//		Thread thread = new Thread(this.server = new Connection(this.SERVERIP, this.PORT));
//		thread.start();
		System.out.println("Trying to connect!");
		this.server = new Connection(this.SERVERIP, this.PORT);
		this.server.setClient(this);
		this.server.run();
		String helloWorld = "Hello World!";
		this.send(helloWorld);
		
	}
	
	public static void main(String args[]) {
		Thread clientThread = new Thread(new Client());
		clientThread.start();
	}
	
}
