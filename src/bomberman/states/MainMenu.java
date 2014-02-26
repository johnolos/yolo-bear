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
	private TextButton game = new TextButton(600, 100, "Game");
	
	
	//This class is no way near finished. Adds simple buttons to access multiplayer- and singleplayer -states.
	public MainMenu(){
		addTouchListener(singlePlayer);
		addTouchListener(multiplayer);
		addTouchListener(game);
		
		singlePlayer.addWidgetListener(this);
		multiplayer.addWidgetListener(this);
		game.addWidgetListener(this);
	}
	
	public void update(float dt){
		
	}
	
	public void draw(Canvas canvas){
		singlePlayer.draw(canvas);
		multiplayer.draw(canvas);
		game.draw(canvas);
	}

	@Override
	//Changing the game state being shown on canvas 
	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == singlePlayer){
			getGame().pushState(new SinglePlayerState());
		} else if(action.getSource() == multiplayer) {
			getGame().pushState(new MultiplayerState());
		} else if(action.getSource() == game) {
//			getGame().pushState(new GameState());
		}
	}
}
