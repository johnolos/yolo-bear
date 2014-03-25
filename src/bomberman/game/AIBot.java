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
	private ArrayList<Player> opponents;
	
	private boolean changedRow = false;
	private boolean changedColumn = false;
	private int posX= 0;
	private int posY = 0;
	private long turnWait = 0;

	public AIBot(String name, ColorObject color, GameState gs) {
		super(name, color, gs);
		this.gameState = gs;
		this.random = new Random();
	}
	
	public void addAllBots(ArrayList<Player> opponents){
		this.opponents = opponents;
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
				Sprite sprite = gameState.getSpriteBoard().get(y + direction.getY())
						.get(x + direction.getX());
				if (sprite instanceof Empty) {
					possibleDir.add(direction);
				}
			}
		}
		if(!possibleDir.isEmpty()){
			Direction dir = possibleDir.get(random.nextInt(possibleDir.size()));
				if(changeingRow(dir)){
					changedRow = true;
				}
				else{
					changedColumn = true;
				}
		startMove(dir);
		}
		}
		else if(changedColumn){
			for(Direction direction : Direction.values()){
				if(changeingRow(direction)){
					Sprite sprite = gameState.getSpriteBoard().get(y + direction.getY()).get(x + direction.getX());
					if (sprite instanceof Empty) {
						possibleDir.add(direction);
					}	
				}
			}
			if(possibleDir.size() != 0){
				Direction dir = possibleDir.get(random.nextInt(possibleDir.size()));
				changedRow = true;
				startMove(dir);
			}
		}
		else{
			for(Direction direction : Direction.values()){
				if(changeingColumn(direction)){
					Sprite sprite = gameState.getSpriteBoard().get(y + direction.getY()).get(x + direction.getX());
					if (sprite instanceof Empty) {
						possibleDir.add(direction);
					}	
				}
			}
			if(possibleDir.size() != 0){
				Direction dir = possibleDir.get(random.nextInt(possibleDir.size()));
				changedColumn = true;
				startMove(dir);
			}
		}
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
		else{
			setGridPos();
		}
	}

	private boolean notMoving() {
		return (this.getSpeed().getX() == 0 && this.getSpeed().getY() == 0);
	}

	private void checkPlaceBomb() {
		int x = Constants.getPositionX(this.getMiddleX());
		int y = Constants.getPositionY(this.getMiddleY());
		for (Direction direction : Direction.values()) {
			Sprite sprite = gameState.getSpriteBoard().get(y + direction.getY()).get(
					x + direction.getX());
			if (sprite instanceof Crate || opponentNear()) {
				placeBomb(x, y);
				break;
			}
			findMove();

		}
	}

	private boolean opponentNear() {
		int myPosX = Constants.getPositionX(this.getMiddleX());
		int myPosY = Constants.getPositionY(this.getMiddleY());
		for(Player opponent : opponents){
			if(opponent != this){
				int oppX = Constants.getPositionX(opponent.getMiddleX());
				int oppY = Constants.getPositionY(opponent.getMiddleY());
				if(myPosX == oppX && Math.abs(myPosY-oppY) <this.getMagnitude()  || myPosY == oppY && Math.abs(myPosX -oppX) < this.getMagnitude()){
					return true;
				}
			}
		}
		return false;
	}

	private void findMove() {
		if (notMoving()) {
			int x = Constants.getPositionX(this.getMiddleX());
			int y = Constants.getPositionY(this.getMiddleY());
			for (Direction direction : Direction.values()) {
				if (direction != Direction.STOP) {
					Sprite sprite = gameState.getSpriteBoard().get(
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
		int posX = (int)gameState.getSpriteBoard().get(y).get(x).getPosition().getX();
		int posY = (int)gameState.getSpriteBoard().get(y).get(x).getPosition().getY();
		if (this.canPlaceBomb()) {
			Bomb bomb = new Bomb(posX, posY, this.getMagnitude(), this.gameState,getColor());
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

	/**
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * This function also calls the update function in the super class, and is not suppose to update the view if is dead is true
	 * @paramc dt
	 */
	public void update(float dt) {
		if(!this.isDead()){
			selectNextMove();
			super.update(dt);
		}
	}

	private void setGridPos() {
		int newPosX = Constants.getPositionX(this.getMiddleX());
		int newPosY = Constants.getPositionY(this.getMiddleY());
		if(newPosX != this.posX || newPosY != this.posY){
			turn();
			this.posX = newPosX;
			this.posY = newPosY;
		}
		
	}

	private void turn(){
		if(System.currentTimeMillis()-turnWait  > 2000){
			int x = Constants.getPositionX(this.getMiddleX());
			int y = Constants.getPositionY(this.getMiddleY());
			ArrayList<Direction> directions = new ArrayList<Direction>();
			for (Direction direction : Direction.values()) {
				if (direction != Direction.STOP) {
					Sprite sprite = gameState.getSpriteBoard().get(
							y + direction.getY()).get(x + direction.getX());
					if (sprite instanceof Empty && direction != getOppositeDirection(getDirection())) {
						directions.add(direction);
					}
				}
			}
			if(!directions.isEmpty()){
				int index = random.nextInt(directions.size());
				startMove(directions.get(index));
				turnWait = System.currentTimeMillis();
			}
			if(opponentNear() || createNear(x, y)){
				placeBomb(x, y);
			}
		}
	}

	private boolean createNear(int x, int y) {
		for(Direction direction : Direction.values()){
			Sprite sprite = gameState.getSpriteBoard().get(y+ direction.getY()).get(x+direction.getX());
			if(sprite instanceof Crate){
				return true;
			}
		}
		return false;
	}

	private boolean needToEvade() {
		if (System.currentTimeMillis() - this.placedBomb < 5000) {
			return true;
		}
		changedColumn = false;
		changedRow = false;
		return false;
	}

	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * This draw calls the draw function in the super class
	 * @param canvas which you draw on.
	 */
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

}
