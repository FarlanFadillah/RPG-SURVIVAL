package com.gameMechanics;


import com.id.ItemType;
import com.main.Game;
import com.obj.Item;

public class PlayerInventory {
	Game game;
	@SuppressWarnings("unchecked")
	public Slot<Item>[] consumeSlot = new Slot[36];
	@SuppressWarnings("unchecked")
	public Slot<Item>[] usedSlot = new Slot[42];
	@SuppressWarnings("unchecked")
	public Slot<Item>[] ingredientSlot = new Slot[36];
	@SuppressWarnings("unchecked")
	public Slot<Item>[] slots = new Slot[36];
    int col, row = 0;
    public PlayerInventory(Game game){
        this.game = game;
        setSlot(consumeSlot);
        setSlot(ingredientSlot);
        setSlot(usedSlot);
    }
    public void addItem(Item item){
    	for (int i = 0; i < 36; i++) {
    		try {
    			if(item.getItemType() == ItemType.Consume) {
    				if(!consumeSlot[i].full && consumeSlot[i].type.equals(item.name)){
    					consumeSlot[i].addItem(item);
    					return;
    				}
    			}else if(item.getItemType() == ItemType.Used) {
    				if(!usedSlot[i].full && usedSlot[i].type.equals(item.name)){
    					usedSlot[i].addItem(item);
    					return;
    				}
    			}else if(item.getItemType() == ItemType.ingredient) {
    				if(!ingredientSlot[i].full && ingredientSlot[i].type.equals(item.name)){
    					ingredientSlot[i].addItem(item);
    					return;
    				}
    			}
			} catch (Exception e) {

			}
		}
		for (int i = 0; i < 36; i++) {
        	if(item.getItemType() == ItemType.Consume) {
        		if(consumeSlot[i].type == null){
        			consumeSlot[i].addItem(item);
        			return;
        		}else if(!consumeSlot[i].full && consumeSlot[i].type.equals(item.name)){
        			consumeSlot[i].addItem(item);
        			return;
        		}
        	}else if(item.getItemType() == ItemType.Used) {
        		if(usedSlot[i].type == null){
        			usedSlot[i].addItem(item);
        			return;
        		}else if(!usedSlot[i].full && usedSlot[i].type.equals(item.name)){
        			usedSlot[i].addItem(item);
        			return;
        		}
        	}else if(item.getItemType() == ItemType.ingredient) {
        		if(ingredientSlot[i].type == null){
        			ingredientSlot[i].addItem(item);
        			return;
        		}else if(!ingredientSlot[i].full && ingredientSlot[i].type.equals(item.name)){
        			ingredientSlot[i].addItem(item);
        			return;
        		}
        	}
        }
	}
    //initialization the slot, total 36 slot.
    public void setSlot(Slot<Item>[] slot){
        int i = 0, col = 0, row = 0;
        while (row < 6) {

        	slot[i] = new Slot<Item>(col, row, 50, 50, 22, 15, 64);
            col++;
            i++;
            if(col >= 6){
                row++;
                col = 0;
            }

        }
        
    }
	//addition for equipment slot
	public void usedSlotSet(){
		PlayerEquipment pe = game.getPlayerObject().playerEquipment;
		int x = 36;
		for (int i = 0; i < pe.slots.length; i++) {
			usedSlot[x] = pe.slots[i];
			x++;
		}
	}

	
	
}
