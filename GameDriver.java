import java.util.Scanner;
import java.util.HashSet;

public class GameDriver {
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private GameState game;

    public GameDriver (Player player1, Player player2) {
        this.currentPlayer = player1; // black starts
        this.player1 = player1;
        this.player2 = player2;
        this.player1.setOpponent(player2);
        this.player2.setOpponent(player1);
        this.game = new GameState();
        System.out.println(game);
        game.reset();
        play();
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        String moveString;
        while (!game.gameOver()) {
            System.out.println(game.toString());
            System.out.println(currentPlayer.getColour() + "'s turn");
            
            System.out.println("Enter the coordinates of the piece you wish to move followed by the coordinates of the position you wish to move it to, separated by a commas (e.g. 5,2,3,4): ");
            moveString = scanner.nextLine();  // Read user input
            String[] coords = moveString.split(",");
            Piece toMove = game.getPiece(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
            Move move = new Move(toMove, new Position(Integer.parseInt(coords[2]), Integer.parseInt(coords[3])));

            HashSet<Move> validMoves = (HashSet<Move>)game.getValidMoves(toMove);
            if (validMoves.contains(move)) {
                game.move(move);
                this.currentPlayer = this.currentPlayer.getOpponent();
            }
            else {
                System.out.println("Invalid move");
            }
        }
    }
}