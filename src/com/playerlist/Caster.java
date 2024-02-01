package com.playerlist;

import java.awt.Rectangle;

import com.id.ID;
import com.obj.Entity;

public class Caster extends Entity{

	public Caster(int x, int y, ID id) {
		super(x, y, id);
		hp = 100;
		mana = 100;
		stamina = 100;
	}

	public void tick() {
		
	}

	public void render() {
		
	}

	public Rectangle getBound() {
		return null;
	}

	public Rectangle renderOrder() {
		return null;
	}

	public void Collision() {
		
	}

}
