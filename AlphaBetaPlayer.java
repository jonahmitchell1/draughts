import java.util.Set;
import java.util.HashSet;

public class AlphaBetaPlayer extends Player{
    private int searchDepth;
    private int alpha, beta;

    public AlphaBetaPlayer(int colour, int timeDelay, int searchDepth) {
        super(colour, timeDelay);
        this.searchDepth = searchDepth;
        this.alpha = Integer.MIN_VALUE;
        this.beta = Integer.MAX_VALUE;
    }

    @Override
    public Move chooseMove(GameState game, Move hint) {
        Move best_move = null, testMove;
        int best_move_val = 0, move_val, v;

        GameState testState = game.deepCopy();
        Set<Move> moves = testState.getValidMoves();

        if (game.currentPlayer.getColour() == 1) {
            v = Integer.MIN_VALUE;
            for (Move move : moves) {
                testState = game.deepCopy();
                testMove = convertMoveToState(testState, move);
                if (testMove == null) {
                    System.out.println("testMove is null");
                    continue;
                }
                testState.move(testMove);

                move_val = minimax_val(testState, this.searchDepth-1, best_move_val, beta);

                if (move_val > best_move_val || best_move == null) {
                    best_move = testMove;
                    best_move_val = move_val;
                }

                this.alpha = Math.max(alpha, v);
            }
            this.alpha = best_move_val;
        }
        else {
            v = Integer.MAX_VALUE;
            for (Move move : moves) {
                testState = game.deepCopy();
                testMove = convertMoveToState(testState, move);
                testState.move(testMove);

                move_val = minimax_val(testState, this.searchDepth-1, alpha, best_move_val);

                if (move_val < best_move_val || best_move == null) {
                    best_move = testMove;
                    best_move_val = move_val;
                }
                
                this.beta = Math.min(beta, v);
            }
            this.beta = best_move_val;
        }

        return convertMoveToState(game, best_move);
    }

    public Move convertMoveToState(GameState state, Move move) {
        Move rtn;
        HashSet<Move> moves = (HashSet<Move>)state.getValidMoves();
        for (Move m : moves) {
            if (m.equals(move)) {
                rtn = m;
                return rtn;
            }
        }
        return null;
    }

    /**
     * https://github.com/kevingregor/Checkers/blob/master/Final%20Project%20Report.pdf
     * Board Evaluation Heuristic inspired by the report in the above link.
     */
    public int boardEval(GameState game) {
        double[][] basicWeights = {
            {4, 4, 4, 4, 4, 4, 4, 4}, 
            {4, 2, 2, 2, 2, 2, 2, 4},
            {4, 2, 2.5, 2.5, 2.5, 2.5, 2, 4},
            {4, 2, 2.5, 2.5, 2.5, 2.5, 2, 4},
            {4, 2, 2.5, 2.5, 2.5, 2.5, 2, 4},
            {4, 2, 2.5, 2.5, 2.5, 2.5, 2, 4},
            {4, 2, 2, 2, 2, 2, 2, 4},
            {4, 4, 4, 4, 4, 4, 4, 4}
        };
        double value = 0;
        Set<Piece> pieces = game.getPieces();
        for (Piece piece : pieces) {
            int x = piece.getX();
            int y = piece.getY();
            value += piece.getValue() * basicWeights[x][y];

            if (piece.getColour() != this.getColour()) { //check for vulnerable pieces
                Set<Move> opponentMoves = game.getValidMoves(piece);
                for (Move m : opponentMoves) {
                    if (m.isHop()) { // if move would take one of the player's pieces, mark that piece down.
                        value -= 2 * piece.getValue();
                    }
                }
            }
        }
        return (int) Math.round(value);
    }

    public int minimax_val(GameState game, int depth, int alpha, int beta) {
        int v;
        GameState testState;

        // if d = 0 or u is terminal then return static value of Node
        if (depth == 0 || game.gameOver()) {
            return boardEval(game);
        }

        testState = game.deepCopy();
        Set <Move> moves = testState.getValidMoves();

        // else if u is maximizing then
        if (game.currentPlayer.getColour() == 1) {
            // V := −∞
            v = Integer.MIN_VALUE;
            // for w in daughters of u
            for (Move move : moves) {
                testState = game.deepCopy();
                testState.move(move);
                // V := max(V, minimax-val(w,d−1, α, β)
                v = Math.max(v, minimax_val(testState, depth-1, alpha, beta));
                // if V ≥ β return V
                if (v >= beta) {
                    return v;
                }
                // α := max(α, V)
                alpha = Math.max(alpha, v);
            }
            // return V
            return v;
        }
        // else
        else {
            // u := ∞
            v = Integer.MAX_VALUE;
            // for w in daughters of v
            for (Move move : moves) {
                testState =  game.deepCopy();
                testState.move(move);
                // V := min(V, minimax-val(w,d−1, α, β)
                v = Math.min(v, minimax_val(testState, depth-1, alpha, beta));
                // if V ≤ α return V
                if (v <= alpha) {
                    return v;
                }
                // β := min(β, V)
                beta = Math.min(beta, v);
            }
            // return V
            return v;
        }
    }
}
