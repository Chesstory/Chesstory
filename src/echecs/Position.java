package echecs;

/**
 * Represents a position ; The intersection of two points. Ease the use of class
 * Deplacement, etc.
 *
 * @author samuel
 */
public class Position {
	private int x, y;

	/**
	 * Lonely constructor.
	 * 
	 * @param x
	 *            The absciss
	 * @param y
	 *            The ordinate
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The absciss of the point
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return The ordinate of the point
	 */
	public int getY() {
		return y;
	}

	/**
	 * Ease the toString() method (which return the standard chess notation of chess case).
	 * 
	 * @param i
	 * @return The letter associated with the given int
	 */
	private String getCharForNumber(int i) {
		return i >= 0 && i < 26 ? String.valueOf((char) (i + 97)) : null;
	}

	@Override
	public String toString() {
		return getCharForNumber(x) + (y + 1);
	}

	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (!(o instanceof Position)) {
			return false;
		}

		if (((Position) o).x != x)
			return false;
		if (((Position) o).y != y)
			return false;

		return true;

	}

}