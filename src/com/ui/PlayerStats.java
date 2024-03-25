package com.ui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import com.filehandler.SpriteSheet;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Entity;
import com.obj.GameObject;

import projectile.ArrowProjectile;
import projectile.TNTProjectile;

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
        g2d.drawString("EQUIP   :"+String.valueOf(player.holdingTools), 96, x+48);
        g2d.drawString("SP      :"+String.valueOf(player.skillPoint), 96, x+64);
    }

    public void checkPlayer(Game game){
        List<GameObject> objects = game.tryWorld.entity;
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if(temp.getID() == ID.Entity && temp.getClass() != TNTProjectile.class && temp.getClass() != ArrowProjectile.class){
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
