package bomberman.connection;

import java.io.Serializable;
import java.net.InetAddress;

public class PeerInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4972474125795943386L;
	private InetAddress dstAddress;
	private int dstPort;
	
	public PeerInfo(InetAddress dstAddress, int dstPort) {
		this.dstAddress = dstAddress;
		this.dstPort = dstPort;
	}
	
	public InetAddress getInetAddress() {
		return this.dstAddress;
	}
	
	public int getPort() {
		return this.dstPort;
	}
	
}
