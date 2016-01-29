package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Controller.ChesstoryGame;
import echecs.Deplacement;
import echecs.Echiquier;
import echecs.Piece;
import echecs.Position;

import java.util.ArrayList;

import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;

import static Controller.ChesstoryConstants.*;

/**
 * Graphic interface of the chess board. It describes everything that is needed
 * to interact with it and shows the user the state of the chess board.
 * 
 * @author Acevedo Roman and Guillemot Baptiste, based on Delepoulle Samuel's
 *         code.
 *
 */
@SuppressWarnings("serial")
public final class YetAnotherChessGame extends JFrame implements MouseListener, MouseMotionListener {

	/**
	 * Background
	 */
	JLayeredPane layeredPane;

	/**
	 * Chess board
	 */
	JPanel chessBoard;

	/**
	 * A chess piece
	 */
	JLabel chessPiece;

	/**
	 * Move in X
	 */
	int xDeplace;
	/**
	 * Move in Y
	 */
	int yDeplace;

	/**
	 * Departure position
	 */
	Position depart;
	/**
	 * Arrival position
	 */
	Position arrive;

	/**
	 * Color themes
	 */
	Color backgroundOne, backgroundTwo, caseSpec, caseAccessible;

	private ChesstoryGame chesstoryGame;
	/**
	 * Current chess board
	 */
	static Echiquier ech;// TODO REMOVE STATIC

	private boolean isClickable = false;
	/**
	 * First click or not
	 */
	private boolean first = true;

	/**
	 * Winner of the game
	 */
	private char winner = 'n';

	/**
	 * Graphically remove all the chess pieces.
	 */
	private void emptyChessboard() {
		for (int j = 0; j < ech.getDimY(); j++) {
			for (int i = 0; i < ech.getDimX(); i++) {
				JPanel panel = (JPanel) chessBoard.getComponent(j * ech.getDimX() + i);
				panel.removeAll();
				panel.repaint();
			}
		}
	}

	/**
	 * Draw every pieces on the chess board. <br />
	 * Basically travel on every cell and check if there is a piece to draw.
	 */
	private void drawEveryPieces() {
		for (int j = 0; j < ech.getDimY(); j++) {
			for (int i = 0; i < ech.getDimX(); i++) {
				Piece p = ech.getPieceCase(i, 8 - j - 1);

				if (p != null) {
					String name = p.getName();
					String fullName = "icons/" + name + ".png";
					ClassLoader cl = this.getClass().getClassLoader();
					JLabel piece = new JLabel(new ImageIcon(cl.getResource(fullName)));
					JPanel panel = (JPanel) chessBoard.getComponent(j * ech.getDimX() + i);
					panel.add(piece);
				}
			}
		}
	}

	/**
	 * Empty and redraw the chess board.
	 */
	private void redraw() {
		emptyChessboard();
		drawEveryPieces();
	}

	/**
	 * Empty and repaint the chessBoard with the entered FEN code
	 * 
	 * @param departureFEN
	 *            FEN code used to repaint the chessBoard
	 */
	public void makeDrawFen(String departureFEN) {
		emptyChessboard();
		ech.setFEN(departureFEN);
		drawEveryPieces();
		chessBoard.revalidate();
		chessBoard.repaint();
	}

	/**
	 * Highlight a cell, use to show accessible position for example.
	 * 
	 * @param pos
	 *            The cell to highlight.
	 * @param couleur
	 *            The color in which the cell will be highlighted
	 */
	private void highlight(Position pos, Color couleur) {
		JPanel panel = (JPanel) chessBoard.getComponent((8 - pos.getY() - 1) * ech.getDimX() + pos.getX());
		panel.setBackground(couleur);
	}

	/**
	 * Highlight all the accessible positions of the selected piece.
	 * 
	 * @param p
	 *            The position of the piece.
	 */
	public void afficheLesPositionsDansLeGUI(Position p) {
		ArrayList<Position> array = ech.affichePositionAccessibles(p);
		ArrayList<Position> arraySpec = ech.showSpecPositions(p);

		// NORMAL
		for (int i = 0; i < array.size(); i++) {
			highlight(array.get(i), caseAccessible/* new Color(0xfc913a *//* 0x74d600 *//* ) */);
		}
		// Special
		for (int i = 0; i < arraySpec.size(); i++) {
			highlight(arraySpec.get(i), caseSpec /* new Color( *//* 0xf9d62e *//* 0xadff00 *//* ) */);
		}
	}

	/**
	 * Draw the cells of the chess board (colors already selected in the theme).
	 */
	public void drawChessboard() {
		for (int j = 0; j < ech.getDimY(); j++) {
			for (int i = 0; i < ech.getDimX(); i++) {
				JPanel panel = (JPanel) chessBoard.getComponent(j * ech.getDimX() + i);
				if (i % 2 == 0) {
					panel.setBackground(j % 2 == 0 ? backgroundTwo : backgroundOne);
				} else {
					panel.setBackground(j % 2 == 0 ? backgroundOne : backgroundTwo);
				}
			}
		}
	}

	/**
	 * Force a move, mainly used for the "forward" method
	 * 
	 * @param move
	 *            Move to force
	 */
	public void forceMakeDeplacement(Deplacement move) {
		ech.forceDeplacement(move);
		redraw();
		chessBoard.repaint();
		chessBoard.revalidate();
	}

	/**
	 * Test and execute a move, then refresh the chess board (gui)
	 * 
	 * @param move
	 *            Move to check
	 * @return True if tests are successful
	 */
	public boolean makeDeplacement(Deplacement move) {

		if (ech.isValidMove(move)) {
			ech.executeDeplacement(move);
			redraw();
			chesstoryGame.addMoveMadeByPlayer(move);
			return true;
		} else
			return false;
	}

	/**
	 * Create everything that we need to draw the chess board, and mostly is an
	 * intermediary between Echiquier and ChesstoryGame.
	 */
	public YetAnotherChessGame(String fenDeDeapart, ChesstoryGame chesstoryGame) {
		this.chesstoryGame = chesstoryGame;
		// ech = new Echiquier();

		backgroundOne = Color.getHSBColor(0.56f, 1.0f, 0.8f);
		backgroundTwo = Color.white;
		caseSpec = Color.ORANGE;
		caseAccessible = Color.YELLOW;

		// This a test with the classical chess pieces
		ech = new Echiquier(new Piece("pion", 'p', 0, 0, 1, 1, 0, 0, false, false),
				new Piece("dame", 'q', true, true, true, true, false),
				new Piece("roi", 'k', 1, 1, 1, 1, 1, 1, true, false), new Piece("cavalier", 'n', true),
				new Piece("fou", 'b', false, false, true, true, false),
				new Piece("tour", 'r', true, true, false, true, false), this);

		ech.setFEN(fenDeDeapart);

		Dimension boardSize = new Dimension(600, 600);

		// Use a Layered Pane for this this application
		layeredPane = new JLayeredPane();

		layeredPane.setPreferredSize(boardSize);
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);

		// Add a chess board to the Layered Pane
		chessBoard = new JPanel();

		layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		chessBoard.setLayout(new GridLayout(8, 8));
		chessBoard.setPreferredSize(boardSize);
		chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

		// dessin de l'équiquier
		for (int i = 0; i < 64; i++) {
			JPanel square = new JPanel(new BorderLayout());
			chessBoard.add(square);

			int row = (i / 8) % 2;
			if (row == 0) {
				square.setBackground(i % 2 == 0 ? backgroundTwo : backgroundOne);
				square.setBorder(BorderFactory.createLineBorder(Color.black));
			} else {
				square.setBackground(i % 2 == 0 ? backgroundOne : backgroundTwo);
				square.setBorder(BorderFactory.createLineBorder(Color.black));
			}
		}
		drawEveryPieces();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent me) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * What happens when you click on the chess board with your mouse !<br />
	 * 2 possibles cases :<br />
	 * - right click : display informations on the piece in the logs.<br />
	 * - left click : if no piece is already focused, it focus this one and
	 * show, if needed, its possible moves. Else, it tests whether the move is
	 * possible or not and do what is needed.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
			if (isClickable) {
				if (!first) {
					chessPiece.setVisible(false);
					// TODO PLUS RAPIDE FDP

					JPanel panel = (JPanel) chessBoard
							.getComponent((8 - depart.getY() - 1) * ech.getDimX() + depart.getX());

					arrive = new Position((int) ((e.getX() / 600.0) * 8.0),
							(int) ((((600.0 - e.getY()) / 600.0) * 8.0)));
					// here we have to save the color and the piece into d in
					// order
					// to
					// later save it in the arraylist

					Deplacement d = new Deplacement(depart, arrive, ech.getPiece(depart).getCode(),
							ech.getPiece(depart).getColor());

					System.out.println("==> Déplacement : " + d);

					if (makeDeplacement(d)) {
						if ((winner = ech.checkGameIsEnded()) != 'n') {
							int answer;

							chesstoryGame.addLogsText("It is finished, " + ((winner == 'w') ? "white" : "black")
									+ " won the game."
									+ "You can either start a new game, leave or browse back in time to see what you could have done better or to win again !");
							answer = JOptionPane.showConfirmDialog(null,
									"The game is finished, do you want to leave ? (you can either browse back in time)",
									"Warning you are about to leave", JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE);

							if (answer == JOptionPane.YES_OPTION)
								System.exit(0);
							else
								chesstoryGame.enableBrowserView();
						}

					} else {
						// replacer sur la case de départ
						panel.add(chessPiece);
						chessPiece.setVisible(true);
					}

					// Remettre la couleur d'origine
					drawChessboard();

					// Put king in a yoloswaggy color if echec
					if (ech.isInCheck(ech.getTrait())) {
						eventInter(CHESS_EVENT_ECHEC,
								((ech.getTrait() == 'w') ? "In favor of black." : "In favor of white."));
						highlight(ech.searchKing(ech.getTrait()), Color.MAGENTA);
					}
					first = true;
				} else {
					drawChessboard();
					chessPiece = null;
					Component c = chessBoard.findComponentAt(e.getX(), e.getY());
					// if it is empty : do nothing
					if (c instanceof JPanel) {
						return;
					}

					// retrouver la case correspondante
					depart = new Position((int) ((e.getX() / 600.0) * 8.0),
							(int) ((((600.0 - e.getY()) / 600.0) * 8.0)));

					System.out.print(depart);

					chessPiece = (JLabel) c;

					// highlight the case
					highlight(depart, (ech.getTrait() == ech.getPiece(depart).getColor()) ? Color.cyan : Color.red);

					if (ech.getTrait() == ech.getPiece(depart).getColor())
						afficheLesPositionsDansLeGUI(depart);

					if (ech.isInCheck(ech.getTrait())) {
						highlight(ech.searchKing(ech.getTrait()), Color.MAGENTA);
					}

					first = !(ech.getTrait() == ech.getPiece(depart).getColor());
				}
			}
		} else {
			Piece chessPieceInfo = null;
			Component c = chessBoard.findComponentAt(e.getX(), e.getY());
			// if it is empty : do nothing
			if (c instanceof JPanel) {
				return;
			}
			depart = new Position((int) ((e.getX() / 600.0) * 8.0), (int) ((((600.0 - e.getY()) / 600.0) * 8.0)));

			chessPieceInfo = ech.getPiece(depart);

			String info = "\nHELP DISPLAY : You clicked on the cell (" + depart.getX() + "," + depart.getY()
					+ "), it contains a " + ((chessPieceInfo.getColor() == 'w') ? "white" : "black") + " "
					+ chessPieceInfo.getNameAlone() + ", its code is " + chessPieceInfo.getCode()
					+ ".\nIts move capacity are the following :\n" + chessPieceInfo.getFancyMoveSet();

			chesstoryGame.addLogsText(info);
		}
	}

	/**
	 * Just an intermediary between Echiquier and ChesstoryGame, it tells to
	 * ChesstoryGame when a special event occurs such as castling, check, etc.
	 * 
	 * @param event
	 *            The event (a constant (take a look at ChesstoryConstant))
	 * @param s
	 *            The text to display, most of time it just tells the color of
	 *            the concerned player.
	 */
	public void eventInter(int event, String s) {
		chesstoryGame.chessEvent(event, s);
	}

	/**
	 * Everything is in the name ! Used when entering/leaving browse mode.
	 * 
	 * @param b
	 *            Represents whether we enter or leave browse mode.
	 */
	public void switchBorder(boolean b) {
		if (b) {
			chessBoard.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 255, 255), new Color(0, 255, 255),
					new Color(0, 255, 255), new Color(0, 255, 255)));
		} else {
			chessBoard.setBorder(new EmptyBorder(0, 0, 0, 0));
		}
	}

	/**
	 * Are you really reading this ? I want a brownie !! Switch the mode between
	 * browsing and playing.
	 * 
	 * @param b
	 *            Whether we can act on the chess board or not.
	 */
	public void switchClickable(boolean b) {
		isClickable = b;
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	/**
	 * Captain Obvious here : It changes the rules *flies away*<br />
	 * Used when we load a file, we wrote it for custom games, you put 6 array
	 * of string (one for each chess piece) and it cut it to change the move
	 * capacities of the chess pieces.
	 * 
	 * @param arrayRulePiece
	 *            A formated array which describes all the chess pieces (take a
	 *            look at getMoveSet() in Piece).
	 */
	public void changeRules(String[] arrayRulePiece) {
		Piece[] arrayPieces = new Piece[6];
		String name;
		char code;
		int minX, maxX, minY, maxY, minDiag, maxDiag;
		boolean backward, horse;

		try {
			for (int i = 0; i < 6; i++) {
				String[] inter = arrayRulePiece[i].split(",");
				name = inter[0];
				code = inter[1].charAt(0);
				minX = Integer.parseInt(inter[2]);
				maxX = Integer.parseInt(inter[3]);
				minY = Integer.parseInt(inter[4]);
				maxY = Integer.parseInt(inter[5]);
				minDiag = Integer.parseInt(inter[6]);
				maxDiag = Integer.parseInt(inter[7]);
				backward = Boolean.parseBoolean(inter[8]);
				horse = Boolean.parseBoolean(inter[9]);

				arrayPieces[i] = new Piece(name, code, minX, maxX, minY, maxY, minDiag, maxDiag, backward, horse);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error : Setting rules of pieces.");
		}

		ech.initPieces(arrayPieces[0], arrayPieces[1], arrayPieces[2], arrayPieces[3], arrayPieces[4], arrayPieces[5]);
	}

	/**
	 * Again an intermediary method between ChesstoryGame and Echiquier.
	 * 
	 * @return The FENcode of the current chess board.
	 */
	public String getFEN() {
		return ech.getFEN();
	}

	/**
	 * Ask Roman, the Bigfoot, I don't know what it is ...
	 * 
	 * @return The layered pane.
	 */
	public JLayeredPane CreationChessboard() {
		return layeredPane;
	}

	/**
	 * Used to save a game, this array will be on top of the save file.
	 * 
	 * @return An array with the pieces move set
	 */
	public String[] getArrayRulePiece() {
		String[] arrayPiece = new String[6];
		arrayPiece[0] = ech.getPawnSpec().getMoveSet();
		arrayPiece[1] = ech.getRookSpec().getMoveSet();
		arrayPiece[2] = ech.getQueenSpec().getMoveSet();
		arrayPiece[3] = ech.getKingSpec().getMoveSet();
		arrayPiece[4] = ech.getBishopSpec().getMoveSet();
		arrayPiece[5] = ech.getKnightSpec().getMoveSet();

		return arrayPiece;
	}

	/**
	 * Used to display the rules, it returns a string which is "readable".
	 * 
	 * @return A beautiful string which describes the chess pieces we currently
	 *         play with.
	 */
	public String getPiecesFancyMoveSet() {
		String info = "The " + ech.getKingSpec().getNameAlone() + " :\n" + ech.getKingSpec().getFancyMoveSet() + "The "
				+ ech.getQueenSpec().getNameAlone() + " :\n" + ech.getQueenSpec().getFancyMoveSet() + "The "
				+ ech.getBishopSpec().getNameAlone() + " :\n" + ech.getBishopSpec().getFancyMoveSet() + "The "
				+ ech.getKnightSpec().getNameAlone() + " :\n" + ech.getKnightSpec().getFancyMoveSet() + "The "
				+ ech.getRookSpec().getNameAlone() + " :\n" + ech.getRookSpec().getFancyMoveSet() + "The "
				+ ech.getPawnSpec().getNameAlone() + " :\n" + ech.getPawnSpec().getFancyMoveSet();

		return info;
	}

	/**
	 * We did not use it a lot cause of time and bad color tastes ...
	 * 
	 * @param theme
	 *            The theme (a constant => ChesstoryConstant).
	 */
	public void changeTheme(Color[] theme) {
		backgroundOne = theme[0];
		backgroundTwo = theme[1];
		caseAccessible = theme[2];
		caseSpec = theme[3];
		drawChessboard();
	}
}
