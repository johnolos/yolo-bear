package bomberman.states;

import android.graphics.Canvas;
import android.view.MotionEvent;
import bomberman.buttons.NextTutorial;
import bomberman.buttons.PreviousTutorial;
import bomberman.game.Constants;
import bomberman.graphics.TutorialImages;
import sheep.game.State;
import sheep.input.TouchListener;

public class TutorialState extends State implements TouchListener {
	private NextTutorial next;
	private PreviousTutorial prev;
	private TutorialImages img;
	public TutorialState() {
		next = new NextTutorial("next",Constants.getScreenWidth(),(Constants.getScreenWidth()/2));
		prev = new PreviousTutorial("prev", 0, (Constants.getScreenWidth()/2));
		img = new TutorialImages(Constants.getScreenWidth(),(Constants.getScreenWidth()/2));
	}
	
	public void update(float dt) {
		next.update(dt);
		prev.update(dt);
		img.update(dt);
	}
	
	public void draw(Canvas canvas) {
		next.draw(canvas);
		prev.draw(canvas);
		img.draw(canvas);
	}
	
	@Override
	public boolean onTouchDown(MotionEvent event) {
		if(next.getBounds().contains(event.getX(), event.getY())) {
			img.getViewFromArray(1);
		} else if(prev.getBounds().contains(event.getX(), event.getY())) {
			prev.changeImageShow(1);
		}
		return false;
	}
	
	@Override
	public boolean onTouchUp(MotionEvent event) {
		if(next.getBounds().contains(event.getX(), event.getY())) {
			img.getViewFromArray(1);
		} else if(prev.getBounds().contains(event.getX(), event.getY())) {
			img.getViewFromArray(0);
		}
		next.changeImageShow(0);
		prev.changeImageShow(0);
		return false;
	}
}
