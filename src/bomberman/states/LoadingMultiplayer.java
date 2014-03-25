package bomberman.states;

import bomberman.connection.Client;
import bomberman.connection.GameLobby;
import bomberman.connection.LobbyInformation;
import bomberman.game.Constants;
import bomberman.game.R;
import bomberman.graphics.Buttons;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.input.TouchListener;

public class LoadingMultiplayer extends State implements TouchListener {
	
	private Client client;
	private GameState gameState;
	private boolean[] isPlayerReady = new boolean[4];
	private String[] nameOfPlayer = new String[4];
	private int nrOfOpponents;
	private int playerNumber = 0;
	private boolean isHost = false;
	private Image ready, notReady;
	private Buttons btnReady, btnNotReady;
	private boolean hasReceivedReadyToStartInformation = false;

	
	public LoadingMultiplayer(){
		if(this.client == null) {
			this.client = new Client();
			for(int i = 0; i < 4; i++) {
				isPlayerReady[i] = false;
			}
			for(int i = 0; i < 4; i++) {
				nameOfPlayer[i] = "Missing player " + (i+1);
			}
			nrOfOpponents = -1;
			gameState = new GameState(this.client);
			this.client.setGameState(gameState);
			client.setLoadingMultiplayer(this);
			Thread clientThread = new Thread(this.client) ;
			clientThread.start();
			
			ready = new Image(R.drawable.singleplayerbutton);
			notReady = new Image(R.drawable.singleplayerbutton);
			
			btnReady = new Buttons(ready, (int) (Constants.screenWidth/2-(ready.getWidth()/2)), (int) (Constants.screenHeight - 2*(Constants.screenHeight/8)));
			btnNotReady = new Buttons(notReady, (int) (Constants.screenWidth/2-(notReady.getWidth()/2)), (int) (Constants.screenHeight - (Constants.screenHeight/8)));
			
		}
	}

	@Override
	public boolean onTouchDown(MotionEvent event) {
		if(btnReady.getBounds().contains(event.getX(), event.getY())) {
			client.send(new LobbyInformation(GameLobby.READY, playerNumber));
		} else if(btnNotReady.getBounds().contains(event.getX(), event.getY())) {
			client.send(new LobbyInformation(GameLobby.NOT_READY, playerNumber));
		}
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
		btnReady.update(dt);
		btnNotReady.update(dt);
		if(client.getClientConnectionCount()==nrOfOpponents && isReadyToStart()){
			gameState.startGame();
			getGame().pushState(gameState);
		}
	}
	
	public void draw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
		for(int i = 0; i < nrOfOpponents + 1; i++) {
			TextButton player;
			if(i == playerNumber)
				player = new TextButton(Constants.screenWidth/2 - 100, Constants.screenHeight/8 + i*(Constants.screenHeight/8), "You");
			else 
				player = new TextButton(Constants.screenWidth/2 - 100, Constants.screenHeight/8 + i*(Constants.screenHeight/8), nameOfPlayer[i]);
			player.draw(canvas);
		}
		for(int i = 0; i < nrOfOpponents + 1; i++) {
			TextButton ready = new TextButton(Constants.screenWidth/2 + 100, Constants.screenHeight/8 + i*(Constants.screenHeight/8), isPlayerReady[i] ? "Ready" : "Not Ready");
			ready.draw(canvas);
		}
		btnReady.draw(canvas);
		btnNotReady.draw(canvas);
	}
	
	public void receiveLobbyInformation(LobbyInformation info) {
		switch(info.getLobby()) {
		case HOST:
			isHost = true;
			addHostMenu();
		break;
		case NOT_READY:
			isPlayerReady[info.getPlayer()] = false;
		break;
		case READY:
			isPlayerReady[info.getPlayer()] = true;
		break;
		case NAME:
			nameOfPlayer[info.getPlayer()] = info.getPlayerName();
		break;
		case SETNUMBEROFPLAYERS:
			setNrOfPlayers(info.getPlayer() - 1);
		break;
		case PLAYERNUMBER:
			System.out.println("Player number recieved is " + info.getPlayer());
			playerNumber = info.getPlayer();
		break;
		case STARTGAME:
			hasReceivedReadyToStartInformation = true;
		break;
		default:
		break;
		}
	}

	private void addHostMenu() {
		getGame().pushState(new SetNumberPlayerState(gameState.getPlayer().getColor(),this));
	}

	public void setNrOfPlayers(int nrOfOpponents) {
		this.nrOfOpponents = nrOfOpponents;
		if (isHost) {
			client.send(new LobbyInformation(GameLobby.SETNUMBEROFPLAYERS, nrOfOpponents + 1));
		}
	}
	
	public boolean isReadyToStart() {
		return hasReceivedReadyToStartInformation;
	}

}
