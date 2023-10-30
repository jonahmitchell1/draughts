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

    /**
     * Constructs a new game state with no pieces.
     */
    public GameState() {
        pieces = new HashSet<Piece>();
        reset();
    }

    /**
     * Resets the game state by clearing all pieces and setting up the initial piece positions.
     * The board is an 8x8 grid, with the top-left corner being a dark square.
     * The pieces are arranged in three rows on each side of the board, with each row containing four pieces.
     * The pieces are placed on the dark squares only.
     * The player with the white pieces moves first.
     */
    public void reset() {
        pieces.clear();
        int width = 8;
        int height = 8;
        // set piece positions
        Piece toAdd;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < width; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 1) {
                        toAdd = new Piece(1, new Position(i, j));
                        pieces.add(toAdd);
                        pieces.add(new Piece(-1, new Position(height - i - 1, width - j - 1)));
                    }
                } 
                else {
                    if (j % 2 == 0) {
                        toAdd = new Piece(1, new Position(i, j));
                        pieces.add(toAdd);
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

    /**
     * Returns a set of valid moves for the given player.
     * @param player the player for whom to get the valid moves
     * @return a set of valid moves for the given player
     */
    public Set<Move> getValidMoves(int player) {
        Set<Move> validMoves = new HashSet<Move>();
        for (Piece piece : pieces) {
            if (piece.getColour() == player) {
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
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        Position newPosition;
        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];

            // check for valid moves in the forward direction
            if (colour == 1 || isKing) {
                newPosition = new Position(x + dx, y + dy);
                if (!outOfBounds(newPosition)) {
                    Piece targetPiece = getPiece(newPosition);
                    if (targetPiece == null) {
                        validMoves.add(new Move(piece, newPosition));
                    } 
                    else if (targetPiece.getColour() != colour) { // check for hop over opponent
                        newPosition.addX(dx);
                        newPosition.addY(dy);
                        if (!outOfBounds(newPosition)) {
                            targetPiece = getPiece(newPosition);
                            if (targetPiece == null) {
                                validMoves.add(new Move(piece, newPosition));
                            }
                        }
                    }
                }
            }

            // check for valid moves in the backward direction
            if (colour == -1 || isKing) {
                newPosition = new Position(x - dx, y - dy);
                if (!outOfBounds(newPosition)) {
                    Piece targetPiece = getPiece(newPosition);
                    if (targetPiece == null) {
                        validMoves.add(new Move(piece, newPosition));
                    } 
                    else if (targetPiece.getColour() != colour) { // check for hop over opponent
                        newPosition.addX(-dx);
                        newPosition.addY(-dy);
                        if (!outOfBounds(newPosition)) {
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

    /**
     * Moves a piece on the board according to the given move object.
     * If the move is a hop, removes the piece that was jumped over.
     * If the moved piece reaches the opposite end of the board, promotes it to a king.
     * @param move The move object containing the piece to be moved and its destination.
     * @return True if the move was a hop, false otherwise.
     */
    public boolean move(Move move) {
        Piece piece = move.getPiece();
        boolean rtn = false;

        if (move.isHop()) {
            Position pos = new Position((piece.getX() + move.getX()) / 2, (piece.getY() + move.getY()) / 2);
            pieces.remove(getPiece(pos));
            rtn = true;
            // player has another go
        }

        piece.setPosition(move.getPosition());

        if (piece.getColour() == 1 && piece.getX() == 7) {
            piece.promote();
        }
        else if (piece.getColour() == -1 && piece.getX() == 0) {
            piece.promote();
        }

        return rtn;
    }

    /**
     * Checks if the given position is out of bounds.
     * @param position the position to check
     * @return true if the position is out of bounds, false otherwise
     */
    private boolean outOfBounds(Position position) {

        return (position.getX() < 0 || position.getX() > 7 || position.getY() < 0 || position.getY() > 7);
    }

    /**
     * Checks if the game is over.
     * @param currentPlayer the player whose turn it is
     * @return true if the game is over, false otherwise
     */
    public boolean gameOver(Player currentPlayer) {
        for (Piece piece : pieces) {
            if (piece.getColour() == currentPlayer.getColour()) {
                if (!getValidMoves(piece).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
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