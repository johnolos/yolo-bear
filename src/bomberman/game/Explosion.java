package bomberman.game;

import android.graphics.Canvas;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Explosion extends Sprite {
	
	private double time;
	private boolean removeMe = false;
	
	public Explosion(float x, float y, Image image){
		this.time = System.currentTimeMillis();
		this.setPosition(x, y);
		this.setView(image);
	}
	
	/**
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * This function also calls the update function in the super class
	 * @param float dt
	 */
	public void update(float dt){
		checkTime();
		super.update(dt);
	}
	
	private void checkTime() {
		if(System.currentTimeMillis()-this.time>1000){
			removeMe = true;
		}
		
	}

	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * This draw calls the draw function in the super class
	 * @param Canvas canvas which you draw on.
	 */
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
	
	public boolean removeExplosion(){
		return removeMe;
	}

}
