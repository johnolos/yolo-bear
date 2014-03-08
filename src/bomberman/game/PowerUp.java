package bomberman.game;

import sheep.game.Sprite;
import sheep.graphics.Image;
import android.graphics.Canvas;
import bomberman.graphics.PowerUpType;

public class PowerUp extends Sprite {
	private PowerUpType powerup;
	private Image image;
	
	public PowerUp(int column, int row) {
		float x = (float) (column * Constants.getHeight() + Constants.getPixelsOnSides());
		float y = (float) (row * Constants.getHeight());
		this.setPosition(x, y);
		this.powerup = PowerUpType.randomPowerUp();
		assignPowerUpImage();
		setView(image);
	}
	
	public PowerUp(int column, int row, PowerUpType type) {
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
			this.image = new Image(R.drawable.upgrade);
			break;
		case KICK:
			this.image = new Image(R.drawable.upgrade);
			break;
		case MAGNITUDE:
			this.image = new Image(R.drawable.upgrade);
			break;
		case SPEED:
			this.image = new Image(R.drawable.upgrade);
			break;
		case THROW:
			this.image = new Image(R.drawable.upgrade);
			break;
		}
	}
	
	public PowerUpType getPowerUpType() {
		return this.powerup;
	}


	public void update(float dt){
		super.update(dt);
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
	}

}
