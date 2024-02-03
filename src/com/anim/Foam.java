package com.anim;

import java.awt.image.BufferedImage;

import com.filehandler.SpriteSheet;
import com.tile.ImageManager;

public class Foam {
    ImageManager im = new ImageManager();
    public BufferedImage[] idle = new BufferedImage[8];
    SpriteSheet ss = new SpriteSheet("/assets/Water/Foam.png");
    public Foam() {
        getImage();
        //TODO Auto-generated constructor stub
    }

    public void getImage() {
        try {
            
            idle[0] = im.scaledImage(ss.grabImage(1, 1, 128, 128), 128, 128);
            idle[1] = im.scaledImage(ss.grabImage(2, 1, 128, 128), 128, 128);
            idle[2] = im.scaledImage(ss.grabImage(3, 1, 128, 128), 128, 128);
            idle[3] = im.scaledImage(ss.grabImage(4, 1, 128, 128), 128, 128);
            idle[4] = im.scaledImage(ss.grabImage(5, 1, 128, 128), 128, 128);
            idle[5] = im.scaledImage(ss.grabImage(6, 1, 128, 128), 128, 128);
            idle[6] = im.scaledImage(ss.grabImage(7, 1, 128, 128), 128, 128);
            idle[7] = im.scaledImage(ss.grabImage(8, 1, 128, 128), 128, 128);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
}
