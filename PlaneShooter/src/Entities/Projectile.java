package Entities;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

import Entities.Effects.BoomEffect;
import Entities.Interface.IPoolObject;
import Entities.Interface.IUpdate;
import main.GamePanel;
import main.PoolManager;
public class Projectile extends Entity implements IUpdate,IPoolObject {

    private boolean isReadyToReuse = false;
    private boolean canBoom = false;
    private int timeDestroyitSelf = 3;
    private int accumulatedTimeDestroyitSelf = 0;
    private GamePanel gamePanel;
    private PoolManager boomEffectPool;
    public void IsReadyToReuse(boolean wantToDestroy){this.isReadyToReuse = wantToDestroy;}
    public Projectile(GamePanel gamePanel,int worldX, int worldY)
    {
        boomEffectPool = new PoolManager();
        this.gamePanel = gamePanel;
        try {
            entityImage = ImageIO.read(getClass().getResourceAsStream("/Images/Projectile.png"));
        } catch (IOException e) {
           
            e.printStackTrace();
        }
        solidArea = new Rectangle(0,0,10,10);
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
        if(isReadyToReuse){return;}
        solidArea.setLocation(worldX, worldY);
        accumulatedTimeDestroyitSelf+=deltaTime;
        this.worldY-=speed;
        OnCollisionCheck();
        if(accumulatedTimeDestroyitSelf<timeDestroyitSelf*1000){return;}
        accumulatedTimeDestroyitSelf = 0;
        isReadyToReuse = true;
    }
    private void OnCollisionCheck()
    {
        PoolManager poolEnemy = GamePanel.EnemyPoolManager;
        for(var tempObj: poolEnemy.getPoolObjs())
        {
            if(tempObj.canReuse()){continue;}
            Enemy enemy = (Enemy)tempObj;
            if(solidArea.intersects(enemy.getSolidArea()))
            {
                IPoolObject temp = boomEffectPool.getReadyObject();
                if(temp!=null)
                {
                    BoomEffect boomEffect = (BoomEffect)temp;
                    boomEffect.reuseObj(worldX, worldY);
                }
                else
                {
                    boomEffectPool.AddPoolObject(new BoomEffect(gamePanel, worldX, worldY));
                }
                canBoom = true;
                isReadyToReuse = true;
            }
        }
    }
    public void draw(Graphics2D g2,int tileSize)
    {
        for(var boomEffect: boomEffectPool.getPoolObjs())
        {
            if(boomEffect.canReuse()){continue;}
            BoomEffect currentBoomEffect = (BoomEffect)boomEffect;
            currentBoomEffect.draw(g2);
        }
        if(isReadyToReuse){return;}
        g2.fillRect(worldX, worldY, 6, 18);
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
        canBoom = false;
        setPosition(worldX, worldY);  
    }
}
