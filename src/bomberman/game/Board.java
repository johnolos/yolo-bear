package bomberman.game;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Random;

import sheep.game.Sprite;

public class Board {
	
	/**
	 * Optimal solution: 
	 * 		Draw four tiles around a player at a time.
	 * 		Draw tiles after bomb impact and powerup consumption.
	 * 
	 */
	
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public void addPropertyChangeListener(PropertyChangeListener l) {
		pcs.addPropertyChangeListener(l);
	}
	public void removePropertyChangeListener(PropertyChangeListener l) {
		pcs.removePropertyChangeListener(l);
	}
	
	private ArrayList<ArrayList<Sprite>> spriteList;
	
	private long timeSinceLastWallplaced;
	
	private int boundaryX, boundaryY;
	private int wallX, wallY;
	private boolean UP, DOWN, RIGHT, LEFT;

	
	public static final int COLUMN_SIZE = 13;
	public static final int ROW_SIZE = 13;
	public long TIMEBETWEENWALLS = 1000;
	
	public int[][] board = {
		{1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,0,0,2,2,2,2,2,2,2,0,0,1},
		{1,0,1,2,1,2,1,2,1,2,1,0,1},
		{1,2,2,2,2,2,2,2,2,2,2,2,1},
		{1,2,1,2,1,2,1,2,1,2,1,2,1},
		{1,2,2,2,2,2,2,2,2,2,2,2,1},
		{1,2,1,2,1,2,1,2,1,2,1,2,1},
		{1,2,2,2,2,2,2,2,2,2,2,2,1},
		{1,2,1,2,1,2,1,2,1,2,1,2,1},
		{1,2,2,2,2,2,2,2,2,2,2,2,1},
		{1,0,1,2,1,2,1,2,1,2,1,0,1},
		{1,0,0,2,2,2,2,2,2,2,0,0,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1}};
	
	public Board(){	
		spriteList = new ArrayList<ArrayList<Sprite>>();
		board = initRandomBoard();
		generateImageBoard(board);
	}
	
	private int[][] initRandomBoard(){
		int[][] _board = new int[Board.COLUMN_SIZE][Board.ROW_SIZE];
		Random random = new Random();

		 for(int row = 0; row<_board.length;row++){
			 for(int column = 0; column<board[0].length;column++){
				 //player starting position and adjacent tiles must be empty
				 if(isStartingPositionOrAdjacent(row,column))
					   continue;
				 //every other tile must be a none-destructible wall
				 if(row/2 == 0 && column/2 == 0){
					 _board[row][column] = 1;
					 continue;
				 }
				 //fills approx. 75% of the board with crates
				 if(random.nextInt(100) >= 25)
					 _board[row][column] = 2;
			 } 
		 }
		 //enforces the outer wall
		 for(int i=0;i<Board.ROW_SIZE;i++){
			 board[0][i] = 1;
			 board[i][0] = 1;
			 board[Board.ROW_SIZE-1][i] = 1;
			 board[Board.COLUMN_SIZE-1][i] = 1;
		 }
		return _board;
	}
	
	private boolean isStartingPositionOrAdjacent(int x, int y){
		//upper left player
		if((x == 1 && y == 1) || (x == 1 && y == 2) || (x == 2 && y == 1)){
			   return true;
		 }
		//upper right player
		if((x == 1 && y == Board.ROW_SIZE-2) || (x == 1 && y == Board.ROW_SIZE-3) || (x == 2 && y == Board.ROW_SIZE-2)){
			   return true;
		 }
		//lower left player
		if((x == Board.COLUMN_SIZE-2 && y == 1) || (x == Board.COLUMN_SIZE-3 && y == 2) || (x == Board.COLUMN_SIZE-3 && y == 1)){
			   return true;
		 }
		//lower right player
		if((x == Board.COLUMN_SIZE-2 && y == Board.ROW_SIZE-2) || (x == Board.COLUMN_SIZE-2 && y == Board.ROW_SIZE-3) || (x == Board.COLUMN_SIZE-3 && y == Board.ROW_SIZE-2)){
			   return true;
		 }
		return false;
	}
	
	public int[][] getBoard() {
		return this.board;
	}
	
	public ArrayList<ArrayList<Sprite>> getSpriteBoard(){ 
		return this.spriteList;
	}
	
	/** OPPRINELIG addSprites i GameState.java ***/
	public void generateImageBoard(int[][] board){
		this.wallX = 0;
		this.wallY = 0;
		float x = Constants.getPixelsOnSides();
		float y = 0.0f;
		spriteList = new ArrayList<ArrayList<Sprite>>();
		for(int i =0; i<board.length;i++){
			ArrayList<Sprite> row = new ArrayList<Sprite>();
			for(int j=0; j<board[1].length;j++){
				if(board[i][j]==1){
					Wall boardPiece = new Wall();
					boardPiece.setPosition((float) (x+Constants.getHeight()*j), (float) (y+Constants.getHeight()*i));
					row.add(boardPiece);
				}
				if(board[i][j]==0){
					Empty boardPiece = new Empty();
					boardPiece.setPosition((float) (x+Constants.getHeight()*j), (float) (y+Constants.getHeight()*i));
					row.add(boardPiece);
				}
				if(board[i][j]==2){
					Crate boardPiece = new Crate();
					boardPiece.setPosition((float) (x+Constants.getHeight()*j), (float) (y+Constants.getHeight()*i));
					row.add(boardPiece);
				}	
			}
			spriteList.add(row);
		}
	}
	
	
	public void setSprite(int column, int row, Sprite sprite) {
		Sprite oldSprite = this.spriteList.get(row).get(column);
		this.spriteList.get(row).remove(oldSprite);
		sprite.setPosition(oldSprite.getX(), oldSprite.getY());
		this.spriteList.get(row).add(column, sprite);
	}
	
	public void initiateSuddenDeath(long dt){
		this.timeSinceLastWallplaced = dt;
		this.boundaryX = 1;
		this.boundaryY = 1;
		this.wallX = boundaryX;
		this.wallY = boundaryY;
		this.UP = false;
		this.DOWN = false;
		this.LEFT = false;
		this.RIGHT = true;
		
	}
	
	public void placeSuddenDeathWall(int x, int y){
		Wall wall = new Wall();
		setSprite(x, y, wall);
		
	}
	
	public boolean timeToPlaceWall(long dt){

		if(dt >= (this.timeSinceLastWallplaced + TIMEBETWEENWALLS) ) {
			this.timeSinceLastWallplaced += TIMEBETWEENWALLS;
			return true;
		}
		return false;
	}
	
	public int getXSuddenDeathWall() {
		if(this.DOWN || this.UP) {
			return wallX;
		}
		if(this.RIGHT) {
			if(wallX == COLUMN_SIZE - 1 - boundaryX) {
				this.RIGHT = false;
				this.DOWN = true;
				return wallX;
			}
			return wallX++;
		}
		if(this.LEFT) {
			if(wallX == boundaryX) {
				this.LEFT = false;
				this.UP = true;
				boundaryY++;
				return wallX;
			}
			return wallX--;
		}
		return -1;
	}
	
	public int getYSuddenDeathWall() {
		if(this.LEFT || this.RIGHT) {
			return wallY;
		}
		if(this.DOWN) {
			if(wallY == ROW_SIZE - 1 - boundaryY) {
				this.LEFT = true;
				this.DOWN = false;
				wallX--;
				return wallY;
			}
			return wallY++;
		}
		if(this.UP) {
			if(wallY == boundaryY) {
				this.RIGHT = true;
				this.UP = false;
				boundaryX++;
				wallX++;
				return wallY;
			}
			return wallY--;
		}
		return -1;
	}
	public int getWallY() {
		return this.wallY;
	}
	public int getWallX() {
		return this.wallX;
	}
	/**
	 * Return true if game is finished
	 * @return
	 */
	public boolean lastWall(){
		if(this.getWallX() == 6 && this.getWallY() == 6){
			return true;
		}
		return false;
	}
	public void reset() {
		this.spriteList.clear();
		this.TIMEBETWEENWALLS = 1000;
		generateImageBoard(board);
	}
	public void SpeedUpSD() {
		this.TIMEBETWEENWALLS = 50;
		
	}
	
}
