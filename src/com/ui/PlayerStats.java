package com.ui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.filehandler.SpriteSheet;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Entity;
import com.obj.GameObject;

public class PlayerStats {
    SpriteSheet ss = new SpriteSheet("/assets/GUI/PlayerIcon/FighterPP.png");
    BufferedImage playerIcon;
    Entity player;
    Font f1 = new Font("DialogInput", Font.BOLD, 25);
    public void drawPlayerIcon(Graphics2D g2d){
        g2d.drawImage(playerIcon, 16, 16, null);
    }
    public void drawPlayerStats(Graphics2D g2d){
        g2d.setFont(f1);
        int x = getHeight(g2d, String.valueOf(player.hp));
        g2d.drawString("HP      :"+String.valueOf(player.hp), 96, 32);
        g2d.drawString("MANA    :"+String.valueOf(player.mana), 96, x+16);
        g2d.drawString("STAMINA :"+String.valueOf((int)player.stamina), 96, x+32);
    }

    public void checkPlayer(Game game){
        ArrayList<GameObject> objects = game.tryWorld.objectLayer.get(0);
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if(temp.getID() == ID.Entity){
                Entity entityTemp = (Entity) temp;
                if(entityTemp.getEntityType() == EntityType.Player && entityTemp.getEntityClass() == EntityClass.Archer){
                    playerIcon = ss.grabImage(2, 1, 64, 64);
                    player = entityTemp;
                }else if(entityTemp.getEntityType() == EntityType.Player && entityTemp.getEntityClass() == EntityClass.Fighter){
                    playerIcon = ss.grabImage(1, 1, 64, 64);
                    player = entityTemp;
                }
            }
        }
    }
    public int getHeight(Graphics2D g2, String text) {
		int x = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
		return x;
	}
}