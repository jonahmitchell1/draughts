import java.util.Set;

public class GameDriver {
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private GameState game;

    public GameDriver (Player player1, Player player2, GameState game) {
        this.currentPlayer = player1; // black starts
        this.player1 = player1;
        this.player2 = player2;
        this.player1.setOpponent(player2);
        this.player2.setOpponent(player1);
        this.game = game;
        System.out.println(game);
        game.reset();
        play();
    }

    public void play() {
        while (!game.gameOver()) {
            System.out.println(game.toString());
            System.out.println(currentPlayer.getColour() + "'s turn");
            
            Move move;
            Set<Move> validMoves = game.getValidMoves(currentPlayer.getColour());
            boolean validMove;
            while (true) {
                try {
                    move = currentPlayer.getMove();
                }
                catch (InvalidMoveException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                validMove = false;
                for (Move m : validMoves) {
                    if (m.equals(move)) {
                        validMove = true;
                        break;
                    }
                }

                if (validMove) {
                    game.move(move);
                    this.currentPlayer = this.currentPlayer.getOpponent();
                    break;
                }
                else {
                    System.out.println("Invalid move. Please try again. ");
                }
            }
        }
    }
}