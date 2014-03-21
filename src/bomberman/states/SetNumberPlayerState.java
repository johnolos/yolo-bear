package bomberman.states;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import bomberman.connection.Client;
import bomberman.game.ColorObject;
import bomberman.graphics.Buttons;
import sheep.game.State;
import sheep.input.TouchListener;

public class SetNumberPlayerState extends State implements TouchListener {
	
	
 private ColorObject color;
 private Buttons one,two,three;

public SetNumberPlayerState(ColorObject color){
	 this.color = color;
	 one = new Buttons("one",0,0);
	 two = new Buttons("two",0,0);
	 three = new Buttons("three",0,0);
 }
 
 public void update(float dt){
	 one.update(dt);
	 two.update(dt);
	 three.update(dt);
 }
 public void draw(Canvas canvas){
	 canvas.drawColor(Color.BLACK);
	 one.draw(canvas);
	 two.draw(canvas);
	 three.draw(canvas);
 }
// public boolean onTouchDown(MotionEvent event) {
//		if(one.getBounds().contains(event.getX(), event.getY())) {
//			singlePlayer.changeImageShow(1);
//		} else if(multiPlayer.getBounds().contains(event.getX(), event.getY())) {
//			multiPlayer.changeImageShow(1);
//		} else if(tutorial.getBounds().contains(event.getX(), event.getY())) {
//			tutorial.changeImageShow(1);
////			TutorialState tutorial = new TutorialState();
////			getGame().pushState(tutorial);
//			System.out.println("Tutorial startup");
//		}
//		
//		return false;
//	}
	
	@Override
	public boolean onTouchUp(MotionEvent event) {
		if(one.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new GameState(this.color,1));
		} 
		else if(two.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new GameState(this.color,2));
		}
		else if(three.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new GameState(this.color,3));
		}
		return false; 
	}
 
 
 

}
