package bomberman.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection extends Thread {
	
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Client client;
	
	Connection(Socket connection, Client client) {
		this.socket = connection;
		this.client = client;
	}
	
	@Override
	public void run() {
		System.out.println("Connected to client on " + this.socket.getRemoteSocketAddress());
		try {
			// Fetches InputStream from connection
			InputStream serverInputStream = this.socket.getInputStream();
			// Fetches OutputStream from connect
			OutputStream serverOutputStream = this.socket.getOutputStream();
			// Create InputStreamReader for InputStream
			// InputStreamReader inFromClient = new InputStreamReader(serverInputStream);

			// Create ObjectOutputStream
			this.oos = new ObjectOutputStream(serverOutputStream);
			//Create InputObjectStream
			this.ois = new ObjectInputStream(serverInputStream);

			System.out.println("Client-Client Connection: Ready");
			// While-loop to ensure continuation of reading in-coming messages
			while (this.socket.isConnected()) {
				try {
					//Receive object from client
					Object obj = this.ois.readObject();
					this.client.receive(obj);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	protected void send(Object obj) {
		try {
			this.oos.writeObject(obj);
			this.oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
