package bomberman.connection;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import bomberman.game.ColorObject;

public class Server {

	private ClientConnection[] clients;
	private boolean[] isClientReady;
	private ColorObject[] clientColors;
	
	private int numberOfPlayers = -1;

	public Server() {
		clients = new ClientConnection[4];
		for(int i = 0; i < 4; i++) {
			clients[i] = null;
		}
		isClientReady = new boolean[4];
		for(int i = 0; i < 4; i++) {
			isClientReady[i] = false;
		}
		clientColors = new ColorObject[4];
		for(int i = 0; i < 4; i++) {
			clientColors[i] = null;
		}
	}

	public void startServer() {
		try {
			// Creating a ServerSocket: Is not used further
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(Config.SERVERPORT, 50,
					InetAddress.getByName(Config.SERVERIP));
			// Printing IP:Port for the server
			System.out.println("Waiting for connections on " + Config.SERVERIP
					+ " : " + Config.SERVERPORT);

			// Establishing connection to database
			// A never-ending while-loop that constantly listens to the Socket
			Socket newConnectionSocket;
			while (true) {
				// Accepts a in-coming connection
				newConnectionSocket = serverSocket.accept();
				// System message
				// Delegating the further connection with the client to a thread
				// class
				// Thread thread = new Thread(new
				// ClientConnection(newConnectionSocket));
				// thread.start();
				ClientConnection client = new ClientConnection(
						newConnectionSocket, this);
				client.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addClientConnection(ClientConnection client) {
		// Creating PeerInfo class for new connection
		PeerInfo peer = new PeerInfo(client.connection.getInetAddress(),
				Config.ANDROIDPORT);
		// Sending peerinfo to all existing connections
		sendAll(peer);
		// Adding connetion to list of connections
		
		int availablePlayerSpot = getAvailablePlayerNumber();
		if(availablePlayerSpot == -1) {
			// Too many players online already
			try {
				client.connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		clients[availablePlayerSpot] = client;
		ColorObject colorAssigned = null;
		for(ColorObject color : ColorObject.values()) {
			if(isColorAvailable(color))
				colorAssigned = color;
		}
		clientColors[availablePlayerSpot] = colorAssigned;
		switch (availablePlayerSpot) {
		case 0:
//			color = ColorObject.BROWN;
			send(new LobbyInformation(GameLobby.HOST),client);
			break;
		case 1:
//			color = ColorObject.BLACK;
			send(new LobbyInformation(GameLobby.SETNUMBEROFPLAYERS,
					numberOfPlayers), client);
			break;
		case 2:
//			color = ColorObject.WHITE;
			send(new LobbyInformation(GameLobby.SETNUMBEROFPLAYERS,
					numberOfPlayers), client);
			break;
		case 3:
//			color = ColorObject.SWAG;
			send(new LobbyInformation(GameLobby.SETNUMBEROFPLAYERS,
					numberOfPlayers), client);
			break;
		default:
			break;
		}
		System.out.println("Color " + colorAssigned + " assigned.");
		send(colorAssigned, client);
		System.out.print("A player connected. ");
		System.out.println( getNumberOfPlayers()
				+ " client(s) are connected to the server.");
		send(new LobbyInformation(GameLobby.PLAYERNUMBER, availablePlayerSpot), availablePlayerSpot);
		sendRefreshedPlayerList();
	}

	public void removeClientConnection(ClientConnection client) {
		for(int i = 0; i < clients.length; i++) {
			if(clients[i] == null) {
				continue;
			}
			if(clients[i].equals(client)) {
				isClientReady[i] = false;
				clients[i] = null;
				clientColors[i] = null;
			}
		}
		System.out.print("A player left. ");
		System.out.println(getNumberOfPlayers()
				+ " client(s) are connected to the server.");
		sendRefreshedPlayerList();
	}

	public int getAvailablePlayerNumber() {
		for(int i = 0; i < clients.length; i++) {
			if(clients[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	public int getNumberOfPlayers() {
		int n = 0;
		for(int i = 0; i < clients.length; i++) {
			if(clients[i] != null)
				n++;
		}
		return n;
	}

	public void sendRefreshedPlayerList() {
		for(int i = 0; i < clients.length; i++) {
			String name = "Player " + (i + 1);
			sendAll(new LobbyInformation(GameLobby.NAME, name, i));
			if(isClientReady[i])
				sendAll(new LobbyInformation(GameLobby.READY, i));
			else
				sendAll(new LobbyInformation(GameLobby.NOT_READY, i));
		}
	}
	
	public void startGame() {
		System.out.println("Trying to start");
		System.out.println(numberOfPlayers);
		if(checkReadyToStart()) {
			System.out.println("LOL og vi må inni ");
			sendAll(new LobbyInformation(GameLobby.STARTGAME));
		}
		for(int i = 0; i < clients.length; i++) {
			if(clients[i] == null)
				continue;
			try {
				clients[i].connection.close();
				clients[i] = null;
				isClientReady[i] = false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Game starting. All players removed from server.");
	}

	public boolean checkReadyToStart() {
		if (numberOfPlayers == -1)
			return false;
		System.out.println("Number of players present: " + getNumberOfPlayers());
		if (getNumberOfPlayers() == numberOfPlayers) {
			int noOfReady = 0;
			for (int i = 0; i < isClientReady.length; i++) {
				if (isClientReady[i]) {
					++noOfReady;
				}
			}
			System.out.println("Ready number:" + noOfReady);
			return (noOfReady == numberOfPlayers);
		}
		return false;
	}

	
	public void sendAll(Object obj) {
		for(ClientConnection client : clients) {
			if(client != null)
				client.send(obj);
		}
	}
	
	
	public void send(Object obj, int index) {
		if(clients[index] == null)
			return;
		clients[index].send(obj);
	}


	public void send(Object obj, ClientConnection client) {
		client.send(obj);
	}

	protected void receive(Object obj) {
		if (obj instanceof LobbyInformation) {
			LobbyInformation lobbyinfo = (LobbyInformation) obj;
			if (lobbyinfo.getLobby() == GameLobby.SETNUMBEROFPLAYERS) {
				numberOfPlayers = lobbyinfo.getPlayer();
			} else if (lobbyinfo.getLobby() == GameLobby.READY
					|| lobbyinfo.getLobby() == GameLobby.NOT_READY) {
				isClientReady[lobbyinfo.getPlayer()] = true;
				sendAll(new LobbyInformation(lobbyinfo.getLobby(), lobbyinfo.getPlayer()));
				startGame();
			}
		} else if (obj instanceof PeerInfo) {
			PeerInfo peer = (PeerInfo) obj;
			System.out.println("Client at: "
					+ peer.getInetAddress().getHostAddress() + ":"
					+ String.valueOf(peer.getPort()));
		}
	}
	
	private boolean isColorAvailable(ColorObject color) {
		for(int i = 0; i < clientColors.length; i++) {
			if(clientColors[i] == color)
				return false;
		}
		return true;
	}

	// Thread class for handling further connection with client when connection
	// is established
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
			System.out.println("Connected to client on "
					+ this.connection.getRemoteSocketAddress());
			InputStream clientInputStream;
			try {
				// Fetches InputStream from connection
				clientInputStream = this.connection.getInputStream();
				// Fetches OutputStream from connect
				OutputStream clientOutputStream = this.connection
						.getOutputStream();
				// Create InputStreamReader for InputStream
				@SuppressWarnings("unused")
				InputStreamReader inFromClient = new InputStreamReader(
						clientInputStream);

				// Create ObjectOutputStream
				oos = new ObjectOutputStream(clientOutputStream);
				// Create InputObjectStream
				ois = new ObjectInputStream(clientInputStream);
				System.out.println("ClientConnection: Ready");
				// Adding newly created connection to serverlist
				this.server.addClientConnection(this);
				// While-loop to ensure continuation of reading in-coming
				// messages
				while (this.connection.isConnected()) {
					try {
						// Receive object from client
						Object obj = this.ois.readObject();
						this.server.receive(obj);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (EOFException e) {
						break;
					}
				}
				removeClientConnection(this);
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
