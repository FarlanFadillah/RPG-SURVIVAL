package com.gameMechanics;

import com.filehandler.SpriteSheet;
import com.main.Game;
import com.obj.Item;

public class PlayerEquipment {
    public Slot<Item> headArmorSlot;
    public Slot<Item> bodyArmorSlot;
    public Slot<Item> mainWeaponSlot;
    public Slot<Item> secondWeaponSlot;
    public Slot<Item> accessories1Slot;
    public Slot<Item> accessories2Slot;
    @SuppressWarnings("unchecked")
    public Slot<Item>[] slots = new Slot[6];
    Game game;
    SpriteSheet ss = new SpriteSheet("/assets/items/money/G_Idle.png");

    int xx = 567, yy = 8;
    public PlayerEquipment(Game game){
        this.game = game;
        setSlot();
    }
    
    public void setSlot(){
        headArmorSlot = new Slot<>(96, 16, 64, 64);
        headArmorSlot.MAX = 1;
        headArmorSlot.equipmentType = "helmet";
        headArmorSlot.equipment = true;

        bodyArmorSlot = new Slot<>(96, 192, 64, 64);
        bodyArmorSlot.MAX = 1;
        bodyArmorSlot.equipmentType = "armor";
        bodyArmorSlot.equipment = true;

        mainWeaponSlot = new Slot<>(16, 112, 64, 64);
        mainWeaponSlot.MAX = 1;
        mainWeaponSlot.equipmentType = "weapon";
        mainWeaponSlot.equipment = true;

        secondWeaponSlot = new Slot<>(176, 112, 64, 64);
        secondWeaponSlot.MAX = 1;
        secondWeaponSlot.equipmentType = "weapon";
        secondWeaponSlot.equipment = true;

        accessories1Slot = new Slot<>(32, 256, 64, 64);
        accessories1Slot.MAX = 1;
        accessories1Slot.equipmentType = "accessories";
        accessories1Slot.equipment = true;
        
        accessories2Slot = new Slot<>(160, 256, 64, 64);
        accessories2Slot.MAX = 1;
        accessories2Slot.equipmentType = "accessories";
        accessories2Slot.equipment = true;

        slots[0] = headArmorSlot;
        slots[1] = bodyArmorSlot;
        slots[2] = mainWeaponSlot;
        slots[3] = secondWeaponSlot;
        slots[4] = accessories1Slot;
        slots[5] = accessories2Slot;
    } 
}
