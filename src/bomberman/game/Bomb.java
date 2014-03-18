package bomberman.game;

import bomberman.graphics.BombImages;
import bomberman.states.GameState;
import sheep.game.Game;
import sheep.game.Sprite;
import sheep.graphics.Image;
import android.graphics.Canvas;

public class Bomb extends Sprite implements Collision{
	
	private int blastRadius; //the "length" of the explosion
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
	private Board board; 
	
	public Bomb(int x, int y, int blastRadius, GameState gs){
		this.column = Constants.getPositionX(x);
		this.row = Constants.getPositionY(y);
		this.setPosition(x, y);
		explodeImages = new Image[4];
		this.blastRadius = blastRadius;
		this.gs = gs;
		if(Constants.screenHeight == 1600){
			this.bomb = new Image(R.drawable.bomblarge);
			this.setShape(120,120);
		}
		else if(Constants.screenHeight == 752){
			this.bomb = new Image(R.drawable.bomblarge);
			this.setShape(60,60);
		}
		else{
			this.bomb = new Image(R.drawable.bomb);
			this.setShape(40,40);
		}
		this.direction = Direction.STOP;
		this.setView(bomb);
	}
	
	public void bombAnimation(){
		if(System.currentTimeMillis() - time >= 2000 && !this.exploded && !this.phase2){
			initiated = true;
			bomb = new Image(R.drawable.bombphase2);
			setView(bomb);
			phase2 = true;
		}
		else if(System.currentTimeMillis() - time >= 3000 && !this.exploded){
			bomb = new Image(R.drawable.explode);
			setView(bomb);
			exploded = true;
			phase2 = false;
			bombImpact();
			explodedTime = System.currentTimeMillis();
		}
		else if(System.currentTimeMillis() - time < 4000 && this.exploded){
			explodeAnimation();
		}
		else if(System.currentTimeMillis() - time >= 5000)
			this.finished = true;
//			gs.getBombs().remove(this);
	}
	
	public void explodeAnimation() {
		if(System.currentTimeMillis() - explodedTime >= 250 && System.currentTimeMillis() - explodedTime < 500){
			bomb = explodeImages[1];
			setView(bomb);
		}
		else if(System.currentTimeMillis() - explodedTime >= 500 && System.currentTimeMillis() - explodedTime < 750){
			bomb = explodeImages[2];
			setView(bomb);
		}
		else if(System.currentTimeMillis() - explodedTime >= 750 && System.currentTimeMillis() - explodedTime < 1000){
			bomb = explodeImages[3];
			setView(bomb);
		}
	}	
	
	public void update(float dt){
		super.update(dt);
		bombAnimation();
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
	
	public double getTime(){
		return this.time;
	}
	
	public boolean finished(){
		return this.finished;
	}
	
	public int getBlastRadius() {
		return this.blastRadius;
	}

	@Override
	public boolean collision(int x, int y) {
		return (this.column == x && this.row == y);
	}
	
	public void setDirection(Direction dir){
		this.direction = dir;
	}
	
	public Direction getDirection(){
		return this.direction;
	}
	
	public boolean initiated(){
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
		float x = this.column*Constants.getHeight() + Constants.getPixelsOnSides();
		float y = this.row*Constants.getHeight();
		setPosition(x, y);
	}
	
	public void bombThrown(Direction direction) {
		int x = getColumn() + direction.getX();
		int y = getRow() + direction.getY();
		if(x == (Board.COLUMN_SIZE - 1)) {
			x = 0;
		}
		else if(x == 0) {
			x = Board.COLUMN_SIZE - 1;
		}
		if(y == (Board.ROW_SIZE - 1)) {
			System.out.println("Shit got real");
			y = 0;
		}
		else if(y == 0) {
			y = Board.ROW_SIZE - 1;
		}
		Sprite sprite = gs.getSpriteBoard().get(y).get(x);
		if(sprite instanceof Empty) {
			if(gs.bombAtPosition(x, y)) {
				System.out.println("This happens");
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
	 * @param bomb
	 */
	public void bombImpact(){
		int blastRadius = getBlastRadius();
		int x = Constants.getPositionX(getPosition().getX()+Constants.getHeight()/2);
		int y = Constants.getPositionY(getPosition().getY()+Constants.getHeight()/2);
		float pixelX = gs.getSpriteBoard().get(y).get(x).getX();
		float pixelY = gs.getSpriteBoard().get(y).get(x).getY();
		gs.addExplosion(new Explosion(pixelX, pixelY, BombImages.bombExplosionCenter));
		gs.checkPlayerHit(pixelX, pixelY);
		int i = 0;
		Sprite sprite;
		for(int column = x-1; column>=0; column-- ){
			if(i == blastRadius)
				break;
			sprite = gs.getSpriteBoard().get(y).get(column);
			float xPixel = sprite.getPosition().getX();
			float yPixel = sprite.getPosition().getY();
			if(sprite instanceof Wall){
				break;
			}
			else if(sprite instanceof Crate || i+1 == blastRadius){
				gs.checkPlayerHit(xPixel, yPixel);
				gs.addExplosion(new Explosion(xPixel,yPixel, BombImages.bombExplosionEndLeft));
				Empty empty = new Empty();
				gs.setSprite(column, y, empty);
				gs.maybeCreatePowerUp(column, y);
				break;
			}
			else if(sprite instanceof Empty){
				gs.checkPlayerHit(xPixel, yPixel);
				if(gs.getSpriteBoard().get(y).get(column-1) instanceof Wall){
					gs.addExplosion(new Explosion(xPixel,yPixel, BombImages.bombExplosionEndLeft));
				}
				else{
					gs.addExplosion(new Explosion(xPixel,yPixel, BombImages.bombExplosionMidLeft));
				}
			}
			i++;
		}
		i = 0;
		for(int column = x+1; column<=12; column++ ){
			if(i == blastRadius)
				break;
			sprite = gs.getSpriteBoard().get(y).get(column);
			float xPixel = sprite.getPosition().getX();
			float yPixel = sprite.getPosition().getY();
			if(sprite instanceof Wall){
				break;
			}
			else if(sprite instanceof Crate || i+1 == blastRadius){
				gs.checkPlayerHit(xPixel, yPixel);
				gs.addExplosion(new Explosion(xPixel,yPixel, BombImages.bombExplosionEndRight));
				Empty empty = new Empty();
				gs.setSprite(column, y, empty);
				gs.maybeCreatePowerUp(column, y);
				break;
			}
			else if(sprite instanceof Empty){
				gs.checkPlayerHit(xPixel, yPixel);
				if(gs.getSpriteBoard().get(y).get(column+1) instanceof Wall){
					gs.addExplosion(new Explosion(xPixel,yPixel, BombImages.bombExplosionEndRight));
				}
				else{
					gs.addExplosion(new Explosion(xPixel,yPixel, BombImages.bombExplosionMidRight));
				}
			}
			i++;
		}
		i = 0;
		for(int row = y-1; row>=0; row-- ){
			if(i == blastRadius)
				break;
			sprite = gs.getSpriteBoard().get(row).get(x);
			float xPixel = sprite.getPosition().getX();
			float yPixel = sprite.getPosition().getY();
			if(sprite instanceof Wall){
				break;
			}
			else if(sprite instanceof Crate || i+1 == blastRadius){
				gs.checkPlayerHit(xPixel, yPixel);
				gs.addExplosion(new Explosion(xPixel,yPixel, BombImages.bombExplosionEndUp));
				Empty empty = new Empty();
				gs.setSprite(x, row, empty);
				gs.maybeCreatePowerUp(x, row);
				break;
			}
			else if(sprite instanceof Empty){
				gs.checkPlayerHit(xPixel, yPixel);
				if(gs.getSpriteBoard().get(row-1).get(x) instanceof Wall){
					gs.addExplosion(new Explosion(xPixel,yPixel, BombImages.bombExplosionEndUp));
				}
				else{
					gs.addExplosion(new Explosion(xPixel,yPixel, BombImages.bombExplosionMidUp));
				}
			}
			i++;
		}
		i = 0;
		for(int row = y+1; row<=12; row++ ){
			if(i == blastRadius)
				break;
			sprite = gs.getSpriteBoard().get(row).get(x);
			float xPixel = sprite.getPosition().getX();
			float yPixel = sprite.getPosition().getY();
			if(sprite instanceof Wall){
				break;
			}
			else if(sprite instanceof Crate || i+1 == blastRadius){
				gs.checkPlayerHit(xPixel, yPixel);
				gs.addExplosion(new Explosion(xPixel,yPixel, BombImages.bombExplosionEndDown));
				Empty empty = new Empty();
				gs.setSprite(x, row, empty);
				gs.maybeCreatePowerUp(x, row);
				break;
			}
			else if(sprite instanceof Empty){
				gs.checkPlayerHit(xPixel, yPixel);
				if(gs.getSpriteBoard().get(row+1).get(x) instanceof Wall){
					gs.addExplosion(new Explosion(xPixel,yPixel, BombImages.bombExplosionEndDown));
				}
				else{
					gs.addExplosion(new Explosion(xPixel,yPixel, BombImages.bombExplosionMidDown));
				}
			}
			i++;
		}
	}
	
	public void bombKicked(Direction direction){
		switch(direction) {
		case UP:
			setSpeed(0, -150*Constants.getReceivingXRatio());
			setDirection(Direction.UP);
			break;
		case DOWN:
			setSpeed(0, 150*Constants.getReceivingXRatio());
			setDirection(Direction.DOWN);
			break;
		case RIGHT:
			setSpeed(150*Constants.getReceivingXRatio(), 0);
			setDirection(Direction.RIGHT);
			break;
		case LEFT:
			setSpeed(-150*Constants.getReceivingXRatio(), 0);
			setDirection(Direction.LEFT);
			break;
		default:
			break;
		}
	}
	
	public void checkBombCollision() {
		if(getDirection() != Direction.STOP){
			int x = Constants.getPositionX(getX()+Constants.getHeight()/2);
			int y = Constants.getPositionY(getY()+Constants.getHeight()/2);
			Direction dir = getDirection();
			if(!(gs.getSpriteBoard().get(y+dir.getY()).get(x+dir.getX()) instanceof Empty)){
				setSpeed(0, 0);
				setPosition(gs.getSpriteBoard().get(y).get(x).getX(), gs.getSpriteBoard().get(y).get(x).getY());
			}
		}
	}
	
}