package com.blockList;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.filehandler.SpriteSheet;
import com.id.BlockType;
import com.id.ID;
import com.main.Game;
import com.obj.Block;

public class SolidBlock extends Block {
    SpriteSheet ss = new SpriteSheet("/assets/Block/solidBlock/transparent.png");
    public int w, h;
    public SolidBlock(int x, int y, ID id, BlockType bt, Game game, int w, int h) {
        super(x, y, id, bt, game);
        image = ss.image;
        this.w = w;
        this.h = h;
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
        return new Rectangle(x, y, w, h);
    }

    @Override
    public void getImage() {
        // TODO Auto-generated method stub
    }

    @Override
    public Rectangle getBound() {
        // TODO Auto-generated method stub
        return new Rectangle(x, y, w, h);
    }

    @Override
    public Rectangle getSize() {
        // TODO Auto-generated method stub
        return new Rectangle(x, y, w, h);
    }

    @Override
    public void hit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hit'");
    }
    
}
