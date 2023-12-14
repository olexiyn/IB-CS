import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Grid g = new Grid();
    System.out.println("Please, choose the mode that you want to play: ");
    Scanner answ = new Scanner(System.in);
    System.out.println("1. Enter 'PVP' to play Player vs Player mode.");
    System.out.println("2. Enter 'PVC' to play Player vs Computer mode.");
    String mode = answ.nextLine();
    // create full game of tictactoe using Grid
    g.print();

    char smb1 = 'X';
    char smb2 = 'O';
    char smb = 'X';
    int md = 0;
    if (mode.equals("PVC")) {
      System.out.println("Do you want to play first? (YES/NO)");
      String temp = answ.nextLine();
      if (temp.equals("YES")) {
        md = 1;
      }
    }
    boolean ce = false;
    System.out.println("Do you want to play CHRISTMAS EDITION? (YES/NO)");
    String temp = answ.nextLine();
    if (temp.equals("YES")) {
      ce = true;
      smb1 = '❄';
      smb2 = '☃';
    }

    for (int i = 0; i < 9; i++) {
      Scanner s = new Scanner(System.in);
      if (i % 2 == 0) {
        smb = smb1;
      } else {
        smb = smb2;
      }
      int row;
      int col;
      while (true) {
        System.out.println("Player " + smb + "'s turn.");
        if (mode.equals("PVC") && i % 2 == md) {
          row = g.minimax(2, smb)[1] + 1;
          col = g.minimax(2, smb)[2] + 1;
          break;
        }
        while (true) {
          System.out.println("Enter row: ");
          row = s.nextInt();
          if (row < 4 && row > 0) {
            break;
          }
          System.out.println("Pick a number between 1 and 3!");
        }
        while (true) {
          System.out.println("Enter column: ");
          col = s.nextInt();
          if (col < 4 && col > 0) {
            break;
          }
          System.out.println("Pick a number between 1 and 3!");
        }
        if (g.get(row - 1, col - 1) != ' ') {
          System.out.println("It is taken! Try another one.");
          continue;
        }
        break;
      }
      g.set(row - 1, col - 1, smb);
      g.print();
      System.out.println();
      if (g.wstate()) {
        System.out.println("Player " + smb + " wins!");
        break;
      }
    }
    if (!g.wstate()) {
      System.out.println("It is a TIE!");
    }
  }
}
