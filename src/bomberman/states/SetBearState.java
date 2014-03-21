package bomberman.states;

import bomberman.game.ColorObject;
import bomberman.graphics.Buttons;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.widget.Button;
import sheep.game.State;
import sheep.gui.WidgetListener;
import sheep.input.TouchListener;

public class SetBearState extends State implements TouchListener{
	Buttons brown,black,white,swag;

	
	public SetBearState(){
		brown = new Buttons("brown", 0, 0);
		black = new Buttons("black", 0, 0);
		white = new Buttons("white", 0, 0);
		swag = new Buttons("swag", 0, 0);
		
		
	}
	
	@Override
	public boolean onTouchDown(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouchUp(MotionEvent event) {
		if(brown.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new SetNumberPlayerState(ColorObject.BROWN));
		}
		else if(black.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new SetNumberPlayerState(ColorObject.BLACK));
		}
		else if(white.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new SetNumberPlayerState(ColorObject.WHITE));
		}
		else if(swag.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new SetNumberPlayerState(ColorObject.SWAG));
		}
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void update(float dt){
		black.update(dt);
		brown.update(dt);
		white.update(dt);
		swag.update(dt);
	}
	public void draw(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		black.draw(canvas);
		brown.draw(canvas);
		white.draw(canvas);
		swag.draw(canvas);
		
	}

}
