package bomberman.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientPeer extends Thread {
	
	private Client client;
	private ServerSocket peerSocket;

	public ClientPeer(ServerSocket peerSocket, Client client) {
		this.client = client;
		this.peerSocket = peerSocket;
	}
	
	@Override
	public void run() {
		System.out.println("I am running!");
		try {
			Socket clientPeer;
			while (true) {
				clientPeer = peerSocket.accept();
				Connection clientConnection = new Connection(clientPeer, this.client);
				
				clientConnection.start();
				this.client.addConnection(clientConnection);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public InetAddress getInetAddress() {
		return this.peerSocket.getInetAddress();
	}
	
	public int getPort() {
		return this.peerSocket.getLocalPort();
	}

}
