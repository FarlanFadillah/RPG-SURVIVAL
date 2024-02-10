package com.playerlist;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Entity;

public class Pawn extends Entity{

    public Pawn(int x, int y, ID id, EntityType et, EntityClass ec, Game game) {
        super(x, y, id, et, ec, game);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    @Override
    public void Collision() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Collision'");
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
        throw new UnsupportedOperationException("Unimplemented method 'hit'");
    }
    
}
