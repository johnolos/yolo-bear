package bomberman.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
	
	// This shall be deleted and replaced with config file later on
	String SERVERIP = "78.91.15.30";
	
	// Config file too
	int PORT = 6374;
	
	ServerSocket serverSocket;
	
	ArrayList<Connection> clients;
	
	public Server() {
		
		try {
			this.clients = new ArrayList<Connection>();
			this.serverSocket = new ServerSocket(this.PORT, 0, InetAddress.getByName(this.SERVERIP));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Accepting connections on: "+ this.SERVERIP + ":" + String.valueOf(this.PORT));
	}

	@Override
	public void run() {
		Socket clientSocket;
		Connection clientConnection;
		
		while(true) {
			try {
				clientSocket = this.serverSocket.accept();
				clientConnection = new Connection(clientSocket);
				clientConnection.run();
				System.out.println("Connection from: " + clientSocket.getInetAddress() + ":" + String.valueOf(clientSocket.getPort()));
				addConnection(new Connection(clientSocket));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addConnection(Connection client) {
		this.clients.add(client);
	}
	
	private void removeConnection(Connection client) {
		this.clients.remove(client);
	}
	
	public static void main(String args[]) {
		Server server = new Server();
		server.run();
	}
	
}
