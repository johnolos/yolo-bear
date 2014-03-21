package bomberman.buttons;

import bomberman.game.R;
import android.graphics.Canvas;
import android.graphics.Rect;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.BoundingBox;

public class MultiPlayer extends Sprite {
	private Image multiPlayer;
	private Image pressedMultiplayer;
	private BoundingBox box;
	
	public MultiPlayer(String Id, float x, float y) {
		Rect bounds = null;
		if(Id.equals("Multiplayer")) {
			multiPlayer = new Image(R.drawable.multiplayerbutton);
			this.setView(multiPlayer);
			float posX = x-(multiPlayer.getWidth()/2);
			float posY =  (float) (y+(multiPlayer.getHeight()*1.25));
			this.setPosition(posX,posY);
			//needs updating
			bounds  = new Rect((int)posX,(int)posY,(int)(posX+multiPlayer.getWidth()),(int)(posY+multiPlayer.getHeight()));
		}
		box = new BoundingBox(bounds);
		pressedMultiplayer = new Image(R.drawable.pressedmultiplayerbutton);
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
	
	public void changeImageShow(int n) {
		if(n==1) {
			this.setView(pressedMultiplayer);
		} else {
			this.setView(multiPlayer);
		}
	}
}
