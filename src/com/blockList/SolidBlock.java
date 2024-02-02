package com.blockList;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.id.BlockType;
import com.id.ID;
import com.main.Game;
import com.obj.Block;

public class SolidBlock extends Block {

    public SolidBlock(int x, int y, ID id, BlockType bt, Game game, int type) {
        super(x, y, id, bt, game);
        getImage();
        image = game.base.layer1[type].image;
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
        g.drawImage(image, x, y, null);
    }

    @Override
    public Rectangle renderOrder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'renderOrder'");
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
    
}
