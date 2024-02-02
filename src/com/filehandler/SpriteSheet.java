package com.filehandler;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class SpriteSheet {
    public BufferedImage image;

    public SpriteSheet(String path){
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public BufferedImage grabImage(int col, int row, int w, int h){
        BufferedImage img = image.getSubimage((col * w) - w, (row *h) - h, w, h);
        return img;
    }

    public BufferedImage grabImageXY(int x, int y, int w, int h){
        BufferedImage img = image.getSubimage(x, y, w, h);
        return img;
    }
}
