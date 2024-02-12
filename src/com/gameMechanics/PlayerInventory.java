package com.gameMechanics;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.main.Game;
import com.obj.Item;

public class PlayerInventory {
    public int draggedSlotNum;
	Game game;
    public Slot[] itemSlot = new Slot[36];
    int col, row = 0;
    public boolean dragged = false;
    public Slot slotDragged;
    public BufferedImage dragItem;
    public PlayerInventory(Game game){
        this.game = game;
        setSlot();
    }
    public void addItem(Item item){
    	for (int i = 0; i < itemSlot.length; i++) {
    		try {
    			if(!itemSlot[i].full && itemSlot[i].type.equals(item.name)){
    				itemSlot[i].addItem(item);
    				return;
    			}
			} catch (Exception e) {
}

        }
    	
        for (int i = 0; i < itemSlot.length; i++) {
            if(itemSlot[i].type == null){
                itemSlot[i].addItem(item);
                return;
            }else if(!itemSlot[i].full && itemSlot[i].type.equals(item.name)){
                itemSlot[i].addItem(item);
                return;
            }
        }
    }
    //initialization the slot, total 36 slot.
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

	public void dragItem(MouseEvent e) {
		for (int i = 0; i < game.gui.inv.slot.length; i++) {
			Slot temp = game.gui.inv.slot[i];
			//Check collision on each slot
			if(temp.getBound().contains(e.getPoint())) {
				//drag item from slots
				if(temp.items.size() > 0 && dragged == false && game.key.holdCtrl == false){
					slotDragged = temp.Copy();
					temp.emptySlot();
					dragged = true;
					dragItem = slotDragged.icon;
					draggedSlotNum = slotDragged.items.size();
					return;
				//split and drag item from slot
				}else if(temp.items.size() > 0 && dragged == false && game.key.holdCtrl == true && temp.items.size() > 1){
					slotDragged = temp.splitCopy();
					dragged = true;
					dragItem = slotDragged.icon;
					draggedSlotNum = slotDragged.items.size();
					return;
				//put dragged item to empty selected slot
				}else if(temp.items.size() <= 0 && dragged == true){
					temp.fill(slotDragged);
					slotDragged.emptySlot();
					dragged = false;
					return;
				//put dragged item to slot that already has the same type of item (fail if the target slot already full)
				}else if(temp.items.size() > 0 && dragged == true && temp.type.equals(slotDragged.type) && temp.full == false && slotDragged.items.size()+temp.items.size() > Slot.MAX == false){
					temp.fill(slotDragged);
					slotDragged.emptySlot();
					dragged = false;
					return;
				/*
				 * Place the drawn item into a slot that has the same type of item, 
				 * but the number of drawn items is only placed until the slot is full.
				 * */
				}else if(temp.items.size() > 0 && dragged == true && temp.type.equals(slotDragged.type) && temp.full == false && slotDragged.items.size()+temp.items.size() > Slot.MAX == true) {
					temp.fillUntilFull(slotDragged, slotDragged.items.size() - temp.items.size());
					draggedSlotNum = slotDragged.items.size();
					return;
				}
			}
		}
		
	}
	public void dropItem() {
		
		for(int i = 0; i < slotDragged.items.size(); i++) {
			Item temp = slotDragged.items.get(i);
			temp.reSpawn(game.getPlayerObject().getX()+32, game.getPlayerObject().getY()+64);
			game.tryWorld.objectLayer.get(0).add(slotDragged.items.get(i));
		}
		slotDragged.emptySlot();
		dragged = false;
	}
}
