package bomberman.states;

import bomberman.game.Constants;
import bomberman.game.R;
import bomberman.graphics.Buttons;
import bomberman.graphics.MainMenuStartImage;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.input.TouchListener;

public class MainMenuWithGraphics extends State implements TouchListener {
	private Image singlePlayer, multiPlayer, tutorial;
	private Image pressedSinglePlayer, pressedMultiPlayer, pressedTutorial;
	private MainMenuStartImage main;
	private Buttons single, multi, tutorialButton;
	
	public MainMenuWithGraphics() {
		singlePlayer = new Image(R.drawable.singleplayerbutton);
		pressedSinglePlayer = new Image(R.drawable.pressedsingleplayerbutton);
		multiPlayer = new Image(R.drawable.multiplayerbutton);
		pressedMultiPlayer = new Image(R.drawable.pressedmultiplayerbutton);
		tutorial = new Image(R.drawable.tutorialbutton);
		pressedTutorial = new Image(R.drawable.pressedtutorialbutton);
		
		single = new Buttons(singlePlayer, (int) (Constants.screenWidth/2-(singlePlayer.getWidth()/2)), (int) (Constants.screenHeight/2));
		multi = new Buttons(multiPlayer, (int) (Constants.screenWidth/2-(multiPlayer.getWidth()/2)), (int) (Constants.screenHeight/2));
		tutorialButton = new Buttons(tutorial, (int) (Constants.screenWidth/2-(tutorial.getWidth()/2)), (int) (Constants.screenHeight/2));
		
		this.main = new MainMenuStartImage();
	}
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
	
	public void update(float dt) {
		main.update(dt);
		singlePlayer.update(dt);
		multiPlayer.update(dt);
		tutorial.update(dt);
	}
	
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		main.draw(canvas);
		single.draw(canvas);
		multi.draw(canvas);
		tutorialButton.draw(canvas);
	}
}
