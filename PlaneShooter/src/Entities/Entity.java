package Entities;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
public class Entity  {
    protected int worldX;
    protected int worldY;
    protected int speed;
    protected Direction nextDirection;
    protected BufferedImage entityImage;
    protected Rectangle solidArea;
    protected boolean collisionOn = false;

    public int getWorldX(){return worldX;}
    public int getWorldY(){return worldY;}
    public Rectangle getSolidArea(){return solidArea;}

}
    
