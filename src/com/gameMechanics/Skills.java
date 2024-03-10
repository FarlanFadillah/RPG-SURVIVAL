package com.gameMechanics;

import com.main.Game;
import com.obj.Skill;

public class Skills {
    Game game;
    public Slot<Skill>[] skillSlots = new Slot[10];
    public Skills(Game game){
        this.game = game;
        setSlot();
    }

    public void setSlot(){
        int col = 0;
        for (int i = 0; i < skillSlots.length; i++) {
            skillSlots[i] = new Slot<Skill>(col, 0, 48, 48, 0, 0, 48);
            col++;
        }
    }
}
