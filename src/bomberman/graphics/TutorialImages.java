package bomberman.graphics;

import java.util.ArrayList;

import android.graphics.Canvas;
import bomberman.game.Constants;
import bomberman.game.R;

import sheep.game.Sprite;
import sheep.graphics.Image;

public class TutorialImages extends Sprite {
	private Image next, prev;
	private Image tutImgOne, tutImgTwo;
	
	public TutorialImages() {
		next = new Image(R.drawable.next);
		this.setPosition(Constants.getScreenWidth()-150, Constants.getScreenHeight()/2);
		prev = new Image(R.drawable.previous);
		this.setPosition(150, Constants.getScreenHeight()/2);
		tutImgOne = new Image(R.drawable.tutorialimgone);
		this.setPosition(Constants.getScreenWidth()/2, Constants.getScreenHeight()/2);
	}
	
	public void update(float dt) {
		super.update(dt);
	}
	
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

}
