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
	private Image brown,black,white,swag;
	private Image one,two,three;
	
	
	
	public Buttons(String buttonID, int x, int y){
		Rect bounds = null;
		if(buttonID.equals("up")){
			up = new Image(R.drawable.up);
			this.setView(up);
			this.setShape(100,100);
			this.setPosition(x,y);
			float offsetX = 100*Constants.getReceivingXRatio();
			float offsetY = 100*Constants.getReceivingYRatio();
			bounds  = new Rect((int)(x-offsetX),(int) (y-offsetY), (int)(x+offsetX), (int)(y+offsetY));
		}
		else if(buttonID.equals("down")){
			down = new Image(R.drawable.down);
			this.setView(down);
			this.setShape(100,100);
			this.setPosition(x, y);
			float offsetX = 100*Constants.getReceivingXRatio();
			float offsetY = 100*Constants.getReceivingYRatio();
			bounds  = new Rect((int)(x-offsetX),(int) (y-offsetY), (int)(x+offsetX), (int)(y+offsetY));
		}
		else if(buttonID.equals("left")){
			left = new Image(R.drawable.left);
			this.setView(left);
			this.setShape(100,100);
			this.setPosition(x, y);
			float offsetX = 100*Constants.getReceivingXRatio();
			float offsetY = 100*Constants.getReceivingYRatio();
			bounds  = new Rect((int)(x-offsetX),(int) (y-offsetY), (int)(x+offsetX), (int)(y+offsetY));
		}
		else if(buttonID.equals("right")){
			right = new Image(R.drawable.right);
			this.setView(right);
			this.setShape(100,100);
			this.setPosition(x, y);
			float offsetX = 100*Constants.getReceivingXRatio();
			float offsetY = 100*Constants.getReceivingYRatio();
			bounds  = new Rect((int)(x-offsetX),(int) (y-offsetY), (int)(x+offsetX), (int)(y+offsetY));
		}
		else if(buttonID.equals("bomb")){
			if(Constants.screenHeight >= 752) {
				bombIcon = new Image(R.drawable.bombicon);
				this.setShape(200,200);
			}
			else {
				bombIcon = new Image(R.drawable.smallbombicon);
				this.setShape(100,100);
			}
			this.setView(bombIcon);
			this.setPosition(x, y);
			float offsetX = 200*Constants.getReceivingXRatio();
			float offsetY = 200*Constants.getReceivingYRatio();
			bounds = new Rect(x,y,(int)(x+offsetX),(int)(y+offsetY));
		}
		else if(buttonID.equals("brown")){
			brown = new Image(R.drawable.brownbeardown);
			this.setScale(5, 5);
			this.setView(brown);
			float posX =  (Constants.getScreenWidth()/2-brown.getWidth()*5);
			float posY =  (Constants.getScreenHeight()/2-brown.getHeight()*5);
			this.setPosition(posX,posY);
			bounds  = new Rect((int)(posX),(int)posY, (int)(posX+brown.getWidth()), (int)(posY +brown.getHeight()));
		}
		else if(buttonID.equals("white")){
			white = new Image(R.drawable.whitebeardown);
			this.setScale(5, 5);
			this.setView(white);
			float posX =  (Constants.getScreenWidth()/2-white.getWidth()*5);
			float posY =  (Constants.getScreenHeight()/2+white.getHeight()*5);
			this.setPosition(posX,posY);
			bounds  = new Rect((int)(posX),(int)posY, (int)(posX+white.getWidth()), (int)(posY +white.getHeight()));
		}
		else if(buttonID.equals("black")){
			black = new Image(R.drawable.blackbeardown);
			this.setScale(5, 5);
			this.setView(black);
			float posX =  (Constants.getScreenWidth()/2+black.getWidth()*4);
			float posY =  (Constants.getScreenHeight()/2-black.getHeight()*4);
			this.setPosition(posX,posY);
			bounds  = new Rect((int)(posX),(int)posY, (int)(posX+black.getWidth()), (int)(posY +black.getHeight()));
		}
		else if(buttonID.equals("swag")){
			swag = new Image(R.drawable.swaggybeardown);
			this.setScale(5, 5);
			this.setView(swag);
			float posX =  (Constants.getScreenWidth()/2+swag.getWidth()*4);
			float posY =  (Constants.getScreenHeight()/2+swag.getHeight()*4);
			this.setPosition(posX,posY);
			bounds  = new Rect((int)(posX),(int)posY, (int)(posX+swag.getWidth()), (int)(posY +swag.getHeight()));
		}
		else if(buttonID.equals("one")){
			one = new Image(R.drawable.swaggybeardown);
			this.setView(one);
			float posX =  (Constants.getScreenWidth()/2-one.getWidth()*4);
			float posY =  (Constants.getScreenHeight()/2-one.getHeight()*4);
			this.setPosition(posX,posY);
			bounds  = new Rect((int)(posX),(int)posY, (int)(posX+one.getWidth()), (int)(posY +one.getHeight()));
		}
		else if(buttonID.equals("two")){
			two = new Image(R.drawable.swaggybeardown);
			this.setView(two);
			float posX =  (Constants.getScreenWidth()/2-two.getWidth()*4);
			float posY =  (Constants.getScreenHeight()/2+two.getHeight()*4);
			this.setPosition(posX,posY);
			bounds  = new Rect((int)(posX),(int)posY, (int)(posX+two.getWidth()), (int)(posY +two.getHeight()));
		}
		else if(buttonID.equals("three")){
			three = new Image(R.drawable.swaggybeardown);
			this.setView(three);
			float posX =  (Constants.getScreenWidth()/2+three.getWidth()*4);
			float posY =  (Constants.getScreenHeight()/2+three.getHeight()*4);
			this.setPosition(posX,posY);
			bounds  = new Rect((int)(posX),(int)posY, (int)(posX+three.getWidth()), (int)(posY +three.getHeight()));
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
