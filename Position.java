import java.lang.Math;

/**
 * The Position class represents a position on a 2D board.
 * @author jonahmitchell1
 */
public class Position {
    private int x;
    private int y;

    /**
     * Constructs a new Position object with the given x and y coordinates.
     *
     * @param x the x coordinate of the position
     * @param y the y coordinate of the position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Checks if the given position is out of bounds.
     * @param position the position to check
     * @return true if the position is out of bounds, false otherwise
     */
    public boolean isOutOfBounds() {

        return (this.getX() < 0 || this.getX() > 7 || this.getY() < 0 || this.getY() > 7);
    }

    /**
     * Returns the x coordinate of the position.
     *
     * @return the x coordinate of the position
     */
    public int getX(){
        return this.x;
    }

    /**
     * Returns the y coordinate of the position.
     *
     * @return the y coordinate of the position
     */
    public int getY() {
        return this.y;
    }

    /**
     * Sets the x coordinate of the position to the given value.
     *
     * @param x the new x coordinate of the position
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y coordinate of the position to the given value.
     *
     * @param y the new y coordinate of the position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Adds the given value to the x coordinate of the position.
     *
     * @param x the value to add to the x coordinate of the position
     */
    public void addX(int x) {
        this.x += x;
    }

    /**
     * Adds the given value to the y coordinate of the position.
     *
     * @param y the value to add to the y coordinate of the position
     */
    public void addY(int y) {
        this.y += y;
    }

    /**
     * Returns a string representation of the position in the format "(x, y)".
     *
     * @return a string representation of the position
     */
    @Override
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }

    @Override
    public int hashCode() {
        // uses coding bijection to map (x, y) to a unique integer
        int result = (int) Math.round((Math.pow(2, this.x)) * (2 * this.y + 1) - 1);
        return result;
    }

    /**
     * Returns true if the given object is a Position with the same x and y coordinates as this Position.
     *
     * @param o the object to compare to this Position
     * @return true if the given object is a Position with the same x and y coordinates as this Position
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Position) {
            Position position = (Position) o;
            return (this.x == position.getX() && this.y == position.getY());
        }
        return false;
    }

}