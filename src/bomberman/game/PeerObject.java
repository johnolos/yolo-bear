package bomberman.game;

import java.io.Serializable;

public class PeerObject  implements Serializable{
	
	
	private GameObject gObj;
	private ColorObject color;
	private float xPosition,yPosition;
	
	public PeerObject(ColorObject color, GameObject gObject, float x, float y) {
		this.color = color;
		this.gObj = gObject;
		this.xPosition = x;
		this.yPosition = y;
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

}
