package bomberman.connection;

import java.util.ArrayList;

public class GameLobby {
	
	/**
	 * Unused class: 25 of February 2014.
	 */
	
	ArrayList<Connection> players;
	

	public GameLobby() {
		this.players = new ArrayList<Connection>();
	}
	
	public void registerPlayer(Connection player) {
		this.players.add(player);
	}
	
	public void removePlayer(Connection player) {
		this.players.remove(player);
	}
	
	public int getNumberOfPlayers() {
		return this.players.size();
	}
	
}
