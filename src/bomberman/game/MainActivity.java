package bomberman.game;


import bomberman.graphics.BombImages;
import bomberman.graphics.UpgradeImages;
import bomberman.states.MainMenuWithGraphics;
import sheep.game.Game;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Game game = new Game(this, null);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
 		Constants.screenHeight = dm.heightPixels;
 		Constants.screenWidth  = dm.widthPixels;
 		
 		BombImages.loadImages();
 		UpgradeImages.loadImages();
        
        System.out.println(Constants.screenHeight);
        System.out.println(Constants.screenWidth);
        game.pushState(new MainMenuWithGraphics());
		setContentView(game);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
