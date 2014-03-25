package bomberman.game;

import java.util.ArrayList;
import java.util.Random;
import sheep.game.Sprite;   

/**
 * Board
 */
public class Board {
	private ArrayList<ArrayList<Sprite>> spriteList;
	private long timeSinceLastWallplaced;
	private int boundaryX, boundaryY;
	private int wallX, wallY;
	private boolean UP, DOWN, RIGHT, LEFT;
	public static final int COLUMN_SIZE = 13;
	public static final int ROW_SIZE = 13;
	public long TIMEBETWEENWALLS = 1000;
	private static final int NOT_FILLED_PERCENTAGE = 25;
	
	
	/**
	 * standard completely filled board for testing
	 */
	private int[][] board = {
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
	
	
	/**
	 * X-board
	 * The 5 is a placeholder for tiles to be filled by the custom filler
	 */
//	@SuppressWarnings("unused")
	private int[][] customBoard1 = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,5,5,1,5,5,0,0,0,1},
			{1,0,1,5,1,5,1,5,1,5,1,0,1},
			{1,0,5,5,5,5,1,5,5,5,5,0,1},
			{1,5,1,5,1,5,1,5,1,5,1,5,1},
			{1,5,5,5,5,5,5,5,5,5,5,5,1},
			{1,1,1,5,1,1,1,1,1,5,1,1,1},
			{1,5,5,5,5,5,5,5,5,5,5,5,1},
			{1,5,1,5,1,5,1,5,1,5,1,5,1},
			{1,0,5,5,5,5,1,5,5,5,5,0,1},
			{1,0,1,5,1,5,1,5,1,5,1,0,1},
			{1,0,0,0,5,5,1,5,5,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1}};
	
	/**
	 * empty board
	 */
	@SuppressWarnings("unused")
	private int[][] customBoard2 = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,1,0,1},
			{1,1,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,1,1},
			{1,0,1,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1}};
	
	/**
	 * Constructor 
	 * @param isMultiplayer is the game a multiplayer game or not
	 */
	public Board(boolean isMultiplayer){	
		spriteList = new ArrayList<ArrayList<Sprite>>();
		if(!isMultiplayer){
			board = initRandomBoard();
			customRandomFillBoard(customBoard1); // X-board to test modifiablilityyty
			board = customBoard1;
//			 customRandomFillBoard(customBoard2); //should stay empty after the fill
//			board = customBoard2;
		}
		generateImageBoard(board);
	}
	
	/**
	 * Initiate a Random board where the crates are placed random
	 * @return the random board
	 */
	private int[][] initRandomBoard(){
		int[][] _board = new int[Board.COLUMN_SIZE][Board.ROW_SIZE];
		Random random = new Random();

		 for(int row = 1; row<_board.length;row++){
			 for(int column = 1; column<board[0].length;column++){
				 //player starting position and adjacent tiles must be empty
				 if(isStartingPositionOrAdjacentTile(row,column))
					   continue;
				 //every other tile must be a none-destructible wall
				 if(row%2.0 == 0 && column%2.0 == 0){
					 _board[row][column] = 1;
					 continue;
				 }
				 if(random.nextInt(100) >= Board.NOT_FILLED_PERCENTAGE)
					 _board[row][column] = 2;
			 }  
		 }
		 //enforces the outer wall
		 for(int i=0;i<Board.ROW_SIZE;i++){
			 _board[0][i] = 1;   					//upper wall
			 _board[i][0] = 1;   					//left wall
			 _board[Board.ROW_SIZE-1][i] = 1; 		//lower wall
			 _board[i][Board.COLUMN_SIZE-1] = 1; 	//right wall
		 }
		return _board;
	}
	
	/**
	 * Goes through the board and fills the given 5-area the given percentage
	 */
	private void customRandomFillBoard(int[][] board){
		Random random = new Random();
		 for(int row = 1; row<Board.ROW_SIZE;row++){
			 for(int column = 1; column<Board.COLUMN_SIZE;column++){
				 if(board[row][column] == 5 && random.nextInt(100) >= Board.NOT_FILLED_PERCENTAGE)
					 board[row][column] = 2;
				 else if(board[row][column] != 1)
					 board[row][column] = 0;
			 }
		 }
	}
	
	/**
	 * Is the starting pos adjacent to the random tile
	 * @param x position of tile
	 * @param y position of tile
	 * @return true if starting pos or adjacentTile.
	 */
	private boolean isStartingPositionOrAdjacentTile(int x, int y){
		//upper left player
		if((x == 1 && y == 1) || (x == 1 && y == 2) || (x == 2 && y == 1)){
			   return true;
		 }
		//upper right player
		if((x == 1 && y == Board.ROW_SIZE-2) || (x == 1 && y == Board.ROW_SIZE-3) || (x == 2 && y == Board.ROW_SIZE-2)){
			   return true;
		 }
		//lower left player
		if((x == Board.COLUMN_SIZE-2 && y == 1) || (x == Board.COLUMN_SIZE-3 && y == 1) || (x == Board.COLUMN_SIZE-3 && y == 1)){
			   return true;
		 }
		//lower right player
		if((x == Board.COLUMN_SIZE-2 && y == Board.ROW_SIZE-2) || (x == Board.COLUMN_SIZE-2 && y == Board.ROW_SIZE-3) || (x == Board.COLUMN_SIZE-3 && y == Board.ROW_SIZE-2)){
			   return true;
		 }
		return false;
	}
	
	/**
	 * Gets the board
	 * @return the board
	 */
	public int[][] getBoard() {
		return this.board;
	}
	
	/**
	 * gets the SpriteBoard
	 * @return the board with sprites
	 */
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
	
	/**
	 * Sets the sprite in the different positions
	 * @param column the col the sprite is to be set
	 * @param row the row the sprite is to be set
	 * @param sprite the sprite
	 */
	public void setSprite(int column, int row, Sprite sprite) {
		Sprite oldSprite = this.spriteList.get(row).get(column);
		this.spriteList.get(row).remove(oldSprite);
		sprite.setPosition(oldSprite.getX(), oldSprite.getY());
		this.spriteList.get(row).add(column, sprite);
	}
	
	/**
	 * Initates the sudden death mode in game
	 * @param dt
	 */
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
	
	/**
	 * Places the suddenDeathWall on the coordinates
	 * @param x coordinate to place suddenDeathWall
	 * @param y coordinate to place suddenDeathWall
	 */
	public void placeSuddenDeathWall(int x, int y){
		Wall wall = new Wall();
		setSprite(x, y, wall);
		
	}
	
	/**
	 * Is it time to place a new suddenDeathWall
	 * @param dt
	 * @return true if it is time else false
	 */
	public boolean timeToPlaceWall(long dt){

		if(dt >= (this.timeSinceLastWallplaced + TIMEBETWEENWALLS) ) {
			this.timeSinceLastWallplaced += TIMEBETWEENWALLS;
			return true;
		}
		return false;
	}
	
	/**
	 * Get the x-coordinate for suddenDeathWall
	 * @return the int for the x value where to put suddenDeathWall
	 */
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
	
	/**
	 * Get the y-coordinate for suddenDeathWall
	 * @return the int for the y value where to put suddenDeathWall
	 */
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
	
	/**
	 * Get the y-coordinate of wall
	 * @return the y-coordinate of wall
	 */
	public int getWallY() {
		return this.wallY;
	}
	
	/**
	 * Get the x-coordinate of wall
	 * @return the x-coordinate of wall
	 */
	public int getWallX() {
		return this.wallX;
	}
	
	/**
	 * Return true if game is finished
	 * @return true or false
	 */
	public boolean isCompletelyFilled(){
		if(this.getWallX() == 6 && this.getWallY() == 6){
			return true;
		}
		return false;
	}
	
	/**
	 * Restet game resets the game for new round
	 */
	public void reset() {
		this.spriteList.clear();
		this.TIMEBETWEENWALLS = 1000;
		generateImageBoard(board);
	}
	
	/**
	 * Speed up time between wall placement in suddenDeathWall
	 */
	public void SpeedUpSD() {
		this.TIMEBETWEENWALLS = 50;
	}
}
