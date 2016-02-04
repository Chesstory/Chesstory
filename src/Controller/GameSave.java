package Controller;

import java.util.ArrayList;
import echecs.Deplacement;

/**
 * Contains what is needed to load and save a chess game. Which means the move
 * that occurs during the game, a formated array of the rules, etc
 * 
 * @author Acevedo Roman and Guillemot Baptiste.
 *
 */
public class GameSave {
	private boolean isGameCorrupted;
	private int gameId;
	private int gameType;
	private String FEN;
	private String[] arrayRulePiece;
	private ArrayList<Deplacement> moveList;
	
	/**
	 * Constructor for load and save
	 *
	 * @param isGameCorrupted for load : if the game file was corrupted the bool is tu true. For save it's useless
	 * @param gameId id of the game
	 * @param gameType type of the game
	 * @param moveList list of the moves made in the game
	 * @param FEN departure FEN
	 * @param arrayRulePiece list of the rules of the game
	 */
	public GameSave(boolean isGameCorrupted, int gameId, int gameType,
			ArrayList<Deplacement> moveList, String FEN, String[] arrayRulePiece) {
		this.arrayRulePiece = arrayRulePiece;
		this.FEN = FEN;
		this.isGameCorrupted = isGameCorrupted;
		this.gameId = gameId;
		this.gameType = gameType;
		this.moveList = moveList;
	}

	/**
	 * @return If the game is corrupted
	 */
	public boolean getIsGameCorrupted() {
		return isGameCorrupted;
	}

	/**
	 * @return Game ID
	 */
	public int getGameId() {
		return gameId;
	}

	/**
	 * @return Game type
	 */
	public int getGameType() {
		return gameType;
	}

	/**
	 * @return FEN Code
	 */
	public String getFEN() {
		return FEN;
	}

	/**
	 * @return Formated move set of the chess pieces
	 */
	public String[] getArrayRulePiece() {
		return arrayRulePiece;
	}

	/**
	 * @return ArrayList of the moves to save
	 */
	public ArrayList<Deplacement> getMoveList() {
		return moveList;
	}

	/**
	 * @param isGameCorrupted
	 *            the isGameCorrupted to set
	 */
	void setGameCorrupted(boolean isGameCorrupted) {
		this.isGameCorrupted = isGameCorrupted;
	}

	/**
	 * @param gameId
	 *            The gameId to set
	 */
	void setGameId(int gameId) {
		this.gameId = gameId;
	}

	/**
	 * @param gameType
	 *            The gameType to set
	 */
	void setGameType(int gameType) {
		this.gameType = gameType;
	}

	/**
	 * @param fEN
	 *            The FEN to set
	 */
	void setFEN(String fEN) {
		FEN = fEN;
	}

	/**
	 * @param arrayRulePiece
	 *            The arrayRulePiece to set
	 */
	void setArrayRulePiece(String[] arrayRulePiece) {
		this.arrayRulePiece = arrayRulePiece;
	}

	/**
	 * @param moveList
	 *            The moveList to set
	 */
	void setMoveList(ArrayList<Deplacement> moveList) {
		this.moveList = moveList;
	}

	public String toString() {
		String s = "_";
		for (int i = 0; i < arrayRulePiece.length; i++) {
			s += arrayRulePiece[i];
			s += ",";
		}
		return s;
	}
}
