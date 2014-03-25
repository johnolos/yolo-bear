package bomberman.states;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import bomberman.game.Constants;
import bomberman.game.R;
import bomberman.graphics.Buttons;
import bomberman.graphics.TutorialImages;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.input.TouchListener;

/**
 * Extends State and implements TouchListener
 */
public class TutorialState extends State implements TouchListener {
	private TutorialImages img;
	private int imgIndex=0;
	private Image nextImage,nextPressedImage,mainMenuImage;
	private Image prevImage,prevPressedImage,mainMenuPressedImage;
	private Buttons next,prev,mainMenu;
	
	/**
	 * The constructor of the Tutorial state, this sets up the buttons main menu, next tutorial image and previous tutorial image.
	 * The constructor also loads in the pressed images, and places the buttons where they are suppose to be.
	 */
	public TutorialState() {
		nextImage = new Image(R.drawable.nextbutton);
		next = new Buttons(nextImage,(int) (Constants.getScreenWidth()-nextImage.getWidth()),(int)(Constants.getScreenHeight()/2));
		prevImage = new Image(R.drawable.previousbutton);
		prev = new Buttons(prevImage,(int) (0),(int)(Constants.getScreenHeight()/2));
		mainMenuImage = new Image(R.drawable.mainmenubutton);
		mainMenu = new Buttons(mainMenuImage,(int) (Constants.getScreenWidth()/2-mainMenuImage.getWidth()/2),(int) (Constants.getScreenHeight()-mainMenuImage.getHeight()));
		img = new TutorialImages(0,0);
		nextPressedImage = new Image(R.drawable.pressednextbutton);
		prevPressedImage = new Image(R.drawable.pressedpreviousbutton);
		mainMenuPressedImage = new Image(R.drawable.pressedmainmenubutton);
		
	}
	
	/**
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * @param dt
	 */
	public void update(float dt) {
		img.update(dt);
		next.update(dt);
		prev.update(dt);
		mainMenu.update(dt);
	}
	
	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * @param canvas which you draw on.
	 */
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		img.draw(canvas);
		next.draw(canvas);
		prev.draw(canvas);
		mainMenu.draw(canvas);
	}
	
	/**
	 * When the a button is pressed down or onTouchDown the view of this button is changed into a pressed version of the button.
	 * @param event
	 */
	@Override
	public boolean onTouchDown(MotionEvent event) {
		if(next.getBounds().contains(event.getX(), event.getY())) {
			next.setView(nextPressedImage);
		} else if(prev.getBounds().contains(event.getX(), event.getY())) {
			prev.setView(prevPressedImage);
		}
		else if(mainMenu.getBounds().contains(event.getX(), event.getY())){
			mainMenu.setView(mainMenuPressedImage);
		}
		return false;
	}
	
	/**
	 * When you release the the touch from the screen, either you go into singleplayer mode, multiplayer mode or tutorial mode.
	 * if you drag your finger away from the button you pressed down and release, you just set the buttons as unpressed and don't go into any mode.
	 * @param event
	 */
	@Override
	public boolean onTouchUp(MotionEvent event) {
		if(next.getBounds().contains(event.getX(), event.getY())) {
			if(imgIndex<img.getArraySize()-1) {
				imgIndex++;				
				img.getViewFromArray(imgIndex);				
			}
		} else if(prev.getBounds().contains(event.getX(), event.getY())) {
			if(imgIndex>0) {
				imgIndex--;
				img.getViewFromArray(imgIndex);				
			}
		}
		else if(mainMenu.getBounds().contains(event.getX(), event.getY())){
			getGame().popState();
		}
		next.setView(nextImage);
		prev.setView(prevImage);
		mainMenu.setView(mainMenuImage);
		return false;
	}
}
