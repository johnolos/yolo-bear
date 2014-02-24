package bomberman.states;

import bomberman.connection.Client;
import android.graphics.Canvas;
import sheep.game.State;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

public class MainMenu extends State {
	
	Client client;
	
	private TextButton singlePlayer = new TextButton(100, 100,"Single Player");
	private TextButton connect = new TextButton(600, 600, "Connect");
	
	public MainMenu(){
		
		connect.addWidgetListener(new WidgetListener() {
			
			@Override
			public void actionPerformed(WidgetAction action) {
				Thread clientThread = new Thread(new Client());
				clientThread.start();
				System.out.println("Click");
			}
		});
	}
	
	public void update(float dt){
		
	}
	public void draw(Canvas canvas){
		singlePlayer.draw(canvas);
		connect.draw(canvas);
	}
}
