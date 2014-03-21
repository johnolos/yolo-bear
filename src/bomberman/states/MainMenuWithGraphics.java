package bomberman.states;

import bomberman.buttons.MultiPlayer;
import bomberman.buttons.SinglePlayer;
import bomberman.buttons.TutorialButton;
import bomberman.connection.Client;
import bomberman.game.Constants;
import bomberman.graphics.MainMenuStartImage;
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
	private TutorialButton tutorial;
	private MainMenuStartImage main;
	
	public MainMenuWithGraphics() {
		this.singlePlayer = new SinglePlayer("Singleplayer", (int) (Constants.screenWidth/2), (int) (Constants.screenHeight/2)+90);
		this.multiPlayer = new MultiPlayer("Multiplayer", (int) (Constants.screenWidth/2), (int) (Constants.screenHeight/2)+150);
		this.main = new MainMenuStartImage();
		this.tutorial = new TutorialButton("Tutorial", (int) (Constants.screenWidth/2), (int) (Constants.screenHeight/2)+250);
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
		} else if(tutorial.getBounds().contains(event.getX(), event.getY())) {
//			TutorialState tutorial = new TutorialState();
//			getGame().pushState(tutorial);
			System.out.println("Tutorial startup");
		}
		return false;
	}
	
	public void update(float dt) {
		System.out.println(singlePlayer.getPosition());
		main.update(dt);
		singlePlayer.update(dt);
		multiPlayer.update(dt);
		tutorial.update(dt);
	}
	
	public void draw(Canvas canvas) {
		main.draw(canvas);
		singlePlayer.draw(canvas);
		multiPlayer.draw(canvas);
		tutorial.draw(canvas);
	}
}
