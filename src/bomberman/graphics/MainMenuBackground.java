package bomberman.graphics;

import android.graphics.Canvas;
import bomberman.game.Constants;
import bomberman.game.R;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class MainMenuBackground extends Sprite {
	private Image startImage;
	
	public MainMenuBackground() {
		startImage = new Image(R.drawable.mainscreen);
		this.setView(startImage);
		this.setPosition((Constants.getScreenWidth()/2)-(startImage.getWidth()/2), (Constants.getScreenHeight()/2)-(startImage.getHeight()/2));
	}
	
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	
	public void update(float dt) {
		super.update(dt);
	}

}
