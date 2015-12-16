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

	private ChesstoryGame chesstoryGame;
	/**
	 * L'échiquier courrant
	 */
	static Echiquier ech;//TODO REMOVE STATIC
	
	private boolean isClickable=false;
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
					String nom = p.getNom();
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
	public void initEch(String departureFEN) {
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

		if (ech.estValideDeplacement(move)) {
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
	public YetAnotherChessGame(String fenDeDeapart, ChesstoryGame chesstoryGame ) {
		this.chesstoryGame=chesstoryGame;
		ech = new Echiquier();
		ech.setFEN(fenDeDeapart);

		// Pour le test !
		 //ech.setFEN("krq5/8/8/8/8/8/8/KQR5");

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
				square.setBackground(i % 2 == 0 ? Color.white : Color.getHSBColor(0.56f, 1.0f, 0.8f));
			} else {
				square.setBackground(i % 2 == 0 ? Color.getHSBColor(0.56f, 1.0f, 0.8f) : Color.white);
			}
		}
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
		if(isClickable){
			if (!first) {
				chessPiece.setVisible(false);
	
				JPanel panel = (JPanel) chessBoard.getComponent((8 - depart.getY() - 1) * ech.getDimX() + depart.getX());
	
				arrive = new Position((int) ((e.getX() / 600.0) * 8.0), (int) ((((600.0 - e.getY()) / 600.0) * 8.0)));
				// here we have to save the color and the piece into d in order to
				// later save it in the arraylist
	
				Deplacement d = new Deplacement(depart, arrive, ech.getPiece(depart).getCode(),
						ech.getPiece(depart).getColor());
	
				System.out.println("==> Déplacement : " + d);
	
				if (makeDeplacement(d)) {
					if ((winner = ech.verifiePartieTerminee()) != 'n') {
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
				
				if(ech.estEnEchec(ech.getTrait())){
					surbrillance(ech.rechercheRoi(ech.getTrait()), Color.MAGENTA);
				}
	
				first = true;
			} else {
				chessPiece = null;
				Component c = chessBoard.findComponentAt(e.getX(), e.getY());
				// if it is empty : do nothing
				if (c instanceof JPanel) {
					return;
				}
	
				// retrouver la case correspondante
				depart = new Position((int) ((e.getX() / 600.0) * 8.0), (int) ((((600.0 - e.getY()) / 600.0) * 8.0)));
	
				System.out.print(depart);
	
				chessPiece = (JLabel) c;
	
				// highlight the case
				surbrillance(depart, (ech.getTrait() == ech.getPiece(depart).getColor()) ? Color.cyan : Color.red);
	
				if(ech.getTrait() == ech.getPiece(depart).getColor())
					afficheLesPositionsDansLeGUI(depart);
	
				if(ech.estEnEchec(ech.getTrait())){
					surbrillance(ech.rechercheRoi(ech.getTrait()), Color.MAGENTA);
				}
				
				first = false;
			}
		}
	}
	public void switchBorder(boolean b){
		if(b){
			chessBoard.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 255, 255), new Color(0, 255, 255), new Color(0, 255, 255), new Color(0, 255, 255)));
		}else{
			chessBoard.setBorder(new EmptyBorder(0, 0, 0, 0));
		}
	}
	public void switchClickable(boolean b){
		isClickable=b;
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
}
