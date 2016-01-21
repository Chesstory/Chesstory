package Controller;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ChesstoryConstants {
	public static final String FILE_DEFAULT_CLASSICAL = "default_CLASSICAL";
	public static final int NB_RULE_PER_PIECE = 10;
	public static final int NB_PIECE = 6;

	/**
	 * Event gesture
	 */
	public final static int CHESS_EVENT_ECHEC = 1;
	public final static int CHESS_EVENT_MAT = 2;
	public final static int CHESS_EVENT_PAT = 3;
	public final static int CHESS_EVENT_ROQUE = 4;
	public final static int CHESS_EVENT_PEP = 5;
	public final static int CHESS_EVENT_PROM = 6;
	public final static int CHESS_EVENT_ELAPSE_TIME = 7;

	/**
	 * Serial gesture
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Gametype gesture
	 */
	public static final int GAMETYPE_CLASSICAL = 1;
	public static final int GAMETYPE_CUSTOM = 2;
	public static final int GAMETYPE_SHATRANJ = 3;
	public static final int GAMETYPE_CHATURANGA = 4;
	
	/**
	 * Rules text gesture
	 */
	public final static String CHESS_RULES_HEADER = "Hello player !\nYou are here to play some chess game aren't you ?\nOh I can see that you choose to play the ";
	public final static String CHESS_RULES_CLASSIC = "classical chess game !\n"
			+ "Remember : if you need some help, just right-click a piece !\n"
			+ "Your chess pieces have the following move ability :\n"
			+ "  - The king is able to go one cell in any direction.\n"
			+ "  - The queen is able to go as far as she wants to in any direction.\n"
			+ "  - The bishop can go as far as he wants to in diagonnal.\n"
			+ "  - The knight can go one cell in a direction and two in the other.\n"
			+ "  - The rook can go as far as it wants to in straight line.\n"
			+ "  - The pawn can travel only one case beyond him.\n"
			+ "Your pawn can travel two cells instead of one if he is on his departure cell.\n"
			+ "If you activated the rook, your king can travel two cells horizontally and put the nearest rook next to him, but only if none of them moved before.\n"
			+ "If you toggled on the Prise-en-passant, your pawn can eat an enemy's one when they are side by side, but only if the enemy's pawn used his ability to travel two cells instead of one just before.\n"
			+ "Good luck & have fun !";
	public final static String CHESS_RULES_CUSTOM_PT1 = "custom game mode !\n"
			+ "Remember : if you need some help, just right-click a piece !\n"
			+ "Your chess pieces have the following move ability :\n";
	public final static String CHESS_RULES_CUSTOM_PT2 = "If your pawn has 'basic' moves capacity he can travel two cells instead of one if he is on his departure cell.\n"
			+ "If you activated the rook, your king can travel two cells horizontally and put the nearest rook next to him, but only if none of them moved before.\n"
			+ "If you toggled on the Prise-en-passant and if your pawn has 'basic' moves capacity, he can eat an enemy's one when they are side by side, but only if the enemy's pawn used his ability to travel two cells instead of one just before.\n"
			+ "Good luck & have fun !";
	public final static String CHESS_RULES_SHATRANJ_PT1 = "";
	public final static String CHESS_RULES_SHATRANJ_PT2 = "";
	public final static String CHESS_RULES_CHATURANGA_PT1 = "";
	public final static String CHESS_RULES_CHATURANGA_PT2 = "";
}