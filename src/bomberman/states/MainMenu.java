package bomberman.states;

import bomberman.connection.Client;
import android.graphics.Canvas;
import sheep.game.Game;
import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

public class MainMenu extends State implements WidgetListener{
	
	Client client;
	
	private TextButton singlePlayer = new TextButton(100, 100,"Single Player");
	private TextButton connect = new TextButton(600, 600, "Connect");
	private TextButton send = new TextButton(700,600, "Send");
	
	public MainMenu(){
		addTouchListener(singlePlayer);
		addTouchListener(connect);
		addTouchListener(send);
		
		singlePlayer.addWidgetListener(this);
		connect.addWidgetListener(this);
		send.addWidgetListener(this);
	}
	
	public void update(float dt){
		
	}
	public void draw(Canvas canvas){
		singlePlayer.draw(canvas);
		connect.draw(canvas);
		send.draw(canvas);
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == singlePlayer){
			getGame().pushState(new SinglePlayerState());
		} else if(action.getSource() == connect) {
			if(this.client == null) {
				this.client = new Client();
				Thread clientThread = new Thread(this.client);
				clientThread.start();
			}
		} else if(action.getSource() == send) {
			if(this.client != null) {
				String message = "'Hello Server!' -Android.";
				this.client.send(message);
			}
		}
		
	}
}
