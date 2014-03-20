package bomberman.graphics;

import bomberman.game.Constants;
import bomberman.game.R;
import android.graphics.Canvas;
import android.graphics.Rect;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.BoundingBox;

public class MultiPlayer extends Sprite {
	private Image multiPlayer;
	private BoundingBox box;
	
	public MultiPlayer(String Id, float x, float y) {
		Rect bounds = null;
		if(Id.equals("multi")) {
			multiPlayer = new Image(R.drawable.multiplayer);
			this.setView(multiPlayer);
			this.setShape(100,100);
			this.setPosition(x,y);
			float offsetX = 100*Constants.getReceivingXRatio();
			float offsetY = 100*Constants.getReceivingYRatio();
			bounds  = new Rect((int)(x-offsetX),(int) (y-offsetY), (int)(x+offsetX), (int)(y+offsetY));
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
