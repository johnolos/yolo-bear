package bomberman.game;

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

	public Bomb(int x, int y, int blastRadius, GameState gs, ColorObject color) {
		this.color = color;
		this.column = Constants.getPositionX(x);
		this.row = Constants.getPositionY(y);
		explodeImages = new Image[4];
		this.blastRadius = blastRadius;
		this.gs = gs;
		if (Constants.screenHeight == 1600) {
			this.bomb = new Image(R.drawable.bomb);
		} else if (Constants.screenHeight == 752) {
			this.bomb = new Image(R.drawable.bomb);
		} else {
			this.bomb = new Image(R.drawable.smallbomb);
		}
		this.direction = Direction.STOP;
		this.setPosition(x, y);
		this.setView(bomb);
	}

	public void bombAnimation() {
		if (System.currentTimeMillis() - time >= 2000 && !this.exploded
				&& !this.phase2) {
			initiated = true;
			bomb = new Image(R.drawable.bombphase2);
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
	 * This updates the view, if there is any changes in the view of the different buttons this function updates it.
	 * This function also calls the update function in the super class, and updates some other things that are set in this class
	 * @param dt
	 */
	public void update(float dt) {
		bombAnimation();
		checkWallCollision();
		column = Constants.getPositionX(getX());
		row = Constants.getPositionY(getY());
		super.update(dt);
	}

	private void checkWallCollision() {
		int x = Constants.getPositionX(this.getX());
		int y = Constants.getPositionY(this.getY());
		if (gs.getSpriteBoard().get(y).get(x) instanceof Wall) {
			this.finished = true;
			setView(null);
		}

	}

	/**
	 * Draw function which draws the things onto the canvas, and draws the updated images onto the canvas.
	 * This draw calls the draw function in the super class
	 * @param canvas which you draw on.
	 */
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	public double getTime() {
		return this.time;
	}

	public boolean finished() {
		return this.finished;
	}

	public int getBlastRadius() {
		return this.blastRadius;
	}

	@Override
	public boolean collision(int x, int y) {
		return (this.column == x && this.row == y);
	}

	public void setDirection(Direction dir) {
		this.direction = dir;
	}

	public Direction getDirection() {
		return this.direction;
	}

	public boolean initiated() {
		return this.initiated;
	}

	public int getColumn() {
		return this.column;
	}

	public void setColum(int column) {
		this.column = column;

	}

	public int getRow() {
		return this.row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void updatePosition() {
		float x = this.column * Constants.getHeight()
				+ Constants.getPixelsOnSides();
		float y = this.row * Constants.getHeight();
		setPosition(x, y);
	}

	public void bombThrown(Direction direction) {
		int x = getColumn() + direction.getX();
		int y = getRow() + direction.getY();
		if (x == (Board.COLUMN_SIZE - 1)) {
			x = 0;
		} else if (x == 0) {
			x = Board.COLUMN_SIZE - 1;
		}
		if(y == (Board.ROW_SIZE - 1)) {
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
	 * Refactoring should be considered.
	 * 
	 */
	public void bombImpact() {
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
		int i = 0;
		Sprite sprite;
		for (int column = x - 1; column >= 0; column--) {
			if (i == blastRadius)
				break;
			sprite = gs.getSpriteBoard().get(y).get(column);
			float xPixel = sprite.getPosition().getX();
			float yPixel = sprite.getPosition().getY();
			if (sprite instanceof Wall) {
				break;
			} else if (sprite instanceof Crate || i + 1 == blastRadius) {
				gs.checkPlayerHit(xPixel, yPixel);
				gs.addExplosion(new Explosion(xPixel, yPixel,
						BombImages.bombExplosionEndLeft));
				Empty empty = new Empty();
				gs.setSprite(column, y, empty);
				if (getColor() == gs.getPlayer().getColor()) {
					gs.randomPlacePowerUp(column, y);

				}
				break;
			} else if (sprite instanceof Empty) {
				gs.checkPlayerHit(xPixel, yPixel);
				if (gs.getSpriteBoard().get(y).get(column - 1) instanceof Wall) {
					gs.addExplosion(new Explosion(xPixel, yPixel,
							BombImages.bombExplosionEndLeft));
				} else {
					gs.addExplosion(new Explosion(xPixel, yPixel,
							BombImages.bombExplosionMidLeft));
				}
			}
			i++;
		}
		i = 0;
		for (int column = x + 1; column <= 12; column++) {
			if (i == blastRadius)
				break;
			sprite = gs.getSpriteBoard().get(y).get(column);
			float xPixel = sprite.getPosition().getX();
			float yPixel = sprite.getPosition().getY();
			if (sprite instanceof Wall) {
				break;
			} else if (sprite instanceof Crate || i + 1 == blastRadius) {
				gs.checkPlayerHit(xPixel, yPixel);
				gs.addExplosion(new Explosion(xPixel, yPixel,
						BombImages.bombExplosionEndRight));
				Empty empty = new Empty();
				gs.setSprite(column, y, empty);
				if (getColor() == gs.getPlayer().getColor()) {
					gs.randomPlacePowerUp(column, y);

				}

				break;
			} else if (sprite instanceof Empty) {
				gs.checkPlayerHit(xPixel, yPixel);
				if (gs.getSpriteBoard().get(y).get(column + 1) instanceof Wall) {
					gs.addExplosion(new Explosion(xPixel, yPixel,
							BombImages.bombExplosionEndRight));
				} else {
					gs.addExplosion(new Explosion(xPixel, yPixel,
							BombImages.bombExplosionMidRight));
				}
			}
			i++;
		}
		i = 0;
		for (int row = y - 1; row >= 0; row--) {
			if (i == blastRadius)
				break;
			sprite = gs.getSpriteBoard().get(row).get(x);
			float xPixel = sprite.getPosition().getX();
			float yPixel = sprite.getPosition().getY();
			if (sprite instanceof Wall) {
				break;
			} else if (sprite instanceof Crate || i + 1 == blastRadius) {
				gs.checkPlayerHit(xPixel, yPixel);
				gs.addExplosion(new Explosion(xPixel, yPixel,
						BombImages.bombExplosionEndUp));
				Empty empty = new Empty();
				gs.setSprite(x, row, empty);
				if (getColor() == gs.getPlayer().getColor()) {
					gs.randomPlacePowerUp(x, row);

				}
				break;
			} else if (sprite instanceof Empty) {
				gs.checkPlayerHit(xPixel, yPixel);
				if (gs.getSpriteBoard().get(row - 1).get(x) instanceof Wall) {
					gs.addExplosion(new Explosion(xPixel, yPixel,
							BombImages.bombExplosionEndUp));
				} else {
					gs.addExplosion(new Explosion(xPixel, yPixel,
							BombImages.bombExplosionMidUp));
				}
			}
			i++;
		}
		i = 0;
		for (int row = y + 1; row <= 12; row++) {
			if (i == blastRadius)
				break;
			sprite = gs.getSpriteBoard().get(row).get(x);
			float xPixel = sprite.getPosition().getX();
			float yPixel = sprite.getPosition().getY();
			if (sprite instanceof Wall) {
				break;
			} else if (sprite instanceof Crate || i + 1 == blastRadius) {
				gs.checkPlayerHit(xPixel, yPixel);
				gs.addExplosion(new Explosion(xPixel, yPixel,
						BombImages.bombExplosionEndDown));
				Empty empty = new Empty();
				gs.setSprite(x, row, empty);
				if (getColor() == gs.getPlayer().getColor()) {
					gs.randomPlacePowerUp(x, row);

				}
				break;
			} else if (sprite instanceof Empty) {
				gs.checkPlayerHit(xPixel, yPixel);
				if (gs.getSpriteBoard().get(row + 1).get(x) instanceof Wall) {
					gs.addExplosion(new Explosion(xPixel, yPixel,
							BombImages.bombExplosionEndDown));
				} else {
					gs.addExplosion(new Explosion(xPixel, yPixel,
							BombImages.bombExplosionMidDown));
				}
			}
			i++;
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
					addEdgePicture(yPixel, xPixel, dir);
					gs.checkPlayerHit(xPixel, yPixel);
					gs.checkPowerUpHit(column, row);
					Empty empty = new Empty();
					gs.setSprite(column, row, empty);
					if (getColor() == gs.getPlayer().getColor()) {
						gs.randomPlacePowerUp(column, row);
					}
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
				if(isEdgeOfExplosion(row, column, dir) || i == blastRadius) {
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

	private boolean isEdgeOfExplosion(int y, int x, Direction dir) {
		Sprite sprite = gs.getSpriteBoard().get(y + dir.getY())
				.get(x + dir.getX());
		if (sprite instanceof Wall) {
			return true;
		}
		return false;

	}

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

	public ColorObject getColor() {
		return this.color;
	}

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