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

public class Piece {

	private String nom;
	private char code;
	private String couleur;
	/**
	 * Various rules
	 * 
	 * min and max for each directions boolean to (dis)allow backward moves
	 */
	private int minX, maxX, minY, maxY, minDiag, maxDiag;
	private boolean backward;

	ArrayList<Position> accessible = new ArrayList<>();

	/**
	 * Définit une pièce en fonction de son non, son code et sa couleur
	 * 
	 * @param nom
	 * @param code
	 * @param couleur
	 */
	Piece(String nom, char code) {
		this.nom = nom;
		this.code = code;
		couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";

		minX = 0;
		maxX = 0;
		minY = 0;
		maxY = 0;
		minDiag = 0;
		maxDiag = 0;
		backward = false;
	}

	/**
	 * Build a chess piece with a name, a code, a color, and its possible moves
	 * 
	 * @param nom
	 * @param code
	 * @param couleur
	 * @param depHor Whether it can move horizontally or not
	 * @param depVer Whether it can move vertically or not
	 * @param depDiag Whether it can move in diagonal or not
	 * @param backward Whether it can move backward or not
	 */
	Piece(String nom, char code, boolean depHor, boolean depVer, boolean depDiag, boolean backward) {
		this.nom = nom;
		this.code = code;
		couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";

		this.backward = backward;
		minX = 0;
		maxX = ((depHor) ? 99 : 0);
		minY = 0;
		maxY = ((depVer) ? 99 : 0);
		minDiag = 0;
		maxDiag = ((depDiag) ? 99 : 0);
	}

	Piece(String nom, char code, int minX, int maxX, int minY, int maxY, int minDiag, int maxDiag, boolean backward) {
		this.nom = nom;
		this.code = code;
		couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";

		this.backward = backward;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.minDiag = minDiag;
		this.maxDiag = maxDiag;
	}

	
	Piece(Piece p) {
		this.nom = new String(p.nom);
		this.code = p.code;
		this.couleur = new String(p.couleur);

		minX = 0;
		maxX = 0;
		minY = 0;
		maxY = 0;
		minDiag = 0;
		maxDiag = 0;
		backward = false;
	}

	/**
	 * Create a piece from it's code, useful to forceMove (YACG)
	 * 
	 * @param code
	 */
	public Piece(char code) {
		this.code = code;
		switch (code) {
		case 'k':
			this.nom = "roi";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'K':
			this.nom = "roi";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'q':
			this.nom = "dame";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'Q':
			this.nom = "dame";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'r':
			this.nom = "tour";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'R':
			this.nom = "tour";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'b':
			this.nom = "fou";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'B':
			this.nom = "fou";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'n':
			this.nom = "cavalier";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'N':
			this.nom = "cavalier";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'p':
			this.nom = "pion";
			couleur = (Character.isUpperCase(code)) ? "blanc" : "noir";
			break;
		case 'P':
			this.nom = "pion";
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
	}

	/**
	 * Retourne le nom
	 * 
	 * @return le nom de la pièce
	 */
	public String getNom() {
		return nom + "_" + couleur;
	}

	public char getColor() {
		return couleur.equals("blanc") ? 'w' : 'b';
	}

	/**
	 * Retourne le code
	 * 
	 * @return code de la pièce
	 */
	public char getCode() {
		return code;
	}

	/**
	 * Retroune vrai si la pièce est blanche faux sinon
	 * 
	 * @return
	 */
	public boolean estBlanc() {
		return (couleur.equals("blanc"));
	}

	/**
	 * Retroune vrai si la pièce est noire faux sinon
	 * 
	 * @return
	 */
	public boolean estNoir() {
		return (couleur.equals("noir"));
	}

	/**
	 * Chaine de caractères qui représenta la pièce
	 * 
	 * @return
	 */
	public String toString() {
		return Character.toString(code);
	}

	public void addCaseAccessible(Position p) {
		accessible.add(p);
	}

	public void suppCaseAccessible(Position p) {
		if (accessible.contains(p))
			accessible.remove(p);
	}

	public ArrayList<Position> getAccessible() {
		return accessible;
	}

	public void videAccessible() {
		accessible = new ArrayList<>();
	}

	/**
	 * Getters & setters for moves capacity
	 */
	public int getMinX() {
		return minX;
	}

	public void setMinX(int i) {
		this.minX = i;
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int i) {
		this.maxX = i;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int i) {
		this.minY = i;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int i) {
		this.maxY = i;
	}

	public int getMinDiag() {
		return minDiag;
	}

	public void setMinDiag(int i) {
		this.minDiag = i;
	}

	public int getMaxDiag() {
		return maxDiag;
	}

	public void setMaxDiag(int i) {
		this.maxDiag = i;
	}

	public boolean getBackward() {
		return backward;
	}

	public void setBackward(boolean b) {
		this.backward = b;
	}
}