package com.blockList;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.filehandler.SpriteSheet;
import com.id.BlockType;
import com.id.ID;
import com.id.ItemType;
import com.item.Wood;
import com.main.Game;
import com.obj.Block;
import com.quadTree.Point;
import com.quadTree.QuadNode;
import com.tile.ImageManager;

public class Tree extends Block {
    ImageManager im = new ImageManager();
    public static SpriteSheet ss = new SpriteSheet("/assets/Block/Tree/Tree.png");
    BufferedImage[] shake = new BufferedImage[2];

    BufferedImage chopped;
    public boolean itemDroped = false;

    int spriteHitNum = 1;

    Random rand = new Random();

    public Tree(int x, int y, ID id, BlockType bt, Game game) {
        super(x, y, id, bt, game);
        //TODO Auto-generated constructor stub
        spriteNum = rand.nextInt(4)+1;
        getImage();
        hp = 100;
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
        if(getHit && hp > 0){
            if(spriteHitNum == 1) {
                image = shake[0];
            }
            if(spriteHitNum == 2) {
                image = shake[1];
            }
        }else if(hp <= 0){
            if(!itemDroped){
                    game.tryWorld.qt.insert(new QuadNode(new Point(x-32, y+96), new Wood(x-32, y+96, ID.Item, ItemType.ingredient)),game.tryWorld.entity, null);
                    game.tryWorld.qt.insert(new QuadNode(new Point(x+32, y+32), new Wood(x+32, y+32, ID.Item, ItemType.ingredient)),game.tryWorld.entity, null);
                    game.tryWorld.qt.insert(new QuadNode(new Point(x+64, y+96), new Wood(x+64, y+96, ID.Item, ItemType.ingredient)),game.tryWorld.entity, null);
                itemDroped = true;
            }
            image = chopped;
        }else if(hover){
            if(spriteNum == 1) {
                image = idle[4];
            }
            if(spriteNum == 2) {
                image = idle[5];
            }
            if(spriteNum == 3) {
                image = idle[6];
            }
            if(spriteNum == 4) {
                image = idle[7];
            }
        }else{
            if(spriteNum == 1) {
                image = idle[0];
            }
            if(spriteNum == 2) {
                image = idle[1];
            }
            if(spriteNum == 3) {
                image = idle[2];
            }
            if(spriteNum == 4) {
                image = idle[3];
            }
        }
    }

    @Override
    public void getImage() {
        try {
            
            idle[0] = im.scaledImage(ss.grabImage(1, 1, 192, 192), 192, 192);
            idle[1] = im.scaledImage(ss.grabImage(2, 1, 192, 192), 192, 192);
            idle[2] = im.scaledImage(ss.grabImage(3, 1, 192, 192), 192, 192);
            idle[3] = im.scaledImage(ss.grabImage(4, 1, 192, 192), 192, 192);


            idle[4] = im.scaledImage(ss.grabImage(1, 3, 192, 192), 192, 192);
            idle[5] = im.scaledImage(ss.grabImage(2, 3, 192, 192), 192, 192);
            idle[6] = im.scaledImage(ss.grabImage(3, 3, 192, 192), 192, 192);
            idle[7] = im.scaledImage(ss.grabImage(4, 3, 192, 192), 192, 192);

            chopped = ss.grabImage(3, 2, 192, 192);

            shake[0] = im.scaledImage(ss.grabImage(1, 2, 192, 192), 192, 192);
            shake[1] = im.scaledImage(ss.grabImage(2, 2, 192, 192), 192, 192);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    

    public void spriteCounter(){
        if (getHit) {
            spriteCounter++;
            if(spriteCounter > 10) {
                if(spriteHitNum == 1) {
                    spriteHitNum =2;
                }else if(spriteHitNum ==2) {
                    spriteHitNum =1;
                    hp -= damageReceived;
                    getHit = false;
                }
                spriteCounter = 0;
            }
        }else{
            spriteCounter++;
            if(spriteCounter > 10) {
                if(spriteNum == 1) {
                    spriteNum =2;
                }else if(spriteNum ==2) {
                    spriteNum =3;
                }else if(spriteNum ==3) {
                    spriteNum =4;
                }else if(spriteNum ==4) {
                    spriteNum =1;
                }
                
                spriteCounter =0;
            }
        }

    }

    @Override
    public Rectangle getSize() {
        // TODO Auto-generated method stub
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    @Override
    public void hit(int damage) {
        getHit = true;
        damageReceived = damage;
    }

    
    
}
