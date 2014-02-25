package bomberman.game;

import java.io.Serializable;

public class PeerObject  implements Serializable{
	
	
	private GameObject gObj;
	private ColorObject color;
	private double xPosition,yPosition;
	
	public PeerObject(ColorObject color, GameObject gObject, double x, double y) {
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

	public double getxPosition() {
		return xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

}
