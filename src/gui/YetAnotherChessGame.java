package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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

public class YetAnotherChessGame extends JFrame implements MouseListener, MouseMotionListener {

	/**
	 * Paneau de fond
	 */
	JLayeredPane layeredPane;

	/**
	 * Echiquier
	 */
	JPanel chessBoard;

	/**
	 * Une pi�ce
	 */
	JLabel chessPiece;

	/**
	 * D�placement en x
	 */
	int xDeplace;
	/**
	 * D�placement en y
	 */
	int yDeplace;

	/**
	 * position de d�part
	 */
	Position depart;
	/**
	 * position d'arriv�e
	 */
	Position arrive;

	/**
	 * L'�chiquier courrant
	 */
	static Echiquier ech;

	/**
	 * First click or not
	 */
	private boolean first = true;

	/**
	 * Winner of the game
	 */
	private char winner = 'n';

	/**
	 * Efface tout l'�chiquier
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
	 * Dessine int�gralement toutes les pi�ces de l'�chiquier
	 */
	private void dessineToutesLesPieces() {
		// dessin des pi�ces
		for (int j = 0; j < ech.getDimY(); j++) {
			for (int i = 0; i < ech.getDimX(); i++) {
				Piece p = ech.getPieceCase(i, 8 - j - 1);

				if (p != null) {
					String nom = p.getNom();
					String nomComplet = "icons/" + nom + ".png";
					// java.net.URL imgURL = getClass().getResource(nomComplet);
					ClassLoader cl = this.getClass().getClassLoader();
					JLabel piece = new JLabel(new ImageIcon(cl.getResource(nomComplet)));
					// JLabel piece = new JLabel(new
					// ImageIcon(this.getClass().getResource("/Ressources/"+nomComplet)));
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

	private void surbrillance(Position pos, Color couleur) {
		JPanel panel = (JPanel) chessBoard.getComponent((8 - pos.getY() - 1) * ech.getDimX() + pos.getX());
		panel.setBackground(couleur);
	}

	public void afficheLesPositionsDansLeGUI(Position p) {
		ArrayList<Position> array = ech.affichePositionAccessibles(p);

		for (int i = 0; i < array.size(); i++) {
			surbrillance(array.get(i), Color.ORANGE);
		}
	}

	private void dessinEchiquier() {
		for (int j = 0; j < ech.getDimY(); j++) {
			for (int i = 0; i < ech.getDimX(); i++) {
				JPanel panel = (JPanel) chessBoard.getComponent(j * ech.getDimX() + i);
				if (i % 2 == 0) {
					panel.setBackground(j % 2 == 0 ? Color.white : Color.getHSBColor(0.56f, 1.0f, 0.8f));
				} else {
					panel.setBackground(j % 2 == 0 ? Color.getHSBColor(0.56f, 1.0f, 0.8f) : Color.white);
				}
			}
		}
	}

	/**
	 * Verification et appliction d'un deplacement
	 * 
	 * @param move
	 *            Le d�placement
	 * @param c
	 * @return La validit� du d�placement
	 */
	public boolean makeDeplacement(Deplacement move, Component c) {
		if (ech.estValideDeplacement(move)) {
			ech.executeDeplacement(move);
			if (ech.isPromotion()) {
				String nom = ech.getPiece(move.getArrive()).getNom();
				Container parent = (Container) c;

				chessPiece = new JLabel(new ImageIcon("icons/" + nom + ".png"));
				parent.add(chessPiece);
			}

			// cas d'une prise
			if (c instanceof JLabel) {
				Container parent = c.getParent();
				parent.remove(0);
				parent.add(chessPiece);
			} else {
				Container parent = (Container) c;
				parent.add(chessPiece);
			}

			if (ech.petitRoqueEnCours() || ech.grandRoqueEnCours() || ech.priseEnPassantEnCours()) {
				redessine();
			}

			chessPiece.setVisible(true);
			return true;
		} else
			return false;
	}

	/**
	 * Constructeur
	 */
	public YetAnotherChessGame(String fenDeDeapart) {
		ech = new Echiquier();
		ech.setFEN(fenDeDeapart);

		// Pour le test !
		// ech.setFEN("krq5/8/8/8/8/8/8/KQR5");

		Dimension boardSize = new Dimension(600, 600);

		// Use a Layered Pane for this this application
		layeredPane = new JLayeredPane();
		// panel.add(layeredPane);

		layeredPane.setPreferredSize(boardSize);
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);

		// Add a chess board to the Layered Pane
		chessBoard = new JPanel();
		layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		chessBoard.setLayout(new GridLayout(8, 8));
		chessBoard.setPreferredSize(boardSize);
		chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

		// dessin de l'�quiquier
		for (int i = 0; i < 64; i++) {
			JPanel square = new JPanel(new BorderLayout());
			chessBoard.add(square);

			int row = (i / 8) % 2;
			if (row == 0) {
				square.setBackground(i % 2 == 0 ? Color.white : Color.getHSBColor(0.56f, 1.0f, 0.8f));
			} else {
				square.setBackground(i % 2 == 0 ? Color.getHSBColor(0.56f, 1.0f, 0.8f) : Color.white);
			}
		}
		dessineToutesLesPieces();
	}

	/**
	 * M�thode appel�e lorsque la souris est cliqu�e
	 *
	 * @param e
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent me) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!first) {
			Component c = chessBoard.findComponentAt(e.getX(), e.getY());
			chessPiece.setVisible(false);

			JPanel panel = (JPanel) chessBoard.getComponent((8 - depart.getY() - 1) * ech.getDimX() + depart.getX());

			arrive = new Position((int) ((e.getX() / 600.0) * 8.0), (int) ((((600.0 - e.getY()) / 600.0) * 8.0)));
			// here we have to save the color and the piece into d in order to
			// later save it in the arraylist

			Deplacement d = new Deplacement(depart, arrive, ech.getPiece(depart).getCode(),
					ech.getPiece(depart).getColor());

			System.out.println("==> D�placement : " + d);

			if (makeDeplacement(d, c)) {
				if ((winner = ech.verifiePartieTerminee()) != 'n') {
					System.out.println("GG aux " + winner);
					System.exit(0);
				}
			} else {
				// replacer sur la case de d�part
				panel.add(chessPiece);
				chessPiece.setVisible(true);
			}

			// Remettre la couleur d'origine
			dessinEchiquier();

			first = true;
		} else {
			chessPiece = null;
			Component c = chessBoard.findComponentAt(e.getX(), e.getY());
			// si la case est vide : rien � faire
			if (c instanceof JPanel) {
				return;
			}

			// retrouver la case correspondante
			depart = new Position((int) ((e.getX() / 600.0) * 8.0), (int) ((((600.0 - e.getY()) / 600.0) * 8.0)));

			System.out.print(depart);

			Point parentLocation = c.getParent().getLocation();

			xDeplace = parentLocation.x - e.getX();
			yDeplace = parentLocation.y - e.getY();
			chessPiece = (JLabel) c;
			chessPiece.setLocation(e.getX() + xDeplace, e.getY() + yDeplace);
			chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());

			// Mise en "surbrillance" de la case
			if (ech.getPiece(depart).estBlanc())
				surbrillance(depart, ((ech.getTrait() == 'w') ? Color.cyan : Color.red));
			else
				surbrillance(depart, ((ech.getTrait() == 'w') ? Color.red : Color.cyan));

			// Mise en surbrillance des cases accessibles
			afficheLesPositionsDansLeGUI(depart);

			layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);

			first = false;
		}
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

	public JLayeredPane CreationChessboard() {

		return layeredPane;
	}

	/**
	 * Methode effectuant et testant un deplacement sans gerer l'interface
	 * graphique
	 * 
	 * @param d Deplacement a tester et effectuer
	 * @return True si test concluant, False sinon
	 */
	public boolean makeMoveUI(Deplacement d) {
		if (ech.estValideDeplacement(d)) {
			ech.executeDeplacement(d);
			if (ech.isPromotion()) {
			//	String nom = ech.getPiece(move.getArrive()).getNom();

				//chessPiece = new JLabel(new ImageIcon("icons/" + nom + ".png"));
				//parent.add(chessPiece);
			}


			if (ech.petitRoqueEnCours() || ech.grandRoqueEnCours() || ech.priseEnPassantEnCours()) {
				redessine();
			}

			return true;
		} else
			return false;	
	}
}
