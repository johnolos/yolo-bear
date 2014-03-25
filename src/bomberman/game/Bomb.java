package bomberman.game;
/**
 * Extends Sprite implements Collision
 */
import bomberman.graphics.BombImages;
import bomberman.states.GameState;
import sheep.game.Sprite;
import sheep.graphics.Image;
import android.graphics.Canvas;

public class Bomb extends Sprite implements Collision {
	private int blastRadius; // the "length" of the explosion
	private Image bomb;
	private double time = System.currentTimeMillis();
	private double explodedTime;
	private boolean exploded = false;
	private boolean phase2 = false;
	private boolean finished = false;
	private GameState gs;
	private Image[] explodeImages;
	private int column, row;
	private Direction direction;
	private boolean initiated = false;
	private ColorObject color;
	public boolean isSuperBomb;

	/**
	 * The constructor
	 * @param x
	 * @param y
	 * @param blastRadius
	 * @param gs
	 * @param color
	 * @param superBomb
	 */
	public Bomb(int x, int y, int blastRadius, GameState gs, ColorObject color,
			boolean superBomb) {
		this.isSuperBomb = superBomb;
		this.color = color;
		this.column = Constants.getPositionX(x);
		this.row = Constants.getPositionY(y);
		explodeImages = new Image[4];
		this.blastRadius = blastRadius;
		this.gs = gs;
		if (Constants.screenHeight == 1600) {
			if (this.isSuperBomb)
				this.bomb = new Image(R.drawable.superbombangry);
			else
				this.bomb = new Image(R.drawable.bomb);
		} else if (Constants.screenHeight == 752) {
			if (this.isSuperBomb)
				this.bomb = new Image(R.drawable.superbombangry);
			else
				this.bomb = new Image(R.drawable.bomb);
		} else {
			if (this.isSuperBomb)
				this.bomb = new Image(R.drawable.superbombangry);
			else
				this.bomb = new Image(R.drawable.smallbomb);
		}
		this.direction = Direction.STOP;
		this.setPosition(x, y);
		this.setView(bomb);
	}

	/**
	 * The bombanimation
	 */
	public void bombAnimation() {
		if (System.currentTimeMillis() - time >= 2000 && !this.exploded
				&& !this.phase2) {
			initiated = true;
			if (this.isSuperBomb)
				this.bomb = new Image(R.drawable.superbombphase2);
			else
				this.bomb = new Image(R.drawable.bombphase2);
			setView(bomb);
			phase2 = true;
		} else if (System.currentTimeMillis() - time >= 3000 && !this.exploded) {
			exploded = true;
			phase2 = false;
			bombImpactRefactored();
			explodedTime = System.currentTimeMillis();
		} else if (System.currentTimeMillis() - time < 4000 && this.exploded) {
			explodeAnimation();
		} else if (System.currentTimeMillis() - time >= 5000)
			this.finished = true;
		// gs.getBombs().remove(this);
	}

	public void explodeAnimation() {
		if (System.currentTimeMillis() - explodedTime >= 250
				&& System.currentTimeMillis() - explodedTime < 500) {
			bomb = explodeImages[1];
			setView(bomb);
		} else if (System.currentTimeMillis() - explodedTime >= 500
				&& System.currentTimeMillis() - explodedTime < 750) {
			bomb = explodeImages[2];
			setView(bomb);
		} else if (System.currentTimeMillis() - explodedTime >= 750
				&& System.currentTimeMillis() - explodedTime < 1000) {
			bomb = explodeImages[3];
			setView(bomb);
		}
	}

	/**
	 * This updates the view, if there is any changes in the view of the
	 * different buttons this function updates it. This function also calls the
	 * update function in the super class, and updates some other things that
	 * are set in this class
	 * @param dt
	 */
	public void update(float dt) {
		bombAnimation();
		checkWallCollision();
		column = Constants.getPositionX(getX());
		row = Constants.getPositionY(getY());
		super.update(dt);
	}

	/**
	 * Check for wallCollision when bomb explode
	 */
	private void checkWallCollision() {
		int x = Constants.getPositionX(this.getX());
		int y = Constants.getPositionY(this.getY());
		if (gs.getSpriteBoard().get(y).get(x) instanceof Wall) {
			this.finished = true;
			setView(null);
		}

	}

	/**
	 * Draw function which draws the things onto the canvas, and draws the
	 * updated images onto the canvas. This draw calls the draw function in the
	 * super class
	 * 
	 * @param canvas which you draw on.
	 */
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	/**
	 * Get bomb time
	 * @return the bomb time
	 */
	public double getTime() {
		return this.time;
	}

	/**
	 * Is  bomb finished
	 * @return true if bomb is finished
	 */
	public boolean finished() {
		return this.finished;
	}

	/**
	 * Get the blast radius of the bomb
	 * @return a int which is the blast radius of bomb
	 */
	public int getBlastRadius() {
		return this.blastRadius;
	}

	/**
	 * check for collision with wall
	 * @return true if collision else false
	 */
	@Override
	public boolean collision(int x, int y) {
		return (this.column == x && this.row == y);
	}

	/**
	 * Set direction for blast of bomb
	 * @param dir the direction of blast
	 */
	public void setDirection(Direction dir) {
		this.direction = dir;
	}

	/**
	 * Get the direction of blast
	 * @return the direction of blast
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * Is bomb initiated
	 * @return true if bomb is initiated else false
	 */
	public boolean initiated() {
		return this.initiated;
	}

	/**
	 * Get the column which the bomb is placed
	 * @return the column the bomb is placed
	 */
	public int getColumn() {
		return this.column;
	}

	/**
	 * Set the column the bomb was placed in
	 * @param column which the bomb was placed
	 */
	public void setColum(int column) {
		this.column = column;

	}

	/**
	 * Get the row the bomb was placed in
	 * @return the row
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * Set the row which the bomb was placed in
	 * @param row where the bomb was placed
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Update position of bomb
	 */
	public void updatePosition() {
		float x = this.column * Constants.getHeight()
				+ Constants.getPixelsOnSides();
		float y = this.row * Constants.getHeight();
		setPosition(x, y);
	}

	/**
	 * If bomb is thrown which direction was it thrown and where to put it
	 * @param direction the bomb was thrown
	 */
	public void bombThrown(Direction direction) {
		int x = getColumn() + direction.getX();
		int y = getRow() + direction.getY();
		if (x == (Board.COLUMN_SIZE - 1)) {
			x = 0;
		} else if (x == 0) {
			x = Board.COLUMN_SIZE - 1;
		}
		if (y == (Board.ROW_SIZE - 1)) {
			y = 0;
		} else if (y == 0) {
			y = Board.ROW_SIZE - 1;
		}
		Sprite sprite = gs.getSpriteBoard().get(y).get(x);
		if (sprite instanceof Empty) {
			if (gs.bombAtPosition(x, y)) {
				setColum(x);
				setRow(y);
				updatePosition();
				bombThrown(direction);
			} else {
				setColum(x);
				setRow(y);
				updatePosition();
			}
		} else {
			setColum(x);
			setRow(y);
			updatePosition();
			bombThrown(direction);
		}
	}

	/**
	 * Evaluates a bomb and removes the creates it finds in its impact range.
	 * 
	 */
	public void bombImpactRefactored() {
		int blastRadius = getBlastRadius();
		int x = Constants.getPositionX(getPosition().getX()
				+ Constants.getHeight() / 2);
		int y = Constants.getPositionY(getPosition().getY()
				+ Constants.getHeight() / 2);
		float pixelX = gs.getSpriteBoard().get(y).get(x).getX();
		float pixelY = gs.getSpriteBoard().get(y).get(x).getY();
		gs.addExplosion(new Explosion(pixelX, pixelY,
				BombImages.bombExplosionCenter));
		gs.checkPlayerHit(pixelX, pixelY);
		boolean up = true, down = true, left = true, right = true;
		int i = 1;
		Sprite sprite;
		while (up || down || left || right) {
			if (i > blastRadius)
				break;
			for (Direction dir : Direction.values()) {
				if (dir == Direction.STOP)
					continue;
				if (dir == Direction.DOWN && down == false) {
					continue;
				}
				if (dir == Direction.UP && up == false) {
					continue;
				}
				if (dir == Direction.LEFT && left == false) {
					continue;
				}
				if (dir == Direction.RIGHT && right == false) {
					continue;
				}
				int row = i * dir.getY() + y;
				int column = i * dir.getX() + x;
				sprite = gs.getSpriteBoard().get(row).get(column);
				float xPixel = sprite.getPosition().getX();
				float yPixel = sprite.getPosition().getY();
				// Last tile of bomb impact
				if (sprite instanceof Wall) {
					if (dir == Direction.UP)
						up = false;
					else if (dir == Direction.DOWN)
						down = false;
					else if (dir == Direction.LEFT)
						left = false;
					else if (dir == Direction.RIGHT)
						right = false;
					continue;
				}
				if (sprite instanceof Crate) {
					gs.checkPlayerHit(xPixel, yPixel);
					gs.checkPowerUpHit(column, row);
					Empty empty = new Empty();
					gs.setSprite(column, row, empty);
					if (getColor() == gs.getPlayer().getColor()) {
						gs.randomPlacePowerUp(column, row);
					}
					if(isSuperBomb) {
						if(isEdgeOfExplosion(row, column, dir))
							addEdgePicture(yPixel, xPixel, dir);
						else
							addMiddlePicture(yPixel, xPixel, dir); 
						continue;
					}
					addEdgePicture(yPixel, xPixel, dir);
					if (dir == Direction.UP)
						up = false;
					else if (dir == Direction.DOWN)
						down = false;
					else if (dir == Direction.LEFT)
						left = false;
					else if (dir == Direction.RIGHT)
						right = false;
					continue;
				}
				if (isEdgeOfExplosion(row, column, dir) || i == blastRadius) {
					addEdgePicture(yPixel, xPixel, dir);
					gs.checkPlayerHit(xPixel, yPixel);
					gs.checkPowerUpHit(column, row);
					if (dir == Direction.UP)
						up = false;
					else if (dir == Direction.DOWN)
						down = false;
					else if (dir == Direction.LEFT)
						left = false;
					else if (dir == Direction.RIGHT)
						right = false;
					continue;
				} else {
					addMiddlePicture(yPixel, xPixel, dir);
					gs.checkPlayerHit(xPixel, yPixel);
					gs.checkPowerUpHit(column, row);
					continue;
				}
			}
			i++;
		}
	}

	/**
	 * Is this the edge of the explosion
	 * @param y
	 * @param x
	 * @param dir
	 * @return true if edge else false
	 */
	private boolean isEdgeOfExplosion(int y, int x, Direction dir) {
		Sprite sprite = gs.getSpriteBoard().get(y + dir.getY())
				.get(x + dir.getX());
		if (sprite instanceof Wall) {
			return true;
		}
		return false;

	}

	/**
	 * adds edge picture to the explosion radius 
	 * @param y
	 * @param x
	 * @param dir
	 */
	private void addEdgePicture(float y, float x, Direction dir) {
		switch (dir) {
		case DOWN:
			gs.addExplosion(new Explosion(x, y, BombImages.bombExplosionEndDown));
			break;
		case UP:
			gs.addExplosion(new Explosion(x, y, BombImages.bombExplosionEndUp));
			break;
		case RIGHT:
			gs.addExplosion(new Explosion(x, y,
					BombImages.bombExplosionEndRight));
			break;
		case LEFT:
			gs.addExplosion(new Explosion(x, y, BombImages.bombExplosionEndLeft));
			break;
		default:
		}
	}

	/**
	 * adds middle picture of explosion radius
	 * @param y
	 * @param x
	 * @param dir
	 */
	private void addMiddlePicture(float y, float x, Direction dir) {
		switch (dir) {
		case DOWN:
			gs.addExplosion(new Explosion(x, y, BombImages.bombExplosionMidDown));
			break;
		case UP:
			gs.addExplosion(new Explosion(x, y, BombImages.bombExplosionMidUp));
			break;
		case RIGHT:
			gs.addExplosion(new Explosion(x, y,
					BombImages.bombExplosionMidRight));
			break;
		case LEFT:
			gs.addExplosion(new Explosion(x, y, BombImages.bombExplosionMidLeft));
			break;
		default:
		}
	}

	/**
	 * Get the color of the bomb
	 * @return the color object of bomb
	 */
	public ColorObject getColor() {
		return this.color;
	}

	/**
	 * Is the bomb kicked and which direction was it kicked
	 * @param direction of the kick
	 */
	public void bombKicked(Direction direction) {
		switch (direction) {
		case UP:
			setSpeed(0, -150 * Constants.getReceivingXRatio());
			setDirection(Direction.UP);
			break;
		case DOWN:
			setSpeed(0, 150 * Constants.getReceivingXRatio());
			setDirection(Direction.DOWN);
			break;
		case RIGHT:
			setSpeed(150 * Constants.getReceivingXRatio(), 0);
			setDirection(Direction.RIGHT);
			break;
		case LEFT:
			setSpeed(-150 * Constants.getReceivingXRatio(), 0);
			setDirection(Direction.LEFT);
			break;
		default:
			break;
		}
	}

	/**
	 * Check if the bomb collides with the wall in if it does stop it, make it stay on map
	 */
	public void checkBombCollision() {
		if (getDirection() != Direction.STOP) {
			int x = Constants.getPositionX(getX() + Constants.getHeight() / 2);
			int y = Constants.getPositionY(getY() + Constants.getHeight() / 2);
			Direction dir = getDirection();
			if (!(gs.getSpriteBoard().get(y + dir.getY()).get(x + dir.getX()) instanceof Empty)) {
				setSpeed(0, 0);
				setPosition(gs.getSpriteBoard().get(y).get(x).getX(), gs
						.getSpriteBoard().get(y).get(x).getY());
			}
		}
	}

}