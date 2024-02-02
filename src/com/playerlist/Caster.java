package com.playerlist;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Entity;

public class Caster extends Entity{

	

	public Caster(int x, int y, ID id, EntityType et, EntityClass ec, Game game) {
		super(x, y, id, et, ec, game);
		//TODO Auto-generated constructor stub
		hp = 100;
		mana = 100;
		stamina = 100;
		speed = 4;
	}

	public void tick() {
		x += velX;
		y += velY;
		playerControl();
	}

	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, 32,32);
	}

	public Rectangle getBound() {
		return new Rectangle();
	}

	public Rectangle renderOrder() {
		return new Rectangle();
	}

	public void Collision() {
		
	}

	public void getImage() {

	}

}
