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
	
	public MainMenu(){
		addTouchListener(singlePlayer);
		addTouchListener(connect);
		
		singlePlayer.addWidgetListener(this);
		connect.addWidgetListener(this);
	}
	
	public void update(float dt){
		
	}
	public void draw(Canvas canvas){
		singlePlayer.draw(canvas);
		connect.draw(canvas);
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == singlePlayer){
			getGame().pushState(new SinglePlayerState());
		}
		
	}
}
