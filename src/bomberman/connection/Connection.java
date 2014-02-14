package bomberman.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection implements Runnable {
	
	Socket socket;
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;
	
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
		
	}
	
	
	private void send(Object obj) {
		try {
			this.objectOutput.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
