/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import gui.YetAnotherChessGame;

import Controller.ChesstoryGame;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author samuel
 */
public class Echiquier {

	private Case[][] c;
	private int dimX, dimY;

	char trait;

	// false par défaut
	boolean roqueK;
	boolean roquek;
	boolean roqueQ;
	boolean roqueq;

	// pour la fin de partie
	private Piece[] existantPieces;
	private final int nbMaxPiece = 30;

	// pour la promotion
	boolean promotion = false;
	private boolean petitRoqueEnCours = false;
	private boolean grandRoqueEnCours = false;
	private boolean priseEnPassantEnCours = false;

	// position de la prise en passant éventuelle
	Position priseEnPassant;

	/**
	 * Constructeur par défaut echiquier 8x8
	 */
	public Echiquier() {
		this(8, 8);
	}

	private void vider() {
		for (int i = 0; i < dimX; i++) {
			for (int j = 0; j < dimY; j++) {
				c[i][j] = new Case();
			}
		}
	}

	/**
	 * Construit un échiquier de taille spécifiée
	 *
	 * @param dimX
	 * @param dimY
	 */
	public Echiquier(int dimX, int dimY) {
		this.dimX = dimX;
		this.dimY = dimY;

		this.trait = 'w';

		existantPieces = new Piece[nbMaxPiece];

		c = new Case[dimX][dimY];
		vider();

	}

	public Echiquier(Echiquier ech) {
		this.dimX = ech.dimX;
		this.dimY = ech.dimY;
		this.trait = ech.trait;

		existantPieces = new Piece[nbMaxPiece];

		c = new Case[dimX][dimY];
		for (int i = 0; i < dimX; i++) {
			for (int j = 0; j < dimY; j++) {
				c[i][j] = new Case(ech.getCase(i, j));
			}
		}
	}

	public Piece getPieceCase(int i, int j) {
		return c[i][j].getPiece();
	}

	public Piece getPieceCase(Position p) {
		return c[p.getX()][p.getY()].getPiece();
	}

	public char getTrait() {
		return trait;
	}

	public boolean estVideCase(Position p) {
		return getPieceCase(p) == null;
	}

	public int getDimX() {
		return dimX;
	}

	public int getDimY() {
		return dimY;
	}

	public Case getCase(int i, int j) {
		return c[i][j];
	}

	/**
	 * Positionne une pièce à une place
	 *
	 * @param p
	 *            La piece
	 * @param x
	 *            colonne
	 * @param y
	 *            ligne
	 */
	void place(Piece p, int x, int y) {
		c[x][y].setPiece(p);
	}

	/**
	 * Permet de retourner la pièce en fonction de la position
	 *
	 * @param x
	 *            colonne
	 * @param y
	 *            ligne
	 * @return la pièce (null si la case est vide)
	 */
	public Piece getPiece(int x, int y) {
		return c[x][y].getPiece();
	}

	public Piece getPiece(Position p) {
		return getPiece(p.getX(), p.getY());
	}

	/**
	 * Entrée du code FEN
	 *
	 * @param FENcode
	 */
	public void setFEN(String FENcode) {

		// vider l'échiquier
		vider();

		try {
			String[] code = FENcode.split(" ");

			String[] ligne = code[0].split("/");

			// Traitement de la première partie du code
			for (int i = 0; i < ligne.length; i++) {

				int decal = 0;
				for (int j = 0; j < ligne[i].length(); j++) {

					char l = ligne[i].charAt(j);

					if (Character.isDigit(l)) {
						decal += (Character.getNumericValue(l) - 1);
					} else {
						int pos = j + (decal);
						switch (l) {
						case 'k':
							c[pos][dimY - i - 1].setPiece(new Piece("roi", 'k'));
							break;
						case 'K':
							c[pos][dimY - i - 1].setPiece(new Piece("roi", 'K'));
							break;
						case 'q':
							c[pos][dimY - i - 1].setPiece(new Piece("dame", 'q'));
							break;
						case 'Q':
							c[pos][dimY - i - 1].setPiece(new Piece("dame", 'Q'));
							break;
						case 'r':
							c[pos][dimY - i - 1].setPiece(new Piece("tour", 'r'));
							break;
						case 'R':
							c[pos][dimY - i - 1].setPiece(new Piece("tour", 'R'));
							break;
						case 'b':
							c[pos][dimY - i - 1].setPiece(new Piece("fou", 'b'));
							break;
						case 'B':
							c[pos][dimY - i - 1].setPiece(new Piece("fou", 'B'));
							break;
						case 'n':
							c[pos][dimY - i - 1].setPiece(new Piece("cavalier", 'n'));
							break;
						case 'N':
							c[pos][dimY - i - 1].setPiece(new Piece("cavalier", 'N'));
							break;
						case 'p':
							c[pos][dimY - i - 1].setPiece(new Piece("pion", 'p'));
							break;
						case 'P':
							c[pos][dimY - i - 1].setPiece(new Piece("pion", 'P'));
							break;

						}
					}
				}
			}

			// seconde partie
			try {
				if (code[1].equals("w")) {
					trait = 'w';
				} else if (code[1].equals("b")) {
					trait = 'b';
				} else {
					throw (new MalformedFENException("le second champs doit etre b ou w et non " + code[1]));
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				// sans indication, le trait est au blancs
				trait = 'w';
			}

			// troisième partie
			try {
				if (code[2].contains("k")) {
					roquek = true;
				}
				if (code[2].contains("K")) {
					roqueK = true;
				}
				if (code[2].contains("q")) {
					roqueq = true;
				}
				if (code[2].contains("Q")) {
					roqueQ = true;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				// sans indication, les roques sont possibles
				roquek = roqueK = roqueq = roqueQ = true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw (new MalformedFENException("le code " + FENcode + " n'est pas un code FEN valide"));
		}

		construitPositionsAccessibles();

	}

	/**
	 * Sortie fen
	 *
	 * @return chaine fen qui représente l'échiquier
	 */
	public String getFEN() {
		String res = "";

		for (int j = (dimY - 1); j >= 0; j--) {
			int compteVide = 0;
			for (int i = 0; i < dimX; i++) {
				Piece p = c[i][j].getPiece();

				if (c[i][j].getPiece() != null) {

					if (compteVide != 0) {
						res += compteVide;
					}
					res += p.getCode();
					compteVide = 0;
				} else {
					compteVide++;
				}
			}
			if (compteVide != 0) {
				res += compteVide;
			}
			if (j != 0) {
				res += "/";
			}
		}

		res += (trait == 'w' ? " w" : " b");
		res += " ";

		if (roquek || roqueK || roqueq || roqueQ) {
			if (roqueK) {
				res += "K";
			}
			if (roqueQ) {
				res += "Q";
			}
			if (roquek) {
				res += "k";
			}
			if (roqueq) {
				res += "q";
			}
		} else {
			res += "-";
		}

		res += " ";

		res += priseEnPassant == null ? "-" : priseEnPassant;

		return res;
	}

	/**
	 * Sortie texte
	 *
	 * @return chaine de caractère qui représente l'échiquier
	 */
	@Override
	public String toString() {
		String res = "";
		for (int j = dimY - 1; j >= 0; j--) {
			for (int i = 0; i < dimX; i++) {
				res += (c[i][j]);
			}
			res += "\n";
		}
		return res;
	}

	/*
	 * private boolean estValideDeplacementTour(Deplacement d){ // déplacement
	 * sur une colonne if (Geometrie.estSurColonne(d)){ int pas = (d.getY1() >
	 * d.getY2() ) ? 1 : -1;
	 * 
	 * 
	 * 
	 * for (y=d.getY1()+pas; y!=getY2; y+=pas) } }
	 */
	/**
	 * Méthode qui indique si un déplacement est valide ou non
	 *
	 * @param d
	 *            Deplacement
	 * @return true si valide sur l'echiquier false sinon
	 */
	public boolean estValideDeplacement(Deplacement d) {

		int x1 = d.getX1();
		int y1 = d.getY1();
		int x2 = d.getX2();
		int y2 = d.getY2();

		// si la case de départ = la case d'arrivée, ce n'est pas un déplacement
		if (x1 == x2 && y1 == y2) {
			return false;
		}

		// si la case de départ est vide, ce n'est pas un déplacement
		if (c[x1][y1].getPiece() == null) {
			return false;
		}

		if (c[x1][y1].estVide()) {
			return false;
		}

		// si ce n'est pas le tour du joueur
		if ((c[x1][y1].getPiece().estBlanc() && trait == 'b') || (c[x1][y1].getPiece().estNoir() && trait == 'w')) {
			return false;
		}

		// System.out.println("# Positions accessibles pour
		// "+c[x1][y1].getPiece().getNom());
		// System.out.println(c[x1][y1].getPiece().accessible);
		// partie verification pour la piece
		if (c[x1][y1].getPiece().getAccessible().contains(new Position(x2, y2))) {

			// vérification de la validité du roque
			if (Character.toUpperCase(c[x1][y1].getPiece().getCode()) == 'K') {

				int depl = d.deplacementHorizontal();

				if (Math.abs(depl) == 2) {

					Position caseDePassage = new Position(d.getX1() + (depl / 2), d.getY1());
					Deplacement passage = new Deplacement(d.getDepart(), caseDePassage);

					// System.out.println("Case de passage = "+caseDePassage);
					if (!verifiePasEchecsApres(passage)) {
						return false;
					}

					if (!estVideCase(caseDePassage)) {
						return false;
					}

					if (depl == 2 && c[x1][y1].getPiece().estBlanc() && !roqueK) {
						return false;
					}

					if (depl == 2 && c[x1][y1].getPiece().estNoir() && !roquek) {
						return false;
					}

					if (depl == -2 && c[x1][y1].getPiece().estBlanc() && !roqueQ) {
						return false;
					}

					if (depl == -2 && c[x1][y1].getPiece().estNoir() && !roqueq) {
						return false;
					}

				}

			}

			return verifiePasEchecsApres(d);

		}

		return false;
	}

	/**
	 * Execute le déplacement (après avoir vérifié sa validité)
	 *
	 *
	 * @param d
	 *            déplacement à executer
	 */
	public void executeDeplacement(Deplacement d) {

		if (estValideDeplacement(d)) {

			int x1 = d.getX1();
			int y1 = d.getY1();
			int x2 = d.getX2();
			int y2 = d.getY2();

			Piece piece = c[x1][y1].getPiece();
			char codePiece = piece.getCode();
			char joueur = 'e';

			// test de ma couleur
			if (codePiece >= 97 && codePiece <= 122) {// alors on est en
														// minuscule
				joueur = 'b';
			} else if (codePiece >= 65 && codePiece <= 90) {
				joueur = 'w';
			}

			promotion = false;
			// cas du pion noir sur première rangée
			if (codePiece == 'p' && (y2 == 0)) {
				promotion = true;
				piece = new Piece("dame", 'q');
			}
			// cas du pion blanc sur dernière rangée
			if (codePiece == 'P' && (y2 == 7)) {
				promotion = true;
				piece = new Piece("dame", 'Q');
			}

			// autoriser la prise en passant si et seulement si avance de 2 pour
			// un pion
			priseEnPassant = null;

			if ((codePiece == 'P') && y1 == 1 && y2 == 3) {
				priseEnPassant = new Position(x1, y1 + 1);
			}
			if ((codePiece == 'p') && y1 == 6 && y2 == 4) {
				priseEnPassant = new Position(x1, y1 - 1);
			}

			// cas de la prise en passant
			if (Character.toUpperCase(codePiece) == 'P' && y1 != y2 && c[x2][y2].estVide()) {

				priseEnPassantEnCours = true;

				c[x2][y1].vider();
			}

			// détection du roque
			if (Character.toUpperCase(codePiece) == 'K') {

				if (d.deplacementHorizontal() == 2) {

					petitRoqueEnCours = true;

					// petit roque blanc
					if (codePiece == 'K') {
						c[7][0].vider();
						c[5][0].setPiece(new Piece("tour", 'R'));
					}

					// petit roque noir
					if (codePiece == 'k') {
						c[7][7].vider();
						c[5][7].setPiece(new Piece("tour", 'r'));
					}
				} else {
					petitRoqueEnCours = false;
				}

				if (d.deplacementHorizontal() == -2) {

					grandRoqueEnCours = true;

					// grand roque blanc
					if (codePiece == 'K') {

						c[0][0].vider();
						c[3][0].setPiece(new Piece("tour", 'R'));

					}

					// grand roque noir
					if (codePiece == 'k') {
						c[0][7].vider();
						c[3][7].setPiece(new Piece("tour", 'r'));

					}

				} else {
					grandRoqueEnCours = false;
				}
			}

			// Pour interdir les futurs roques...
			if ((codePiece == 'R') && (x1 == 0)) {
				roqueQ = false;
			}

			if ((codePiece == 'r') && (x1 == 0)) {
				roqueq = false;
			}

			if ((codePiece == 'R') && (x1 == 7)) {
				roqueK = false;
			}

			if ((codePiece == 'r') && (x1 == 7)) {
				roquek = false;
			}

			if (codePiece == 'K') {
				roqueQ = false;
				roqueK = false;
			}

			if (codePiece == 'k') {
				roqueq = false;
				roquek = false;
			}

			c[x2][y2].setPiece(piece);
			c[x1][y1].vider();

			// Ajout dans ArrayList
			ChesstoryGame.addMove(d);
			// ici on affiche dans le textArea
			ChesstoryGame.addLogsMove(d, piece.getNom(), joueur);
			// ChesstoryGame.addLogsText("Deplacement : ("+x1+","+y1+") ,
			// ("+x2+","+y2+")");

		}

		// on change le trait
		trait = (trait == 'w' ? 'b' : 'w');

		construitPositionsAccessibles();

		System.out.println(this.getFEN());

	}

	/**
	 * effectue un déplacement sans contrôle de validité
	 *
	 *
	 * @param d
	 *            Déplacement qui doit être effectué
	 */
	public void forceDeplacement(Deplacement d) {
		int x1 = d.getX1();
		int y1 = d.getY1();
		int x2 = d.getX2();
		int y2 = d.getY2();

		Piece piece = c[x1][y1].getPiece();
		c[x2][y2].setPiece(piece);
		c[x1][y1].vider();
	}

	/**
	 * Retourne un échiquier sur lequel le déplacement a été réalisé
	 *
	 * @param d
	 * @return
	 */
	public Echiquier simuleDeplacement(Deplacement d) {
		Echiquier ech = new Echiquier(this);
		ech.executeDeplacement(d);

		return ech;
	}

	/**
	 * Execute le déplacement (après avoir vérifié sa validité)
	 *
	 * @param x1
	 *            x de départ
	 * @param y1
	 *            y de départ
	 * @param x2
	 *            x d'arrivée
	 * @param y2
	 *            y d'arrivée
	 */
	/*
	 * public void executeDeplacement(int x1, int y1, int x2, int y2) {
	 * executeDeplacement(new Deplacement(x1, y1, x2, y2)); }
	 */
	/**
	 * Execute le déplacement (après avoir vérifié sa validité). La chaine de
	 * caractère représente le dépoe
	 *
	 * @param s
	 */
	public void executeDeplacement(String s) {
		executeDeplacement(new Deplacement(s));
	}

	/**
	 * Ajoute à la case toutes les cases qui lui sont accessibles en ligne
	 * droite dans une direction donnée
	 *
	 * @param i
	 * @param j
	 * @param di
	 * @param dj
	 */
	public void accessibleEnLigneDroite(int i, int j, int di, int dj) {

		int posi = i + di;
		int posj = j + dj;

		boolean continuer = true;

		while (continuer) {

			if (posi < 0 || posi >= dimX || posj < 0 || posj >= dimY) {
				continuer = false;
			} else // si la case de destination est vide
			{
				if (c[posi][posj].estVide()) {
					c[i][j].getPiece().addCaseAccessible(new Position(posi, posj));
					posi += di;
					posj += dj;
				} else {
					// si elle est occupée par une case de couleur différente
					if ((c[i][j].getPiece().estBlanc() && c[posi][posj].getPiece().estNoir())
							|| (c[i][j].getPiece().estNoir() && c[posi][posj].getPiece().estBlanc())) {
						c[i][j].getPiece().addCaseAccessible(new Position(posi, posj));
					}
					continuer = false;
				}
			}
		}
	}

	public void accessibleTour(int i, int j) {
		accessibleEnLigneDroite(i, j, 0, 1);
		accessibleEnLigneDroite(i, j, 0, -1);
		accessibleEnLigneDroite(i, j, 1, 0);
		accessibleEnLigneDroite(i, j, -1, 0);
	}

	public void accessibleFou(int i, int j) {
		accessibleEnLigneDroite(i, j, 1, 1);
		accessibleEnLigneDroite(i, j, -1, -1);
		accessibleEnLigneDroite(i, j, 1, -1);
		accessibleEnLigneDroite(i, j, -1, 1);
	}

	public boolean existeCase(int i, int j) {
		return (i >= 0 && i < dimX && j >= 0 && j < dimY);
	}

	public boolean existeEtVide(int i, int j) {
		return existeCase(i, j) && (c[i][j].estVide());
	}

	public boolean existeEtLibre(int i, int j, boolean blanc) {
		// il faut que la case existe, soit libre ou occupée par une pièce
		// de couleur différente
		return existeCase(i, j) && (c[i][j].estVide() || (c[i][j].getPiece().estBlanc() != blanc));
	}

	public void accessibleCavalier(int i, int j) {
		boolean blanc = c[i][j].getPiece().estBlanc();

		// Les 8 positions du cavalier
		if (existeEtLibre(i + 1, j + 2, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 1, j + 2));
		}

		if (existeEtLibre(i + 2, j + 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 2, j + 1));
		}

		if (existeEtLibre(i - 1, j - 2, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 1, j - 2));
		}

		if (existeEtLibre(i - 2, j - 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 2, j - 1));
		}

		if (existeEtLibre(i + 1, j - 2, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 1, j - 2));
		}

		if (existeEtLibre(i + 2, j - 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 2, j - 1));
		}

		if (existeEtLibre(i - 1, j + 2, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 1, j + 2));
		}

		if (existeEtLibre(i - 2, j + 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 2, j + 1));
		}

	}

	public void accessibleRoi(int i, int j) {
		boolean blanc = c[i][j].getPiece().estBlanc();

		// Les 8 positions du roi
		if (existeEtLibre(i, j + 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i, j + 1));
		}

		if (existeEtLibre(i, j - 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i, j - 1));
		}

		if (existeEtLibre(i + 1, j, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 1, j));
		}

		if (existeEtLibre(i - 1, j, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 1, j));
		}

		if (existeEtLibre(i + 1, j + 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 1, j + 1));
		}

		if (existeEtLibre(i + 1, j - 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 1, j - 1));
		}

		if (existeEtLibre(i - 1, j - 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 1, j - 1));
		}

		if (existeEtLibre(i - 1, j + 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 1, j + 1));
		}

		// roques
		if ((blanc && (i == 4) && (j == 0)) || (!blanc && (i == 4) && (j == 7))) {
			// System.out.println("roi se déplace depuis sa position de
			// départ");
			c[i][j].getPiece().addCaseAccessible(new Position(i + 2, j));
			c[i][j].getPiece().addCaseAccessible(new Position(i - 2, j));

		}
	}

	public void accessiblePionBlanc(int i, int j) {

		// déplacement normal
		if (existeEtVide(i, j + 1)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i, j + 1));

			// premier déplacement
			if ((j == 1) && existeEtVide(i, j + 2)) {
				c[i][j].getPiece().addCaseAccessible(new Position(i, j + 2));
			}
		}

		// prise à gauche
		try {
			if (c[i - 1][j + 1].getPiece().estNoir()) {
				c[i][j].getPiece().addCaseAccessible(new Position(i - 1, j + 1));
			}
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			// rien à faire puisque la pièce ou la case n'existe pas...
		}

		// prise à droite
		try {
			if (c[i + 1][j + 1].getPiece().estNoir()) {
				c[i][j].getPiece().addCaseAccessible(new Position(i + 1, j + 1));
			}
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			// rien à faire puisque la pièce ou la case n'existe pas...
		}

		// prise en passant à gauche
		try {
			if ((j == 4) && c[i - 1][4].getPiece().getCode() == 'p') {
				Position pep = new Position(i - 1, 5);
				if (pep.equals(priseEnPassant)) {
					c[i][j].getPiece().addCaseAccessible(pep);
				}
			}
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			// rien à faire puisque la pièce ou la case n'existe pas...
		}

		// prise en passant à droite
		try {
			if ((j == 4) && c[i + 1][4].getPiece().getCode() == 'p') {
				Position pep = new Position(i + 1, 5);
				if (pep.equals(priseEnPassant)) {
					c[i][j].getPiece().addCaseAccessible(pep);
				}
			}
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			// rien à faire puisque la pièce ou la case n'existe pas...
		}

	}

	public void accessiblePionNoir(int i, int j) {

		if (existeEtVide(i, j - 1)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i, j - 1));

			if (j == (dimY - 2) && existeEtVide(i, j - 2)) {
				c[i][j].getPiece().addCaseAccessible(new Position(i, j - 2));
			}
		}

		// prise à gauche
		try {
			if (c[i - 1][j - 1].getPiece().estBlanc()) {
				c[i][j].getPiece().addCaseAccessible(new Position(i - 1, j - 1));
			}
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			// rien à faire puisque la pièce ou la case n'existe pas...
		}

		// prise à droite
		try {
			if (c[i + 1][j - 1].getPiece().estBlanc()) {
				c[i][j].getPiece().addCaseAccessible(new Position(i + 1, j - 1));
			}
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			// rien à faire puisque la pièce ou la case n'existe pas...
		}

		// prise en passant à gauche
		try {
			if ((j == 3) && c[i - 1][3].getPiece().getCode() == 'P') {
				Position pep = new Position(i - 1, 2);
				if (pep.equals(priseEnPassant)) {
					c[i][j].getPiece().addCaseAccessible(pep);
				}
			}
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			// rien à faire puisque la pièce ou la case n'existe pas...
		}

		// prise en passant à droite
		try {
			if ((j == 3) && c[i + 1][3].getPiece().getCode() == 'P') {
				Position pep = new Position(i + 1, 2);
				if (pep.equals(priseEnPassant)) {
					c[i][j].getPiece().addCaseAccessible(pep);
				}
			}
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			// rien à faire puisque la pièce ou la case n'existe pas...
		}

	}

	/**
	 * est accessible "naivement" (sans vérification de l'échec)
	 *
	 *
	 */
	public void construitPositionsAccessibles() {

		// Premier parcours "naïf"
		for (int j = 0; j < dimY; j++) {
			for (int i = 0; i < dimX; i++) {
				Piece p = c[i][j].getPiece();
				if (p != null) {

					p.videAccessible();
					if ((p.estBlanc() && trait == 'w') || (p.estNoir() && trait == 'b')) {

						if (p.getCode() == 'P') {
							accessiblePionBlanc(i, j);
						}
						if (p.getCode() == 'p') {
							accessiblePionNoir(i, j);
						}
						if (Character.toLowerCase(p.getCode()) == 'r') {
							accessibleTour(i, j);
						}

						if (Character.toLowerCase(p.getCode()) == 'b') {
							accessibleFou(i, j);
						}

						if (Character.toLowerCase(p.getCode()) == 'q') {
							accessibleTour(i, j);
							accessibleFou(i, j);
						}

						if (Character.toLowerCase(p.getCode()) == 'n') {
							accessibleCavalier(i, j);
						}

						if (Character.toLowerCase(p.getCode()) == 'k') {
							accessibleRoi(i, j);
						}
					}

				}
			}
		}
	}

	public ArrayList<Position> affichePositionAccessibles(Position posPiece) {
		int x = posPiece.getX();
		int y = posPiece.getY();
		Piece p = c[x][y].getPiece();
		return p.getAccessible();
	}

	/**
	 * Vérifie si le joueur est en échec ou non. On suppose que la liste des
	 * positions possibles pour les pièces sont déjà calculées
	 *
	 * @param couleur
	 *            couleur du joueur
	 * @return
	 */
	public boolean estEnEchec(char couleur) {
		Position pos = rechercheRoi(couleur);
		// System.out.println("# le roi est en position "+pos);

		// Pour toutes les pièces
		for (int j = 0; j < dimY; j++) {
			for (int i = 0; i < dimX; i++) {
				Piece p = c[i][j].getPiece();
				if (p != null) {
					if (p.getColor() != couleur) {
						// System.out.print(p+" en case "+new Position(i,j));
						// System.out.println(" peut aller en
						// "+p.getAccessible());
						if (p.getAccessible().contains(pos)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * Recherche la position du roi d'une couleur (on suppose qu'elle existe
	 * tjs)
	 *
	 * @param couleur
	 * @return
	 */
	public Position rechercheRoi(char couleur) {

		// recherche de la position du roi
		for (int j = 0; j < dimY; j++) {
			for (int i = 0; i < dimX; i++) {
				Piece p = c[i][j].getPiece();
				if (p != null) {
					if ((p.getCode() == 'k' && couleur == 'b') || (p.getCode() == 'K' && couleur == 'w')) {
						return new Position(i, j);
					}
				}
			}
		}
		return null;
	}

	public boolean isPromotion() {
		return promotion;
	}

	public boolean petitRoqueEnCours() {
		return petitRoqueEnCours;
	}

	public boolean grandRoqueEnCours() {
		return grandRoqueEnCours;
	}

	public boolean priseEnPassantEnCours() {
		return priseEnPassantEnCours;
	}

	/**
	 * Permet de vérifier qu'un déplacement ne met pas en échec.
	 *
	 * @param d
	 *            Déplacement considéré
	 * @return vrai si le déplacement ne met pas en échec, faux sinon.
	 */
	public boolean verifiePasEchecsApres(Deplacement d) {

		Echiquier n = new Echiquier(this);
		n.forceDeplacement(d);
		n.construitPositionsAccessibles();

		if (n.estEnEchec(trait)) {
			return false;
		}

		return true;
	}

	/**
	 * Test si la partie est finie
	 * 
	 * @return n si la partie n'est pas finie, b ou w selon le gagnant
	 */
	public char verifiePartieTerminee() {
		char end = 'n';
		boolean whiteKing = false;
		boolean blackKing = false;
		int i;

		// Check the existing pieces on the board
		int k = 0;
		for (i = 0; i < dimX; i++) {
			for (int j = 0; j < dimY; j++) {
				if (c[i][j].getPiece() != null) {
					existantPieces[k] = c[i][j].getPiece();
					k++;
				}
			}
		}

		// Test if there is only the king on one side
		i = 0;
		while (!whiteKing && !blackKing && (i < k)) {
			if (!whiteKing)
				whiteKing = (existantPieces[i].estBlanc() && existantPieces[i].getCode() != 'K');

			if (!blackKing)
				blackKing = (existantPieces[i].estNoir() && existantPieces[i].getCode() != 'k');
			
			i++;
		}
		
		if(blackKing)
			end = 'w';
		if(whiteKing)
			end = 'b';

		return (end);
	}
}
