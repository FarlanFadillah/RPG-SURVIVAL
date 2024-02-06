package com.monsters;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.filehandler.SpriteSheet;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Entity;
import com.tile.ImageManager;

public class GoblinBarrel extends Entity{
	
	public SpriteSheet ss = new SpriteSheet("/assets/assetsentity/Goblins_Barrel_Red.png");
	ImageManager im = new ImageManager();

	public GoblinBarrel(int x, int y, ID id, EntityType et, EntityClass ec, Game game) {
		super(x, y, id, et, ec, game);
		hp = 50;
		mana = 50;
		stamina = 50;
		speed = 4;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Collision() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getImage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle renderOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void hit() {
		// TODO Auto-generated method stub
		
	}

}
