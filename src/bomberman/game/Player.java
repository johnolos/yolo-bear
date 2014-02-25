package bomberman.game;
import sheep.game.Sprite;
import sheep.graphics.Image;
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
	
	public Player(String name) {
		this.nameOfPlayer = name;

		if(Constants.screenHeight==1600){
			this.playerRed = new Image(R.drawable.playerredlarge);
			this.setPosition(1833.5f, 153.5f);
//			this.prevPosX = 1833;
//			this.prevPosY = 153;
//			this.gridPosX = 11;
//			this.gridPosY = 1;
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
	
	
	/**
	 * @param powerUp Power-up the player archived.
	 */
	private void powerUp(PowerUp powerUp) {
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
}
