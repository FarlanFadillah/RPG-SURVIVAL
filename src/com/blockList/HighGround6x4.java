package com.blockList;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.filehandler.SpriteSheet;
import com.id.BlockType;
import com.id.ID;
import com.main.Game;
import com.obj.Block;

public class HighGround6x4 extends Block{
    SpriteSheet ss = new SpriteSheet("/assets/Block/solidBlock/highGround6x4.png");
    public HighGround6x4(int x, int y, ID id, BlockType bt, Game game) {
        super(x, y, id, bt, game);
        image = ss.image;
        this.highGround = true;
        //TODO Auto-generated constructor stub
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
    public Rectangle renderOrder() {
        // TODO Auto-generated method stub
        // return new Rectangle(x, y, image.getWidth(), image.getHeight());
        return new Rectangle();
    }

    @Override
    public void getImage() {
        // TODO Auto-generated method stub
    }

    @Override
    public Rectangle getBound() {
        // TODO Auto-generated method stub
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    @Override
    public Rectangle getSize() {
        // TODO Auto-generated method stub
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    @Override
    public void hit(int damage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hit'");
    }
    
}
