package Controller;
import java.util.ArrayList;
import echecs.Deplacement;

public class GameSave {
	private boolean isGameCorrupted;
	private int gameId;
	private int gameType;
	private String FEN;
	private String[] arrayRulePiece;
	private ArrayList<Deplacement> moveList;
	
	public GameSave(boolean isGameCorrupted, int gameId, int gameType,
			ArrayList<Deplacement> moveList, String FEN, String[] arrayRulePiece){
		this.arrayRulePiece=arrayRulePiece;
		this.FEN=FEN;
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
	public String getFEN(){
		return FEN;
	}
	public String[] getArrayRulePiece(){
		return arrayRulePiece;
	}
	public ArrayList<Deplacement> getMoveList(){
		return moveList;
	}
}
