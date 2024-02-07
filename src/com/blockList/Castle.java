package com.blockList;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.filehandler.SpriteSheet;
import com.id.BlockType;
import com.id.ID;
import com.main.Game;
import com.obj.Block;

public class Castle extends Block{
    protected SpriteSheet ss = new SpriteSheet("/assets/Block/Castle/Castle_Blue.png");
    public Castle(int x, int y, ID id, BlockType bt, Game game) {
        super(x, y, id, bt, game);
        image = ss.image;
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
        return new Rectangle(x+16, y+176, 288, 80);
    }

    @Override
    public void getImage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getImage'");
    }

    @Override
    public Rectangle getBound() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBound'");
    }

    @Override
    public Rectangle getSize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSize'");
    }

    @Override
    public void hit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hit'");
    }
    
}