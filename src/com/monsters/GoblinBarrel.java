package com.monsters;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.filehandler.SpriteSheet;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Entity;
import com.obj.GameObject;
import com.tile.ImageManager;

public class GoblinBarrel extends Entity{
	
	public SpriteSheet ss = new SpriteSheet("/assets/assetsentity/Goblins_Barrel_Red.png");
	ImageManager im = new ImageManager();
	private int xMove = 0;
	private int yMove = 0;
	boolean idle = false;
	private int spriteIdle = 1;

	public GoblinBarrel(int x, int y, ID id, EntityType et, EntityClass ec, Game game) {
		super(x, y, id, et, ec, game);
		hp = 50;
		mana = 50;
		stamina = 50;
		speed = 1;
		getImage();
		image = idleRight[0];
		arah = "kanan";
	}
	
	public void tick() {
		x += velX;
		y += velY;
		Collision();
		spriteCounter();
		playerControl();
		checkDistance();
	}
	
	public void checkDistance() {
		xMove += velX;
		yMove += velY;
		if(xMove >= 192) {
	        xMove = 0;
		}
	    if(xMove <= -192) {
	        xMove = 0;
	    }
	    if(yMove <= 192) {
	    	yMove = 0;
	    }
	    if(yMove <= -192) {
	    	yMove = 0;
	    }
	}
	
	public void setAction( ) {
		
		Random random = new Random();
		int i = random.nextInt(100)+1; //pick up a number from 1 to 100
		
		if(i <= 10) {
			setUp(true);
			setDown(false);
			setLeft(false);
			setRight(false);
			idle = false;
			arah = "atas";
		}
		if(i > 10 && i <= 20) {
			setUp(false);
			setDown(true);
			setLeft(false);
			setRight(false);
			idle = false;
			arah = "bawah";	
		}
		if(i > 20 && i <= 30) {
			setLeft(true);
			setRight(false);
			setUp(false);
			setDown(false);
			idle = false;
			arah = "kiri";
		}
		if(i > 30 && i <= 40) {
			setLeft(false);
			setRight(true);
			setUp(false);
			setDown(false);
			idle = false;
			arah = "kanan";
		}
		if(i > 40 && i <=100 && idle == false) {
			setLeft(false);
			setRight(false);
			setUp(false);
			setDown(false);
			idle = true;
		}
		
	}

	public void render(Graphics g) {
		animatedSprite();
		g.drawImage(image, x, y, null);
		
	}

	public void Collision() {
		for (int i = 0; i < game.tryWorld.objectLayer.get(0).size(); i++) {
			GameObject temp = game.tryWorld.objectLayer.get(0).get(i);
			if(getBound().intersects(temp.getBound()) && temp.getID() == ID.Block && temp.highGround == false){
				x += velX * -1;
				y += velY * -1;
			}
			if(temp.getID() == ID.Entity) {
				Entity selfcol = (Entity) temp;
				if(getBound().intersects(selfcol.getBound()) && selfcol.getEntityType() == EntityType.Player) {
					x += velX * -1;
					y += velY * -1;
				}
			}
		}
	}
	
	public void animatedSprite(){
		if(!idle) {
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
					break;
				}
				
			}else {
				switch (arah) {
				case "atas": 
					if(spriteNum == 1) {
						image = idleUp[0];
					}
					break;
					
				case "bawah":
					if(spriteNum == 1) {
						image = idleDown[0];
					}
					break;
				case "kanan":
					if(spriteNum == 1) {
						image = idleRight[0];
					}
					break;
				case "kiri":
					if(spriteNum == 1) {
						image = idleLeft[0];
					}
					break;
				}
			}
		}
		else if(idle) {
			if(spriteIdle == 1) {
				image = idletransitionIn[0];
			}
			if(spriteIdle == 2) {
				image = idletransitionIn[1];
			}
			if(spriteIdle == 3) {
				image = idletransitionIn[2];
			}
			if(spriteIdle == 4) {
				image = idletransitionIn[3];
			}
			if(spriteIdle == 5) {
				image = idletransitionIn[4];
			}
			if(spriteIdle == 6) {
				image = idletransitionIn[5];
			}
		}
	}

	public void getImage() {
		try {
			
			runDown[0] = im.scaledImage(ss.grabImage(1, 5, 128, 128), 128,128);
			runDown[1] = im.scaledImage(ss.grabImage(2, 5, 128, 128), 128,128);
			runDown[2] = im.scaledImage(ss.grabImage(3, 5, 128, 128), 128,128);
						
			runUp[0] = im.scaledImage(ss.grabImage(4, 5, 128, 128), 128,128);
			runUp[1] = im.scaledImage(ss.grabImage(5, 5, 128, 128), 128,128);
			runUp[2] = im.scaledImage(ss.grabImage(6, 5, 128, 128), 128,128);
						
			runRight[0] = im.scaledImage(ss.grabImage(1, 5, 128, 128), 128,128);
			runRight[1] = im.scaledImage(ss.grabImage(2, 5, 128, 128), 128,128);
			runRight[2] = im.scaledImage(ss.grabImage(3, 5, 128, 128), 128,128);
			
			runLeft[0] = im.scaledImage(ss.grabImage(4, 5, 128, 128), 128,128);
			runLeft[1] = im.scaledImage(ss.grabImage(5, 5, 128, 128), 128,128);
			runLeft[2] = im.scaledImage(ss.grabImage(6, 5, 128, 128), 128,128);
									
			idleDown[0] = im.scaledImage(ss.grabImage(1, 1, 128, 128), 128,128);
			idleLeft[0] = im.scaledImage(ss.grabImage(1, 1, 128, 128), 128,128);
			idleRight[0] = im.scaledImage(ss.grabImage(1, 1, 128, 128), 128,128);
			idleUp[0] = im.scaledImage(ss.grabImage(1, 1, 128, 128), 128,128);
			
			idle2Down[0] = im.scaledImage(ss.grabImage(1, 3, 128, 128), 128,128);
			idle2Left[0] = im.scaledImage(ss.grabImage(1, 3, 128, 128), 128,128);
			idle2Right[0] = im.scaledImage(ss.grabImage(1, 3, 128, 128), 128,128);
			idle2Up[0] = im.scaledImage(ss.grabImage(1, 3, 128, 128), 128,128);
			
			idletransitionIn[0] = im.scaledImage(ss.grabImage(1, 2, 128, 128), 128,128);
			idletransitionIn[1] = im.scaledImage(ss.grabImage(2, 2, 128, 128), 128,128);
			idletransitionIn[2] = im.scaledImage(ss.grabImage(3, 2, 128, 128), 128,128);
			idletransitionIn[3] = im.scaledImage(ss.grabImage(4, 2, 128, 128), 128,128);
			idletransitionIn[4] = im.scaledImage(ss.grabImage(5, 2, 128, 128), 128,128);
			idletransitionIn[5] = im.scaledImage(ss.grabImage(6, 2, 128, 128), 128,128);
			
			idletransitionOut[0] = im.scaledImage(ss.grabImage(1, 2, 128, 128), 128,128);
			idletransitionOut[1] = im.scaledImage(ss.grabImage(2, 2, 128, 128), 128,128);
			idletransitionOut[2] = im.scaledImage(ss.grabImage(3, 2, 128, 128), 128,128);
			idletransitionOut[3] = im.scaledImage(ss.grabImage(4, 2, 128, 128), 128,128);
			idletransitionOut[4] = im.scaledImage(ss.grabImage(5, 2, 128, 128), 128,128);
			idletransitionOut[5] = im.scaledImage(ss.grabImage(6, 2, 128, 128), 128,128);
						
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Rectangle getBound() {
		return new Rectangle(x+32, y +64, 64, 48 );
	}

	public Rectangle renderOrder() {
		return new Rectangle(x+32, y +64, 64, 48 );
	}

	public Rectangle getSize() {
		return new Rectangle(x, y, image.getWidth(), image.getHeight());
	}

	public void hit() {
		throw new UnsupportedOperationException("Unimplemented method 'hit'");
	}
	
	public void spriteCounter(){
		spriteCounter++;
		if(!idle) {
			if(spriteCounter > 6) {
				if(spriteNum == 1) {
					spriteNum =2;
				}else if(spriteNum ==2) {
					spriteNum =3;
				}else if(spriteNum ==3) {
					spriteNum =1;
					setAction();
				}
				spriteCounter =0;
			}
		}
		else if(idle) {
			if(spriteCounter > 10) {
				if(spriteIdle == 1) {
					spriteIdle =2;
				}else if(spriteIdle ==2) {
					spriteIdle =3;
				}else if(spriteIdle ==3) {
					spriteIdle =4;
				}else if(spriteIdle ==4) {
					spriteIdle =5;
				}else if(spriteIdle ==5) {
					spriteIdle =6;
				}else if(spriteIdle ==6) {
					spriteIdle =7;
				}else if(spriteIdle ==7) {
					spriteIdle =1;
					idle = false;
					setAction();
				}
				
				spriteCounter =0;
			}
		}
		
	}
}
