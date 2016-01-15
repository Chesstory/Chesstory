/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

/**
 *
 * @author samuel
 */

class Case {

	private Piece content;

	/**
	 * Create a case with a piece
	 * 
	 * @param content
	 */
	public Case(Piece content) {
		this.content = content;
	}

	/**
	 * Create an empty case
	 */
	public Case() {
		this.content = null;
	}

	public Case(Case c) {
		if (c.content == null) {
			this.content = null;
		} else {
			this.content = new Piece(c.content);
		}
	}

	/**
	 * Return the case (what is within)
	 * 
	 * @return Content of the case
	 */
	public Piece getPiece() {
		return content;
	}

	/**
	 * Set the content of a case
	 * 
	 * @param p
	 *            piece
	 */
	public void setPiece(Piece p) {
		content = p;
	}

	/**
	 * @return Emptiness of the case
	 */
	public boolean isEmpty() {
		return (content == null);
	}

	/**
	 * Empty a case
	 */
	public void empty() {
		content = null;
	}

	@Override
	public String toString() {
		if (content != null)
			return content.toString();

		return " ";
	}
}