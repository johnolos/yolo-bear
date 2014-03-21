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
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import sheep.input.TouchListener;

public class GameFinished extends State implements TouchListener{
	
	private Buttons newRound,backMenu;
	private Image newRoundImage,backMenuImage;
	private float x,y;
	private GameState gs;
	private ArrayList<Player> players;
	private Sprite resultSprite;
	
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
	
	public boolean isWinner(ArrayList<Player> allPlayers,Player player){
		Player winner=allPlayers.get(0);
		for (Player p : allPlayers) {
			if(p.getTimeStamp()>winner.getTimeStamp()){
				winner = p;
			}
		}
		return winner.equals(player);
	}
	
	public void update(float dt){
		resultSprite.update(dt);
		newRound.update(dt);
		backMenu.update(dt);
	}
	
	
	public void draw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
		resultSprite.draw(canvas);
		newRound.draw(canvas);
		backMenu.draw(canvas);
	}
	@Override
	public boolean onTouchUp(MotionEvent event) {
		if (newRound.getBounds().contains(event.getX(), event.getY())) {
			gs.resetGame();
			getGame().popState();
		}
		else if (backMenu.getBounds().contains(event.getX(), event.getY())) {
			getGame().popState(4);
		}
		return false;
	}


//	@Override
//	public void actionPerformed(WidgetAction action) {
//		if(action.getSource() == newRound){
//			gs.resetGame();
//			getGame().popState(4);
//		}
//		
//	}

}
