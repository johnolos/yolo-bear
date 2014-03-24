package bomberman.graphics;

import java.util.ArrayList;

import android.graphics.Canvas;
import bomberman.game.R;

import sheep.game.Sprite;
import sheep.graphics.Image;

public class TutorialImages extends Sprite {
	private Image tutImgOne, tutImgTwo, tutImgThree, tutImgFour, tutImgFive, tutImgSix;
	private ArrayList<Image> img;
	
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
	
	private void addToArray() {
		img.add(tutImgOne);
		img.add(tutImgTwo);
		img.add(tutImgThree);
		img.add(tutImgFour);
		img.add(tutImgFive);
		img.add(tutImgSix);
	}

	public void update(float dt) {
		super.update(dt);
	}
	
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	
	public void getViewFromArray(int n) {
			this.setView(img.get(n));
	}
	
	public int getArraySize() {
		return this.img.size();
	}

}
