package bomberman.states;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

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
import bomberman.game.PowerUp;
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
	private ArrayList<PowerUp> powerups;
	private ArrayList<ArrayList<Sprite>> spriteList = new ArrayList<ArrayList<Sprite>>();
	private double startingX;
	private double startingY;
	private ArrayList<Opponent> opponents;
	private Client client;
	private Random randomGenerator = new Random();
	
	int counter = 0;
	
	public GameState (Client client){
		this.client = client;
		this.player = new Player("Player1");
		this.board = new Board();
		this.startingX = Constants.screenWidth/2 - Constants.getHeight()*6.5;
		this.startingY = 0.0f;
		
		//Buttons to control the player
		this.up = new Buttons("up",(int) (Constants.screenWidth*0.888f), (int) (Constants.screenHeight*0.3125f));
		this.down = new Buttons("down",(int) (Constants.screenWidth*0.888f), (int) (Constants.screenHeight*0.4375f));
		this.right = new Buttons("right",(int) (Constants.screenWidth*0.9665f), (int) (Constants.screenHeight*0.375f));
		this.left = new Buttons("left",(int) (Constants.screenWidth*0.81f), (int) (Constants.screenHeight*0.375f));
		if(Constants.screenHeight > 752)
			this.bombIcon = new Buttons("bomb", (int) (Constants.screenWidth*0.08f), (int) (Constants.screenHeight*0.4f));
		else
			this.bombIcon = new Buttons("bomb", (int) (Constants.screenWidth*0.08f), (int) (Constants.screenHeight*0.407f));
		bombs = new ArrayList<Bomb>();
		powerups = new ArrayList<PowerUp>();
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
	
	/**
	 * Handles onTouchDown events given for the various game elements in the state.
	 */
	
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
		else if(bombIcon.getBounds().contains(event.getX(), event.getY())){
			if (this.player.canPlaceBomb()) {
				//TODO: Image glitch where bomb is viewed a few milliseconds in top-left of the screen before placed correctly.
				Bomb bomb = new Bomb(getTilePositionX(),getTilePositionY(),player.getMagnitude(),this);
				bombs.add(bomb);
				this.player.addBomb(bomb);
				client.sendAll(new PeerObject(this.player.getColor(), GameObject.BOMB, Constants.getPositionX(getTilePositionX()), Constants.getPositionY(getTilePositionY()), Direction.UP));
			}
		}
		return false;
	}
	
	/**
	 * Returns tile position in X-direction from current player position.
	 * @return
	 */
	public int getTilePositionX(){
		int gridX = Constants.getPositionX(player.getMiddleX());
		int gridY = Constants.getPositionY(player.getMiddleY());	
		return (int) spriteList.get(gridY).get(gridX).getX();
	}
	
	/**
	 * Returns tile position in Y-direction from the current player position.
	 * @return
	 */
	public int getTilePositionY(){
		int gridX = Constants.getPositionX(player.getMiddleX());
		int gridY = Constants.getPositionY(player.getMiddleY());
		return (int) spriteList.get(gridY).get(gridX).getY();
	}
	
	public void playerCollision() {
		if(!canPlayerMoveUp() && this.player.getDirection() == Direction.UP)
			player.setSpeed(player.getSpeed().getX(), 0);
		
		if(!canPlayerMoveDown() && this.player.getDirection() == Direction.DOWN)
			player.setSpeed(player.getSpeed().getX(), 0);
		
		if(!canPlayerMoveLeft() && this.player.getDirection() == Direction.LEFT)
			player.setSpeed(0, player.getSpeed().getY());
		
		if(!canPlayerMoveRight() && this.player.getDirection() == Direction.RIGHT)
			player.setSpeed(0, player.getSpeed().getY());
	}
	
	/**
	 * Returns true if a player can up right from the current location.
	 * @return
	 */
	public boolean canPlayerMoveUp() {
		Sprite sprite = spriteList.get(Constants.getPositionY(player.getMiddleY()) - 1).get(Constants.getPositionX(player.getMiddleX()));
		if(player.canMoveY())
			if( sprite instanceof Empty) return true;
		float pixelsY = (player.getPosition().getY()) - (sprite.getPosition().getY() + Constants.getHeight());
		if(pixelsY > 4.0f * Constants.getReceivingYRatio())
			return true;
		return false;
	}
	
	/**
	 * Returns true if a player can move down from the current location.
	 * @return
	 */
	public boolean canPlayerMoveDown() {
		Sprite sprite = spriteList.get(Constants.getPositionY(player.getMiddleY()) + 1).get(Constants.getPositionX(player.getMiddleX()));
		if(player.canMoveY())
			if( sprite instanceof Empty) return true;
		float pixelsY = sprite.getPosition().getY() - (player.getPosition().getY() + player.getImageHeight());
		if(pixelsY > 4.0f * Constants.getReceivingYRatio())
			return true;
		return false;
	}
	
	/**
	 * Returns true if a player can move left from the current location.
	 * @return
	 */
	public boolean canPlayerMoveLeft() {
		Sprite sprite = spriteList.get(Constants.getPositionY(player.getMiddleY())).get(Constants.getPositionX(player.getMiddleX()) - 1);
		if(player.canMoveX())
			if( sprite instanceof Empty) return true;
		float pixelsX = player.getPosition().getX() - (sprite.getPosition().getX() + Constants.getHeight());
		if(pixelsX > 4.0f * Constants.getReceivingYRatio())
			return true;
		return false;
	}
	
	/**
	 * Returns true if a player can move right from the current location.
	 * @return
	 */
	public boolean canPlayerMoveRight() {
		Sprite sprite = spriteList.get(Constants.getPositionY(player.getMiddleY())).get(Constants.getPositionX(player.getMiddleX()) + 1);
		if(player.canMoveX())
			if( sprite instanceof Empty) return true;
		float pixelsX = sprite.getPosition().getX() - (player.getPosition().getX() + player.getImageHeight());
		if(pixelsX > 4.0f * Constants.getReceivingYRatio())
			return true;
		return false;
	}
	
	/**
	 * This method adds the correct number of opponents to the game when started.
	 */
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
	
	/**
	 * Called every game tic. All sprites needs to be updated here.
	 */
	public void update(float dt){
		playerCollision();
		//Sending player location to all other players.
		++counter;
		if(counter % 3 == 0 && this.player.hasMovedSince()) {
			client.sendAll(new PeerObject(this.player.getColor(),GameObject.PLAYER,
				Constants.getUniversalXPosition(this.player.getMiddleX()),
				Constants.getUniversalYPosition(this.player.getMiddleY()),
				this.player.getDirection())
			);
			player.updatePosition();
		}
			
		
		up.update(dt);
		down.update(dt);
		left.update(dt);
		right.update(dt);
		bombIcon.update(dt);
		player.update(dt);
		for(Iterator<Bomb> it = bombs.iterator(); it.hasNext();){
			Bomb bomb = it.next();
			bomb.update(dt);
			if(bomb.finished()) {
				it.remove();
				player.removeBomb(bomb);
			}
		}
		for (PowerUp powerup : this.powerups)
			powerup.update(dt);
		for (Opponent opp : this.opponents)
			opp.update(dt);
		for(ArrayList<Sprite> row : spriteList){
			for(Sprite sprite : row)
				sprite.update(dt);
		}
	}
	
	/**
	 * Evaluates a bomb and removes the creates it finds in its impact range.
	 * Refactoring should be considered.
	 * @param bomb
	 */
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
				maybeCreatePowerUp(column, y);
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
				maybeCreatePowerUp(column, y);
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
				maybeCreatePowerUp(x, row);
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
				maybeCreatePowerUp(x, row);
				break;
			}
			i++;
		}
	}
	
	public ArrayList<Bomb> getBombs(){
		return bombs;
	}
	
	/**
	 * Called every game tic. Sprites are drawn on the given canvas parameter here.
	 */
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
		for(Iterator<Bomb> it = bombs.iterator(); it.hasNext();){
			Bomb bomb = it.next();
			bomb.draw(canvas);
		}
		for (Opponent opp : this.opponents) {
			opp.draw(canvas);
		}
		for(PowerUp powerup : this.powerups)
			powerup.draw(canvas);
		player.draw(canvas);
	}
	
	/**
	 * Called with an PeerObject which will update the GameState for this player.
	 * @param obj PeerObject received from other players.
	 * TODO: UPDATE FOR MORE GAME ELEMENTS
	 */
	public void updateGame(PeerObject obj) {
		switch (obj.getgObj()) {
		case PLAYER:
			ColorObject color = obj.getColor();
			for(Opponent opponent : opponents){
				if(opponent.getColor() == color){
					float x = obj.getX();
					float y = obj.getY();
					x = Constants.getLocalXPosition(x);
					y = Constants.getLocalYPosition(y);
					x = x - (this.player.getImageWidth() / 2);
					y = y - (this.player.getImageHeight() / 2);
					opponent.setPosition(x, y);
					opponent.setDirection(obj.getDirection());
				}
			}
			break;
		case BOMB:
			for(Opponent opponent : opponents){
				if(opponent.getColor() == obj.getColor()){
					Sprite sprite = spriteList.get((int)obj.getY()).get((int)obj.getX());
					bombs.add(new Bomb((int)sprite.getX(),(int)sprite.getY(),opponent.getMagnitude(),this));
				}
			}
			break;
		case POWERUP:
			PowerUp powerup = new PowerUp((int)obj.getX(), (int)obj.getY(),obj.getPowerUpType());
			this.powerups.add(powerup);
			break;
		default:
			break;
		}
	}
	
	public void maybeCreatePowerUp(int x, int y) {
		int p = randomGenerator.nextInt(10);
		if(p >= 7) {
			PowerUp powerup = new PowerUp(x, y);
			powerups.add(powerup);
			client.sendAll(new PeerObject(GameObject.POWERUP, x, y, powerup.getPowerUpType()));
		}
	}

	public void setPlayerColor(ColorObject obj) {
		this.player.setColor(obj);
	}
}
