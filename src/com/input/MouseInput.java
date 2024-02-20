package com.input;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.main.Game;
import com.obj.Block;
import com.obj.Entity;
import com.obj.GameObject;
import com.ui.GUI;


import com.blockList.Tree;
import com.gameMechanics.Slot;
import com.id.BlockType;
import com.id.ID;

public class MouseInput extends MouseAdapter{
	
	Game game;
	
	public int mx, my;
	public BufferedImage image;
	GUI gui;

	public boolean dragged = false;
	public Slot slotDragged;
	public BufferedImage dragItem;
	Entity player;
	public MouseInput(Game game) {
		this.game = game;
		this.gui = game.gui;
		player = game.getPlayerObject();
		image = Tree.ss.grabImage(1, 1, 192, 192);
	}

	@Override
	public void mouseClicked(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1){
			if(game.gameState == game.InventoryState){
				game.gui.inv.dragItem(e, game.gui.inv.itemType);
				if(!game.gui.inv.getBound().contains(e.getPoint()) && game.gui.inv.dragged && !game.gui.inv.equipment.getBound().contains(e.getPoint())) {
					game.gui.inv.dropItem();
					game.gameState = game.playState;
				}
			}else if(game.gameState == game.playState){
				player.attacking1(e);
				player.checkTree(e, true);
				
			}
        }else if (e.getButton() == MouseEvent.BUTTON2){
            
        }else if (e.getButton() == MouseEvent.BUTTON3){
        	
        }
		
	}
	

	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		if(game.gameState == game.InventoryState) {
			gui.inv.mx = mx;
			gui.inv.my = my;
		}
	}
	public void putBlock(Graphics2D g, int x, int y, double xx, double yy){

	}
	
	public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
        	if(game.gameState == game.playState){

    		}else if(game.gameState == game.InventoryState) {
    			gui.inv.checkButton(e);
    		}
            
        } else if (e.getButton() == MouseEvent.BUTTON2){
            
        } else if (e.getButton() == MouseEvent.BUTTON3) {
        	if(game.gameState == game.playState){
    			player.attacking2(e);

    		}else if(game.gameState == game.InventoryState) {
    
    		}
            
        } 
    }
	
	@Override
	public void mouseReleased(MouseEvent e){
		player.attack2();
	}
	
}
