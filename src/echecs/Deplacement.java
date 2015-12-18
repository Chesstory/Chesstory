/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

/**
 * Représentation d'un déplacement
 * 
 * @author samuel
 */
public class Deplacement {
	private final char pieceCode;
	private final char color;
	private final int x1;
	private final int y1;
	private final int x2;
	private final int y2;

	public Deplacement(Position p1, Position p2, char pieceCode, char color) {// for
																				// the
																				// execution
																				// in
																				// YACG
		x1 = p1.getX();
		y1 = p1.getY();
		x2 = p2.getX();
		y2 = p2.getY();
		this.pieceCode = pieceCode;
		this.color = color;
	}

	public Deplacement(Position p1, Position p2) {// for the tests in Echiquier
		x1 = p1.getX();
		y1 = p1.getY();
		x2 = p2.getX();
		y2 = p2.getY();
		this.pieceCode = 'x';
		this.color = 'x';
	}

	/**
	 * Constructeur par recopie
	 * 
	 * @param that
	 */
	public Deplacement(Deplacement that) {
		x1 = that.x1;
		y1 = that.y1;
		x2 = that.x2;
		y2 = that.y2;
		this.pieceCode = that.pieceCode;
		this.color = that.color;
	}

	public Position getDepart() {
		return new Position(x1, y1);
	}

	public Position getArrive() {
		return new Position(x2, y2);
	}

	/**
	 * panel de methodes donnant la taille d'un deplacement
	 */

	public int deplacementHorizontal() {
		return x2 - x1;
	}

	public int deplacementVertical() {
		return y2 - y1;
	}

	public int deplacementDiagonal() {
		int depX = Math.abs(deplacementHorizontal());
		int depY = Math.abs(deplacementVertical());

		if (depX == depY) {
			return depX;
		}
		return 0;
	}
	
	public boolean backwardMove(){
		return(y1>y2);
	}

	/**
	 * Mouvement codé suivant la notation algébrique
	 * 
	 * @param s
	 */
	public Deplacement(String s) {
		x1 = s.charAt(0) - 'a';
		y1 = s.charAt(1) - '1';
		x2 = s.charAt(3) - 'a';
		y2 = s.charAt(4) - '1';
		color = 'x';
		pieceCode = 'x';
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}

	public int minX() {
		return (x1 < x2) ? x1 : x2;
	}

	public int maxX() {
		return (x1 > x2) ? x1 : x2;
	}

	public int minY() {
		return (y1 < y2) ? y1 : y2;
	}

	public int maxY() {
		return (y1 > y2) ? y1 : y2;
	}

	public char getPiececode() {
		return pieceCode;
	}

	public char getColor() {
		return color;
	}

	public String toString() {
		// return (new Position(x1,y1)).toString()+" � "+(new
		// Position(x2,y2)).toString();//not working anymore
		return color + " " + pieceCode + " " + " (" + x1 + "," + y1 + ") ====> (" + x2 + "," + y2 + ")";
	}

}