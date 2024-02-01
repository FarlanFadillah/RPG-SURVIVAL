package com.obj;

import java.awt.*;

import com.id.*;

public abstract class GameObject {
    public int x, y;
    ID id;
    public abstract void tick();
    public abstract void render(Graphics g);

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
