package bomberman.game;

import android.graphics.Canvas;

import sheep.collision.CollisionListener;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Crate extends Sprite {
	private Image crate;
	
	public Crate(){
		if(Constants.screenHeight == 1600){
			this.crate = new Image(R.drawable.cratelarge);
			this.setView(crate);
			this.setShape(120,120);
		}
		else if(Constants.screenHeight == 752) {
			this.crate = new Image(R.drawable.cratelarge);
			this.setView(crate);
			this.setShape(60,60);
		}
		else{
			this.crate = new Image(R.drawable.crate);
			this.setView(crate);
			this.setShape(40,40);
		}
	}
	
	public void update(float dt){
		super.update(dt);
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
}