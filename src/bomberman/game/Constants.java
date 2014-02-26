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
	
	public static float getRecievingXRatio(){
		return standardWidth/screenWidth;
	}
	
	public static float getRecievingYRatio(){
		return standardHeight/screenHeight;
	}
	public static float getSendingXRatio(){
		return screenWidth/standardWidth;
	}
	
	public static float getSendingYRatio(){
		return screenHeight/standardHeight;
	}

}
