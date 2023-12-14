import java.util.*;

public class Grid {
  private char[][] grid;
  protected char mySeed; // computer's seed
  protected char oppSeed; // opponent's seed

  public Grid() {
    grid = new char[3][3];
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        grid[row][col] = ' ';
      }
    }
  }

  public void set(int row, int col, char p) {
    grid[row][col] = p;
  }

  public char get(int row, int col) {
    return grid[row][col];
  }

  public void print() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        System.out.print("[" + grid[row][col] + "]");
      }
      System.out.println();
    }
  }

  public boolean wstate() {
    for (int row = 0; row < 3; row++) {
      if (grid[row][0] == grid[row][1] && grid[row][1] == grid[row][2] && grid[row][0] != ' ') {
        return true;
      }
    }
    for (int col = 0; col < 3; col++) {
      if (grid[0][col] == grid[1][col] && grid[1][col] == grid[2][col] && grid[0][col] != ' ') {
        return true;
      }
    }
    if (grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[0][0] != ' ') {
      return true;
    }
    if (grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0] && grid[0][2] != ' ') {
      return true;
    }
    return false;
  }

  int[] move() {
    int[] result = minimax(2, mySeed); // depth, max turn
    return new int[] { result[1], result[2] }; // row, col
  }

  public int[] minimax(int depth, char player) {
    // Generate possible next moves in a List of int[2] of {row, col}.
    List<int[]> nextMoves = generateMoves();

    // mySeed is maximizing; while oppSeed is minimizing
    int bestScore = (player == mySeed) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    int currentScore;
    int bestRow = -1;
    int bestCol = -1;

    if (nextMoves.isEmpty() || depth == 0) {
      // Gameover or depth reached, evaluate score
      bestScore = evaluate();
    } else {
      for (int[] move : nextMoves) {
        // Try this move for the current "player"
        grid[move[0]][move[1]] = player;
        if (player == mySeed) { // mySeed (computer) is maximizing player
          currentScore = minimax(depth - 1, oppSeed)[0];
          if (currentScore > bestScore) {
            bestScore = currentScore;
            bestRow = move[0];
            bestCol = move[1];
          }
        } else { // oppSeed is minimizing player
          currentScore = minimax(depth - 1, mySeed)[0];
          if (currentScore < bestScore) {
            bestScore = currentScore;
            bestRow = move[0];
            bestCol = move[1];
          }
        }
        // Undo move
        grid[move[0]][move[1]] = ' ';
      }
    }
    return new int[] { bestScore, bestRow, bestCol };
  }

  public List<int[]> generateMoves() {
    List<int[]> nextMoves = new ArrayList<int[]>(); // allocate List

    // If gameover, i.e., no next move
    if (this.wstate()) {
      return nextMoves; // return empty list
    }

    // Search for empty cells and add to the List
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (grid[row][col] == ' ') {
          nextMoves.add(new int[] { row, col });
        }
      }
    }
    return nextMoves;
  }

  public int evaluate() {
    int score = 0;
    // Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
    score += evaluateLine(0, 0, 0, 1, 0, 2); // row 0
    score += evaluateLine(1, 0, 1, 1, 1, 2); // row 1
    score += evaluateLine(2, 0, 2, 1, 2, 2); // row 2
    score += evaluateLine(0, 0, 1, 0, 2, 0); // col 0
    score += evaluateLine(0, 1, 1, 1, 2, 1); // col 1
    score += evaluateLine(0, 2, 1, 2, 2, 2); // col 2
    score += evaluateLine(0, 0, 1, 1, 2, 2); // diagonal
    score += evaluateLine(0, 2, 1, 1, 2, 0); // alternate diagonal
    return score;
  }

  public int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
    int score = 0;

    // First cell
    if (grid[row1][col1] == mySeed) {
      score = 1;
    } else if (grid[row1][col1] == oppSeed) {
      score = -1;
    }

    // Second cell
    if (grid[row2][col2] == mySeed) {
      if (score == 1) { // cell1 is mySeed
        score = 10;
      } else if (score == -1) { // cell1 is oppSeed
        return 0;
      } else { // cell1 is empty
        score = 1;
      }
    } else if (grid[row2][col2] == oppSeed) {
      if (score == -1) { // cell1 is oppSeed
        score = -10;
      } else if (score == 1) { // cell1 is mySeed
        return 0;
      } else { // cell1 is empty
        score = -1;
      }
    }

    // Third cell
    if (grid[row3][col3] == mySeed) {
      if (score > 0) { // cell1 and/or cell2 is mySeed
        score *= 10;
      } else if (score < 0) { // cell1 and/or cell2 is oppSeed
        return 0;
      } else { // cell1 and cell2 are empty
        score = 1;
      }
    } else if (grid[row3][col3] == oppSeed) {
      if (score < 0) { // cell1 and/or cell2 is oppSeed
        score *= 10;
      } else if (score > 1) { // cell1 and/or cell2 is mySeed
        return 0;
      } else { // cell1 and cell2 are empty
        score = -1;
      }
    }
    return score;
  }
}
