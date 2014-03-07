package bomberman.states;

import android.graphics.Canvas;
import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

public class SinglePlayerState extends State implements WidgetListener {
	
	private TextButton back = new TextButton(300, 600, "Back");
	
	public SinglePlayerState(){
		addTouchListener(back);
		back.addWidgetListener(this);
		
	}
	public void update(float dt){
		
	}
	public void draw(Canvas canvas){
		back.draw(canvas);
	}

	@Override
	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == back){
			getGame().popState();
		}
		
	}

}
