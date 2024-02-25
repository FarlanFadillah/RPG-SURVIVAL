package com.quadTree;

public class Point {
    public int x, y;
 
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
 
    public Point() {
        x = 0;
        y = 0;
    }

    public String toString(){
        return "x: " + String.valueOf(this.x) + ", y: " + String.valueOf(this.y);
    }
}
