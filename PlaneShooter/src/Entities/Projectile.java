package Entities;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

import Entities.Interface.IPoolObject;
import Entities.Interface.IUpdate;
public class Projectile extends Entity implements IUpdate,IPoolObject {

    private boolean isReadyToReuse = false;
    private int timeDestroyitSelf = 3;
    private int accumulatedTimeDestroyitSelf = 0;
    public void IsReadyToReuse(boolean wantToDestroy){this.isReadyToReuse = wantToDestroy;}
    public Projectile(int worldX, int worldY)
    {
        try {
            entityImage = ImageIO.read(getClass().getResourceAsStream("/Images/Projectile.png"));
        } catch (IOException e) {
           
            e.printStackTrace();
        }
        
        setPosition(worldX, worldY);
    }
    private void setPosition(int worldX,int worldY)
    {
        this.worldX = worldX;
        this.worldY = worldY;
        speed = 20;
    }
    public void update(long deltaTime)
    {
        accumulatedTimeDestroyitSelf+=deltaTime;
        this.worldY-=speed;
        if(accumulatedTimeDestroyitSelf<timeDestroyitSelf*1000){return;}
        accumulatedTimeDestroyitSelf = 0;
        isReadyToReuse = true;
    }
    public void draw(Graphics2D g2,int tileSize)
    {
        g2.drawImage(entityImage,worldX, worldY,tileSize/7,tileSize/2,null);
    }
    public boolean IsWantedDestroy(){return isReadyToReuse;}
    @Override
    public boolean canReuse() {
        
        return isReadyToReuse;
    }
    @Override
    public void reuseObj() {
         
    }
    public void reuseObj(int worldX, int worldY)
    {
        isReadyToReuse = false;
        setPosition(worldX, worldY);  
    }
}
