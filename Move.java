public class Move {
    private Piece piece;
    private Position position;

    public Move(Piece piece, Position position) {
        this.piece = piece;

        this.position = position;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public int getX() {
        return this.position.getX();
    }

    public int getY() {
        return this.position.getY();
    }

    @Override
    public String toString() {
        return "Move: " + piece + " to (" + position.getX() + ", " + position.getY() + ")";
    }
}
