public class Piece {
    private final int colour;
    private Position position;
    private boolean king;

    public Piece(int colour, Position position) {
        this.colour = colour;
        this.position = position;
        this.king = false;
    }

    public void promote() {
        this.king = true;
    }

    public int getColour() {
        return this.colour;
    }

    public boolean isKing() {
        return this.king;
    }

    public int getX() {
        return this.position.getX();
    }

    public int getY() {
        return this.position.getY();
    }

    @Override
    public String toString(){
        return "Piece: " + colour + " at (" + this.getX() + ", " + this.getY() + ")";
    }
}
