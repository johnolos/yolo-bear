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
	private TextButton multiplayer = new TextButton(300,100,"Multiplayer");
	
	public MainMenu(){
		addTouchListener(singlePlayer);
		addTouchListener(multiplayer);
		
		singlePlayer.addWidgetListener(this);
		multiplayer.addWidgetListener(this);
	}
	
	public void update(float dt){
		
	}
	
	public void draw(Canvas canvas){
		singlePlayer.draw(canvas);
		multiplayer.draw(canvas);
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == singlePlayer){
			getGame().pushState(new SinglePlayerState());
		} else if(action.getSource() == multiplayer) {
			getGame().pushState(new MultiplayerState());
		}
	}
}
