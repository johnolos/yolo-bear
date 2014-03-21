package bomberman.states;

import bomberman.connection.Client;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.input.TouchListener;

public class loadingMultiplayer extends State implements TouchListener {
	
	private Client client;
	private GameState gameState;

	
	public loadingMultiplayer(){
		if(this.client == null) {
			this.client = new Client();
			gameState = new GameState(this.client);
			this.client.setGameState(gameState);
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
		if(client.getClientConnectionCount()>0){
			
			getGame().pushState(gameState);
			
		}
	}
	
	public void draw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
	}

}
