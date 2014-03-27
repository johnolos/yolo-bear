package bomberman.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.conn.util.InetAddressUtils;

import android.annotation.SuppressLint;
import bomberman.game.ColorObject;
import bomberman.game.PeerObject;
import bomberman.states.GameState;
import bomberman.states.LoadingMultiplayer;

@SuppressLint("DefaultLocale")
public class Client extends Thread {
	/** Peer-to-peer client **/
	ServerConnection server;
	ArrayList<Connection> clients;
	ClientPeer peerConnection;
	private GameState game;
	private LoadingMultiplayer loadingScreen;
	@SuppressWarnings("unused")
	private Socket serverConnection;
	
	
	public Client() {
		clients = new ArrayList<Connection>();
	}
	
	public int getClientConnectionCount(){
		return this.clients.size();
	}
	
	public void run() {
		System.out.println("Trying to connect to server!");
		Socket serverConnection;
		try {
			String androidIp = getIPAddress(true);
			serverConnection = new Socket(Config.SERVERIP, Config.SERVERPORT);
			ServerSocket peerSocket = new ServerSocket(Config.ANDROIDPORT, 50, InetAddress.getByName(androidIp));
			this.peerConnection = new ClientPeer(peerSocket, this);
			this.peerConnection.start();
			this.server = new ServerConnection(serverConnection, this);
			this.server.start();
			this.serverConnection = serverConnection;
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
		System.out.println("Sending information to: " +this.clients.size());
		for(Connection connection : this.clients) {
			connection.send(obj);
		}
	}
	
	/**
	 * Objects which is received are handled here.
	 * @param obj
	 */
	protected void receive(Object obj) {
		System.out.print("Receiving ");
		if(obj instanceof LobbyInformation) {
			System.out.println(" lobby information");
			loadingScreen.receiveLobbyInformation((LobbyInformation)obj);
		} else if (obj instanceof PeerInfo) {
			System.out.println(" peer information");
			PeerInfo peer = (PeerInfo)obj;
			Connection peerClient = new Connection(peer.getInetAddress(), this);
			peerClient.start();
			addConnection(peerClient);
		}
		else if(obj instanceof PeerObject){
			System.out.println(" peerobject information");
			this.game.receiveGameEvent((PeerObject) obj);
		}
		else if(obj instanceof ColorObject){
			System.out.println(" color information");
			this.game.setPlayerColor((ColorObject) obj);
		}
	}
	
	/**
	 * Adds connection to connection list.
	 * @param client Connection to client.
	 */
	protected void addConnection(Connection client) {
		this.clients.add(client);
	}
	
	// Class to continue connection with server.
	class ServerConnection extends Thread {
		private Socket connection;
		private Client client;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		boolean isRunning = true;
		
		ServerConnection(Socket connection, Client client) {
			this.connection = connection;
			this.client = client;
		}
		
		/**
		 * Stop this thread.
		 */
		public void stopRunning() {
			isRunning = false;
		}
		
		public void run() {
			System.out.println("Connected to server on " + this.connection.getRemoteSocketAddress());
			try {
				// Fetches InputStream from connection
				InputStream serverInputStream = this.connection.getInputStream();
				// Fetches OutputStream from connect.
				OutputStream serverOutputStream = this.connection.getOutputStream();
				// Create ObjectOutputStream. Used to output objects
				this.oos = new ObjectOutputStream(serverOutputStream);
				//Create InputObjectStream. Used to get input objects.
				this.ois = new ObjectInputStream(serverInputStream);

				System.out.println("ServerConnection: Ready");
				// While-loop to ensure continuation of reading in-coming messages
				while (this.connection.isConnected() && isRunning) {
					try {
						//Receive object from server
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
		 * Sending given object to server
		 * @param obj Object to be sent.
		 */
		protected void send(Object obj) {
			try {
				oos.writeObject(obj);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Set GameState so that objects received can be passed on.
	 * @param gameState GameState
	 */
	public void setGameState(GameState gameState) {
		this.game = gameState;
	}
	
	/**
	 * Sets LoadingMuliplayer so that objects received can be passed on.
	 * @param loadingScreen LoadingMultiplayer.
	 */
	public void setLoadingMultiplayer(LoadingMultiplayer loadingScreen) {
		this.loadingScreen = loadingScreen;
	}
	
	
	/**
	 * Get local IP address for android device.
	 * @param useIPv4
	 * @return
	 */
	public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr); 
                        if (useIPv4) {
                            if (isIPv4) 
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim<0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }
	
	/**
	 * Close server connection.
	 */
	public void closeServerConnection() {
		try {
			server.connection.close();
			server.stopRunning();
		} catch (IOException e) {
		}
		server = null;
	}	
}
