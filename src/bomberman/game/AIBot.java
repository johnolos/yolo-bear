package bomberman.game;

import java.util.ArrayList;
import java.util.Random;

import bomberman.states.GameState;
import android.graphics.Canvas;
import sheep.game.Sprite;

public class AIBot extends Player {

	private GameState gameState;
	private long placedBomb;
	private Random random;
	
	private boolean changedRow = false;
	private boolean changedColumn = false;

	public AIBot(String name, ColorObject color, GameState gs) {
		super(name, color, gs);
		this.gameState = gs;
		this.random = new Random();
	}

	public void evadeBomb() {
		if(changedColumn && changedRow){
			return;
		}
		int x = Constants.getPositionX(this.getMiddleX());
		int y = Constants.getPositionY(this.getMiddleY());
		ArrayList<Direction> possibleDir = new ArrayList<Direction>();
		if(!changedColumn && !changedRow){
		for (Direction direction : Direction.values()) {
			if (direction != Direction.STOP) {
				Sprite sprite = GameState.spriteList.get(y + direction.getY())
						.get(x + direction.getX());
				if (sprite instanceof Empty) {
					possibleDir.add(direction);
				}
			}
		}
		for (Direction direction : possibleDir) {
			System.out.println(direction);
		}
		Direction dir = possibleDir.get(random.nextInt(possibleDir.size()));
		if(changeingRow(dir)){
			changedRow = true;
		}
		else{
			changedColumn = true;
		}
		startMove(dir);
		}
		else if(changedColumn){
			for(Direction direction : Direction.values()){
				if(changeingRow(direction)){
					Sprite sprite = GameState.spriteList.get(y + direction.getY()).get(x + direction.getX());
					if (sprite instanceof Empty) {
						possibleDir.add(direction);
					}	
				}
			}
			if(possibleDir.size() != 0){
				for (Direction direction : possibleDir) {
					System.out.println(direction);
				}
				Direction dir = possibleDir.get(random.nextInt(possibleDir.size()));
				changedRow = true;
				startMove(dir);
			}
		}
		else{
			for(Direction direction : Direction.values()){
				if(changeingColumn(direction)){
					Sprite sprite = GameState.spriteList.get(y + direction.getY()).get(x + direction.getX());
					if (sprite instanceof Empty) {
						possibleDir.add(direction);
					}	
				}
			}
			if(possibleDir.size() != 0){
				for (Direction direction : possibleDir) {
					System.out.println(direction);
				}
				Direction dir = possibleDir.get(random.nextInt(possibleDir.size()));
				changedColumn = true;
				startMove(dir);
			}
		}
		System.out.println("ChangedRow: " + changedRow);
		System.out.println("ChangedColumn: " + changedColumn);

	}

	private boolean changeingColumn(Direction direction) {
		if(direction ==Direction.LEFT || direction == Direction.RIGHT){
			return true;
		}
		return false;
	}

	private boolean changeingRow(Direction dir) {
		if(dir == Direction.DOWN || dir == Direction.UP){
			return true;
		}
		return false;
	}

	public void selectNextMove() {
		if (needToEvade()) {
			evadeBomb();
			return;
		} else if (this.getDirection() == Direction.STOP) {
			checkPlaceBomb();
			return;
		} else if (notMoving()) {
			findMove();
		}
	}

	private boolean notMoving() {
		return (this.getSpeed().getX() == 0 && this.getSpeed().getY() == 0);
	}

	private void checkPlaceBomb() {
		int x = Constants.getPositionX(this.getMiddleX());
		int y = Constants.getPositionY(this.getMiddleY());
		for (Direction direction : Direction.values()) {
			Sprite sprite = GameState.spriteList.get(y + direction.getY()).get(
					x + direction.getX());
			if (sprite instanceof Crate) {
				placeBomb(x, y);
				break;
			}
			findMove();

		}
	}

	private void findMove() {
		if (notMoving()) {
			int x = Constants.getPositionX(this.getMiddleX());
			int y = Constants.getPositionY(this.getMiddleY());
			for (Direction direction : Direction.values()) {
				if (direction != Direction.STOP) {
					Sprite sprite = GameState.spriteList.get(
							y + direction.getY()).get(x + direction.getX());
					if (sprite instanceof Empty && direction != getOppositeDirection(getDirection())) {
						startMove(direction);
						break;
					}
				}
			}
			startMove(getOppositeDirection(getDirection()));
		}
		
	}

	private Direction getOppositeDirection(Direction dir) {
		Direction direction = null;
		switch (dir) {
		case UP:
			direction = Direction.DOWN;
			break;
		case DOWN:
			direction = Direction.UP;
			break;
		case LEFT: 
			direction = Direction.RIGHT;
		case RIGHT:
			direction = Direction.LEFT;
		case STOP:
			direction = Direction.values()[new Random().nextInt(Direction.values().length)];
		default:
			break;
		}
		return direction;
	}

	private void placeBomb(int x, int y) {
		int posX = (int)GameState.spriteList.get(y).get(x).getPosition().getX();
		int posY = (int)GameState.spriteList.get(y).get(x).getPosition().getY();
		if (this.canPlaceBomb()) {
			Bomb bomb = new Bomb(posX, posY, this.getMagnitude(), this.gameState);
			this.gameState.addBomb(bomb);
			this.addBomb(bomb);
			this.placedBomb = System.currentTimeMillis();
			evadeBomb();
		}

	}

	private void startMove(Direction direction) {
		this.setDirection(direction);
		this.setSpeed(
				(150 * Constants.getReceivingXRatio() * this.getPlayerSpeed())
						* direction.getX(),
				(150 * Constants.getReceivingXRatio() * this.getPlayerSpeed())
						* direction.getY());
	}

	public void update(float dt) {
		selectNextMove();
		playerCollision();
		super.update(dt);
	}

	private boolean needToEvade() {
		if (System.currentTimeMillis() - this.placedBomb < 5000) {
			return true;
		}
		changedColumn = false;
		changedRow = false;
		return false;
	}

	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	
	public void handleCollision(Direction dir,int y, int x, float posX, float posY){
		Sprite sprite = this.gameState.spriteList.get(y+dir.getY()).get(x+dir.getX());
		if(sprite instanceof Empty){
			setPosition(posX, posY);
		}
		else{
			setDirection(Direction.STOP);
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
		Sprite thisSprite = this.gameState.spriteList.get(y).get(x);
		float posX = thisSprite.getX()+diff;
		float posY = thisSprite.getY()+diff;
		if(!canPlayerMove(Direction.UP) && getDirection() == Direction.UP)
			handleCollision(Direction.UP, y, x, posX, posY);

		if(!canPlayerMove(Direction.DOWN) && getDirection() == Direction.DOWN)
			handleCollision(Direction.DOWN, y, x, posX, posY);
//			player.setSpeed(player.getSpeed().getX(), 0);
		
		if(!canPlayerMove(Direction.LEFT) && getDirection() == Direction.LEFT)
			handleCollision(Direction.LEFT, y, x, posX, posY);
//			player.setSpeed(0, player.getSpeed().getY());
		
		if(!canPlayerMove(Direction.RIGHT) && getDirection() == Direction.RIGHT)
			handleCollision(Direction.RIGHT, y, x, posX, posY);
//			player.setSpeed(0, player.getSpeed().getY());
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
		Sprite sprite = this.gameState.spriteList.get(y+dir.getY()).get(x+dir.getX());
		if(dir == Direction.DOWN || dir == Direction.UP){
			if(canMoveY()){
				if(sprite instanceof Empty){
					if(!this.gameState.bombAtPosition(x+dir.getX(), y+dir.getY())) return true;
				}
			}
			float pixelsY = getPixelsY(dir, sprite);
			if(pixelsY > Constants.COLLSION_RANGE * Constants.getReceivingYRatio())
				return true;
			if(this.gameState.bombAtPosition(x+dir.getX(),y+dir.getY())&& getDirection() == dir) {
				this.gameState.kickBomb(x +dir.getX(),y +dir.getY(),getDirection());
			}
			return false;
		}
		else{
			if(canMoveX()){
				if(sprite instanceof Empty){
					if(!this.gameState.bombAtPosition(x+dir.getX(), y+dir.getY())) return true;
				}
			}
			float pixelsX = getPixelsX(dir, sprite);
			if(pixelsX > Constants.COLLSION_RANGE * Constants.getReceivingYRatio())
				return true;
			if(this.gameState.bombAtPosition(x+dir.getX(),y+dir.getY()) && getDirection()==dir) {
				this.gameState.kickBomb(x+dir.getX(),y+dir.getY(),getDirection());
			}
			return false;
		}

	}

}
