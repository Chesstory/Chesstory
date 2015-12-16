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
	}

	Piece(Piece p) {
		this.nom = new String(p.nom);
		this.code = p.code;
		this.couleur = new String(p.couleur);
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
	
	public void suppCaseAccessible(Position p){
		if(accessible.contains(p))
			accessible.remove(p);
	}

	public ArrayList<Position> getAccessible() {
		return accessible;
	}

	public void videAccessible() {
		accessible = new ArrayList<>();
	}

}