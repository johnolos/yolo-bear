package bomberman.connection;

import java.io.Serializable;

public enum GameLobby implements Serializable {
	HOST, 				/* Server sends host information so that a player
						   can select how many players opponents. */
	
	REFRESHPLAYERLIST, 	/* A player is asked to refresh the playerlist. */
	
	READY, 				/* A player is ready and notifies server. */
	
	NOT_READY, 			/* A player is not ready and notifies server. */
	
	NAME, 				/* *not in use* Server "support" custom names. */
	
	SETNUMBEROFPLAYERS, /* How many players are in the game. */
	
	STARTGAME, 			/* Sent from server to initiate games on devices. */
	
	PLAYERNUMBER;		/* Client receive and identification number. */
	
}
