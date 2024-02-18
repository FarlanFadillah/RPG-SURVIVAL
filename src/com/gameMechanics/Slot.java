package com.gameMechanics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.obj.Item;

public class Slot implements Cloneable{
    public int MAX = 32;
    public boolean full = false;

    public BufferedImage icon;

    public String type;
    public String equipmentType = "";


    public PlayerInventory playerInventory;
    public int col, row;
    public int x, y, width, height;
    public ArrayList<Item> items = new ArrayList<>();
    public Rectangle rectangle;
    public boolean equipment = false;
    public Slot(int col, int row){
        this.col = col;
        this.row = row;
        rectangle = new Rectangle(col*64, (row*64)+8, 64, 64);
    }
    public Slot(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rectangle = new Rectangle(x, y, width, height);
    }

    public Rectangle getBound(){
        return rectangle;
    }
    public Rectangle getBound(int x, int y){
        return new Rectangle(this.x+x, this.y+y, 64, 64);
    }
    public void addItem(Item item){
        try {
            if(item.equipmentType.equals(null)) this.MAX = 1; full = true;
        } catch (Exception e) {
            // TODO: handle exception
        }
        if(items.size() == 0){
            type = item.name;
            items.add(item);
            icon = item.icon;
        }else if(items.size()>0 && items.size() < MAX && full == false){
            items.add(item);

        }else{
            this.full = true;
        }

        if(items.size() == MAX){
            full = true;
        }
    }
    public void removeItem(Item item) {
    	items.remove(item);
    	if(items.size() < MAX) {
    		full = false;
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
    public Slot Copy() {
    	Slot slot = new Slot(col, row);
    	for(int i = 0; i < items.size(); i++) {
    		slot.items.add(items.get(i));
    	}
    	slot.type = this.type;
    	slot.icon = this.icon;
    	return slot;
    }

    public void emptySlot() {
        items.removeAll(items);
        this.MAX = 32;
        this.full = false;
        this.type = null;
        this.icon = null;
    }
    public void emptySlot(int MAX) {
        items.removeAll(items);
        this.MAX = MAX;
        this.full = true;
        this.type = null;
        this.icon = null;
    }

    public void fill(Slot slotDragged) {
        // TODO Auto-generated method stub
        for (int j = 0; j < slotDragged.items.size(); j++) {
            addItem(slotDragged.items.get(j));
        }
        type = slotDragged.type;
        icon = slotDragged.icon;
        full = slotDragged.full;
    }
    public void fillEquipment(Slot slotDragged) {
        // TODO Auto-generated method stub
        for (int j = 0; j < slotDragged.items.size(); j++) {
            addItem(slotDragged.items.get(j));
        }
        icon = slotDragged.icon;
        full = slotDragged.full;
    }

	public void fillUntilFull(Slot slotDragged, int i) {
		// TODO Auto-generated method stub
		ArrayList<Item> fillItem = new ArrayList<>();
		for (int j = 0; j < i; j++) {
			fillItem.add(slotDragged.items.get(j));
		}
		for (int j = 0; j < fillItem.size(); j++) {
			items.add(fillItem.get(j));
			slotDragged.removeItem(fillItem.get(j));
		}
	}

	public Slot splitCopy() {
		// TODO Auto-generated method stub
		Slot slot = new Slot(col, row);
    	for(int i = 0; i < items.size()/2; i++) {
    		slot.items.add(items.get(i));
    	}
    	items.removeAll(slot.items);
    	slot.type = this.type;
    	slot.icon = this.icon;
    	return slot;
	} 


}
