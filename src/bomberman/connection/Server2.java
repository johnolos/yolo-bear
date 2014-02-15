package bomberman.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server2 {

	private final static String SERVERIP = "78.91.15.30";

	private final static int SERVERPORT = 4078;
	
	ArrayList<ClientConnection> clients;

	public Server2() {
		clients = new ArrayList<ClientConnection>();
	}

	public void startServer() {
		try {
			// Creating a ServerSocket: Is not used further
			ServerSocket serverSocket = new ServerSocket(this.SERVERPORT, 50, InetAddress.getByName(this.SERVERIP));
			// Printing IP:Port for the server
			System.out.println("Waiting for connections on " + this.SERVERIP + " : " + this.SERVERPORT);


			// Establishing connection to database
			// A never-ending while-loop that constantly listens to the Socket
			Socket newConnectionSocket;
			while (true) {
				// Accepts a in-coming connection
				newConnectionSocket = serverSocket.accept();
				// System message 
				// Delegating the further connection with the client to a thread class
//				Thread thread = new Thread(new ClientConnection(newConnectionSocket));
//				thread.start();
				ClientConnection client = new ClientConnection(newConnectionSocket);
				client.start();
				this.clients.add(client);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendAll(Object obj) {
		for(ClientConnection client : this.clients)
			client.send(obj);
	}
	
	public void send(Object obj, int index) {
		this.clients.get(index).send(obj);
		
	}
	
	public void send(Object obj, ClientConnection client) {
		client.send(obj);
	}

	// Thread class for handling further connection with client when connection is established
	class ClientConnection extends Thread {
		private Socket connection;
		public ObjectOutputStream oos;
		private ObjectInputStream ois;
		
		ClientConnection(Socket connection) {
			this.connection = connection;
		}
		
		private void send(Object obj) {
			try {
				oos.writeObject(obj);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			System.out.println("Connected to client on " + this.connection.getRemoteSocketAddress());
			InputStream clientInputStream;
			try {
				// Fetches InputStream from connection
				clientInputStream = this.connection.getInputStream();
				// Fetches OutputStream from connect
				OutputStream clientOutputStream = this.connection.getOutputStream();
				// Create InputStreamReader for InputStream
				InputStreamReader inFromClient = new InputStreamReader(clientInputStream);

				// Create ObjectOutputStream
				oos = new ObjectOutputStream(clientOutputStream);
				//Create InputObjectStream
				ois = new ObjectInputStream(clientInputStream);
				// While-loop to ensure continuation of reading in-coming messages
				while (this.connection.isConnected()) {
					try {
						System.out.println("ClientConnection: Ready");
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
	}
	
	public static void main(String[] args) {
		new Server2().startServer();
	}
	
}
