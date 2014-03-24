package bomberman.notused;

import bomberman.connection.Client;
import bomberman.states.GameState;
import android.graphics.Canvas;
import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

public class MultiplayerState extends State implements WidgetListener {
	//NOT IN USE
	
	private Client client;
	
	private TextButton back = new TextButton(100, 100, "Back");
	
	private TextButton connect = new TextButton(100, 300, "Connect");
	private TextButton send = new TextButton(300,300, "Send");
	private TextButton peer = new TextButton(600,300, "Peer");
	
	
	public MultiplayerState(){
		
		addTouchListener(back);
		addTouchListener(connect);
		addTouchListener(send);
		addTouchListener(peer);
		
		back.addWidgetListener(this);
		connect.addWidgetListener(this);
		send.addWidgetListener(this);
		peer.addWidgetListener(this);
	}
	
	
	public void update(float dt) {
		
	}
	
	public void draw(Canvas canvas) {
		back.draw(canvas);
		connect.draw(canvas);
		send.draw(canvas);
		peer.draw(canvas);
	}

	@Override
	public void actionPerformed(WidgetAction action) {
	if(action.getSource() == connect) {
		if(this.client == null) {
			this.client = new Client();
			Thread clientThread = new Thread(this.client);
			clientThread.start();
			GameState gameState = new GameState(this.client);
			getGame().pushState(gameState);
			this.client.setGameState(gameState);
		}
	} else if(action.getSource() == send) {
		if(this.client != null) {
			String message = "'Hello Server!' -Android.";
			this.client.send(message);
		}
	} else if(action.getSource() == peer) {
		if(this.client != null) {
			this.client.sendAll("This button just sucks!");
		}
	}
		
	}

}
