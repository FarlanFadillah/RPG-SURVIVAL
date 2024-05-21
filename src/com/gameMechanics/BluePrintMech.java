package com.gameMechanics;


import com.id.ID;
import com.obj.BluePrint;

public class BluePrintMech {


    @SuppressWarnings("unchecked")
    public Slot<BluePrint>[] blueprints = new Slot[32];

    public BluePrintMech(){
        setSlot();
        setBlueprint();
    }


    public void setSlot(){
        int col = 0, row = 0;
        for (int i = 0; i < blueprints.length; i++) {
            blueprints[i] = new Slot<>(col, row, 96, 96, 10, 11, 96);
            col++;

            if(col == 8){
                col = 0;
                row++;
            }
        }
    }

    public void setBlueprint(){
        blueprints[0].addItem(new BluePrint(0, 0, ID.Blueprint, "BlueHouse", 1,1));
        blueprints[0].bluePrintStored.ingredients.put("Wood", 2);
        blueprints[0].bluePrintStored.ingredients.put("Money", 2);

        blueprints[1].addItem(new BluePrint(0, 0, ID.Blueprint, "Castle", 2,1));
        blueprints[2].addItem(new BluePrint(0, 0, ID.Blueprint, "Tower", 3,1));
    }
    
}
