package com.obj;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.id.BlockType;
import com.id.ID;
import com.main.Game;

public abstract class Block extends GameObject {
    
    BlockType bt;
    public Game game;
    public int spriteNum = 1 ; // Counter Animation
	public int spriteCounter = 0;
    public BufferedImage[] idle = new BufferedImage[10];

    public boolean getHit = false;

    public Block(int x, int y, ID id, BlockType bt, Game game) {
        super(x, y, id);
        this.bt = bt;
        this.game = game;
        //TODO Auto-generated constructor stub
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle renderOrder();
    public abstract void getImage();
    public BlockType getBlockType(){
        return bt;
    }

    
    
}
