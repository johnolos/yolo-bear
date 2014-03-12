package bomberman.game;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import sheep.game.Sprite;

public class Board {
	
	/*
	 * Jeg foeler jeg maa skrive en komentar paa dette.
	 * Implementerer vi PropertyChangeSupport saa kan vi;
	 * 1. Ved initialisjon av spillet, kun tegne opp brettet en gang.
	 * 2. Tenge opp endringer som blir kalt av firePropertyChange.
	 * 
	 * Kommentar: Mulig 1.ikke fungerer da spillerens bevegelser vil
	 * festes på skjermen om vi ikke tenger nye sprites over. Brage?
	 * 
	 * Dessuten saa brukte vi PCL og PCS i MMI for MVC struktur.
	 */
	
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public void addPropertyChangeListener(PropertyChangeListener l) {
		pcs.addPropertyChangeListener(l);
	}
	public void removePropertyChangeListener(PropertyChangeListener l) {
		pcs.removePropertyChangeListener(l);
	}
	
	ArrayList<ArrayList<Sprite>> spriteList;
	
	public static final int COLUMN_SIZE = 13;
	public static final int ROW_SIZE = 13;
	
	public static final int[][] board = {
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
		initiateBoard(board);
	}
	
	public int[][] getBoard() {
		return this.board;
	}
	
	/** OPPRINELIG addSprites i GameState.java ***/
	public void initiateBoard(int[][] board){
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
		Sprite oldValue = this.spriteList.get(row).remove(column);
		sprite.setPosition(oldValue.getX(), oldValue.getY());
		this.spriteList.get(row).add(column, sprite);
		pcs.firePropertyChange("board", oldValue, sprite);
		
	}
	
}
