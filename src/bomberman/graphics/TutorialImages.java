package bomberman.graphics;

import java.util.ArrayList;

import android.graphics.Canvas;
import bomberman.game.R;

import sheep.game.Sprite;
import sheep.graphics.Image;

/**
 * This class extends Sprite.
 */
public class TutorialImages extends Sprite {
	private Image tutImgOne, tutImgTwo, tutImgThree, tutImgFour, tutImgFive, tutImgSix;
	private ArrayList<Image> img;
	
	/**
	 * The constructor for this class, this loads the images and calls the function addToarray.
	 * @param x the x-coordinate where the Images from the ArrayList<Image> img is to be placed
	 * @param y the y-coordinate where the Images from the ArrayList<Image> img is to be placed
	 */
	public TutorialImages(float x, float y) {
		img = new ArrayList<Image>();
		tutImgOne = new Image(R.drawable.tutorialone);
		tutImgTwo = new Image(R.drawable.tutorialtwo);
		tutImgThree = new Image(R.drawable.tutorialthree);
		tutImgFour = new Image(R.drawable.tutorialfour);
		tutImgFive = new Image(R.drawable.tutorialfive);
		tutImgSix = new Image(R.drawable.tutorialsix);
		addToArray();
		this.setPosition(x, y);
		this.setView(img.get(0));
	}
	
	/**
	 * Adds the images into the ArrayList<Image>
	 */
	private void addToArray() {
		img.add(tutImgOne);
		img.add(tutImgTwo);
		img.add(tutImgThree);
		img.add(tutImgFour);
		img.add(tutImgFive);
		img.add(tutImgSix);
	}

	/**
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * This function also calls the update function in the super class
	 * @param dt
	 */
	public void update(float dt) {
		super.update(dt);
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
	 * Sets the view by using the int n to get the image from the array, and sets this image onto the screen.
	 * @param n the index to decide which image to be set into the view
	 */
	public void getViewFromArray(int n) {
			this.setView(img.get(n));
	}
	
	/**
	 * Gets the size of the ArrayList
	 * @return this.img.size() the size of the ArrayList as an int
	 */
	public int getArraySize() {
		return this.img.size();
	}

}
