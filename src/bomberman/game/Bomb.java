package bomberman.game;

import sheep.game.Sprite;
import sheep.graphics.Image;
import android.graphics.Canvas;

public class Bomb extends Sprite{
	
	private int blastRadius; //the "length" of the explosion
	private Image bomb;
	private int x,y;
	
	public Bomb(int x, int y, int blastRadius){
		this.blastRadius = blastRadius;
		this.setPosition(x, y);
		if(Constants.screenHeight == 1600){
			this.bomb = new Image(R.drawable.bomblarge);
			this.setShape(120,120);
		}
		else if(Constants.screenHeight == 752){
			this.bomb = new Image(R.drawable.bomblarge);
			this.setShape(60,60);
		}
		else{
			this.bomb = new Image(R.drawable.bomb);
			this.setShape(40,40);
		}
		this.setView(bomb);
		
	}
	
	public void update(float dt){
		super.update(dt);
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
	
}
