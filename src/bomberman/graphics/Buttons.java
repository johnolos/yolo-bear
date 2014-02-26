package bomberman.graphics;

import bomberman.game.Constants;
import bomberman.game.R;
import android.graphics.Canvas;
import android.graphics.Rect;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.BoundingBox;

public class Buttons extends Sprite {
	
	private Image up;
	private Image down;
	private Image right;
	private Image left;
	private Image bombIcon;
	private BoundingBox box;
	
	
	public Buttons(String buttonID, int x, int y){
		
		Rect bounds = null;
		if(buttonID.equals("up")){
			up = new Image(R.drawable.crate);
			this.setView(up);
			this.setShape(100,100);
			this.setPosition(x,y);
			float offsetX = 75*Constants.getReceivingXRatio();
			float offsetY = 75*Constants.getReceivingYRatio();
			bounds  = new Rect((int)(x-offsetX),(int) (y-offsetY), (int)(x+offsetX), (int)(y+offsetY));
		}
		else if(buttonID.equals("bomb")){
			bombIcon = new Image(R.drawable.bombicon);
			this.setView(bombIcon);
			this.setShape(200,200);
			this.setPosition(x, y);
			float offsetX = 200*Constants.getReceivingXRatio();
			float offsetY = 200*Constants.getReceivingYRatio();
			bounds = new Rect(x,y,(int)(x+offsetX),(int)(y+offsetY));
		}
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
