package com.obj;

import java.awt.Rectangle;
import com.id.ID;

public abstract class Entity extends GameObject {

    public String name;
    public int hp, mana, xp, stamina;

    public Entity(int x, int y, ID id) {
        super(x, y, id);
    }

    public abstract void tick();
    public abstract void render();
    public abstract Rectangle getBound();
    public abstract Rectangle renderOrder();
    public abstract void Collision();

    
}
