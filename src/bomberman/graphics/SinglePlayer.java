package bomberman.graphics;

import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.BoundingBox;
import android.graphics.Canvas;
import android.graphics.Rect;
import bomberman.game.Constants;
import bomberman.game.R;

public class SinglePlayer extends Sprite {
	private Image singlePlayer;
	private BoundingBox box;
	
	public SinglePlayer(String Id, float x, float y) {
		Rect bounds = null;
		if(Id.equals("Singleplayer")) {
			singlePlayer = new Image(R.drawable.singleplayer);
			this.setShape(100,100);
			this.setPosition(x,y);
			this.setView(singlePlayer);
			float offsetX = 100*Constants.getReceivingXRatio();
			float offsetY = 100*Constants.getReceivingYRatio();
			bounds  = new Rect((int)x,(int)y,(int)(x+singlePlayer.getWidth()),(int)(y+singlePlayer.getHeight()));
		}
		box = new BoundingBox(bounds);
	}
	
	public void update(float dt){
		super.update(dt);
	}
	
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	
	public BoundingBox getBounds() {
		return this.box;
	}
}
