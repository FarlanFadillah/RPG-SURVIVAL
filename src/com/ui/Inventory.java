package com.ui;

import java.awt.Graphics2D;

import com.filehandler.SpriteSheet;

public class Inventory {
    SpriteSheet inventoryBox = new SpriteSheet("/assets/GUI/Banners/Carved_36Slides.png");

    public void drawInventory(Graphics2D g2d, int x, int y){
        g2d.drawImage(inventoryBox.image, x, y, null);
    }
}
