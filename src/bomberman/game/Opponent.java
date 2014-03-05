package bomberman.game;

import java.util.ArrayList;

import android.graphics.Canvas;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class Opponent extends Sprite {
	
	private ArrayList<Image> playerImages = new ArrayList<Image>(); // UP, DOWN, RIGHT, LEFT
	private ColorObject myColor;
	private Direction direction = Direction.UP;
	private int magnitude=2;

	public Opponent(ColorObject color) {
		
		this.myColor = color;

		if (Constants.screenHeight >750) {
			switch (color) {
			case BROWN:
				this.playerImages.add(new Image(R.drawable.brownbearup));
				this.playerImages.add(new Image(R.drawable.brownbeardown));
				this.playerImages.add(new Image(R.drawable.brownbearright));
				this.playerImages.add(new Image(R.drawable.brownbearleft));
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.4f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case BLACK:
				this.playerImages.add(new Image(R.drawable.blackbearup));
				this.playerImages.add(new Image(R.drawable.blackbeardown));
				this.playerImages.add(new Image(R.drawable.blackbearright));
				this.playerImages.add(new Image(R.drawable.blackbearleft));
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case WHITE:
				this.playerImages.add(new Image(R.drawable.whitebearup));
				this.playerImages.add(new Image(R.drawable.whitebeardown));
				this.playerImages.add(new Image(R.drawable.whitebearright));
				this.playerImages.add(new Image(R.drawable.whitebearleft));
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.40f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;
			case SWAG:
				this.playerImages.add(new Image(R.drawable.swaggybearup));
				this.playerImages.add(new Image(R.drawable.swaggybeardown));
				this.playerImages.add(new Image(R.drawable.swaggybearright));
				this.playerImages.add(new Image(R.drawable.swaggybearleft));
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;

			default:
				break;
			}
		}
		else{
			switch (color) {
			case BROWN:
				this.playerImages.add(new Image(R.drawable.smallbrownbearup));
				this.playerImages.add(new Image(R.drawable.smallbrownbeardown));
				this.playerImages.add(new Image(R.drawable.smallbrownbearright));
				this.playerImages.add(new Image(R.drawable.smallbrownbearleft));
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.4f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case BLACK:
				this.playerImages.add(new Image(R.drawable.smallblackbearup));
				this.playerImages.add(new Image(R.drawable.smallblackbeardown));
				this.playerImages.add(new Image(R.drawable.smallblackbearright));
				this.playerImages.add(new Image(R.drawable.smallblackbearleft));
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case WHITE:
				this.playerImages.add(new Image(R.drawable.smallwhitebearup));
				this.playerImages.add(new Image(R.drawable.smallwhitebeardown));
				this.playerImages.add(new Image(R.drawable.smallwhitebearright));
				this.playerImages.add(new Image(R.drawable.smallwhitebearleft));
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.40f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;
			case SWAG:
				this.playerImages.add(new Image(R.drawable.smallswaggybearup));
				this.playerImages.add(new Image(R.drawable.smallswaggybeardown));
				this.playerImages.add(new Image(R.drawable.smallswaggybearright));
				this.playerImages.add(new Image(R.drawable.smallswaggybearleft));
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;

			default:
				break;
			}
		}
		this.setView(playerImages.get(0));
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
		switch(direction) {
		case UP:
			this.setView(this.playerImages.get(0));
			break;
		case DOWN:
			this.setView(this.playerImages.get(1));
			break;
		case RIGHT:
			this.setView(this.playerImages.get(2));
			break;
		case LEFT:
			this.setView(this.playerImages.get(3));
			break;
		}
	}
	
	public void updateMagnitude(){
		this.magnitude ++;
	}
	
	public int getMagnitude(){
		return this.magnitude;
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
