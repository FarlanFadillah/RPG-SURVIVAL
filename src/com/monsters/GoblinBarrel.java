package com.monsters;

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
import com.quadTree.Point;
import com.tile.ImageManager;

public class GoblinBarrel extends Entity{
	
	public SpriteSheet ss = new SpriteSheet("/assets/assetsentity/Goblins_Barrel_Red.png");
	public SpriteSheet ssdead = new SpriteSheet("/assets/assetsentity/Dead.png");
	ImageManager im = new ImageManager();
	private int xMove = 0;
	private int yMove = 0;
	boolean idlein = true;
	boolean idleout = false;
	private int spriteIdleIn = 1;
	private int spriteIdleOut = 1;
	private int spriteAttack1 = 1;

	public GoblinBarrel(int x, int y, ID id, EntityType et, EntityClass ec, Game game) {
		super(x, y, id, et, ec, game);
		hp = 50;
		mana = 50;
		stamina = 50;
		speed = 1;
		start = game.second;
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
		attackCollision();
		if(hp <= 0) {
			speed = 0;
			dead = true;
		}
		if(hp < 50) {
			attack1 = true;
			//onPath = true;
		}
	}
	
	public void checkDistance() {
		xMove += velX;
		yMove += velY;
		setAction(2);
		if(xMove >= 128) {
	        xMove = 0;
		}
	    if(xMove <= -128) {
	        xMove = 0;
	    }
	    if(yMove <= 128) {
	    	yMove = 0;
	    }
	    if(yMove <= -128) {
	    	yMove = 0;
	    }
	}
	
	public void setAction(int delay) {
		if(onPath == true) {
			int goalCol = (game.tryWorld.player.getX()/64);
			int goalRow = (game.tryWorld.player.getY()/64);
			
			searchPath(goalCol, goalRow);
			
		}
		else {
			Random random = new Random();
			int i = random.nextInt(100)+1; //pick up a number from 1 to 100
			stop = game.second;
			if(stop - start >= delay){
				if(i <= 10) {
					setUp(true);
					setDown(false);
					setLeft(false);
					setRight(false);
					if(idlein){
						idleout = true;
					}
					
					spriteIdleIn = 1;
					arah = "atas";
				}
				if(i > 10 && i <= 20) {
					setUp(false);
					setDown(true);
					setLeft(false);
					setRight(false);
					if(idlein){
						idleout = true;
					}
					
					spriteIdleIn = 1;
					arah = "bawah";	
				}
				if(i > 20 && i <= 30) {
					setLeft(true);
					setRight(false);
					setUp(false);
					setDown(false);
					if(idlein){
						idleout = true;
					}
					
					spriteIdleIn = 1;
					arah = "kiri";
				}
				if(i > 30 && i <= 40) {
					setLeft(false);
					setRight(true);
					setUp(false);
					setDown(false);
					
					if(idlein){
						idleout = true;
					}
					spriteIdleIn = 1;
					arah = "kanan";
				}
				if(i > 40 && i <=100 && idlein == false) {
					setLeft(false);
					setRight(false);
					setUp(false);
					setDown(false);
					if(!idlein){
						idlein = true;
						spriteIdleOut = 1;
					}
				}
				start = stop;
			}
		}
		
	}

	public void render(Graphics g) {
		animatedSprite();
		g.drawImage(image, x, y, null);
		//g.fillRect(getBound().x, getBound().y, getBound().width, getBound().height);
		
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
	
	public void attackCollision() {
		for (int i = 0; i < game.tryWorld.objects.size(); i++) {
			GameObject temp = game.tryWorld.objects.get(i);
			if(this != temp && attackArea.intersects(temp.getBound()) &&temp.getID() == ID.Entity) {
					temp.hp -= 100;
					attackArea = new Rectangle(0, 0, 0, 0);
			}

		}
		
	}
	
	public void animatedSprite(){
		if(dead == false) {
			if(attack1 == false) {
				if(!idlein) {
					if(isUp() || isDown()|| isRight() || isLeft()) {
						switch (arah) {
						case "atas": 
							if(spriteNum == 1) {image = runUp[0];}
							if(spriteNum == 2) {image = runUp[1];}
							if(spriteNum == 3) {image = runUp[2];}	break;
						case "bawah":
							if(spriteNum == 1) {image = runDown[0];}
							if(spriteNum == 2) {image = runDown[1];}
							if(spriteNum == 3) {image = runDown[2];}	break;
						case "kanan":
							if(spriteNum == 1) {image = runRight[0];}
							if(spriteNum == 2) {image = runRight[1];}
							if(spriteNum == 3) {image = runRight[2];}	break;
						case "kiri":
							if(spriteNum == 1) {image = runLeft[0];}
							if(spriteNum == 2) {image = runLeft[1];}
							if(spriteNum == 3) {image = runLeft[2];}	break;
						}
					}
					else {
						switch (arah) {
						case "atas": 
							if(spriteNum == 1) {image = idleUp[0];}	break;
						case "bawah":
							if(spriteNum == 1) {image = idleDown[0];}	break;
						case "kanan":
							if(spriteNum == 1) {image = idleRight[0];}	break;
						case "kiri":
							if(spriteNum == 1) {image = idleLeft[0];}	break;
						}
					}
				}
				
				else if(idlein) {
					if(spriteIdleIn == 1) {image = idletransitionIn[0];}
					if(spriteIdleIn == 2) {image = idletransitionIn[1];}
					if(spriteIdleIn == 3) {image = idletransitionIn[2];}
					if(spriteIdleIn == 4) {image = idletransitionIn[3];}
					if(spriteIdleIn == 5) {image = idletransitionIn[4];}
					if(spriteIdleIn == 6) {image = idletransitionIn[5];}

					if(idleout) {
						if(spriteIdleOut == 1) {image = idletransitionOut[0];}
						if(spriteIdleOut == 2) {image = idletransitionOut[1];}
						if(spriteIdleOut == 3) {image = idletransitionOut[2];}
						if(spriteIdleOut == 4) {image = idletransitionOut[3];}
						if(spriteIdleOut == 5) {image = idletransitionOut[4];}
						if(spriteIdleOut == 6) {image = idletransitionOut[5];}
					}
				}
			} else if(attack1 == true) {
				if(spriteAttack1 == 1) {image = attackBarrel[0];}
				if(spriteAttack1 == 2) {image = attackBarrel[1];}
				if(spriteAttack1 == 3) {image = attackBarrel[2];}
				if(spriteAttack1 == 4) {image = attackBarrel[0];}
				if(spriteAttack1 == 5) {image = attackBarrel[1];}
				if(spriteAttack1 == 6) {image = attackBarrel[2];}
				if(spriteAttack1 == 7) {image = attackBarrel[0];}
				if(spriteAttack1 == 8) {image = attackBarrel[1];}
				if(spriteAttack1 == 9) {image = attackBarrel[2]; 
				hp = 0;}

			}
			
		}else {
			if(spriteDead == 1) {image = death[0];}
			if(spriteDead == 2) {image = death[1];}
			if(spriteDead == 3) {image = death[2];}
			if(spriteDead == 4) {image = death[3];}
			if(spriteDead == 5) {image = death[4];}
			if(spriteDead == 6) {image = death[5];}
			if(spriteDead == 7) {image = death[6];}
			if(spriteDead == 8) {image = death[7];}
			if(spriteDead == 9) {image = death[8];}
			if(spriteDead == 10) {image = death[9];}
			if(spriteDead == 11) {image = death[10];}
			if(spriteDead == 12) {image = death[11];}
			if(spriteDead == 13) {image = death[12];}
			if(spriteDead == 14) {image = death[13];}
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
			
			idletransitionIn[0] = im.scaledImage(ss.grabImage(1, 4, 128, 128), 128,128);
			idletransitionIn[1] = im.scaledImage(ss.grabImage(2, 4, 128, 128), 128,128);
			idletransitionIn[2] = im.scaledImage(ss.grabImage(3, 4, 128, 128), 128,128);
			idletransitionIn[3] = im.scaledImage(ss.grabImage(4, 4, 128, 128), 128,128);
			idletransitionIn[4] = im.scaledImage(ss.grabImage(5, 4, 128, 128), 128,128);
			idletransitionIn[5] = im.scaledImage(ss.grabImage(6, 4, 128, 128), 128,128);
			
			idletransitionOut[0] = im.scaledImage(ss.grabImage(1, 2, 128, 128), 128,128);
			idletransitionOut[1] = im.scaledImage(ss.grabImage(2, 2, 128, 128), 128,128);
			idletransitionOut[2] = im.scaledImage(ss.grabImage(3, 2, 128, 128), 128,128);
			idletransitionOut[3] = im.scaledImage(ss.grabImage(4, 2, 128, 128), 128,128);
			idletransitionOut[4] = im.scaledImage(ss.grabImage(5, 2, 128, 128), 128,128);
			idletransitionOut[5] = im.scaledImage(ss.grabImage(6, 2, 128, 128), 128,128);
			
			attackBarrel[0] = im.scaledImage(ss.grabImage(3, 6, 128, 128), 128,128);
			attackBarrel[1] = im.scaledImage(ss.grabImage(2, 6, 128, 128), 128,128);
			attackBarrel[2] = im.scaledImage(ss.grabImage(1, 6, 128, 128), 128,128);
			
			death[0] = im.scaledImage(ssdead.grabImage(1, 1, 128, 128), 128, 128);
			death[1] = im.scaledImage(ssdead.grabImage(2, 1, 128, 128), 128, 128);
			death[2] = im.scaledImage(ssdead.grabImage(3, 1, 128, 128), 128, 128);
			death[3] = im.scaledImage(ssdead.grabImage(4, 1, 128, 128), 128, 128);
			death[4] = im.scaledImage(ssdead.grabImage(5, 1, 128, 128), 128, 128);
			death[5] = im.scaledImage(ssdead.grabImage(6, 1, 128, 128), 128, 128);
			death[6] = im.scaledImage(ssdead.grabImage(7, 1, 128, 128), 128, 128);
			death[7] = im.scaledImage(ssdead.grabImage(1, 2, 128, 128), 128, 128);
			death[8] = im.scaledImage(ssdead.grabImage(2, 2, 128, 128), 128, 128);
			death[9] = im.scaledImage(ssdead.grabImage(3, 2, 128, 128), 128, 128);
			death[10] = im.scaledImage(ssdead.grabImage(4, 2, 128, 128), 128, 128);
			death[11] = im.scaledImage(ssdead.grabImage(5, 2, 128, 128), 128, 128);
			death[12] = im.scaledImage(ssdead.grabImage(6, 2, 128, 128), 128, 128);
			death[13] = im.scaledImage(ssdead.grabImage(7, 2, 128, 128), 128, 128);
						
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Rectangle getBound() {
		return new Rectangle(x+45, y +51, 38, 40 );
	}

	public Rectangle renderOrder() {
		return new Rectangle(x+45, y +51, 38, 40 );
	}

	public Rectangle getSize() {
		return new Rectangle(x, y, image.getWidth(), image.getHeight());
	}

	public void hit() {
		throw new UnsupportedOperationException("Unimplemented method 'hit'");
	}
	
	public void spriteCounter(){
		spriteCounter++;
		if(dead == false) {
			if(attack1 == false) {
				if(!idlein) {
					if(spriteCounter > 6) {
						if(spriteNum == 1) {spriteNum =2;}
						else if(spriteNum ==2) {spriteNum =3;}
						else if(spriteNum ==3) {spriteNum =1;}
						spriteCounter =0;
					}
				}
				
				else if(idlein) {
					if(spriteCounter > 10 && !idleout) {
						if(spriteIdleIn == 1) {spriteIdleIn =2;}
						else if(spriteIdleIn ==2) {spriteIdleIn =3;}
						else if(spriteIdleIn ==3) {spriteIdleIn =4;}
						else if(spriteIdleIn ==4) {spriteIdleIn =5;}
						else if(spriteIdleIn ==5) {spriteIdleIn =6;}
						else if(spriteIdleIn ==6) {spriteIdleIn =6;}
						spriteCounter =0;
					}
					if(idleout){
						if(spriteCounter > 10) {
							if(spriteIdleOut == 1) {spriteIdleOut =2;}
							else if(spriteIdleOut ==2) {spriteIdleOut =3;}
							else if(spriteIdleOut ==3) {spriteIdleOut =4;}
							else if(spriteIdleOut ==4) {spriteIdleOut =5;}
							else if(spriteIdleOut ==5) {spriteIdleOut =6;}
							else if(spriteIdleOut ==6) {spriteIdleOut =6; idleout = false; idlein = false;}
							spriteCounter =0;
						}
					}
				}
			}
			
			else if(attack1 == true) {
				//Counter Attack1
				if(spriteCounter > 20) {
					if(spriteAttack1 == 1) {spriteAttack1 =2;
						speed = 0;
					}
					else if(spriteAttack1 ==2) {spriteAttack1 =3;}
					else if(spriteAttack1 ==3) {spriteAttack1 =4;}
					else if(spriteAttack1 ==4) {spriteAttack1 =5;}
					else if(spriteAttack1 ==5) {spriteAttack1 =6;}
					else if(spriteAttack1 ==6) {spriteAttack1 =7;}
					else if(spriteAttack1 ==7) {spriteAttack1 =8;}
					else if(spriteAttack1 ==8) {spriteAttack1 =9;}
					else if(spriteAttack1 ==9) {spriteAttack1 =1;
					
						//Attack Collision On not fixed
						attackArea.x = x+64;
						attackArea.y = y+64;
						attackArea.width  = 128;
						attackArea.height = 40;
						
						attack1 = false;
						
						//Attack Collision Off
						attackArea = new Rectangle(0, 0, 0, 0);
					}
					spriteCounter =0;

				}
			}
			
		}
		
		else if(dead == true) {
			if(spriteCounter > 8) {
				if(spriteDead == 1) {spriteDead = 2;}
				else if(spriteDead == 2) {spriteDead = 3;}
				else if(spriteDead == 3) {spriteDead = 4;}
				else if(spriteDead == 4) {spriteDead = 5;}
				else if(spriteDead == 5) {spriteDead = 6;}
				else if(spriteDead == 6) {spriteDead = 7;}
				else if(spriteDead == 7) {spriteDead = 8;}
				else if(spriteDead == 8) {spriteDead = 9;}
				else if(spriteDead == 9) {spriteDead = 10;}
				else if(spriteDead == 10) {spriteDead = 11;}
				else if(spriteDead == 11) {spriteDead = 12;}
				else if(spriteDead == 12) {spriteDead = 13;}
				else if(spriteDead == 13) {spriteDead = 14; game.tryWorld.qt.remove(game.tryWorld.qt.search(new Point(this.x, this.y)));	game.tryWorld.entity.remove(this);}
				spriteCounter =0;
			}
		}
	}

	public void playerControl(){
		if(isUp()) {
			velY=-speed;
			arah = "atas"; 
		}
		else velY=0;
		
		if(isDown()) {
			velY=speed;
			arah = "bawah"; 
		}
		else if(!isUp())velY=0;
		
		if(isRight()) {
			velX=speed;
			arah = "kanan";
		}
		else if(!isLeft())velX=0;
		
		if(isLeft()) {
			velX=-speed;
			arah = "kiri";
		}
		else if(!isRight()) velX=0;
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
}
