
/**
 * Represents a human player in the game of draughts.
 * @author jonahmitchell1
 */
public class HumanPlayer extends Player {

    /**
     * Creates a new instance of the HumanPlayer class.
     * @param colour The colour of the player's pieces.
     * @param game The game state object.
     */
    public HumanPlayer(int colour, int timeDelay) {
        super(colour, timeDelay);
    }

    public Move chooseMove(GameState game, Move hint) {
        return hint;
    }

    /**
     * Converts a string representation of a position to a Position object.
     * @param string The string representation of the position.
     * @return The Position object.
     * @throws InvalidMoveException if an invalid position is entered.
     */
    public Position stringToPosition(String string) throws InvalidMoveException {
        String stringArray[] = string.split(",");

        if (stringArray.length != 2) {
            throw new InvalidMoveException("Invalid position: " + string);
        }

        int[] coords = new int[4];
        coords[0] = (int) stringArray[0].charAt(0) - 97; // numbers convert nicely
        coords[1] = Integer.parseInt(stringArray[1]); // ascii conversion alphabet to index

        return new Position(coords[0], coords[1]);
    }
}