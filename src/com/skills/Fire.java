package com.skills;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.id.ID;
import com.obj.Skill;

public class Fire extends Skill{
    public Fire(int x, int y, ID id) {
        super(x, y, id);
        //TODO Auto-generated constructor stub
        name = "Fire";
        icon = ss.grabImageXY(0, 0, 32*6, 32);
        getImages();
    }




    //Not Used Function

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
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
    public void hit() {
        // TODO Auto-generated method stub
    }
    
}
