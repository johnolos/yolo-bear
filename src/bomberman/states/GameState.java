package bomberman.states;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import bomberman.connection.Client;
import bomberman.game.Board;
import bomberman.game.Bomb;
import bomberman.game.Constants;
import bomberman.game.Crate;
import bomberman.game.Direction;
import bomberman.game.Empty;
import bomberman.game.GameObject;
import bomberman.game.Opponent;
import bomberman.game.PeerObject;
import bomberman.game.Player;
import bomberman.game.ColorObject;
import bomberman.game.Wall;
import bomberman.graphics.Buttons;
import sheep.game.Game;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.input.TouchListener;

public class GameState extends State implements TouchListener{
	
	private Player player;
	private Board  board;
	private Buttons up, down, left, right, bombIcon;
	private ArrayList<Bomb> bombs;
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
//		this.startingY = Constants.screenHeight/2-Constants.getHeight()*6.5;
		this.startingY = 0.0f;
		//Buttons to control the player
		this.up = new Buttons("up",(int) (Constants.screenWidth*0.888f), (int) (Constants.screenHeight*0.3125f));
		this.down = new Buttons("down",(int) (Constants.screenWidth*0.888f), (int) (Constants.screenHeight*0.4375f));
		this.right = new Buttons("right",(int) (Constants.screenWidth*0.9665f), (int) (Constants.screenHeight*0.375f));
		this.left = new Buttons("left",(int) (Constants.screenWidth*0.81f), (int) (Constants.screenHeight*0.375f));
		this.bombIcon = new Buttons("bomb", (int) (Constants.screenWidth*0.08f), (int) (Constants.screenHeight*0.4f));
		bombs = new ArrayList<Bomb>();
		addSprites();
		addOpponent();
	}
	
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
			this.player.setDirection(Direction.UP);
			player.setSpeed(0, -150*Constants.getReceivingXRatio());
		}
		else if(down.getBounds().contains(event.getX(), event.getY())){
			this.player.setDirection(Direction.DOWN);
			player.setSpeed(0, 150*Constants.getReceivingXRatio());
		}
		else if(left.getBounds().contains(event.getX(), event.getY())){
			this.player.setDirection(Direction.LEFT);
			player.setSpeed(-150*Constants.getReceivingXRatio(), 0);
		}
		else if(right.getBounds().contains(event.getX(), event.getY())){
			this.player.setDirection(Direction.RIGHT);
			player.setSpeed(150*Constants.getReceivingXRatio(), 0);
		}
		//The bombs are now placed in the center of the tile the player is located. 
		else if(bombIcon.getBounds().contains(event.getX(), event.getY())){
			bombs.add(new Bomb(getTilePositionX(),getTilePositionY(),player.getMagnitude(),this));
			client.sendAll(new PeerObject(this.player.getColor(), GameObject.BOMB, getTilePositionX(), getTilePositionY()));
		}
		return false;
	}
	
	public int getTilePositionX(){
		int gridX = Constants.getPositionX(player.getMiddleX());
		int gridY = Constants.getPositionY(player.getMiddleY());	
		return (int) spriteList.get(gridY).get(gridX).getPosition().getX();
	}
	
	public int getTilePositionY(){
		float x = player.getPosition().getX();
		float y = player.getPosition().getY();
		int gridX = Constants.getPositionX(x);
		int gridY = Constants.getPositionY(y);
		return (int) spriteList.get(gridY).get(gridX).getPosition().getY();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canPlayerMoveUp() {
		if(player.canMoveY()) {
			Sprite sprite = spriteList.get(Constants.getPositionY(player.getMiddleY()) - 1).get(Constants.getPositionX(player.getMiddleX()));
			if( sprite instanceof Empty) {
				return true;
			} else {
				float pixelsY = (player.getPosition().getY()) - (sprite.getPosition().getY() + Constants.getHeight());
				if(pixelsY > 8.0f * Constants.getReceivingYRatio()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canPlayerMoveDown() {
		if(player.canMoveY()) {
			Sprite sprite = spriteList.get(Constants.getPositionY(player.getMiddleY()) + 1).get(Constants.getPositionX(player.getMiddleX()));
			if( sprite instanceof Empty) {
				return true;
			} else {
				float pixelsY = sprite.getPosition().getY() - (player.getPosition().getY() + player.getImageHeight());
				if(pixelsY > 8.0f * Constants.getReceivingYRatio()) {
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canPlayerMoveLeft() {
		if(player.canMoveX()) {
			Sprite sprite = spriteList.get(Constants.getPositionY(player.getMiddleY())).get(Constants.getPositionX(player.getMiddleX()) - 1);
			if( sprite instanceof Empty) {
				return true;
			} else {
				float pixelsX = player.getPosition().getX() - (sprite.getPosition().getX() + Constants.getHeight());
				if(pixelsX > 8.0f * Constants.getReceivingYRatio()) {
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canPlayerMoveRight() {
		if(player.canMoveX()) {
			Sprite sprite = spriteList.get(Constants.getPositionY(player.getMiddleY())).get(Constants.getPositionX(player.getMiddleX()) + 1);
			if( sprite instanceof Empty) {
				return true;
			} else {
				float pixelsX = sprite.getPosition().getX() - (player.getPosition().getX() + player.getImageHeight());
				if(pixelsX > 8.0f * Constants.getReceivingYRatio()) {
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	
	//This method should add the correct number of opponents to the game. Should in the future take in number of players, and color of players.
	public void addOpponent(){
		opponents = new ArrayList<Opponent>();
		opponents.add(new Opponent(ColorObject.BROWN));
		opponents.add(new Opponent(ColorObject.BLACK));
		opponents.add(new Opponent(ColorObject.WHITE));
		opponents.add(new Opponent(ColorObject.SWAG));
	}
	
	
	//This adds all board objects to an Array of Sprites. This Array is used when the board is drawn and will also used for player detection.
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
		
		if(!canPlayerMoveUp() && this.player.getDirection() == Direction.UP) {
			player.setSpeed(player.getSpeed().getX(), 0);
		}
		
		if(!canPlayerMoveDown() && this.player.getDirection() == Direction.DOWN) {
			player.setSpeed(player.getSpeed().getX(), 0);
		}
		
		if(!canPlayerMoveLeft() && this.player.getDirection() == Direction.LEFT) {
			player.setSpeed(0, player.getSpeed().getY());
		}
		
		if(!canPlayerMoveRight() && this.player.getDirection() == Direction.RIGHT) {
			player.setSpeed(0, player.getSpeed().getY());
		}
		
		//Sending player location to all other players.
		float x = this.player.getMiddleX();
		float y = this.player.getMiddleY();
		System.out.println("X: " + x);
		System.out.println("Y: " + y);
		float x1 = Constants.getUniversalXPosition(this.player.getMiddleX());
		float y1 = Constants.getUniversalYPosition(this.player.getMiddleY());
		System.out.println("X: " + x1);
		System.out.println("Y: " + y1);
		client.sendAll(new PeerObject(this.player.getColor(),GameObject.PLAYER,
				Constants.getUniversalXPosition(this.player.getMiddleX()),
				Constants.getUniversalYPosition(this.player.getMiddleY()))
		);
		
//		client.sendAll(new PeerObject(ColorObject.BLUE,GameObject.PLAYER,Constants.getUniversalXPosition(this.player.getX()),Constants.getUniversalYPosition(this.player.getY())));
		
//		Think this is the way to do this!?
//		client.sendAll(new PeerObject(this.player.getColor(),GameObject.PLAYER, Constants.pxToDp(this.player.getX()),Constants.pxToDp(this.player.getY())));
//		Constants.pxToDp(this.player.getX());
		
		
		
		up.update(dt);
		down.update(dt);
		left.update(dt);
		right.update(dt);
		bombIcon.update(dt);
		player.update(dt);
		for(Iterator<Bomb> it = bombs.iterator(); it.hasNext();){
			Bomb bomb = it.next();
			bomb.update(dt);
			if(bomb.finished()){
				it.remove();
			}
		}
		for (Opponent opp : this.opponents) {
			opp.update(dt);
		}
		
		for(ArrayList<Sprite> row : spriteList){
			for(Sprite sprite : row){
				sprite.update(dt);
			}
		}
	}
	
	public void bombImpact(Bomb bomb){
		int blastRadius = bomb.getBlastRadius();
		int x = Constants.getPositionX(bomb.getPosition().getX());
		int y = Constants.getPositionY(bomb.getPosition().getY());
		int i = 0;
		Sprite sprite;
		for(int column = x-1; column>=0; column-- ){
			if(i == blastRadius)
				break;
			sprite = spriteList.get(y).get(column);
			if(sprite instanceof Wall){
				break;
			}
			else if(sprite instanceof Crate){
				float xPixel = sprite.getPosition().getX();
				float yPixel = sprite.getPosition().getY();
				spriteList.get(y).remove(sprite);
				Empty empty = new Empty();
				empty.setPosition(xPixel, yPixel);
				spriteList.get(y).add(column,empty);
				break;
			}
			i++;
		}
		i = 0;
		for(int column = x+1; column<=12; column++ ){
			if(i == blastRadius)
				break;
			sprite = spriteList.get(y).get(column);
			if(sprite instanceof Wall){
				break;
			}
			else if(sprite instanceof Crate){
				float xPixel = sprite.getPosition().getX();
				float yPixel = sprite.getPosition().getY();
				spriteList.get(y).remove(sprite);
				Empty empty = new Empty();
				empty.setPosition(xPixel, yPixel);
				spriteList.get(y).add(column,empty);
				break;
			}
			i++;
		}
		i = 0;
		for(int row = y-1; row>=0; row-- ){
			if(i == blastRadius)
				break;
			sprite = spriteList.get(row).get(x);
			if(sprite instanceof Wall){
				break;
			}
			else if(sprite instanceof Crate){
				float xPixel = sprite.getPosition().getX();
				float yPixel = sprite.getPosition().getY();
				spriteList.get(row).remove(sprite);
				Empty empty = new Empty();
				empty.setPosition(xPixel, yPixel);
				spriteList.get(row).add(x,empty);
				break;
			}
			i++;
		}
		i = 0;
		for(int row = y+1; row<=12; row++ ){
			if(i == blastRadius)
				break;
			sprite = spriteList.get(row).get(x);
			if(sprite instanceof Wall){
				break;
			}
			else if(sprite instanceof Crate){
				float xPixel = sprite.getPosition().getX();
				float yPixel = sprite.getPosition().getY();
				spriteList.get(row).remove(sprite);
				Empty empty = new Empty();
				empty.setPosition(xPixel, yPixel);
				spriteList.get(row).add(x,empty);
				break;
			}
			i++;
		}
	}
	
	public ArrayList<Bomb> getBombs(){
		return bombs;
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
		bombIcon.draw(canvas);
		player.draw(canvas);
		for(Iterator<Bomb> it = bombs.iterator(); it.hasNext();){
			Bomb bomb = it.next();
			bomb.draw(canvas);
		}
		for (Opponent opp : this.opponents) {
			opp.draw(canvas);
		}
	}
	
	//Called when client receives information from the other peers. Positions are here converted to fit the device the game is running on.
	//Need code for bombs and such..
	public void updateGame(PeerObject obj) {
		switch (obj.getgObj()) {
		case PLAYER:
//			New code for moving opponents, now we should move the correct player. Player ID is color.
			ColorObject color = obj.getColor();
			for(Opponent opponent : opponents){
				if(opponent.getColor() == color){
					float x = obj.getxPosition();
					float y = obj.getyPosition();
					x = Constants.getLocalXPosition(x);
					y = Constants.getLocalYPosition(y);
					x = x - (this.player.getImageWidth() / 2);
					y = y - (this.player.getImageHeight() / 2);
					opponent.setPosition(x, y);
					
//					Original:
//					opponent.setPosition((float)obj.getxPosition()*Constants.getReceivingXRatio(),(float) obj.getyPosition()*Constants.getReceivingYRatio());
					
//					Testing with John-Olav:
//					opponent.setPosition(Constants.getLocalXPosition(obj.getxPosition()),Constants.getLocalYPosition(obj.getyPosition()));
					
//					Epic solution by Brage:
//					opponent.setPosition(Constants.dpToPx(obj.getxPosition()),Constants.dpToPx(obj.getyPosition()));
				}
			}
//			This is the original code for moving opponent:
//			opponents.get(0).setPosition((float)obj.getxPosition()*Constants.getReceivingXRatio(),(float) obj.getyPosition()*Constants.getReceivingYRatio());
//			System.out.println(obj.getxPosition()*Constants.getReceivingXRatio() + " x og y er " + obj.getyPosition()*Constants.getReceivingYRatio());
			break;
		case BOMB:
			System.out.println("Bomb received");
			for(Opponent opponent : opponents){
				if(opponent.getColor() == obj.getColor()){
					bombs.add(new Bomb((int)obj.getxPosition(),(int)obj.getyPosition(),opponent.getMagnitude(),this));
				}
			}
			break;

		default:
			break;
		}
		
	}

	public void setPlayerColor(ColorObject obj) {
		this.player.setColor(obj);
	}
}
