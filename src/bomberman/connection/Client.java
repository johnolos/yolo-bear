package bomberman.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import bomberman.game.PeerObject;
import bomberman.states.GameState;

public class Client extends Thread {
//	/** Peer-to-peer client **/
//	
//	// This shall be deleted and replaced with config file later on
//	String SERVERIP = "78.91.12.244";
//	String ANDROIDIP = "78.91.80.79";
//	
//	// Config file too
//	public static final int PORT = 4078;
//	public static final int PEERPORT = 4093;
//	
	ServerConnection server;
	ArrayList<Connection> clients;
	ClientPeer peerConnection;
	private GameState game;
	
	
	public Client() {
		clients = new ArrayList<Connection>();
		// Make a connection to game server
	}
	
	public void run() {
		System.out.println("Trying to connect to server!");
		Socket serverConnection;
		try {
			serverConnection = new Socket(Config.SERVERIP, Config.SERVERPORT);
			ServerSocket peerSocket = new ServerSocket(Config.ANDROIDPORT, 50, InetAddress.getByName(Config.ANDROIDIP));
			this.peerConnection = new ClientPeer(peerSocket, this);
			this.peerConnection.start();
			this.server = new ServerConnection(serverConnection, this);
			this.server.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends object to server
	 * @param obj Object to be sent to server.
	 */
	public void send(Object obj) {
		this.server.send(obj);
	}
	
	/**
	 * Sends object to all other clients.
	 * Should be used during game.
	 * @param obj Object to be sent.
	 */
	public void sendAll(Object obj) {
		System.out.println(this.clients.size());
		for(Connection connection : this.clients) {
			connection.send(obj);
		}
	}
	
	/**
	 * Objects which is received are handled here.
	 * @param obj
	 */
	protected void receive(Object obj) {
		if(obj instanceof String) {
			System.out.println((String)obj);
		} else if (obj instanceof PeerInfo) {
			PeerInfo peer = (PeerInfo)obj;
			Connection peerClient = new Connection(peer.getInetAddress(), this);
			peerClient.start();
			addConnection(peerClient);
		}
		else if(obj instanceof PeerObject){
			this.game.updateGame((PeerObject) obj);
		}
	}
	
	protected void addConnection(Connection client) {
		System.out.println("PeerConnection:" + client.getIP());
		this.clients.add(client);
	}
	
	// Class to continue connection with server.
	class ServerConnection extends Thread {
		private Socket connection;
		private Client client;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		
		ServerConnection(Socket connection, Client client) {
			this.connection = connection;
			this.client = client;
		}
		
		public void run() {
			System.out.println("Connected to server on " + this.connection.getRemoteSocketAddress());
			try {
				// Fetches InputStream from connection
				InputStream serverInputStream = this.connection.getInputStream();
				// Fetches OutputStream from connect
				OutputStream serverOutputStream = this.connection.getOutputStream();
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
				oos.writeObject(obj);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) {
		new Client().run();
	}

	public void setGameState(GameState gameState) {
		this.game = gameState;
		
	}
	
}
