import java.util.Scanner;

public class ConnectFour {
    private Board board;
    private AI ai;
    private Scanner scanner;
    private boolean playWithAI;

    public ConnectFour(boolean playWithAI) {
        this.playWithAI = playWithAI;
        board = new Board();
        scanner = new Scanner(System.in);
        ai = new AI('O', 'X'); // AI is 'O', human is 'X'
    }

    public void start() {
        char currentPlayer = 'X';
        while (true) {
            board.printBoard();

            if (board.isFull()) {
                System.out.println("Game is a draw!");
                break;
            }

            if (currentPlayer == 'X' || !playWithAI) {
                // Human player
                System.out.print("Player " + currentPlayer + ", choose a column (0-6): ");
                int col = scanner.nextInt();

                if (col < 0 || col >= Board.COLS || board.isColumnFull(col)) {
                    System.out.println("Invalid move, try again.");
                    continue;
                }

                board.dropDisc(col, currentPlayer);
            } else {
                // AI player
                System.out.println("AI is thinking...");
                int col = ai.getBestMove(board);
                board.dropDisc(col, currentPlayer);
                System.out.println("AI plays column " + col);
            }

            if (board.checkWin(currentPlayer)) {
                board.printBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                break;
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Play vs AI? (yes/no): ");
        boolean vsAI = s.nextLine().trim().equalsIgnoreCase("yes");
        ConnectFour game = new ConnectFour(vsAI);
        game.start();
    }
}
