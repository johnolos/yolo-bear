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
	
	private Direction direction = Direction.DOWN;
	
	public GameState (Client client){
		this.client = client;
		this.player = new Player("Player1");
		this.board = new Board();
		//Finding the upper-left coordinates of the game-view
		this.startingX = Constants.screenWidth/2 - Constants.getHeight()*6.5;
		this.startingY = Constants.screenHeight/2-Constants.getHeight()*6.5;
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
			direction = Direction.UP;
			player.setSpeed(0, -150*Constants.getReceivingYRatio());
		}
		else if(down.getBounds().contains(event.getX(), event.getY())){
			direction = Direction.DOWN;
			player.setSpeed(0, 150*Constants.getReceivingYRatio());
		}
		else if(left.getBounds().contains(event.getX(), event.getY())){
			direction = Direction.LEFT;
			player.setSpeed(-150*Constants.getReceivingXRatio(), 0);
		}
		else if(right.getBounds().contains(event.getX(), event.getY())){
			direction = Direction.RIGHT;
			player.setSpeed(150*Constants.getReceivingXRatio(), 0);
		}
		//The bombs are now placed in the center of the tile the player is located. 
		else if(bombIcon.getBounds().contains(event.getX(), event.getY())){
			bombs.add(new Bomb(getTilePositionX(),getTilePositionY(),player.getMagnitude(),this));
		}
		return false;
	}
	
	public int getTilePositionX(){
		float x = player.getPosition().getX();
		float y = player.getPosition().getY();
		int gridX = Constants.getPositionX(x+(player.getImageHeight()/2));
		int gridY = Constants.getPositionY(y+(player.getImageHeight()/2));
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
			Sprite sprite = spriteList.get(Constants.getPositionY(player.getPosition().getY()) - 1).get(Constants.getPositionX(player.getPosition().getX() + 
							player.getImageHeight()/2));
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
			Sprite sprite = spriteList.get(Constants.getPositionY(player.getPosition().getY() +
					player.getImageHeight()/2) + 1).get(Constants.getPositionX(player.getPosition().getX() + 
							player.getImageHeight()/2));
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
			Sprite sprite = spriteList.get(Constants.getPositionY(player.getPosition().getY() +
					player.getImageHeight()/2)).get(Constants.getPositionX(player.getPosition().getX()) - 1);
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
			Sprite sprite = spriteList.get(Constants.getPositionY(player.getPosition().getY() +
					player.getImageHeight()/2)).get(Constants.getPositionX(player.getPosition().getX()) + 1);
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
		opponents.add(new Opponent(ColorObject.BLUE));
		opponents.add(new Opponent(ColorObject.GREEN));
		opponents.add(new Opponent(ColorObject.YELLOW));
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
		
		System.out.println(Constants.getPositionX(player.getPosition().getX()));
		
		if(!canPlayerMoveUp() && direction == Direction.UP) {
			player.setSpeed(player.getSpeed().getX(), 0);
		}
		
		if(!canPlayerMoveDown() && direction == Direction.DOWN) {
			player.setSpeed(player.getSpeed().getX(), 0);
		}
		
		if(!canPlayerMoveLeft() && direction == Direction.LEFT) {
			player.setSpeed(0, player.getSpeed().getY());
		}
		
		if(!canPlayerMoveRight() && direction == Direction.RIGHT) {
			player.setSpeed(0, player.getSpeed().getY());
		}
		
		//Sending player location to all other players.
		client.sendAll(new PeerObject(ColorObject.BLUE,GameObject.PLAYER,this.player.getX()*Constants.getSendingXRatio(),this.player.getY()*Constants.getSendingYRatio()));
//		client.sendAll(new PeerObject(ColorObject.BLUE,GameObject.PLAYER,Constants.getUniversalXPosition(this.player.getX()),Constants.getUniversalYPosition(this.player.getY())));
		
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
					opponent.setPosition((float)obj.getxPosition()*Constants.getReceivingXRatio(),(float) obj.getyPosition()*Constants.getReceivingYRatio());
//					opponent.setPosition(Constants.getLocalXPosition(obj.getxPosition()),Constants.getLocalYPosition(obj.getyPosition()));
				}
			}
//			This is the original code for moving opponent:
//			opponents.get(0).setPosition((float)obj.getxPosition()*Constants.getReceivingXRatio(),(float) obj.getyPosition()*Constants.getReceivingYRatio());
//			System.out.println(obj.getxPosition()*Constants.getReceivingXRatio() + " x og y er " + obj.getyPosition()*Constants.getReceivingYRatio());
			break;
		case BOMB:
			break;

		default:
			break;
		}
		
	}

	public void setPlayerColor(ColorObject obj) {
		this.player.setColor(obj);
	}
}
