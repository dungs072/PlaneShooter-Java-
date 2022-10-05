package Entities.Effects;

import java.util.ArrayList;

import java.awt.Graphics2D;
import javax.imageio.ImageIO;

import Entities.Interface.IPoolObject;
import main.GamePanel;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class BoomEffect extends Effect implements IPoolObject {

    private float timeAnimation = 0.04f;
    private float accumulatedTimeAnimation =0f;
    private GamePanel gamePanel;
    {
        
        maxImage = 7;
        counter = 0;
    }

    public BoomEffect(GamePanel gamePanel,int worldX, int worldY)
    {
        this.worldX = worldX;
        this.worldY = worldY;
        this.gamePanel = gamePanel;
        imageEffects = new ArrayList<BufferedImage>();
        setUpImages();
    }
    private void setUpImages()
    {
        for(int i =0;i<maxImage;i++)
        {
            String path = "/Images/BoomEffect/EffectBoom"+(i+1)+".png";
            try {
                imageEffects.add(ImageIO.read(getClass().getResourceAsStream(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void setPosition(int worldX, int worldY)
    {
        this.worldX = worldX;
        this.worldY = worldY;
    }
    public void draw(Graphics2D g2)
    {
        if(counter==imageEffects.size()){return;}
        
        
        g2.drawImage(imageEffects.get(counter), worldX, worldY,gamePanel.getTileSize(),gamePanel.getTileSize(),null);
        
        accumulatedTimeAnimation+=gamePanel.getDeltaTime();
        if(accumulatedTimeAnimation<timeAnimation*1000){return;}
        accumulatedTimeAnimation = 0;
        counter++;
    }
    @Override
    public boolean canReuse() {
        
        return counter==imageEffects.size();
    }
    @Override
    public void reuseObj() {
        // TODO Auto-generated method stub
        
    }
    public void reuseObj(int worldX, int worldY)
    {
        setPosition(worldX, worldY);
    }
}
