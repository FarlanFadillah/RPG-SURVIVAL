package com.quadTree;


import com.obj.GameObject;

public class QuadNode {
    Point pos;
    GameObject gameObject;
 
    public QuadNode(Point _pos, GameObject gameObject) {
        pos = _pos;
        this.gameObject = gameObject;
    }
    public QuadNode() {
        gameObject = null;
    }

    public String toString(){
        return gameObject + "x: " + String.valueOf(gameObject.x) + ", y: " + String.valueOf(gameObject.y);
    }
}
