public class Draughts {
    public final int startPlayer = 1;

    public static void main(String[] args) {
        GameDriver driver;
        Player player1;
        Player player2;

        player1 = new HumanPlayer(-1); // black
        player2 = new HumanPlayer(1); // white

        driver = new GameDriver(player1, player2);
    }
}
