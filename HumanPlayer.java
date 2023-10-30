import java.io.Console;

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
    public HumanPlayer(int colour, GameState game) {
        super(colour, game);
    }

    /**
     * Gets the move to be made by the player.
     * @return The move to be made.
     * @throws InvalidMoveException If an invalid move is entered.
     */
    public Move getMove() throws InvalidMoveException {
        Console cons = System.console();
        Position origin, destination;
        String input;
        Move move;
        while (true){
            System.out.println("> Enter origin:");
            input = cons.readLine();  // Read user input
            if (input.equals("skip")) {
                move = new Move();
                break;
            }
            try {
                origin = stringToPosition(input);  // Read user input
            }
            catch (InvalidMoveException e) {
                throw e;
            }

            System.out.println("> Enter destination:");
            input = cons.readLine();  // Read user input
            try {
                destination = stringToPosition(input);  // Read user input
            }
            catch (InvalidMoveException e) {
                throw e;
            }

            Piece originPiece = game.getPiece(origin);
            if (originPiece == null) {
                throw new InvalidMoveException("No piece at origin: " + origin);
            }

            if (originPiece.getColour() != this.getColour()) {
                throw new InvalidMoveException("Not your piece at origin: " + origin);
            }

            move = new Move(originPiece, destination);
            break;
        }

        return move;
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