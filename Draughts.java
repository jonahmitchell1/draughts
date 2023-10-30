
/**
 * The Draughts class represents the main class of the Draughts game.
 * It contains the main method and initializes the game by creating a GameState object,
 * two HumanPlayer objects, and a GameDriver object.
 * The startPlayer field is set to 1.
 * 
 * @author jonahmitchell1
 */
public class Draughts {
    public final int startPlayer = 1;

    /**
     * The main method of the Draughts game.
     * It initializes the game by creating a GameState object,
     * two HumanPlayer objects, and a GameDriver object.
     * The startPlayer field is set to 1.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameDriver driver;
        GameState game;
        Player player1;
        Player player2;

        game = new GameState();

        player1 = new HumanPlayer(-1, game); // black
        player2 = new HumanPlayer(1, game); // white

        driver = new GameDriver(player1, player2, game);
    }
}
