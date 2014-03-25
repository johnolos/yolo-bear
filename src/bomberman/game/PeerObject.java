package bomberman.game;

import java.io.Serializable;

import bomberman.graphics.PowerUpType;

/**
 * PeerObject implements Serializable
 */
public class PeerObject implements Serializable{
	
	
	/**
	 * Generated serial value
	 */
	private static final long serialVersionUID = -713360367638714156L;
	private GameObject gObj;
	private ColorObject color;
	private Direction direction;
	private PowerUpType powerType;
	private float xPosition,yPosition;
	private int magnitude;
	private long timestamp;
	
	/**
	 * Constructor
	 * @param gameObject
	 * @param x
	 * @param y
	 * @param direction
	 */
	public PeerObject(GameObject gameObject, float x, float y, Direction direction) {
		this.gObj = gameObject;
		this.xPosition = x;
		this.yPosition = y;
		this.direction = direction;
	}
	
	/**
	 * Constructor
	 * @param color
	 * @param gObject
	 * @param x
	 * @param y
	 * @param direction
	 */
	public PeerObject(ColorObject color, GameObject gObject, float x, float y, Direction direction) {
		this.color = color;
		this.gObj = gObject;
		this.xPosition = x;
		this.yPosition = y;
		this.direction = direction;
	}
	
	/**
	 * Constructor
	 * @param gameObject
	 * @param x
	 * @param y
	 * @param type
	 */
	public PeerObject(GameObject gameObject, float x, float y, PowerUpType type) {
		this.gObj = gameObject;
		this.xPosition = x;
		this.yPosition = y;
		this.powerType = type;
	}

	/**
	 * Constructor
	 * @param color2
	 * @param bomb
	 * @param x
	 * @param y
	 * @param direction
	 * @param magnitude
	 */
	public PeerObject(ColorObject color2, GameObject bomb, int x, int y, Direction direction, int magnitude) {
		this.color = color2;
		this.gObj = bomb;
		this.xPosition = x;
		this.yPosition = y;
		this.direction = direction;
		this.magnitude =magnitude;
	}
	
	/**
	 * Constructor
	 * Died
	 * @param color
	 * @param died
	 * @param dt
	 */
	public PeerObject(ColorObject color, GameObject died, long dt) {
		this.color = color;
		this.gObj = died;
		this.timestamp = dt;
	}

	/**
	 * GameObject
	 * @return gameobject
	 */
	public GameObject getgObj() {
		return gObj;
	}

	/**
	 * getColor
	 * @return color on game object
	 */
	public ColorObject getColor() {
		return color;
	}

	/**
	 * getX
	 * @return xPosition the x-cooridinate
	 */
	public float getX() {
		return xPosition;
	}

	/**
	 * getY
	 * @return yPosition the y-coordinate
	 */
	public float getY() {
		return yPosition;
	}
	
	/**
	 * getPowerUPType
	 * @return powerType which powerUp it is
	 */
	public PowerUpType getPowerUpType() {
		return this.powerType;
	}
	
	/**
	 * getDirection
	 * @return direction
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * getMagnitude
	 * @return magnitude
	 */
	public int getMagnitude() {
		return magnitude;
	}

	/**
	 * getTimeStamp
	 * @return timestamp
	 */
	public long getTimeStamp() {
		return timestamp;
	}

}
