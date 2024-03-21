package com.gameMechanics;

import java.util.ArrayList;

import com.id.ID;
import com.obj.Skill;
import com.skills.Fire;
import com.tile.ImageManager;

public class Skilltree {

    public ArrayList<Skill> skills = new ArrayList<>();
    @SuppressWarnings("unchecked")
    public Slot<Skill>[] skillSlots = new Slot[8];
    Fire fire = new Fire(0, 0, ID.Skill);
    ImageManager im = new ImageManager();
    public Skilltree(){
    }

    public void setSlot(Skill skill){
        skillSlots[0] = new Slot<Skill>(0, 0, 64, 64, 18, 32, 64);
        skillSlots[1] = new Slot<Skill>(2, 0, 64, 64, 18, 32, 64);
        skillSlots[2] = new Slot<Skill>(4, 0, 64, 64, 18, 32, 64);
        skillSlots[3] = new Slot<Skill>(0, 2, 64, 64, 18, 32, 64);
        skillSlots[4] = new Slot<Skill>(2, 2, 64, 64, 18, 32, 64);
        skillSlots[5] = new Slot<Skill>(4, 2, 64, 64, 18, 32, 64);
        skillSlots[6] = new Slot<Skill>(0, 4, 64, 64, 18, 32, 64);
        skillSlots[7] = new Slot<Skill>(2, 4, 64, 64, 18, 32, 64);
        
        for (int i = 0; i < 6; i++) {
            skill.skillNum = i;
            skill.icon = im.scaledImage(skill.images[i], 64, 64);
            if(skill.getClass().equals(Fire.class)){
                Fire fire = new Fire(0, 0, ID.Skill);
                fire.requiredLevel = i+1;
                fire.icon = im.scaledImage(skill.images[i], 64, 64);
                skillSlots[i].addItem(fire);
            }
        }
        skillSlots[0].lock = false;
    }
    
}
