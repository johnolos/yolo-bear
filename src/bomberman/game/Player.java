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
	private Image player;
	private int prevPosX;
	private int prevPosY;
	private int gridPosX;
	private int gridPosY;
	private boolean betweenCol;
	private boolean betweenRow;
	private ColorObject color;
	
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
				this.player = new Image(R.drawable.brownbear);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.4f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case BLACK:
				this.player = new Image(R.drawable.blackbear);
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case WHITE:
				this.player = new Image(R.drawable.whitebear);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.40f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;
			case SWAG:
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				this.player = new Image(R.drawable.yolobear);
				break;

			default:
				break;
			}
		}
		else{
			switch (color) {
			case BROWN:
				this.player = new Image(R.drawable.smallbrownbear);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.4f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case BLACK:
				this.player = new Image(R.drawable.smallblackbear);
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case WHITE:
				this.player = new Image(R.drawable.smallwhitebear);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.40f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;
			case SWAG:
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				this.player = new Image(R.drawable.smallswaggybear);
				break;

			default:
				break;
			}
		}
		this.setView(player);
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
