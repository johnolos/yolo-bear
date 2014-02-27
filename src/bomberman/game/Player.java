package bomberman.game;
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
	private Image playerRed;
	private int prevPosX;
	private int prevPosY;
	private int gridPosX;
	private int gridPosY;
	private boolean betweenCol;
	private boolean betweenRow;
	
	public Player(String name) {
		this.nameOfPlayer = name;

		if(Constants.screenHeight>750){
			this.playerRed = new Image(R.drawable.playerredlarge);
			this.setPosition(1833.5f*Constants.getReceivingXRatio(), 153.5f*Constants.getReceivingYRatio());
			this.prevPosX = 1833;
			this.prevPosY = 153;
			this.gridPosX = 11;
			this.gridPosY = 1;
			this.setShape(93,93);
		}
		else{
			this.playerRed = new Image(R.drawable.playerred);
			this.setPosition(100, 100);
			this.setShape(40,40);
		}
		this.setView(playerRed);
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
//		System.out.println("X: " + Constants.getPositionX(this.getPosition().getX() + this.playerRed.getHeight()/2)
//				+ " Y: " + Constants.getPositionY(this.getPosition().getY() + this.playerRed.getHeight()/2));
//		System.out.println("TX: " + Constants.getPositionX(this.getPosition().getX())
//				+ " TY: " + Constants.getPositionY(this.getPosition().getY()));
//		System.out.println("BX: " + Constants.getPositionX(this.getPosition().getX() + this.playerRed.getHeight())
//				+ " BY: " + Constants.getPositionY(this.getPosition().getY() + this.playerRed.getHeight()));
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
	
	public int getCenterOfTilePosition(){
		return 0;
	}
	
	public boolean canMoveY() {
		if(Constants.getPositionX(this.getPosition().getX()) == Constants.getPositionX(this.getPosition().getX() + this.playerRed.getHeight()))
			return true;
		return false;
	}
	
	public boolean canMoveX() {
		if(Constants.getPositionY(this.getPosition().getY()) == Constants.getPositionY(this.getPosition().getY() + this.playerRed.getHeight()))
			return true;
		return false;
	}
	
	public boolean canMoveUp(){
		
		return false;
		
	}
	
	public float getImageHeight(){
		return this.playerRed.getHeight();
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
	// TODO: Is this used?
	public void checkPosition(){
		if(Math.abs(prevPosX - Math.round(this.getPosition().getX()))>13.5 && Math.abs(prevPosX - Math.round(this.getPosition().getX()))<116.5){
			this.betweenCol = true;
		}
		if(Math.abs(prevPosY - Math.round(this.getPosition().getY()))>13.5 && Math.abs(prevPosY - Math.round(this.getPosition().getY()))<116.5){
			this.betweenRow = true;
		}
		if(prevPosX-Math.round(this.getPosition().getX()) >116.5){
			this.gridPosX -= 1;
			System.out.println(this.gridPosX);
			this.setPosition(prevPosX-120, prevPosY);
			this.prevPosX = prevPosX-120;
			this.betweenCol = false;
		}
		else if(prevPosX-Math.round(this.getPosition().getX())<-116.5){
			this.gridPosX += 1;
			System.out.println(this.gridPosX);
			this.setPosition(prevPosX+120, prevPosY);
			this.prevPosX = prevPosX+120;
			this.betweenCol = false;
		}
		if(prevPosY-Math.round(this.getPosition().getY()) >116.5){
			this.gridPosY -= 1;
			System.out.println(this.gridPosY);
			this.setPosition(prevPosX, prevPosY-120);
			this.prevPosY = prevPosY-120;
			this.betweenRow = false;
		}
		else if(prevPosY-Math.round(this.getPosition().getY())<-116.5){
			this.gridPosY += 1;
			System.out.println(this.gridPosY);
			this.setPosition(prevPosX, prevPosY+120);
			this.prevPosY = prevPosY+120;
			this.betweenRow = false;
		}
	}
	
	public int getMagnitude(){
		return this.magnitudeOfBombs;
	}
}
