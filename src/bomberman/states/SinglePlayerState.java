package bomberman.states;

import android.graphics.Canvas;
import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

public class SinglePlayerState extends State implements WidgetListener {
	
	private TextButton start = new TextButton(300, 600, "Start");
	
	public SinglePlayerState(){
		addTouchListener(start);
		start.addWidgetListener(this);
		
	}
	public void update(float dt){
		
	}
	
	public void draw(Canvas canvas){
		start.draw(canvas);
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == start){
//			getGame().pushState(new GameState());
		}
		
	}

}
