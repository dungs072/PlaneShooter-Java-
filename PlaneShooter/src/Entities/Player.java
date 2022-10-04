package Entities;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Rectangle;
import Entities.Interface.*;


import main.*;
public class Player extends Entity implements IUpdate{
    private GamePanel gamePanel;
    private KeyHandler keyHandler;


    private PoolManager poolProjectilesManager;
   
    private int maxX;
    private int maxY;


    
    //position spawn projectile
    public Player(GamePanel gp, KeyHandler keyH)
    {
        gamePanel = gp;
        keyHandler = keyH;
        poolProjectilesManager = new PoolManager();
        
        solidArea = new Rectangle(0,0,48,48);
    
      
        setDefaultValues();
        getPlayerImage();
    }
    public void SetMaxMoveOfPlayer(int maxX,int maxY)
    {
        this.maxX = maxX;
        this.maxY = maxY;
    }
    private void setDefaultValues()
    {
        worldX = 100;
        worldY  =100;
        speed = 4;
    }
    private void getPlayerImage()
    {
        try
        {
            entityImage = ImageIO.read(getClass().getResourceAsStream("/Images/Player.png"));

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void update(long deltaTime)
    {
        handleMovement();
        handleFire();
        handleProjectile(deltaTime);
    }
    private void handleProjectile(long deltaTime)
    {
        for(var tempProjectile:poolProjectilesManager.getPoolObjs())
        {
            Projectile projectile = (Projectile)tempProjectile;
            //if(projectile.IsWantedDestroy()){continue;}
            projectile.update(deltaTime);
        }
    }
    private void handleFire()
    {
        if(keyHandler.IsFirePressed())
        {
            IPoolObject tempObj = poolProjectilesManager.getReadyObject();
            if(tempObj!=null)
            {
                Projectile projectile = (Projectile) tempObj;
                projectile.reuseObj(worldX+21, worldY-7);
            }
            else
            {
                
                poolProjectilesManager.AddPoolObject(new Projectile(worldX+21, worldY-7));  
            }


        }
    }
    private void handleMovement() {
        int valueX = worldX;
        int valueY = worldY;
        if(keyHandler.IsUpPressed())
        {
            nextDirection = Direction.FORWARD;
            valueY-=speed;
        }
        else if(keyHandler.IsDownPressed())
        {
            nextDirection = Direction.BACKWARD;
            valueY+=speed;
        }
        else if(keyHandler.IsLeftPressed())
        {
            nextDirection = Direction.LEFT;
            valueX-=speed;
        }
        else if(keyHandler.IsRightPressed())
        {
            nextDirection = Direction.RIGHT;
            valueX+=speed;
        }
        else
        {
            nextDirection = Direction.NONE;
        }

        if(valueX<maxX-50&&valueX>0)
        {
            worldX = valueX;
        }
        if(valueY<maxY-50&&valueY>0)
        {
            worldY = valueY;
        }
        solidArea.setLocation(worldX,worldY);
    }
    public void draw(Graphics2D g2)
    {
        //g2.setColor(Color.white);
        g2.fillRect(worldX, worldY, 48, 48);
        for(var tempProjectile:poolProjectilesManager.getPoolObjs())
        {
            Projectile projectile = (Projectile)tempProjectile;
            projectile.draw(g2,gamePanel.getTileSize());
        }
        g2.drawImage(entityImage, worldX, worldY,gamePanel.getTileSize(),gamePanel.getTileSize(),null);
        
    }
}
