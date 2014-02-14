package bomberman.game;

import bomberman.graphics.PowerUp;

public class Player {

	private String nameOfPlayer;
	private int numberOfBombs = 1;
	private boolean kickBombs = false;
	private boolean throwBombs = false;
	private double speedOfPlayer = 1.0;
	private int magnitudeOfBombs = 2;
	private int scoreOfPlayer = 0;
	
	public Player(String name) {
		this.nameOfPlayer = name;
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
