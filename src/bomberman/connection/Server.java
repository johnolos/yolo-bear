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

	/**
	 * Starts the server
	 */
	public void startServer() {
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(Config.SERVERPORT, 50,
					InetAddress.getByName(Config.SERVERIP));
			// Printing IP:Port for the server
			System.out.println("Waiting for connections on " + Config.SERVERIP
					+ " : " + Config.SERVERPORT);
			Socket newConnectionSocket;
			while (true) {
				// Accepts a in-coming connection
				newConnectionSocket = serverSocket.accept();;
				ClientConnection client = new ClientConnection(
						newConnectionSocket, this);
				client.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adding the given ClientConnection to the list of connections.
	 * @param client ClientConnection to be removed.
	 */
	public void addClientConnection(ClientConnection client) {
		// Creating PeerInfo class for new connection
		PeerInfo peer = new PeerInfo(client.connection.getInetAddress(),
				Config.ANDROIDPORT);
		// Sending PeerInfo to all existing connections
		sendAll(peer);
		
		// Adding connection to list of connections
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
			send(new LobbyInformation(GameLobby.HOST),client);
			break;
		case 1:
			send(new LobbyInformation(GameLobby.SETNUMBEROFPLAYERS,
					numberOfPlayers), client);
			break;
		case 2:
			send(new LobbyInformation(GameLobby.SETNUMBEROFPLAYERS,
					numberOfPlayers), client);
			break;
		case 3:
			send(new LobbyInformation(GameLobby.SETNUMBEROFPLAYERS,
					numberOfPlayers), client);
			break;
		default:
			break;
		}
		send(colorAssigned, client);
		System.out.print("A player connected. ");
		System.out.println( getNumberOfPlayers()
				+ " client(s) are connected to the server.");
		send(new LobbyInformation(GameLobby.PLAYERNUMBER, availablePlayerSpot), availablePlayerSpot);
		sendRefreshedPlayerList();
	}

	/**
	 * Removing the given ClientConnection from list of connections
	 * if it is present.
	 * @param client ClientConnection to be removed.
	 */
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

	/**
	 * Get the number of available player number
	 * @return
	 */
	private int getAvailablePlayerNumber() {
		for(int i = 0; i < clients.length; i++) {
			if(clients[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Get the number of players present at server.
	 * @return
	 */
	private int getNumberOfPlayers() {
		int n = 0;
		for(int i = 0; i < clients.length; i++) {
			if(clients[i] != null)
				n++;
		}
		return n;
	}

	/**
	 * Sends refreshed player list to ClientConnections.
	 */
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
	
	/**
	 * Starts the game with the number of players listed in connections.
	 * If players are not ready however, nothing happens.
	 */
	public void startGame() {
		if(checkReadyToStart()) {
			System.out.println("Starting game.");
			sendAll(new LobbyInformation(GameLobby.STARTGAME));
		}
	}

	/**
	 * Method checks if every single player is ready to start.
	 * If not it returns false.
	 * @return Boolean whether or not all players are ready.
	 */
	public boolean checkReadyToStart() {
		if (numberOfPlayers == -1)
			return false;
		if (getNumberOfPlayers() == numberOfPlayers) {
			int noOfReady = 0;
			for (int i = 0; i < isClientReady.length; i++) {
				if (isClientReady[i]) {
					++noOfReady;
				}
			}
			return (noOfReady == numberOfPlayers);
		}
		return false;
	}

	/**
	 * Sends object to all present connections.
	 * @param obj Object sent to all connections.
	 */
	public void sendAll(Object obj) {
		for(ClientConnection client : clients) {
			if(client != null)
				client.send(obj);
		}
	}
	
	/**
	 * Sends object to the connection at given index.
	 * @param obj Object to be sent to specified connection.
	 * @param index Index to specified connection.
	 */
	public void send(Object obj, int index) {
		if(clients[index] == null)
			return;
		clients[index].send(obj);
	}


	/**
	 * Sends object to the given ClientConnection.
	 * @param obj Object to be sent.
	 * @param client ClientConnection object shall be sent to.
	 */
	public void send(Object obj, ClientConnection client) {
		client.send(obj);
	}

	/**
	 * This method receives object from whatever socket and
	 * handles it properly here.
	 * @param obj Object received through sockets.
	 */
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
	
	/**
	 * Checks if the specified color is available.
	 * @param color
	 * @return
	 */
	private boolean isColorAvailable(ColorObject color) {
		for(int i = 0; i < clientColors.length; i++) {
			if(clientColors[i] == color)
				return false;
		}
		return true;
	}

	/**
	 *  Thread class for handling further connection with client when connection
	 *  is established
	 */
	class ClientConnection extends Thread {
		private Socket connection;
		private Server server;
		public ObjectOutputStream oos;
		private ObjectInputStream ois;

		ClientConnection(Socket connection, Server server) {
			this.connection = connection;
			this.server = server;
		}

		/**
		 * Sends object through the socket connection.
		 * @param obj
		 */
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
				// Adding newly created connection to server list.
				this.server.addClientConnection(this);
				while (this.connection.isConnected()) {
					try {
						// Receive object from client
						Object obj = this.ois.readObject();
						this.server.receive(obj);
					} catch (IOException e) {
						System.out.println("A player left the game");
						break;
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
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
