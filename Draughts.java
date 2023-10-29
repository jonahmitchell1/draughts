public class Draughts {
    public final int startPlayer = 1;

    public static void main(String[] args) {
        GameState state;
        Player player1;
        Player player2;

        state = new GameState();

        player1 = new HumanPlayer();
        player2 = new AlphaBetaPlayer();
    }
}
