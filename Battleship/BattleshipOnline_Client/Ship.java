public class Ship
{
    private static final int UNSET = -1;
    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;

    private int row = UNSET;
    private int col = UNSET;
    private int length = UNSET;
    private int direction = UNSET;
    
    public Ship(int theLength){
        length = theLength;
    }
    
    public boolean isLocationSet(){
        if(row==UNSET && col==UNSET){
            return false;
        }
        return true;
    }
    
    public boolean isDirectionSet(){
        if(direction==UNSET){
            return false;
        }
        return true;
    }
    
    public void setLocation(int theRow, int theCol)
    {
        row = theRow;
        col = theCol;
    }
    
    public void setDirection(int theDirection)
    {
        direction = theDirection;
    }
    
    public int getRow()
    {
        return row;
    }
    
    public int getCol()
    {
        return col;
    }
    
    public int getLength()
    {
        return length;
    }
    
    public int getDirection()
    {
        return direction;
    }
    
    private String directionToString()
    {
        if(direction == HORIZONTAL){
            return "horizontal";
        }
        if(direction == VERTICAL){
            return "vertical";
        }
        return "unset direction";
    }
    
    private String locationToString()
    {
        if(!isLocationSet()){
            return "(unset location)";
        }
        return "(" + row + ", " + col + ")";
    }
    
    public String toString(){
        return (String)(directionToString() + " ship of length " + getLength() + " at " + locationToString());
    }
}