package bomberman.game;

import android.graphics.Canvas;
import sheep.game.Sprite;
import sheep.graphics.Image;

/**
 * Wall extends Sprite
 */
public class Wall extends Sprite {
	private Image wall;
	
	/**
	 * Constructor
	 */
	public Wall(){
		if(Constants.screenHeight  == 1600){
			this.wall = new Image(R.drawable.wall);
			this.setView(wall);
			this.setShape(120,120);
		}
		else if(Constants.screenHeight == 752) {
			this.wall = new Image(R.drawable.wall);
			this.setView(wall);
			this.setShape(70,70);
		}
		else{
			this.wall = new Image(R.drawable.smallwall);
			this.setView(wall);
			this.setShape(40,40);
		}
	}
	
	/**
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * This function also calls the update function in the super class
	 * @param dt
	 */
	public void update(float dt){
		super.update(dt);
	}
	
	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * This draw calls the draw function in the super class
	 * @param canvas which you draw on.
	 */
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
}
