/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

/**
 * Representation of a move
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

	/**
	 * Create a move with two positions, the code of the piece concerned
	 * and the color of this piece Used in YetAnotherChessGame mainly
	 * 
	 * @param p1
	 *            Departure position
	 * @param p2
	 *            Arrival position
	 * @param piece
	 *            Code Code of the concerned piece
	 * @param color
	 *            Color of the concerned piece
	 */
	public Deplacement(Position p1, Position p2, char pieceCode, char color) {
		x1 = p1.getX();
		y1 = p1.getY();
		x2 = p2.getX();
		y2 = p2.getY();
		this.pieceCode = pieceCode;
		this.color = color;
	}

	/**
	 * Link two positions, mainly used for some tests
	 * 
	 * @param p1
	 *            Departure position
	 * @param p2
	 *            Arrival position
	 */
	public Deplacement(Position p1, Position p2) {
		x1 = p1.getX();
		y1 = p1.getY();
		x2 = p2.getX();
		y2 = p2.getY();
		this.pieceCode = 'x';
		this.color = 'x';
	}

	/**
	 * Copy constructor
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

	/**
	 * @return Departure position
	 */
	public Position getDepart() {
		return new Position(x1, y1);
	}

	/**
	 * @return Arrival position
	 */
	public Position getArrive() {
		return new Position(x2, y2);
	}

	/**
	 * Many method that helps for tests of validity in other classes
	 */

	/**
	 * @return Length of the dep, horizontally
	 */
	public int deplacementHorizontal() {
		return x2 - x1;
	}

	/**
	 * @return Length of the dep, vertically
	 */
	public int deplacementVertical() {
		return y2 - y1;
	}

	/**
	 * @return Length of the dep, diagonally
	 */
	public int deplacementDiagonal() {
		int depX = Math.abs(deplacementHorizontal());
		int depY = Math.abs(deplacementVertical());

		if (depX == depY) {
			return depX;
		}
		return 0;
	}

	/**
	 * @return If it is a backward move for white pieces
	 */
	public boolean backwardMoveWhite() {
		return (y1 > y2);
	}

	/**
	 * @return If it is a backward move for black pieces
	 */
	public boolean backwardMoveBlack() {
		return (y2 > y1);
	}

	/**
	 * Move coded following algebric notation
	 * 
	 * @param s
	 *            Algebric notation
	 */
	public Deplacement(String s) {
		x1 = s.charAt(0) - 'a';
		y1 = s.charAt(1) - '1';
		x2 = s.charAt(3) - 'a';
		y2 = s.charAt(4) - '1';
		color = 'x';
		pieceCode = 'x';
	}

	/**
	 * @return X of departure position
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * @return Y of departure position
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * @return X of arrival position
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * @return Y of arrival position
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * @return The littlest X value
	 */
	public int minX() {
		return (x1 < x2) ? x1 : x2;
	}

	/**
	 * @return The biggest X value
	 */
	public int maxX() {
		return (x1 > x2) ? x1 : x2;
	}

	/**
	 * @return The littlest Y value
	 */
	public int minY() {
		return (y1 < y2) ? y1 : y2;
	}

	/**
	 * @return The biggest Y value
	 */
	public int maxY() {
		return (y1 > y2) ? y1 : y2;
	}

	/**
	 * @return The code of the concerned piece
	 */
	public char getPiececode() {
		return pieceCode;
	}

	/**
	 * @return The color of the concerned piece
	 */
	public char getColor() {
		return color;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return color + " " + pieceCode + " " + " (" + x1 + "," + y1 + ") ====> (" + x2 + "," + y2 + ")";
	}

}