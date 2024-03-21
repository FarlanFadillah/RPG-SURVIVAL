package com.gameMechanics;

import com.id.ID;
import com.main.Game;
import com.obj.Skill;
import com.skills.Fire;
import com.tile.ImageManager;

public class Skills {
    Game game;
    @SuppressWarnings("unchecked")
    public Slot<Skill>[] skillSlots = new Slot[10];
    Fire fire = new Fire(0, 0, ID.Skill);
    ImageManager im = new ImageManager();
    public Skills(Game game){
        this.game = game;
        setSlot();
    }

    public void setSlot(){
        int col = 0;
        for (int i = 0; i < skillSlots.length; i++) {
            skillSlots[i] = new Slot<Skill>(col, 0, 48, 48, 0, 0, 48);
            skillSlots[i].MAX = 1;
            col++;
        }
    }
}
