package bomberman.graphics;

import java.util.ArrayList;

import android.graphics.Canvas;
import bomberman.buttons.NextTutorial;
import bomberman.buttons.PreviousTutorial;
import bomberman.game.Constants;
import bomberman.game.R;

import sheep.game.Sprite;
import sheep.graphics.Image;

public class TutorialImages extends Sprite {
	private Image tutImgOne, tutImgTwo;
	private ArrayList<Image> img;
	
	public TutorialImages(float x, float y) {
		img = new ArrayList<Image>();
		tutImgOne = new Image(R.drawable.tutorialimgone);
		this.setPosition(x, y);
		this.setView(tutImgOne);
		tutImgTwo = new Image(R.drawable.tutorialimgtwo);
		addToArray();
	}
	
	private void addToArray() {
		img.add(tutImgOne);
		img.add(tutImgTwo);
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

}
