package bomberman.game;

import android.graphics.Canvas;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Opponent extends Sprite {
	
	private Image opp;
	private ColorObject myColor;

	public Opponent(ColorObject color) {
		
		this.myColor = color;

		if (Constants.screenHeight >750) {
			switch (color) {
			case RED:
				this.opp = new Image(R.drawable.playerredlarge);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.4f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case YELLOW:
				this.opp = new Image(R.drawable.playerredlarge);
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case BLUE:
				this.opp = new Image(R.drawable.playerredlarge);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.40f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;
			case GREEN:
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				this.opp = new Image(R.drawable.playerredlarge);
				break;

			default:
				break;
			}
		}
		else{
			switch (color) {
			case RED:
				this.opp = new Image(R.drawable.playerred);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.4f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case YELLOW:
				this.opp = new Image(R.drawable.playerred);
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case BLUE:
				this.opp = new Image(R.drawable.playerred);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.40f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;
			case GREEN:
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				this.opp = new Image(R.drawable.playerred);
				break;

			default:
				break;
			}
		}
		this.setView(opp);
	}

	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	public void update(float dt) {
		super.update(dt);
	}
	
	public ColorObject getColor(){
		return this.myColor;
	}
}
