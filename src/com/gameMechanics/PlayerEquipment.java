package com.gameMechanics;

import com.obj.Item;

public class PlayerEquipment {
    public Slot headArmorSlot;
    public Slot bodyArmorSlot;
    public Slot mainWeaponSlot;
    public Slot seconWeaponSlot;
    public Slot accessories1Slot;
    public Slot accessories2Slot;
    int x, y;
    public PlayerEquipment(int x, int y){
        this.x = x;
        this.y = y;
        setSlot();
    }
    public void addEquipment(Item item){
        
    }

    public void setSlot(){
        headArmorSlot = new Slot(x+96, y+16, 64, 64);
        bodyArmorSlot = new Slot(x+96, y+192, 64, 64);
        mainWeaponSlot = new Slot(x+16, y+112, 64, 64);
        seconWeaponSlot = new Slot(x+176, y+112, 64, 64);
        accessories1Slot = new Slot(x+32, y+256, 64, 64);
        accessories2Slot = new Slot(x+160, y+256, 64, 64);
    }
    
}
