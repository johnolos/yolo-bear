package bomberman.states;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import bomberman.game.ColorObject;
import bomberman.game.Constants;
import bomberman.game.R;
import bomberman.graphics.Buttons;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.input.TouchListener;

/**
 * Extends State implements TouchListener
 */
public class SetNumberOfPlayerState extends State implements TouchListener {
	private ColorObject color;
	private Buttons one, two, three, textS;
	private Image oneImage, twoImage, threeImage, text;
	private float x, y;
	private LoadingMultiplayer loading;

	/**
	 * The Constructor
	 * @param color the color of the object
	 * @param loading multiplayer loading screen
	 */
	public SetNumberOfPlayerState(ColorObject color, LoadingMultiplayer loading) {
		this.color = color;
		this.loading = loading;
		// text
		text = new Image(R.drawable.chooseoponents);
		x = (Constants.getScreenWidth() / 2 - text.getWidth() / 2);
		y = (Constants.getHeight() / 6);
		textS = new Buttons(text, (int) x, (int) y);
		textS.setPosition(x, y);
		// Buttons
		// One
		oneImage = new Image(R.drawable.fireone);
		x = (Constants.getScreenWidth() / 2 - oneImage.getWidth() * 2);
		y = (Constants.getScreenHeight() / 2 - oneImage.getHeight() / 2);
		one = new Buttons(oneImage, (int) x, (int) y);
		// two
		twoImage = new Image(R.drawable.firetwo);
		x = (Constants.getScreenWidth() / 2 - twoImage.getWidth() / 2);
		y = (Constants.getScreenHeight() / 2 - twoImage.getHeight() / 2);
		two = new Buttons(twoImage, (int) x, (int) y);
		// Three
		threeImage = new Image(R.drawable.firethree);
		x = (float) (Constants.getScreenWidth() / 2 + threeImage.getWidth() * 1);
		y = (Constants.getScreenHeight() / 2 - threeImage.getHeight() / 2);
		three = new Buttons(threeImage, (int) x, (int) y);

	}

	/**
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * @param dt
	 */
	public void update(float dt) {
		one.update(dt);
		two.update(dt);
		three.update(dt);
		textS.update(dt);
	}

	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * @param canvas which you draw on.
	 */
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		one.draw(canvas);
		two.draw(canvas);
		three.draw(canvas);
		textS.draw(canvas);
	}

	public boolean onTouchDown(MotionEvent event) {
		if (one.getBounds().contains(event.getX(), event.getY())) {
			one.setView(new Image(R.drawable.pressedfireone));
		} else if (two.getBounds().contains(event.getX(), event.getY())) {
			two.setView(new Image(R.drawable.pressedfiretwo));
		} else if (three.getBounds().contains(event.getX(), event.getY())) {
			three.setView(new Image(R.drawable.pressedfirethree));
		}
		return false;
	}

	/**
	 * onTouchUp what happens when you release the screen
	 * @param event
	 */
	@Override
	public boolean onTouchUp(MotionEvent event) {
		if (one.getBounds().contains(event.getX(), event.getY())) {
			if (loading != null) {
				loading.setNrOfPlayers(1);
				getGame().popState();
			} else
				getGame().pushState(new GameState(this.color, 1));
		} else if (two.getBounds().contains(event.getX(), event.getY())) {
			if (loading != null) {
				loading.setNrOfPlayers(2);
				getGame().popState();
			} else
				getGame().pushState(new GameState(this.color, 2));
		} else if (three.getBounds().contains(event.getX(), event.getY())) {
			if (loading != null) {
				loading.setNrOfPlayers(3);
				getGame().popState();
			} else
				getGame().pushState(new GameState(this.color, 3));
		}
		one.setView(new Image(R.drawable.fireone));
		two.setView(new Image(R.drawable.firetwo));
		three.setView(new Image(R.drawable.firethree));
		return false;
	}
	

}
