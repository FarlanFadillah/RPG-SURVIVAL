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

import projectile.ArrowProjectile;

import com.blockList.Tree;
import com.gameMechanics.Slot;
import com.id.BlockType;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;

public class MouseInput extends MouseAdapter{
	
	private Camera camera;
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
		this.camera = game.camera;
		this.gui = game.gui;
		player = game.getPlayerObject();
		image = Tree.ss.grabImage(1, 1, 192, 192);
	}

	@Override
	public void mouseClicked(MouseEvent e){
		if(game.gameState == game.InventoryState){
			player.playerInventory.dragItem(e);
			if(!game.gui.inv.getBound().contains(e.getPoint()) && player.playerInventory.dragged == false) {
				game.gameState = game.playState;
			}else if(!game.gui.inv.getBound().contains(e.getPoint()) && player.playerInventory.dragged) {
				player.playerInventory.dropItem();
				game.gameState = game.playState;
			}
		}else if(game.gameState == game.playState){
			hitTree(e, true);
			player.attacking(e);
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
		int mx = (int) (e.getX() + camera.getX());
		int my = (int) (e.getY() + camera.getY());
		
		shootArrow(mx, my);

		
	}
	@Override
	public void mouseReleased(MouseEvent e){
	}

	public void shootArrow(int mx, int my){
		for(int i = 0; i < game.tryWorld.objectLayer.get(0).size(); i++) {
			GameObject tempObject = game.tryWorld.objectLayer.get(0).get(i);
			
			if(tempObject.getID() == ID.Entity){
				Entity entity = (Entity) tempObject;
				
				if(entity.getEntityType() == EntityType.Player && entity.getEntityClass() == EntityClass.Archer) {
					game.tryWorld.objectLayer.get(0).add(new ArrowProjectile(entity.getX()+24, entity.getY()+16, null, BlockType.Projectile, game, mx, my));
				}
			}
			
			
		}
	}

	public void hitTree(MouseEvent e, boolean gethit){
		Block hit = null;
		Rectangle key = new Rectangle((e.getX() + (int)game.camera.getX())-5, (e.getY() + (int) game.camera.getY())-5, 10, 10);
		for(int i = 0; i < game.tryWorld.objectLayer.get(0).size(); i++) {
			GameObject tempObject = game.tryWorld.objectLayer.get(0).get(i);
			if(tempObject.getBound().intersects(key.getBounds())&&tempObject.getID() == ID.Block){
				Block tempBlock = (Block) tempObject;
				if(tempBlock.getBlockType() == BlockType.DestroyAble){
					hit = (Block)tempBlock;
				}
			}
			
			
		}
		try {
			hit.getHit = true;
		} catch (Exception ex) {
			// TODO: handle exception
		}
	}

}
