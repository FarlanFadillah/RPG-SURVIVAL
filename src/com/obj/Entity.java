package com.obj;

public class Entity extends GameObject {

    public String name;
    public int hp, mana, xp, stamina;

    public Entity(int x, int y) {
        super(x, y);
    }
    @Override
    public void tick() {
    }
    @Override
    public void render() {
    }

    
}
