package Controller;
import java.util.ArrayList;
import echecs.Deplacement;

public class GameSave {
	private boolean isGameCorrupted;
	private int gameId;
	private int gameType;
	private String FEN;
	/**
	 * @param isGameCorrupted the isGameCorrupted to set
	 */
	void setGameCorrupted(boolean isGameCorrupted) {
		this.isGameCorrupted = isGameCorrupted;
	}
	/**
	 * @param gameId the gameId to set
	 */
	void setGameId(int gameId) {
		this.gameId = gameId;
	}
	/**
	 * @param gameType the gameType to set
	 */
	void setGameType(int gameType) {
		this.gameType = gameType;
	}
	/**
	 * @param fEN the fEN to set
	 */
	void setFEN(String fEN) {
		FEN = fEN;
	}
	/**
	 * @param arrayRulePiece the arrayRulePiece to set
	 */
	void setArrayRulePiece(String[] arrayRulePiece) {
		this.arrayRulePiece = arrayRulePiece;
	}
	/**
	 * @param moveList the moveList to set
	 */
	void setMoveList(ArrayList<Deplacement> moveList) {
		this.moveList = moveList;
	}
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
	public String toString(){
		String s="_";
		for(int i=0;i<arrayRulePiece.length;i++){
			s+=arrayRulePiece[i];
			s+=",";
		}
		return s;
	}
}
