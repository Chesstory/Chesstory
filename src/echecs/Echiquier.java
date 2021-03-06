/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.omg.CORBA.SetOverrideTypeHelper;

import Controller.ChesstoryGame;
import Controller.RulesEditor;
import gui.YetAnotherChessGame;

import static Controller.ChesstoryConstants.*;

/**
 * This is where everything is done. This class builds the chess board and
 * checks all the moves possibility, etc. Everything that is not graphic occurs
 * here. It contains a set of boolean to adjusts rules, the specimen chess
 * pieces, the width and height of the chess board, etc
 *
 * @author Acevedo Roman and Guillemot Baptiste, based on Delepoulle Samuel's
 *         code.
 */
@SuppressWarnings("unused")
public class Echiquier {

	private Case[][] c;
	private int dimX, dimY;

	private YetAnotherChessGame yacg;

	char trait;

	// For the roque
	boolean roque;
	int roqueL, roqueR, roquel, roquer;

	// For testing the end of the game
	private Piece[] existantPieces;
	private final int nbMaxPiece = 35;

	boolean promotion = false;

	// position de la prise en passant éventuelle
	Position priseEnPassant;
	boolean priseEnPass;

	// 'Specimen' chessPieces
	Piece pawnSpec, rookSpec, queenSpec, kingSpec, bishopSpec, knightSpec;

	/**
	 * Default constructor (8x8)
	 */
	public Echiquier() {
		this(8, 8);

		existantPieces = new Piece[nbMaxPiece];
	}

	/**
	 * Empty the chess board
	 */
	private void empty() {
		for (int i = 0; i < dimX; i++) {
			for (int j = 0; j < dimY; j++) {
				c[i][j] = new Case();
			}
		}
	}

	/**
	 * Create a chessboard with the specified width and height
	 *
	 * @param dimX
	 *            Width of the chess board
	 * @param dimY
	 *            Height of the chess board
	 */
	public Echiquier(int dimX, int dimY) {
		this.dimX = dimX;
		this.dimY = dimY;

		this.trait = 'w';

		existantPieces = new Piece[nbMaxPiece];

		c = new Case[dimX][dimY];
		empty();

	}

	/**
	 * Create a chess board with all the specimen pieces
	 * 
	 * @param pawn
	 *            Specimen pawn
	 * @param queen
	 *            Specimen queen
	 * @param king
	 *            Specimen king
	 * @param knight
	 *            Specimen knight
	 * @param bishop
	 *            Specimen bishop
	 * @param rook
	 *            Specimen rook
	 * @param yacg
	 */
	public Echiquier(Piece pawn, Piece queen, Piece king, Piece knight, Piece bishop, Piece rook,
			YetAnotherChessGame yacg) {
		this(8, 8);
		this.yacg = yacg;
		initPieces(pawn, rook, queen, king, bishop, knight);
	}

	public Echiquier(int a) {

	}

	/**
	 * Copy constructor
	 * 
	 * @param ech
	 *            Chess board to copy
	 */
	public Echiquier(Echiquier ech) {
		this.dimX = ech.dimX;
		this.dimY = ech.dimY;
		this.trait = ech.trait;

		this.pawnSpec = ech.pawnSpec;
		this.bishopSpec = ech.bishopSpec;
		this.kingSpec = ech.kingSpec;
		this.queenSpec = ech.queenSpec;
		this.rookSpec = ech.rookSpec;
		this.knightSpec = ech.knightSpec;

		roque = true;

		existantPieces = new Piece[nbMaxPiece];

		c = new Case[dimX][dimY];
		for (int i = 0; i < dimX; i++) {
			for (int j = 0; j < dimY; j++) {
				c[i][j] = new Case(ech.getCase(i, j));
			}
		}
	}

	/**
	 * @param i
	 *            X coordonate
	 * @param j
	 *            Y coordonate
	 * @return The content of the wanted case
	 */
	public Piece getPieceCase(int i, int j) {
		return c[i][j].getPiece();
	}

	/**
	 * @param p
	 *            Wanted position
	 * @return The content of the wanted case
	 */
	public Piece getPieceCase(Position p) {
		return c[p.getX()][p.getY()].getPiece();
	}

	/**
	 * @return The player that has to play
	 */
	public char getTrait() {
		return trait;
	}

	/**
	 * @param p
	 *            Position of the case to check
	 * @return Whether the case is empty
	 */
	public boolean isEmptyCase(Position p) {
		return getPieceCase(p) == null;
	}

	/**
	 * @return The width of the chess board
	 */
	public int getDimX() {
		return dimX;
	}

	/**
	 * @return The height of the chess board
	 */
	public int getDimY() {
		return dimY;
	}

	/**
	 * @param i
	 *            X coordonate
	 * @param j
	 *            Y coordonate
	 * @return The wanted case
	 */
	public Case getCase(int i, int j) {
		return c[i][j];
	}

	/**
	 * Add a piece at the wanted position
	 *
	 * @param p
	 *            The piece
	 * @param x
	 *            X coordonate
	 * @param y
	 *            Y coordonate
	 */
	void place(Piece p, int x, int y) {
		c[x][y].setPiece(p);
	}

	/**
	 * @param x
	 *            X coordonate
	 * @param y
	 *            Y coordonate
	 * @return The piece (null if the case is empty)
	 */
	public Piece getPiece(int x, int y) {
		return c[x][y].getPiece();
	}

	/**
	 * @param p
	 *            The position of the case
	 * @return The piece (null if empty)
	 */
	public Piece getPiece(Position p) {
		return getPiece(p.getX(), p.getY());
	}

	/**
	 * Add a FEN code, it will change the pieces on the chess board following it
	 *
	 * @param FENcode
	 */
	public void setFEN(String FENcode) {
		empty();
		// TODO allow change width and height
		try {
			String[] code = FENcode.split(" ");
			String[] ligne = code[0].split("/");

			// First part of the code
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
							c[pos][dimY - i - 1].setPiece(new Piece(kingSpec, 'b'));
							break;
						case 'K':
							c[pos][dimY - i - 1].setPiece(new Piece(kingSpec, 'w'));
							break;
						case 'q':
							c[pos][dimY - i - 1].setPiece(new Piece(queenSpec, 'b'));
							break;
						case 'Q':
							c[pos][dimY - i - 1].setPiece(new Piece(queenSpec, 'w'));
							break;
						case 'r':
							c[pos][dimY - i - 1].setPiece(new Piece(rookSpec, 'b'));
							break;
						case 'R':
							c[pos][dimY - i - 1].setPiece(new Piece(rookSpec, 'w'));
							break;
						case 'b':
							c[pos][dimY - i - 1].setPiece(new Piece(bishopSpec, 'b'));
							break;
						case 'B':
							c[pos][dimY - i - 1].setPiece(new Piece(bishopSpec, 'w'));
							break;
						case 'n':
							c[pos][dimY - i - 1].setPiece(new Piece(knightSpec, 'b'));
							break;
						case 'N':
							c[pos][dimY - i - 1].setPiece(new Piece(knightSpec, 'w'));
							break;
						case 'p':
							c[pos][dimY - i - 1].setPiece(new Piece(pawnSpec, 'b'));
							break;
						case 'P':
							c[pos][dimY - i - 1].setPiece(new Piece(pawnSpec, 'w'));
							break;

						// Shatranj
						case 'a':
							c[pos][dimY - i - 1].setPiece(new Piece(kingSpec, 'b'));
							break;
						case 'A':
							c[pos][dimY - i - 1].setPiece(new Piece(kingSpec, 'w'));
							break;
						case 'c':
							c[pos][dimY - i - 1].setPiece(new Piece(queenSpec, 'b'));
							break;
						case 'C':
							c[pos][dimY - i - 1].setPiece(new Piece(queenSpec, 'w'));
							break;
						case 'd':
							c[pos][dimY - i - 1].setPiece(new Piece(bishopSpec, 'b'));
							break;
						case 'D':
							c[pos][dimY - i - 1].setPiece(new Piece(bishopSpec, 'w'));
							break;
						case 'e':
							c[pos][dimY - i - 1].setPiece(new Piece(knightSpec, 'b'));
							break;
						case 'E':
							c[pos][dimY - i - 1].setPiece(new Piece(knightSpec, 'w'));
							break;
						case 'f':
							c[pos][dimY - i - 1].setPiece(new Piece(rookSpec, 'b'));
							break;
						case 'F':
							c[pos][dimY - i - 1].setPiece(new Piece(rookSpec, 'w'));
							break;
						case 'g':
							c[pos][dimY - i - 1].setPiece(new Piece(pawnSpec, 'b'));
							break;
						case 'G':
							c[pos][dimY - i - 1].setPiece(new Piece(pawnSpec, 'w'));
							break;

						// Chaturanga
						case 'h':
							c[pos][dimY - i - 1].setPiece(new Piece(kingSpec, 'b'));
							break;
						case 'H':
							c[pos][dimY - i - 1].setPiece(new Piece(kingSpec, 'w'));
							break;
						case 'i':
							c[pos][dimY - i - 1].setPiece(new Piece(queenSpec, 'b'));
							break;
						case 'I':
							c[pos][dimY - i - 1].setPiece(new Piece(queenSpec, 'w'));
							break;
						case 'j':
							c[pos][dimY - i - 1].setPiece(new Piece(bishopSpec, 'b'));
							break;
						case 'J':
							c[pos][dimY - i - 1].setPiece(new Piece(bishopSpec, 'w'));
							break;
						case 'l':
							c[pos][dimY - i - 1].setPiece(new Piece(knightSpec, 'b'));
							break;
						case 'L':
							c[pos][dimY - i - 1].setPiece(new Piece(knightSpec, 'w'));
							break;
						case 'm':
							c[pos][dimY - i - 1].setPiece(new Piece(rookSpec, 'b'));
							break;
						case 'M':
							c[pos][dimY - i - 1].setPiece(new Piece(rookSpec, 'w'));
							break;
						case 'o':
							c[pos][dimY - i - 1].setPiece(new Piece(pawnSpec, 'b'));
							break;
						case 'O':
							c[pos][dimY - i - 1].setPiece(new Piece(pawnSpec, 'w'));
							break;
						}
					}
				}
			}

			// second part
			try {
				if (code[1].equals("w")) {
					trait = 'w';
				} else if (code[1].equals("b")) {
					trait = 'b';
				} else {
					throw (new MalformedFENException("le second champs doit etre b ou w et non " + code[1]));
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				// without indications, the trait is for white
				trait = 'w';
			}

			// third part
			try {
				if (code[2].contains("r"))
					roque = true;
				if (code[2].contains("p"))
					priseEnPass = true;
			} catch (ArrayIndexOutOfBoundsException e) {
				// without indications, roque is allowed
				roque = true;
				priseEnPass = true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw (new MalformedFENException("le code " + FENcode + " n'est pas un code FEN valide"));
		}
		buildAccessiblePositions();
	}

	/**
	 * @return FENcode that represents the chess board
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

		if (roque)
			res += "r";
		else
			res += "-";

		if (priseEnPass)
			res += "p";
		else
			res += "-";

		res += " ";
		res += priseEnPassant == null ? "-" : priseEnPassant;

		return res;
	}

	/**
	 * @return String that represents the chess board
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
	 * Say whether a deplacement is valid or not
	 *
	 * @param d
	 *            Deplacement
	 * @return The validity of the dep
	 */
	public boolean Deplacement(Deplacement d) {

		int x1 = d.getX1();
		int y1 = d.getY1();
		int x2 = d.getX2();
		int y2 = d.getY2();

		// Check if the two case are differents
		if (x1 == x2 && y1 == y2) {
			return false;
		}

		// Check if the departure case is empty
		if (c[x1][y1].getPiece() == null)
			return false;

		if (c[x1][y1].isEmpty())
			return false;

		// Check if it is the good player
		if ((c[x1][y1].getPiece().isWhite() && trait == 'b') || (c[x1][y1].getPiece().isBlack() && trait == 'w'))
			return false;

		// Checking for the piece
		if (c[x1][y1].getPiece().getAccessible().contains(new Position(x2, y2))) {

			// Roque
			if (Character.toUpperCase(c[x1][y1].getPiece().getCode()) == 'K') {

				int depl = d.deplacementHorizontal();

				if (Math.abs(depl) == 2) {

					Position caseDePassage = new Position(d.getX1() + (depl / 2), d.getY1());
					Deplacement passage = new Deplacement(d.getDepart(), caseDePassage);

					// System.out.println("Case de passage = "+caseDePassage);
					if (!checkNotCheckAfter(passage)) {
						return false;
					}

					if (!isEmptyCase(caseDePassage)) {
						return false;
					}
				}
			}
			return checkNotCheckAfter(d);
		}
		return false;
	}

	/**
	 * Do a move after checking if it is allowed
	 *
	 *
	 * @param d
	 *            Move to do
	 */
	public boolean executeDeplacement(Deplacement d) {
		boolean eaten = false;
		if (isValidMove(d)) {
			int x1 = d.getX1();
			int y1 = d.getY1();
			int x2 = d.getX2();
			int y2 = d.getY2();

			Piece piece = c[x1][y1].getPiece();
			char codePiece = piece.getCode();
			char joueur = 'e';
			eaten = !c[x2][y2].isEmpty();

			// Color test
			if (codePiece >= 97 && codePiece <= 122) {// alors on est en
														// minuscule
				joueur = 'b';
			} else if (codePiece >= 65 && codePiece <= 90) {
				joueur = 'w';
			}

			promotion = false;

			// Black pawn on 1st line
			if (codePiece == 'p' && (y2 == 0)) {
				promotion = true;
				piece = new Piece(queenSpec, 'b');
				yacg.eventInter(CHESS_EVENT_PROM, "For black.");
			}
			if (codePiece == 'P' && (y2 == dimY - 1)) {
				promotion = true;
				piece = new Piece(queenSpec, 'w');
				yacg.eventInter(CHESS_EVENT_PROM, "For white.");
			}

			// Prise en passant (i'vre written this word so many times ...
			// Still
			// doesn't know him in english :( )
			// So : it has to be a pawn, with pawn's move set who try to
			// go
			// where he should not
			if (priseEnPass && Character.toLowerCase(codePiece) == 'p' && piece.getMaxDiag() == 0
					&& piece.getMaxX() == 0 && !piece.getBackward() && d.deplacementDiagonal() == 1
					&& !c[x2][y1].isEmpty() && c[x2][y1].getPiece().getColor() != c[x1][y1].getPiece().getColor()
					&& c[x2][y2].isEmpty()) {
				eaten = !c[x2][y1].isEmpty();
				c[x2][y1].empty();
				yacg.eventInter(CHESS_EVENT_PEP,
						((piece.getCode() == 'P') ? "In favor of white." : "In favor of black."));
			}

			// In case of : roque (king, on the same line, which move
			// further
			// than it can
			if (roque && Character.toLowerCase(codePiece) == 'k' && y1 == y2
					&& Math.abs(d.deplacementHorizontal()) > piece.getMaxX()) {
				Piece tour;
				if (x2 > x1) {
					if (piece.isWhite()) {
						tour = c[roqueR][y1].getPiece();
						c[x1 + 1][y1].setPiece(tour);
						c[roqueR][y1].empty();
						yacg.eventInter(CHESS_EVENT_ROQUE, "White did the little rook's move.");
					} else {
						tour = c[roquer][y1].getPiece();
						c[x1 + 1][y1].setPiece(tour);
						c[roquer][y1].empty();
						yacg.eventInter(CHESS_EVENT_ROQUE, "Black did the little rook's move.");
					}
				} else {
					if (piece.isWhite()) {
						tour = c[roqueL][y1].getPiece();
						c[x1 - 1][y1].setPiece(tour);
						c[roqueL][y1].empty();
						yacg.eventInter(CHESS_EVENT_ROQUE, "White did the great rook's move.");
					} else {
						tour = c[roquel][y1].getPiece();
						c[x1 - 1][y1].setPiece(tour);
						c[roquel][y1].empty();
						yacg.eventInter(CHESS_EVENT_ROQUE, "Black did the great rook's move.");
					}
				}
			}

			c[x2][y2].setPiece(piece);
			c[x1][y1].empty();

			// We do our business with this little boolean that help a lot
			// for
			// roque and prise-en-passant (srsly i have to search those
			// words in
			// english ... jambon-beurre)

			// Here, if the king or tower moved (roque)
			if (Character.toLowerCase(codePiece) == 'k' || Character.toLowerCase(codePiece) == 'r')
				piece.setMoved(true);

			// Here for the pawns, if they jump one more case, for the
			// PeP(si)
			if (Character.toLowerCase(codePiece) == 'p' && piece.getMaxDiag() == 0 && piece.getMaxX() == 0
					&& !piece.getBackward())
				piece.setMoved(Math.abs(d.deplacementVertical()) == piece.getMaxY() + 1);

			// Here for the rajah, (chaturanga) if it used its special
			// ability
			if (Character.toLowerCase(codePiece) == 'h'
					&& (Math.abs(d.deplacementHorizontal()) == 2 || Math.abs(d.deplacementVertical()) == 2))
				piece.setMoved(true);
		}
		// Change the trait
		trait = (trait == 'w' ? 'b' : 'w');

		buildAccessiblePositions();
		System.out.println(this.getFEN());

		return eaten;
	}

	/**
	 * Check if a move is valid or not ; If it is in the piece's list of
	 * accessible moves
	 * 
	 * @param d
	 *            The move to check
	 * @return True if ok, False if not
	 */
	public boolean isValidMove(Deplacement d) {
		Piece p = c[d.getX1()][d.getY1()].getPiece();
		if (p.getColor() == trait) {
			if (p.getAccessible().contains(d.getArrive()))
				return true;
		}
		return false;
	}

	/**
	 * Force a move (no verif)
	 *
	 *
	 * @param d
	 *            Move to do
	 */
	public void forceDeplacement(Deplacement d) {
		int x1 = d.getX1();
		int y1 = d.getY1();
		int x2 = d.getX2();
		int y2 = d.getY2();

		Piece piece = c[x1][y1].getPiece();
		c[x2][y2].setPiece(piece);
		c[x1][y1].empty();

		// Change trait
		trait = (trait == 'w' ? 'b' : 'w');
		buildAccessiblePositions();
	}

	/**
	 * Simule a move
	 *
	 * @param d
	 *            Move to simule
	 * @return The 'new' chess board
	 */
	public Echiquier simuleDeplacement(Deplacement d) {
		Echiquier ech = new Echiquier(this);
		ech.executeDeplacement(d);

		return ech;
	}

	/**
	 * Execute a move (after verification)
	 *
	 * @param s
	 *            String that represents the move
	 */
	public void executeDeplacement(String s) {
		executeDeplacement(new Deplacement(s));
	}

	/**
	 * @param i
	 *            X coordonate
	 * @param j
	 *            Y coordonate
	 * @return Whether the case is real
	 */
	public boolean existCase(int i, int j) {
		return (i >= 0 && i < dimX && j >= 0 && j < dimY);
	}

	/**
	 * @param i
	 *            X coordonate
	 * @param j
	 *            Y coordonate
	 * @return Whether the case exists and is empty
	 */
	public boolean existAndEmpty(int i, int j) {
		return existCase(i, j) && (c[i][j].isEmpty());
	}

	/**
	 * @param i
	 *            X coordonate
	 * @param j
	 *            Y coordoante
	 * @param blanc
	 *            Whether it is a white piece
	 * @return Whether the case exists and is empty OR occuped by an enemy piece
	 */
	public boolean existAndFree(int i, int j, boolean blanc) {
		return existCase(i, j) && (c[i][j].isEmpty() || (c[i][j].getPiece().isWhite() != blanc));
	}

	/**
	 * Add accesible move of a piece with knight's move capacities
	 * 
	 * @param i
	 *            X coordonate
	 * @param j
	 *            J coordonate
	 */
	public void accessibleKnight(int i, int j) {
		boolean blanc = c[i][j].getPiece().isWhite();
		Piece knight = c[i][j].getPiece();
		boolean rajah = Character.toLowerCase(knight.getCode()) == 'h';

		// Les 8 positions du cavalier
		if (existAndFree(i + 1, j + 2, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 1, j + 2));
			if (rajah)
				c[i][j].getPiece().addCaseSpec(new Position(i + 1, j + 2));
		}

		if (existAndFree(i + 2, j + 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 2, j + 1));
			if (rajah)
				c[i][j].getPiece().addCaseSpec(new Position(i + 2, j + 1));
		}

		if (existAndFree(i - 1, j - 2, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 1, j - 2));
			if (rajah)
				c[i][j].getPiece().addCaseSpec(new Position(i - 1, j - 2));
		}

		if (existAndFree(i - 2, j - 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 2, j - 1));
			if (rajah)
				c[i][j].getPiece().addCaseSpec(new Position(i - 2, j - 1));
		}

		if (existAndFree(i + 1, j - 2, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 1, j - 2));
			if (rajah)
				c[i][j].getPiece().addCaseSpec(new Position(i + 1, j - 2));
		}

		if (existAndFree(i + 2, j - 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i + 2, j - 1));
			if (rajah)
				c[i][j].getPiece().addCaseSpec(new Position(i + 2, j - 1));
		}

		if (existAndFree(i - 1, j + 2, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 1, j + 2));
			if (rajah)
				c[i][j].getPiece().addCaseSpec(new Position(i - 1, j + 2));
		}

		if (existAndFree(i - 2, j + 1, blanc)) {
			c[i][j].getPiece().addCaseAccessible(new Position(i - 2, j + 1));
			if (rajah)
				c[i][j].getPiece().addCaseSpec(new Position(i - 2, j + 1));
		}
	}

	/**
	 * Add accessible pos if roque(& roll) is possible
	 * 
	 * @param i
	 *            X coordonate of the king
	 * @param j
	 *            Y coordonate of the king
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
				if (!c[a][j].isEmpty()) {
					fini = true;
					if (Character.toLowerCase(c[a][j].getPiece().getCode()) == 'r' && !c[a][j].getPiece().isMoved()) {
						if (c[i][j].getPiece().isWhite())
							roqueL = a;
						else
							roquel = a;

						for (int b = i - 1; b > a; b--) {
							if (!c[b][j].isEmpty())
								useful = false;
						}
						if (useful) {
							c[i][j].getPiece().addCaseAccessible(new Position(i - 2, j));
							c[i][j].getPiece().addCaseSpec(new Position(i - 2, j));
						}
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
		a = dimY - 1;
		while (!fini) {
			if (a == i)
				fini = true;
			else {
				if (!c[a][j].isEmpty()) {
					fini = true;
					if (Character.toLowerCase(c[a][j].getPiece().getCode()) == 'r' && !c[a][j].getPiece().isMoved()) {
						if (c[i][j].getPiece().isWhite())
							roqueR = a;
						else
							roquer = a;

						for (int b = i + 1; b < a; b++) {
							if (!c[b][j].isEmpty())
								useful = false;
						}
						if (useful) {
							c[i][j].getPiece().addCaseAccessible(new Position(i + 2, j));
							c[i][j].getPiece().addCaseSpec(new Position(i + 2, j));
						}
					}
				}
				a--;
			}
		}
	}

	/**
	 * Add 'special moves' for a pawn (miamage & prise en passant (french accent
	 * included))
	 * 
	 * @param i
	 *            The X coordonate
	 * @param j
	 *            The Y coordonate (my english is very bon)
	 * @param p
	 *            The piece (of cake (which is a lie))
	 */
	public void accessiblePawn(int i, int j, Piece p) {
		int magicCoef, specialLine;

		if (Character.isLowerCase(p.getCode())) {
			specialLine = dimY - 2;
			magicCoef = -1;
		} else {
			specialLine = 1;
			magicCoef = 1;
		}
		// On 2nd/before-last line
		if (Character.toUpperCase(p.getCode()) == 'P' && j == specialLine
				&& c[i][j + (p.getMaxY() * magicCoef) + magicCoef].isEmpty() && p.getMaxY() < dimY) {
			p.addCaseAccessible(new Position(i, j + (p.getMaxY() * magicCoef) + magicCoef));
			p.addCaseSpec(new Position(i, j + (p.getMaxY() * magicCoef) + magicCoef));
		}

		// Nomnom of the white pawn
		int parcI, parcJ;
		boolean fini;
		// Left diag
		parcI = i - 1;
		parcJ = j + magicCoef;
		fini = false;
		while (!fini) {
			// If there is no case, stop
			if (!existCase(parcI, parcJ))
				fini = true;
			else {
				if (!c[parcI][parcJ].isEmpty()) {
					// If not empty, stop anyway and check
					// if it can eat the piece
					if (c[parcI][parcJ].getPiece().getColor() != p.getColor())
						p.addCaseAccessible(new Position(parcI, parcJ));
					fini = true;
				}
				// If its dep capacity is outmerged, stop
				if (((magicCoef == 1 && (parcJ - j) == p.getMaxY()) || (magicCoef == -1 && (j - parcJ) == p.getMaxY()))
						|| parcJ == dimY)
					fini = true;
				parcI--;
				parcJ += magicCoef;
			}
		}
		// Right diag
		parcI = i + 1;
		parcJ = j + magicCoef;
		fini = false;
		while (!fini) {
			// If there is no case, stop
			if (!existCase(parcI, parcJ))
				fini = true;
			else {
				if (!c[parcI][parcJ].isEmpty()) {
					// If not empty, stop anyway and check
					// if it can eat the piece
					if (c[parcI][parcJ].getPiece().getColor() != p.getColor())
						p.addCaseAccessible(new Position(parcI, parcJ));
					fini = true;
				}
				// If its dep capacity is outmerged, stop
				if (((magicCoef == 1 && (parcJ - j) == p.getMaxY()) || (magicCoef == -1 && (j - parcJ) == p.getMaxY()))
						|| parcJ == dimY)
					fini = true;
				parcI++;
				parcJ += magicCoef;
			}
		}
		// Prise en passant (baguette)
		if (Character.toUpperCase(p.getCode()) == 'P' && priseEnPass) {
			// Right one
			if (existCase(i + 1, j) && !c[i + 1][j].isEmpty()
					&& Character.toLowerCase(c[i + 1][j].getPiece().getCode()) == 'p'
					&& c[i + 1][j].getPiece().getColor() != p.getColor() && c[i + 1][j].getPiece().isMoved()
					&& c[i + 1][j + 1].isEmpty()) {
				p.addCaseAccessible(new Position(i + 1, j + magicCoef));
				p.addCaseSpec(new Position(i + 1, j + magicCoef));
			}
			// Left one
			if (existCase(i - 1, j) && !c[i - 1][j].isEmpty()
					&& Character.toLowerCase(c[i - 1][j].getPiece().getCode()) == 'p'
					&& c[i - 1][j].getPiece().getColor() != p.getColor() && c[i - 1][j].getPiece().isMoved()
					&& c[i - 1][j + 1].isEmpty()) {
				p.addCaseAccessible(new Position(i - 1, j + magicCoef));
				p.addCaseSpec(new Position(i - 1, j + magicCoef));
			}
		}
	}

	/**
	 * Try every possible moves for each piece ! Roque, pawn's, king's specif
	 * and knight's one included cause we are muttafoking badass
	 *
	 *
	 */
	public void buildAccessiblePositions() {
		for (int j = 0; j < dimY; j++) {
			for (int i = 0; i < dimX; i++) {
				Piece p = c[i][j].getPiece();
				if (p != null) {
					p.videAccessible();
					p.videSpec();

					// If it's a knight with basic move set
					if (p.isHorse())
						accessibleKnight(i, j);

					// Pawn with 'normal' dep capacities
					if ((Character.toUpperCase(p.getCode()) == 'P' || Character.toUpperCase(p.getCode()) == 'O'
							|| Character.toUpperCase(p.getCode()) == 'G') && !p.getBackward() && p.getMaxDiag() == 0
							&& p.getMaxX() == 0)
						accessiblePawn(i, j, p);

					if (roque && Character.toLowerCase(p.getCode()) == 'k' && !p.isMoved() && p.getMaxX() == 1)
						geranceDuRoque(i, j);

					// Chaturanga's special rule for rajah
					if (Character.toLowerCase(p.getCode()) == 'h' && !p.isMoved() && !isInCheck(p.getColor()))
						accessibleKnight(i, j);

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
								if (!c[parcoursX][parcoursY].isEmpty())
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
								if (!c[parcoursX][parcoursY].isEmpty())
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
								if (!c[parcoursX][parcoursY].isEmpty()) {
									continu = false;
									if (Character.toLowerCase(p.getCode()) == 'p'
											|| Character.toLowerCase(p.getCode()) == 'g')
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
								if (!c[parcoursX][parcoursY].isEmpty()) {
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
								if (!c[parcoursX][parcoursY].isEmpty() && Character.toLowerCase(p.getCode()) != 'd'
										&& Character.toLowerCase(p.getCode()) != 'j')
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
								if (!c[parcoursX][parcoursY].isEmpty() && Character.toLowerCase(p.getCode()) != 'd'
										&& Character.toLowerCase(p.getCode()) != 'j')
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
								if (!c[parcoursX][parcoursY].isEmpty() && Character.toLowerCase(p.getCode()) != 'd'
										&& Character.toLowerCase(p.getCode()) != 'j')
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
								if (!c[parcoursX][parcoursY].isEmpty() && Character.toLowerCase(p.getCode()) != 'd'
										&& Character.toLowerCase(p.getCode()) != 'j')
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

	/**
	 * Check if the piece can 'basically' do a move
	 * 
	 * @param move
	 *            Move to check
	 * @return Whether the piece can do this trip
	 */
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
			if (c[iArr][jArr].isEmpty()
					|| (!c[iArr][jArr].isEmpty() && c[iArr][jArr].getPiece().getColor() != piece.getColor())) {

				// Third step : check if backward and if allowed
				if (piece.getBackward() || (!piece.getBackward() && piece.isWhite() && !move.backwardMoveWhite())
						|| (!piece.getBackward() && piece.isBlack() && !move.backwardMoveBlack()))
					return true;
			}
		}
		return false;
	}

	public ArrayList<Position> affichePositionAccessibles(Position posPiece) {
		// TODO Change name
		int x = posPiece.getX();
		int y = posPiece.getY();
		Piece p = c[x][y].getPiece();
		for (int i = 0; i < p.getAccessible().size(); i++) {
			if (!checkNotCheckAfter(new Deplacement(posPiece, p.getAccessible().get(i)))) {
				p.suppCaseSpec(p.getAccessible().get(i));
				p.suppCaseAccessible(p.getAccessible().get(i));
				i--;
			}
		}
		return p.getAccessible();
	}

	/**
	 * To be called AFTER previous method
	 * 
	 * @param posPiece
	 *            Position of the piece
	 * @return The special positions allowed for this piece
	 */
	public ArrayList<Position> showSpecPositions(Position posPiece) {
		int x = posPiece.getX();
		int y = posPiece.getY();
		Piece p = c[x][y].getPiece();

		return p.getSpec();
	}

	/**
	 * Check if a player is in check
	 *
	 * @param color
	 *            The player
	 * @return Whether he is in echec
	 */
	public boolean isInCheck(char color) {
		Position pos = searchKing(color);

		for (int j = 0; j < dimY; j++) {
			for (int i = 0; i < dimX; i++) {
				Piece p = c[i][j].getPiece();
				if (p != null) {
					if (p.getColor() != color) {
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
	 * Search the king of a player
	 *
	 * @param color
	 *            The king to search
	 * @return The king's position
	 */
	public Position searchKing(char color) {
		for (int j = 0; j < dimY; j++) {
			for (int i = 0; i < dimX; i++) {
				Piece p = c[i][j].getPiece();
				if (p != null) {
					if ((p.getCode() == 'k' && color == 'b') || (p.getCode() == 'K' && color == 'w')) {
						return new Position(i, j);
					}
				}
			}
		}
		return null;
	}

	/**
	 * @return Whether a promotion is in course
	 */
	public boolean isPromotion() {
		return promotion;
	}

	/**
	 * Check if a move does not put the king in check
	 *
	 * @param d
	 *            Move to check
	 * @return Whether the move will let the king in check
	 */
	public boolean checkNotCheckAfter(Deplacement d) {
		Echiquier n = new Echiquier(this);
		n.forceDeplacement(d);
		n.buildAccessiblePositions();

		if (n.isInCheck(trait))
			return false;

		return true;
	}

	/**
	 * Check if the game is ended
	 * 
	 * @return n if not ended, b or w following who is the winner
	 */
	public char checkGameIsEnded() {
		// TODO Other tests, like king + one "big" piece

		char end = 'n';

		// At start, we set those var true
		boolean whiteKingAlone = true;
		boolean blackKingAlone = true;

		boolean whiteCantMove = true;
		boolean blackCantMove = true;

		int i;

		buildAccessiblePositions();

		// Check the existing pieces on the board
		int k = 0;
		for (i = 0; i < dimX; i++) {
			for (int j = 0; j < dimY; j++) {
				if (c[i][j].getPiece() != null) {
					existantPieces[k] = c[i][j].getPiece();
					affichePositionAccessibles(new Position(i, j));
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
			if (existantPieces[i].isWhite()) {
				if (existantPieces[i].getCode() != 'K')
					whiteKingAlone = false;

				if (!existantPieces[i].getAccessible().isEmpty())
					whiteCantMove = false;
			}

			if (existantPieces[i].isBlack()) {
				if (existantPieces[i].getCode() != 'k')
					blackKingAlone = false;

				if (!existantPieces[i].getAccessible().isEmpty())
					blackCantMove = false;
			}
			i++;
		}

		if (blackCantMove) {
			if (isInCheck('b'))
				yacg.eventInter(CHESS_EVENT_MAT, "White won !");
			else
				yacg.eventInter(CHESS_EVENT_PAT, "White won !");
		}

		if (whiteCantMove) {
			if (isInCheck('w'))
				yacg.eventInter(CHESS_EVENT_MAT, "Black won !");
			else
				yacg.eventInter(CHESS_EVENT_PAT, "Black won !");
		}

		if (blackKingAlone || blackCantMove)
			end = 'w';
		if (whiteKingAlone || whiteCantMove)
			end = 'b';

		return (end);
	}

	/**
	 * Init the specimen pieces of the chess board (its rules)
	 * 
	 * @param pawn
	 *            New specimen pawn
	 * @param rook
	 *            New specimen rook
	 * @param queen
	 *            New specimen queen
	 * @param king
	 *            New specimen king
	 * @param bishop
	 *            New specimen bishop
	 * @param knight
	 *            New specimen knight
	 */
	public void initPieces(Piece pawn, Piece rook, Piece queen, Piece king, Piece bishop, Piece knight) {
		this.pawnSpec = new Piece(pawn);
		this.bishopSpec = new Piece(bishop);
		this.kingSpec = new Piece(king);
		this.queenSpec = new Piece(queen);
		this.rookSpec = new Piece(rook);
		this.knightSpec = new Piece(knight);
	}

	/**
	 * Initialize the chess piece with the basical chess rules
	 */
	private void initChess() {
		this.pawnSpec = new Piece("pawn", 'p', 0, 0, 1, 1, 0, 0, false, false);
		this.bishopSpec = new Piece("bishop", 'b', false, false, true, true, false);
		this.kingSpec = new Piece("king", 'k', 1, 1, 1, 1, 1, 1, true, false);
		this.queenSpec = new Piece("queen", 'q', true, true, true, true, false);
		this.rookSpec = new Piece("rook", 'r', true, true, false, true, false);
		this.knightSpec = new Piece("knight", 'n', true);
	}

	/**
	 * Initialize the chess piece with the shatranj rules
	 */
	private void initShatranj() {
		this.pawnSpec = new Piece("baidaq", 'g', 0, 0, 1, 1, 0, 0, false, false);
		this.bishopSpec = new Piece("fil", 'd', 0, 0, 0, 0, 2, 2, true, false);
		this.kingSpec = new Piece("shah", 'a', 1, 1, 1, 1, 1, 1, true, false);
		this.queenSpec = new Piece("farzin", 'c', 0, 0, 0, 0, 1, 1, true, false);
		this.rookSpec = new Piece("roukh", 'f', true, true, false, true, false);
		this.knightSpec = new Piece("faras", 'e', true);
	}

	/**
	 * Initialize the chess piece with the chaturanga rules
	 */
	private void initChaturanga() {
		this.pawnSpec = new Piece("padati", 'o', 0, 0, 1, 1, 0, 0, false, false);
		this.bishopSpec = new Piece("gaja", 'j', 0, 0, 0, 0, 2, 2, true, false);
		this.kingSpec = new Piece("rajah", 'h', 1, 1, 1, 1, 1, 1, true, false);
		this.queenSpec = new Piece("mantri", 'i', 0, 0, 0, 0, 1, 1, true, false);
		this.rookSpec = new Piece("ratha", 'm', true, true, false, true, false);
		this.knightSpec = new Piece("ma", 'l', true);
	}

	/**
	 * @return the pawnSpec
	 */
	public Piece getPawnSpec() {
		return pawnSpec;
	}

	/**
	 * @return the rookSpec
	 */
	public Piece getRookSpec() {
		return rookSpec;
	}

	/**
	 * @return the queenSpec
	 */
	public Piece getQueenSpec() {
		return queenSpec;
	}

	/**
	 * @return the kingSpec
	 */
	public Piece getKingSpec() {
		return kingSpec;
	}

	/**
	 * @return the bishopSpec
	 */
	public Piece getBishopSpec() {
		return bishopSpec;
	}

	/**
	 * @return the knightSpec
	 */
	public Piece getKnightSpec() {
		return knightSpec;
	}
}