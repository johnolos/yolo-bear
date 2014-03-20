package bomberman.states;

import bomberman.connection.Client;
import bomberman.game.Constants;
import bomberman.graphics.MainMenuStartImage;
import bomberman.graphics.MultiPlayer;
import bomberman.graphics.SinglePlayer;
import android.graphics.Canvas;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import sheep.input.TouchListener;

public class MainMenuWithGraphics extends State implements TouchListener {
	private Client client = null;
	private SinglePlayer singlePlayer;
	private MultiPlayer multiPlayer;
	private MainMenuStartImage main;
	
	public MainMenuWithGraphics() {
		this.singlePlayer = new SinglePlayer("Singleplayer", (int) (Constants.screenWidth/2), (int) (Constants.screenHeight/2));
		this.multiPlayer = new MultiPlayer("Multiplayer", (int) (Constants.screenWidth*0.888f), (int) (Constants.screenHeight*0.500f));
		this.main = new MainMenuStartImage();
	}
	@Override
	public boolean onTouchDown(MotionEvent event) {
		if(singlePlayer.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new GameState());
		} else if(multiPlayer.getBounds().contains(event.getX(), event.getY())) {
			if(this.client == null) {
				this.client = new Client();
				Thread clientThread = new Thread(this.client);
				clientThread.start();
				GameState gameState = new GameState(this.client);
				getGame().pushState(gameState);
				this.client.setGameState(gameState);
			}
		}
		return false;
	}
	@Override
	public boolean onTouchMove(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchMove(event);
	}
	@Override
	public boolean onTouchUp(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchUp(event);
	}
	
	public void update(float dt) {
		System.out.println(singlePlayer.getPosition());
		singlePlayer.update(dt);
		multiPlayer.update(dt);
	}
	
	public void draw(Canvas canvas) {
		singlePlayer.draw(canvas);
		multiPlayer.draw(canvas);
	}
}
