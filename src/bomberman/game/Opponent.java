package bomberman.game;

import java.util.ArrayList;
import bomberman.states.GameState;
import android.graphics.Canvas;
import sheep.graphics.Image;

public class Opponent extends Player {
	
	private ArrayList<Image> opponentImages = new ArrayList<Image>(); // UP, DOWN, RIGHT, LEFT
	private ColorObject myColor;
	private Direction direction = Direction.UP;
	private int magnitude=1;

	public Opponent(ColorObject color, GameState gs) {
		super(" ", color,gs);
		this.myColor = color;
		if (Constants.screenHeight >750) {
			switch (color) {
			case BROWN:
				this.opponentImages.add(new Image(R.drawable.brownbearup));
				this.opponentImages.add(new Image(R.drawable.brownbeardown));
				this.opponentImages.add(new Image(R.drawable.brownbearright));
				this.opponentImages.add(new Image(R.drawable.brownbearleft));
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.4f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case BLACK:
				this.opponentImages.add(new Image(R.drawable.blackbearup));
				this.opponentImages.add(new Image(R.drawable.blackbeardown));
				this.opponentImages.add(new Image(R.drawable.blackbearright));
				this.opponentImages.add(new Image(R.drawable.blackbearleft));
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case WHITE:
				this.opponentImages.add(new Image(R.drawable.whitebearup));
				this.opponentImages.add(new Image(R.drawable.whitebeardown));
				this.opponentImages.add(new Image(R.drawable.whitebearright));
				this.opponentImages.add(new Image(R.drawable.whitebearleft));
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.40f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;
			case SWAG:
				this.opponentImages.add(new Image(R.drawable.swaggybearup));
				this.opponentImages.add(new Image(R.drawable.swaggybeardown));
				this.opponentImages.add(new Image(R.drawable.swaggybearright));
				this.opponentImages.add(new Image(R.drawable.swaggybearleft));
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;

			default:
				break;
			}
		}
		else{
			switch (color) {
			case BROWN:
				this.opponentImages.add(new Image(R.drawable.smallbrownbearup));
				this.opponentImages.add(new Image(R.drawable.smallbrownbeardown));
				this.opponentImages.add(new Image(R.drawable.smallbrownbearright));
				this.opponentImages.add(new Image(R.drawable.smallbrownbearleft));
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.4f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case BLACK:
				this.opponentImages.add(new Image(R.drawable.smallblackbearup));
				this.opponentImages.add(new Image(R.drawable.smallblackbeardown));
				this.opponentImages.add(new Image(R.drawable.smallblackbearright));
				this.opponentImages.add(new Image(R.drawable.smallblackbearleft));
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case WHITE:
				this.opponentImages.add(new Image(R.drawable.smallwhitebearup));
				this.opponentImages.add(new Image(R.drawable.smallwhitebeardown));
				this.opponentImages.add(new Image(R.drawable.smallwhitebearright));
				this.opponentImages.add(new Image(R.drawable.smallwhitebearleft));
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.40f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;
			case SWAG:
				this.opponentImages.add(new Image(R.drawable.smallswaggybearup));
				this.opponentImages.add(new Image(R.drawable.smallswaggybeardown));
				this.opponentImages.add(new Image(R.drawable.smallswaggybearright));
				this.opponentImages.add(new Image(R.drawable.smallswaggybearleft));
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;

			default:
				break;
			}
		}
		this.setView(opponentImages.get(0));
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
		switch(direction) {
		case UP:
			this.setView(this.opponentImages.get(0));
			break;
		case DOWN:
			this.setView(this.opponentImages.get(1));
			break;
		case RIGHT:
			this.setView(this.opponentImages.get(2));
			break;
		case LEFT:
			this.setView(this.opponentImages.get(3));
			break;
		default:
			break;
		}
	}
	
	public void updateMagnitude(){
		this.magnitude ++;
	}
	
	public int getMagnitude(){
		return this.magnitude;
	}

	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * This draw calls the draw function in the super class
	 * @param canvas which you draw on.
	 */
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	/**
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * This function also calls the update function in the super class
	 * @param dt
	 */
	public void update(float dt) {
		super.update(dt);
	}
	
	public ColorObject getColor(){
		return this.myColor;
	}

}
