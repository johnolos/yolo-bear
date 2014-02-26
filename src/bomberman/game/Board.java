package bomberman.game;

public class Board {
	
//	Here we create the game boards. Easy to create new ones. Just add a new int[][] like below.
	
	private int[][] board = {{1,1,1,1,1,1,1,1,1,1,1,1,1},
							 {1,0,0,2,2,2,2,2,2,2,0,0,1},
							 {1,0,1,2,1,2,1,2,1,2,1,0,1},
							 {1,2,2,2,0,0,2,0,0,2,2,2,1},
							 {1,2,1,2,1,0,1,0,1,2,1,2,1},
							 {1,2,2,2,0,2,2,2,0,2,2,2,1},
							 {1,2,1,2,1,2,1,2,1,2,1,2,1},
							 {1,2,2,2,0,2,2,2,0,2,2,2,1},
							 {1,2,1,2,1,0,1,0,1,2,1,2,1},
							 {1,2,2,2,0,0,2,0,0,2,0,2,1},
							 {1,0,1,2,1,2,1,2,1,2,1,0,1},
							 {1,0,0,2,2,2,2,2,2,2,0,0,1},
							 {1,1,1,1,1,1,1,1,1,1,1,1,1}};
	
	public Board(){
		
	}
	
	public int[][] getBoard(){
		return this.board;
	}

}