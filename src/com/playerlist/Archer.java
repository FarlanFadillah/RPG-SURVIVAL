package com.playerlist;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.filehandler.SpriteSheet;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Entity;
import com.obj.GameObject;
import com.obj.Item;
import com.tile.ImageManager;

public class Archer extends Entity{

	public SpriteSheet ss = new SpriteSheet("/assets/assetsentity/Archer.png");
	ImageManager im = new ImageManager();
	public Archer(int x, int y, ID id, EntityType et, EntityClass ec, Game game) {
		super(x, y, id, et, ec, game);
		hp = 100;
		mana = 100;
		stamina = 100;
		speed = 4;
		getImage();
		image = idleRight[0];
		arah = "kanan";
	}
	
	public void tick() {
		x += velX;
		y += velY;
		Collision();
		playerControl();
		spriteCounter();	
	}

	public void render(Graphics g) {
		animatedSprite();
		g.drawImage(image, x, y, null);
	}

	public Rectangle getBound() {
		return new Rectangle(x+48, y +64, 32, 32 );
	}

	public Rectangle renderOrder() {
		return new Rectangle(x+48, y +64, 32, 32 );
	}

	public void Collision() {
		for (int i = 0; i < game.tryWorld.objectLayer.get(0).size(); i++) {
			GameObject temp = game.tryWorld.objectLayer.get(0).get(i);
			if(getBound().intersects(temp.getBound()) && temp.getID() == ID.Block && temp.highGround == false){
				x += velX * -1;
				y += velY * -1;
			}

			
			if(this != temp && getBound().intersects(temp.getBound()) &&temp.getID() == ID.Entity) {
					x += velX * -1;
					y += velY * -1;
			}
			
			if(getBound().intersects(temp.getBound()) && temp.getID() == ID.Item){
				game.tryWorld.objectLayer.get(0).remove(temp);
				Item getItem = (Item) temp;
				playerInventory.addItem(getItem);
			}
		}
		
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
			
			runDown[0] = im.scaledImage(ss.grabImage(1, 2, 128, 128), 128, 128);
			runDown[1] = im.scaledImage(ss.grabImage(2, 2, 128, 128), 128, 128);
			runDown[2] = im.scaledImage(ss.grabImage(3, 2, 128, 128), 128, 128);
			runDown[3] = im.scaledImage(ss.grabImage(4, 2, 128, 128), 128,128);
			runDown[4] = im.scaledImage(ss.grabImage(5, 2, 128, 128), 128,128);
			runDown[5] = im.scaledImage(ss.grabImage(6, 2, 128, 128), 128,128);
			
			runUp[0] = im.scaledImage(ss.grabImage(1, 3, 128, 128), 128,128);
			runUp[1] = im.scaledImage(ss.grabImage(2, 3, 128, 128), 128,128);
			runUp[2] = im.scaledImage(ss.grabImage(3, 3, 128, 128), 128,128);
			runUp[3] = im.scaledImage(ss.grabImage(4, 3, 128, 128), 128,128);
			runUp[4] = im.scaledImage(ss.grabImage(5, 3, 128, 128), 128,128);
			runUp[5] = im.scaledImage(ss.grabImage(6, 3, 128, 128), 128,128);
			
			runRight[0] = im.scaledImage(ss.grabImage(1, 2, 128, 128), 128,128);
			runRight[1] = im.scaledImage(ss.grabImage(2, 2, 128, 128), 128,128);
			runRight[2] = im.scaledImage(ss.grabImage(3, 2, 128, 128), 128,128);
			runRight[3] = im.scaledImage(ss.grabImage(4, 2, 128, 128), 128,128);
			runRight[4] = im.scaledImage(ss.grabImage(5, 2, 128, 128), 128,128);
			runRight[5] = im.scaledImage(ss.grabImage(6, 2, 128, 128), 128,128);
			
			runLeft[0] = im.scaledImage(ss.grabImage(1, 3, 128, 128), 128,128);
			runLeft[1] = im.scaledImage(ss.grabImage(2, 3, 128, 128), 128,128);
			runLeft[2] = im.scaledImage(ss.grabImage(3, 3, 128, 128), 128,128);
			runLeft[3] = im.scaledImage(ss.grabImage(4, 3, 128, 128), 128,128);
			runLeft[4] = im.scaledImage(ss.grabImage(5, 3, 128, 128), 128,128);
			runLeft[5] = im.scaledImage(ss.grabImage(6, 3, 128, 128), 128,128);
			
			idleUp[0] = im.scaledImage(ss.grabImage(1, 1, 128, 128), 128,128);
			idleUp[1] = im.scaledImage(ss.grabImage(2, 1, 128, 128), 128,128);
			idleUp[2] = im.scaledImage(ss.grabImage(3, 1, 128, 128), 128,128);
			idleUp[3] = im.scaledImage(ss.grabImage(4, 1, 128, 128), 128,128);
			idleUp[4] = im.scaledImage(ss.grabImage(5, 1, 128, 128), 128,128);
			idleUp[5] = im.scaledImage(ss.grabImage(6, 1, 128, 128), 128,128);
			
			idleDown[0] = im.scaledImage(ss.grabImage(1, 1, 128, 128), 128,128);
			idleDown[1] = im.scaledImage(ss.grabImage(2, 1, 128, 128), 128,128);
			idleDown[2] = im.scaledImage(ss.grabImage(3, 1, 128, 128), 128,128);
			idleDown[3] = im.scaledImage(ss.grabImage(4, 1, 128, 128), 128,128);
			idleDown[4] = im.scaledImage(ss.grabImage(5, 1, 128, 128), 128,128);
			idleDown[5] = im.scaledImage(ss.grabImage(6, 1, 128, 128), 128,128);
			
			idleLeft[0] = im.scaledImage(ss.grabImage(1, 1, 128, 128), 128,128);
			idleLeft[1] = im.scaledImage(ss.grabImage(2, 1, 128, 128), 128,128);
			idleLeft[2] = im.scaledImage(ss.grabImage(3, 1, 128, 128), 128,128);
			idleLeft[3] = im.scaledImage(ss.grabImage(4, 1, 128, 128), 128,128);
			idleLeft[4] = im.scaledImage(ss.grabImage(5, 1, 128, 128), 128,128);
			idleLeft[5] = im.scaledImage(ss.grabImage(6, 1, 128, 128), 128,128);
			
			idleRight[0] = im.scaledImage(ss.grabImage(1, 1, 128, 128), 128,128);
			idleRight[1] = im.scaledImage(ss.grabImage(2, 1, 128, 128), 128,128);
			idleRight[2] = im.scaledImage(ss.grabImage(3, 1, 128, 128), 128,128);
			idleRight[3] = im.scaledImage(ss.grabImage(4, 1, 128, 128), 128,128);
			idleRight[4] = im.scaledImage(ss.grabImage(5, 1, 128, 128), 128,128);
			idleRight[5] = im.scaledImage(ss.grabImage(6, 1, 128, 128), 128,128);
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Rectangle getSize() {
		// TODO Auto-generated method stub
		return new Rectangle(x, y, image.getWidth(), image.getHeight());
	}

	@Override
	public void hit() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'hit'");
	}
	


}
