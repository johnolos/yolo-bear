package bomberman.states;
/**
 * This class extends State and implements TouchListner
 */

import bomberman.game.Constants;
import bomberman.game.R;
import bomberman.graphics.Buttons;
import bomberman.graphics.MainMenuBackground;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.input.TouchListener;

public class MainMenuGraphics extends State implements TouchListener {
	private Image singlePlayer, multiPlayer, tutorial;
	private Image pressedSinglePlayer, pressedMultiPlayer, pressedTutorial;
	private MainMenuBackground main;
	private Buttons single, multi, tutorialButton;
	
	/**
	 * The Constructor in the class MainMenuWithGraphics
	 * This class uses the pictures, to represent the different buttons you can push.
	 * The class loads in both the unpressed and pressed buttons in both singleplayer, multiplayer and tutorial. 
	 */
	public MainMenuGraphics() {
		singlePlayer = new Image(R.drawable.singleplayerbutton);
		pressedSinglePlayer = new Image(R.drawable.pressedsingleplayerbutton);
		multiPlayer = new Image(R.drawable.multiplayerbutton);
		pressedMultiPlayer = new Image(R.drawable.pressedmultiplayerbutton);
		tutorial = new Image(R.drawable.tutorialbutton);
		pressedTutorial = new Image(R.drawable.pressedtutorialbutton);
		
		single = new Buttons(singlePlayer, (int) (Constants.screenWidth/2-(singlePlayer.getWidth()/2)), (int) (Constants.screenHeight/2) );
		multi = new Buttons(multiPlayer, (int) (Constants.screenWidth/2-(multiPlayer.getWidth()/2)), (int) (Constants.screenHeight/2+(multiPlayer.getHeight()*1.25)));
		tutorialButton = new Buttons(tutorial, (int) (Constants.screenWidth/2-(tutorial.getWidth()/2)), (int) (Constants.screenHeight/2+tutorial.getHeight()*2.5));
		this.main = new MainMenuBackground();
	}
	
	/**
	 * When the a button is pressed down or onTouchDown the view of this button is changed into a pressed version of the button.
	 * @param MotionEvent event
	 */
	@Override
	public boolean onTouchDown(MotionEvent event) {
		if(single.getBounds().contains(event.getX(), event.getY())) {
			single.setView(pressedSinglePlayer);
		} else if(multi.getBounds().contains(event.getX(), event.getY())) {
			multi.setView(pressedMultiPlayer);
		} else if(tutorialButton.getBounds().contains(event.getX(), event.getY())) {
			tutorialButton.setView(pressedTutorial);
		}
		
		return false;
	}
	
	/**
	 * When you release the the touch from the screen, either you go into singleplayer mode, multiplayer mode or tutorial mode.
	 * if you drag your finger away from the button you pressed down and release, you just set the buttons as unpressed and don't go into any mode.
	 * @param MotionEvent event
	 */
	@Override
	public boolean onTouchUp(MotionEvent event) {
		if(single.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new SetBearState());
		} else if(multi.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new LoadingMultiplayer());
		} else if(tutorialButton.getBounds().contains(event.getX(), event.getY())) {
			TutorialState tutorial = new TutorialState();
			getGame().pushState(tutorial);
		}
		single.setView(singlePlayer);
		multi.setView(multiPlayer);
		tutorialButton.setView(tutorial);
		return false;
	}
	
	/**
	 * This updates the view, if there is any changes in the view of the different buttons this updates function it.
	 * @param float dt
	 */
	public void update(float dt) {
		main.update(dt);
		single.update(dt);
		multi.update(dt);
		tutorialButton.update(dt);
	}
	
	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * @param Canvas canvas which you draw on.
	 */
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		main.draw(canvas);
		single.draw(canvas);
		multi.draw(canvas);
		tutorialButton.draw(canvas);
	}
}
