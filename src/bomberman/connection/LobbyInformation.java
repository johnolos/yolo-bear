package bomberman.connection;

import java.io.Serializable;

public class LobbyInformation implements Serializable {
	private GameLobby lobby;
	private int player;
	private String nameOfPlayer;
	
	
	public GameLobby getLobby() {
		return lobby;
	}


	public int getPlayer() {
		return player;
	}
	
	public String getPlayerName() {
		return nameOfPlayer;
	}


	public LobbyInformation(GameLobby lobby, int player) {
		this.lobby = lobby;
		this.player = player;
	}
	
	public LobbyInformation(GameLobby lobby) {
		this.lobby = lobby;
		this.player = -1;
	}
	
	public LobbyInformation(GameLobby lobby, String nameOfPlayer, int player) {
		this.lobby = lobby;
		this.nameOfPlayer = nameOfPlayer;
		this.player = player;
	}
	
	
}
