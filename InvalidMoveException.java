
/**
 * This exception is thrown when an invalid move is attempted in the game of draughts.
 * @author jonahmitchell1
 */
public class InvalidMoveException extends Exception {
    /**
     * Constructs a new InvalidMoveException object with the given message.
     * @param message the message to be displayed when the exception is thrown
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}