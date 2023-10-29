public class GameDriver {
    private Player currentPlayer;
    private GameState game;

    public GameDriver (Player player1, Player player2) {
        this.currentPlayer = player1; // black starts
        this.game = new GameState();
        System.out.println(game);
        game.reset();
        play();
    }

    public void play() {
        while (!game.gameOver()) {
            System.out.println(game.toString());
            System.out.println(currentPlayer.getColour() + "'s turn");
            
        }
    }

}