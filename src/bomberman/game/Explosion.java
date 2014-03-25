package bomberman.game;

import android.graphics.Canvas;
import sheep.game.Sprite;
import sheep.graphics.Image;
/**
 * Explosion extends Sprite
 */
public class Explosion extends Sprite {
	
	private double time;
	private boolean removeMe = false;
	
	/**
	 * Constructor
	 * @param x x-coordinate of explosion
	 * @param y y-coordinate of explosion
	 * @param image image for explosion
	 */
	public Explosion(float x, float y, Image image){
		this.time = System.currentTimeMillis();
		this.setPosition(x, y);
		this.setView(image);
	}
	
	/**
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * This function also calls the update function in the super class
	 * @param dt
	 */
	public void update(float dt){
		checkTime();
		super.update(dt);
	}
	
	/**
	 * Check time for explosion
	 */
	private void checkTime() {
		if(System.currentTimeMillis()-this.time>1000){
			removeMe = true;
		}
		
	}

	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * This draw calls the draw function in the super class
	 * @param canvas which you draw on.
	 */
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
	
	/**
	 * Remove the explosion
	 * @return true if explosion has happend
	 */
	public boolean removeExplosion(){
		return removeMe;
	}

}
