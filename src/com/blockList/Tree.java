package com.blockList;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.filehandler.SpriteSheet;
import com.id.BlockType;
import com.id.ID;
import com.main.Game;
import com.obj.Block;
import com.tile.ImageManager;

public class Tree extends Block {
    ImageManager im = new ImageManager();

    SpriteSheet ss = new SpriteSheet("/assets/Block/Tree/Tree.png");
    public Tree(int x, int y, ID id, BlockType bt, Game game) {
        super(x, y, id, bt, game);
        //TODO Auto-generated constructor stub
        getImage();
        image = idle[0];
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        spriteCounter();
    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        animatedSprite();
        g.drawImage(image, x, y, null);
    }

    @Override
    public Rectangle getBound() {
        // TODO Auto-generated method stub
        return new Rectangle(x+80, y+144, 32, 32);
    }

    @Override
    public Rectangle renderOrder() {
        // TODO Auto-generated method stub
        return new Rectangle(x+80, y+144, 32, 32);
    }

    public void animatedSprite(){
        if(spriteNum == 1) {
            image = idle[0];
        }
        if(spriteNum == 2) {
            image = idle[1];
        }
        if(spriteNum == 3) {
            image = idle[2];
        }
    }

    @Override
    public void getImage() {
        try {
            
            idle[0] = im.scaledImage(ss.grabImage(2, 1, 192, 192), 192, 192);
            idle[1] = im.scaledImage(ss.grabImage(3, 1, 192, 192), 192, 192);
            idle[2] = im.scaledImage(ss.grabImage(4, 1, 192, 192), 192, 192);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void spriteCounter(){

        spriteCounter++;
        if(spriteCounter > 10) {
            if(spriteNum == 1) {
                spriteNum =2;
            }else if(spriteNum ==2) {
                spriteNum =3;
            }else if(spriteNum ==3) {
                spriteNum =1;
            }
            
            spriteCounter =0;
        }
        
    }

    @Override
    public Rectangle getSize() {
        // TODO Auto-generated method stub
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    
    
}