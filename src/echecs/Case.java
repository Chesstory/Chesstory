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

	private Piece contenu;

	/**
	 * Défini une case qui contient une pièce
	 * 
	 * @param contenu
	 */
	public Case(Piece contenu) {
		this.contenu = contenu;
	}

	/**
	 * Défini une case vide
	 */
	public Case() {
		this.contenu = null;
	}

	public Case(Case c) {
		if (c.contenu == null) {
			this.contenu = null;
		} else {
			this.contenu = new Piece(c.contenu);
		}
	}

	/**
	 * Retourne la pièce
	 * 
	 * @return
	 */
	public Piece getPiece() {
		return contenu;
	}

	/**
	 * Affecte une pièce
	 * 
	 * @param p
	 *            piece
	 */
	public void setPiece(Piece p) {
		contenu = p;
	}

	/**
	 * Indique si la case est vide
	 * 
	 * @return
	 */
	public boolean estVide() {
		return (contenu == null);
	}

	/**
	 * Vide la case
	 */
	public void vider() {
		contenu = null;
	}

	@Override
	public String toString() {
		if (contenu != null)
			return contenu.toString();

		return " ";
	}
}