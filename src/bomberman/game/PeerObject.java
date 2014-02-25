package bomberman.game;

public class PeerObject {
	
	
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
