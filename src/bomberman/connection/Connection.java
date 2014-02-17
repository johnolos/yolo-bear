package bomberman.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection implements Runnable {
	
	Socket socket;
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;
	private Client client;
	//private Server server;
	
	// This constructor will be used by client
	Connection(String ip, int port) {
		try {
			socket = new Socket(InetAddress.getByName(ip),port);
			this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
			this.objectInput = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// This constructor will be used by server
	Connection(Socket connection) {
		this.socket = connection;
		try {
			this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
			this.objectInput = new ObjectInputStream(socket.getInputStream());
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(this.socket.isConnected()) {
			try {
				Object obj;
				if((obj = this.objectInput.readObject()) != null) {
					if(obj instanceof String) {
						String message = (String)obj;
						if(this.client != null) {
							this.client.printMsg(message);
						}
					}
				}
			} catch (OptionalDataException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void send(Object obj) {
		try {
			this.objectOutput.writeObject(obj);
			this.objectOutput.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setClient(Client client) {
		if(this.client == null) {
			this.client = client;
		}
	}
	
	/*
	public void setServer(Server server) {
		if(this.server == null) {
			this.server = server;
		}
	}
	*/
}
