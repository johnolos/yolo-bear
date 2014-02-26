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
import bomberman.graphics.Buttons;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.input.TouchListener;

public class GameState extends State{
	
	private Player player;
	private Board  board;
	private Buttons up, down, left, right, bomb;
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
		//Buttons to control the player
		this.up = new Buttons("up",(int) (Constants.screenWidth*0.888f), (int) (Constants.screenHeight*0.3125f));
		this.down = new Buttons("up",(int) (Constants.screenWidth*0.888f), (int) (Constants.screenHeight*0.4375f));
		this.right = new Buttons("up",(int) (Constants.screenWidth*0.9665f), (int) (Constants.screenHeight*0.375f));
		this.left = new Buttons("up",(int) (Constants.screenWidth*0.81f), (int) (Constants.screenHeight*0.375f));
		this.bomb = new Buttons("bomb", (int) (Constants.screenWidth*0.08f), (int) (Constants.screenHeight*0.4f));
		addSprites();
		addOpponent();
		
		
		//Listens on direction buttons. Movement of the player is defined here.
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
					player.setSpeed(0, -150*Constants.getReceivingYRatio());
				}
				else if(down.getBounds().contains(event.getX(), event.getY())){
					player.setSpeed(0, 150*Constants.getReceivingYRatio());
				}
				else if(left.getBounds().contains(event.getX(), event.getY())){
					player.setSpeed(-150*Constants.getReceivingXRatio(), 0);
				}
				else if(right.getBounds().contains(event.getX(), event.getY())){
					player.setSpeed(150*Constants.getReceivingXRatio(), 0);
				}
				
				
				
				return false;
			}
		};
		
		addTouchListener(touch);
		
	}
	
	//This method should add the correct number of opponents to the game. Should in the future take in number of players, and color of players.
	public void addOpponent(){
		opponents = new ArrayList<Opponent>();
		opponents.add(new Opponent(ColorObject.BLUE));
		opponents.add(new Opponent(ColorObject.GREEN));
		opponents.add(new Opponent(ColorObject.YELLOW));
	}
	
	
	//This adds all board objects to an Array of Sprites. This Array is used when the board is drawn and will also be used for collision
	//later on (perhaps).
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
	
	//Called by Game every tic. All sprites needs to be updated here
	public void update(float dt){
		//Sending player location to all other players.
		client.sendAll(new PeerObject(ColorObject.BLUE,GameObject.PLAYER,this.player.getX()*Constants.getSendingXRatio(),this.player.getY()*Constants.getSendingYRatio()));
		
		up.update(dt);
		down.update(dt);
		left.update(dt);
		right.update(dt);
		bomb.update(dt);
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
	
	//Called by Game every tic. Sprites you want to see on the canvas should be drawn here. Mind the order the Sprites are drawn.
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
		bomb.draw(canvas);
		player.draw(canvas);
		for (Opponent opp : this.opponents) {
			opp.draw(canvas);
		}
	}
	
	//Called when client receives information from the other peers. Positions are here converted to fit the device the game is running on.
	//Need code for bombs and such..
	public void updateGame(PeerObject obj) {
		switch (obj.getgObj()) {
		case PLAYER:
			opponents.get(0).setPosition((float)obj.getxPosition()*Constants.getReceivingXRatio(),(float) obj.getyPosition()*Constants.getReceivingYRatio());
			System.out.println(obj.getxPosition()*Constants.getReceivingXRatio() + " x og y er " + obj.getyPosition()*Constants.getReceivingYRatio());
			break;
		case BOMB:
			break;

		default:
			break;
		}
		
	}
}
