package bomberman.states;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import bomberman.game.Player;
import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

public class GameFinished extends State implements WidgetListener{
	
	private TextButton newGame = new TextButton(100, 100,"Start new game");
	private GameState gs;
	
	
	public GameFinished(ArrayList<Player> allPlayers, GameState gs){
		this.gs = gs;
		addTouchListener(newGame);
		
		newGame.addWidgetListener(this);
		System.out.println(allPlayers);
	}
	
	
	public void update(float dt){
	}
	
	
	public void draw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
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
