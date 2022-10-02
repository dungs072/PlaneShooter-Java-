package main;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import Entities.*;

public class GamePanel extends JPanel implements Runnable{
    //Screen settings
    private final int originalTitleSize = 16;
    private final int scale = 3;

    private final int tileSize = originalTitleSize*scale;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize* maxScreenCol;
    private final int screenHeight = tileSize* maxScreenRow;

    //FPS
    private final int FPS = 60;

    private Thread gameThread;
    private Player player;
    private KeyHandler keyHandler;
    private Background backGround;

    //Pool
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    {
        keyHandler = new KeyHandler();
        player = new Player(this, keyHandler);
        backGround = new Background();
       
       
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
        
        for(int i =0;i<3;i++)
        {
            try {
                Thread.sleep(2000);
                enemies.add(new Enemy(this));
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
        }
    
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
        player.update();
        updateEnemy();
        
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
    //enemy
    private void updateEnemy()
    {
        for (Enemy enemy : enemies) {
            enemy.update();
        }
    }
    private void paintEnemy(Graphics2D g2)
    {
        for (Enemy enemy : enemies) {
            enemy.draw(g2);
        }
    }
}
