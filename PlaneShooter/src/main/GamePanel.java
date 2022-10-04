package main;
import java.awt.*;

import javax.swing.JPanel;
import Entities.*;
import Entities.Interface.IPoolObject;

public class GamePanel extends JPanel implements Runnable{
    //Screen settings
    private final int originalTitleSize = 16;
    private final int scale = 3;

    private final int tileSize = originalTitleSize*scale;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize* maxScreenCol;
    private final int screenHeight = tileSize* maxScreenRow;

    //Collision
    private CollisionChecker cChecker;
    public CollisionChecker getCChecker(){return cChecker;}
    //FPS
    private final int FPS = 60;

    private Thread gameThread;
    private Player player;
    private KeyHandler keyHandler;
    private Background backGround;

    //Pool
    public static PoolManager EnemyPoolManager;

    //Delta time
    private long deltaTime = 0;
  

    //enemies
    private float timeSpawnEnemyAfterSecond = 2;
    private float accumulateTimeSpawnEnemy = 0;
    {
        keyHandler = new KeyHandler();
        EnemyPoolManager = new PoolManager();
        player = new Player(this, keyHandler);
        backGround = new Background();
        cChecker = new CollisionChecker(this);
        
    }
    public int getTileSize(){return tileSize;}
    public int getMaxScreenCol(){return maxScreenCol;}
    public int getMaxScreenRow(){return maxScreenRow;}
    /**
     * 
     */
    public GamePanel()
    {
        
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);  
        player.SetMaxMoveOfPlayer(screenWidth, screenHeight);
    
    }
    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
        
      
    }
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime()+drawInterval;
        while(gameThread!=null)
        {
            update();
            repaint();
            try
            {
                double remainingTime = nextDrawTime-System.nanoTime();
                remainingTime/=1000000;
                if(remainingTime<0)
                {
                    remainingTime = 0;
                }
                deltaTime = (long)remainingTime;
                Thread.sleep((long)remainingTime);
                nextDrawTime+=drawInterval;
            }catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }   
    }
    public void update()
    {
        player.update(deltaTime);
        spawnEnemy();
        updateEnemy();
        handleCollision();
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        // layer base on the order what image draw first
        backGround.draw(g2,this.getWidth(),this.getHeight());
        paintEnemy(g2);
        player.draw(g2);
        g2.dispose();
    }
    private void handleCollision()
    {
        for(var tempObj : EnemyPoolManager.getPoolObjs())
        {
            if(tempObj.canReuse()){continue;}
            Entity entity = (Entity)tempObj;
            if(entity.getSolidArea().intersects(player.getSolidArea()))
            {
                System.out.println("Boom");
            }
        }
    }
    //enemy
    private void spawnEnemy()
    {
        accumulateTimeSpawnEnemy+=deltaTime;   
        if(accumulateTimeSpawnEnemy<timeSpawnEnemyAfterSecond*1000){return;}
        accumulateTimeSpawnEnemy = 0;
        
        IPoolObject tempObj = EnemyPoolManager.getReadyObject();
        if(tempObj!=null)
        {
            tempObj.reuseObj();
        }
        else
        {
            EnemyPoolManager.AddPoolObject(new Enemy(this));
        }
      
    }

    private void updateEnemy()
    {
        for (var enemy : EnemyPoolManager.getPoolObjs()) {
            Enemy newEnemy = (Enemy)enemy;
            newEnemy.update(deltaTime);
        }
    }
    private void paintEnemy(Graphics2D g2)
    {
        for (var enemy : EnemyPoolManager.getPoolObjs()) {
            Enemy newEnemy = (Enemy)enemy;
            newEnemy.draw(g2);
        }
    }
}
