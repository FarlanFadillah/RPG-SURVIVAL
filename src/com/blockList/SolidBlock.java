package com.blockList;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.id.BlockType;
import com.id.ID;
import com.main.Game;
import com.obj.Block;

public class SolidBlock extends Block {

    public SolidBlock(int x, int y, ID id, BlockType bt, Game game, BufferedImage image) {
        super(x, y, id, bt, game);
        this.image = image;
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
        return new Rectangle();
    }

    @Override
    public void getImage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getImage'");
    }

    @Override
    public Rectangle getBound() {
        // TODO Auto-generated method stub
        return new Rectangle();
    }
    
}
