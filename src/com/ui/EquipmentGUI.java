package com.ui;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.filehandler.SpriteSheet;
import com.gameMechanics.PlayerEquipment;
import com.gameMechanics.Slot;
import com.main.Game;
import com.obj.Item;;

public class EquipmentGUI {
    int stop, start;
    SpriteSheet slotEquipment = new SpriteSheet("/assets/GUI/Banners/Equipment.png");
    public PlayerEquipment pe;
    public Game game;
    public int x, y;

    public PlayerEquipment playerEquipment;
    public EquipmentGUI(Game game){
        this.game = game;
        stop = (Game.WIDTH-slotEquipment.image.getWidth())-16;
        start = Game.WIDTH+64;
        x = start;
        y = 8;
        pe = game.getPlayerObject().playerEquipment;
    }
    public void tick(boolean state){
        if(state){
            if(x > stop){
                x -= 50;
            }
        }else{
            if(x < start){
                x += 50;
            }
        }
    }

    public void drawEquipmentSlot(Graphics2D g2d){
        g2d.drawImage(slotEquipment.image, x, y, null);
        g2d.drawImage(game.getPlayerObject().idleDown[0], x+32, y+48, null);
    }

    public Rectangle getBound(){
        return new Rectangle(x, y, slotEquipment.image.getWidth(), slotEquipment.image.getHeight());
    }

    public void checkEquipmentSlot(MouseEvent e){
        for (int i = 0; i < pe.slots.length; i++) {
            Slot<Item> temp = pe.slots[i];
            if(temp.getBound(x, y).contains(e.getPoint())){
                if(temp.items.size() > 0 && game.gui.inv.dragged == false){
                    game.gui.inv.slotDragged = temp.Copy();

                    temp.emptySlot(1);

                    game.gui.inv.dragged = true;
                    game.gui.inv.dragItem = game.gui.inv.slotDragged.icon;
                    game.gui.inv.draggedSlotNum = game.gui.inv.slotDragged.items.size(); 
                    return;
                }else if(temp.items.size() <= 0 && game.gui.inv.dragged == true && game.gui.inv.slotDragged.items.get(0).getItemType() == game.gui.inv.itemType && temp.equipment == true && temp.equipmentType.equals(game.gui.inv.slotDragged.items.get(0).equipmentType)){
                    temp.fill(game.gui.inv.slotDragged);
                    game.gui.inv.slotDragged.emptySlot();
                    game.gui.inv.dragged = false;
                    return;
                }
                
            }
        }
    }

    public void drawEpuipment(Graphics2D g2d){
        for (int i = 0; i < pe.slots.length; i++) {
            Slot<Item> temp = pe.slots[i];
            if(temp.type != null){
                g2d.drawImage(temp.icon, (int)temp.getBound(x, y).getX(), (int)temp.getBound(x,y).getY(), null);
            }
        }
    }
}
