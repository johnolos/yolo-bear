package bomberman.game;
/**
 * Constants
 */
public class Constants {
	public static float screenHeight = 0.0f;
	public static float screenWidth = 0.0f;
	public static float standardHeight = 1600;
	public static float standardWidth = 2560;
	public static double horSize;
	public static double verSize;
	
	public static float COLLSION_RANGE = 8.0f;
	
	/**
	 * Getheight of screen
	 * @return height
	 */
	public static float getHeight(){
		if(screenHeight==1600){
			horSize = screenWidth;
			verSize = screenHeight;
			return 120;
		}
		else if(screenHeight==752) {
			horSize = screenWidth;
			verSize = screenHeight;
			return 60;
		}
		else{
			horSize = screenWidth;
			verSize = screenHeight;
			return 40;
		}
	}
	
	/**
	 * GetScreenWidth
	 * @return screenwidth
	 */
	public static float getScreenWidth() {
		return screenWidth;
	}
	
	/**
	 * GetScreenHeight
	 * @return screenHeight
	 */
	public static float getScreenHeight() {
		return screenHeight;
	}
	
	
	/** WORKING FOR OUR PURPOSE **/
	
	/**
	 * getUniversalXPosition
	 * @param x
	 * @return x position
	 */
	public static float getUniversalXPosition(float x) {
		return x / screenWidth;
	}
	
	/**
	 * getUniversalYPosition
	 * @param y
	 * @return y position
	 */
	public static float getUniversalYPosition(float y) {
		return y / (13*getHeight());
	}
	
	/**
	 * getLocalXPosition
	 * @param x
	 * @return x position
	 */
	public static float getLocalXPosition(float x) {
		return x * screenWidth;
	}
	
	/**
	 * getLocalYPosition
	 * @param y
	 * @return y position
	 */
	public static float getLocalYPosition(float y) {
		return y * 13*getHeight();
	}
		
	/**
	 * Returns 1-indexed for position
	 * @param xPixel
	 * @return
	 */
	public static int getPositionX(float xPixel) {
		float re = xPixel - ((getScreenWidth() / 2.0f) - 6.5f * getHeight());
		re = re / getHeight();
		return (int)re;
	}
	
	/**
	 * Returns 1-indexed value for position y
	 * @param xPixel
	 * @return
	 */
	public static int getPositionY(float yPixel) {
		float re = yPixel / getHeight();
		return (int)re;
	}
	
	/**
	 * Get the number of pixles on the side of screen
	 * @return the pixles on side
	 */
	public static float getPixelsOnSides() {
		return (getScreenWidth() - (13.0f * getHeight())) / 2.0f;
	}
	
	/**
	 * Sending X Ratio
	 * @return ratio
	 */
	public static float getSendingXRatio(){
		return standardWidth/screenWidth;
	}
	
	/**
	 * Sending Y Ratio
	 * @return ratio
	 */
	public static float getSendingYRatio(){
		return standardHeight/screenHeight;
	}
	
	/**
	 * Reciving X Ratio
	 * @return ratio
	 */
	public static float getReceivingXRatio(){
		return screenWidth/standardWidth;
	}
	
	/**
	 * Reciving X Ratio
	 * @return ratio
	 */
	public static float getReceivingYRatio(){
		return screenHeight/standardHeight;
	}

}
