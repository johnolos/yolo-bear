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
	private Socket serverConnection;
	private boolean isRunning;
	
	
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
//			ServerSocket peerSocket = new ServerSocket(Config.ANDROIDPORT, 50, InetAddress.getByName(Config.ANDROIDIP));
//			serverConnection = new Socket(Config.SERVERIP, Config.SERVERPORT);
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
		for(Connection connection : this.clients) {
			connection.send(obj);
		}
	}
	
	/**
	 * Objects which is received are handled here.
	 * @param obj
	 */
	protected void receive(Object obj) {
		if(obj instanceof LobbyInformation) {
			loadingScreen.receiveLobbyInformation((LobbyInformation)obj);
		} else if (obj instanceof PeerInfo) {
			PeerInfo peer = (PeerInfo)obj;
			Connection peerClient = new Connection(peer.getInetAddress(), this);
			peerClient.start();
			addConnection(peerClient);
		}
		else if(obj instanceof PeerObject){
			this.game.receiveGameEvent((PeerObject) obj);
		}
		else if(obj instanceof ColorObject){
			this.game.setPlayerColor((ColorObject) obj);
		}
	}
	
	/**
	 * Adds connection to connection list.
	 * @param client Connection to client.
	 */
	protected void addConnection(Connection client) {
//		System.out.println("PeerConnection:" + client.getIP());
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
	
	public void setLoadingMultiplayer(LoadingMultiplayer loadingScreen) {
		this.loadingScreen = loadingScreen;
	}
	
//	public String getLocalIpAddress() {
//		try {
//			for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
//				NetworkInterface intf = (NetworkInterface) en.nextElement();
//				for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
//					InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
//					if (!inetAddress.isLoopbackAddress()) {
//						return get
//					}
//				}
//			}
//		} catch (SocketException ex) {
//		}
//		return null;
//	}
	
	
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
	
	public static String convertHexToIP(String hex)
	{
	    String ip= "";

	    for (int j = 0; j < hex.length(); j+=2) {
	        String sub = hex.substring(j, j+2);
	        int num = Integer.parseInt(sub, 16);
	        ip += num+".";
	    }

	    ip = ip.substring(0, ip.length()-1);
	    return ip;
	}
	
	public void closePeerConnections() {
		for(int i = 0; i < clients.size(); i++) {
			clients.get(i).stopRunning();
			clients.get(i).closeConnection();
		}
		clients.clear();
	}
	
	public void closeServerConnection() {
		try {
			server.connection.close();
			server.stopRunning();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		server = null;
	}
	
	public void clientShutdown() {
		closePeerConnections();
		
	}
	
	public void restartServerConnection() {
		server.run();
	}
	
	
}
