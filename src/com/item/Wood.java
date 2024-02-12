package com.item;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.filehandler.SpriteSheet;
import com.id.ID;
import com.id.ItemType;
import com.obj.Item;
import com.tile.ImageManager;

public class Wood extends Item{
    public int size = 128;
    protected SpriteSheet ss = new SpriteSheet("/assets/items/wood/W_Spawn.png");
    protected SpriteSheet ss2 = new SpriteSheet("/assets/items/wood/W_Idle.png");
    ImageManager im = new ImageManager();
    public Wood(int x, int y, ID id, ItemType it) {
        super(x, y, id, it);
        //TODO Auto-generated constructor stub\
        icon = ss2.grabImageXY(32, 48, 64, 64);
        name = "Wood";
        image = ss2.image;
        getImage();
    }

    @Override
    public void spawn() {
        // TODO Auto-generated method stub
        if(spawn == false){
            if(spriteNum == 1) {
                image = spawnImage[0];
            }
            if(spriteNum == 2) {
                image = spawnImage[1];
            }
            if(spriteNum == 3) {
                image = spawnImage[2];
            }
            if(spriteNum == 4) {
                image = spawnImage[3];
            }
            if(spriteNum == 5) {
                image = spawnImage[4];
            }
            if(spriteNum == 6) {
                image = spawnImage[5];
            }
            if(spriteNum == 7) {
                image = spawnImage[6];
                spawn = true;
            }
        }else{
            image = ss2.image;
        }
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        spriteCounter();
    }
    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        if(!spawn){
            spawn();
            g.drawImage(image, x, y, null);
        }else{
            g.drawImage(image, x, y, null);
        }
    }

    @Override
    public Rectangle getBound() {
        // TODO Auto-generated method stub
        return new Rectangle(x+16, y+64,96, 48);
    }

    @Override
    public Rectangle renderOrder() {
        // TODO Auto-generated method stub
        return new Rectangle(x+16, y+64,96, 48);
    }

    @Override
    public Rectangle getSize() {
        // TODO Auto-generated method stub
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    @Override
    public void hit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hit'");
    }

    public void getImage(){
        try {
            
            spawnImage[0] = im.scaledImage(ss.grabImage(1, 1, size, size), size, size);
            spawnImage[1] = im.scaledImage(ss.grabImage(2, 1, size, size), size, size);
            spawnImage[2] = im.scaledImage(ss.grabImage(3, 1, size, size), size, size);
            spawnImage[3] = im.scaledImage(ss.grabImage(4, 1, size, size), size, size);
            spawnImage[4] = im.scaledImage(ss.grabImage(5, 1, size, size), size, size);
            spawnImage[5] = im.scaledImage(ss.grabImage(6, 1, size, size), size, size);
            spawnImage[6] = im.scaledImage(ss.grabImage(7, 1, size, size), size, size);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    
}
