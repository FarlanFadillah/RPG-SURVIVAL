package com.npc;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.filehandler.SpriteSheet;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Entity;
import com.obj.GameObject;
import com.tile.ImageManager;

public class NormalNPC extends Entity{
	
	public SpriteSheet ss = new SpriteSheet("/assets/assetsentity/Pawn.png");
	ImageManager im = new ImageManager();
	private int xMove = 0;
	private int yMove = 0;
	public int start, stop = 0;

	public NormalNPC(int x, int y, ID id, EntityType et, EntityClass ec, Game game) {
		super(x, y, id, et, ec, game);
		hp = 50;
		mana = 50;
		stamina = 50;
		speed = 4;
		start = game.second;
		getImage();
		image = idleRight[0];
		arah = "kanan";
	}
	
	public void checkDistance() {
		xMove += velX;
		yMove += velY;
		setAction(40);
		if(xMove >= 64) {
	        xMove = 0;
		}
	    if(xMove <= -64) {
	        xMove = 0;
	    }
	    if(yMove <= 64) {
	    	yMove = 0;
	    }
	    if(yMove <= -64) {
	    	yMove = 0;
	    }
	}
	
	public void setAction(int delay) {
		
		Random random = new Random();
		int i = random.nextInt(100)+1; //pick up a number from 1 to 100
		stop = game.second;
		if(stop - start >= delay) {
			if(i <= 15) {
				setUp(true);
				setDown(false);
				setLeft(false);
				setRight(false);
				arah = "atas";
			}
			if(i > 15 && i <= 30) {
				setUp(false);
				setDown(true);
				setLeft(false);
				setRight(false);
				arah = "bawah";	
			}
			if(i > 30 && i <= 45) {
				setLeft(true);
				setRight(false);
				setUp(false);
				setDown(false);
				arah = "kiri";
			}
			if(i > 45 && i <= 60) {
				setLeft(false);
				setRight(true);
				setUp(false);
				setDown(false);
				arah = "kanan";
			}
			if(i > 60 && i <=100) {
				setLeft(false);
				setRight(false);
				setUp(false);
				setDown(false);
			}
			start = stop;
		}
	}

	public void render(Graphics g) {
		animatedSprite();
		g.drawImage(image, x, y, null);
	}

	public void Collision() {
		for (int i = 0; i < game.tryWorld.objects.size(); i++) {
			GameObject temp = game.tryWorld.objects.get(i);
			if(getBound().intersects(temp.getBound()) && temp.getID() == ID.Block && temp.highGround == false){
				x += velX * -1;
				y += velY * -1;
			}
			if(this != temp && getBound().intersects(temp.getBound()) &&temp.getID() == ID.Entity) {
				x += velX * -1;
				y += velY * -1;
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
			
			runDown[0] = im.scaledImage(ss.grabImage(1, 2, 64, 64), 64,64);
			runDown[1] = im.scaledImage(ss.grabImage(2, 2, 64, 64), 64,64);
			runDown[2] = im.scaledImage(ss.grabImage(3, 2, 64, 64), 64,64);
			runDown[3] = im.scaledImage(ss.grabImage(4, 2, 64, 64), 64,64);
			runDown[4] = im.scaledImage(ss.grabImage(5, 2, 64, 64), 64,64);
			runDown[5] = im.scaledImage(ss.grabImage(6, 2, 64, 64), 64,64);
			
			runUp[0] = im.scaledImage(ss.grabImage(1, 3, 64, 64), 64,64);
			runUp[1] = im.scaledImage(ss.grabImage(2, 3, 64, 64), 64,64);
			runUp[2] = im.scaledImage(ss.grabImage(3, 3, 64, 64), 64,64);
			runUp[3] = im.scaledImage(ss.grabImage(4, 3, 64, 64), 64,64);
			runUp[4] = im.scaledImage(ss.grabImage(5, 3, 64, 64), 64,64);
			runUp[5] = im.scaledImage(ss.grabImage(6, 3, 64, 64), 64,64);
			
			runRight[0] = im.scaledImage(ss.grabImage(1, 2, 64, 64), 64,64);
			runRight[1] = im.scaledImage(ss.grabImage(2, 2, 64, 64), 64,64);
			runRight[2] = im.scaledImage(ss.grabImage(3, 2, 64, 64), 64,64);
			runRight[3] = im.scaledImage(ss.grabImage(4, 2, 64, 64), 64,64);
			runRight[4] = im.scaledImage(ss.grabImage(5, 2, 64, 64), 64,64);
			runRight[5] = im.scaledImage(ss.grabImage(6, 2, 64, 64), 64,64);
			
			runLeft[0] = im.scaledImage(ss.grabImage(1, 3, 64, 64), 64,64);
			runLeft[1] = im.scaledImage(ss.grabImage(2, 3, 64, 64), 64,64);
			runLeft[2] = im.scaledImage(ss.grabImage(3, 3, 64, 64), 64,64);
			runLeft[3] = im.scaledImage(ss.grabImage(4, 3, 64, 64), 64,64);
			runLeft[4] = im.scaledImage(ss.grabImage(5, 3, 64, 64), 64,64);
			runLeft[5] = im.scaledImage(ss.grabImage(6, 3, 64, 64), 64,64);
						
			idleDown[0] = im.scaledImage(ss.grabImage(1, 1, 64, 64), 64,64);
			idleDown[1] = im.scaledImage(ss.grabImage(2, 1, 64, 64), 64,64);
			idleDown[2] = im.scaledImage(ss.grabImage(3, 1, 64, 64), 64,64);
			idleDown[3] = im.scaledImage(ss.grabImage(4, 1, 64, 64), 64,64);
			idleDown[4] = im.scaledImage(ss.grabImage(5, 1, 64, 64), 64,64);
			idleDown[5] = im.scaledImage(ss.grabImage(6, 1, 64, 64), 64,64);
			
			idleLeft[0] = im.scaledImage(ss.grabImage(1, 1, 64, 64), 64,64);
			idleLeft[1] = im.scaledImage(ss.grabImage(2, 1, 64, 64), 64,64);
			idleLeft[2] = im.scaledImage(ss.grabImage(3, 1, 64, 64), 64,64);
			idleLeft[3] = im.scaledImage(ss.grabImage(4, 1, 64, 64), 64,64);
			idleLeft[4] = im.scaledImage(ss.grabImage(5, 1, 64, 64), 64,64);
			idleLeft[5] = im.scaledImage(ss.grabImage(6, 1, 64, 64), 64,64);

			idleRight[0] = im.scaledImage(ss.grabImage(1, 1, 64, 64), 64,64);
			idleRight[1] = im.scaledImage(ss.grabImage(2, 1, 64, 64), 64,64);
			idleRight[2] = im.scaledImage(ss.grabImage(3, 1, 64, 64), 64,64);
			idleRight[3] = im.scaledImage(ss.grabImage(4, 1, 64, 64), 64,64);
			idleRight[4] = im.scaledImage(ss.grabImage(5, 1, 64, 64), 64,64);
			idleRight[5] = im.scaledImage(ss.grabImage(6, 1, 64, 64), 64,64);
			
			idleUp[0] = im.scaledImage(ss.grabImage(1, 1, 64, 64), 64,64);
			idleUp[1] = im.scaledImage(ss.grabImage(2, 1, 64, 64), 64,64);
			idleUp[2] = im.scaledImage(ss.grabImage(3, 1, 64, 64), 64,64);
			idleUp[3] = im.scaledImage(ss.grabImage(4, 1, 64, 64), 64,64);
			idleUp[4] = im.scaledImage(ss.grabImage(5, 1, 64, 64), 64,64);
			idleUp[5] = im.scaledImage(ss.grabImage(6, 1, 64, 64), 64,64);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Rectangle getBound() {
		return new Rectangle(x+0, y +32, 64, 32 );
	}

	public Rectangle renderOrder() {
		return new Rectangle(x+0, y +32, 64, 32 );
	}

	public Rectangle getSize() {
		return new Rectangle(x, y, image.getWidth(), image.getHeight());
	}

	@Override
    public void hit(int damage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hit'");
    }
	
	public void spriteCounter(){

		spriteCounter++;
		if(spriteCounter > 6) {
			if(spriteNum == 1) {
				spriteNum =2;
			}else if(spriteNum ==2) {
				spriteNum =3;
			}else if(spriteNum ==3) {
				spriteNum =4;
			}else if(spriteNum ==4) {
				spriteNum =5;
			}else if(spriteNum ==5) {
				spriteNum =6;
			}else if(spriteNum ==6) {
				spriteNum =1;
				setAction();
			}
			
			spriteCounter =0;
		}
		
	}

	@Override
	public void attacking1(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attacking2(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hitTree() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkTree(MouseEvent e, boolean hitTree) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attack2() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkEquipment(MouseEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'checkEquipment'");
	}

	@Override
	public void openChest(MouseEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'openChest'");
	}

}
