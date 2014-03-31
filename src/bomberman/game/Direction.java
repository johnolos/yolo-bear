package bomberman.game;

import java.io.Serializable;
/**
 * Enum Direction implements serializable
 */
public enum Direction implements Serializable {
	UP(0,-1), DOWN(0,1), LEFT(-1,0), RIGHT(1,0), STOP(0,0);
	
	private int x, y;
	
	/**
	 * Sets direction
	 * @param x x-direction
	 * @param y y-direction
	 */
	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the x value in direction
	 * @return x value
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Get the y value in direction
	 * @return y value
	 */
	public int getY() {
		return this.y;
	}
	
}
