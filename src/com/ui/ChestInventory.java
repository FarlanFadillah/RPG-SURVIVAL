package com.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.blockList.Chest;
import com.filehandler.SpriteSheet;
import com.gameMechanics.PlayerEquipment;
import com.main.Game;

public class ChestInventory {
    int stop, start;
    SpriteSheet slotInventory = new SpriteSheet("/assets/GUI/Banners/Carved_16Slides_WithFrame.png");
    public PlayerEquipment pe;
    public Game game;
    public int x, y;

    public Chest chestOpen;
    public ChestInventory(Game game){
        this.game = game;
        stop = (Game.WIDTH-slotInventory.image.getWidth())-16;
        start = Game.WIDTH+64;
        x = start;
        y = 8;
        pe = game.getPlayerObject().playerEquipment;
    }
    public void tick(boolean state){
        if(state){
            if(x > stop && chestOpen.openCounter >= 5){
                x -= 50;
                game.gameState = game.InventoryState;
            }
        }else{
            if(x < start){
                x += 50;
                chestOpen.open = false;
            }
        }
    }

    public void drawInventorytSlot(Graphics2D g2d){
        g2d.drawImage(slotInventory.image, x, y, null);
    }

    public Rectangle getBound(){
        return new Rectangle(x, y, slotInventory.image.getWidth(), slotInventory.image.getHeight());
    }
}
