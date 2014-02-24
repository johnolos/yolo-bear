package bomberman.states;

import sheep.game.Game;
import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

public class SinglePlayerState extends State {
	
	private TextButton back = new TextButton(600, 600, "Back");
	
	public SinglePlayerState(){
		
		back.addWidgetListener(new WidgetListener() {
			
			@Override
			public void actionPerformed(WidgetAction action) {
				Game.getInstance().popState();
				
			}
		});
		
	}

}
