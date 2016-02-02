package Controller;

import java.awt.Color;

/**
 * Contains all the constants we use ! (Themes, font_size, texts, etc)
 * 
 * @author Acevedo Roman and Guillemot Baptiste.
 *
 */
public class ChesstoryConstants {
	public static final int dimX = 8;
	public static final int dimY = 8;

	/**
	 * Fonts used int Chesstory
	 */
	public static final String FONT_NAME_TITLE_1 = "Comic Sans MS";// Trolleur
																	// de
																	// qualité !
	public static final int FONT_SIZE_TITLE_1 = 32;

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
	public static final int GAMETYPE_SHATRANJ = 2;
	public static final int GAMETYPE_CHATURANGA = 3;
	public static final int GAMETYPE_CUSTOM = 4;
	public static final String TEMP_FILE_NAME = "tempFile_TEMP";
	/**
	 * Thems gesture
	 */
	public final static Color[] THEM_DEFAULT = {
			Color.getHSBColor(0.56f, 1.0f, 0.8f), Color.white, Color.YELLOW,
			Color.ORANGE };
	public final static Color[] THEM_SHATRANJ = { Color.white, Color.white,
			Color.YELLOW, Color.ORANGE };
	public final static Color[] THEM_SOUP = { new Color(0x89725B),
			new Color(0xb0cc99), new Color(0x677e52), new Color(0xf6e8b1) };
	public final static Color[] THEM_FLASHY = { new Color(0xff456a),
			new Color(0xf4ff3a), new Color(0x84cecc), new Color(0x006d80) };
	public final static Color[] THEM_YELD = { new Color(0xe8cc06),
			new Color(0x1d702d), new Color(0x9c9e4b), new Color(0xffbd4f) };
	public final static Color[] THEM_TRVEBLKMETOL = { new Color(0x9e9e9e),
			Color.BLACK, new Color(0x677179), new Color(0xe21313) };
	public final static Color[] THEM_ROSE = { new Color(0xffb5c5),
			new Color(0xcd919e), new Color(0xeee0e5), new Color(0xe7c7c5) };
	public final static Color[] THEM_SPRING = { new Color(0xadff2f),
			new Color(0x00c5cd), new Color(0xffd700), new Color(0xdaa520) };
	public final static Color[] THEM_OUTCH = { new Color(0x80d5ff),
			new Color(0xff84db), new Color(0xb136dd), new Color(0xe78cff) };

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
			+ "If you activated castling , your king can travel two cells horizontally and put the nearest rook next to him, but only if none of them moved before.\n"
			+ "If you toggled on the Prise-en-passant and if your pawn has 'basic' moves capacity, he can eat an enemy's one when they are side by side, but only if the enemy's pawn used his ability to travel two cells instead of one just before.\n"
			+ "Good luck & have fun !";

	public final static String CHESS_RULES_SHATRANJ_PT1 = "shatranj game !\n"
			+ "Remember : if you need some help, just right-click a piece !\n"
			+ "Your chess pieces have the following move ability :\n";
	public final static String CHESS_RULES_SHATRANJ_PT2 = "As you can see, those rules are really close to ours.\n"
			+ "In fact, our chess game has been conceived with shatranj's rules.\n"
			+ "This game was born in east Asia, around IV or Vth century.\n"
			+ "The shatranj has been brought to us by persians.\n"
			+ "It basically refers to a batlle field, with different units such as chariots, foot soldiers, etc.\n"
			+ "Good luck & have fun !";

	public final static String CHESS_RULES_CHATURANGA_PT1 = "chaturanga game !\n"
			+ "Remember : if you need some help, just right-click a piece !\n"
			+ "Your chess pieces have the following move ability :\n";
	public final static String CHESS_RULES_CHATURANGA_PT2 = "";
	
	public static boolean YACG_DISPLAYHELP = true;
	
}