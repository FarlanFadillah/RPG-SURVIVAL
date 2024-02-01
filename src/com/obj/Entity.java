package com.obj;

import java.awt.*;
import java.awt.Rectangle;

import com.id.EntityType;
import com.id.ID;
import com.main.Game;

public abstract class Entity extends GameObject {
    public int speed;
    public boolean up = false, down = false, right= false, left = false;
    public String name;
    public int hp, mana, xp, stamina;
    public float velX=0, velY=0;
    public EntityType et;
    public Game game;
    public Entity(int x, int y, ID id, EntityType et, Game game) {
        super(x, y, id);
        this.et = et;
        this.game = game;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBound();
    public abstract Rectangle renderOrder();
    public abstract void Collision();

    public void playerControl(){
		if(isUp()) {
			velY=-speed;
			// arah = "atas"; 
		}
		else velY=0;
		
		if(isDown()) {
			velY=speed;
			// arah = "bawah"; 
		}
		else if(!isUp())velY=0;
		
		if(isRight()) {
			velX=speed;
			// arah = "kanan";
		}
		else if(!isLeft())velX=0;
		
		if(isLeft()) {
			velX=-speed;
			// arah = "kiri";
		}
		else if(!isRight()) velX=0;
	}

    public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}

    public EntityType getEntityType(){
        return et;
    }
    
}
