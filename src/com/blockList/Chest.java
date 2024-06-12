package com.blockList;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.filehandler.SpriteSheet;
import com.gameMechanics.PlayerInventory;
import com.gameMechanics.Slot;
import com.id.BlockType;
import com.id.ID;
import com.main.Game;
import com.obj.Block;
import com.obj.Entity;
import com.obj.Item;
import com.playerlist.Archer;

public class Chest extends Block{

    SpriteSheet ss = new SpriteSheet("/assets/Block/Chest/Chest.png");

    BufferedImage[] openChest = new BufferedImage[5];
    BufferedImage[] closeChest = new BufferedImage[3];
    public PlayerInventory inventory;
    public boolean open = false;

    public int openCounter = 1, closeCounter = 1;

    @SuppressWarnings("unchecked")
    public Slot<Item>[] slotChest = new Slot[16]; 

    public Chest(int x, int y, ID id, BlockType bt, Game game) {
        super(x, y, id, bt, game);
        //TODO Auto-generated constructor stub
        inventory = new PlayerInventory(game);
        getImage();
        image = closeChest[2];
        closeCounter = 3;

        setSlot();
        
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        spriteCounter();
        Entity player = game.getPlayerObject();
        if(!rangeCheck(player.x + player.image.getWidth()/2, player.y + player.image.getHeight()/2)){
            open = false;
        }
    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        
        animatedSprite();
        g.drawImage(image, x, y, null);
    }

    @Override
    public Rectangle renderOrder() {
        // TODO Auto-generated method stub
        return new Rectangle(x+16, y+20,34,26);
    }

    @Override
    public void getImage() {
        
        openChest[0] = ss.grabImage(1, 1, 64, 64);
        openChest[1] = ss.grabImage(2, 1, 64, 64);
        openChest[2] = ss.grabImage(3, 1, 64, 64);
        openChest[3] = ss.grabImage(4, 1, 64, 64);
        openChest[4] = ss.grabImage(5, 1, 64, 64);

        closeChest[0] = ss.grabImage(5, 1, 64, 64);
        closeChest[1] = ss.grabImage(4, 1, 64, 64);
        closeChest[2] = ss.grabImage(1, 1, 64, 64);

    }

    @Override
    public Rectangle getBound() {
        // TODO Auto-generated method stub
        return new Rectangle(x+16, y+20,34,26);
    }

    @Override
    public Rectangle getSize() {
        // TODO Auto-generated method stub
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    @Override
    public void hit(int damage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hit'");
    }
    
    public void animatedSprite(){
        if(open){
            if(openCounter == 1){
                image = openChest[0];
            }else if(openCounter == 2){
                image = openChest[1];
            }else if(openCounter == 3){
                image = openChest[2];
            }else if(openCounter == 4){
                image = openChest[3];
            }else if(openCounter == 5){
                image = openChest[4];
            }
        }else{
            if(closeCounter == 1){
                image = closeChest[0];
            }else if(closeCounter == 2){
                image = closeChest[1];
            }else if(closeCounter == 3){
                image = closeChest[2];
            }
        }
    }

    public void spriteCounter(){
        if(open){
            closeCounter = 1;
            spriteCounter++;
            if(spriteCounter > 3) {
                if(openCounter == 1){
                    openCounter = 2;
                }else if(openCounter == 2){
                    openCounter = 3;
                }else if(openCounter == 3){
                    openCounter = 4;
                }else if(openCounter == 4){
                    openCounter = 5;
                }else if(openCounter == 5){
                    openCounter = 5;
                }
                spriteCounter = 0;
            }
        }else{
            openCounter = 1;
            spriteCounter++;
            if(spriteCounter > 3) {
                if(closeCounter == 1){
                    closeCounter = 2;
                }else if(closeCounter == 2){
                    closeCounter = 3;
                }else if(closeCounter == 3){
                    closeCounter = 3;
                }
                spriteCounter = 0;
            }
        }
    }

    public boolean rangeCheck(Archer archer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rangeCheck'");
    }

    public boolean rangeCheck(int x, int y) {
        // TODO Auto-generated method stub
        int xRange = Math.abs(x-(this.x + image.getWidth()/2));
        int yRange = Math.abs(y-(this.y + image.getHeight()/2));

        return xRange <= 96 && yRange <= 96;
    }

    public void open(){
        open = true;
    }
    public void close(){
        open = false;
    }

    public void setSlot(){
        int i = 0, col = 0, row = 0;
        while(row < 4) {
            slotChest[i] = new Slot<Item>(col, row, 48, 48, 8, 8, 64);

            col++;
            i++;

            if(col >= 4){
                col = 0;
                row++;
            }
        }
    }

    public boolean addItem(Slot<Item> slot){
        for (int i = 0; i < 16; i++) {
            try {
                if(!slotChest[i].full && slotChest[i].type.equals(slot.type) && slotChest[i].items.size()+slot.items.size() <= slotChest[i].MAX){
                    slotChest[i].fill(slot);
                    return true;
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            
        }

        for (int i = 0; i < 16; i++) {
            if(slotChest[i].type == null){
                slotChest[i].fill(slot);
                return true;
            }else if(!slotChest[i].full && slotChest[i].type.equals(slot.type) && slotChest[i].items.size()+slot.items.size() <= slotChest[i].MAX){
                slotChest[i].fill(slot);
                return true;
            }
        }
        return false;
    }
}

