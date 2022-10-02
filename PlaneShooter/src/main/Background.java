package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {

    private BufferedImage backgroundImage;
    {
       try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/Images/Background_sahara.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getBackgroundImage(){return backgroundImage;}
    public void draw(Graphics2D g2,int width, int height)
    {
        g2.drawImage(backgroundImage,0,0,width,height,null);
    }
}
