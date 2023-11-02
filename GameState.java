import java.util.Set;
import java.util.HashSet;

/**
 * Represents the state of a game of checkers.
 * The game state includes the positions of all pieces on the board, and provides methods for moving pieces and checking for valid moves.
 * The board is an 8x8 grid, with the top-left corner being a dark square.
 * The pieces are arranged in three rows on each side of the board, with each row containing four pieces.
 * The pieces are placed on the dark squares only.
 * The player with the white pieces moves first.
 * 
 * @author jonahmitchell1
 */
public class GameState {
    private HashSet<Piece> pieces;
    public Player currentPlayer;
    public int turnsTaken;
    private Piece hopPiece;

    /**
     * Constructs a new game state with no pieces.
     */
    public GameState() {
        pieces = new HashSet<Piece>();
        reset();
    }

    /**
     * Resets the game state by clearing all pieces and setting up the initial piece positions.
     * The board is an 8x8 grid, with the top-left corner being a light square.
     * The pieces are arranged in three rows on each side of the board, with each row containing four pieces.
     * The pieces are placed on the dark squares only.
     * The player with the white pieces moves first.
     */
    public void reset() {
        pieces.clear();
        int width = 8;
        int height = 8;
        // set piece positions
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < width; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 1) {
                        pieces.add(new Piece(1, new Position(i, j)));
                        pieces.add(new Piece(-1, new Position(height - i - 1, width - j - 1)));
                    }
                } 
                else {
                    if (j % 2 == 0) {
                        pieces.add(new Piece(1, new Position(i, j)));
                        pieces.add(new Piece(-1, new Position(height - i - 1, width - j - 1)));
                    }
                }
            }
        }
    }

    /**
     * Returns the piece at the specified position.
     *
     * @param position the position of the piece to retrieve
     * @return the piece at the specified position, or null if no piece is found
     */
    public Piece getPiece(Position position) {
        for (Piece piece : pieces) {
            if (piece.getX() == position.getX() && piece.getY() == position.getY()) {
                return piece;
            }
        }
        return null;
    }

    public Set<Piece> getPieces() {
        return pieces;
    }

    /**
     * Returns a set of valid moves for the current player.
     * @return a set of valid moves for the current player.
     */
    public Set<Move> getValidMoves() {
        Set<Move> validMoves = new HashSet<Move>();
        for (Piece piece : pieces) {
            if (piece.getColour() == currentPlayer.getColour()) {
                validMoves.addAll(getValidMoves(piece));
            }
        }
        return validMoves;
    }

    /**
     * Returns a set of valid moves for the given piece.
     * 
     * @param piece the piece to get valid moves for
     * @return a set of valid moves for the given piece
     */
    
    public Set<Move> getValidMoves(Piece piece) {
        Set<Move> validMoves = new HashSet<Move>();

        int x = piece.getX();
        int y = piece.getY();
        int colour = piece.getColour();

        // check if piece is a king
        boolean isKing = piece.isKing();

        // check for valid moves in all directions
        int[][] positiveXDirections = {{1, 1}, {1, -1}};
        int[][] negativeXDirections = {{-1, 1}, {-1, -1}};
        int dx, dy;
        Position newPosition;

        // check for valid moves in the forward direction
        if (colour == 1 || isKing) {
            for (int[] dir : positiveXDirections) {
                dx = dir[0];
                dy = dir[1];

                newPosition = new Position(x + dx, y + dy);
                if (!newPosition.isOutOfBounds()) {
                    Piece targetPiece = getPiece(newPosition);
                    if (targetPiece == null) {
                        if (turnsTaken < 1) {
                            validMoves.add(new Move(piece, newPosition));
                        }
                    } 
                    else if (targetPiece.getColour() != colour) { // check for hop over opponent
                        newPosition.addX(dx);
                        newPosition.addY(dy);
                        if (!newPosition.isOutOfBounds()) {
                            targetPiece = getPiece(newPosition);
                            if (targetPiece == null) {
                                validMoves.add(new Move(piece, newPosition));
                            }
                        }
                    }
                }
            }
        }

        // check for valid moves in the backward direction
        if (colour == -1 || isKing) {
            for (int[] dir : negativeXDirections) {
                dx = dir[0];
                dy = dir[1];

                newPosition = new Position(x + dx, y + dy);
                if (!newPosition.isOutOfBounds()) {
                    Piece targetPiece = getPiece(newPosition);
                    if (targetPiece == null) {
                        if (turnsTaken < 1) {
                            validMoves.add(new Move(piece, newPosition));
                        }
                    } 
                    else if (targetPiece.getColour() != colour) { // check for hop over opponent
                        newPosition.addX(dx);
                        newPosition.addY(dy);
                        if (!newPosition.isOutOfBounds()) {
                            targetPiece = getPiece(newPosition);
                            if (targetPiece == null) {
                                validMoves.add(new Move(piece, newPosition));
                            }
                        }
                    }
                }
            }
        }

        return validMoves;
    }

    public boolean checkValidMove(Move move) {
        Set<Move> validMoves = getValidMoves();
        // disallow movement of other pieces when chaining hops
        if (turnsTaken >= 1) {
            if (move.getPiece() != hopPiece) {
                return false;
            }
            else if (!move.isHop()) {
                return false;
            }
        }
        // cannot move from a square with no piece
        if (move.getPiece() == null) {
            return false;
        }
        return validMoves.contains(move);
    }

    /**
     * Moves a piece on the board according to the given move object.
     * If the move is a hop, removes the piece that was jumped over and checks if the player can take another turn.
     * If the moved piece reaches the opposite end of the board, promotes it to a king.
     * @param move The move object containing the piece to be moved and its destination.
     * @return True if the move was a hop, false otherwise.
     */
    public void move(Move move) {
        boolean isHop = move.isHop();
        Piece piece = move.getPiece();
        this.turnsTaken++;

        if (isHop) {
            hopPiece = piece; //used for enforcing moving of the same piece in subsequent chained moves
            Position pos = new Position((piece.getX() + move.getX()) / 2, (piece.getY() + move.getY()) / 2);
            System.out.println("Removing: "+ getPiece(pos));
            pieces.remove(getPiece(pos));
        }
        else {
            this.currentPlayer = this.currentPlayer.getOpponent();
            this.turnsTaken = 0;
        }

        piece.setPosition(move.getPosition());

        if (piece.getColour() == 1 && piece.getX() == 7) {
            piece.promote();
        }
        else if (piece.getColour() == -1 && piece.getX() == 0) {
            piece.promote();
        }

        if (isHop && getValidMoves(piece).size() == 0) { // if player now has no available moves, change player.
                this.currentPlayer = this.currentPlayer.getOpponent();
                this.turnsTaken = 0;
        }
        
        if (gameOver()) {
            System.out.println("Game over! " + currentPlayer.getOpponent().getColourAsString() + " wins!");
        }
        else {
            currentPlayer.chooseMove(this);
        }
    }

    /**
     * Ends the current player's turn. They must had a turn first or have no moves available to them.
     * @return True if the turn was ended, false otherwise.
     */
    public boolean endTurn() {
        if ( (this.turnsTaken > 0) || (this.getValidMoves().size() == 0) ) {
            this.currentPlayer = this.currentPlayer.getOpponent();
            this.turnsTaken = 0;
            return true;
        }
        return false;
    }

    /**
     * Checks if the game is over.
     * @return true if the game is over, false otherwise
     */
    public boolean gameOver() {
        if (this.getValidMoves().size() == 0) { // If the current player has no moves available to them.
            return true;
        }
        for (Piece piece : pieces) { // If the current player has no pieces left.
            if (piece.getColour() == currentPlayer.getColour()) {
                return false;
            }
        }
        return true;
    }

    public GameState deepCopy() {
        GameState copy = new GameState();
        copy.pieces = new HashSet<Piece>();
        for (Piece piece : pieces) {
            copy.pieces.add(piece.deepCopy());
        }
        copy.currentPlayer = currentPlayer;
        copy.turnsTaken = turnsTaken;
        if (this.hopPiece != null) {
            copy.hopPiece = this.hopPiece.deepCopy();
        }
        else {
            copy.hopPiece = null;
        }
        return copy;
    }

    /**
     * Returns a string representation of the game state.
     * @return a string representation of the game state
     */
    @Override
    public String toString() {
        int[][] board = new int[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] =0;
            }
        }
        for (Piece piece : pieces) {
            board[piece.getX()][piece.getY()] = piece.getColour();
        }

        String str = "";
        char r = 'a';
        for (int i = 0; i < board[0].length; i++) {
            str += "\t" + i + "\t";
        }
        str += "\n";
        for (int[] row : board) {
            str += r + "|\t";
            r++;
            for (int piece : row) {
                str += piece + "\t|\t";
            }
            str += "\n";
        }
        return str;
    }

}