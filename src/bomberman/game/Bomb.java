package bomberman.game;

import bomberman.states.GameState;
import sheep.game.Sprite;
import sheep.graphics.Image;
import android.graphics.Canvas;

public class Bomb extends Sprite{
	
	private int blastRadius; //the "length" of the explosion
	private Image bomb;
	private double time = System.currentTimeMillis();
	private double explodedTime;
	private boolean exploded = false;
	private boolean phase2 = false;
	private boolean finished = false;
	private GameState gs;
	private Image[] explodeImages;
	
	public Bomb(int x, int y, int blastRadius, GameState gs){
		explodeImages = new Image[4];
		explodeImages[1] = new Image(R.drawable.explode2);
		explodeImages[2] = new Image(R.drawable.explode3);
		explodeImages[3] = new Image(R.drawable.explode4);
		this.blastRadius = blastRadius;
		this.gs = gs;
		this.setPosition(x, y);
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
		this.setView(bomb);
	}
	
	public void bombAnimation(){
		if(System.currentTimeMillis() - time >= 2000 && !this.exploded && !this.phase2){
			bomb = new Image(R.drawable.bombphase2);
			setView(bomb);
			phase2 = true;
		}
		else if(System.currentTimeMillis() - time >= 3000 && !this.exploded){
			bomb = new Image(R.drawable.explode);
			setView(bomb);
			exploded = true;
			phase2 = false;
			gs.bombImpact(this);
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
			System.out.println("hello");
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
}