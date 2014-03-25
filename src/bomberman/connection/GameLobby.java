package bomberman.connection;

import java.io.Serializable;

public enum GameLobby implements Serializable {
	HOST,
	REFRESHPLAYERLIST,
	READY,
	NOT_READY,
	NAME,
	SETNUMBEROFPLAYERS,
	STARTGAME,
	PLAYERNUMBER;
}
