package bomberman.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
	// This shall be deleted and replaced with config file later on
	String SERVERIP = "127.0.0.1";
	
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
	
	private void send(Object obj, Connection client) {
		client.send(obj);
	}
	
	private void addConnection(Connection client) {
		this.clients.add(client);
	}
	
	private void removeConnection(Connection client) {
		this.clients.remove(client);
	}
	
	public static void main(String args[]) {
		Thread serverThread = new Thread(new Server());
		serverThread.start();
	}

	@Override
	public void run() {
		Socket clientSocket;
		Connection clientConnection;
		while(true) {
			System.out.println("Waiting for connections!");
			try {
				clientSocket = this.serverSocket.accept();
				System.out.println("Connection from: " + clientSocket.getInetAddress() + ":" + String.valueOf(clientSocket.getPort()));
				Thread thread = new Thread(clientConnection = new Connection(clientSocket));
//				clientConnection = new Connection(clientSocket);
				clientConnection.setServer(this);
				thread.start();
				//clientConnection.run();
				addConnection(new Connection(clientSocket));
				System.out.println("Sendig hello client");
				String helloClient = "Hello Client";
				this.send(helloClient, clientConnection);
				
//				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
}
