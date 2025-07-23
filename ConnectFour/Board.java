public class Board {
    public static final int ROWS = 6;
    public static final int COLS = 7;
    private char[][] grid;
    public static final char EMPTY = '.';

    public Board() {
        grid = new char[ROWS][COLS];
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                grid[r][c] = EMPTY;
    }

    public boolean dropDisc(int col, char disc) {
        if (col < 0 || col >= COLS) return false;
        for (int r = ROWS - 1; r >= 0; r--) {
            if (grid[r][col] == EMPTY) {
                grid[r][col] = disc;
                return true;
            }
        }
        return false; // column full
    }

    public boolean isFull() {
        for (int c = 0; c < COLS; c++) {
            if (grid[0][c] == EMPTY) return false;
        }
        return true;
    }

    public boolean checkWin(char disc) {
        // Horizontal check
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c <= COLS - 4; c++) {
                if (grid[r][c] == disc && grid[r][c+1] == disc &&
                    grid[r][c+2] == disc && grid[r][c+3] == disc)
                    return true;
            }
        }
        // Vertical check
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r <= ROWS - 4; r++) {
                if (grid[r][c] == disc && grid[r+1][c] == disc &&
                    grid[r+2][c] == disc && grid[r+3][c] == disc)
                    return true;
            }
        }
        // Diagonal (bottom-left to top-right)
        for (int r = 3; r < ROWS; r++) {
            for (int c = 0; c <= COLS - 4; c++) {
                if (grid[r][c] == disc && grid[r-1][c+1] == disc &&
                    grid[r-2][c+2] == disc && grid[r-3][c+3] == disc)
                    return true;
            }
        }
        // Diagonal (top-left to bottom-right)
        for (int r = 0; r <= ROWS - 4; r++) {
            for (int c = 0; c <= COLS - 4; c++) {
                if (grid[r][c] == disc && grid[r+1][c+1] == disc &&
                    grid[r+2][c+2] == disc && grid[r+3][c+3] == disc)
                    return true;
            }
        }
        return false;
    }

    public void printBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                System.out.print(grid[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println("0 1 2 3 4 5 6"); // Column indices
    }

    public char[][] getGrid() {
        // Return deep copy if needed (optional)
        return grid;
    }

    public void undoMove(int col) {
        for (int r = 0; r < ROWS; r++) {
            if (grid[r][col] != EMPTY) {
                grid[r][col] = EMPTY;
                break;
            }
        }
    }

    public boolean isColumnFull(int col) {
        return grid[0][col] != EMPTY;
    }
}