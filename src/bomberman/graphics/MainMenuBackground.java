package bomberman.graphics;

import android.graphics.Canvas;
import bomberman.game.Constants;
import bomberman.game.R;
import sheep.game.Sprite;
import sheep.graphics.Image;

/**
 * This class extends Sprite
 */
public class MainMenuBackground extends Sprite {
	private Image startImage;
	
	/**
	 * The constructor for this class, it loads an image sets it into the view and its position
	 */
	public MainMenuBackground() {
		startImage = new Image(R.drawable.mainscreen);
		this.setView(startImage);
		this.setPosition((Constants.getScreenWidth()/2)-(startImage.getWidth()/2), (Constants.getScreenHeight()/2)-(startImage.getHeight()/2));
	}
	
	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * This draw calls the draw function in the super class
	 * @param canvas which you draw on.
	 */
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	
	/**
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * This function also calls the update function in the super class
	 * @param dt
	 */
	public void update(float dt) {
		super.update(dt);
	}

}
