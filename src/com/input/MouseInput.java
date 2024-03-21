package com.input;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.main.Game;
import com.obj.Entity;
import com.obj.GameObject;
import com.ui.GUI;


import com.blockList.Tree;

public class MouseInput extends MouseAdapter{
	
	Game game;
	
	public int mx, my;
	public BufferedImage image;
	GUI gui;

	public boolean dragged = false;
	public BufferedImage dragItem;
	Entity player;
	public MouseInput(Game game) {
		this.game = game;
		this.gui = game.gui;
		player = game.getPlayerObject();
		image = Tree.ss.grabImage(1, 1, 192, 192);
	}
	@Override
	public void mouseDragged(MouseEvent e){
		if(game.gameState == game.skillTabState){
			gui.skillUi.checkPlusButtonHover(e);
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		Rectangle mouse = new Rectangle(e.getX()+(int)game.camera.getX(), e.getY()+(int)game.camera.getY(), 1, 1);
		if(game.gameState == game.InventoryState) {
			gui.inv.mx = mx;
			gui.inv.my = my;
			gui.inv.hoverSlotInv(e);
		}else if(game.gameState == game.playState){
			gui.skillUi.slotHover(e);
			for (int i = 0; i < game.tryWorld.objects.size(); i++) {
				GameObject temp = game.tryWorld.objects.get(i);
				if(temp.getBound().contains(mouse) && temp.getClass() == Tree.class){
					temp.hover = true;
					return;
				}else{
					temp.hover = false;
				}
			}
			for (int i = 0; i < game.tryWorld.entity.size(); i++) {
				GameObject temp = game.tryWorld.entity.get(i);
				if(temp.getBound().contains(mouse)){
					temp.hover = true;
					return;
				}else{
					temp.hover = false;
				}
			}
		}else if(game.gameState == game.skillTabState){
			gui.skillUi.slotHover(e);
			gui.skillUi.mousePos(e);
			gui.skillUi.skillTreeHover(e);
		}
	}
	public void putBlock(Graphics2D g, int x, int y, double xx, double yy){

	}
	
	public void drawPointer(Graphics2D g){
		g.fillRect(mx, my, 5, 5);
	}
	
	public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
        	if(game.gameState == game.playState){
				player.automationEquipment(e);
				player.checkEquipment(e);
				game.gui.skillUi.checkSlot(e);
    		}else if(game.gameState == game.InventoryState) {
				gui.inv.checkButton(e);
				if(game.gui.inv.getBound().contains(e.getPoint()) || game.gui.inv.equipment.getBound().contains(e.getPoint())){
					game.gui.inv.dragItem(e, game.gui.inv.itemType);
				}else if(!game.gui.inv.getBound().contains(e.getPoint()) && game.gui.inv.dragged && !game.gui.inv.equipment.getBound().contains(e.getPoint())) {
					game.gui.inv.dropItem();
					game.gameState = game.playState;
				}
    		}else if(game.gameState == game.skillTabState){
				game.gui.skillUi.checkSlot(e);
				game.gui.skillUi.checkSkillTree(e);
				gui.skillUi.checkPlusButton(e, true);
			}
            
        } else if (e.getButton() == MouseEvent.BUTTON2){
            
        } else if (e.getButton() == MouseEvent.BUTTON3) {
        	if(game.gameState == game.playState){
				player.automationEquipment(e);
    			player.checkEquipment(e);

    		}else if(game.gameState == game.InventoryState) {
    
    		}
            
        } 
    }
	
	@Override
	public void mouseReleased(MouseEvent e){
		player.attack2();
		if(game.gameState == game.skillTabState){
			gui.skillUi.checkPlusButton(e, false);
		}
	}
	
}
