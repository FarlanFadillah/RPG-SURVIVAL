package com.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.filehandler.SpriteSheet;
import com.gameMechanics.Slot;
import com.main.Game;
import com.obj.Entity;

public class InventoryGUI {
    SpriteSheet inventoryBox = new SpriteSheet("/assets/GUI/Banners/Carved_36Slides_WithFrame.png");
    SpriteSheet itemSheet = new SpriteSheet("/assets/items/ItemSpriteSheet.png");
    BufferedImage[] itemImages = new BufferedImage[100];
    int xstop = 0;
    int xstart = -400;
    int x = xstart;
    int y = 8;
    Font f1 = new Font("DialogInput", Font.BOLD, 12);
    public float opacity = 0f;
    Game game;
    Entity player;
    public Slot[] slot;
    public InventoryGUI(Game game){
        this.game = game;
        player = game.getPlayerObject();
        slot = player.playerInventory.itemSlot;
        getItemsImage();
    }
    public void tick(boolean openInventory){
        if(openInventory){
            if(x < xstop){
                x += 50;
            }
            if(opacity < 0.5f){
                opacity += 0.1f;
            }
        }else{
            if(x > xstart){
                x -= 50;
            }
            if(opacity > 0.1f){
                opacity -= 0.05f;
            }
        }
    }
    public void drawInventory(Graphics2D g2d){
        if(x > xstart){
            g2d.setColor(Color.BLACK);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g2d.drawImage(inventoryBox.image, x, y,inventoryBox.image.getWidth(),inventoryBox.image.getHeight(), null);
            drawItemStored(g2d, x, y);
        }

    }

    public void drawItemStored(Graphics2D g2d, int x, int y){
        for (int i = 0; i < slot.length; i++) {
            if(slot[i].type != null){
                g2d.drawImage(slot[i].icon, x+(slot[i].col*64), y+ (slot[i].row*64), null);
                g2d.setColor(Color.white);
                g2d.setFont(f1);
                g2d.drawString(String.valueOf(slot[i].total), x+(slot[i].col*64)+44, y+ (slot[i].row*64)+55);
            }
        }
        
    }
    public int[][] getItem(){
        return new int[1][1];
    }

    public void getItemsImage(){
        itemImages[0] = itemSheet.grabImage(1, 1, 64, 64);
    }

}
