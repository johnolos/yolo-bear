package bomberman.states;

import android.graphics.Canvas;
import android.view.MotionEvent;
import bomberman.connection.Client;
import bomberman.game.Constants;
import sheep.game.State;
import sheep.input.TouchListener;

public class MainState extends State  implements TouchListener {
	private MainMenuWithGraphics single;
	private MainMenuWithGraphics multi;
	private Client client;
	
	public MainState() {
		this.single = new MainMenuWithGraphics("Single", (int) (Constants.screenWidth*0.5f), (int) (Constants.screenHeight*0.3f));
		this.multi = new MainMenuWithGraphics("multi", (int) (Constants.screenWidth*0.888f), (int) (Constants.screenHeight*0.3125f));
		
		
	}
	
	public boolean onTouchDown(MotionEvent event) {
		if(single.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new SinglePlayerState());
			
			
		} else if(multi.getBounds().contains(event.getX(),event.getY())) {
			getGame().pushState(new MultiplayerState());
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
	
	public void update(float dt) {
		super.update(dt);
	}
	
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

}
