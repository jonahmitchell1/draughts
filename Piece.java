
/**
 * Represents a piece in the game of Draughts.
 * @author jonahmitchell1
 */
public class Piece {
    private final int colour;
    private Position position;
    private boolean king;

    /**
     * Creates a new Piece object with the given colour and position.
     * @param colour The colour of the piece.
     * @param position The position of the piece on the board.
     */
    public Piece(int colour, Position position) {
        this.colour = colour;
        this.position = position;
        this.king = false;
    }

    /**
     * Promotes the piece to a king.
     */
    public void promote() {
        this.king = true;
    }

    /**
     * Returns the colour of the piece.
     * @return The colour of the piece.
     */
    public int getColour() {
        return this.colour;
    }

    /**
     * Returns whether the piece is a king.
     * @return True if the piece is a king, false otherwise.
     */
    public boolean isKing() {
        return this.king;
    }

    /**
     * Returns the position of the piece on the board.
     * @return The position of the piece.
     */
    public Position getPosition() {
        return this.position;
    }
    
    /**
     * Sets the position of the piece on the board.
     * @param position The new position of the piece.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Returns the x-coordinate of the piece on the board.
     * @return The x-coordinate of the piece.
     */
    public int getX() {
        return this.position.getX();
    }

    /**
     * Returns the y-coordinate of the piece on the board.
     * @return The y-coordinate of the piece.
     */
    public int getY() {
        return this.position.getY();
    }

    /**
     * Returns a string representation of the piece.
     * @return A string representation of the piece.
     */
    @Override
    public String toString(){
        return Integer.toHexString(this.hashCode()) + ": " + colour + " at (" + this.getX() + ", " + this.getY() + ")";
    }
}
