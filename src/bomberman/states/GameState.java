package bomberman.states;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import bomberman.connection.Client;
import bomberman.game.AIBot;
import bomberman.game.Board;
import bomberman.game.Bomb;
import bomberman.game.Constants;
import bomberman.game.Crate;
import bomberman.game.Direction;
import bomberman.game.Empty;
import bomberman.game.Explosion;
import bomberman.game.GameObject;
import bomberman.game.Opponent;
import bomberman.game.PeerObject;
import bomberman.game.Player;
import bomberman.game.ColorObject;
import bomberman.game.PowerUp;
import bomberman.game.Wall;
import bomberman.graphics.BombImages;
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
	private ArrayList<Explosion> explosions;
	
	public static ArrayList<ArrayList<Sprite>> spriteList = new ArrayList<ArrayList<Sprite>>();
	private double startingX;
	private double startingY;
	private ArrayList<Opponent> opponents;
	private ArrayList<AIBot> bots;
	private Client client =null;
	private ArrayList<Player> allPlayers;
	private Random randomGenerator = new Random();
//	private MainMenuWithGraphics single, multi;
	
	int counter = 0;
	
	public GameState(){
		this.board = new Board();
		this.player = new Player("Player1",ColorObject.BROWN,this);
		this.startingX = Constants.screenWidth/2 - Constants.getHeight()*6.5;
		this.startingY = 0.0f;
		
		//Buttons to control the player
		this.up = new Buttons("up",(int) (Constants.screenWidth*0.888f), (int) (Constants.screenHeight*0.3125f));
		this.down = new Buttons("down",(int) (Constants.screenWidth*0.888f), (int) (Constants.screenHeight*0.4375f));
		this.right = new Buttons("right",(int) (Constants.screenWidth*0.9665f), (int) (Constants.screenHeight*0.375f));
		this.left = new Buttons("left",(int) (Constants.screenWidth*0.81f), (int) (Constants.screenHeight*0.375f));
		
		/**
		 * Button do place bombs
		 */
		if(Constants.screenHeight > 752)
			this.bombIcon = new Buttons("bomb", (int) (Constants.screenWidth*0.08f), (int) (Constants.screenHeight*0.4f));
		else
			this.bombIcon = new Buttons("bomb", (int) (Constants.screenWidth*0.08f), (int) (Constants.screenHeight*0.407f));
		
		bombs = new ArrayList<Bomb>();
		powerups = new ArrayList<PowerUp>();
		explosions = new ArrayList<Explosion>();
		bots = new ArrayList<AIBot>();
//		addSprites();
		addBots();
	}
	
	private void addBots() {
		bots.add(new AIBot("adjkhasd", ColorObject.BLACK, this));
		bots.add(new AIBot("adjkhasd", ColorObject.WHITE, this));
		bots.add(new AIBot("adjkhasd", ColorObject.SWAG, this));
		allPlayers = new ArrayList<Player>();
		allPlayers.add(player);
		for(AIBot bot : bots){
			allPlayers.add(bot);
		}
		for(AIBot bot : bots){
			bot.addAllBots(allPlayers);
		}
	}

	public GameState (Client client){
		this.board = new Board();
		this.client = client;
//		this.single = new MainMenuWithGraphics("single", 100, 100);
//		this.multi = new MainMenuWithGraphics("multi", 100, 200);
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
		explosions = new ArrayList<Explosion>();
		opponents = new ArrayList<Opponent>();
//		addSprites();
		this.player = new Player("Player1", ColorObject.BROWN,this);
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
			player.setSpeed(0, -150*Constants.getReceivingXRatio()*player.getPlayerSpeed());
		}
		else if(down.getBounds().contains(event.getX(), event.getY())){
			this.player.setDirection(Direction.DOWN);
			player.setSpeed(0, 150*Constants.getReceivingXRatio()*player.getPlayerSpeed());
		}
		else if(left.getBounds().contains(event.getX(), event.getY())){
			this.player.setDirection(Direction.LEFT);
			player.setSpeed(-150*Constants.getReceivingXRatio()*player.getPlayerSpeed(), 0);
		}
		else if(right.getBounds().contains(event.getX(), event.getY())){
			this.player.setDirection(Direction.RIGHT);
			player.setSpeed(150*Constants.getReceivingXRatio()*player.getPlayerSpeed(), 0);
		}
		else if(bombIcon.getBounds().contains(event.getX(), event.getY())){
			if(this.player.canThrowBomb()) {
				int x1 = Constants.getPositionX(player.getMiddleX());
				int y1 = Constants.getPositionY(player.getMiddleY());
				int x2 = x1 + this.player.getDirection().getX();
				int y2 = y1 + this.player.getDirection().getY();
				for(Bomb bomb : this.bombs) {
					if(bomb.collision(x1, y1) || bomb.collision(x2, y2)) {
						bomb.bombThrown(player.getDirection());
						return true;
					}
				}
			}
			if (this.player.canPlaceBomb()) {
				//TODO: Image glitch where bomb is viewed a few milliseconds in top-left of the screen before placed correctly.
				Bomb bomb = new Bomb(getTilePositionX(),getTilePositionY(),player.getMagnitude(),this);
				bombs.add(bomb);
				this.player.addBomb(bomb);
				if(client != null){
					client.sendAll(new PeerObject(this.player.getColor(), GameObject.BOMB, Constants.getPositionX(getTilePositionX()), Constants.getPositionY(getTilePositionY()), Direction.UP));
				}
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
		return (int) getSpriteBoard().get(gridY).get(gridX).getX();
	}
	
	/**
	 * Returns tile position in Y-direction from the current player position.
	 * @return
	 */
	public int getTilePositionY(){
		int gridX = Constants.getPositionX(player.getMiddleX());
		int gridY = Constants.getPositionY(player.getMiddleY());
		return (int) getSpriteBoard().get(gridY).get(gridX).getY();
	}
		
	/**
	 * This method adds the correct number of opponents to the game when started.
	 */
	public void addOpponent(Opponent opponent){
		opponents.add(opponent);
	}
	
	public void setSprite(int column, int row, Sprite sprite){
		this.board.setSprite(column, row, sprite);
	}
	
	public ArrayList<ArrayList<Sprite>> getSpriteBoard(){
		return this.board.getSpriteBoard();
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
		//Sending player location to all other players.
		++counter;
		if(counter % 3 == 0 && this.player.hasMovedSince()) {
			if(client != null){
			client.sendAll(new PeerObject(this.player.getColor(),GameObject.PLAYER,
				Constants.getUniversalXPosition(this.player.getMiddleX()),
				Constants.getUniversalYPosition(this.player.getMiddleY()),
				this.player.getDirection())
			);
			}
			player.updatePosition();
		}
			
		collisionCheck();
		removeExplosions();
		up.update(dt);
		down.update(dt);
		left.update(dt);
		right.update(dt);
		bombIcon.update(dt);
		player.update(dt);
		for(Iterator<Bomb> it = bombs.iterator(); it.hasNext();){
			Bomb bomb = it.next();
			bomb.checkBombCollision();
			bomb.update(dt);
			if(bomb.finished()) {
				it.remove();
				player.removeBomb(bomb);
				if(this.bots != null){
					for(AIBot bot: this.bots){
						bot.removeBomb(bomb);
					}
				}
			}
		}
		for (PowerUp powerup : this.powerups)
			powerup.update(dt);
//		for (Opponent opp : this.opponents)
//			opp.update(dt);
		if(this.bots != null){
			for (AIBot bot : this.bots)
				bot.update(dt);
		}
		for (Explosion explosion : this.explosions)
			explosion.update(dt);
		for(ArrayList<Sprite> row : getSpriteBoard()){
			for(Sprite sprite : row)
				sprite.update(dt);
		}
	}
	
	private void removeExplosions() {
		for(Iterator<Explosion> it = explosions.iterator(); it.hasNext();){
			Explosion explosion = it.next();
			if(explosion.removeExplosion()){
				it.remove();
			}
			
		}
		
	}
	
	public void addExplosion(Explosion explosion){
		this.explosions.add(explosion);
	}
	
	public void checkPlayerHit(float xPixel, float yPixel) {
		this.player.checkGotHit(xPixel, yPixel);
		for(AIBot bot : bots){
			bot.checkGotHit(xPixel, yPixel);
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
		for(ArrayList<Sprite> row : getSpriteBoard()){
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
//		for (Opponent opp : this.opponents) {
//			opp.draw(canvas);
//		}
		if(this.bots !=null){
			for (AIBot bot : this.bots) {
				bot.draw(canvas);
			}
		}
		for(PowerUp powerup : this.powerups)
			powerup.draw(canvas);
		for(Explosion explosion : this.explosions)
			explosion.draw(canvas);
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
					Sprite sprite = getSpriteBoard().get((int)obj.getY()).get((int)obj.getX());
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
	
	public void collisionCheck() {
		if(this.powerups.size() == 0) {
			return;
		}
		for(Player player : allPlayers){
			int x1 = Constants.getPositionX(player.getX());
			int y1 = Constants.getPositionY(player.getY());
			int x2 = Constants.getPositionX(player.getX() + player.getImageWidth());
			int y2 = Constants.getPositionY(player.getY() + player.getImageHeight());
		
			Iterator<PowerUp> it = this.powerups.iterator();
			while(it.hasNext()) {
				PowerUp powerup = it.next();
				if(powerup.collision(x1, y1) || powerup.collision(x2, y2)) {
					player.powerUp(powerup.getPowerUpType());
					it.remove();
				}
			}
		}
	}
	
	
	public void maybeCreatePowerUp(int x, int y) {
		int p = randomGenerator.nextInt(10);
		//TODO: Changed for testing purposes.
		if(p >= 8) {
			PowerUp powerup = new PowerUp(x, y);
			powerups.add(powerup);
			if(client != null){
				client.sendAll(new PeerObject(GameObject.POWERUP, x, y, powerup.getPowerUpType()));
			}
		}
	}
	
	public void kickBomb(int x, int y, Direction direction) {
		for(Bomb bomb : this.bombs) {
			if(bomb.collision(x, y) && !bomb.initiated()) {
				bomb.bombKicked(direction);
			}
		}
	}
	
	public boolean bombAtPosition(int x, int y) {
		if(this.bombs.size() == 0)
			return false;
		for(Bomb bomb : this.bombs)
			if(bomb.collision(x, y)){
				return true;
			}
		return false;
	}

	public void setPlayerColor(ColorObject obj) {
		this.player.setColor(obj);
	}

	public void addBomb(Bomb bomb) {
		this.bombs.add(bomb);
		
	}
}
