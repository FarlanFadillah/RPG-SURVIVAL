package com.skills;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.id.ID;
import com.obj.Skill;

public class Air extends Skill{

    public Air(int x, int y, ID id) {
        super(x, y, id);
        name = "Air";
        icon = ss.grabImageXY(0, 32, 32*6, 32);
        getImages();
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tick'");
    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    @Override
    public Rectangle getBound() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBound'");
    }

    @Override
    public Rectangle renderOrder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'renderOrder'");
    }

    @Override
    public Rectangle getSize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSize'");
    }

    @Override
    public void hit(int damage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hit'");
    }
    
}
