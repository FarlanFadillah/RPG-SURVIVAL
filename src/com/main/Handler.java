package com.main;

import java.awt.Graphics;
import java.util.ArrayList;

import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.obj.GameObject;
import com.playerlist.Archer;
import com.playerlist.Caster;
import com.playerlist.Fighter;

public class Handler {

    public ArrayList<GameObject> objects = new ArrayList<>();
    Archer archer;
    Caster caster;
    Fighter fighter;
    public Handler(Game game){
        // caster = new Caster(50, 50, ID.Entity, EntityType.PasifNPC, EntityClass.Caster, game);
        // fighter = new Fighter(100, 100, ID.Entity, EntityType.Player, EntityClass.Fighter, game);
        archer = new Archer(100, 100, ID.Entity, EntityType.Player, EntityClass.Archer, game);
        objects.add(archer);
        // objects.add(caster);
        // objects.add(fighter);
    }

    public void tick(){
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            temp.tick();
        }
    }

    public void render(Graphics g, double xx, double yy){
        for (int i = 0; i < objects.size(); i++) {
            int x1 = objects.get(i).getX();
            int y1 = objects.get(i).getY();
                if( x1 < xx+ Game.WIDTH*2 && x1 > xx - objects.get(i).image.getWidth() && y1 <yy+Game.HEIGHT*2 && y1 > yy - objects.get(i).image.getHeight()){
                    objects.get(i).render(g);           
                }
        }
    }
}
