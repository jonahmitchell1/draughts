/**
 * Represents a move made by a player in a game of draughts.
 * @author jonahmitchell1
 */
public class Move {
    private Position origin;
    private Position destination;
    private boolean isSkip;

    /**
     * Constructs a new Move object with the given piece and position.
     * @param piece the piece being moved
     * @param position the position the piece is being moved to
     */
    public Move(Position origin, Position destination) {
        this.origin = origin;
        this.destination = destination;
    }

    /**
     * Constructs a new Move object representing a skip move.
     */
    public Move() {
        this.isSkip = true;
        this.origin = null;
        this.destination = null;
    }

    /**
     * Returns whether this move is a skip move.
     * @return true if this move is a skip move, false otherwise
     */
    public boolean isSkip() {
        return this.isSkip;
    }

    /**
     * Returns the position the piece is being moved to.
     * @return the position the piece is being moved to
     */
    public Position getOrigin() {
        return this.origin;
    }

    public Position getDestination() {
        return this.destination;
    }

    /**
     * Returns whether this move is a hop move (i.e. a move that jumps over an opponent's piece).
     * @return true if this move is a hop move, false otherwise
     */
    public boolean isHop() {
        int x1 = this.origin.getX();
        int y1 = this.origin.getY();
        int x2 = this.destination.getX();
        int y2 = this.destination.getY();

        return (Math.abs(x1 - x2) == 2 && Math.abs(y1 - y2) == 2);
    }

    /**
     * Returns a string representation of this move.
     * @return a string representation of this move
     */
    @Override
    public String toString() {
        return "Move piece at (" + origin.getX() + ", " + origin.getY() + ") to (" + destination.getX() + ", " + destination.getY() + ")";
    }

    @Override
    public int hashCode() {
        // uses coding bijection to map hashCodes of the two positions to a unique integer
        int originHashCode = origin.hashCode();
        int destinationHashCode = destination.hashCode();
        int result = (int) Math.round((Math.pow(2, originHashCode)) * (2 * destinationHashCode + 1) - 1);
        return result;
    }

    /**
     * Returns whether this move is equal to the given object.
     * @param obj the object to compare to
     * @return true if this move is equal to the given object, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Move) {
            Move move = (Move) obj;
            return ((this.origin.equals(move.getOrigin())) && (this.destination.equals(move.getDestination())));
        }
        return false;
    }
}
