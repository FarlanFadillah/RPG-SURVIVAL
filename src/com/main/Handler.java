package com.main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.obj.GameObject;
import com.playerlist.Archer;
import com.playerlist.Caster;
import com.playerlist.Fighter;

public class Handler {

    public ArrayList<GameObject> objects = new ArrayList<>();
    public Archer archer;
    public Caster caster;
    public Fighter fighter;
    public Handler(Game game){
        // caster = new Caster(50, 50, ID.Entity, EntityType.PasifNPC, EntityClass.Caster, game);
        fighter = new Fighter(100, 100, ID.Entity, EntityType.Player, EntityClass.Fighter, game);
        archer = new Archer(100, 100, ID.Entity, EntityType.PasifNPC, EntityClass.Archer, game);
        objects.add(archer);
        // objects.add(caster);
        objects.add(fighter);
    }

    public void tick(){
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            temp.tick();
        }
    }

    public void render(Graphics g, double xx, double yy){
        try {
            Collections.sort(objects, new Comparator<GameObject>() {
    
                @Override
                public int compare(GameObject o1, GameObject o2) {
                    int y1 = (int) o1.renderOrder().getY();
                    int y2 = (int) o2.renderOrder().getY();
                    return Integer.compare(y1, y2);
                }
                
            });
        } catch (Exception e) {
            // TODO: handle exception
        }

        for (int i = 0; i < objects.size(); i++) {
            int x1 = objects.get(i).getX();
            int y1 = objects.get(i).getY();
                if( x1 < xx+ Game.WIDTH && x1 > xx - objects.get(i).image.getWidth() && y1 <yy+Game.HEIGHT && y1 > yy - objects.get(i).image.getHeight()){
                    objects.get(i).render(g);           
                }
        }
    }
}
