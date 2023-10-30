import java.util.Set;

/**
 * The GameDriver class is responsible for managing the game state and player turns. 
 * It initializes the game with two players and a game state, and starts the game by calling the play() method.
 * The play() method runs a loop until the game is over, where it prints the current game state and prompts the current player for their move.
 * It then checks if the move is valid, and if so, makes the move and switches to the next player's turn.
 * If the move is not valid, it prompts the player to try again.
 * The extraTurn() method is called when a player makes a move that allows them to take an extra turn.
 * It prompts the player for their move and checks if it is valid, and if so, makes the move and calls itself recursively to allow the player to take another turn.
 * If the move is not valid or the player chooses not to take another turn, it returns false.
 * 
 * @author jonahmitchell1
 */
public class GameDriver {
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private GameState game;

    /**
     * Constructs a new GameDriver object with the given players and game state.
     * @param player1 the first player
     * @param player2 the second player
     * @param game the game state
     */
    public GameDriver (Player player1, Player player2, GameState game) {
        this.currentPlayer = player1; // black starts
        this.player1 = player1;
        this.player2 = player2;
        this.player1.setOpponent(player2);
        this.player2.setOpponent(player1);
        this.game = game;
        game.reset();
        play();
    }

    /**
     * Starts the game.
     */
    public void play() {
        while (!game.gameOver(currentPlayer)) {
            System.out.println(game.toString());
            System.out.println(currentPlayer.getColourAsString() + "'s turn");
            
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
                    if (game.move(move)) {
                        extraTurn(move.getPiece());
                    }

                    this.currentPlayer = this.currentPlayer.getOpponent();
                    break;
                }
                else {
                    System.out.println("Invalid move. Please try again. ");
                }
            }
        }
    }

    /**
     * Allows the player to take an extra turn.
     * @param piece the piece that was moved
     * @return true if the player takes an extra turn, false otherwise
     */
    public boolean extraTurn(Piece piece) {
        System.out.println(game.toString());
        System.out.println(currentPlayer.getColourAsString() + "'s turn");

        Move move;
        boolean validMove;
        Set<Move> validMoves = game.getValidMoves(piece);
        while (true) {
            try {
                move = currentPlayer.getMove();
            }
            catch (InvalidMoveException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (move.isSkip()) {
                return false;
            }

            validMove = false;
            for (Move m : validMoves) {
                if (m.equals(move)) {
                    validMove = true;
                    break;
                }
            }

            if (validMove) {
                if (game.move(move)) {
                    return extraTurn(piece);
                }
                else {
                    return false;
                }
            }
            else {
                System.out.println("Invalid move. Please try again. ");
            }
        }
    }
}