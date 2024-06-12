package com.input;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.main.Game;
import com.obj.Entity;
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

	public int prevX, prevY;
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
		}else if(game.gameState == game.mapState){
			if(!new Rectangle(game.gui.mapX, game.gui.mapY, (int)(game.gui.mh.worldMap.getWidth() * game.gui.currentScale), (int)(game.gui.mh.worldMap.getHeight() * game.gui.currentScale)).contains(new Rectangle(prevX, prevY, 1,1)))return;
			

			
			int deltaX = e.getX() - prevX;
			int deltaY = e.getY() - prevY;

			if(game.gui.mapX + deltaX < 0 && game.gui.mapX+(int)(game.gui.mh.worldMap.getWidth()*game.gui.currentScale)+deltaX > Game.WIDTH){
				game.gui.mapX += deltaX;
			}

			if(game.gui.mapY + deltaY < 0 && game.gui.mapY+(int)(game.gui.mh.worldMap.getHeight()*game.gui.currentScale)+deltaY > Game.HEIGHT){
				game.gui.mapY += deltaY;
			}

			prevX = e.getX();
			prevY = e.getY();
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		gui.getCurrentMousePost(e.getX(), e.getY());
		mx = e.getX();
		my = e.getY();
		Rectangle mouse = new Rectangle(e.getX()+(int)game.camera.getX(), e.getY()+(int)game.camera.getY(), 1, 1);
		if(game.gameState == game.InventoryState) {
			gui.inv.mx = mx;
			gui.inv.my = my;
			try {
				gui.inv.hoverSlotInv(e);
			} catch (Exception ex) {
				// TODO: handle exception
			}

			//for condition opening chest
			if(game.getPlayerObject().isOpeningChest){
				gui.inv.chestInventory.hoverSlotChest(e);
			}
		}else if(game.gameState == game.playState){
			gui.skillUi.slotHover(e);
			game.getPlayerObject().checkMouseHoverOnObject(mouse);
			gui.tileMec.hoverTiles(e);
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
		prevX = e.getX();
		prevY = e.getY();
        if (e.getButton() == MouseEvent.BUTTON1){
        	if(game.gameState == game.playState){
				player.automationEquipment(e);
				player.checkEquipment(e);
				player.openChest(e);
				game.gui.skillUi.checkSlot(e);
				game.gui.tileMec.tileClick(e);
				
    		}else if(game.gameState == game.InventoryState) {
				gui.inv.checkButton(e);

				//for condition opening chest
				if(game.getPlayerObject().isOpeningChest){
					gui.inv.chestInventory.checkChestSlot(e);
					gui.inv.chestInventory.hoverSlotChest(e);
				}
				if(game.gui.inv.getBound().contains(e.getPoint())){
					game.gui.inv.dragItem(e, game.gui.inv.itemType);
				}else if(game.gui.inv.equipment.getBound().contains(e.getPoint())){
					game.gui.inv.equipment.checkEquipmentSlot(e);
				}else if(!game.gui.inv.getBound().contains(e.getPoint()) && game.gui.inv.dragged && !game.gui.inv.equipment.getBound().contains(e.getPoint())) {
					// game.gui.inv.dropItem();
					// game.gameState = game.playState;
				}
    		}else if(game.gameState == game.skillTabState){
				game.gui.skillUi.checkSlot(e);
				game.gui.skillUi.checkSkillTree(e);
				gui.skillUi.checkPlusButton(e, true);
			}else if(game.gameState == game.BlueprintWindow){
				game.gui.blueprintGUI.checkBlueprintClick(e);
			}
            
        } else if (e.getButton() == MouseEvent.BUTTON2){
			if(game.gameState == game.playState){
				if(player.holdingTools == player.hammer){
					// if(gui.blueprintGUI.open){
					// 	gui.blueprintGUI.open = false;
					// }else{
					// 	gui.blueprintGUI.open = true;
					// }
					game.gameState = game.BlueprintWindow;
				}else if(player.holdingTools == player.building){
					player.holdingTools = player.hammer;
				}
			}else if(game.gameState == game.BlueprintWindow){
				if(player.holdingTools == player.hammer){
					game.gameState = game.playState;
				}
			}
            
        } else if (e.getButton() == MouseEvent.BUTTON3) {
        	if(game.gameState == game.playState){
				player.automationEquipment(e);
    			player.checkEquipment(e);

				if(player.holdingTools == player.building){
					player.holdingTools = player.hammer;
				}

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
