
/**
 * The abstract class Player represents a player in the game of Draughts.
 * It contains methods and properties that are common to both human and computer players.
 * 
 * @author jonahmitchell1
 */
public abstract class Player {
    private int colour;
    private Player opponent;

    /**
     * Constructs a new Player object with the given colour and GameState.
     * @param colour The colour of the player.
     */
    public Player(int colour) {
        this.colour = colour;
    }

    /**
     * Chooses a move for the player to make. Should be overridden
     * @param game
     * @return null
     */
    public void chooseMove(GameState game) {
        System.out.println(game.currentPlayer.getColourAsString() + "'s turn.");
    }

    /**
     * Returns the colour of the player.
     * @return the colour of the player
     */
    public int getColour() {
        return this.colour;
    }

    /**
     * Returns the colour of the player as a string.
     * @return the colour of the player as a string
     */
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

    /**
     * Sets the opponent of the player.
     * @param opponent the opponent of the player
     */
    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    /**
     * Returns the opponent of the player.
     * @return the opponent of the player
     */
    public Player getOpponent() {
        return this.opponent;
    }
}