package Entities;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Enemy extends Entity implements IUpdate{
    private int[][] mapPoints;
    private GamePanel gamePanel;
    public Enemy(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
        try {
            entityImage = ImageIO.read(getClass().getResourceAsStream("/Images/EnemyPlane.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mapPoints = new int[gamePanel.getMaxScreenCol()][gamePanel.getMaxScreenRow()];
        setDefaultValue();
        
    }
    public void setDefaultValue()
    {
        worldX = 48;
        worldY = 0;
        speed  =4;
        loadMap();
    }
    private void loadMap()
    {
        try {
            InputStream is = getClass().getResourceAsStream("/path/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while(col<gamePanel.getMaxScreenCol()&&row<gamePanel.getMaxScreenRow())
            {
                String line = br.readLine();
                while(col<gamePanel.getMaxScreenCol())
                {
                    String[] numbers = line.split(" ");
                    Integer num = Integer.parseInt(numbers[col]);
                    mapPoints[col][row] = num; 
                    col++;
                }
                if(col==gamePanel.getMaxScreenCol())
                {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    @Override
    public void update() {
        
    }
    public void draw(Graphics2D g2)
    {
        g2.drawImage(entityImage, worldX, worldY,null);
    }
   
    
}
