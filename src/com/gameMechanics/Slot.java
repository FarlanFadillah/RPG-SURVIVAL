package com.gameMechanics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.obj.Item;

public class Slot implements Cloneable{
    public int total = 0;
    public int max = 32;
    public boolean full = false;

    public BufferedImage icon;

    public String type;


    public PlayerInventory playerInventory;
    public int col, row;
    public ArrayList<Item> items = new ArrayList<>();
    public Slot(int col, int row){
        this.col = col;
        this.row = row;
    }

    public Rectangle getBound(){
        return new Rectangle(col*64, (row*64)+8, 64, 64);
    }
    public void addItem(Item item){
        if(total == 0){
            type = item.name;
            items.add(item);
            icon = item.icon;
            total++;
        }else if(total < max ){
            items.add(item);
            total++;

        }else{
            this.full = true;
        }

        if(total == max){
            full = true;
        }
    }
    public void moveSlot(int col, int row){
        this.col = col;
        this.row = row;
    }

    public void drag() {
        // TODO Auto-generated method stub
        System.out.println(this.col +", "+ this.row);
    }

    public Object clone() throws CloneNotSupportedException 
    { 
        return super.clone(); 
    }

    public void emptySlot() {
        for (int i = 0; i < items.size(); i++) {
            items.remove(i);
        }
        this.total =0;
        this.type = null;
        this.icon = null;
    }

    public void fill(Slot slotDragged) {
        // TODO Auto-generated method stub
        for (int j = 0; j < slotDragged.items.size(); j++) {
            addItem(slotDragged.items.get(j));
        }
        type = slotDragged.type;
        total += slotDragged.total;
        icon = slotDragged.icon;
    } 


}
