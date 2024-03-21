package com.gameMechanics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.obj.Item;
import com.obj.Skill;
import com.tile.ImageManager;

public class Slot<E> implements Cloneable{
    ImageManager im = new ImageManager();
    public int MAX = 32;
    public boolean full = false;

    public BufferedImage icon;

    public String type;
    public String equipmentType = "";


    public PlayerInventory playerInventory;
    public int col, row;
    public int x, y, width, height;
    public ArrayList<E> items = new ArrayList<>();
    public Rectangle rectangle;
    public boolean equipment = false;
    int sizeSlot;
    int offW;
    int offH;
    public boolean lock = false;
    public boolean select = false;
    public Skill skill;
    public Slot(int col, int row, int width, int height, int offW, int offH, int sizeSlot){
        this.col = col;
        this.row = row;
        this.width = width;
        this.height = height;
        this.x = (col*sizeSlot)+offW;
        this.y = (row*sizeSlot)+offH;
        this.sizeSlot = sizeSlot;
        this.offW = offW;
        this.offH = offH;
        rectangle = new Rectangle(x, y, width, height);
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
        return new Rectangle(this.x+x, this.y+y, width, height);
    }
    public void addItem(E item){
        if(item.getClass().getSuperclass().equals(Item.class)){
            try {
                Item temp = (Item) item;
                if(temp.equipmentType.equals(null)) this.MAX = 1; full = true;
            } catch (Exception e) {
                // TODO: handle exception
            }
            Item temp = (Item) item;
            if(items.size() == 0){
                type = temp.name;
                items.add(item);
                icon = temp.icon;
            }else if(items.size()>0 && items.size() < MAX && full == false){
                items.add(item);
    
            }else{
                this.full = true;
            }
    
            if(items.size() == MAX){
                full = true;
            }
            return;
        }else if(item.getClass().getSuperclass() == Skill.class){
            if(!full){
                Skill temp = (Skill) item;
                lock = true;
                type = "Skills";
                MAX = 1;
                full = true;
                icon = temp.icon;
                skill = temp;
            }
            return;
        }
    }
    public void removeItem(E item) {
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
    public Slot<E> Copy() {
    	Slot<E> slot = new Slot<E>(col, row, width, height, offW,offH,sizeSlot);
    	for(int i = 0; i < items.size(); i++) {
    		slot.items.add(items.get(i));
    	}
        slot.type = this.type;
    	slot.icon = this.icon;
    	return slot;
    }

    public Slot<Skill> CopySkillSlot(){
        Slot<Skill> slot = new Slot<Skill>(col, row, width, height, offW,offH,sizeSlot);
        slot.skill = skill;
        slot.type = type;
        slot.icon = icon;

        // System.out.println(slot.skill + " " + slot.type + " " + slot.icon.getWidth());
        return slot ;
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
        skill = null;
        this.MAX = MAX;
        this.full = false;
        this.type = null;
        this.icon = null;
    }

    public void fill(Slot<E> slotDragged) {
        // TODO Auto-generated method stub
        for (int j = 0; j < slotDragged.items.size(); j++) {
            addItem(slotDragged.items.get(j));
        }
        type = slotDragged.type;
        icon = slotDragged.icon;
        full = slotDragged.full;
    }
    public void fillSkill(Slot<Skill> slotDragged) {
        // TODO Auto-generated method stub
        skill = slotDragged.skill;
        type = slotDragged.type;
        icon = im.scaledImage(slotDragged.icon, 48, 48);
        full = slotDragged.full;
    }
    public void fillEquipment(Slot<E> slotDragged) {
        // TODO Auto-generated method stub
        for (int j = 0; j < slotDragged.items.size(); j++) {
            addItem(slotDragged.items.get(j));
        }
        icon = slotDragged.icon;
        full = slotDragged.full;
    }

	public void fillUntilFull(Slot<E> slotDragged, int i) {
		// TODO Auto-generated method stub
		ArrayList<E> fillItem = new ArrayList<>();
		for (int j = 0; j < i; j++) {
			fillItem.add(slotDragged.items.get(j));
		}
		for (int j = 0; j < fillItem.size(); j++) {
			items.add(fillItem.get(j));
			slotDragged.removeItem(fillItem.get(j));
		}
	}

	public Slot<E> splitCopy() {
		// TODO Auto-generated method stub
		Slot<E> slot = new Slot<>(col, row, width,height, offW,offH, sizeSlot);
    	for(int i = 0; i < items.size()/2; i++) {
    		slot.items.add(items.get(i));
    	}
    	items.removeAll(slot.items);
    	slot.type = this.type;
    	slot.icon = this.icon;
    	return slot;
	} 


}
