package com.obj;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.id.*;

public abstract class GameObject {
    public BufferedImage image;
    public int x, y;
    ID id;
    public double hp;
	public boolean highGround = false;
    public String name;
    public boolean hover;
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBound();
    public abstract Rectangle renderOrder();
    public abstract Rectangle getSize();
    public abstract void hit();
    
    public int start, stop = 0;

    public GameObject(int x, int y, ID id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    public ID getID(){
        return id;
    }

    
}
