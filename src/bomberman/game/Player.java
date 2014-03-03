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
	
	public Player(String name,ColorObject color) {
		this.nameOfPlayer = name;
		if (Constants.screenHeight >750) {
			switch (color) {
			case RED:
				this.player = new Image(R.drawable.playerredlarge);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.4f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case YELLOW:
				this.player = new Image(R.drawable.playerredlarge);
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case BLUE:
				this.player = new Image(R.drawable.playerredlarge);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.40f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;
			case GREEN:
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				this.player = new Image(R.drawable.playerredlarge);
				break;

			default:
				break;
			}
		}
		else{
			switch (color) {
			case RED:
				this.player = new Image(R.drawable.playerred);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.4f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case YELLOW:
				this.player = new Image(R.drawable.playerred);
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 -Constants.getHeight()*5.40f);
				break;
			case BLUE:
				this.player = new Image(R.drawable.playerred);
				this.setPosition((float)Constants.screenWidth/2-Constants.getHeight()*5.40f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				break;
			case GREEN:
				this.setPosition((float)Constants.screenWidth/2+Constants.getHeight()*4.60f,(float) Constants.screenHeight/2 +Constants.getHeight()*4.60f);
				this.player = new Image(R.drawable.playerred);
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
}
