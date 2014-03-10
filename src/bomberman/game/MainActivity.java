package bomberman.game;

import bomberman.states.MainMenu;
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
		game.pushState(new MainMenu());
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		

        Constants.screenHeight = dm.heightPixels;
        Constants.screenWidth  = dm.widthPixels;
        
        System.out.println(Constants.screenHeight);
        System.out.println(Constants.screenWidth);
        
        Constants.densityX = Constants.screenWidth/Constants.standardXdp;
        Constants.densityY = Constants.screenHeight/Constants.standardYdp;

        
        if(Constants.screenWidth == 2560){
        	Constants.density = 2.0f;
        }
        else if(Constants.screenWidth == 1280){
        	Constants.density = 1.0f;
        }
        else{
        	Constants.density = 0.75f;
        }
		setContentView(game);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
