public abstract class Player {
    private int colour;

    public Player(int colour) {
        this.colour = colour;
    }

    abstract public Move getMove();

    public String getColour() {
        switch(colour) {
            case 0:
                return "Black";
            case 1:
                return "White";
            default:
                return "Invalid colour";
        }
    }
}