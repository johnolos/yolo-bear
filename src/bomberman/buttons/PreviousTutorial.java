package bomberman.buttons;

import android.graphics.Canvas;
import android.graphics.Rect;
import bomberman.game.R;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.BoundingBox;

public class PreviousTutorial extends Sprite {
	private Image prevTutorial, pressedPrev;
	private BoundingBox box;

	public PreviousTutorial(String Id, float x, float y) {
		Rect bounds = null;
		if(Id.equals("prev")) {
			prevTutorial = new Image(R.drawable.previousbutton);
			this.setView(prevTutorial);
			this.setPosition(x,y);
			//needs updating
			bounds  = new Rect((int)(x-prevTutorial.getWidth()),(int)(y-prevTutorial.getWidth()),(int)(x+prevTutorial.getWidth()),(int)(y+prevTutorial.getHeight()));
		}
		box = new BoundingBox(bounds);
		pressedPrev = new Image(R.drawable.pressedpreviousbutton);
	}
	
	public void update(float dt) {
		super.update(dt);
	}
	
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	
	public BoundingBox getBounds() {
		return this.box;
	}
	
	public void changeImageShow(int n) {
		if(n==1) {
			this.setView(pressedPrev);
		} else {
			this.setView(prevTutorial);
		}
	}
}
