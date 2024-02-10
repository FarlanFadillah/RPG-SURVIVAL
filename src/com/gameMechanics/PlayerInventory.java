package com.gameMechanics;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import com.item.Money;
import com.item.Wood;
import com.main.Game;
import com.obj.Item;

public class PlayerInventory {
    Game game;
    public Slot[] itemSlot = new Slot[36];
    int col, row = 0;
    public PlayerInventory(Game game){
        this.game = game;
        setSlot();
    }
    public void addItem(Item item){
        for (int i = 0; i < itemSlot.length; i++) {
            if(itemSlot[i].type == null){
                itemSlot[i].addItem(item);
                break;
            }else if(!itemSlot[i].full && itemSlot[i].type.equals(item.name)){
                itemSlot[i].addItem(item);
                break;
            }

        }
    }

    public void setSlot(){
        int i = 0, col = 0, row = 0;
        while (row < 6) {

            itemSlot[i] = new Slot(col, row);
            col++;
            i++;
            if(col >= 6){
                row++;
                col = 0;
            }

        }
        
    }

    public void drag(){
        
    }
}
