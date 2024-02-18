package com.ui;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.filehandler.SpriteSheet;
import com.gameMechanics.PlayerEquipment;
import com.main.Game;;

public class EquipmentGUI {
    int stop, start;
    SpriteSheet slotEquipment = new SpriteSheet("/assets/GUI/Banners/Equipment.png");
    public PlayerEquipment pe;
    public Game game;
    public int x, y;

    public PlayerEquipment playerEquipment;
    public EquipmentGUI(Game game){
        this.game = game;
        stop = (Game.WIDTH-slotEquipment.image.getWidth())-16;
        start = Game.WIDTH+64;
        x = start;
        y = 8;
        pe = game.getPlayerObject().playerEquipment;
    }
    public void tick(boolean state){
        if(state){
            if(x > stop){
                x -= 50;
            }
        }else{
            if(x < start){
                x += 50;
            }
        }
    }

    public void drawEquipmentSlot(Graphics2D g2d){
        g2d.drawImage(slotEquipment.image, x, y, null);
        g2d.drawImage(game.getPlayerObject().idleDown[0], x+32, y+48, null);
    }

    public Rectangle getBound(){
        return new Rectangle(x, y, slotEquipment.image.getWidth(), slotEquipment.image.getHeight());
    }
}
