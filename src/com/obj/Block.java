package com.obj;
import java.awt.Rectangle;

import com.id.BlockType;
import com.id.ID;

public abstract class Block extends GameObject {
    BlockType bt;
    public Block(int x, int y, ID id, BlockType bt) {
        super(x, y, id);
        this.bt = bt;
        //TODO Auto-generated constructor stub
    }

    public abstract void tick();
    public abstract void render();
    public abstract Rectangle getBound();
    public abstract Rectangle renderOrder();

    
}
