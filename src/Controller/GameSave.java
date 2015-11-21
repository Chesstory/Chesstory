package Controller;
import java.util.ArrayList;
import echecs.Deplacement;

public class GameSave {
	private boolean isGameCorrupted;
	private int gameId;
	private int gameType;
	private ArrayList<Deplacement> moveList;
	
	public GameSave(boolean isGameCorrupted, int gameId, int gameType, ArrayList<Deplacement> moveList){
		this.isGameCorrupted=isGameCorrupted;
		this.gameId=gameId;
		this.gameType=gameType;
		this.moveList=moveList;
	}
	public boolean getIsGameCorrupted(){
		return isGameCorrupted;
	}
	public int getGameId(){
		return gameId;
	}
	public int getGameType(){
		return gameType;
	}
	public ArrayList<Deplacement> getMoveList(){
		return moveList;
	}
}
