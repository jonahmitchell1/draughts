public class Draughts {
    public final int startPlayer = 1;

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
