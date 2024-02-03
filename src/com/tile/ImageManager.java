package com.tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageManager {
    public BufferedImage scaledImage(BufferedImage image, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(image, 0, 0, width, height, null);
        return scaledImage;
    }
}
