import java.util.*;

public class AI {
    private char aiDisc;
    private char humanDisc;
    private static final int MAX_DEPTH = 5;

    public AI(char aiDisc, char humanDisc) {
        this.aiDisc = aiDisc;
        this.humanDisc = humanDisc;
    }

    public int getBestMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        int bestCol = 0;

        for (int col = 0; col < Board.COLS; col++) {
            if (board.isColumnFull(col)) continue;

            board.dropDisc(col, aiDisc);
            int score = minimax(board, MAX_DEPTH - 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.undoMove(col);

            if (score > bestScore) {
                bestScore = score;
                bestCol = col;
            }
        }
        return bestCol;
    }

    private int minimax(Board board, int depth, boolean maximizingPlayer, int alpha, int beta) {
        if (board.checkWin(aiDisc)) return 100000;
        if (board.checkWin(humanDisc)) return -100000;
        if (board.isFull() || depth == 0) return evaluateBoard(board);

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int col = 0; col < Board.COLS; col++) {
                if (board.isColumnFull(col)) continue;
                board.dropDisc(col, aiDisc);
                int eval = minimax(board, depth - 1, false, alpha, beta);
                board.undoMove(col);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < Board.COLS; col++) {
                if (board.isColumnFull(col)) continue;
                board.dropDisc(col, humanDisc);
                int eval = minimax(board, depth - 1, true, alpha, beta);
                board.undoMove(col);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    private int evaluateBoard(Board board) {
        // Simple heuristic: count potential 2s and 3s in a row for AI minus human
        // This is a simplified heuristic — can be improved

        int score = 0;
        score += countPatterns(board, aiDisc, 3) * 100;
        score += countPatterns(board, aiDisc, 2) * 10;

        score -= countPatterns(board, humanDisc, 3) * 100;
        score -= countPatterns(board, humanDisc, 2) * 10;

        return score;
    }

    private int countPatterns(Board board, char disc, int length) {
        int count = 0;
        char[][] grid = board.getGrid();

        // Horizontal
        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c <= Board.COLS - length; c++) {
                int discs = 0, empties = 0;
                for (int i = 0; i < length; i++) {
                    if (grid[r][c+i] == disc) discs++;
                    else if (grid[r][c+i] == Board.EMPTY) empties++;
                }
                if (discs == length) count++;
                else if (discs + empties == length && discs > 0) count++;
            }
        }
        // Vertical
        for (int c = 0; c < Board.COLS; c++) {
            for (int r = 0; r <= Board.ROWS - length; r++) {
                int discs = 0, empties = 0;
                for (int i = 0; i < length; i++) {
                    if (grid[r+i][c] == disc) discs++;
                    else if (grid[r+i][c] == Board.EMPTY) empties++;
                }
                if (discs == length) count++;
                else if (discs + empties == length && discs > 0) count++;
            }
        }
        // Diagonal (bottom-left to top-right)
        for (int r = length - 1; r < Board.ROWS; r++) {
            for (int c = 0; c <= Board.COLS - length; c++) {
                int discs = 0, empties = 0;
                for (int i = 0; i < length; i++) {
                    if (grid[r - i][c + i] == disc) discs++;
                    else if (grid[r - i][c + i] == Board.EMPTY) empties++;
                }
                if (discs == length) count++;
                else if (discs + empties == length && discs > 0) count++;
            }
        }
        // Diagonal (top-left to bottom-right)
        for (int r = 0; r <= Board.ROWS - length; r++) {
            for (int c = 0; c <= Board.COLS - length; c++) {
                int discs = 0, empties = 0;
                for (int i = 0; i < length; i++) {
                    if (grid[r + i][c + i] == disc) discs++;
                    else if (grid[r + i][c + i] == Board.EMPTY) empties++;
                }
                if (discs == length) count++;
                else if (discs + empties == length && discs > 0) count++;
            }
        }

        return count;
    }
}