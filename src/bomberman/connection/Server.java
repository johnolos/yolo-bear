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
import bomberman.game.ColorObject;

public class Server {
	
	private ArrayList<ClientConnection> clients;
	
	private int numberOfPlayers = -1;

	public Server() {
		clients = new ArrayList<ClientConnection>();
	}

	public void startServer() {
		try {
			// Creating a ServerSocket: Is not used further
			ServerSocket serverSocket = new ServerSocket(Config.SERVERPORT, 50, InetAddress.getByName(Config.SERVERIP));
			// Printing IP:Port for the server
			System.out.println("Waiting for connections on " + Config.SERVERIP + " : " + Config.SERVERPORT);


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
				ClientConnection client = new ClientConnection(newConnectionSocket, this);
				client.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addClientConnection(ClientConnection client) {
		// Creating PeerInfo class for new connection
		PeerInfo peer = new PeerInfo(client.connection.getInetAddress(), Config.ANDROIDPORT);
		// Sending peerinfo to all existing connections
		sendAll(peer);
		// Adding connetion to list of connections
		this.clients.add(client);
		int clientNumber = this.clients.size()-1;
		ColorObject color = null;
		switch(clientNumber){
		case 0:
			color = ColorObject.BROWN;
			send(new LobbyInformation(GameLobby.HOST,clientNumber),clients.get(clientNumber));
			break;
		case 1:
			color = ColorObject.BLACK;
			send(new LobbyInformation(GameLobby.SETNUMBEROFPLAYERS,numberOfPlayers),clients.get(clientNumber));
			send(new LobbyInformation(GameLobby.NOT_READY,clientNumber),clients.get(clientNumber));
			break;
		case 2:
			color = ColorObject.WHITE;
			send(new LobbyInformation(GameLobby.SETNUMBEROFPLAYERS,numberOfPlayers),clients.get(clientNumber));
			send(new LobbyInformation(GameLobby.NOT_READY,clientNumber),clients.get(clientNumber));
			break;
		case 3:
			color = ColorObject.SWAG;
			send(new LobbyInformation(GameLobby.SETNUMBEROFPLAYERS,numberOfPlayers),clients.get(clientNumber));
			send(new LobbyInformation(GameLobby.NOT_READY,clientNumber),clients.get(clientNumber));
			break;
		default:
			break;
		}
		send(color,this.clients.get(clientNumber));
	}
	
	public void sendAll(Object obj) {
		for(ClientConnection client : this.clients) {
			client.send(obj);
		}
	}
	
	public void send(Object obj, int index) {
		this.clients.get(index).send(obj);
		
	}
	
	public void send(Object obj, ClientConnection client) {
		client.send(obj);
	}
	
	protected void receive(Object obj) {
		if(obj instanceof LobbyInformation) {
			LobbyInformation lobbyinfo = (LobbyInformation)obj;
			if(lobbyinfo.getLobby() == GameLobby.SETNUMBEROFPLAYERS) {
				numberOfPlayers = lobbyinfo.getPlayer();
			} else if (lobbyinfo.getLobby() == GameLobby.READY || lobbyinfo.getLobby() == GameLobby.NOT_READY) {
				sendAll(obj);
			}
		} else if (obj instanceof PeerInfo) {
			PeerInfo peer = (PeerInfo)obj;
			System.out.println("Client at: " + peer.getInetAddress().getHostAddress() 
					+ ":" + String.valueOf(peer.getPort()));
		}
	}

	// Thread class for handling further connection with client when connection is established
	class ClientConnection extends Thread {
		private Socket connection;
		private Server server;
		public ObjectOutputStream oos;
		private ObjectInputStream ois;
		
		ClientConnection(Socket connection, Server server) {
			this.connection = connection;
			this.server = server;
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
				System.out.println("ClientConnection: Ready");
				// Adding newly created connection to serverlist
				this.server.addClientConnection(this);
				// While-loop to ensure continuation of reading in-coming messages
				while (this.connection.isConnected()) {
					try {
						//Receive object from client
						Object obj = this.ois.readObject();
						this.server.receive(obj);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				this.connection.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}
	
	public static void main(String[] args) {
		new Server().startServer();
	}
	
}
