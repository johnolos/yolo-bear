package bomberman.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
	/** Peer-to-peer client **/
	
	// This shall be deleted and replaced with config file later on
	String SERVERIP = "78.91.15.30";
	
	// Config file too
	int PORT = 4078;
	
	ServerConnection server;
	ArrayList<Connection> clients;
	
	
	public Client() {
		clients = new ArrayList<Connection>();
		// Make a connection to game server
	}
	
	public void startClient() {
		System.out.println("Trying to connect to server!");
		Socket serverConnection;
		try {
			serverConnection = new Socket(this.SERVERIP, this.PORT);
//			Thread thread = new Thread(this.server = new ServerConnection(serverConnection));
//			thread.run();
			this.server = new ServerConnection(serverConnection);
			this.server.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void send(Object obj) {
		this.server.send(obj);
	}
	
	protected void printMsg(String message) {
		System.out.println(message);
	}
	
	
	// Class to continue connection with server.
	class ServerConnection extends Thread {
		private Socket connection;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		
		ServerConnection(Socket connection) {
			this.connection = connection;
		}
		
		public void run() {
			System.out.println("Connected to server on " + this.connection.getRemoteSocketAddress());
			try {
				// Fetches InputStream from connection
				InputStream serverInputStream = this.connection.getInputStream();
				// Fetches OutputStream from connect
				OutputStream serverOutputStream = this.connection.getOutputStream();
				// Create InputStreamReader for InputStream
				// InputStreamReader inFromClient = new InputStreamReader(serverInputStream);

				// Create ObjectOutputStream
				this.oos = new ObjectOutputStream(serverOutputStream);
				//Create InputObjectStream
				this.ois = new ObjectInputStream(serverInputStream);

				System.out.println("ServerConnection: Ready");
				// While-loop to ensure continuation of reading in-coming messages
				while (this.connection.isConnected()) {
					try {
						//Receive object from client
						Object obj = this.ois.readObject();
						if(obj instanceof String) {
							System.out.println((String)obj);
						}
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		private void send(Object obj) {
			try {
				oos.writeObject(obj);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) {
		new Client().startClient();
	}
	
}
