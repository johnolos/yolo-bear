package bomberman.game;

import sheep.game.Sprite;
import sheep.graphics.Image;
import android.graphics.Canvas;
import bomberman.graphics.PowerUpType;
import bomberman.graphics.UpgradeImages;
import bomberman.states.GameState;

public class PowerUp extends Sprite implements Collision{
	private PowerUpType powerup;
	private Image image;
	int column, row;
	private GameState gs;
	
	public PowerUp(int column, int row, GameState gs) {
		this.gs = gs;
		this.column = column;
		this.row = row;
		float x = (float) (column * Constants.getHeight() + Constants.getPixelsOnSides());
		float y = (float) (row * Constants.getHeight());
		this.setPosition(x, y);
		this.powerup = PowerUpType.randomPowerUp();
		assignPowerUpImage();
		setView(image);
	}
	
	public PowerUp(int column, int row, PowerUpType type,GameState gs) {
		this.gs = gs;
		this.column = column;
		this.row = row;
		float x = (float) (column * Constants.getHeight() + Constants.getPixelsOnSides());
		float y = (float) (row * Constants.getHeight());
		this.setPosition(x, y);
		this.powerup = type;
		assignPowerUpImage();
		setView(image);
		
	}
	
	private void assignPowerUpImage() {
		switch(this.powerup) {
		case BOMB:
			this.image = UpgradeImages.BOMB_COUNT;
			break;
		case KICK:
			this.image = UpgradeImages.KICK_ABILITY;
			break;
		case MAGNITUDE:
			this.image = UpgradeImages.BIGGER_BOMB;
			break;
		case SPEED:
			this.image = UpgradeImages.SPEED;
			break;
		case THROW:
			this.image = UpgradeImages.THROW_ABILITY;
			break;
		}
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public PowerUpType getPowerUpType() {
		return this.powerup;
	}

	/**
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * This function also calls the update function in the super class
	 * @param dt
	 */
	public void update(float dt){
		super.update(dt);
		if(gs.getSpriteBoard().get(row).get(column) instanceof Wall){
			this.setView(null);
		}
		
	}
	
	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * This draw calls the draw function in the super class
	 * @param canvas which you draw on.
	 */
	public void draw(Canvas canvas){
		super.draw(canvas);
	}

	public boolean collision(int x, int y) {
		return (this.column == x && this.row == y);
	}

}
