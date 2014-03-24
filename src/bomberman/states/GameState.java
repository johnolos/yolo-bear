package bomberman.states;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;
import java.util.jar.Attributes;

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
import bomberman.game.R;
import bomberman.game.Wall;
import bomberman.graphics.BombImages;
import bomberman.graphics.Buttons;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.input.TouchListener;

public class GameState extends State implements TouchListener {

	private Player player;
	private Board board;
	private Buttons up, down, left, right, bombIcon;
	private ArrayList<Bomb> bombs, copyOfBombs;
	private ArrayList<PowerUp> powerups, copyOfPowerups;
	private ArrayList<Explosion> explosions, copyOfExplosions;
	private boolean isBombArrayChanged = true,isPowerupArrayChanged = true, isExplosionArrayChanged = true;
	private boolean isMultiplayer = false;

	public static ArrayList<ArrayList<Sprite>> spriteList = new ArrayList<ArrayList<Sprite>>();
	private ArrayList<Opponent> opponents;
	private ArrayList<AIBot> bots;
	private Client client = null;
	private ArrayList<Player> allPlayers;
	private Random randomGenerator = new Random();
	// private MainMenuWithGraphics single, multi;
	private boolean haveMoved = false;
	int counter = 0;
	private long gameStarted;
	private boolean suddenDeathInitiated = false;

	public GameState(ColorObject color, int opponentNumber) {
		// Board class now handles creating the actual gameBoard,
		this.board = new Board();
		this.gameStarted = System.currentTimeMillis();
		this.player = new Player("Player1", color, this);

		// Buttons to control the player
		this.up = new Buttons("up", (int) (Constants.screenWidth * 0.888f),
				(int) (Constants.screenHeight * 0.3125f));
		this.down = new Buttons("down", (int) (Constants.screenWidth * 0.888f),
				(int) (Constants.screenHeight * 0.4375f));
		this.right = new Buttons("right",
				(int) (Constants.screenWidth * 0.9665f),
				(int) (Constants.screenHeight * 0.375f));
		this.left = new Buttons("left", (int) (Constants.screenWidth * 0.81f),
				(int) (Constants.screenHeight * 0.375f));

		/**
		 * Button do place bombs
		 */
		if (Constants.screenHeight > 752)
			this.bombIcon = new Buttons("bomb",
					(int) (Constants.screenWidth * 0.08f),
					(int) (Constants.screenHeight * 0.4f));
		else
			this.bombIcon = new Buttons("bomb",
					(int) (Constants.screenWidth * 0.08f),
					(int) (Constants.screenHeight * 0.407f));

		bombs = new ArrayList<Bomb>();
		powerups = new ArrayList<PowerUp>();
		explosions = new ArrayList<Explosion>();
		bots = new ArrayList<AIBot>();
		haveMoved = true;
		addBots(opponentNumber);
	}
	
	public GameState(Client client) {
		this.isMultiplayer= true;
		this.board = new Board();
		this.client = client;


		// Buttons to control the player
		this.up = new Buttons("up", (int) (Constants.screenWidth * 0.888f),
				(int) (Constants.screenHeight * 0.3125f));
		this.down = new Buttons("down", (int) (Constants.screenWidth * 0.888f),
				(int) (Constants.screenHeight * 0.4375f));
		this.right = new Buttons("right",
				(int) (Constants.screenWidth * 0.9665f),
				(int) (Constants.screenHeight * 0.375f));
		this.left = new Buttons("left", (int) (Constants.screenWidth * 0.81f),
				(int) (Constants.screenHeight * 0.375f));
		
		if (Constants.screenHeight > 752)
			this.bombIcon = new Buttons("bomb",
					(int) (Constants.screenWidth * 0.08f),
					(int) (Constants.screenHeight * 0.4f));
		else
			this.bombIcon = new Buttons("bomb",
					(int) (Constants.screenWidth * 0.08f),
					(int) (Constants.screenHeight * 0.407f));
		bombs = new ArrayList<Bomb>();
		powerups = new ArrayList<PowerUp>();
		explosions = new ArrayList<Explosion>();
		allPlayers = new ArrayList<Player>();
		this.player = new Player("Player1", ColorObject.BROWN, this);
	}

	private void addBots(int opponentNumber) {
		
		for (ColorObject color : ColorObject.values()) {
			if(!color.equals(this.player.getColor()) && bots.size()<opponentNumber){
				bots.add(new AIBot("adjkhasd", color, this));
			}
		}
		allPlayers = new ArrayList<Player>();
		allPlayers.add(player);
		for (AIBot bot : bots) {
			allPlayers.add(bot);
		}
		for (AIBot bot : bots) {
			bot.addAllBots(allPlayers);
		}
	}

	
	@Override
	public boolean onTouchUp(MotionEvent event) {
		player.setSpeed(0, 0);
		return false;
	}

	@Override
	public boolean onTouchMove(MotionEvent event) {
		return false;
	}

	/**
	 * Handles onTouchDown events given for the various game elements in the
	 * state.
	 */
	
	@Override
	public boolean onTouchDown(MotionEvent event) {
		if (up.getBounds().contains(event.getX(), event.getY())) {
			this.player.setDirection(Direction.UP);
			player.setSpeed(
					0,
					-150 * Constants.getReceivingXRatio()
					* player.getPlayerSpeed());
		} else if (down.getBounds().contains(event.getX(), event.getY())) {
			this.player.setDirection(Direction.DOWN);
			player.setSpeed(
					0,
					150 * Constants.getReceivingXRatio()
					* player.getPlayerSpeed());
		} else if (left.getBounds().contains(event.getX(), event.getY())) {
			this.player.setDirection(Direction.LEFT);
			player.setSpeed(
					-150 * Constants.getReceivingXRatio()
					* player.getPlayerSpeed(), 0);
		} else if (right.getBounds().contains(event.getX(), event.getY())) {
			this.player.setDirection(Direction.RIGHT);
			player.setSpeed(
					150 * Constants.getReceivingXRatio()
					* player.getPlayerSpeed(), 0);
		} else if (bombIcon.getBounds().contains(event.getX(), event.getY())) {
			if (this.player.canThrowBomb()) {
				int x1 = Constants.getPositionX(player.getMiddleX());
				int y1 = Constants.getPositionY(player.getMiddleY());
				int x2 = x1 + this.player.getDirection().getX();
				int y2 = y1 + this.player.getDirection().getY();
				for (Bomb bomb : this.bombs) {
					if (bomb.collision(x1, y1) || bomb.collision(x2, y2)) {
						if(isMultiplayer){
							this.client.sendAll(new PeerObject(GameObject.THROW, bomb.getColumn(), bomb.getRow(), player.getDirection()));
							}
						bomb.bombThrown(player.getDirection());
						return true;
					}
				}
			}
			if (this.player.canPlaceBomb()) {
				// TODO: Image glitch where bomb is viewed a few milliseconds in
				// top-left of the screen before placed correctly.
				Bomb bomb = new Bomb(getTilePositionX(), getTilePositionY(),
						player.getMagnitude(), this,getPlayer().getColor());
				addBomb(bomb);
				this.player.addBomb(bomb);
				
				if (isMultiplayer) {
					client.sendAll(new PeerObject(this.player.getColor(),
							GameObject.BOMB, Constants
							.getPositionX(getTilePositionX()),
							Constants.getPositionY(getTilePositionY()),
							Direction.UP,player.getMagnitude()));
				}
			}
		}
		return false;
	}

	/**
	 * Returns tile position in X-direction from current player position.
	 * 
	 * @return
	 */
	public int getTilePositionX() {
		int gridX = Constants.getPositionX(player.getMiddleX());
		int gridY = Constants.getPositionY(player.getMiddleY());
		return (int) getSpriteBoard().get(gridY).get(gridX).getX();
	}

	/**
	 * Returns tile position in Y-direction from the current player position.
	 * 
	 * @return
	 */
	public int getTilePositionY() {
		int gridX = Constants.getPositionX(player.getMiddleX());
		int gridY = Constants.getPositionY(player.getMiddleY());
		return (int) getSpriteBoard().get(gridY).get(gridX).getY();
	}

	/**
	 * This method adds the correct number of opponents to the game when
	 * started.
	 */
//	public void addOpponent(Opponent opponent) {
//		opponents.add(opponent);
//	}

	public void setSprite(int column, int row, Sprite sprite) {
		this.board.setSprite(column, row, sprite);
	}

	public ArrayList<ArrayList<Sprite>> getSpriteBoard() {
		return this.board.getSpriteBoard();
	}

	/**
	 * Called every game tic. All sprites needs to be updated here.
	 */
	public void update(float dt) {
		
		if (!(board.lastWall())) {
			++counter;
			if (counter % 3 == 0 && this.player.hasMovedSince()) {
				if (isMultiplayer) {
					//Sends position to player
					client.sendAll(new PeerObject(this.player.getColor(),
							GameObject.PLAYER, Constants
							.getUniversalXPosition(this.player
									.getMiddleX()), Constants
									.getUniversalYPosition(this.player
											.getMiddleY()), this.player
											.getDirection()));
				}
				player.updatePosition();
			}

			if (this.suddenDeathInitiated) {
				// if(!(board.lastWall())){
				if (board.timeToPlaceWall(System.currentTimeMillis())) {
					board.placeSuddenDeathWall(board.getXSuddenDeathWall(),
							board.getYSuddenDeathWall());

				}
			}

			collisionCheck();
			removeExplosions();
			checkTimer();
			up.update(dt);
			down.update(dt);
			left.update(dt);
			right.update(dt);
			bombIcon.update(dt);
			player.update(dt);
			for (Iterator<Bomb> it = bombs.iterator(); it.hasNext();) {
				Bomb bomb = it.next();
				bomb.checkBombCollision();
				bomb.update(dt);
				if (bomb.finished()) {
					it.remove();
					isBombArrayChanged = true;
					player.removeBomb(bomb);
					if (!isMultiplayer) {
						for (AIBot bot : this.bots) {
							bot.removeBomb(bomb);
						}
					}
				}
			}
			for (PowerUp powerup : this.powerups)
				powerup.update(dt);
			if(isMultiplayer){
				for (Player opp : this.allPlayers){				
					opp.update(dt);
		
				}
			}
		
			if (!isMultiplayer) {
				for (AIBot bot : this.bots)
					bot.update(dt);
			}
			for (Explosion explosion : this.explosions)
				explosion.update(dt);
			for (ArrayList<Sprite> row : getSpriteBoard()) {
				for (Sprite sprite : row)
					sprite.update(dt);
			}

		} else {
			getGame().pushState(new GameFinished(this.allPlayers,this.player, this));
		}
	}

	public void resetGame() {
		this.board.reset();
		this.powerups.clear();
		this.suddenDeathInitiated = false;
		this.gameStarted = System.currentTimeMillis();
		for (Player player : this.allPlayers) {
			player.resetRound();
		}

	}

	private void checkTimer() {
		if(!haveMoved){
			return;
		}
		if ((System.currentTimeMillis() - gameStarted > 150000
				&& !suddenDeathInitiated )) {
			board.initiateSuddenDeath(System.currentTimeMillis());
			suddenDeathInitiated = true;
		}
		if(gameOver()){
			if(!suddenDeathInitiated){
				board.initiateSuddenDeath(System.currentTimeMillis());
				board.SpeedUpSD();
				suddenDeathInitiated = true;
			}
			else{
				suddenDeathInitiated = true;
				board.SpeedUpSD();
			}
		}
		

	}

	private boolean gameOver() {
		if(this.player.isDead() && !isMultiplayer){
			return true;
		}
		int nrDead = 0 ;
		if(allPlayers != null){
			for(Player player : this.allPlayers){
				if(player.isDead()){
					nrDead++;
				}
			}
		}
		if(this.player.isDead()){
			return true;
		}
		return nrDead > allPlayers.size()-1 ? true:false;
	}

	private void removeExplosions() {
		for (Iterator<Explosion> it = explosions.iterator(); it.hasNext();) {
			Explosion explosion = it.next();
			if (explosion.removeExplosion()) {
				it.remove();
				isExplosionArrayChanged = true;
			}

		}

	}

	public void addExplosion(Explosion explosion) {
		explosions.add(explosion);
		isExplosionArrayChanged = true;
	}

	public void checkPlayerHit(float xPixel, float yPixel) {
		this.player.checkGotHit(xPixel, yPixel);
		if(!isMultiplayer){
			for (AIBot bot : bots) {
				bot.checkGotHit(xPixel, yPixel);
			}
		}
	}
	
	public void checkPowerUpHit(int x, int y) {
		for (Iterator<PowerUp> it = copyOfPowerups.iterator(); it.hasNext();) {
			PowerUp powerup = it.next();
			if(powerup.getColumn() == x && powerup.getRow() == y) {
				powerups.remove(powerup);
				it.remove();
				isPowerupArrayChanged = true;
				client.sendAll(new PeerObject(GameObject.POWERUP_CONSUMED, powerup.getColumn(), powerup.getRow(), player.getDirection()));
			}
		}
	}

	public ArrayList<Bomb> getBombs() {
		return bombs;
	}

	/**
	 * Called every game tic. Sprites are drawn on the given canvas parameter
	 * here.
	 */
	public void draw(Canvas canvas) {
		updateCopyOfElementArrays();
		canvas.drawColor(Color.BLACK);
		for (ArrayList<Sprite> row : getSpriteBoard()) {
			for (Sprite sprite : row) {
				sprite.draw(canvas);
			}
		}
		up.draw(canvas);
		down.draw(canvas);
		left.draw(canvas);
		right.draw(canvas);
		bombIcon.draw(canvas);
<<<<<<< HEAD
		for (Iterator<Bomb> it = bombs.iterator(); it.hasNext();) {
			Bomb bomb;
			try {
				bomb = it.next();
				bomb.draw(canvas);
			} catch (ConcurrentModificationException e) {
				e.printStackTrace();
			}
		
=======
		for (Iterator<Bomb> it = copyOfBombs.iterator(); it.hasNext();) {
			Bomb bomb = it.next();
			bomb.draw(canvas);
>>>>>>> e7eab6daf9e2725c941be3480154361428efbf5e
		}
		if(isMultiplayer){
			for (Player opp : this.allPlayers) {
				opp.draw(canvas);
		 
			}
		}
		if (this.bots != null) {
			for (AIBot bot : this.bots) {
				bot.draw(canvas);
			}
		}
		for (Iterator<PowerUp> it = copyOfPowerups.iterator(); it.hasNext();){
			PowerUp powerup = it.next();
			powerup.draw(canvas);
		}
		for (Iterator<Explosion> it = copyOfExplosions.iterator(); it.hasNext();){
			Explosion explosion = it.next();
			explosion.draw(canvas);
		}
		player.draw(canvas);
	}

	private void updateCopyOfElementArrays() {
		if(isBombArrayChanged) {
			copyOfBombs = new ArrayList<Bomb>(bombs);
			isBombArrayChanged = false;
		}
		if(isExplosionArrayChanged) {
			copyOfExplosions = new ArrayList<Explosion>(explosions);
			isExplosionArrayChanged = false;
		}
		if (isPowerupArrayChanged) {
			copyOfPowerups = new ArrayList<PowerUp>(powerups);
			isPowerupArrayChanged = false;
		}
	}

	/**
	 * Called with an PeerObject which will update the GameState for this
	 * player.
	 * 
	 * @param obj
	 *            PeerObject received from other players. TODO: UPDATE FOR MORE
	 *            GAME ELEMENTS
	 */
	public void receiveGameEvent(PeerObject obj) {
		if(obj.getColor()==getPlayer().getColor()){
			return;
		}
		
		switch (obj.getgObj()) {
		case PLAYER:
			ColorObject color = obj.getColor();
			if(allPlayers.isEmpty()){
				allPlayers.add(new Opponent(color, this));
				haveMoved = true;
			}
			for (Player opponent : allPlayers) {
				if (opponent.getColor() == color) {
					float x = obj.getX();
					float y = obj.getY();
					x = Constants.getLocalXPosition(x);
					y = Constants.getLocalYPosition(y);
					x = x - (this.player.getImageWidth() / 2);
					y = y - (this.player.getImageHeight() / 2);
					opponent.setPosition(x, y);
					opponent.setDirection(obj.getDirection());
				}
				else{
					allPlayers.add(new Opponent(color, this));
				}
			}
			break;
		case BOMB:
			for (Player opponent : allPlayers) {
				if (opponent.getColor() == obj.getColor()) {
					opponent.setMagnitude(obj.getMagnitude());
					Sprite sprite = getSpriteBoard().get((int) obj.getY()).get(
							(int) obj.getX());
					addBomb(new Bomb((int) sprite.getX(),
							(int) sprite.getY(), obj.getMagnitude(), this, obj.getColor()));
				}
			}
			break;
		case KICK:
			int x = (int) obj.getX();
			int y = (int) obj.getY();
			Direction kickDirection = obj.getDirection();
			for(Bomb bomb : this.bombs) {
				if(bomb.getColumn() == x && bomb.getRow() == y)
					bomb.bombKicked(kickDirection);
			}
			break;
		case THROW:
			int x1 = (int) obj.getX();
			int y1 = (int) obj.getY();
			Direction throwDirection = obj.getDirection();
			for(Bomb bomb : this.bombs) {
				if(bomb.getColumn() == x1 && bomb.getRow() == y1)
					bomb.bombThrown(throwDirection);
			} 
			break;
		case POWERUP_CONSUMED:
			int x2 = (int)obj.getX();
			int y2 = (int)obj.getY();
			for (PowerUp powerup : this.powerups) {
				if(powerup.getColumn() == x2 && powerup.getRow() == y2) {
					this.powerups.remove(powerup);
					isPowerupArrayChanged = true;
					break;
				}
			}
			break;
		case POWERUP:
			PowerUp powerup = new PowerUp((int) obj.getX(), (int) obj.getY(),
					obj.getPowerUpType(),this);
			addPowerup(powerup);
			break;
		case DIED:
			for (Player opponent : allPlayers) {
				if(opponent.getColor() == obj.getColor()){
					opponent.setDeadOpponent(obj.getTimeStamp());
				}
			}
			break;
		default:
			break;
		}
	}

	public void collisionCheck() {
		if (this.powerups.size() == 0) {
			return;
		}
		if(!isMultiplayer){
			if(allPlayers!= null){
				for (Player player : allPlayers) {
					int x1 = Constants.getPositionX(player.getX());
					int y1 = Constants.getPositionY(player.getY());
					int x2 = Constants.getPositionX(player.getX()
							+ player.getImageWidth());
					int y2 = Constants.getPositionY(player.getY()
							+ player.getImageHeight());

					Iterator<PowerUp> it = copyOfPowerups.iterator();
					while (it.hasNext()) {
						PowerUp powerup = it.next();
						if (powerup.collision(x1, y1) || powerup.collision(x2, y2)) {
							player.powerUp(powerup.getPowerUpType());
//							it.remove();
							powerups.remove(powerup);
							it.remove();
							isPowerupArrayChanged = true;
						}
					}
				}
			}
		}
		//Player picked up powerUp
		else{
			int x1 = Constants.getPositionX(player.getX());
			int y1 = Constants.getPositionY(player.getY());
			int x2 = Constants.getPositionX(player.getX()
					+ player.getImageWidth());
			int y2 = Constants.getPositionY(player.getY()
					+ player.getImageHeight());
			Iterator<PowerUp> it = copyOfPowerups.iterator();
			while (it.hasNext()) {
				PowerUp powerup = it.next();
				if (powerup.collision(x1, y1) || powerup.collision(x2, y2)) {
					player.powerUp(powerup.getPowerUpType());
					client.sendAll(new PeerObject(GameObject.POWERUP_CONSUMED, powerup.getColumn(), powerup.getRow(), player.getDirection()));
					it.remove();
					isPowerupArrayChanged = true;
				}
			}
		}
	}

	public void maybeCreatePowerUp(int x, int y) {
		int p = randomGenerator.nextInt(10);
		if (p >= 7) {
			PowerUp powerup = new PowerUp(x, y, this);
			addPowerup(powerup);
			if (isMultiplayer) {
				client.sendAll(new PeerObject(GameObject.POWERUP, x, y, powerup
						.getPowerUpType()));
			}
		}
	}

	public void kickBomb(int x, int y, Direction direction) {
		for (Bomb bomb : this.bombs) {
			if (bomb.collision(x, y) && !bomb.initiated()) {
				bomb.bombKicked(direction);
				if(isMultiplayer){
					client.sendAll(new PeerObject(GameObject.KICK, x, y, direction));
				}
			}
		}
	}

	public boolean bombAtPosition(int x, int y) {
		if (this.bombs.size() == 0)
			return false;
		for (Iterator<Bomb> it = copyOfBombs.iterator(); it.hasNext();) {
			Bomb bomb = it.next();
			if(bomb.collision(x, y))
				return true;
		}
		return false;
	}

	public void setPlayerColor(ColorObject obj) {
		this.player.setColor(obj);
	}

	public void addBomb(Bomb bomb) {
		bombs.add(bomb);
		isBombArrayChanged = true;
	}
	
	public void addPowerup(PowerUp powerup) {
		powerups.add(powerup);
		isPowerupArrayChanged = true;
	}

	public boolean isMultiplayer() {
		return isMultiplayer;
	}

	public Player getPlayer() {
		return player;
	}

	public void startGame() {
		gameStarted = System.currentTimeMillis();
		
	}
	
	public long getGameStarted() {
		return gameStarted;
	}

	public void playerDied() {
<<<<<<< HEAD
		long dt = System.currentTimeMillis() - gameStarted;
		if(isMultiplayer){
			client.sendAll(new PeerObject(player.getColor(), GameObject.DIED, dt));	
		}
=======
		if (isMultiplayer) {
			long dt = System.currentTimeMillis() - gameStarted;
			client.sendAll(new PeerObject(player.getColor(), GameObject.DIED, dt));		
		}	
>>>>>>> e7eab6daf9e2725c941be3480154361428efbf5e
	}
}
