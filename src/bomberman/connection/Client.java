package bomberman.connection;

import java.util.ArrayList;

public class Client {
	/** Peer-to-peer client **/
	
	// This shall be deleted and replaced with config file later on
	String SERVERIP = "78.91.15.30";
	
	// Config file too
	int PORT = 6374;
	
	
	Connection server;
	ArrayList<Connection> clients;
	
	
	public Client() {
		// Make a connection to game server
		this.server = new Connection(this.SERVERIP, this.PORT);
		clients = new ArrayList<Connection>();
		this.server.run();
	}
	
}
