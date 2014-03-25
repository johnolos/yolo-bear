package bomberman.states;
/**
 * extends State implements TouchListener
 */
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
	private Image readyPressed, notReadyPressed;
	private Buttons btnReady, btnNotReady;
	private boolean hasReceivedReadyToStartInformation = false;

	/**
	 * Constructor of loading multiplayer
	 */
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
			
			ready = new Image(R.drawable.readybutton);
			notReady = new Image(R.drawable.notreadybutton);
			readyPressed = new Image(R.drawable.pressedreadybutton);
			notReadyPressed = new Image(R.drawable.pressednotreadybutton);
			
			btnReady = new Buttons(ready, (int) (Constants.screenWidth/2-(ready.getWidth()/2)), (int) (Constants.screenHeight - 2*(Constants.screenHeight/8)));
			btnNotReady = new Buttons(notReady, (int) (Constants.screenWidth/2-(notReady.getWidth()/2)), (int) (Constants.screenHeight - (Constants.screenHeight/8)));
			btnReady.setView(ready);
			btnNotReady.setView(notReady);	
		}
	}

	/**
	 * When the screen is pressed down, the buttons is changes if the event is in the button bounds
	 * @param event
	 */
	@Override
	public boolean onTouchDown(MotionEvent event) {
		if(btnReady.getBounds().contains(event.getX(), event.getY())) {
			btnReady.setView(readyPressed);
//			client.send(new LobbyInformation(GameLobby.READY, playerNumber));
		} else if(btnNotReady.getBounds().contains(event.getX(), event.getY())) {
			btnNotReady.setView(notReadyPressed);
//			client.send(new LobbyInformation(GameLobby.NOT_READY, playerNumber));
		}
		return false;
	}

	/**
	 * What happens when you release the screen, and updates the view
	 * @param event
	 */
	@Override
	public boolean onTouchUp(MotionEvent event) {
		if(btnReady.getBounds().contains(event.getX(), event.getY())) {
			client.send(new LobbyInformation(GameLobby.READY, playerNumber));
		} else if(btnNotReady.getBounds().contains(event.getX(), event.getY())) {
			client.send(new LobbyInformation(GameLobby.NOT_READY, playerNumber));
		}
		btnNotReady.setView(notReady);
		btnReady.setView(ready);
		return false;
	}

	/**
	 * not used
	 */
	@Override
	public boolean onTouchMove(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * @param dt
	 */
	public void update(float dt){
		btnReady.update(dt);
		btnNotReady.update(dt);
		if(isHost){
			checkPlayersReady();
		}
		if(client.getClientConnectionCount()==nrOfOpponents && isReadyToStart()){
			gameState.startGame();
			getGame().pushState(gameState);
		}
	}
	
	/**
	 * Checks wheter the players are ready to play a multiplayer game
	 */
	private void checkPlayersReady() {
		int nrReadyPlayers = 0 ;
		for(int i = 0; i<isPlayerReady.length; i++){
			if(isPlayerReady[0] == true){
				System.out.println("hey");
				nrReadyPlayers++;
			}
		}
		if(nrReadyPlayers == nrOfOpponents+1){
			client.sendAll(new LobbyInformation(GameLobby.STARTGAME));
			
		}
	}

	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * @param canvas which you draw on.
	 */
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
	
	/**
	 * The lobby information is taken care of here
	 * @param info
	 */
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

	/**
	 * Host menu
	 */
	private void addHostMenu() {
		getGame().pushState(new SetNumberPlayerState(gameState.getPlayer().getColor(),this));
	}

	/**
	 * Sets the number of players in a multiplayer game
	 * @param nrOfOpponents the number of opponents
	 */
	public void setNrOfPlayers(int nrOfOpponents) {
		this.nrOfOpponents = nrOfOpponents;
		if (isHost) {
			client.send(new LobbyInformation(GameLobby.SETNUMBEROFPLAYERS, nrOfOpponents + 1));
		}
	}
	
	/**
	 * Ready to start game
	 * @return true if hasReceivedReadyToStartInformation else false
	 */
	public boolean isReadyToStart() {
		return hasReceivedReadyToStartInformation;
	}

}
