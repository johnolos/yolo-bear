package bomberman.states;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import bomberman.connection.Client;
import bomberman.game.ColorObject;
import bomberman.game.Constants;
import bomberman.game.R;
import bomberman.graphics.Buttons;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.input.TouchListener;

public class SetNumberPlayerState extends State implements TouchListener {

	private ColorObject color;
	private Buttons one, two, three;
	private Image oneImage, twoImage, threeImage;
	private float x, y;

	public SetNumberPlayerState(ColorObject color) {
		this.color = color;
		
		
		//Buttons
		// One
		oneImage = new Image(R.drawable.fireone);
		x = (Constants.getScreenWidth() / 2 - oneImage.getWidth() * 2);
		y = (Constants.getScreenHeight() / 2 - oneImage.getHeight() / 2);
		one = new Buttons(oneImage, (int) x, (int) y);
		//two
		twoImage = new Image(R.drawable.firetwo);
		x = (Constants.getScreenWidth() / 2 - twoImage.getWidth() / 2);
		y = (Constants.getScreenHeight() / 2 - twoImage.getHeight() / 2);
		two = new Buttons(twoImage, (int) x, (int) y);
		//Three
		threeImage = new Image(R.drawable.firethree);
		x = (float) (Constants.getScreenWidth() / 2 + threeImage.getWidth() * 1);
		y = (Constants.getScreenHeight() / 2 - threeImage.getHeight() / 2);
		three = new Buttons(threeImage, (int) x, (int) y);
		

	}

	public void update(float dt) {
		one.update(dt);
		two.update(dt);
		three.update(dt);
	}

	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		one.draw(canvas);
		two.draw(canvas);
		three.draw(canvas);
	}

	// public boolean onTouchDown(MotionEvent event) {
	// if(one.getBounds().contains(event.getX(), event.getY())) {
	// singlePlayer.changeImageShow(1);
	// } else if(multiPlayer.getBounds().contains(event.getX(), event.getY())) {
	// multiPlayer.changeImageShow(1);
	// } else if(tutorial.getBounds().contains(event.getX(), event.getY())) {
	// tutorial.changeImageShow(1);
	// // TutorialState tutorial = new TutorialState();
	// // getGame().pushState(tutorial);
	// System.out.println("Tutorial startup");
	// }
	//
	// return false;
	// }

	@Override
	public boolean onTouchUp(MotionEvent event) {
		if (one.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new GameState(this.color, 1));
		} else if (two.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new GameState(this.color, 2));
		} else if (three.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new GameState(this.color, 3));
		}
		return false;
	}

}
