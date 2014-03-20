package bomberman.graphics;

import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.BoundingBox;
import android.graphics.Canvas;
import android.graphics.Rect;
import bomberman.game.R;

public class SinglePlayer extends Sprite {
	private Image singlePlayer;
	private BoundingBox box;
	
	public SinglePlayer(String Id, float x, float y) {
		Rect bounds = null;
		if(Id.equals("Singleplayer")) {
			this.singlePlayer = new Image(R.drawable.singleplayer);
			this.setPosition(x-(singlePlayer.getWidth()/2),y-(singlePlayer.getHeight()));
			this.setView(singlePlayer);
			bounds  = new Rect((int)(x-singlePlayer.getWidth()),(int)(y-singlePlayer.getWidth()),(int)(x+singlePlayer.getWidth()),(int)(y+singlePlayer.getHeight()));
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
