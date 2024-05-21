package com.obj;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.filehandler.SpriteSheet;
import com.id.ID;

public class BluePrint extends GameObject{
    public BufferedImage bpicon;
    public SpriteSheet itemImage = new SpriteSheet("/assets/blueprints/BluePrintItem.png");
    public SpriteSheet icon;
    public BufferedImage buildingImage;

    public HashMap<String, Integer> ingredients = new HashMap<>();

    public BluePrint(int x, int y, ID id, String name, int col, int row) {
        super(x, y, id);
        icon = new SpriteSheet("/assets/blueprints/BluePrintIcons.png");
        bpicon = icon.grabImage(col, row, 74, 74);
        this.name = name;
        //TODO Auto-generated constructor stub
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tick'");
    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        g.drawImage(icon.image, x, y, null);
    }

    @Override
    public Rectangle getBound() {
        // TODO Auto-generated method stub
        return new Rectangle(x, y, icon.image.getWidth(), icon.image.getHeight());
    }

    @Override
    public Rectangle renderOrder() {
        // TODO Auto-generated method stub
        return new Rectangle(x, y, icon.image.getWidth(), icon.image.getHeight());
    }

    @Override
    public Rectangle getSize() {
        // TODO Auto-generated method stub
        return new Rectangle(x, y, icon.image.getWidth(), icon.image.getHeight());
    }

    @Override
    public void hit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hit'");
    }
    
    
}
