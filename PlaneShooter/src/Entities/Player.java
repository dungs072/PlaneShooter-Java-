package Entities;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

import Entities.Interface.IUpdate;

import java.util.ArrayList;

import main.*;
public class Player extends Entity implements IUpdate{
    private GamePanel gamePanel;
    private KeyHandler keyHandler;

    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
   
    private int maxX;
    private int maxY;

    //position spawn projectile
    public Player(GamePanel gp, KeyHandler keyH)
    {
        gamePanel = gp;
        keyHandler = keyH;
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
    public void update()
    {
        handleMovement();
        handleFire();
        handleProjectile();
    }
    private void handleProjectile()
    {
        for(int i =0;i<projectiles.size();i++)
        {
            if(projectiles.get(i).IsWantedDestroy())
            {
                projectiles.remove(i);
                i--;
            }
           
        }
        for(Projectile projectile:projectiles)
        {
            projectile.update();
        }
    }
    private void handleFire()
    {
        if(keyHandler.IsFirePressed())
        {
            
            projectiles.add(new Projectile(worldX+21,worldY-7));

        }
    }
    private void handleMovement() {
        int valueX = worldX;
        int valueY = worldY;
        if(keyHandler.IsUpPressed())
        {
            valueY-=speed;
        }
        else if(keyHandler.IsDownPressed())
        {
            valueY+=speed;
        }
        else if(keyHandler.IsLeftPressed())
        {
            valueX-=speed;
        }
        else if(keyHandler.IsRightPressed())
        {
            valueX+=speed;
        }

        if(valueX<maxX-50&&valueX>0)
        {
            worldX = valueX;
        }
        if(valueY<maxY-50&&valueY>0)
        {
            worldY = valueY;
        }
    }
    public void draw(Graphics2D g2)
    {
        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gamePanel.getTileSize(), gamePanel.getTileSize());
        for(Projectile projectile:projectiles)
        {
            projectile.draw(g2,gamePanel.getTileSize());;
        }
        g2.drawImage(entityImage, worldX, worldY,gamePanel.getTileSize(),gamePanel.getTileSize(),null);
        
    }
}
