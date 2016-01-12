/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

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
	private final int nbMaxPiece = 35;

	// pour la promotion
	boolean promotion = false;
	private boolean petitRoqueEnCours = false;
	private boolean grandRoqueEnCours = false;
	private boolean priseEnPassantEnCours = false;

	// position de la prise en passant éventuelle
	Position priseEnPassant;

	// 'Specimen' chessPieces
	Piece pionType, tourType, dameType, roiType, fouType, cavalierType;

	/**
	 * Constructeur par défaut echiquier 8x8
	 */
	public Echiquier() {
		this(8, 8);
		existantPieces = new Piece[nbMaxPiece];
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

	public Echiquier(Piece pion, Piece dame, Piece roi, Piece cavalier, Piece fou, Piece tour) {
		this(8, 8);
		initPieces(pion, dame, roi, cavalier, fou, tour);
	}

	public Echiquier(Echiquier ech) {
		this.dimX = ech.dimX;
		this.dimY = ech.dimY;
		this.trait = ech.trait;

		this.pionType = ech.pionType;
		this.fouType = ech.fouType;
		this.roiType = ech.roiType;
		this.dameType = ech.dameType;
		this.tourType = ech.tourType;
		this.cavalierType = ech.cavalierType;

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
							c[pos][dimY - i - 1].setPiece(new Piece(roiType, 'b'));
							break;
						case 'K':
							c[pos][dimY - i - 1].setPiece(new Piece(roiType, 'w'));
							break;
						case 'q':
							c[pos][dimY - i - 1].setPiece(new Piece(dameType, 'b'));
							break;
						case 'Q':
							c[pos][dimY - i - 1].setPiece(new Piece(dameType, 'w'));
							break;
						case 'r':
							c[pos][dimY - i - 1].setPiece(new Piece(tourType, 'b'));
							break;
						case 'R':
							c[pos][dimY - i - 1].setPiece(new Piece(tourType, 'w'));
							break;
						case 'b':
							c[pos][dimY - i - 1].setPiece(new Piece(fouType, 'b'));
							break;
						case 'B':
							c[pos][dimY - i - 1].setPiece(new Piece(fouType, 'w'));
							break;
						case 'n':
							c[pos][dimY - i - 1].setPiece(new Piece(cavalierType, 'b'));
							break;
						case 'N':
							c[pos][dimY - i - 1].setPiece(new Piece(cavalierType, 'w'));
							break;
						case 'p':
							c[pos][dimY - i - 1].setPiece(new Piece(pionType, 'b'));
							break;
						case 'P':
							c[pos][dimY - i - 1].setPiece(new Piece(pionType, 'w'));
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

	/**
	 * Méthode qui indique si un déplacement est valide ou non
	 *
	 * @param d
	 *            Deplacement
	 * @return true si valide sur l'echiquier false sinon
	 */
	public boolean Deplacement(Deplacement d) {

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
			@SuppressWarnings("unused")
			char joueur = 'e';

			// test de la couleur
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
				piece = new Piece(dameType, 'b');
			}
			// cas du pion blanc sur dernière rangée
			if (codePiece == 'P' && (y2 == dimY - 1)) {
				promotion = true;
				piece = new Piece(dameType, 'w');
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

			c[x2][y2].setPiece(piece);
			c[x1][y1].vider();

		}
		// on change le trait
		trait = (trait == 'w' ? 'b' : 'w');

		construitPositionsAccessibles();
		System.out.println(this.getFEN());
	}

	/**
	 * Check if a move is valid or not ; If it is in the piece's list of
	 * accessible moves
	 * 
	 * @param d
	 *            The move to check
	 * @return True if ok, False if not
	 */
	public boolean estValideDeplacement(Deplacement d) {
		Piece p = c[d.getX1()][d.getY1()].getPiece();
		if (p.getColor() == trait) {
			construitPositionsAccessibles();
			if (p.getAccessible().contains(d.getArrive()))
				return true;
		}
		return false;
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

		// TODO Check for captured piece

		Piece piece = c[x1][y1].getPiece();
		c[x2][y2].setPiece(piece);
		c[x1][y1].vider();

		// on change le trait
		trait = (trait == 'w' ? 'b' : 'w');
		construitPositionsAccessibles();
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
		Piece knight = c[i][j].getPiece();

		// Les 8 positions du cavalier
		if (existeEtLibre(i + knight.getMinX(), j + knight.getMaxX(), blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 1, j + 2));
		}

		if (existeEtLibre(i + knight.getMaxX(), j + knight.getMinX(), blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 2, j + 1));
		}

		if (existeEtLibre(i - knight.getMinX(), j - knight.getMaxX(), blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 1, j - 2));
		}

		if (existeEtLibre(i - knight.getMaxX(), j - knight.getMinX(), blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 2, j - 1));
		}

		if (existeEtLibre(i + knight.getMinX(), j - knight.getMaxX(), blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 1, j - 2));
		}

		if (existeEtLibre(i + knight.getMaxX(), j - knight.getMinX(), blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 2, j - 1));
		}

		if (existeEtLibre(i - knight.getMinX(), j + knight.getMaxX(), blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 1, j + 2));
		}

		if (existeEtLibre(i - knight.getMaxX(), j + knight.getMinX(), blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 2, j + 1));
		}

	}

	/**
	 * Check for roque (and roll)
	 * 
	 * 
	 * 
	 */
	public void geranceDuRoque(int i, int j) {
		boolean useful, fini;
		int a;

		// left side of the king
		// We search for a rook and then allow the dep if all conditions are
		// here
		useful = true;
		fini = false;
		a = 0;
		while (!fini) {
			if (a == i)
				fini = true;
			else {
				if (!c[a][j].estVide()) {
					fini = true;
					if (Character.toLowerCase(c[a][j].getPiece().getCode()) == 'r' && !c[a][j].getPiece().isMoved()) {
						for (int b = i - 1; b > a; b--) {
							if (!c[b][j].estVide())
								useful = false;
						}
						if (useful)
							c[i][j].getPiece().addCaseAccessible(new Position(i - 3, j));
					}
				}
				a++;
			}
		}

		// Right side of the king
		// We search for a rook and then allow the dep if all conditions are
		// here
		useful = true;
		fini = false;
		a = dimY-1;
		while (!fini) {
			if (a == i)
				fini = true;
			else {
				if (!c[a][j].estVide()) {
					fini = true;
					if (Character.toLowerCase(c[a][j].getPiece().getCode()) == 'r' && !c[a][j].getPiece().isMoved()) {
						for (int b = i + 1; b < a; b++) {
							if (!c[b][j].estVide())
								useful = false;
						}
						if (useful)
							c[i][j].getPiece().addCaseAccessible(new Position(i + 2, j));
					}
				}
				a--;
			}
		}
	}

	/**
	 * Add 'special moves' of the white pawn (miamage & prise en passant (french
	 * accent included))
	 * 
	 * @param i
	 *            The X coordonate
	 * @param j
	 *            The Y coordonate (my english is very bon)
	 * @param p
	 *            The piece (of cake (which is a lie))
	 */
	public void accessiblePionBlanc(int i, int j, Piece p) {
		// On 2nd line maggle
		if (j == 1 && c[i][j + p.getMaxY() + 1].estVide() && p.getMaxY() < dimY) {
			p.addCaseAccessible(new Position(i, j + p.getMaxY() + 1));
		}

		// Nomnom of the white pawn
		int parcI, parcJ;
		boolean fini;
		// Left diag
		parcI = i - 1;
		parcJ = j + 1;
		fini = false;
		while (!fini) {
			// If there is no case, stop
			if (!existeCase(parcI, parcJ))
				fini = true;
			else {
				if (!c[parcI][parcJ].estVide()) {
					// If not empty, stop anyway and check
					// if it can eat the piece
					if (c[parcI][parcJ].getPiece().getColor() == 'b')
						p.addCaseAccessible(new Position(parcI, parcJ));
					fini = true;
				}
				// If its dep capacity is outmerged, stop
				if ((parcJ - j) == p.getMaxY() || parcJ == dimY)
					fini = true;
				parcI--;
				parcJ++;
			}
		}
		// Right diag
		parcI = i + 1;
		parcJ = j + 1;
		fini = false;
		while (!fini) {
			// If there is no case, stop
			if (!existeCase(parcI, parcJ))
				fini = true;
			else {
				if (!c[parcI][parcJ].estVide()) {
					// If not empty, stop anyway and check
					// if it can eat the piece
					if (c[parcI][parcJ].getPiece().getColor() == 'b')
						p.addCaseAccessible(new Position(parcI, parcJ));
					fini = true;
				}
				// If its dep capacity is outmerged, stop
				if ((parcJ - j) == p.getMaxY() || parcJ == dimY)
					fini = true;
				parcI++;
				parcJ++;
			}
		}

		// prise en passant à gauche
		/*
		 * try { if ((j == 4) && c[i - 1][4].getPiece().getCode() == 'p') {
		 * Position pep = new Position(i - 1, 5); if
		 * (pep.equals(priseEnPassant)) {
		 * c[i][j].getPiece().addCaseAccessible(pep); } } } catch
		 * (ArrayIndexOutOfBoundsException | NullPointerException e) {
		 */
		// rien à faire puisque la pièce ou la case n'existe pas...
		// }

		// prise en passant à droite
		/*
		 * try { if ((j == 4) && c[i + 1][4].getPiece().getCode() == 'p') {
		 * Position pep = new Position(i + 1, 5); if
		 * (pep.equals(priseEnPassant)) {
		 * c[i][j].getPiece().addCaseAccessible(pep); } } } catch
		 * (ArrayIndexOutOfBoundsException | NullPointerException e) {
		 */
		// rien à faire puisque la pièce ou la case n'existe pas...
		// }
	}

	/**
	 * Add 'special moves' of the black pawn (miamage & prise en passant (french
	 * accent included))
	 * 
	 * @param i
	 *            Die X kohordone ! Ach ! (with german accent my general)
	 * @param j
	 *            Coordonnee en Y (with french accent)
	 * @param p
	 *            The piece
	 */
	public void accessiblePionNoir(int i, int j, Piece p) {
		// On ANTEPENULTIEME(FDP) line
		if (j == (dimY - 2) && c[i][j - p.getMaxY() - 1].estVide() && p.getMaxY() < dimY) {
			p.addCaseAccessible(new Position(i, j - p.getMaxY() - 1));
		}

		// Nomnom of the black pawn
		int parcI, parcJ;
		boolean fini;
		// Left diag
		parcI = i - 1;
		parcJ = j - 1;
		fini = false;
		while (!fini) {
			// If there is no case, stop
			if (!existeCase(parcI, parcJ))
				fini = true;
			else {
				if (!c[parcI][parcJ].estVide()) {
					// If not empty, stop anyway and check
					// if it can eat the piece
					if (c[parcI][parcJ].getPiece().getColor() == 'w')
						p.addCaseAccessible(new Position(parcI, parcJ));
					fini = true;
				}
				// If its dep capacity is outmerged, stop
				if ((j - parcJ) == p.getMaxY() || parcJ == dimY)
					fini = true;
				parcI--;
				parcJ--;
			}
		}
		// Right diag
		parcI = i + 1;
		parcJ = j - 1;
		fini = false;
		while (!fini) {
			// If there is no case, stop
			if (!existeCase(parcI, parcJ))
				fini = true;
			else {
				if (!c[parcI][parcJ].estVide()) {
					// If not empty, stop anyway and check
					// if it can eat the piece
					if (c[parcI][parcJ].getPiece().getColor() == 'w')
						p.addCaseAccessible(new Position(parcI, parcJ));
					fini = true;
				}
				// If its dep capacity is outmerged, stop
				if ((j - parcJ) == p.getMaxY() || parcJ == dimY)
					fini = true;
				parcI++;
				parcJ--;
			}
		}

		// prise en passant à gauche
		/*
		 * try { if ((j == 3) && c[i - 1][3].getPiece().getCode() == 'P') {
		 * Position pep = new Position(i - 1, 2); if
		 * (pep.equals(priseEnPassant)) {
		 * c[i][j].getPiece().addCaseAccessible(pep); } } } catch
		 * (ArrayIndexOutOfBoundsException | NullPointerException e) {
		 */
		// rien à faire puisque la pièce ou la case n'existe pas...
		// }

		// prise en passant à droite
		/*
		 * try
		 * 
		 * { if ((j == 3) && c[i + 1][3].getPiece().getCode() == 'P') { Position
		 * pep = new Position(i + 1, 2); if (pep.equals(priseEnPassant)) {
		 * c[i][j].getPiece().addCaseAccessible(pep); } } } catch
		 * (ArrayIndexOutOfBoundsException | NullPointerException e) {
		 */
		// rien à faire puisque la pièce ou la case n'existe pas...
		// }
	}

	/**
	 * Try every possible moves for each piece ! Roque, pawn's, king's specif
	 * and knight's one included cause we are muttafoking badass
	 *
	 *
	 */
	public void construitPositionsAccessibles() {
		// TODO maybe separate this big thing into many little things

		for (int j = 0; j < dimY; j++) {
			for (int i = 0; i < dimX; i++) {
				Piece p = c[i][j].getPiece();
				if (p != null) {
					p.videAccessible();

					// If it's a knight with basic move set
					if (Character.toLowerCase(p.getCode()) == 'n' && p.getMaxY() == 0 && p.getMaxDiag() == 0) {
						accessibleCavalier(i, j);
					} else {

						// White pawn with 'normal' dep capacities
						if (p.getCode() == 'P' && !p.getBackward() && p.getMaxDiag() == 0 && p.getMaxX() == 0)
							accessiblePionBlanc(i, j, p);

						// Black pawn with 'normal' dep capacities
						if (p.getCode() == 'p' && !p.getBackward() && p.getMaxDiag() == 0 && p.getMaxX() == 0)
							accessiblePionNoir(i, j, p);

						if (((p.getCode() == 'k' && roquek) || (p.getCode() == 'K' && roqueK)) && !p.isMoved())
							geranceDuRoque(i, j);

						Position posPiece = new Position(i, j);
						int parcoursX;
						int parcoursY;
						Position parcours;
						Deplacement dep;
						boolean continu = true;

						// Horizontal moves here
						if (p.getMaxX() > 0) {
							// To the right
							parcoursX = i + 1;
							parcoursY = j;
							while (continu) {
								if (parcoursX < dimX) {
									parcours = new Position(parcoursX, parcoursY);
									dep = new Deplacement(posPiece, parcours, p.getCode(), p.getColor());
									if (accessiblePiece(dep))
										p.addCaseAccessible(parcours);
									if (!c[parcoursX][parcoursY].estVide())
										continu = false;

									parcoursX++;
								} else
									continu = false;
							}

							parcoursX = i - 1;
							parcoursY = j;
							continu = true;

							// To the left
							while (continu) {
								if (parcoursX >= 0) {
									parcours = new Position(parcoursX, parcoursY);
									dep = new Deplacement(posPiece, parcours, p.getCode(), p.getColor());
									if (accessiblePiece(dep))
										p.addCaseAccessible(parcours);
									if (!c[parcoursX][parcoursY].estVide())
										continu = false;

									parcoursX--;
								} else
									continu = false;
							}
						}

						// Vertical checking here
						if (p.getMaxY() > 0) {
							parcoursY = j + 1;
							parcoursX = i;
							continu = true;
							// To the bottom !
							while (continu) {
								if (parcoursY < dimY) {
									parcours = new Position(parcoursX, parcoursY);
									dep = new Deplacement(posPiece, parcours, p.getCode(), p.getColor());
									if (accessiblePiece(dep))
										p.addCaseAccessible(parcours);
									if (!c[parcoursX][parcoursY].estVide()) {
										continu = false;
										if (Character.toLowerCase(p.getCode()) == 'p')
											p.suppCaseAccessible(parcours);
									}

									parcoursY++;
								} else
									continu = false;
							}

							parcoursX = i;
							parcoursY = j - 1;
							continu = true;

							// To the top, to the stars !
							while (continu) {
								if (parcoursY >= 0) {
									parcours = new Position(parcoursX, parcoursY);
									dep = new Deplacement(posPiece, parcours, p.getCode(), p.getColor());
									if (accessiblePiece(dep))
										p.addCaseAccessible(parcours);
									if (!c[parcoursX][parcoursY].estVide()) {
										continu = false;
										if (Character.toLowerCase(p.getCode()) == 'p')
											p.suppCaseAccessible(parcours);
									}
									parcoursY--;
								} else
									continu = false;
							}
						}

						// Diagonal checking here ! 4 directions !
						if (p.getMaxDiag() > 0) {
							continu = true;
							parcoursX = i + 1;
							parcoursY = j + 1;

							// First one ! bottom-right I think
							while (continu) {
								if (parcoursY < dimY && parcoursX < dimX) {
									parcours = new Position(parcoursX, parcoursY);
									dep = new Deplacement(posPiece, parcours, p.getCode(), p.getColor());
									if (accessiblePiece(dep))
										p.addCaseAccessible(parcours);
									if (!c[parcoursX][parcoursY].estVide())
										continu = false;

									parcoursX++;
									parcoursY++;
								} else
									continu = false;
							}

							continu = true;
							parcoursX = i - 1;
							parcoursY = j + 1;

							// Second one ! bottom-left normally
							// Do you know why the chicken crossed the road ??
							// Because road is human-made, chicken do not get
							// it.
							while (continu) {
								if (parcoursY < dimY && parcoursX >= 0) {
									parcours = new Position(parcoursX, parcoursY);
									dep = new Deplacement(posPiece, parcours, p.getCode(), p.getColor());
									if (accessiblePiece(dep))
										p.addCaseAccessible(parcours);
									if (!c[parcoursX][parcoursY].estVide())
										continu = false;

									parcoursX--;
									parcoursY++;
								} else
									continu = false;
							}

							continu = true;
							parcoursX = i - 1;
							parcoursY = j - 1;

							// Third one ! top-right maybe, or top-chef ? Who
							// knows
							// ?
							while (continu) {
								if (parcoursY >= 0 && parcoursX >= 0) {
									parcours = new Position(parcoursX, parcoursY);
									dep = new Deplacement(posPiece, parcours, p.getCode(), p.getColor());
									if (accessiblePiece(dep))
										p.addCaseAccessible(parcours);
									if (!c[parcoursX][parcoursY].estVide())
										continu = false;

									parcoursX--;
									parcoursY--;
								} else
									continu = false;
							}

							continu = true;
							parcoursX = i + 1;
							parcoursY = j - 1;

							// Fourth one ! top-right if i'm right, got it ?
							// right
							// -> right !
							while (continu) {
								if (parcoursY >= 0 && parcoursX < dimX) {
									parcours = new Position(parcoursX, parcoursY);
									dep = new Deplacement(posPiece, parcours, p.getCode(), p.getColor());
									if (accessiblePiece(dep))
										p.addCaseAccessible(parcours);
									if (!c[parcoursX][parcoursY].estVide())
										continu = false;

									parcoursX++;
									parcoursY--;
								} else
									continu = false;
							}
						}
					}
				}
			}
		}
	}

	public boolean accessiblePiece(Deplacement move) {
		int iDep = move.getDepart().getX();
		int jDep = move.getDepart().getY();
		int iArr = move.getArrive().getX();
		int jArr = move.getArrive().getY();

		Piece piece = new Piece(c[iDep][jDep].getPiece());
		// First step : check length
		if (((Math.abs(move.deplacementHorizontal()) == 0 && piece.getMinY() <= Math.abs(move.deplacementVertical())
				&& piece.getMaxY() >= Math.abs(move.deplacementVertical()))
				|| (Math.abs(move.deplacementVertical()) == 0
						&& piece.getMinX() <= Math.abs(move.deplacementHorizontal())
						&& piece.getMaxX() >= Math.abs(move.deplacementHorizontal())))
				|| (Math.abs(move.deplacementDiagonal()) != 0 && (piece.getMinDiag() <= move.deplacementDiagonal()
						&& piece.getMaxDiag() >= move.deplacementDiagonal()))) {

			// Second step : check if there is something to kill
			if (c[iArr][jArr].estVide()
					|| (!c[iArr][jArr].estVide() && c[iArr][jArr].getPiece().getColor() != piece.getColor())) {

				// Third step : check if backward and if allowed
				if (piece.getBackward() || (!piece.getBackward() && piece.estBlanc() && !move.backwardMoveWhite())
						|| (!piece.getBackward() && piece.estNoir() && !move.backwardMoveBlack()))
					return true;
			}
		}
		return false;
	}

	public ArrayList<Position> affichePositionAccessibles(Position posPiece) {
		int x = posPiece.getX();
		int y = posPiece.getY();
		Piece p = c[x][y].getPiece();
		for (int i = 0; i < p.getAccessible().size(); i++) {
			if (!verifiePasEchecsApres(new Deplacement(posPiece, p.getAccessible().get(i)))) {
				p.suppCaseAccessible(p.getAccessible().get(i));
				i--;
			}
		}
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

		// Pour toutes les pièces
		for (int j = 0; j < dimY; j++) {
			for (int i = 0; i < dimX; i++) {
				Piece p = c[i][j].getPiece();
				if (p != null) {
					if (p.getColor() != couleur) {
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

		if (n.estEnEchec(trait))
			return false;

		return true;
	}

	/**
	 * Test si la partie est finie
	 * 
	 * @return n si la partie n'est pas finie, b ou w selon le gagnant
	 */
	public char verifiePartieTerminee() {
		// TODO Other tests, like king + one "big" piece
		// TODO Fix the mat

		char end = 'n';
		// At start, we set those var true
		boolean whiteKingAlone = true;
		boolean blackKingAlone = true;

		boolean whiteCantMove = true;
		boolean blackCantMove = true;

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

		// Test if there only remains the king on one side
		// If there is an other piece on one side of the board, it means king is
		// not alone
		// Also test if there is a mat
		i = 0;
		while (i < k) {
			if (existantPieces[i].estBlanc()) {
				if (existantPieces[i].getCode() != 'K')
					whiteKingAlone = false;

				if (!existantPieces[i].getAccessible().isEmpty())
					whiteCantMove = false;
			}

			if (existantPieces[i].estNoir()) {
				if (existantPieces[i].getCode() != 'k')
					blackKingAlone = false;

				if (!existantPieces[i].getAccessible().isEmpty())
					blackCantMove = false;

			}
			i++;
		}

		if (blackKingAlone || blackCantMove)
			end = 'w';
		if (whiteKingAlone || whiteCantMove)
			end = 'b';

		return (end);
	}

	public void initPieces(Piece pion, Piece dame, Piece roi, Piece cavalier, Piece fou, Piece tour) {
		this.pionType = new Piece(pion);
		this.fouType = new Piece(fou);
		this.roiType = new Piece(roi);
		this.dameType = new Piece(dame);
		this.tourType = new Piece(tour);
		this.cavalierType = new Piece(cavalier);
	}
}