package bomberman.graphics;

import bomberman.game.R;
import android.graphics.Canvas;
import android.graphics.Rect;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.BoundingBox;

public class DirectionKey extends Sprite {
	
	private Image up;
	private Image down;
	private Image right;
	private Image left;
	private BoundingBox box;
	
	
	public DirectionKey(String direction, int x, int y){
		if(direction.equals("up")){
			up = new Image(R.drawable.crate);
			this.setView(up);
			this.setShape(100,100);
			this.setPosition(x,y);
		}
		Rect bounds  = new Rect(x-75, y-75, x+75, y+75);
		this.box = new BoundingBox(bounds);
	}
	
	public void update(float dt){
		super.update(dt);
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
	
	public BoundingBox getBounds(){
		return this.box;
	}

}
