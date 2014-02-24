package bomberman.game;

public class Constants {
	
	public static double screenHeight = 0.0;
	public static double screenWidth =0.0;
	public static double horSize;
	public static double verSize;
	public static float getHeight(){
		if(screenHeight==1600){
			horSize = screenWidth;
			verSize = screenHeight;
			return 120;
		}
		else{
			horSize = screenWidth;
			verSize = screenHeight;
			return 40;
		}
	}

}
