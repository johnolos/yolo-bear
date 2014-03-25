package bomberman.game;

import java.util.ArrayList;
import sheep.game.Sprite;
import sheep.graphics.Image;
import android.graphics.Canvas;
import bomberman.graphics.PowerUpType;
import bomberman.states.GameState;

/**
 * Player extends Sprite
 */
public class Player extends Sprite {

	/** Player settings **/
	private String nameOfPlayer;
	private ArrayList<Image> playerImages = new ArrayList<Image>(); // UP, DOWN, RIGHT, LEFT
	private ColorObject color;
	private Direction direction = Direction.UP;
	private GameState gameState;
	
	/** Player power **/
	private int numberOfBombs = 1;
	private static final int MAX_NR_OF_BOMBS = 6;
	private int health =3; 
	private boolean kickBombs = false;
	private boolean throwBombs = false;
	private float speedOfPlayer = 1.7f;
	private static final float SPEED_UPGRADE = 0.4f;
	private static final float MAX_SPEED = 3.2f;
	private int magnitudeOfBombs = 1;
	private static final int MAX_NR_OF_MAGNITUDE_UPS = 5;
	private int scoreOfPlayer = 0;
	public boolean hasSuperBomb = false;
	
	private float previousX;
	private float previousY;
	private ArrayList<Bomb> bombsPlaced;
	private boolean dead = false;
	private float startPosX;
	private float startPosY;
	private long timeStamp;
	
	/**
	 * Constructor
	 * @param name
	 * @param color
	 * @param gs
	 */
	public Player(String name, ColorObject color, GameState gs) {
		this.nameOfPlayer = name;
		this.setPosition(Constants.screenWidth/2, Constants.screenHeight/2);
		this.setColor(color);
		this.gameState = gs;
		bombsPlaced = new ArrayList<Bomb>();
	}
	
	/**
	 * Returns the centered X-coordinate of player sprite.
	 * @return this.getX() + this.getImageWidth()/2 which is the middle of the screen x-axis
	 */
	public float getMiddleX(){
		return this.getX() + this.getImageWidth() / 2;
	}
	
	/**
	 * gotHit the player got hit by the bomb explosion
	 */
	public void gotHit(){
		this.health = this.health - 1;
		if(this.health == 0){
			this.setView(null);
			this.setPosition(0, 0);
			if(!isDead()){
				this.setDead(System.currentTimeMillis() - gameState.getGameStarted());				
			}
			gameState.playerDied();
			this.setSpeed(0, 0);
		}
	}
	
	/**
	 * Isdead does the player have any life's left
	 * @return true if player is dead else false
	 */
	public boolean isDead(){
		return this.dead;
	}
	
	/**
	 * CheckGotHit check if the player got hit by the bomb explosion
	 * @param xPixel
	 * @param yPixel
	 */
	public void checkGotHit(float xPixel, float yPixel){
		int x = Constants.getPositionX(xPixel);
		int y = Constants.getPositionY(yPixel);
		int playerX = Constants.getPositionX(getMiddleX());
		int playerY = Constants.getPositionY(getMiddleY());
		if(x == playerX && y == playerY){
			this.gotHit();
		}
	}
	
	/**
	 * Returns the centered Y-coordinate of player sprite.
	 * @return this.getY() + this.getImageHeight()/2 which is the middle of the screen y-axis
	 */
	public float getMiddleY() {
		return this.getY() + this.getImageHeight() / 2;
	}
	
	/**
	 * Handles appropriate image assignment given ColorObject input.
	 * Should be called at beginning of game when color is decided.
	 * @param color
	 */
	public void setColor(ColorObject color) {
		this.color = color;
		this.playerImages.clear();
		if (Constants.screenHeight >750) {
			switch (color) {
			case BROWN:
				this.playerImages.add(new Image(R.drawable.brownbearup));
				this.playerImages.add(new Image(R.drawable.brownbeardown));
				this.playerImages.add(new Image(R.drawable.brownbearright));
				this.playerImages.add(new Image(R.drawable.brownbearleft));
				this.startPosX = (float)Constants.screenWidth/2-Constants.getHeight()*5.4f;
				this.startPosY = (float) Constants.screenHeight/2 -Constants.getHeight()*5.60f;
				this.setPosition(this.startPosX,this.startPosY);
				break;
			case BLACK:
				this.playerImages.add(new Image(R.drawable.blackbearup));
				this.playerImages.add(new Image(R.drawable.blackbeardown));
				this.playerImages.add(new Image(R.drawable.blackbearright));
				this.playerImages.add(new Image(R.drawable.blackbearleft));
				this.startPosX = (float)Constants.screenWidth/2+Constants.getHeight()*4.60f;
				this.startPosY = (float) Constants.screenHeight/2 -Constants.getHeight()*5.60f;
				this.setPosition(this.startPosX,this.startPosY);
				break;
			case WHITE:
				this.playerImages.add(new Image(R.drawable.whitebearup));
				this.playerImages.add(new Image(R.drawable.whitebeardown));
				this.playerImages.add(new Image(R.drawable.whitebearright));
				this.playerImages.add(new Image(R.drawable.whitebearleft));
				this.startPosX = (float)Constants.screenWidth/2-Constants.getHeight()*5.40f;
				this.startPosY = (float) Constants.screenHeight/2 +Constants.getHeight()*4.60f;
				this.setPosition(this.startPosX,this.startPosY);
				break;
			case SWAG:
				this.playerImages.add(new Image(R.drawable.swaggybearup));
				this.playerImages.add(new Image(R.drawable.swaggybeardown));
				this.playerImages.add(new Image(R.drawable.swaggybearright));
				this.playerImages.add(new Image(R.drawable.swaggybearleft));
				this.startPosX = (float)Constants.screenWidth/2+Constants.getHeight()*4.60f;
				this.startPosY = (float) Constants.screenHeight/2 +Constants.getHeight()*4.60f;
				this.setPosition(this.startPosX,this.startPosY);
				break;

			default:
				break;
			}
		}
		else{
			switch (color) {
			case BROWN:
				this.playerImages.add(new Image(R.drawable.smallbrownbearup));
				this.playerImages.add(new Image(R.drawable.smallbrownbeardown));
				this.playerImages.add(new Image(R.drawable.smallbrownbearright));
				this.playerImages.add(new Image(R.drawable.smallbrownbearleft));
				this.startPosX = (float)Constants.screenWidth/2-Constants.getHeight()*5.4f;
				this.startPosY = (float) Constants.screenHeight/2 -Constants.getHeight()*5.40f;
				this.setPosition(startPosX,startPosY);
				break;
			case BLACK:
				this.playerImages.add(new Image(R.drawable.smallblackbearup));
				this.playerImages.add(new Image(R.drawable.smallblackbeardown));
				this.playerImages.add(new Image(R.drawable.smallblackbearright));
				this.playerImages.add(new Image(R.drawable.smallblackbearleft));
				this.startPosX = (float)Constants.screenWidth/2+Constants.getHeight()*4.60f;
				this.startPosY = (float) Constants.screenHeight/2 -Constants.getHeight()*5.40f;
				this.setPosition(startPosX,startPosY);
				break;
			case WHITE:
				this.playerImages.add(new Image(R.drawable.smallwhitebearup));
				this.playerImages.add(new Image(R.drawable.smallwhitebeardown));
				this.playerImages.add(new Image(R.drawable.smallwhitebearright));
				this.playerImages.add(new Image(R.drawable.smallwhitebearleft));
				this.startPosX = (float)Constants.screenWidth/2-Constants.getHeight()*5.40f;
				this.startPosY = (float) Constants.screenHeight/2 +Constants.getHeight()*4.60f;
				this.setPosition(startPosX,startPosY);
				break;
			case SWAG:
				this.playerImages.add(new Image(R.drawable.smallswaggybearup));
				this.playerImages.add(new Image(R.drawable.smallswaggybeardown));
				this.playerImages.add(new Image(R.drawable.smallswaggybearright));
				this.playerImages.add(new Image(R.drawable.smallswaggybearleft));
				this.startPosX = (float)Constants.screenWidth/2+Constants.getHeight()*4.60f;
				this.startPosY = (float) Constants.screenHeight/2 +Constants.getHeight()*4.60f;
				this.setPosition(startPosX,startPosY);
				break;

			default:
				break;
			}
		}
		this.setView(playerImages.get(0));
	}

	/**
	 * Reset player power-ups at end of round.
	 */
	public void resetRound() {
		this.numberOfBombs = 1;
		this.kickBombs = false;
		this.throwBombs = false;
		this.speedOfPlayer = 1.7f;
		this.magnitudeOfBombs = 2;
		this.dead = false;
		this.timeStamp = 0;
		this.health = 3;
		this.setPosition(startPosX, startPosY);
		this.setView(playerImages.get(0));
	}
	
	/**
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * This function also calls the update function in the super class
	 * @param dt
	 */
	public void update(float dt){
		if((!this.dead && !gameState.isMultiplayer())|| (gameState.getPlayer()==this && !this.dead)){
			playerCollision();
		}
		super.update(dt);

	}
	
	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * This draw calls the draw function in the super class
	 * @param canvas which you draw on.
	 */
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
	
	/**
	 * Checks if a player can move in Y-direction.
	 * @return true or false, true if player can move
	 */
	public boolean canMoveY() {
		if(Constants.getPositionX(this.getPosition().getX()) == Constants.getPositionX(this.getPosition().getX() + this.playerImages.get(0).getHeight()))
			return true;
		return false;
	}
	
	/**+
	 * Checks if a player can move in X-direction
	 * @return true or false, true if player can move
	 */
	public boolean canMoveX() {
		if(Constants.getPositionY(this.getPosition().getY()) == Constants.getPositionY(this.getPosition().getY() + this.playerImages.get(0).getHeight()))
			return true;
		return false;
	}
	
	/**
	 * get the direction the player is moving in
	 * @return direction
	 */
	public Direction getDirection() {
		return this.direction;
	}
	
	/**
	 * Sets player animation based on walking direction.
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		if(!isDead()){
			this.direction = direction;
			switch(direction) {
			case UP:
				this.setView(this.playerImages.get(0));
				break;
			case DOWN:
				this.setView(this.playerImages.get(1));
				break;
			case RIGHT:
				this.setView(this.playerImages.get(2));
				break;
			case LEFT:
				this.setView(this.playerImages.get(3));
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * getImageheight 
	 * @return playerImages.get(0).getHeight the height of the players
	 */
	public float getImageHeight(){
		return this.playerImages.get(0).getHeight();
	}
	
	/**
	 * getImageWidth
	 * @return playerImages.get(0).getWidth the width of the player
	 */
	public float getImageWidth() {
		return this.playerImages.get(0).getWidth();
	}
	
	/**
	 * Gives the player the power-ups, if the player have not maxed thus far
	 * @param powerUp Power-up the player archived.
	 */
	public void powerUp(PowerUpType powerUp) {
		switch(powerUp) {
		case BOMB:
			if(this.numberOfBombs < Player.MAX_NR_OF_BOMBS)
				this.numberOfBombs++;
			return;
		case SPEED:
			if(this.speedOfPlayer < Player.MAX_SPEED)
			this.speedOfPlayer+=Player.SPEED_UPGRADE;
			return; 
		case THROW:
			this.throwBombs = true;
			return;
		case KICK:
			this.kickBombs = true;
			return;
		case MAGNITUDE:
			if(this.magnitudeOfBombs < Player.MAX_NR_OF_MAGNITUDE_UPS)
			this.magnitudeOfBombs++;
			return;
		case SUPERBOMB:
			if(hasSuperBomb)
				return;
			else{
				hasSuperBomb = true;
				return;
			}
		default:
			break;
		}
		return;
	}
	
	/**
	 * getNameOfPlayer
	 * @return nameOfPlayer the name of the player
	 */
	public String getNameOfPlayer() {
		return this.nameOfPlayer;
	}
	
	/**
	 * getMagnitude
	 * @return magnitudeOfBombs the magnitude of the bombs
	 */
	public int getMagnitude(){
		return this.magnitudeOfBombs;
	}
	
	/**
	 * getColor
	 * @return color of the object
	 */
	public ColorObject getColor(){
		return this.color;
	}
	
	/**
	 * getPlayerSpeed
	 * @return speedOfPlayer the speed of the player
	 */
	public float getPlayerSpeed() {
		return this.speedOfPlayer;
	}
	
	/**
	 * getScore
	 * @return scoreOfPlayer the score of the player
	 */
	public int getScore() {
		return this.scoreOfPlayer;
	}

	/**
	 * canThrowBomb
	 * @return true if you can throw bombs else false
	 */
	public boolean canThrowBomb() {
		return this.throwBombs;
	}
	
	/**
	 * getNumber of bombs the player can drop at one time.
	 * @return numberOfBombs
	 */
	public int getNumberOfBombs(){
		return this.numberOfBombs;
	}
	
	/**
	 * canKickBomb can the player kick the bombs
	 * @return true if player can kick bomb else false
	 */
	public boolean canKickBomb() {
		return this.kickBombs;
	}
	
	/**
	 * UpdatePosition of player on screen
	 */
	public void updatePosition() {
		this.previousX = this.getMiddleX();
		this.previousY = this.getMiddleY();
	}
	
	/**
	 * HasMovedSince have the player moved since last time checked
	 * @return true if moved else false
	 */
	public boolean hasMovedSince() {
		return !(this.previousX == this.getMiddleX() && this.previousY == this.getMiddleY());
	}
	
	/**
	 * canPlaceBom can player place bomb
	 * @return true if player can place bomb, else false
	 */
	public boolean canPlaceBomb() {
		return (this.bombsPlaced.size() < getNumberOfBombs());
	}
	
	/**
	 * AddBomb place bomb on screen and into array
	 * @param bomb
	 */
	public void addBomb(Bomb bomb) {
		this.bombsPlaced.add(bomb);
	}
	
	/**
	 * removeBomb remove bomb from array and screen
	 * @param bomb
	 */
	public void removeBomb(Bomb bomb) {
		this.bombsPlaced.remove(bomb);
	}
	
	/**
	 * handleCollision between things
	 * @param dir
	 * @param y
	 * @param x
	 * @param posX
	 * @param posY
	 */
	public void handleCollision(Direction dir,int y, int x, float posX, float posY){
		Sprite sprite = gameState.getSpriteBoard().get(y+dir.getY()).get(x+dir.getX());
		if(sprite instanceof Empty){
			setPosition(posX, posY);
		}
		else{
			setSpeed(0, 0);
		}
	}
	
	/**
	 * Runs player collision detection in the direction the player is moving.
	 */
	public void playerCollision() {
		int x = Constants.getPositionX(getMiddleX());
		int y = Constants.getPositionY(getMiddleY());
		float diff = (Constants.getHeight()-getImageHeight())/2;
		Sprite thisSprite = gameState.getSpriteBoard().get(y).get(x);
		float posX = thisSprite.getX()+diff;
		float posY = thisSprite.getY()+diff;
		if(!canPlayerMove(Direction.UP) && getDirection() == Direction.UP)
			handleCollision(Direction.UP, y, x, posX, posY);

		if(!canPlayerMove(Direction.DOWN) && getDirection() == Direction.DOWN)
			handleCollision(Direction.DOWN, y, x, posX, posY);
		
		if(!canPlayerMove(Direction.LEFT) && getDirection() == Direction.LEFT)
			handleCollision(Direction.LEFT, y, x, posX, posY);
		
		if(!canPlayerMove(Direction.RIGHT) && getDirection() == Direction.RIGHT)
			handleCollision(Direction.RIGHT, y, x, posX, posY);
		if(gameState.getSpriteBoard().get(y).get(x) instanceof Wall){
			if(!isDead()){
				this.setDead(System.currentTimeMillis() - gameState.getGameStarted());
				setView(null);
				gameState.playerDied();
			}
		}
	}
	
	/**
	 * setDead set player dead
	 * @param dt
	 */
	public void setDead(long dt){
		this.dead = true;
		this.timeStamp = dt;
	}
	
	/**
	 * getPixlesY get the number of pixels between player and wall in direction down
	 * @param dir
	 * @param sprite
	 * @return
	 */
	public float getPixelsY(Direction dir,Sprite sprite){
		if(dir == Direction.DOWN){
			return sprite.getPosition().getY() - (getPosition().getY() + getImageHeight());
		}
		else{
			return getPosition().getY() - (sprite.getPosition().getY() + Constants.getHeight());
		}
	}
	
	/**
	 * getPixlesY get the number of pixels between player and wall in direction right
	 * @param dir
	 * @param sprite
	 * @return
	 */
	public float getPixelsX(Direction dir,Sprite sprite){
		if(dir == Direction.RIGHT){
			return sprite.getPosition().getX() - (getPosition().getX() + getImageHeight());
		}
		else{
			return getPosition().getX() - (sprite.getPosition().getX() + Constants.getHeight());
		}
	}
	
	/**
	 * Returns true if a player can move to the given direction.
	 * @return true or false
	 */
	public boolean canPlayerMove(Direction dir){
		int y = Constants.getPositionY(getMiddleY());
		int x = Constants.getPositionX(getMiddleX());
		Sprite sprite = gameState.getSpriteBoard().get(y+dir.getY()).get(x+dir.getX());
		if(dir == Direction.DOWN || dir == Direction.UP){
			if(canMoveY()){
				if(sprite instanceof Empty){
					if(!gameState.bombAtPosition(x+dir.getX(), y+dir.getY())) return true;
				}
			}
			float pixelsY = getPixelsY(dir, sprite);
			if(pixelsY > Constants.COLLSION_RANGE * Constants.getReceivingYRatio())
				return true;
			if(gameState.bombAtPosition(x+dir.getX(),y+dir.getY())&& getDirection() == dir) {
				gameState.kickBomb(x +dir.getX(),y +dir.getY(),getDirection());
			}
			return false;
		}
		else{
			if(canMoveX()){
				if(sprite instanceof Empty){
					if(!gameState.bombAtPosition(x+dir.getX(), y+dir.getY())) return true;
				}
			}
			float pixelsX = getPixelsX(dir, sprite);
			if(pixelsX > Constants.COLLSION_RANGE * Constants.getReceivingYRatio())
				return true;
			if(gameState.bombAtPosition(x+dir.getX(),y+dir.getY()) && getDirection()==dir) {
				gameState.kickBomb(x+dir.getX(),y+dir.getY(),getDirection());
			}
			return false;
		}

	}

	/**
	 * getTimeStamp
	 * @return timeStamp
	 */
	public long getTimeStamp() {
		return this.timeStamp;
	}
	
	/**
	 * setMagnitude
	 * @param magnitude new magnitude
	 */
	public void setMagnitude(int magnitude) {
		magnitudeOfBombs = magnitude;
		
	}

	/**
	 * setDeadOpponent opponents dead
	 * @param timeStamp2
	 */
	public void setDeadOpponent(long timeStamp2) {
		timeStamp = timeStamp2;
		dead = true;
		
	}
}
