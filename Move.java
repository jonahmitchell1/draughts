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

    public Position getPosition() {
        return this.position;
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Move) {
            Move m = (Move) o;
            return (this.piece.equals(m.getPiece()) && this.position.equals(m.getPosition()));
        }
        return false;
    }
}
