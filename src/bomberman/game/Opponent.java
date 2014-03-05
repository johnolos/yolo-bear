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
			case BROWN:
				this.opp = new Image(R.drawable.brownbear);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.4f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case BLACK:
				this.opp = new Image(R.drawable.blackbear);
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case WHITE:
				this.opp = new Image(R.drawable.whitebear);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.40f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;
			case SWAG:
				this.opp = new Image(R.drawable.yolobear);
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;

			default:
				break;
			}
		}
		else{
			switch (color) {
			case BROWN:
				this.opp = new Image(R.drawable.smallbrownbear);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.4f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case BLACK:
				this.opp = new Image(R.drawable.smallblackbear);
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case WHITE:
				this.opp = new Image(R.drawable.smallwhitebear);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.40f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;
			case SWAG:
				this.opp = new Image(R.drawable.smallswaggybear);
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
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
