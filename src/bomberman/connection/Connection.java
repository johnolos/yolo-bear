package bomberman.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Connection class:
 * 		Class used to continue communication to another client.
 */
public class Connection extends Thread {
	
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Client client;
	
	Connection(Socket connection, Client client) {
		this.client = client;
		this.socket = connection;
	}
	
	Connection(InetAddress inet, Client client) {
		this.client = client;
		try {
			socket = new Socket(inet, Config.ANDROIDPORT);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			// Fetches InputStream from connection
			InputStream serverInputStream = this.socket.getInputStream();
			// Fetches OutputStream from connect
			OutputStream serverOutputStream = this.socket.getOutputStream();
			// Create ObjectOutputStream
			this.oos = new ObjectOutputStream(serverOutputStream);
			// Create InputObjectStream
			this.ois = new ObjectInputStream(serverInputStream);
			System.out.println("Peer-Peer Connection: Ready");
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
	
	/**
	 * Used to send given object parameter to the connection established.
	 * @param obj Object to be sent through socket connection.
	 */
	protected void send(Object obj) {
		try {
			this.oos.writeObject(obj);
			this.oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get IP of socket established.
	 * @return
	 */
	public InetAddress getInetAddress() {
		return this.socket.getInetAddress();
	}

	/**
	 * Close established connection.
	 */
	public void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
