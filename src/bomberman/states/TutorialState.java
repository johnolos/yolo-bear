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

public class TutorialState extends State implements TouchListener {
	private TutorialImages img;
	private int imgIndex=0;
	private Image nextImage,nextPressedImage,mainMenuImage;
	private Image prevImage,prevPressedImage,mainMenuPressedImage;
	private Buttons next,prev,mainMenu;
	
	/**
	 * The ....
	 */
	public TutorialState() {
		nextImage = new Image(R.drawable.nextbutton);
		next = new Buttons(nextImage,(int) (Constants.getScreenWidth()-nextImage.getWidth()),(int)(Constants.getScreenHeight()/2));
		prevImage = new Image(R.drawable.previousbutton);
		prev = new Buttons(prevImage,(int) (0),(int)(Constants.getScreenHeight()/2));
		mainMenuImage = new Image(R.drawable.mainmenubutton);
		mainMenu = new Buttons(mainMenuImage,(int) (Constants.getScreenWidth()/2),(int) (Constants.getScreenHeight()-mainMenuImage.getHeight()));
		img = new TutorialImages(0,0);
		nextPressedImage = new Image(R.drawable.pressednextbutton);
		prevPressedImage = new Image(R.drawable.pressedpreviousbutton);
		mainMenuPressedImage = new Image(R.drawable.pressedmainmenubutton);
		
	}
	
	public void update(float dt) {
		img.update(dt);
		next.update(dt);
		prev.update(dt);
		mainMenu.update(dt);
	}
	
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		img.draw(canvas);
		next.draw(canvas);
		prev.draw(canvas);
		mainMenu.draw(canvas);
	}
	
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
