package com.playerlist;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.filehandler.SpriteSheet;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Entity;

public class Fighter extends Entity{
	
	public SpriteSheet ss = new SpriteSheet("/assetsentity/Fighter.png");
	
	public Fighter(int x, int y, ID id, EntityType et, EntityClass ec, Game game) {
		super(x, y, id, et, ec, game);
		hp = 100;
		mana = 100;
		stamina = 100;
		speed = 4;
		arah = "down";
		
		getImage();
	}

	public void tick() {
		x += velX;
		y += velY;
		playerControl();
		spriteCounter();
	}

	public void render(Graphics g) {
		animatedSprite();
		g.drawImage(image, x, y, 128, 128, null);
	}

	public Rectangle getBound() {
		return new Rectangle();
		
	}

	public Rectangle renderOrder() {
		return new Rectangle();
	}

	public void Collision() {
		
	}
	
	public void animatedSprite(){
		if(isUp() || isDown()|| isRight() || isLeft()) {
			switch (arah) {
			case "atas": 
				if(spriteNum == 1) {
					image = runUp[0];
				}
				if(spriteNum == 2) {
					image = runUp[1];
				}
				if(spriteNum == 3) {
					image = runUp[2];
				}
				if(spriteNum == 4) {
					image = runUp[3];
				}
				if(spriteNum == 5) {
					image = runUp[4];
				}
				if(spriteNum == 6) {
					image = runUp[5];
				}

				break;
				
			case "bawah":
				if(spriteNum == 1) {
					image = runDown[0];
				}
				if(spriteNum == 2) {
					image = runDown[1];
				}
				if(spriteNum == 3) {
					image = runDown[2];
				}
				if(spriteNum ==4) {
					image = runDown[3];
				}
				if(spriteNum ==5) {
					image = runDown[4];
				}
				if(spriteNum ==6) {
					image = runDown[5];
				}

				break;
			case "kanan":
				if(spriteNum == 1) {
					image = runRight[0];
				}
				if(spriteNum == 2) {
					image = runRight[1];
				}
				if(spriteNum == 3) {
					image = runRight[2];
				}
				if(spriteNum ==4) {
					image = runRight[3];
				}
				if(spriteNum ==5) {
					image = runRight[4];
				}
				if(spriteNum ==6) {
					image = runRight[5];
				}

				
				break;
			case "kiri":
				if(spriteNum == 1) {
					image = runLeft[0];
				}
				if(spriteNum == 2) {
					image = runLeft[1];
				}
				if(spriteNum == 3) {
					image = runLeft[2];
				}
				if(spriteNum ==4) {
					image = runLeft[3];
				}
				if(spriteNum ==5) {
					image = runLeft[4];
				}
				if(spriteNum ==65) {
					image = runLeft[5];
				}
				break;
			}
			
		}else {
			switch (arah) {
			case "atas": 
				if(spriteNum == 1) {
					image = idleUp[0];
				}
				if(spriteNum == 2) {
					image = idleUp[1];
				}
				if(spriteNum == 3) {
					image = idleUp[2];
				}
				if(spriteNum ==4) {
					image = idleUp[3];
				}
				if(spriteNum ==5) {
					image = idleUp[4];
				}
				if(spriteNum ==6) {
					image = idleUp[5];
				}
				break;
				
			case "bawah":
				if(spriteNum == 1) {
					image = idleDown[0];
				}
				if(spriteNum == 2) {
					image = idleDown[1];
				}
				if(spriteNum == 3) {
					image = idleDown[2];
				}
				if(spriteNum ==4) {
					image = idleDown[3];
				}
				if(spriteNum ==5) {
					image = idleDown[4];
				}
				if(spriteNum ==6) {
					image = idleDown[5];
				}
				break;
			case "kanan":
				if(spriteNum == 1) {
					image = idleRight[0];
				}
				if(spriteNum == 2) {
					image = idleRight[1];
				}
				if(spriteNum == 3) {
					image = idleRight[2];
				}
				if(spriteNum ==4) {
					image = idleRight[3];
				}
				if(spriteNum ==5) {
					image = idleRight[4];
				}
				if(spriteNum ==6) {
					image = idleRight[5];
				}
				break;
			case "kiri":
				if(spriteNum == 1) {
					image = idleLeft[0];
				}
				if(spriteNum == 2) {
					image = idleLeft[1];
				}
				if(spriteNum == 3) {
					image = idleLeft[2];
				}
				if(spriteNum ==4) {
					image = idleLeft[3];
				}
				if(spriteNum ==5) {
					image = idleLeft[4];
				}
				if(spriteNum ==6) {
					image = idleLeft[5];
				}
				break;
			}
		}
	}
	
	public void getImage() {
		
		try {
			
			runDown[0] = ss.grabImage(1, 2, 128, 128);
			runDown[1] = ss.grabImage(2, 2, 128, 128);
			runDown[2] = ss.grabImage(3, 2, 128, 128);
			runDown[3] = ss.grabImage(4, 2, 128, 128);
			runDown[4] = ss.grabImage(5, 2, 128, 128);
			runDown[5] = ss.grabImage(6, 2, 128, 128);
			
			runUp[0] = ss.grabImage(1, 3, 128, 128);
			runUp[1] = ss.grabImage(2, 3, 128, 128);
			runUp[2] = ss.grabImage(3, 3, 128, 128);
			runUp[3] = ss.grabImage(4, 3, 128, 128);
			runUp[4] = ss.grabImage(5, 3, 128, 128);
			runUp[5] = ss.grabImage(6, 3, 128, 128);
			
			runRight[0] = ss.grabImage(1, 2, 128, 128);
			runRight[1] = ss.grabImage(2, 2, 128, 128);
			runRight[2] = ss.grabImage(3, 2, 128, 128);
			runRight[3] = ss.grabImage(4, 2, 128, 128);
			runRight[4] = ss.grabImage(5, 2, 128, 128);
			runRight[5] = ss.grabImage(6, 2, 128, 128);
			
			runLeft[0] = ss.grabImage(1, 3, 128, 128);
			runLeft[1] = ss.grabImage(2, 3, 128, 128);
			runLeft[2] = ss.grabImage(3, 3, 128, 128);
			runLeft[3] = ss.grabImage(4, 3, 128, 128);
			runLeft[4] = ss.grabImage(5, 3, 128, 128);
			runLeft[5] = ss.grabImage(6, 3, 128, 128);
			
			idleUp[0] = ss.grabImage(1, 1, 128, 128);
			idleUp[1] = ss.grabImage(2, 1, 128, 128);
			idleUp[2] = ss.grabImage(3, 1, 128, 128);
			idleUp[3] = ss.grabImage(4, 1, 128, 128);
			idleUp[4] = ss.grabImage(5, 1, 128, 128);
			idleUp[5] = ss.grabImage(6, 1, 128, 128);
			
			idleDown[0] = ss.grabImage(1, 1, 128, 128);
			idleDown[1] = ss.grabImage(2, 1, 128, 128);
			idleDown[2] = ss.grabImage(3, 1, 128, 128);
			idleDown[3] = ss.grabImage(4, 1, 128, 128);
			idleDown[4] = ss.grabImage(5, 1, 128, 128);
			idleDown[5] = ss.grabImage(6, 1, 128, 128);
			
			idleRight[0] = ss.grabImage(1, 1, 128, 128);
			idleRight[1] = ss.grabImage(2, 1, 128, 128);
			idleRight[2] = ss.grabImage(3, 1, 128, 128);
			idleRight[3] = ss.grabImage(4, 1, 128, 128);
			idleRight[4] = ss.grabImage(5, 1, 128, 128);
			idleRight[5] = ss.grabImage(6, 1, 128, 128);
			
			idleLeft[0] = ss.grabImage(1, 1, 128, 128);
			idleLeft[1] = ss.grabImage(2, 1, 128, 128);
			idleLeft[2] = ss.grabImage(3, 1, 128, 128);
			idleLeft[3] = ss.grabImage(4, 1, 128, 128);
			idleLeft[4] = ss.grabImage(5, 1, 128, 128);
			idleLeft[5] = ss.grabImage(6, 1, 128, 128);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
