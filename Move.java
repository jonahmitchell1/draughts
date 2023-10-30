/**
 * Represents a move made by a player in a game of draughts.
 * @author jonahmitchell1
 */
public class Move {
    private Piece piece;
    private Position position;
    private boolean isSkip;

    /**
     * Constructs a new Move object with the given piece and position.
     * @param piece the piece being moved
     * @param position the position the piece is being moved to
     */
    public Move(Piece piece, Position position) {
        this.piece = piece;
        this.position = position;
    }

    /**
     * Constructs a new Move object representing a skip move.
     */
    public Move() {
        this.isSkip = true;
        this.piece = null;
        this.position = null;
    }

    /**
     * Returns whether this move is a skip move.
     * @return true if this move is a skip move, false otherwise
     */
    public boolean isSkip() {
        return this.isSkip;
    }

    /**
     * Returns the piece being moved.
     * @return the piece being moved
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Returns the position the piece is being moved to.
     * @return the position the piece is being moved to
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Returns the x-coordinate of the position the piece is being moved to.
     * @return the x-coordinate of the position the piece is being moved to
     */
    public int getX() {
        return this.position.getX();
    }

    /**
     * Returns the y-coordinate of the position the piece is being moved to.
     * @return the y-coordinate of the position the piece is being moved to
     */
    public int getY() {
        return this.position.getY();
    }

    /**
     * Returns whether this move is a hop move (i.e. a move that jumps over an opponent's piece).
     * @return true if this move is a hop move, false otherwise
     */
    public boolean isHop() {
        int x1 = this.piece.getX();
        int y1 = this.piece.getY();
        int x2 = this.position.getX();
        int y2 = this.position.getY();

        return (Math.abs(x1 - x2) == 2 && Math.abs(y1 - y2) == 2);
    }

    /**
     * Returns a string representation of this move.
     * @return a string representation of this move
     */
    @Override
    public String toString() {
        return "Move: " + piece + " to (" + position.getX() + ", " + position.getY() + ")";
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
            return (this.piece.equals(move.getPiece()) && this.position.equals(move.getPosition()));
        }
        return false;
    }
}
