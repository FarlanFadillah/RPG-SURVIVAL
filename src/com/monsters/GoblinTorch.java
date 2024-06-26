package com.monsters;

import java.awt.Color;
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

public class GoblinTorch extends Entity{
	
	public SpriteSheet ss = new SpriteSheet("/assets/assetsentity/Goblins_Torch_Red.png");
	public SpriteSheet ssdead = new SpriteSheet("/assets/assetsentity/Dead.png");
	ImageManager im = new ImageManager();
	private int xMove = 0;
	private int yMove = 0;
	private int spriteAttack1 = 1;
	public int start, stop = 0;

	public GoblinTorch(int x, int y, ID id, EntityType et, EntityClass ec, Game game) {
		super(x, y, id, et, ec, game);
		hp = 50;
		mana = 50;
		stamina = 50;
		speed = 4;
		start = game.second;
		getImage();
		image = idleRight[0];
		arahAttack = "kanan";
		arah = "kanan";
	}
	
	public void tick() {
		if(attack1 == false) {
			speed = 4;
		}else if(attack1 == true) {
			speed = 0;
		}
		
		x += velX;
		y += velY;
		Collision();
		spriteCounter();
		playerControl();
		//checkDistance();
		//attackCollision();
		//rangeCollision();
		
		if(hp <= 0) {
			speed = 0;
			dead = true;
		}
		if(hp < 50) {
			attack1 = true;
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
		//if(onPath == true) {
		//	int goalCol = (game.tryWorld.player.getX()/64);
		//	int goalRow = (game.tryWorld.player.getY()/64);
		//	
		//	searchPath(goalCol, goalRow);	
		//}
		int attackRangeX = Math.abs(x - game.tryWorld.player.getX());
		int attackRangeY = Math.abs(y - game.tryWorld.player.getY());
		
		if(attackRangeX <= 64*3 && attackRangeY <= 64*3 && attack1 == false) {
			stop = game.second;
			if(stop - start >= delay) {
				if(game.tryWorld.player.getX() <= x && x - game.tryWorld.player.getX() <= 64*3) {
					setLeft(true);
					setRight(false);
					
					arah = "kiri";
					
				} else if(game.tryWorld.player.getX() >= x && game.tryWorld.player.getX() - x <= 64*3) {
					setLeft(false);
					setRight(true);
					
					arah = "kanan";
				}
				
				if(game.tryWorld.player.getY() <= y && y - game.tryWorld.player.getY() <= 64*3) {
					setUp(true);
					setDown(false);
					
					arah = "atas";
					
				} else if(game.tryWorld.player.getY() >= y && game.tryWorld.player.getY() - y <= 64*3) {
					setUp(false);
					setDown(true);
					
					arah = "bawah";
				}
			}
			
			if(attackRangeX <= 40 && attackRangeY <= 40) {
				attack1 = true;
				stop = game.second;
				if(stop - start >= delay) {
					if(game.tryWorld.player.getX() <= x && x - game.tryWorld.player.getX() <= 40) {
						setLeft(true);
						setRight(false);
						
						arahAttack = "kiri";
						
					} else if(game.tryWorld.player.getX() >= x && game.tryWorld.player.getX() - x <= 40) {
						setLeft(false);
						setRight(true);
						
						arahAttack = "kanan";
					}
					
					if(game.tryWorld.player.getY() <= y && y - game.tryWorld.player.getY() <= 40) {
						setUp(true);
						setDown(false);
						
						arahAttack = "atas";
						
					} else if(game.tryWorld.player.getY() >= y && game.tryWorld.player.getY() - y <= 40) {
						setUp(false);
						setDown(true);
						
						arahAttack = "bawah";
					}
				}
			}
			
		} else {
			checkArah();
			Random random = new Random();
			int i = random.nextInt(100)+1; //pick up a number from 1 to 100
			stop = game.second;
			if(stop - start >= delay){
				if(i <= 20) {
					setUp(true);
					setDown(false);
					setLeft(false);
					setRight(false);
					
					arah = "atas";
					
					
				}
				if(i > 20 && i <= 40) {
					setUp(false);
					setDown(true);
					setLeft(false);
					setRight(false);
					
					arah = "bawah";
					
				}
				if(i > 40 && i <= 60) {
					setLeft(true);
					setRight(false);
					setUp(false);
					setDown(false);
					
					arah = "kiri";
					
				}
				if(i > 60 && i <= 80) {
					setLeft(false);
					setRight(true);
					setUp(false);
					setDown(false);
					
					arah = "kanan";
					
				}
				if(i > 80 && i <=100) {
					setLeft(false);
					setRight(false);
					setUp(false);
					setDown(false);
					
				}
				start = stop;
			}
		}
		
	}
	
	public void checkArah() {
		switch(arah) {
		case "atas": 
			rangeArea.x = x+48;
			rangeArea.y = y+64;
			rangeArea.width  = 80;
			rangeArea.height = 16; break;
		case "bawah":
			rangeArea.x = x+48;
			rangeArea.y = y+128;
			rangeArea.width  = 80;
			rangeArea.height = 16; break;
		case "kanan":
			rangeArea.x = x+128;
			rangeArea.y = y+64;
			rangeArea.width  = 16;
			rangeArea.height = 64; break;
		case "kiri":
			rangeArea.x = x+48;
			rangeArea.y = y+64;
			rangeArea.width  = 16;
			rangeArea.height = 64; break;
		}
	}
	
	public void setArah(String arah2) {
		switch(arah2) {
		case "atas":
			setUp(false);
			setDown(true);
			setLeft(false);
			setRight(false);
			arah = "bawah"; break;
		case "bawah": 
			setUp(true);
			setDown(false);
			setLeft(false);
			setRight(false);
			arah = "atas"; break;
		case "kanan": 
			setUp(false);
			setDown(false);
			setLeft(true);
			setRight(false);
			arah = "kiri"; break;
		case "kiri": 
			setUp(false);
			setDown(false);
			setLeft(false);
			setRight(true);
			arah = "kanan"; break;
		}
	}

	public void render(Graphics g) {
		animatedSprite();
		g.drawImage(image, x, y, null);
		// g.fillRect(getBound().x, getBound().y, getBound().width, getBound().height);
		// g.fillRect(attackArea.x, attackArea.y, attackArea.width, attackArea.height);
		// g.fillRect(rangeArea.x, rangeArea.y, rangeArea.width, rangeArea.height);
		g.setColor(Color.red);
		g.fillRect(x+(image.getWidth()/2)-25, y+image.getHeight()-48, (int)((hp/50)*50), 5);
		g.setColor(Color.BLACK);
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
					temp.hp -= 10;
					attackArea = new Rectangle(0, 0, 0, 0);
			}

		}
		
	}
	
	public void rangeCollision() {
		
		for (int i = 0; i < game.tryWorld.objects.size(); i++) {
			GameObject temp = game.tryWorld.objects.get(i);
			
			if(this != temp && rangeArea.intersects(temp.getBound())) {
				Random random = new Random();
				int z = random.nextInt(2); //pick up a number from 1 to 100
				switch(arah) {
				case "atas":
					String [] arah2 = {"bawah", "kanan", "kiri"};
					setArah(arah2[z]);
					break;
				case "bawah": 
					String [] arah21 = {"atas", "kanan", "kiri"};
					setArah(arah21[z]);
					break;
				case "kanan":
					String [] arah22 = {"atas", "bawah", "kiri"};
					setArah(arah22[z]);
					break;
				case "kiri":
					String [] arah23 = {"atas", "bawah", "kanan"};
					setArah(arah23[z]);
					break;
				}
				rangeArea = new Rectangle(0, 0, 0, 0);
				return;
			}

		}
		
	}
	
	public void animatedSprite(){
		if(dead == false) {
			if(attack1 == false) {
				if(isUp() || isDown()|| isRight() || isLeft()) {
					//Move Animations
					switch (arah) {
					case "atas": 
						if(spriteNum == 1) {image = runUp[0];}
						if(spriteNum == 2) {image = runUp[1];}
						if(spriteNum == 3) {image = runUp[2];}
						if(spriteNum == 4) {image = runUp[3];}
						if(spriteNum == 5) {image = runUp[4];}
						if(spriteNum == 6) {image = runUp[5];}	break;
					case "bawah":
						if(spriteNum == 1) {image = runDown[0];}
						if(spriteNum == 2) {image = runDown[1];}
						if(spriteNum == 3) {image = runDown[2];}
						if(spriteNum ==4) {image = runDown[3];}
						if(spriteNum ==5) {image = runDown[4];}
						if(spriteNum ==6) {image = runDown[5];}	break;
					case "kanan":
						if(spriteNum == 1) {image = runRight[0];}
						if(spriteNum == 2) {image = runRight[1];}
						if(spriteNum == 3) {image = runRight[2];}
						if(spriteNum ==4) {image = runRight[3];}
						if(spriteNum ==5) {image = runRight[4];}
						if(spriteNum ==6) {image = runRight[5];}	break;
					case "kiri":
						if(spriteNum == 1) {image = runLeft[0];}
						if(spriteNum == 2) {image = runLeft[1];}
						if(spriteNum == 3) {image = runLeft[2];}
						if(spriteNum ==4) {image = runLeft[3];}
						if(spriteNum ==5) {image = runLeft[4];}
						if(spriteNum ==65) {image = runLeft[5];}	break;
					}
					
				}else {
					//Idle Animations
					switch (arah) {
					case "atas": 
						if(spriteNum == 1) {image = idleUp[0];}
						if(spriteNum == 2) {image = idleUp[1];}
						if(spriteNum == 3) {image = idleUp[2];}
						if(spriteNum ==4) {image = idleUp[3];}
						if(spriteNum ==5) {image = idleUp[4];}
						if(spriteNum ==6) {image = idleUp[5];}	break;
					case "bawah":
						if(spriteNum == 1) {image = idleDown[0];}
						if(spriteNum == 2) {image = idleDown[1];}
						if(spriteNum == 3) {image = idleDown[2];}
						if(spriteNum ==4) {image = idleDown[3];}
						if(spriteNum ==5) {image = idleDown[4];}
						if(spriteNum ==6) {image = idleDown[5];}	break;
					case "kanan":
						if(spriteNum == 1) {image = idleRight[0];}
						if(spriteNum == 2) {image = idleRight[1];}
						if(spriteNum == 3) {image = idleRight[2];}
						if(spriteNum ==4) {image = idleRight[3];}
						if(spriteNum ==5) {image = idleRight[4];}
						if(spriteNum ==6) {image = idleRight[5];}	break;
					case "kiri":
						if(spriteNum == 1) {image = idleLeft[0];}
						if(spriteNum == 2) {image = idleLeft[1];}
						if(spriteNum == 3) {image = idleLeft[2];}
						if(spriteNum ==4) {image = idleLeft[3];}
						if(spriteNum ==5) {image = idleLeft[4];}
						if(spriteNum ==6) {image = idleLeft[5];}	break;
					}
				}
			} else if(attack1 == true) {
				//Attack 1 Animations
				switch (arahAttack) {
				case "atas": 
					if(spriteAttack1 == 1) {image = attack1Up[0];}
					if(spriteAttack1 == 2) {image = attack1Up[1];}
					if(spriteAttack1 == 3) {image = attack1Up[2];}
					if(spriteAttack1 == 4) {image = attack1Up[3];}
					if(spriteAttack1 == 5) {image = attack1Up[4];}
					if(spriteAttack1 == 6) {image = attack1Up[5];}	break;
				case "bawah":
					if(spriteAttack1 == 1) {image = attack1Down[0];}
					if(spriteAttack1 == 2) {image = attack1Down[1];}
					if(spriteAttack1 == 3) {image = attack1Down[2];}
					if(spriteAttack1 ==4) {image = attack1Down[3];}
					if(spriteAttack1 ==5) {image = attack1Down[4];}
					if(spriteAttack1 ==6) {image = attack1Down[5];}	break;
				case "kanan":
					if(spriteAttack1 == 1) {image = attack1Right[0];}
					if(spriteAttack1 == 2) {image = attack1Right[1];}
					if(spriteAttack1 == 3) {image = attack1Right[2];}
					if(spriteAttack1 ==4) {image = attack1Right[3];}
					if(spriteAttack1 ==5) {image = attack1Right[4];}
					if(spriteAttack1 ==6) {image = attack1Right[5];}	break;
				case "kiri":
					if(spriteAttack1 == 1) {image = attack1Left[0];}
					if(spriteAttack1 == 2) {image = attack1Left[1];}
					if(spriteAttack1 == 3) {image = attack1Left[2];}
					if(spriteAttack1 ==4) {image = attack1Left[3];}
					if(spriteAttack1 ==5) {image = attack1Left[4];}
					if(spriteAttack1 ==6) {image = attack1Left[5];}	break;
				}
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
			
			runDown[0] = im.scaledImage(ss.grabImage(1, 2, 192, 192), 192,192);
			runDown[1] = im.scaledImage(ss.grabImage(2, 2, 192, 192), 192,192);
			runDown[2] = im.scaledImage(ss.grabImage(3, 2, 192, 192), 192,192);
			runDown[3] = im.scaledImage(ss.grabImage(4, 2, 192, 192), 192,192);
			runDown[4] = im.scaledImage(ss.grabImage(5, 2, 192, 192), 192,192);
			runDown[5] = im.scaledImage(ss.grabImage(6, 2, 192, 192), 192,192);
			
			runUp[0] = im.scaledImage(ss.grabImage(1, 6, 192, 192), 192,192);
			runUp[1] = im.scaledImage(ss.grabImage(2, 6, 192, 192), 192,192);
			runUp[2] = im.scaledImage(ss.grabImage(3, 6, 192, 192), 192,192);
			runUp[3] = im.scaledImage(ss.grabImage(4, 6, 192, 192), 192,192);
			runUp[4] = im.scaledImage(ss.grabImage(5, 6, 192, 192), 192,192);
			runUp[5] = im.scaledImage(ss.grabImage(6, 6, 192, 192), 192,192);
			
			runRight[0] = im.scaledImage(ss.grabImage(1, 2, 192, 192), 192,192);
			runRight[1] = im.scaledImage(ss.grabImage(2, 2, 192, 192), 192,192);
			runRight[2] = im.scaledImage(ss.grabImage(3, 2, 192, 192), 192,192);
			runRight[3] = im.scaledImage(ss.grabImage(4, 2, 192, 192), 192,192);
			runRight[4] = im.scaledImage(ss.grabImage(5, 2, 192, 192), 192,192);
			runRight[5] = im.scaledImage(ss.grabImage(6, 2, 192, 192), 192,192);
			
			runLeft[0] = im.scaledImage(ss.grabImage(1, 6, 192, 192), 192,192);
			runLeft[1] = im.scaledImage(ss.grabImage(2, 6, 192, 192), 192,192);
			runLeft[2] = im.scaledImage(ss.grabImage(3, 6, 192, 192), 192,192);
			runLeft[3] = im.scaledImage(ss.grabImage(4, 6, 192, 192), 192,192);
			runLeft[4] = im.scaledImage(ss.grabImage(5, 6, 192, 192), 192,192);
			runLeft[5] = im.scaledImage(ss.grabImage(6, 6, 192, 192), 192,192);
						
			idleDown[0] = im.scaledImage(ss.grabImage(1, 1, 192, 192), 192,192);
			idleDown[1] = im.scaledImage(ss.grabImage(2, 1, 192, 192), 192,192);
			idleDown[2] = im.scaledImage(ss.grabImage(3, 1, 192, 192), 192,192);
			idleDown[3] = im.scaledImage(ss.grabImage(4, 1, 192, 192), 192,192);
			idleDown[4] = im.scaledImage(ss.grabImage(5, 1, 192, 192), 192,192);
			idleDown[5] = im.scaledImage(ss.grabImage(6, 1, 192, 192), 192,192);
			
			idleLeft[0] = im.scaledImage(ss.grabImage(1, 1, 192, 192), 192,192);
			idleLeft[1] = im.scaledImage(ss.grabImage(2, 1, 192, 192), 192,192);
			idleLeft[2] = im.scaledImage(ss.grabImage(3, 1, 192, 192), 192,192);
			idleLeft[3] = im.scaledImage(ss.grabImage(4, 1, 192, 192), 192,192);
			idleLeft[4] = im.scaledImage(ss.grabImage(5, 1, 192, 192), 192,192);
			idleLeft[5] = im.scaledImage(ss.grabImage(6, 1, 192, 192), 192,192);

			idleRight[0] = im.scaledImage(ss.grabImage(1, 1, 192, 192), 192,192);
			idleRight[1] = im.scaledImage(ss.grabImage(2, 1, 192, 192), 192,192);
			idleRight[2] = im.scaledImage(ss.grabImage(3, 1, 192, 192), 192,192);
			idleRight[3] = im.scaledImage(ss.grabImage(4, 1, 192, 192), 192,192);
			idleRight[4] = im.scaledImage(ss.grabImage(5, 1, 192, 192), 192,192);
			idleRight[5] = im.scaledImage(ss.grabImage(6, 1, 192, 192), 192,192);
			
			idleUp[0] = im.scaledImage(ss.grabImage(1, 1, 192, 192), 192,192);
			idleUp[1] = im.scaledImage(ss.grabImage(2, 1, 192, 192), 192,192);
			idleUp[2] = im.scaledImage(ss.grabImage(3, 1, 192, 192), 192,192);
			idleUp[3] = im.scaledImage(ss.grabImage(4, 1, 192, 192), 192,192);
			idleUp[4] = im.scaledImage(ss.grabImage(5, 1, 192, 192), 192,192);
			idleUp[5] = im.scaledImage(ss.grabImage(6, 1, 192, 192), 192,192);
			
			attack1Up[0] = im.scaledImage(ss.grabImage(1, 5, 192, 192), 192,192);
			attack1Up[1] = im.scaledImage(ss.grabImage(2, 5, 192, 192), 192,192);
			attack1Up[2] = im.scaledImage(ss.grabImage(3, 5, 192, 192), 192,192);
			attack1Up[3] = im.scaledImage(ss.grabImage(4, 5, 192, 192), 192,192);
			attack1Up[4] = im.scaledImage(ss.grabImage(5, 5, 192, 192), 192,192);
			attack1Up[5] = im.scaledImage(ss.grabImage(6, 5, 192, 192), 192,192);
			
			attack1Down[0] = im.scaledImage(ss.grabImage(1, 4, 192, 192), 192,192);
			attack1Down[1] = im.scaledImage(ss.grabImage(2, 4, 192, 192), 192,192);
			attack1Down[2] = im.scaledImage(ss.grabImage(3, 4, 192, 192), 192,192);
			attack1Down[3] = im.scaledImage(ss.grabImage(4, 4, 192, 192), 192,192);
			attack1Down[4] = im.scaledImage(ss.grabImage(5, 4, 192, 192), 192,192);
			attack1Down[5] = im.scaledImage(ss.grabImage(6, 4, 192, 192), 192,192);
			
			attack1Left[0] = im.scaledImage(ss.grabImage(6, 7, 192, 192), 192,192);
			attack1Left[1] = im.scaledImage(ss.grabImage(5, 7, 192, 192), 192,192);
			attack1Left[2] = im.scaledImage(ss.grabImage(4, 7, 192, 192), 192,192);
			attack1Left[3] = im.scaledImage(ss.grabImage(3, 7, 192, 192), 192,192);
			attack1Left[4] = im.scaledImage(ss.grabImage(2, 7, 192, 192), 192,192);
			attack1Left[5] = im.scaledImage(ss.grabImage(1, 7, 192, 192), 192,192);
			
			attack1Right[0] = im.scaledImage(ss.grabImage(1, 3, 192, 192), 192,192);
			attack1Right[1] = im.scaledImage(ss.grabImage(2, 3, 192, 192), 192,192);
			attack1Right[2] = im.scaledImage(ss.grabImage(3, 3, 192, 192), 192,192);
			attack1Right[3] = im.scaledImage(ss.grabImage(4, 3, 192, 192), 192,192);
			attack1Right[4] = im.scaledImage(ss.grabImage(5, 3, 192, 192), 192,192);
			attack1Right[5] = im.scaledImage(ss.grabImage(6, 3, 192, 192), 192,192);
			
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
		return new Rectangle(x+78, y+78, 34, 42 );
	}

	public Rectangle renderOrder() {
		return new Rectangle(x+78, y+78, 34, 42 );
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
		if(dead == false) {
			if(attack1 == false) {
				//Counter Move and Idle
				if(spriteCounter > 6) {
					if(spriteNum == 1) {spriteNum =2;}
					else if(spriteNum ==2) {spriteNum =3;}
					else if(spriteNum ==3) {spriteNum =4;}
					else if(spriteNum ==4) {spriteNum =5;}
					else if(spriteNum ==5) {spriteNum =6;}
					else if(spriteNum ==6) {spriteNum =1;	setAction();}
					spriteCounter =0;
				}
			} else if(attack1 == true) {
				//Counter Attack1
				if(spriteCounter > 6) {
					if(spriteAttack1 == 1) {spriteAttack1 =2;}
					else if(spriteAttack1 ==2) {spriteAttack1 =3;}
					else if(spriteAttack1 ==3) {spriteAttack1 =4;}
					else if(spriteAttack1 ==4) {spriteAttack1 =5;
					
						//Attack Collision On
						attackArea.x = x+64;
						attackArea.y = y+64;
						
						switch(arahAttack) {
						case "atas": attackArea.x = x+48; attackArea.y  = y+32; attackArea.width  = 128; attackArea.height = 40; break;
						case "bawah": attackArea.x = x+48; attackArea.y  = y+128; attackArea.width = 128; attackArea.height = 40; break;
						case "kanan": attackArea.x = x+144; attackArea.y  = y+64; attackArea.width = 40; attackArea.height = 72; break;
						case "kiri": attackArea.x = x+8; attackArea.y  = y+64; attackArea.width = 40; attackArea.height = 72; break;
						}
					}
					else if(spriteAttack1 ==5) {spriteAttack1 =6;}
					else if(spriteAttack1 ==6) {spriteAttack1 =1;
					
						attack1 = false;
						//Attack Collision Off
						attackArea = new Rectangle(0, 0, 0, 0);
					}
					spriteCounter =0;
				}
			}
		}
		
		else if (dead == true) {
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
				else if(spriteDead == 13) {spriteDead = 14;	game.tryWorld.entity.remove(this);}
				spriteCounter =0;
			}
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
