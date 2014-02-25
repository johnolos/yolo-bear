package bomberman.states;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import bomberman.game.Board;
import bomberman.game.Constants;
import bomberman.game.Crate;
import bomberman.game.Empty;
import bomberman.game.Player;
import bomberman.game.Wall;
import sheep.game.Sprite;
import sheep.game.State;

public class GameState extends State{
	
	private Player player;
	private Board  board;
	private ArrayList<ArrayList<Sprite>> spriteList = new ArrayList<ArrayList<Sprite>>();
	private double startingX;
	private double startingY;
	private boolean walls = false;
	
	public GameState (){
		this.player = new Player("Player1");
		this.board = new Board();
		//Finding the upper-left coordinates of the game-view
		this.startingX = Constants.screenWidth/2 - Constants.getHeight()*6.5;
		this.startingY = Constants.screenHeight/2-Constants.getHeight()*6.5;
		addSprites();
		
	}
	
	
	public void addSprites(){
		spriteList = new ArrayList<ArrayList<Sprite>>();
		for(int i =0; i<board.getBoard().length;i++){
			ArrayList<Sprite> row = new ArrayList<Sprite>();
			for(int j=0; j<board.getBoard()[1].length;j++){
				if(board.getBoard()[i][j]==1){
					Wall boardPiece = new Wall();
					boardPiece.setPosition((float) (this.startingX+Constants.getHeight()*j), (float) (this.startingY+Constants.getHeight()*i));
					row.add(boardPiece);
				}
				if(board.getBoard()[i][j]==0){
					Empty boardPiece = new Empty();
					boardPiece.setPosition((float) (this.startingX+Constants.getHeight()*j), (float) (this.startingY+Constants.getHeight()*i));
					row.add(boardPiece);
				}
				if(board.getBoard()[i][j]==2){
					Crate boardPiece = new Crate();
					boardPiece.setPosition((float) (this.startingX+Constants.getHeight()*j), (float) (this.startingY+Constants.getHeight()*i));
					row.add(boardPiece);
				}	
			}
			spriteList.add(row);
		}
	}
	
	public void update(float dt){
		for(ArrayList<Sprite> row : spriteList){
			for(Sprite sprite : row){
				sprite.update(dt);
			}
		}
	}
	
	public void draw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
		for(ArrayList<Sprite> row : spriteList){
			for(Sprite sprite : row){
				sprite.draw(canvas);
			}
		}
	}	

}
