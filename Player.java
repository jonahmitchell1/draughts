public abstract class Player {
    private int colour;
    private Player opponent;
    protected GameState game;

    public Player(int colour, GameState game) {
        this.colour = colour;
        this.game = game;
    }

    abstract public Move getMove() throws InvalidMoveException;

    public int getColour() {
        return this.colour;
    }

    public String getColourAsString() {
        switch(this.colour) {
            case -1:
                return "Black";
            case 1:
                return "White";
            default:
                return "Invalid colour";
        }
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public Player getOpponent() {
        return this.opponent;
    }
}