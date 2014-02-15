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

public class Server2 {

	private final static String SERVERIP = "78.91.15.30";

	private final static int SERVERPORT = 4078;

	public Server2() {

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
				System.out.println("New connection.");
				// Delegating the further connection with the client to a thread class
				Thread thread = new Thread(new ClientConnection(newConnectionSocket));
				thread.start();
//				new ClientConnection(newConnectionSocket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Thread class for handling further connection with server when connection is established
	class ClientConnection extends Thread {
		private Socket connection;
		private ObjectOutputStream objectOut;
		private ObjectInputStream objectIn;
		
		ClientConnection(Socket connection) {
			this.connection = connection;
		}
		
		private void send(Object obj) {
			try {
				this.objectOut.writeObject(obj);
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
				objectOut = new ObjectOutputStream(clientOutputStream);
				//Create InputObjectStream
				objectIn = new ObjectInputStream(clientInputStream);

				System.out.println("Waiting for message from client");

				// While-loop to ensure continuation of reading in-coming messages
				while (this.connection.isConnected()) {
					try {
						//Receive object from client
						Object obj = this.objectIn.readObject();
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
