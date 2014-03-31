package bomberman.graphics;

import bomberman.game.Constants;
import bomberman.game.R;
import sheep.graphics.Image;

/**
 * BombImages
 */
public class BombImages {
	
	public static Image bombPhase1;
	public static Image bombPhase2;
	public static Image bombExplosionCenter;
	public static Image bombExplosionMidLeft;
	public static Image bombExplosionMidRight;
	public static Image bombExplosionMidUp;
	public static Image bombExplosionMidDown;
	public static Image bombExplosionEndLeft;
	public static Image bombExplosionEndRight;
	public static Image bombExplosionEndUp;
	public static Image bombExplosionEndDown;
	
	/**
	 * Constructor in BombImages
	 */
	public static void loadImages(){
		if(Constants.screenHeight >= 752){
			bombPhase1 = new Image(R.drawable.bomb);
			bombPhase2 = new Image(R.drawable.bombphase2);
			bombExplosionCenter = new Image(R.drawable.bombcenter);
			bombExplosionMidLeft = new Image(R.drawable.bombmiddleleft);
			bombExplosionMidRight = new Image(R.drawable.bombmiddleright);
			bombExplosionMidUp = new Image(R.drawable.bombmiddleup);
			bombExplosionMidDown = new Image(R.drawable.bombmiddledown);
			bombExplosionEndLeft = new Image(R.drawable.bombendleft);
			bombExplosionEndRight = new Image(R.drawable.bombendright);
			bombExplosionEndUp = new Image(R.drawable.bombendup);
			bombExplosionEndDown = new Image(R.drawable.bombenddown);
		}
		else{
			bombPhase1 = new Image(R.drawable.smallbomb);
			bombPhase2 = new Image(R.drawable.bombphase2);
			bombExplosionCenter = new Image(R.drawable.smallbombcenter);
			bombExplosionMidLeft = new Image(R.drawable.smallbombmiddleleft);
			bombExplosionMidRight = new Image(R.drawable.smallbombmiddleright);
			bombExplosionMidUp = new Image(R.drawable.smallbombmiddleup);
			bombExplosionMidDown = new Image(R.drawable.smallbombmiddledown);
			bombExplosionEndLeft = new Image(R.drawable.smallbombendleft);
			bombExplosionEndRight = new Image(R.drawable.smallbombendright);
			bombExplosionEndUp = new Image(R.drawable.smallbombendup);
			bombExplosionEndDown = new Image(R.drawable.smallbombenddown);
			
		}
	}
}
