import java.util.*;
import java.net.*;
import java.io.*;

public class Battleship_Server
{
    static int portNumber = 6354;
    static int int_clientinput(){
        try (
            ServerSocket serverSocket =
                new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();     
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            int inputLine = Integer.parseInt(in.readLine());
            return inputLine;
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
        return 11;
    }
    public static String str_clientinput(){
        try (
            ServerSocket serverSocket =
                new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();     
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine = in.readLine();
            return inputLine;
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
        return "error";
    }

    private static Player player = new Player();
    private static Player computer = new Player();;
    static Scanner sc = new Scanner(System.in);

    private static void askForGuess(){
        int row=-1, col=-1;
        while(true){
            System.out.println("Guess a row (where A is 1, ..., J is 10) of opponent's Battleship: ");
            row = sc.nextInt();
            if(row>0 && row<=10){
                row--;
                break;
            }
            System.out.println("Enter a number between 1 and 10!");
        }
        while(true){
            System.out.println("Guess a column of opponent's Battleship: ");
            col = sc.nextInt();
            if(col>0 && col<=10){
                col--;
                break;
            }
            System.out.println("Enter a number between 1 and 10!");
        }
        if(computer.recordOpponentGuess(row, col)){
            player.OPP_GRID.markHit(row, col);
            System.out.println("HIT!");
        }
        else{
            player.OPP_GRID.markMiss(row, col);
            System.out.println("MISS!");
        }
    }
    
    public static void main(String[] args)
    {
        System.out.println("Welcome to Battleship!");
        int tempint = int_clientinput();
        String tempstr = str_clientinput();
        System.out.println(tempint + " - " + tempstr);
        
        //player sets battleships
        System.out.println("Set your Battleships: ");
        for(int i=0; i<5; i++){
            boolean temp=true;
            int row=-1, col=-1;
            int direction=-1;
            while(temp){
                temp = false;
                while(true){
                    System.out.println("Enter a row (where A is 1, ..., J is 10) for Battleship of length " + player.SHIP_LENGTHS[i] + ": ");
                    row = sc.nextInt();
                    if(row>0 && row<=10){
                        row--;
                        break;
                    }
                    System.out.println("Enter a number between 1 and 10!");
                }
                while(true){
                    System.out.println("Enter a column for Battleship of length " + player.SHIP_LENGTHS[i] + ": ");
                    col = sc.nextInt();
                    if(col>0 && col<=10){
                        col--;
                        break;
                    }
                    System.out.println("Enter a number between 1 and 10!");
                }
                while(true){
                    System.out.println("Enter a direction (horizontal is 0, vertical is 1) for Battleship of length " + player.SHIP_LENGTHS[i] + ": ");
                    direction = sc.nextInt();
                    if(direction==0 || direction==1){
                        break;
                    }
                    System.out.println("Enter 0 or 1!");
                }
                int maxrow=9, maxcol=9;
                if(direction==0){
                    maxcol-=player.SHIP_LENGTHS[i];
                }
                else{
                    maxrow-=player.SHIP_LENGTHS[i];
                }
                if(row>maxrow || col>maxcol){
                    System.out.println("The ship is out of the area!");
                    temp=true;
                }
                else{
                    int temp_row=row, temp_col=col;
                    for(int j=0; j<player.SHIP_LENGTHS[i]; j++){
                        if(player.MY_GRID.hasShip(temp_row, temp_col)){
                            System.out.println("There is already a ship in that position or your ships intersect!");
                            temp=true;
                            break;
                        }
                        if(direction==0){
                            temp_col++;
                        }
                        else{
                            temp_row++;
                        }
                    }
                }
            }
            Ship temp_ship = new Ship(player.SHIP_LENGTHS[i]);
            player.chooseShipLocation(temp_ship, row, col, direction);
        }
        
        //computer sets battleships
        for(int i=0; i<5; i++){
            boolean temp=true;
            int row=-1, col=-1;
            int direction = -1;
            int maxrow=9, maxcol=9;
            while(temp){
                temp=false;
                maxrow=9;
                maxcol=9;
                direction = Randomizer.nextInt(0, 1);
                if(direction==0){
                    maxcol-=computer.SHIP_LENGTHS[i];
                }
                else{
                    maxrow-=computer.SHIP_LENGTHS[i];
                }
                row = Randomizer.nextInt(0, maxrow);
                col = Randomizer.nextInt(0, maxcol);
                int temp_row=row, temp_col=col;
                for(int j=0; j<computer.SHIP_LENGTHS[i]; j++){
                    if(computer.MY_GRID.hasShip(temp_row, temp_col)){
                        temp=true;
                        break;
                    }
                    if(direction==0){
                        temp_col++;
                    }
                    else{
                        temp_row++;
                    }
                }
            }
            Ship temp_ship = new Ship(computer.SHIP_LENGTHS[i]);
            computer.chooseShipLocation(temp_ship, row, col, direction);
        }
        
        //game
        for(int i=0; i<200; i++){
            if(i%2 == 0){
                System.out.println("Your Turn!");
                System.out.println("Your Ships: ");
                player.printMyShips();
                System.out.println("Your Guesses: ");
                player.printMyGuesses();
                System.out.println("Opponent's Guesses: ");
                player.printOpponentGuesses();
                askForGuess();
            }
            else{
                System.out.println("Opponent's Turn!");
                int row = Randomizer.nextInt(1, 10);
                int col = Randomizer.nextInt(1, 10);
                System.out.println("Opponent guessed row " + row + " and column " + col + ". And it is...");
                row--;
                col--;
                if(player.recordOpponentGuess(row, col)){
                    computer.OPP_GRID.markHit(row, col);
                    System.out.println("HIT!");
                }
                else{
                    computer.OPP_GRID.markMiss(row, col);
                    System.out.println("MISS!");
                }
            }
            if(computer.hp <= 0){
                System.out.println("GAME OVER");
                System.out.println("YOU WON");
                break;
            }
            if(player.hp <= 0){
                System.out.println("GAME OVER");
                System.out.println("YOU LOST");
                break;
            }
        }
        sc.close();
    }
}