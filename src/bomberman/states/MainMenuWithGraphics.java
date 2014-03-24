package bomberman.states;

import bomberman.buttons.MultiPlayer;
import bomberman.buttons.SinglePlayer;
import bomberman.buttons.TutorialButton;
import bomberman.connection.Client;
import bomberman.game.Constants;
import bomberman.game.R;
import bomberman.graphics.MainMenuStartImage;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.input.TouchListener;

public class MainMenuWithGraphics extends State implements TouchListener {
	private Client client = null;
	private SinglePlayer singlePlayer;
	private MultiPlayer multiPlayer;
	private TutorialButton tutorial;
	private MainMenuStartImage main;
	private Image singlePlayerImage;
	
	public MainMenuWithGraphics() {
		singlePlayerImage = new Image(R.drawable.singleplayerbutton);
		this.singlePlayer = new SinglePlayer("Singleplayer", (int) (Constants.screenWidth/2), (int) (Constants.screenHeight/2));
		this.multiPlayer = new MultiPlayer("Multiplayer", (int) (Constants.screenWidth/2), (int) (Constants.screenHeight/2));
		this.main = new MainMenuStartImage();
		this.tutorial = new TutorialButton("Tutorial", (int) (Constants.screenWidth/2), (int) (Constants.screenHeight/2));
	}
	@Override
	public boolean onTouchDown(MotionEvent event) {
		if(singlePlayer.getBounds().contains(event.getX(), event.getY())) {
			singlePlayer.changeImageShow(1);
		} else if(multiPlayer.getBounds().contains(event.getX(), event.getY())) {
			multiPlayer.changeImageShow(1);
		} else if(tutorial.getBounds().contains(event.getX(), event.getY())) {
			tutorial.changeImageShow(1);
//			TutorialState tutorial = new TutorialState();
//			getGame().pushState(tutorial);
		}
		
		return false;
	}
	
	@Override
	public boolean onTouchUp(MotionEvent event) {
		if(singlePlayer.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new SetBearState());
		} else if(multiPlayer.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new LoadingMultiplayer());
//			if(this.client == null) {
//				this.client = new Client();
//				Thread clientThread = new Thread(this.client);
//				clientThread.start();
//				GameState gameState = new GameState(this.client);
//				getGame().pushState(gameState);
//				this.client.setGameState(gameState);
//			}
		} else if(tutorial.getBounds().contains(event.getX(), event.getY())) {
			TutorialState tutorial = new TutorialState();
			getGame().pushState(tutorial);
//			tutorial.changeImageShow(0);
//			TutorialState tutorial = new TutorialState();
			getGame().pushState(tutorial);
		}
		singlePlayer.changeImageShow(0);
		multiPlayer.changeImageShow(0);
		tutorial.changeImageShow(0);
		return false;
	}
	
	public void update(float dt) {
		main.update(dt);
		singlePlayer.update(dt);
		multiPlayer.update(dt);
		tutorial.update(dt);
	}
	
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		main.draw(canvas);
		singlePlayer.draw(canvas);
		multiPlayer.draw(canvas);
		tutorial.draw(canvas);
	}
}
