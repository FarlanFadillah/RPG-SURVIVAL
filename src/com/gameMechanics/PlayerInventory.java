package com.gameMechanics;


import com.id.ItemType;
import com.main.Game;
import com.obj.BluePrint;
import com.obj.Item;

public class PlayerInventory {
	Game game;
	@SuppressWarnings("unchecked")
	public Slot<Item>[] consumeSlot = new Slot[36];
	@SuppressWarnings("unchecked")
	public Slot<Item>[] usedSlot = new Slot[36];
	@SuppressWarnings("unchecked")
	public Slot<Item>[] ingredientSlot = new Slot[36];
	@SuppressWarnings("unchecked")
	public Slot<Item>[] slots = new Slot[36];
    int col, row = 0;
    public PlayerInventory(Game game){
        this.game = game;
        setSlot(consumeSlot, "consume");
        setSlot(ingredientSlot, "ingredient");
        setSlot(usedSlot, "used");
    }
    public boolean addItem(Item item){
    	for (int i = 0; i < 36; i++) {
    		try {
    			if(item.getItemType() == ItemType.Consume) {
    				if(!consumeSlot[i].full && consumeSlot[i].type.equals(item.name)){
    					consumeSlot[i].addItem(item);
    					return true;
    				}
    			}else if(item.getItemType() == ItemType.Used) {
    				if(!usedSlot[i].full && usedSlot[i].type.equals(item.name)){
    					usedSlot[i].addItem(item);
    					return true;
    				}
    			}else if(item.getItemType() == ItemType.ingredient) {
    				if(!ingredientSlot[i].full && ingredientSlot[i].type.equals(item.name)){
    					ingredientSlot[i].addItem(item);
    					return true;
    				}
    			}
			} catch (Exception e) {

			}
		}
		for (int i = 0; i < 36; i++) {
        	if(item.getItemType() == ItemType.Consume) {
        		if(consumeSlot[i].type == null){
        			consumeSlot[i].addItem(item);
        			return true;
        		}else if(!consumeSlot[i].full && consumeSlot[i].type.equals(item.name)){
        			consumeSlot[i].addItem(item);
        			return true;
        		}
        	}else if(item.getItemType() == ItemType.Used) {
        		if(usedSlot[i].type == null){
        			usedSlot[i].addItem(item);
        			return true;
        		}else if(!usedSlot[i].full && usedSlot[i].type.equals(item.name)){
        			usedSlot[i].addItem(item);
        			return true;
        		}
        	}else if(item.getItemType() == ItemType.ingredient) {
        		if(ingredientSlot[i].type == null){
        			ingredientSlot[i].addItem(item);
        			return true;
        		}else if(!ingredientSlot[i].full && ingredientSlot[i].type.equals(item.name)){
        			ingredientSlot[i].addItem(item);
        			return true;
        		}
        	}
        }
		return false;
	}
    //initialization the slot, total 36 slot.
    public void setSlot(Slot<Item>[] slot, String type){
        int i = 0, col = 0, row = 0;
        while (row < 6) {

        	slot[i] = new Slot<Item>(col, row, 50, 50, 22, 15, 64); 
			if(type.equals("used")){
				slot[i].MAX = 1;
			}
            col++;
            i++;
            if(col >= 6){
                row++;
                col = 0;
            }

        }
        
    }
	
	public boolean addItem(Slot<Item> slot){
		@SuppressWarnings("unchecked")
		Slot<Item>[] slot2 = new Slot[0];
		if(slot.items.get(0).getItemType() == ItemType.Consume) slot2 = consumeSlot;
		else if(slot.items.get(0).getItemType() == ItemType.Used) slot2 = usedSlot;
		else slot2 = ingredientSlot;
        for (int i = 0; i < 36; i++) {
            try {
                if(!slot2[i].full && slot2[i].type.equals(slot.type) && slot2[i].items.size()+slot.items.size() <= slot2[i].MAX){
                    slot2[i].fill(slot);
                    return true;
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            
        }

        for (int i = 0; i < 36; i++) {
            if(slot2[i].type == null){
                slot2[i].fill(slot);
                return true;
            }else if(!slot2[i].full && slot2[i].type.equals(slot.type) && slot2[i].items.size()+slot.items.size() <= slot2[i].MAX){
                slot2[i].fill(slot);
                return true;
            }
        }

		return false;
    }
    public boolean checkIngredient(BluePrint bluprintBuilding) {
        // TODO Auto-generated method stub
		boolean[] canBuild = new boolean[bluprintBuilding.ingredients.size()];
		for (int i = 0; i < bluprintBuilding.ingredients.size(); i++) {
			String ingredient = (String) bluprintBuilding.ingredients.keySet().toArray()[i];
			int value = bluprintBuilding.ingredients.get(ingredient);
			for (int j = 0; j < ingredientSlot.length; j++) {
				if(ingredientSlot[j].type == null) continue;
				if(ingredientSlot[j].items.get(0).name.equals(ingredient) && ingredientSlot[j].items.size() >= value){
					canBuild[i] = true;
					break;
				}else{
					canBuild[i] = false;
				}
			}
		}
		for (int i = 0; i < canBuild.length; i++) {
			if(canBuild[i] == false){
				System.out.println("ingredient less");
				return false;
			}
		}

		cutIngredient(bluprintBuilding);
		return true;
    }
	private void cutIngredient(BluePrint bluprintBuilding) {
		for (int i = 0; i < bluprintBuilding.ingredients.size(); i++) {
			String ingredient = (String) bluprintBuilding.ingredients.keySet().toArray()[i];
			int value = bluprintBuilding.ingredients.get(ingredient);
			for (int j = 0; j < ingredientSlot.length; j++) {
				if(ingredientSlot[j].type == null) continue;
				if(ingredientSlot[j].items.get(0).name.equals(ingredient) && ingredientSlot[j].items.size() >= value){
					while(value > 0){
						ingredientSlot[j].removeItem(ingredientSlot[j].items.remove(ingredientSlot[j].items.size()-1));
						value--;
					}
					break;
				}
			}
		}
	}
	
	
}
