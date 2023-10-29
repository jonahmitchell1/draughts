import java.util.ArrayList;

public class GameState {
    private int[][] board, oldBoard;
    private int player, oldPlayer;

    public GameState() {
        board = new int[8][8];
    }

    public int getPiece(int i, int j) {
        return board[i][j];
    }

    public void setPiece(int i, int j, int piece) {
        board[i][j] = piece;
    }

    public int[] getScores() {
        int[] score = {0, 0};
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; i < board[i].length; i++) {
                if (board[i][j] == 1) {
                    score[0]++;
                } else if (board[i][j] == -1) {
                    score[1]++;
                }
            }
        }
        return score;
    }

    public int getBoardScore() {
        int[] scores = getScores();
        return scores[0] - scores[1];
    }

    public GameState clone() {
        GameState clone = new GameState();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; i < board[i].length; i++) {
                clone.setPiece(i, j, board[i][j]);
            }
        }
        return clone;
    }

    public void save() {
        oldBoard = board;
        oldPlayer = player;
    }

    public void restore() {
        board = oldBoard;
        player = oldPlayer;
    }



}