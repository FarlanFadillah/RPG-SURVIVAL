package com.obj;

import java.awt.image.BufferedImage;

import com.filehandler.SpriteSheet;
import com.id.ID;
import com.tile.ImageManager;

public abstract class Skill extends GameObject{
    public int requiredLevel;
    public String name;
    public int level, dmg, exp;
    public BufferedImage icon;
    public SpriteSheet ss;
    public ImageManager im = new ImageManager();
    public BufferedImage[] images = new BufferedImage[6];
    public int skillNum;
    public Skill(int x, int y, ID id) {
        super(x, y, id);
        ss = new SpriteSheet("/assets/Skills/Free_Skills.png");
    }

    public void getImages(){
        int col = 1;
        for (int i = 0; i < images.length; i++) {
            images[i] = im.scaledImage(ss.grabImage(col, 1, 32, 32, icon), 64, 64);
            col++;
        }
    }
    
}
