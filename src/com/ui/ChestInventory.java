package com.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.blockList.Chest;
import com.filehandler.SpriteSheet;
import com.gameMechanics.PlayerEquipment;
import com.gameMechanics.Slot;
import com.id.ID;
import com.id.ItemType;
import com.main.Game;
import com.obj.Item;

public class ChestInventory {
    int stop, start;
    SpriteSheet slotInventory = new SpriteSheet("/assets/GUI/Banners/Carved_16Slides_WithFrame.png");
    public PlayerEquipment pe;
    public Game game;
    public int x, y;

    public Chest chestOpen;
    public ChestInventory(Game game){
        this.game = game;
        stop = (Game.WIDTH-slotInventory.image.getWidth())-16;
        start = Game.WIDTH+64;
        x = start;
        y = 8;
        pe = game.getPlayerObject().playerEquipment;
    }
    public void tick(boolean state){
        if(state){
            if(x > stop && chestOpen.openCounter >= 5){
                x -= 50;
                game.gameState = ID.INVENT_STATE;
            }
        }else{
            if(x < start){
                x += 50;
                chestOpen.open = false;
            }
        }
    }

    public void drawInventorytSlot(Graphics2D g2d){
        g2d.drawImage(slotInventory.image, x, y, null);
    }

    public Rectangle getBound(){
        return new Rectangle(x, y, slotInventory.image.getWidth(), slotInventory.image.getHeight());
    }

    /**
     * @param e
     */
    public void checkChestSlot(MouseEvent e){
        for (int i = 0; i < chestOpen.slotChest.length; i++) {
            Slot<Item> temp = chestOpen.slotChest[i];
            if(temp.getBound(x, y).contains(e.getPoint())){
                try {
                    if(temp.items.get(0).getItemType() == ItemType.Consume && game.gui.inv.dragged == false){
                        game.gui.inv.consumeTab.pressed = true;
                        game.gui.inv.usedTab.pressed = false;
                        game.gui.inv.ingredientTab.pressed = false;
                        game.gui.inv.itemType = ItemType.Consume;
                    }else if(temp.items.get(0).getItemType() == ItemType.Used && game.gui.inv.dragged == false){
                        game.gui.inv.usedTab.pressed = true;
                        game.gui.inv.consumeTab.pressed = false;
                        game.gui.inv.ingredientTab.pressed = false;
                        game.gui.inv.itemType = ItemType.Used;
                    }else if(temp.items.get(0).getItemType() == ItemType.ingredient && game.gui.inv.dragged == false){
                        game.gui.inv.ingredientTab.pressed = true;
                        game.gui.inv.usedTab.pressed = false;
                        game.gui.inv.consumeTab.pressed = false;
                        game.gui.inv.itemType = ItemType.ingredient;
                    }
                } catch (Exception ex) {
                    // TODO: handle exception
                }
                //drag item from slots
				if(temp.items.size() > 0 && game.gui.inv.dragged == false && game.key.holdCtrl == false && game.key.holdShift == false){
					game.gui.inv.slotDragged = temp.Copy();
					temp.emptySlot();
					game.gui.inv.dragged = true;
					game.gui.inv.dragItem = game.gui.inv.slotDragged.icon;
					game.gui.inv.draggedSlotNum = game.gui.inv.slotDragged.items.size();
					return;
				//split and drag item from slot
				}else if(temp.items.size() > 0 && game.gui.inv.dragged == false && game.key.holdCtrl == true && game.key.holdShift == false && temp.items.size() > 1){
                    game.gui.inv.slotDragged = temp.splitCopy();
					game.gui.inv.dragged = true;
					game.gui.inv.dragItem = game.gui.inv.slotDragged.icon;
					game.gui.inv.draggedSlotNum = game.gui.inv.slotDragged.items.size();
					return;
				//put dragged item to empty selected slot
				}else if(temp.items.size() <= 0 && game.gui.inv.dragged == true && game.gui.inv.slotDragged.items.get(0).getItemType() == game.gui.inv.itemType){
					temp.fill(game.gui.inv.slotDragged);
					game.gui.inv.slotDragged.emptySlot();
					game.gui.inv.dragged = false;
					return;
				//put dragged item to slot that already has the same type of item (fail if the target slot already full)
				}else if(temp.items.size() > 0 && game.gui.inv.dragged == true && temp.type.equals(game.gui.inv.slotDragged.type) && temp.full == false && game.gui.inv.slotDragged.items.size()+temp.items.size() > temp.MAX == false && game.gui.inv.slotDragged.items.get(0).getItemType() == game.gui.inv.itemType){
					temp.fill(game.gui.inv.slotDragged);
					game.gui.inv.slotDragged.emptySlot();
					game.gui.inv.dragged = false;
					return;
				/*
				 * Place the drawn item into a slot that has the same type of item, 
				 * but the number of drawn items is only placed until the slot is full.
				 * */
				}else if(temp.items.size() > 0 && game.gui.inv.dragged == true && temp.type.equals(game.gui.inv.slotDragged.type) && temp.full == false && game.gui.inv.slotDragged.items.size()+temp.items.size() > temp.MAX == true && game.gui.inv.slotDragged.items.get(0).getItemType() == game.gui.inv.itemType) {
					temp.fillUntilFull(game.gui.inv.slotDragged, game.gui.inv.slotDragged.items.size() - temp.items.size());
					game.gui.inv.draggedSlotNum = game.gui.inv.slotDragged.items.size();
					return;
				}else if(game.gui.inv.dragged == true && !temp.type.equals(game.gui.inv.slotDragged.type)){
                    return;
                }else if(temp.items.size() > 0 && game.gui.inv.dragged == false && game.key.holdShift == true && game.getPlayerObject().isOpeningChest){
                    boolean success = game.getPlayerObject().playerInventory.addItem(temp);
					if(success) temp.emptySlot();
					return;
				}
            }
        }
    }

    public void drawSlot(Graphics2D g2d){
        for (int i = 0; i < chestOpen.slotChest.length; i++) {
            Slot<Item> slot = chestOpen.slotChest[i];
            if(slot.type != null){
                g2d.drawImage(slot.icon, x+(slot.col*64), y+ (slot.row*64), null);
                g2d.setColor(Color.white);
                g2d.setFont(game.gui.inv.f1);
                g2d.drawString(String.valueOf(slot.items.size()), x+(slot.col*64)+44, y+ (slot.row*64)+55);
            }
        }
    }
    public void hoverSlotChest(MouseEvent e){
		for (int i = 0; i < chestOpen.slotChest.length; i++) {
			Slot<Item> temp = chestOpen.slotChest[i];
			if(temp.getBound(x,y).contains(e.getPoint()) && temp.type != null) {
				game.gui.inv.hover = true;
				game.gui.inv.mousex = e.getX();
				game.gui.inv.mousey = e.getY();
				game.gui.inv.slotHover = temp;
				return;
			}else{
				game.gui.inv.hover = false;
			}
		}
	}




}
