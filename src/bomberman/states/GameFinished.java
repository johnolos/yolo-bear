package bomberman.states;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import bomberman.game.Constants;
import bomberman.game.Player;
import bomberman.game.R;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

public class GameFinished extends State implements WidgetListener{
	
	private TextButton newGame = new TextButton(100, 100,"Start new game");
	private GameState gs;
	private ArrayList<Player> players;
	private Sprite resultSprite;
	
	public GameFinished(ArrayList<Player> allPlayers,Player player, GameState gs){
		this.gs = gs;
		addTouchListener(newGame);
		
		newGame.addWidgetListener(this);
		
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
	}
	
	
	public void draw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
		resultSprite.draw(canvas);
		newGame.draw(canvas);
	}


	@Override
	public void actionPerformed(WidgetAction action) {
		if(action.getSource() == newGame){
			gs.resetGame();
			getGame().popState();
		}
		
	}

}
