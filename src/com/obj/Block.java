package com.obj;
import java.awt.Rectangle;

import com.id.BlockType;
import com.id.ID;
import com.main.Game;

public abstract class Block extends GameObject {
    BlockType bt;
    public Game game;
    public Block(int x, int y, ID id, BlockType bt, Game game) {
        super(x, y, id);
        this.bt = bt;
        this.game = game;
        //TODO Auto-generated constructor stub
    }

    public abstract void tick();
    public abstract void render();
    public abstract Rectangle getBound();
    public abstract Rectangle renderOrder();

    
}
