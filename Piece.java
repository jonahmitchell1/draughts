public class Piece {
    private final int colour;
    private Position position;
    private boolean king;

    public Piece(int colour, Position position) {
        this.colour = colour;
        this.position = position;
        this.king = false;
        System.out.println("Instantiated new piece: " + this.toString());
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

    public Position getPosition() {
        return this.position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }

    public int getX() {
        return this.position.getX();
    }

    public int getY() {
        return this.position.getY();
    }

    @Override
    public String toString(){
        return Integer.toHexString(this.hashCode()) + ": " + colour + " at (" + this.getX() + ", " + this.getY() + ")";
    }
}
