package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Controller.ChesstoryGame;
import echecs.Deplacement;
import echecs.Echiquier;
import echecs.Piece;
import echecs.Position;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe principale pour l'interface graphique
 *
 * @author samuel
 */
import java.util.ArrayList;

import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import static Controller.ChesstoryConstants.*;

@SuppressWarnings("serial")
public final class YetAnotherChessGame extends JFrame implements MouseListener, MouseMotionListener {

	/**
	 * Paneau de fond
	 */
	JLayeredPane layeredPane;

	/**
	 * Echiquier
	 */
	JPanel chessBoard;

	/**
	 * Une pièce
	 */
	JLabel chessPiece;

	/**
	 * Déplacement en x
	 */
	int xDeplace;
	/**
	 * Déplacement en y
	 */
	int yDeplace;

	/**
	 * position de départ
	 */
	Position depart;
	/**
	 * position d'arrivée
	 */
	Position arrive;

	/**
	 * Color themes
	 */
	Color backgroundOne, backgroundTwo, caseSpec, caseAccessible;
	private final static Color[] DEFAULT_THEM = { Color.getHSBColor(0.56f, 1.0f, 0.8f), Color.white, Color.YELLOW,
			Color.ORANGE };
	private final static Color[] SHATRANJ_THEM = { Color.white, Color.white, Color.YELLOW, Color.ORANGE };

	private ChesstoryGame chesstoryGame;
	/**
	 * L'échiquier courrant
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
	 * Efface tout l'échiquier
	 */
	private void videEchiquier() {
		for (int j = 0; j < ech.getDimY(); j++) {
			for (int i = 0; i < ech.getDimX(); i++) {
				JPanel panel = (JPanel) chessBoard.getComponent(j * ech.getDimX() + i);
				panel.removeAll();
				panel.repaint();
			}
		}
	}

	/**
	 * Dessine intégralement toutes les pièces de l'échiquier
	 */
	private void dessineToutesLesPieces() {
		// dessin des pièces
		for (int j = 0; j < ech.getDimY(); j++) {
			for (int i = 0; i < ech.getDimX(); i++) {
				Piece p = ech.getPieceCase(i, 8 - j - 1);

				if (p != null) {
					String nom = p.getName();
					String nomComplet = "icons/" + nom + ".png";
					ClassLoader cl = this.getClass().getClassLoader();
					JLabel piece = new JLabel(new ImageIcon(cl.getResource(nomComplet)));
					JPanel panel = (JPanel) chessBoard.getComponent(j * ech.getDimX() + i);
					panel.add(piece);
				}
			}
		}
	}

	/**
	 * Vide et dessine
	 */
	private void redessine() {
		videEchiquier();
		dessineToutesLesPieces();
	}

	/**
	 * Empty and repaint the chessBoard with the entered FEN code
	 * 
	 * @param departureFEN
	 *            FEN code used to repaint the chessBoard
	 */
	public void makeDrawFen(String departureFEN) {
		videEchiquier();
		ech.setFEN(departureFEN);
		dessineToutesLesPieces();
		chessBoard.revalidate();
		chessBoard.repaint();
	}

	private void surbrillance(Position pos, Color couleur) {
		JPanel panel = (JPanel) chessBoard.getComponent((8 - pos.getY() - 1) * ech.getDimX() + pos.getX());
		panel.setBackground(couleur);
	}

	public void afficheLesPositionsDansLeGUI(Position p) {
		ArrayList<Position> array = ech.affichePositionAccessibles(p);
		ArrayList<Position> arraySpec = ech.showSpecPositions(p);

		// NORMAL
		for (int i = 0; i < array.size(); i++) {
			surbrillance(array.get(i), caseAccessible/* new Color(0xfc913a *//* 0x74d600 *//* ) */);
		}
		// TRISOMIQUE
		for (int i = 0; i < arraySpec.size(); i++) {
			surbrillance(arraySpec.get(i), caseSpec /* new Color( *//* 0xf9d62e *//* 0xadff00 *//* ) */);
		}
	}

	private void dessinEchiquier() {
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
		redessine();
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
			redessine();
			chesstoryGame.addMoveMadeByPlayer(move);
			return true;
		} else
			return false;
	}

	/**
	 * Constructeur
	 */
	public YetAnotherChessGame(String fenDeDeapart, ChesstoryGame chesstoryGame) {
		// TODO a good looking shit to initialize pieces :3

		this.chesstoryGame = chesstoryGame;
		// ech = new Echiquier();

		changeTheme(DEFAULT_THEM);

		// This a test with the classical chess pieces
		ech = new Echiquier(new Piece("pion", 'p', 0, 0, 1, 1, 0, 0, false, false),
				new Piece("dame", 'q', true, true, true, true, false),
				new Piece("roi", 'k', 1, 1, 1, 1, 1, 1, true, false), new Piece("cavalier", 'n', true),
				new Piece("fou", 'b', false, false, true, true, false),
				new Piece("tour", 'r', true, true, false, true, false), this);

		ech.setFEN(fenDeDeapart);

		// Pour le test !
		// ech.setFEN("kqqqqqqq/8/8/5b2/pppppppp/8/8/K5PP");

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
			} else {
				square.setBackground(i % 2 == 0 ? backgroundOne : backgroundTwo);
			}
		}
		// tu pues du cul
		dessineToutesLesPieces();
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
	 * Méthode appelée lorsque la souris est cliquée
	 *
	 * @param e
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
							System.out.println("GG aux " + winner);
							System.exit(0);
						}

					} else {
						// replacer sur la case de départ
						panel.add(chessPiece);
						chessPiece.setVisible(true);
					}

					// Remettre la couleur d'origine
					dessinEchiquier();

					// Put king in a yoloswaggy color if echec
					if (ech.isInCheck(ech.getTrait())) {
						eventInter(CHESS_EVENT_ECHEC,
								((ech.getTrait() == 'w') ? "In favor of black." : "In favor of white."));
						surbrillance(ech.searchKing(ech.getTrait()), Color.MAGENTA);
					}
					first = true;
				} else {
					dessinEchiquier();
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
					surbrillance(depart, (ech.getTrait() == ech.getPiece(depart).getColor()) ? Color.cyan : Color.red);

					if (ech.getTrait() == ech.getPiece(depart).getColor())
						afficheLesPositionsDansLeGUI(depart);

					if (ech.isInCheck(ech.getTrait())) {
						surbrillance(ech.searchKing(ech.getTrait()), Color.MAGENTA);
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
					+ ".\nIts move capacity are the following :\n";

			if (chessPieceInfo.isHorse())
				info += "  - Knight's specificities (1 cell in a direction, 2 in the other).\n";

			if (chessPieceInfo.getMaxX() == 0)
				info += "  - It can't move horizontally.\n";
			else
				info += "  - It can moves from " + chessPieceInfo.getMinX() + " cell(s) to " + chessPieceInfo.getMaxX()
						+ " horizontally.\n";

			if (chessPieceInfo.getMaxY() == 0)
				info += "  - It can't move vertically.\n";
			else
				info += "  - It can moves from " + chessPieceInfo.getMinY() + " cell(s) to " + chessPieceInfo.getMaxY()
						+ " vertically.\n";

			if (chessPieceInfo.getMaxDiag() == 0)
				info += "  - It can't move diagonnally.\n";
			else
				info += "  - It can moves from " + chessPieceInfo.getMinDiag() + " cell(s) to "
						+ chessPieceInfo.getMaxDiag() + " diagonnally.\n";
			
			if(chessPieceInfo.getBackward())
				info += "  - It can move backward.\n";
			else
				info += "  - It can't move backward.\n";

			chesstoryGame.addLogsText(info);
		}
	}

	public void eventInter(int event, String s) {
		chesstoryGame.chessEvent(event, s);
	}

	public void switchBorder(boolean b) {
		if (b) {
			chessBoard.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 255, 255), new Color(0, 255, 255),
					new Color(0, 255, 255), new Color(0, 255, 255)));
		} else {
			chessBoard.setBorder(new EmptyBorder(0, 0, 0, 0));
		}
	}

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

	public String getFEN() {
		return ech.getFEN();
	}

	public JLayeredPane CreationChessboard() {
		return layeredPane;
	}

	/**
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

	public void changeTheme(Color[] theme) {
		backgroundOne = theme[0];
		backgroundTwo = theme[1];
		caseAccessible = theme[2];
		caseSpec = theme[3];
	}
}
