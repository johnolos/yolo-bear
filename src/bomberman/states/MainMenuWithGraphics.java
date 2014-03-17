package bomberman.states;

import android.graphics.Canvas;
import android.graphics.Rect;
import bomberman.game.Constants;
import bomberman.game.R;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import sheep.math.BoundingBox;

public class MainMenuWithGraphics extends Sprite {
	private Image singlePlayer; 
	private Image multiPlayer;
	private BoundingBox box;
	
	/**
	 * The constructor 
	 */
	public MainMenuWithGraphics(String buttonID, int x, int y) {
		Rect bounds = null;
		if(buttonID.equals("Single")) {
			singlePlayer = new Image(R.drawable.singleplayer);
			this.setView(singlePlayer);
			this.setShape(100, 100); //DENNE Må ENDRES
			this.setPosition(x, y);
			float offsetX = 100*Constants.getReceivingXRatio();
			float offsetY = 100*Constants.getReceivingYRatio();
			bounds = new Rect((int)(x-offsetX),(int)(y-offsetY),(int)(x+offsetX),(int)(y+offsetY));
		} else if(buttonID.equals("Multi")) {
			multiPlayer = new Image(R.drawable.multiplayer);
			this.setShape(100, 100); //DENNE Må ENDRES
			this.setPosition(x, y);
			float offsetX = 100*Constants.getReceivingXRatio();
			float offsetY = 100*Constants.getReceivingYRatio();
			bounds = new Rect((int)(x-offsetX),(int)(y-offsetY),(int)(x+offsetX),(int)(y+offsetY));
		}
		this.box = new BoundingBox(bounds);
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
}
