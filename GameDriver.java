import java.util.Scanner;
import java.util.HashSet;

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
            while (true) {
                try {
                    move = currentPlayer.getMove();
                }
                catch (InvalidMoveException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                if (game.getValidMoves().contains(move)) {
                    game.move(move);
                    this.currentPlayer = this.currentPlayer.getOpponent();
                    break;
                }
                else {
                    System.out.println("Invalid move. ");
                }
            }
        }
    }
}