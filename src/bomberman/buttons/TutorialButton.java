package bomberman.buttons;

import bomberman.game.R;
import android.graphics.Canvas;
import android.graphics.Rect;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.BoundingBox;

public class TutorialButton extends Sprite {
	private Image tutorial;
	private Image pressedTutorial;
	private BoundingBox box;
	
	public TutorialButton(String id, float x, float y) {
		Rect bounds = null;
		if(id.equals("Tutorial")) {
			tutorial = new Image(R.drawable.tutorialbutton);
			this.setView(tutorial);
			this.setPosition(x-(tutorial.getWidth()/2),(float)(y+(tutorial.getHeight()*2.5)));
			bounds  = new Rect((int)(x-tutorial.getWidth()),(int)(y-tutorial.getWidth()),(int)(x+tutorial.getWidth()),(int)(y+tutorial.getHeight()));
		}
		box = new BoundingBox(bounds);
		pressedTutorial = new Image(R.drawable.pressedtutorialbutton);
	}
	
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	
	public void update(float dt) {
		super.update(dt);
	}
	
	public BoundingBox getBounds() {
		return this.box;
	}

	public void changeImageShow(int n) {
		if(n==1) {
			this.setView(pressedTutorial);
		} else {
			this.setView(tutorial);
		}
	}
}
