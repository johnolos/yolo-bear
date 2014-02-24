package bomberman.states;

import android.graphics.Canvas;
import sheep.game.State;
import sheep.gui.TextButton;

public class MainMenu extends State {
	
	
	
	private TextButton singlePlayer = new TextButton(100, 100,"Single Player");
	
	public void update(float dt){
		
	}
	public void draw(Canvas canvas){
		singlePlayer.draw(canvas);
	}
}
