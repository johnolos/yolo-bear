package bomberman.game;

import java.util.ArrayList;

import sheep.game.Sprite;
import sheep.graphics.Image;
import android.graphics.Canvas;
import bomberman.graphics.PowerUpType;
import bomberman.states.GameState;

public class Player extends Sprite {

	/** Player settings **/
	private String nameOfPlayer;
	private ArrayList<Image> playerImages = new ArrayList<Image>(); // UP, DOWN, RIGHT, LEFT
	private ColorObject color;
	private Direction direction = Direction.UP;
	private GameState gameState;
	
	/** Player power **/
	private int numberOfBombs = 1;
	private int health =3;
	private boolean kickBombs = false;
	private boolean throwBombs = false;
	private float speedOfPlayer = 1.0f;
	private int magnitudeOfBombs = 2;
	private int scoreOfPlayer = 0;
	
	private float previousX;
	private float previousY;
	private ArrayList<Bomb> bombsPlaced;
	private boolean dead = false;
	private float startPosX;
	private float startPosY;
	private long timeStamp;
	
	
	public Player(String name, ColorObject color, GameState gs) {
		this.nameOfPlayer = name;
		this.setPosition(Constants.screenWidth/2, Constants.screenHeight/2);
		this.setColor(color);
		this.gameState = gs;
		bombsPlaced = new ArrayList<Bomb>();
	}
	
	/**
	 * Returns the centered X-coordinate of player sprite.
	 * @return
	 */
	public float getMiddleX(){
		return this.getX() + this.getImageWidth() / 2;
	}
	
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
	
	public boolean isDead(){
		return this.dead;
	}
	
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
	 * @return
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
		this.speedOfPlayer = 1.0f;
		this.magnitudeOfBombs = 2;
		this.dead = false;
		this.timeStamp = 0;
		this.health = 3;
		this.setPosition(startPosX, startPosY);
		this.setView(playerImages.get(0));
	}
	
	public void update(float dt){
		if((!this.dead && !gameState.isMultiplayer())|| (gameState.getPlayer()==this && !this.dead)){
			playerCollision();
		}
		super.update(dt);

	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
	
	
	
	/**
	 * Checks if a player can move in Y-direction.
	 * @return
	 */
	public boolean canMoveY() {
		if(Constants.getPositionX(this.getPosition().getX()) == Constants.getPositionX(this.getPosition().getX() + this.playerImages.get(0).getHeight()))
			return true;
		return false;
	}
	
	/**
	 * Checks if a player can move in X-direction
	 * @return
	 */
	public boolean canMoveX() {
		if(Constants.getPositionY(this.getPosition().getY()) == Constants.getPositionY(this.getPosition().getY() + this.playerImages.get(0).getHeight()))
			return true;
		return false;
	}
	
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
			}
		}
	}
	
	public float getImageHeight(){
		return this.playerImages.get(0).getHeight();
	}
	
	public float getImageWidth() {
		return this.playerImages.get(0).getWidth();
	}
	
	/**
	 * Handles power-ups
	 * @param powerUp Power-up the player archived.
	 */
	public void powerUp(PowerUpType powerUp) {
		switch(powerUp) {
		case BOMB:
			this.numberOfBombs++;
			return;
		case SPEED:
			this.speedOfPlayer+=0.2f;
			return;
		case THROW:
			this.throwBombs = true;
			return;
		case KICK:
			this.kickBombs = true;
			return;
		case MAGNITUDE:
			this.magnitudeOfBombs++;
			return;
		default:
			break;
		}
		return;
	}
	
	public String getNameOfPlayer() {
		return this.nameOfPlayer;
	}
	
	public int getMagnitude(){
		return this.magnitudeOfBombs;
	}
	
	public ColorObject getColor(){
		return this.color;
	}
	
	public float getPlayerSpeed() {
		return this.speedOfPlayer;
	}
	
	public int getScore() {
		return this.scoreOfPlayer;
	}
	
	public boolean canThrowBomb() {
		return this.throwBombs;
	}
	
	public int getNumberOfBombs(){
		return this.numberOfBombs;
	}
	
	public boolean canKickBomb() {
		return this.kickBombs;
	}
	
	public void updatePosition() {
		this.previousX = this.getMiddleX();
		this.previousY = this.getMiddleY();
	}
	
	public boolean hasMovedSince() {
		return !(this.previousX == this.getMiddleX() && this.previousY == this.getMiddleY());
	}
	
	public boolean canPlaceBomb() {
		return (this.bombsPlaced.size() < getNumberOfBombs());
	}
	
	public void addBomb(Bomb bomb) {
		this.bombsPlaced.add(bomb);
	}
	
	public void removeBomb(Bomb bomb) {
		this.bombsPlaced.remove(bomb);
	}
	
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
	public void setDead(long dt){
		this.dead = true;
		this.timeStamp = dt;
	}
	public float getPixelsY(Direction dir,Sprite sprite){
		if(dir == Direction.DOWN){
			return sprite.getPosition().getY() - (getPosition().getY() + getImageHeight());
		}
		else{
			return getPosition().getY() - (sprite.getPosition().getY() + Constants.getHeight());
		}
	}
	
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
	 * @return
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

	public long getTimeStamp() {
		return this.timeStamp;
	}
	
	public void setMagnitude(int magnitude) {
		magnitudeOfBombs = magnitude;
		
	}

	public void setDeadOpponent(long timeStamp2) {
		timeStamp = timeStamp2;
		dead = true;
		
	}

	
}
