package bomberman.game;

public class Constants {
	
	public static float screenHeight = 0.0f;
	public static float screenWidth = 0.0f;
	public static float standardHeight = 1600;
	public static float standardWidth = 2560;
	public static double horSize;
	public static double verSize;
	
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
	
	public static float getSendingXRatio(){
		return standardWidth/screenWidth;
	}
	
	public static float getSendingYRatio(){
		return standardHeight/screenHeight;
	}
	public static float getReceivingXRatio(){
		return screenWidth/standardWidth;
	}
	
	public static float getReceivingYRatio(){
		return screenHeight/standardHeight;
	}
	
	public static float getScreenWidth() {
		return screenWidth;
	}
	
	public static float getScreenHeight() {
		return screenHeight;
	}
	
	
	/** TESTING PURPOSE **/
	
	public static float getUniversalXPosition(float x) {
		return x / screenWidth;
	}
	
	public static float getUniversalYPosition(float y) {
		return y / screenHeight;
	}
	
	public static float getLocalXPosition(float x) {
		return x * screenWidth;
	}
	
	public static float getLocalYPosition(float y) {
		return y * screenHeight;
	}
	
	/*******/
	
	public static int getPositionX(float xPixel) {
		float re = xPixel - ((getScreenWidth() / 2.0f) - 6.5f * getHeight());
		re = re / getHeight();
		return (int)re;
	}
	
	public static int getPositionY(float xPixel) {
		float re = xPixel - ((getScreenHeight() / 2.0f) - 6.5f * getHeight());
		re = re / getHeight();
		return (int)re;
	}
	
	public static float getPixelsOnSides() {
		return (getScreenWidth() - (13.0f * getHeight())) / 2.0f;
	}

}
