package bomberman.buttons;

import android.graphics.Canvas;
import android.graphics.Rect;
import bomberman.game.R;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.BoundingBox;

public class NextTutorial extends Sprite {
	private Image nextTutorial, nextPressed;
	private BoundingBox box;

	public NextTutorial(String Id, float x, float y) {
		Rect bounds = null;
		if(Id.equals("next")) {
			nextTutorial = new Image(R.drawable.nextbutton);
			this.setView(nextTutorial);
			this.setPosition(x,y);
			//needs updating
			bounds  = new Rect((int)(x-nextTutorial.getWidth()),(int)(y-nextTutorial.getWidth()),(int)(x+nextTutorial.getWidth()),(int)(y+nextTutorial.getHeight()));
		}
		box = new BoundingBox(bounds);
		nextPressed = new Image(R.drawable.pressednextbutton);
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
			this.setView(nextPressed);
		} else {
			this.setView(nextTutorial);
		}
	}
}
