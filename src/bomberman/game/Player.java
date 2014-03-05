package bomberman.game;
import java.util.ArrayList;

import sheep.game.Sprite;
import sheep.graphics.Image;
import android.graphics.Canvas;
import bomberman.graphics.PowerUp;

public class Player extends Sprite {

	private String nameOfPlayer;
	private int numberOfBombs = 1;
	private boolean kickBombs = false;
	private boolean throwBombs = false;
	private double speedOfPlayer = 1.0;
	private int magnitudeOfBombs = 2;
	private int scoreOfPlayer = 0;
	private Image player;
	private ArrayList<Image> playerImages; // UP, DOWN, RIGHT, LEFT
	private int prevPosX;
	private int prevPosY;
	private int gridPosX;
	private int gridPosY;
	private boolean betweenCol;
	private boolean betweenRow;
	private ColorObject color;
	private Direction direction = Direction.DOWN;
	
	public Player(String name) {
		this.nameOfPlayer = name;
		this.player = new Image(R.drawable.playerredlarge);
		this.setPosition(Constants.screenWidth/2, Constants.screenHeight/2);
	}
	
	public void setColor(ColorObject color) {
		this.color = color;
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

	public void resetRound() {
		this.numberOfBombs = 1;
		this.kickBombs = false;
		this.throwBombs = false;
		this.speedOfPlayer = 1.0;
		this.magnitudeOfBombs = 2;
	}
	
	public void update(float dt){
		super.update(dt);
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
	
	public int getCenterOfTilePosition(){
		return 0;
	}
	
	public boolean canMoveY() {
		if(Constants.getPositionX(this.getPosition().getX()) == Constants.getPositionX(this.getPosition().getX() + this.player.getHeight()))
			return true;
		return false;
	}
	
	public boolean canMoveX() {
		if(Constants.getPositionY(this.getPosition().getY()) == Constants.getPositionY(this.getPosition().getY() + this.player.getHeight()))
			return true;
		return false;
	}
	
	public boolean canMoveUp(){
		
		return false;
		
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
	
	public float getImageHeight(){
		return this.player.getHeight();
	}
	
	/**
	 * @param powerUp Power-up the player archived.
	 */
	public void powerUp(PowerUp powerUp) {
		switch(powerUp) {
		case BOMB:
			this.numberOfBombs++;
			return;
		case SPEED:
			this.speedOfPlayer++;
			return;
		case THROW:
			this.throwBombs = true;
			return;
		case KICK:
			this.kickBombs = true;
			return;
		case MAGNITUDE:
			this.magnitudeOfBombs++;
			return;
		default:
			break;
		}
		return;
	}
	public int getMagnitude(){
		return this.magnitudeOfBombs;
	}
	
	public ColorObject getColor(){
		return this.color;
	}
}
