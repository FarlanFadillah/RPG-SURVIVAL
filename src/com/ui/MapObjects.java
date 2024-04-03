package com.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MapObjects {
    public BufferedImage image;
    public int x, y;
    public String type = "inAnimated";
    public MapObjects(BufferedImage image, int x, int y){
        this.image = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics g = this.image.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        this.x = x;
        this.y = y;
    }
}
