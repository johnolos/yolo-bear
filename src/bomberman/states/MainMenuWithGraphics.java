package bomberman.states;

import android.graphics.Canvas;
import bomberman.game.R;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

public class MainMenuWithGraphics extends Sprite implements WidgetListener{
//	private Image singlePlayer; 
//	private Image multiPlayer;
	private TextButton singlePlayer2 = new TextButton(200,200,"SinglePlayer");
	private TextButton multiPlayer2 = new TextButton(200,200,"Multiplayer");
	
	/**
	 * The constructor where the widgetListener is added to the singleplayer and multiplayer
	 * textbuttons is added.
	 */
	public MainMenuWithGraphics() {
//		singlePlayer = new Image(R.drawable.singleplayer);
//		multiPlayer = new Image(R.drawable.multiplayer);
		singlePlayer2.addWidgetListener(this);
		multiPlayer2.addWidgetListener(this);
	}
	
	public void draw(Canvas canvas) {
		
	}
	
	public void update(float dt) {
		
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		if(action.getSource()==singlePlayer2) {
			//DO SHIT
		} else if(action.getSource()== multiPlayer2) {
			//DO SHIT
		}
		
	}
}
