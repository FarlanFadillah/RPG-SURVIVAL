package com.input;

import com.main.Game;

public class Camera {
	
	public float x, y;
	
	

	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public void tick(Game game) {
		
		x+= (((game.key.player.getX() - x) - Game.WIDTH/2)+64) * 0.05f;
		y+= (((game.key.player.getY() - y) - Game.HEIGHT/2)+64) * 0.05f;
		if(x<=0) {
			x=0;
		}
		if(x>=(game.tryWorld.tilem.WIDTHMAP*64)-Game.WIDTH) {
			x=(game.tryWorld.tilem.WIDTHMAP*64)-Game.WIDTH;
		}
		if(y<=0) {
			y=0;
		}
		if(y>=(game.tryWorld.tilem.HEIGHTMAP*64)-Game.HEIGHT) {
			y=(game.tryWorld.tilem.HEIGHTMAP*64)-Game.HEIGHT;
		}
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	

}
