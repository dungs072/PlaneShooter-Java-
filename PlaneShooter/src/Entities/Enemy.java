package Entities;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import Entities.Interface.IPoolObject;
import Entities.Interface.IUpdate;
import main.GamePanel;

public class Enemy extends Entity implements IUpdate, IPoolObject{
    private int[][] mapPoints;
    private GamePanel gamePanel;
    private Direction nextDirection;
    private MapVector2 currentMapPosition;
    private MapVector2 desiredPosition;
    
    private int maxCol = 0;
    private int maxRow = 0;
    private String[] pathMaps = {"/Paths/map.txt","/Paths/map2.txt","/Paths/map3.txt",
                                "/Paths/map4.txt","/Paths/map5.txt"};
    public Enemy(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        currentMapPosition = MapVector2.zero();
        desiredPosition = MapVector2.zero();
        nextDirection = Direction.NONE;
        try {
            entityImage = ImageIO.read(getClass().getResourceAsStream("/Images/EnemyPlane.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        maxCol = gamePanel.getMaxScreenCol();
        maxRow = gamePanel.getMaxScreenRow()+2;
        mapPoints = new int[maxRow][maxCol];
        setDefaultValue();
        
    }
    public void setDefaultValue()
    {
        loadMap();
        worldY = -1;
        speed  =1;
        for(int i =0;i<maxCol;i++)
        {
            if(mapPoints[0][i]==1)
            {
                worldX = gamePanel.getTileSize()*i;
                currentMapPosition.setXY(i,0);
                desiredPosition.setXY(i,0);
                break;
            }
        }
    }
    private void loadMap()
    {
        try {
            Random random = new Random();
            int randomNumber = random.nextInt(0,pathMaps.length);
            InputStream is = getClass().getResourceAsStream(pathMaps[randomNumber]);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while(col<maxCol&&row<maxRow)
            {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                while(col<maxCol)
                {

                    Integer num = Integer.parseInt(numbers[col]);
                    mapPoints[row][col] = num; 
                    col++;
                }
                if(col==maxCol)
                {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void findNextPoint()
    {
        int x = currentMapPosition.getX();
        int y = currentMapPosition.getY();
        //move forward;
        y+=1;
        if(hasRightPoint(x, y))
        {
            nextDirection = Direction.FORWARD;
            return;
        }
        else y-=1;
        // //move backward;
        // y-=1;
        // if(hasRightPoint(x, y))
        // {
        //     nextDirection = Direction.BACKWARD;
        //     return;
        // }
        // else y+=1;
        //move left;
        x-=1;
        if(hasRightPoint(x, y))
        {
            nextDirection = Direction.LEFT;
            return;
        }
        else x+=1;
        //move right;
        x+=1;
        if(hasRightPoint(x, y))
        {
            nextDirection = Direction.RIGHT;
            return;
        }
    }
    private boolean hasRightPoint(int x,int y)
    {
        return (x>=0&&x<maxCol)&&
                (y>=0&&y<maxRow) &&
                (mapPoints[y][x]>0);
    }
    private boolean isMovedToRightPosition(int desireX,int desireY)
    {
        return worldX==desireX&&worldY == desireY;
    }
    private void calculateDesiredPosition()
    {
        if(nextDirection==Direction.NONE){return;}
        int x = currentMapPosition.getX();
        int y = currentMapPosition.getY();
        if(nextDirection==Direction.FORWARD)
        {
            y+=1;
        }
        else if(nextDirection==Direction.BACKWARD)
        {
            y-=1;
        }
        else if(nextDirection==Direction.LEFT)
        {
            x-=1;
        }
        else if(nextDirection==Direction.RIGHT)
        {
            x+=1;
        }
        desiredPosition.setXY(x*gamePanel.getTileSize(), y*gamePanel.getTileSize());
    }
    @Override
    public void update() {
        Move();
        if(nextDirection==Direction.NONE)
        {
            findNextPoint();
            calculateDesiredPosition();
        }  
        if(!isMovedToRightPosition(desiredPosition.getX(), desiredPosition.getY())){return;}
        mapPoints[currentMapPosition.getY()][currentMapPosition.getX()] = 0;
        currentMapPosition.setXY(desiredPosition.getX()/gamePanel.getTileSize(), 
                                desiredPosition.getY()/gamePanel.getTileSize());
        nextDirection = Direction.NONE;
       
    }
    private void Move() {
        if(worldX<desiredPosition.getX())
        {
            worldX+=speed;
        }
        if(worldY<desiredPosition.getY())
        {
            worldY+=speed;
        }
        if(worldX>desiredPosition.getX())
        {
            worldX-=speed;
        }
        if(worldY>desiredPosition.getY())
        {
            worldY-=speed;
        }
    }
    public void draw(Graphics2D g2)
    {
        g2.drawImage(entityImage, worldX, worldY,null);
    }
    @Override
    public boolean canReuse() {
        
        return isReachDestination();
    }
    @Override
    public void reuseObj() {
        setDefaultValue();
        nextDirection = Direction.NONE;
    }
    private boolean isReachDestination()
    {
        return currentMapPosition.getY()==maxRow-1;
    }
   


}
