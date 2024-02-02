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
        caster = new Caster(50, 50, ID.Entity, EntityType.PasifNPC, EntityClass.Caster, game);
        fighter = new Fighter(100, 100, ID.Entity, EntityType.Player, EntityClass.Fighter, game);
        archer = new Archer(10, 10, ID.Entity, EntityType.Player, EntityClass.Archer, game);
        objects.add(caster);
        objects.add(fighter);
        objects.add(archer);
    }

    public void tick(){
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            temp.tick();
        }
    }

    public void render(Graphics g){
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            temp.render(g);
        }
    }
}
