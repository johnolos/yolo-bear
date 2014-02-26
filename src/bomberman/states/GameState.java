package bomberman.states;

import java.util.ArrayList;







import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import bomberman.connection.Client;
import bomberman.game.Board;
import bomberman.game.Constants;
import bomberman.game.Crate;
import bomberman.game.Empty;
import bomberman.game.GameObject;
import bomberman.game.Opponent;
import bomberman.game.PeerObject;
import bomberman.game.Player;
import bomberman.game.ColorObject;
import bomberman.game.Wall;
import bomberman.graphics.DirectionKey;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.input.TouchListener;

public class GameState extends State{
	
	private Player player;
	private Board  board;
	private DirectionKey up, down, left, right;
	private ArrayList<ArrayList<Sprite>> spriteList = new ArrayList<ArrayList<Sprite>>();
	private double startingX;
	private double startingY;
	private TouchListener touch;
	private ArrayList<Opponent> opponents;
	private Client client;
	
	public GameState (Client client){
		this.client = client;
		this.player = new Player("Player1");
		this.board = new Board();
		//Finding the upper-left coordinates of the game-view
		this.startingX = Constants.screenWidth/2 - Constants.getHeight()*6.5;
		this.startingY = Constants.screenHeight/2-Constants.getHeight()*6.5;
		this.up = new DirectionKey("up",2300, 500);
		this.down = new DirectionKey("up", 2300, 700);
		this.right = new DirectionKey("up", 2500, 600);
		this.left = new DirectionKey("up", 2100, 600);
		addSprites();
		addOpponent();
		
		touch = new TouchListener() {
			
			@Override
			public boolean onTouchUp(MotionEvent event) {
				player.setSpeed(0,0);
				return false;
			}
			
			@Override
			public boolean onTouchMove(MotionEvent event) {
				
				return false;
			}
			
			@Override
			public boolean onTouchDown(MotionEvent event) {
				if(up.getBounds().contains(event.getX(), event.getY())){
					player.setSpeed(0, -150);
				}
				else if(down.getBounds().contains(event.getX(), event.getY())){
					player.setSpeed(0, 150);
				}
				else if(left.getBounds().contains(event.getX(), event.getY())){
					player.setSpeed(-150, 0);
				}
				else if(right.getBounds().contains(event.getX(), event.getY())){
					player.setSpeed(150, 0);
				}
				
				
				
				return false;
			}
		};
		
		addTouchListener(touch);
		
	}
	public void addOpponent(){
		opponents = new ArrayList<Opponent>();
		opponents.add(new Opponent(ColorObject.BLUE));
		opponents.add(new Opponent(ColorObject.GREEN));
		opponents.add(new Opponent(ColorObject.YELLOW));
	}
	
	
	
	public void addSprites(){
		spriteList = new ArrayList<ArrayList<Sprite>>();
		for(int i =0; i<board.getBoard().length;i++){
			ArrayList<Sprite> row = new ArrayList<Sprite>();
			for(int j=0; j<board.getBoard()[1].length;j++){
				if(board.getBoard()[i][j]==1){
					Wall boardPiece = new Wall();
					boardPiece.setPosition((float) (this.startingX+Constants.getHeight()*j), (float) (this.startingY+Constants.getHeight()*i));
					row.add(boardPiece);
				}
				if(board.getBoard()[i][j]==0){
					Empty boardPiece = new Empty();
					boardPiece.setPosition((float) (this.startingX+Constants.getHeight()*j), (float) (this.startingY+Constants.getHeight()*i));
					row.add(boardPiece);
				}
				if(board.getBoard()[i][j]==2){
					Crate boardPiece = new Crate();
					boardPiece.setPosition((float) (this.startingX+Constants.getHeight()*j), (float) (this.startingY+Constants.getHeight()*i));
					row.add(boardPiece);
				}	
			}
			spriteList.add(row);
		}
	}
	
	public void update(float dt){
		client.sendAll(new PeerObject(ColorObject.BLUE,GameObject.PLAYER,this.player.getX(),this.player.getY()));
		
		up.update(dt);
		down.update(dt);
		left.update(dt);
		right.update(dt);
		player.update(dt);
		for (Opponent opp : this.opponents) {
			opp.update(dt);
		}
		
		for(ArrayList<Sprite> row : spriteList){
			for(Sprite sprite : row){
				sprite.update(dt);
			}
		}
	}
	
	public void draw(Canvas canvas){
		
		
		canvas.drawColor(Color.BLACK);
		for(ArrayList<Sprite> row : spriteList){
			for(Sprite sprite : row){
				sprite.draw(canvas);
			}
		}
		up.draw(canvas);
		down.draw(canvas);
		left.draw(canvas);
		right.draw(canvas);
		player.draw(canvas);
		for (Opponent opp : this.opponents) {
			opp.draw(canvas);
		}
	}
	public void updateGame(PeerObject obj) {
		switch (obj.getgObj()) {
		case PLAYER:
			opponents.get(0).setPosition((float)obj.getxPosition(),(float) obj.getyPosition());
//			System.out.println(obj.getxPosition() + " x og y er " + obj.getyPosition());
			break;
		case BOMB:
			break;

		default:
			break;
		}
		
	}
}
