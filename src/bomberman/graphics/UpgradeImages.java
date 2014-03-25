package bomberman.graphics;
/**
 * 
 */
import bomberman.game.Constants;
import bomberman.game.R;
import sheep.graphics.Image;

public class UpgradeImages {
	
	public static Image BOMB_COUNT;
	public static Image BIGGER_BOMB;
	public static Image SPEED;
	public static Image THROW_ABILITY;
	public static Image KICK_ABILITY;
	
	/**
	 * Loads the upgradeImages
	 */
	public static void loadImages(){
		if(Constants.screenHeight >= 752){
			BOMB_COUNT = new Image(R.drawable.morebombupgrade);
			BIGGER_BOMB = new Image(R.drawable.biggerbombupgrade);
			SPEED = new Image(R.drawable.speedupgrade);
			THROW_ABILITY = new Image(R.drawable.throwability);
			KICK_ABILITY = new Image(R.drawable.kickability);
		} else{
			BOMB_COUNT = new Image(R.drawable.smallmorebombupgrade);
			BIGGER_BOMB = new Image(R.drawable.smallbiggerbombupgrade);
			SPEED = new Image(R.drawable.smallspeedupgrade);
			THROW_ABILITY = new Image(R.drawable.smallthrowability);
			KICK_ABILITY = new Image(R.drawable.smallkickability);
		}
	}
	
}
