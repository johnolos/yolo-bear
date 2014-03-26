package bomberman.states;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import bomberman.game.Constants;
import bomberman.game.Player;
import bomberman.game.R;
import bomberman.graphics.Buttons;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.input.TouchListener;
/**
 * This class extends State and implements TouchListener
 */
public class GameFinished extends State implements TouchListener{
	private Buttons newRound,backMenu;
	private Image newRoundImage,backMenuImage;
	private float x,y;
	private GameState gs;
	private Sprite resultSprite;
	
	/**
	 * The constructor in GameFinished
	 */
	public GameFinished(ArrayList<Player> allPlayers,Player player, GameState gs){
		this.gs = gs;
		newRoundImage = new Image(R.drawable.newgamebutton);
		x = (float) (Constants.screenWidth/2 - newRoundImage.getWidth()*1.5);
		y = Constants.screenHeight/2 - newRoundImage.getHeight()*4;
		newRound = new Buttons(newRoundImage,(int)x,(int)y);
		
		backMenuImage = new Image(R.drawable.mainmenubutton);
		x = (float) (Constants.screenWidth/2 + backMenuImage.getWidth()*0.5);
		y = Constants.screenHeight/2 - backMenuImage.getHeight()*4;
		backMenu = new Buttons(backMenuImage,(int)x,(int)y);
		
		

		
		resultSprite = new Sprite();
		
		if(isWinner(allPlayers, player)){
			Image win = new Image(R.drawable.winner);
			resultSprite.setView(win);
			resultSprite.setPosition(Constants.getScreenWidth()/2-win.getWidth()/2, Constants.getScreenHeight()/2 - win.getHeight()/2);
		}
		else{
			Image lose = new Image(R.drawable.loser);
			resultSprite.setView(lose);
			resultSprite.setPosition(Constants.getScreenWidth()/2-lose.getWidth()/2, Constants.getScreenHeight()/2 - lose.getHeight()/2);
		}
	}
	
	/**
	 * isWinner is a function which returns a boolean value and checks whether you are the winner or not,
	 * if you are the winner it returns true if not it returns false.
	 * @param allPlayers an ArrayList<Player> with all the players
	 * @param player a instance from the Player class
	 * @return true or false if the player is a winner or not
	 */
	public boolean isWinner(ArrayList<Player> allPlayers,Player player){
		Player winner=player;
		for (Player p : allPlayers) {
			System.out.println(p.getTimeStamp());
			if(p.getTimeStamp()>winner.getTimeStamp()){
				winner = p;
			}
		}
		return winner.equals(player);
	}
	
	/**
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * @param dt
	 */
	public void update(float dt){
		resultSprite.update(dt);
		newRound.update(dt);
		backMenu.update(dt);
	}
	
	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * @param canvas, which you draw on.
	 */
	public void draw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
		resultSprite.draw(canvas);
		newRound.draw(canvas);
		backMenu.draw(canvas);
	}
	
	/**
	 * What happens when you release your finger from the screen, from the different buttons
	 * @param event
	 */
	@Override
	public boolean onTouchUp(MotionEvent event) {
		if (newRound.getBounds().contains(event.getX(), event.getY())) {
			gs.resetGame();
			getGame().popState();
		}
		else if (backMenu.getBounds().contains(event.getX(), event.getY())) {
			if(gs.isMultiplayer()){
				gs.getClient().clientShutdown();
				getGame().popState(3);
			}
			else{
				getGame().popState(4);
			}
		}
		return false;
	}
}
