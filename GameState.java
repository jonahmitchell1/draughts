import java.util.Set;
import java.util.HashSet;

public class GameState {
    private HashSet<Piece> pieces;

    public GameState() {
        pieces = new HashSet<Piece>();
        reset();
    }

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

    public Piece getPiece(Position position) {
        for (Piece piece : pieces) {
            if (piece.getX() == position.getX() && piece.getY() == position.getY()) {
                return piece;
            }
        }
        return null;
    }

    public Set<Move> getValidMoves(int player) {
        Set<Move> validMoves = new HashSet<Move>();
        for (Piece piece : pieces) {
            if (piece.getColour() == player) {
                validMoves.addAll(getValidMoves(piece));
            }
        }
        return validMoves;
    }

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

    public void move(Move move) {
        Piece piece = move.getPiece();

        if (move.isHop()) {
            Position pos = new Position((piece.getX() + move.getX()) / 2, (piece.getY() + move.getY()) / 2);
            pieces.remove(getPiece(pos));
        }

        piece.setPosition(move.getPosition());

        if (piece.getColour() == 1 && piece.getX() == 7) {
            piece.promote();
        }
        else if (piece.getColour() == -1 && piece.getX() == 0) {
            piece.promote();
        }
    }

    public String piecesToString() {
        String str = "";
        for (Piece piece : pieces) {
            str += piece + "\n";
        }
        return str;
    }

    private boolean outOfBounds(Position position) {

        return (position.getX() < 0 || position.getX() > 7 || position.getY() < 0 || position.getY() > 7);
    }

    public boolean gameOver() {
        for (Piece piece : pieces) {
            if (!getValidMoves(piece).isEmpty()) {
                return false;
            }
        }
        return true;
    }

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