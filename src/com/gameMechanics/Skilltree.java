package com.gameMechanics;

import java.util.ArrayList;

import com.id.ID;
import com.obj.Skill;
import com.skills.Air;
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
        skillSlots[0] = new Slot<Skill>(1, 1, 64, 64, 0, 0, 64);
        skillSlots[1] = new Slot<Skill>(2, 1, 64, 64, 0, 0, 64);
        skillSlots[2] = new Slot<Skill>(3, 1, 64, 64, 0, 0, 64);
        skillSlots[3] = new Slot<Skill>(3, 2, 64, 64, 0, 0, 64);
        skillSlots[4] = new Slot<Skill>(3, 3, 64, 64, 0, 0, 64);
        skillSlots[5] = new Slot<Skill>(2, 4, 64, 64, 0, 0, 64);
        skillSlots[6] = new Slot<Skill>(4, 4, 64, 64, 0, 0, 64);
        skillSlots[7] = new Slot<Skill>(1, 4, 64, 64, 0, 0, 64);
        
        for (int i = 0; i < 6; i++) {
            skillSlots[i].lock = true;
            skillSlots[i].addItem(skill);
            skillSlots[i].type = "Skills";
            skillSlots[i].MAX = 1;
            skillSlots[i].icon = im.scaledImage(skill.images[i], 64, 64);
        }
    }
    
}
