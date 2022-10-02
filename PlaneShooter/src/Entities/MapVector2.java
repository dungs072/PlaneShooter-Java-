package Entities;

public class MapVector2 {
    private int x;
    private int y;
    public static MapVector2 zero()
    {
        return new MapVector2(0, 0);
    }
    public MapVector2(int x,int y)
    {
        setXY(x, y);
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public void setXY(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public String toString()
    {
        return String.format("x = %d and y = %d", x,y);
    }

}
