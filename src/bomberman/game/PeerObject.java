package bomberman.game;

import java.io.Serializable;

import bomberman.graphics.PowerUpType;

public class PeerObject  implements Serializable{
	
	
	/**
	 * Generated serial value
	 */
	private static final long serialVersionUID = -713360367638714156L;
	private GameObject gObj;
	private ColorObject color;
	private Direction direction;
	private PowerUpType powerType;
	private float xPosition,yPosition;
	
	public PeerObject(ColorObject color, GameObject gObject, float x, float y, Direction direction) {
		this.color = color;
		this.gObj = gObject;
		this.xPosition = x;
		this.yPosition = y;
		this.direction = direction;
	}
	
	public PeerObject(GameObject gameObject, float x, float y, PowerUpType type) {
		this.gObj = gameObject;
		this.xPosition = x;
		this.yPosition = y;
		this.powerType = type;
	}

	public GameObject getgObj() {
		return gObj;
	}

	public ColorObject getColor() {
		return color;
	}

	public float getX() {
		return xPosition;
	}

	public float getY() {
		return yPosition;
	}
	
	public PowerUpType getPowerUpType() {
		return this.powerType;
	}
	
	public Direction getDirection() {
		return this.direction;
	}

}
