public class Location
{
    //add singleton
    private static Location cell = null;
    
    
    public static final int UNGUESSED = 0;
    public static final int HIT = 1;
    public static final int MISSED = 2;
        
    private boolean has_ship;
    private int status;
        
    public Location()
    {
        has_ship = false;
        status = UNGUESSED;
    }
    
    public boolean checkHit()
    {
        return (status==HIT);
    }
    
    public boolean checkMiss()
    {
        return (status==MISSED);
    }
        
    public boolean isUnguessed()
    {
        return (status==UNGUESSED);
    }
    
    public void markHit()
    {
        status = HIT;
    }
    
    public void markMiss()
    {
        status = MISSED;
    }
    
    public boolean hasShip()
    {
        return has_ship;
    }
    
    public void setShip(boolean val)
    {
        has_ship = val;
    }
    
    public void setStatus(int theStatus)
    {
        status = theStatus;
    }
    
    public int getStatus()
    {
        return status;
    }
}