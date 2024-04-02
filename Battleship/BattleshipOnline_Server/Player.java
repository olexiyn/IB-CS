public class Player
{
    public static final int[] SHIP_LENGTHS = {2, 3, 3, 4, 5};
    private Ship[] ships;
    public Grid MY_GRID;
    public Grid OPP_GRID;
    private int ships_added=0;
    public int hp=17;
    
    public Player(){
        ships = new Ship[5];
        MY_GRID = new Grid();
        OPP_GRID = new Grid();
        for(int i=0; i<5; i++){
            ships[i] = new Ship(SHIP_LENGTHS[i]);
        }
    }
    
    public void chooseShipLocation(Ship s, int row, int col, int direction)
    {
        if(ships_added<5){
            s.setLocation(row, col);
            s.setDirection(direction);
            MY_GRID.addShip(s);
            ships_added++;
        }
    }
    
    public void printMyShips()
    {
        MY_GRID.printShips();
    }
    
    public void printOpponentGuesses()
    {
        MY_GRID.printStatus();
    }
    
    public void printMyGuesses()
    {
        OPP_GRID.printStatus();
    }
    
    public boolean recordOpponentGuess(int row, int col)
    {
        if(MY_GRID.hasShip(row, col)){
            MY_GRID.markHit(row, col);
            hp--;
            return true;
        }
        else{
            MY_GRID.markMiss(row, col);
            return false;
        }
    }
}