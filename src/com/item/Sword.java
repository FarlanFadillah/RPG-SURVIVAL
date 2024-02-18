package com.item;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.filehandler.SpriteSheet;
import com.id.ID;
import com.id.ItemType;
import com.obj.Item;

public class Sword extends Item{
    SpriteSheet ss = new SpriteSheet("/assets/items/sword/Ugly_Motherfucker_sword.png");
    public Sword(int x, int y, ID id, ItemType it) {
        super(x, y, id, it);
        equipmentType = "weapon";
        name = "sword";
        image = ss.image;
        icon = ss.image;
        //TODO Auto-generated constructor stub
    }

    @Override
    public void spawn() {
        // TODO Auto-generated method stub
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        g.drawImage(image, x, y, null);
    }

    @Override
    public Rectangle getBound() {
        // TODO Auto-generated method stub
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    @Override
    public Rectangle renderOrder() {
        // TODO Auto-generated method stub
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
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
    
}
