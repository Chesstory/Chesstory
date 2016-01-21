/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import java.util.ArrayList;

/**
 * Representation of a chess piece. A piece has a name, a code, a color and a
 * set of move possibility.<br />
 * <br />
 * 
 * Code description : <br />
 * k : King <br />
 * q : Queen <br />
 * b : Bishop <br />
 * r : Rook <br />
 * n : Knight <br />
 * p : Pawn
 * 
 * <br />
 * <br />
 * Shatranj : <br />
 * a : Shah (king)<br />
 * c : Farzin (queen)<br />
 * d : Fil (bishop)<br />
 * e : Faras (knight)<br />
 * f : Roukh (rook)<br />
 * g : Baidaq (pawn)
 * 
 * <br />
 * <br />
 * Chaturanga : <br />
 * h : Rajag (king)<br />
 * i : Mantri (queen)<br />
 * j : Gaja (bishop)<br />
 * l : Ma (knight)<br />
 * m : Ratha (rook)<br />
 * o : Padati (pawn)
 *
 * @author samuel
 */
public class Piece {

	private String name;
	private char code;
	private String couleur;
	// Tell if the piece moved during the game, useful for roque,
	// prise-en-passant (omelette du fromage, etc
	private boolean moved;

	/**
	 * Various rules
	 * 
	 * min and max for each directions boolean to (dis)allow backward moves
	 */
	private int minX, maxX, minY, maxY, minDiag, maxDiag;
	private boolean backward, horse;

	ArrayList<Position> accessible = new ArrayList<>();
	ArrayList<Position> accessibleSpec = new ArrayList<>();

	/**
	 * Create a piece by its name, code and color
	 * 
	 * @param name
	 *            Name of the piece
	 * @param code
	 *            Code of the piece (Uppercase if white, Lowercase if black)
	 */
	public Piece(String name, char code) {
		this.name = name;
		this.code = code;
		couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";

		minX = 0;
		maxX = 0;
		minY = 0;
		maxY = 0;
		minDiag = 0;
		maxDiag = 0;
		moved = backward = horse = false;
	}

	/**
	 * Build a chess piece with a name, a code, a color, and its possible moves
	 * Used to create the specimen pieces in the chessboard easily
	 * 
	 * @param name
	 *            Name of the piece
	 * @param code
	 *            Code of the piece
	 * @param couleur
	 *            Color of the piece
	 * @param depHor
	 *            Whether it can move horizontally or not
	 * @param depVer
	 *            Whether it can move vertically or not
	 * @param depDiag
	 *            Whether it can move in diagonal or not
	 * @param backward
	 *            Whether it can move backward or not
	 * @param horse
	 *            Whether it has knight's move set
	 */
	public Piece(String name, char code, boolean depHor, boolean depVer, boolean depDiag, boolean backward,
			boolean horse) {
		this.name = name;
		this.code = code;
		couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";

		this.backward = backward;
		this.moved = false;
		this.horse = horse;

		minX = 0;
		maxX = ((depHor) ? 99 : 0);
		minY = 0;
		maxY = ((depVer) ? 99 : 0);
		minDiag = 0;
		maxDiag = ((depDiag) ? 99 : 0);
	}

	/**
	 * Build a chess piece with a name, a code, a color, and its possible moves
	 * Used to create the specimen pieces in the chessboard
	 * 
	 * @param name
	 *            Name of the piece
	 * @param code
	 *            Code of the piece
	 * @param minX
	 *            Minimum horizontal dep capacity
	 * @param maxX
	 *            Maximum horizontal dep capacity
	 * @param minY
	 *            Minimum vertical dep capacity
	 * @param maxY
	 *            Maximum vertical dep capacity
	 * @param minDiag
	 *            Minimum diagonal dep capacity
	 * @param maxDiag
	 *            Maximum diagonal dep capacity
	 * @param backward
	 *            Whether it can move backward
	 * @param horse
	 *            Whether it has knight's move set
	 */
	public Piece(String name, char code, int minX, int maxX, int minY, int maxY, int minDiag, int maxDiag,
			boolean backward, boolean horse) {
		this.name = name;
		this.code = code;
		couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";

		this.backward = backward;
		this.moved = false;
		this.horse = horse;

		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.minDiag = minDiag;
		this.maxDiag = maxDiag;
	}

	/**
	 * Constructor that build a chess piece with the same caracs as the entered
	 * one Useful for 'type' pieces (no color choice here)
	 * 
	 * @param that
	 *            The piece to copy
	 */
	Piece(Piece that) {
		this.name = new String(that.name);
		this.code = that.code;
		this.couleur = new String(that.couleur);

		this.minX = that.minX;
		this.maxX = that.maxX;
		this.minY = that.minY;
		this.maxY = that.maxY;
		this.minDiag = that.minDiag;
		this.maxDiag = that.maxDiag;
		this.backward = that.backward;
		this.horse = that.horse;
		this.moved = false;
	}

	/**
	 * Copy constructor, here we chose the color, and it's wonderful !
	 * 
	 * @param that
	 *            The piece to copy
	 * @param color
	 *            The color of the new piece
	 */
	Piece(Piece that, char color) {
		this.name = new String(that.name);
		this.code = ((color == 'w') ? Character.toUpperCase(that.code) : that.code);
		this.couleur = ((color == 'w') ? "blanc" : "noir");

		this.minX = that.minX;
		this.maxX = that.maxX;
		this.minY = that.minY;
		this.maxY = that.maxY;
		this.minDiag = that.minDiag;
		this.maxDiag = that.maxDiag;
		this.backward = that.backward;
		this.horse = that.horse;
		this.moved = false;
	}

	/**
	 * Constructor mainly used for the knight. I call it : "The Horse builder",
	 * it does sound kinda awesome for me ! And it's very ingenious cause a
	 * knight is riding a horse, so when you construct (or build ;) ) a knight,
	 * you have to build the horse too ! I could have call it "The Armorsmith"
	 * too, following this
	 * 
	 * @param name
	 *            Name of the piece
	 * @param code
	 *            Code of the piece
	 * @param horse
	 *            Says that it has horse's move set
	 */
	public Piece(String name, char code, boolean horse) {
		this.name = name;
		this.code = code;
		couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";

		this.backward = true;
		this.minX = 0;
		this.maxX = 0;
		this.minY = 0;
		this.maxY = 0;
		this.minDiag = 0;
		this.maxDiag = 0;
		this.moved = false;
		this.horse = horse;
	}

	/**
	 * Create a piece from it's code, useful to forceMove (YACG)
	 * 
	 * @param code
	 */
	public Piece(char code) {
		// TODO useless like this, the piece won't move
		this.code = code;
		switch (code) {
		case 'k':
			this.name = "roi";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'K':
			this.name = "roi";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'q':
			this.name = "dame";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'Q':
			this.name = "dame";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'r':
			this.name = "tour";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'R':
			this.name = "tour";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'b':
			this.name = "fou";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'B':
			this.name = "fou";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'n':
			this.name = "cavalier";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'N':
			this.name = "cavalier";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'p':
			this.name = "pion";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'P':
			this.name = "pion";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		}

		minX = 0;
		maxX = 0;
		minY = 0;
		maxY = 0;
		minDiag = 0;
		maxDiag = 0;
		backward = false;
		moved = false;
		horse = false;
	}

	/**
	 * @return Name of the piece
	 */
	public String getName() {
		return name + "_" + couleur;
	}

	/**
	 * @return The color of the piece
	 */
	public char getColor() {
		return couleur.equals("blanc") ? 'w' : 'b';
	}

	/**
	 * @return The code of the piece
	 */
	public char getCode() {
		return code;
	}

	/**
	 * @return Whether the piece is white
	 */
	public boolean estBlanc() {
		return (couleur.equals("blanc"));
	}

	/**
	 * @return Whether the piece is black
	 */
	public boolean estNoir() {
		return (couleur.equals("noir"));
	}

	/**
	 * @return A string that represents the piece
	 */
	public String toString() {
		return Character.toString(code);
	}

	/**
	 * Add an accessible pos to the piece
	 * 
	 * @param p
	 *            The position to add
	 */
	public void addCaseAccessible(Position p) {
		accessible.add(p);
	}

	/**
	 * Delete an accessible pos to the piece (if it is already in the list)
	 * 
	 * @param p
	 *            The position to delete
	 */
	public void suppCaseAccessible(Position p) {
		if (accessible.contains(p))
			accessible.remove(p);
	}

	/**
	 * @return The list of the accessible position of the piece
	 */
	public ArrayList<Position> getAccessible() {
		return accessible;
	}

	/**
	 * Empty the list of accessibleble position of the piece
	 */
	public void videAccessible() {
		accessible = new ArrayList<>();
	}

	/**
	 * @param p
	 *            The position to add to the 'special' accessible positions of
	 *            the piece
	 */
	public void addCaseSpec(Position p) {
		accessibleSpec.add(p);
	}

	/**
	 * @param p
	 *            The position to delete from the 'special' accessible positions
	 *            of the piece
	 */
	public void suppCaseSpec(Position p) {
		if (accessibleSpec.contains(p))
			accessibleSpec.remove(p);
	}

	/**
	 * @return The list of the the 'special' accessible positions of the piece
	 */
	public ArrayList<Position> getSpec() {
		return accessibleSpec;
	}

	/**
	 * Empty the list of the 'special' accessible positions of the piece
	 */
	public void videSpec() {
		accessibleSpec = new ArrayList<>();
	}

	/**
	 * Getters & setters for moves capacity
	 */
	/**
	 * @return The littlest horizontal dep capacity
	 */
	public int getMinX() {
		return minX;
	}

	/**
	 * @param i
	 *            The new littlest horizontal dep capacity
	 */
	public void setMinX(int i) {
		this.minX = i;
	}

	/**
	 * @return The biggest horizontal dep capacity
	 */
	public int getMaxX() {
		return maxX;
	}

	/**
	 * @param i
	 *            The new biggest horizontal dep capacity
	 */
	public void setMaxX(int i) {
		this.maxX = i;
	}

	/**
	 * @return The littlest vertical move capacity
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * @param i
	 *            The new littlest vertical move capacity
	 */
	public void setMinY(int i) {
		this.minY = i;
	}

	/**
	 * @return The biggest vertical move possibility
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * @param i
	 *            The new biggest vertical move possibility
	 */
	public void setMaxY(int i) {
		this.maxY = i;
	}

	/**
	 * @return The littlest diagonal move possibility
	 */
	public int getMinDiag() {
		return minDiag;
	}

	/**
	 * @param i
	 *            The new littlest diagonal move possibilty
	 */
	public void setMinDiag(int i) {
		this.minDiag = i;
	}

	/**
	 * @return The biggest diagonal move possibility
	 */
	public int getMaxDiag() {
		return maxDiag;
	}

	/**
	 * @param i
	 *            The new biggest diagonal move possibility
	 */
	public void setMaxDiag(int i) {
		this.maxDiag = i;
	}

	/**
	 * @return Whether the piece can go backward
	 */
	public boolean getBackward() {
		return backward;
	}

	/**
	 * @param b
	 *            If the piece can go backward
	 */
	public void setBackward(boolean b) {
		this.backward = b;
	}

	/**
	 * @return Whether this boolean useful for many things has been changed
	 */
	public boolean isMoved() {
		return moved;
	}

	/**
	 * @param moved
	 *            the moved to set
	 */
	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	/**
	 * @return A string useful for saving games
	 */
	public String getMoveSet() {
		return new String(name + "," + code + "," + minX + "," + maxX + "," + minY + "," + maxY + "," + minDiag + ","
				+ maxDiag + "," + backward + "," + horse);
	}

	public String getNameAlone() {
		return name;
	}

	/**
	 * @return If the piece has knight's move set
	 */
	public boolean isHorse() {
		return horse;
	}

	/**
	 * @param horse
	 *            The horse to set
	 */
	public void setHorse(boolean horse) {
		this.horse = horse;
	}
}