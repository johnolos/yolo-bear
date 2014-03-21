package bomberman.graphics;

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
		if(Id.equals("Multiplayer")) {
			multiPlayer = new Image(R.drawable.multiplayer);
			this.setView(multiPlayer);
			this.setPosition(x-(multiPlayer.getWidth()/2),y-(multiPlayer.getHeight()));
			bounds  = new Rect((int)(x-multiPlayer.getWidth()),(int)(y-multiPlayer.getWidth()),(int)(x+multiPlayer.getWidth()),(int)(y+multiPlayer.getHeight()));
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
