package bomberman.game;

import android.graphics.Canvas;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Wall extends Sprite {
	
	
	private Image wall;
	
	public Wall(){
		if(Constants.screenHeight  == 1600){
			this.wall = new Image(R.drawable.walllarge);
			this.setView(wall);
			this.setShape(120,120);
		}
		else{
			this.wall = new Image(R.drawable.wall);
			this.setView(wall);
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
