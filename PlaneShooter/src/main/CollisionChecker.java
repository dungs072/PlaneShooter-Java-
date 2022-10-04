package main;

import Entities.Entity;

public class CollisionChecker {
    GamePanel gamePanel;
    public CollisionChecker(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }
    public void CheckEntity(Entity entity)
    {
        int entityLeftWorldX = entity.getWorldX()+entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + 
                                entity.getSolidArea().x+
                                entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY()+entity.getSolidArea().y+ entity.getSolidArea().height;
        
        int entityLeftCol = entityLeftWorldX/gamePanel.getTileSize();
        int entityRightCol = entityRightWorldX/gamePanel.getTileSize();
        int entityTopRow = entityTopWorldY/gamePanel.getTileSize();
        int entityBottomRow = entityBottomWorldY/gamePanel.getTileSize();

        

    }
}
