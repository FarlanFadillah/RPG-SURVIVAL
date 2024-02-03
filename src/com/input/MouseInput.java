package com.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.main.Handler;
import com.obj.Entity;
import com.obj.GameObject;

import projectile.ArrowProjectile;

import com.id.BlockType;
import com.id.EntityType;
import com.id.ID;

public class MouseInput extends MouseAdapter{
	
	private Handler handler;
	private Camera camera;
	
	public MouseInput(Handler handler, Camera camera) {
		this.handler = handler;
		this.camera = camera;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = (int) (e.getX() + camera.getX());
		int my = (int) (e.getY() + camera.getY());
		
		for(int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			
			if(tempObject.getID() == ID.Entity){
				Entity entity = (Entity) tempObject;
				
				if(entity.getEntityType() == EntityType.Player) {
					handler.objects.add(new ArrowProjectile(entity.getX()+24, entity.getY()+16, null, BlockType.Projectile, null, handler, mx, my));
				}
			}
			
			
		}
		
	}

}
