public class Move {
    private Piece piece;
    private Position position;
    private boolean isHop;

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

    public boolean isHop() {
        int x1 = this.piece.getX();
        int y1 = this.piece.getY();
        int x2 = this.position.getX();
        int y2 = this.position.getY();

        return (Math.abs(x1 - x2) == 2 && Math.abs(y1 - y2) == 2);
    }

    @Override
    public String toString() {
        return "Move: " + piece + " to (" + position.getX() + ", " + position.getY() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Move) {
            Move move = (Move) obj;
            return (this.piece.equals(move.getPiece()) && this.position.equals(move.getPosition()));
        }
        return false;
    }
}
