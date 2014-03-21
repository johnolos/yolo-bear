package bomberman.states;

import bomberman.game.ColorObject;
import bomberman.game.Constants;
import bomberman.game.R;
import bomberman.graphics.Buttons;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.Button;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.WidgetListener;
import sheep.input.TouchListener;

public class SetBearState extends State implements TouchListener{
	Buttons brown,black,white,swag,textS;
	Image brownImage,blackImage,whiteImage,swagImage,text;
	private float x,y;

	
	public SetBearState(){
		//text
		text = new Image(R.drawable.chooseplayer);
		x = (Constants.getScreenWidth() / 2 - text.getWidth() / 2);
		y = (Constants.getHeight() / 6);
		textS = new Buttons(text,(int) x,(int) y);
		//Brown
		brownImage = new Image(R.drawable.playerbrownbear);
		x =  (float) (Constants.getScreenWidth()/2-brownImage.getWidth()*1.2);
		y =  (Constants.getScreenHeight()/2-brownImage.getHeight());
		brown = new Buttons(brownImage,(int)x, (int)y);

		//Black
		blackImage = new Image(R.drawable.playerblackbear);
		x =  (float) (Constants.getScreenWidth()/2+blackImage.getWidth()*0.2);
		y = (Constants.getScreenHeight()/2-blackImage.getHeight());
		black = new Buttons(blackImage,(int)x, (int)y);
		
		//White
		whiteImage = new Image(R.drawable.playerwhitebear);
		x =  (float) (Constants.getScreenWidth()/2-whiteImage.getWidth()*1.2);
		y =  (Constants.getScreenHeight()/2+whiteImage.getHeight()/4);
		white = new Buttons(whiteImage,(int)x, (int)y);
		
		
		//Swag
		swagImage = new Image(R.drawable.playerswaggybear);
		x = (float) (Constants.getScreenWidth()/2+swagImage.getWidth()*0.2);
		y =  (Constants.getScreenHeight()/2+swagImage.getHeight()/4);
		swag = new Buttons(swagImage,(int)x, (int)y);
		
		
		
		
		
	}

	
	@Override
	public boolean onTouchDown(MotionEvent event) {
		if(brown.getBounds().contains(event.getX(), event.getY())) {
			brown.setView(new Image(R.drawable.pressedplayerbrownbear));
		}
		else if(black.getBounds().contains(event.getX(), event.getY())) {
			black.setView(new Image(R.drawable.pressedplayerblackbear));
		}
		else if(white.getBounds().contains(event.getX(), event.getY())){
			white.setView(new Image(R.drawable.pressedplayerwhitebear));
		}
		else if(swag.getBounds().contains(event.getX(), event.getY())) {
			swag.setView(new Image(R.drawable.pressedplayerswaggybear));
		}
		return false;
	}

	@Override
	public boolean onTouchUp(MotionEvent event) {
		if(brown.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new SetNumberPlayerState(ColorObject.BROWN));
		}
		else if(black.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new SetNumberPlayerState(ColorObject.BLACK));
		}
		else if(white.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new SetNumberPlayerState(ColorObject.WHITE));
		}
		else if(swag.getBounds().contains(event.getX(), event.getY())) {
			getGame().pushState(new SetNumberPlayerState(ColorObject.SWAG));
		}
		brown.setView(new Image(R.drawable.playerbrownbear));
		black.setView(new Image(R.drawable.playerblackbear));
		white.setView(new Image(R.drawable.playerwhitebear));
		swag.setView(new Image(R.drawable.playerswaggybear));
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void update(float dt){
		black.update(dt);
		brown.update(dt);
		white.update(dt);
		swag.update(dt);
		textS.update(dt);
	}
	public void draw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
		black.draw(canvas);
		brown.draw(canvas);
		white.draw(canvas);
		swag.draw(canvas);
		textS.draw(canvas);
	}

}
