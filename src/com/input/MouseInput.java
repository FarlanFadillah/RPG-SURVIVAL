package com.input;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.main.Game;
import com.obj.Block;
import com.obj.Entity;
import com.obj.GameObject;

import projectile.ArrowProjectile;

import com.id.BlockType;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;

public class MouseInput extends MouseAdapter{
	
	private Camera camera;
	Game game;
	public MouseInput(Game game) {
		this.game = game;
		this.camera = game.camera;
	}

	@Override
	public void mouseClicked(MouseEvent e){
		hitTree(e, true);

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
