package Entities;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

import Entities.Interface.IUpdate;
public class Projectile extends Entity implements Runnable, IUpdate {

    private boolean wantToDestroy = false;
    private int timeDestroyitSelf = 3;
    private Thread projectileThread;
    public Projectile(int worldX, int worldY)
    {
        try {
            entityImage = ImageIO.read(getClass().getResourceAsStream("/Images/Projectile.png"));
        } catch (IOException e) {
           
            e.printStackTrace();
        }
        StartThread();
        setPosition(worldX, worldY);
    }
    private void StartThread()
    {
        projectileThread = new Thread(this);
        projectileThread.start();
    }
    private void setPosition(int worldX,int worldY)
    {
        this.worldX = worldX;
        this.worldY = worldY;
        speed = 20;
    }
    public void update()
    {
        this.worldY-=speed;
    }
    public void draw(Graphics2D g2,int tileSize)
    {
        g2.drawImage(entityImage,worldX, worldY,tileSize/7,tileSize/2,null);
    }
    @Override
    public void run() {
        try {
            Thread.sleep(timeDestroyitSelf*1000);
            wantToDestroy = true;

        } catch (InterruptedException e) {       
            e.printStackTrace();
        }

    }
    public boolean IsWantedDestroy(){return wantToDestroy;}
}
