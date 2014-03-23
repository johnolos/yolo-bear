package bomberman.states;

import java.util.ArrayList;

import bomberman.connection.Client;
import bomberman.connection.GameLobby;
import bomberman.connection.LobbyInformation;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.input.TouchListener;

public class loadingMultiplayer extends State implements TouchListener {
	
	private Client client;
	private GameState gameState;
	private ArrayList<GameLobby> players;
	private ArrayList<String> nameOfPlayers;
	private int nrOfOpponents;
	private boolean isHost = false;

	
	public loadingMultiplayer(){
		if(this.client == null) {
			this.client = new Client();
			players = new ArrayList<GameLobby>();
			nameOfPlayers = new ArrayList<String>();
			nrOfOpponents = -1;
			gameState = new GameState(this.client);
			this.client.setGameState(gameState);
			client.setLoadingMultiplayer(this);
			Thread clientThread =new Thread(this.client) ;
			clientThread.start();
			
			
		}
	}

	@Override
	public boolean onTouchDown(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchUp(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void update(float dt){
		System.out.println(client.getClientConnectionCount());
		if(client.getClientConnectionCount()==nrOfOpponents && isReadyToStart()){
			gameState.startGame();
			getGame().pushState(gameState);
			
		}
	}
	
	public void draw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
	}
	
	public void receiveLobbyInformation(LobbyInformation info) {
		System.out.println(info.getLobby());
		switch(info.getLobby()) {
		case HOST:
			isHost = true;
			addHostMenu();
		break;
		case NOT_READY:
//			players.remove(info.getPlayer());
//			players.add(info.getPlayer(), GameLobby.NOT_READY);
		break;
		case READY:
			players.remove(info.getPlayer());
			players.add(info.getPlayer(), GameLobby.READY);
		break;
		case NAME:
			nameOfPlayers.remove(info.getPlayer());
			nameOfPlayers.add(info.getPlayer(), info.getPlayerName());
		break;
		case SETNUMBEROFPLAYERS:
			setNrOfPlayers(info.getPlayer() - 1);
		break;
		default:
		break;
		}
	}

	private void addHostMenu() {
		getGame().pushState(new SetNumberPlayerState(gameState.getThisPlayer().getColor(),this));
	}

	public void setNrOfPlayers(int nrOfOpponents) {
		this.nrOfOpponents = nrOfOpponents;
		if (isHost) {
			client.send(new LobbyInformation(GameLobby.SETNUMBEROFPLAYERS, nrOfOpponents + 1));
		}
		
		
	}
	
	public boolean isReadyToStart() {
		for (GameLobby player : players) {
			if(player == GameLobby.NOT_READY)
				return false;
		}
		return true;
	}

}
