package com.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.main.Game;
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
	
	public void mousePressed(MouseEvent e) {
		int mx = (int) (e.getX() + camera.getX());
		int my = (int) (e.getY() + camera.getY());
		
		shootArrow(mx, my);
		
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

}
