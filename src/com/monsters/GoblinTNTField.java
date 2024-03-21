package com.monsters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.filehandler.SpriteSheet;
import com.id.BlockType;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Entity;
import com.obj.GameObject;
import com.quadTree.Point;
import com.quadTree.QuadNode;
import com.tile.ImageManager;

import projectile.TNTProjectile;

public class GoblinTNTField extends Entity{
	
	public SpriteSheet ss = new SpriteSheet("/assets/assetsentity/Goblins_TNT_Red.png");
	public SpriteSheet ssdead = new SpriteSheet("/assets/assetsentity/Dead.png");
	ImageManager im = new ImageManager();
	private int xMove = 0;
	private int yMove = 0;
	private int spriteAttack1 = 1;
	public int start, stop = 0;
	public float angle;

	public GoblinTNTField(int x, int y, ID id, EntityType et, EntityClass ec, Game game) {
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
		checkDistance();
		attackCollision();
		rangeCollision();
		
		if(hp <= 0) {
			speed = 0;
			dead = true;
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
		
		int attackRangeX = Math.abs(x - game.tryWorld.player.getX());
		int attackRangeY = Math.abs(y - game.tryWorld.player.getY());
		
		if(attackRangeX <= 64*4 && attackRangeY <= 64*4) {
			attack1 = true;
			stop = game.second;
			if(stop - start >= delay) {
				attackTNT();
			}
			
		} else {
			stopMove();
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
		g.setColor(Color.red);
		g.fillRect(x+(image.getWidth()/2)-25, y+image.getHeight()-48, (int)((hp/50)*50), 5);
		g.setColor(Color.BLACK);
		
		// g.fillRect(getBound().x, getBound().y, getBound().width, getBound().height);
		// g.fillRect(attackArea.x, attackArea.y, attackArea.width, attackArea.height);
		// g.fillRect(rangeArea.x, rangeArea.y, rangeArea.width, rangeArea.height);
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
					switch (arah) {
					case "atas": 
						if(spriteNum == 1) {image = runUp[0];}
						if(spriteNum == 2) {image = runUp[1];}
						if(spriteNum == 3) {image = runUp[2];}
						if(spriteNum == 4) {image = runUp[3];}
						if(spriteNum == 5) {image = runUp[4];}
						if(spriteNum == 6) {image = runUp[5];}		break;
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
					if(spriteAttack1 == 1) {image = attackTNTUp[0];}
					if(spriteAttack1 == 2) {image = attackTNTUp[1];}
					if(spriteAttack1 == 3) {image = attackTNTUp[2];}
					if(spriteAttack1 == 4) {image = attackTNTUp[3];}
					if(spriteAttack1 == 5) {image = attackTNTUp[4];}
					if(spriteAttack1 == 6) {image = attackTNTUp[5];}
					if(spriteAttack1 == 7) {image = attackTNTUp[6];}	break;
				case "bawah":
					if(spriteAttack1 == 1) {image = attackTNTDown[0];}
					if(spriteAttack1 == 2) {image = attackTNTDown[1];}
					if(spriteAttack1 == 3) {image = attackTNTDown[2];}
					if(spriteAttack1 == 4) {image = attackTNTDown[3];}
					if(spriteAttack1 == 5) {image = attackTNTDown[4];}
					if(spriteAttack1 == 6) {image = attackTNTDown[5];}
					if(spriteAttack1 == 7) {image = attackTNTDown[6];}	break;
				case "kanan":
					if(spriteAttack1 == 1) {image = attackTNTRight[0];}
					if(spriteAttack1 == 2) {image = attackTNTRight[1];}
					if(spriteAttack1 == 3) {image = attackTNTRight[2];}
					if(spriteAttack1 ==4) {image = attackTNTRight[3];}
					if(spriteAttack1 ==5) {image = attackTNTRight[4];}
					if(spriteAttack1 ==6) {image = attackTNTRight[5];}
					if(spriteAttack1 == 7) {image = attackTNTRight[6];}	break;
				case "kiri":
					if(spriteAttack1 == 1) {image = attackTNTLeft[0];}
					if(spriteAttack1 == 2) {image = attackTNTLeft[1];}
					if(spriteAttack1 == 3) {image = attackTNTLeft[2];}
					if(spriteAttack1 == 4) {image = attackTNTLeft[3];}
					if(spriteAttack1 == 5) {image = attackTNTLeft[4];}
					if(spriteAttack1 == 6) {image = attackTNTLeft[5];}
					if(spriteAttack1 == 7) {image = attackTNTLeft[6];}	break;
				case "ataskiri":
					if(spriteAttack1 == 1) {image = attackTNTLeft[0];}
					if(spriteAttack1 == 2) {image = attackTNTLeft[1];}
					if(spriteAttack1 == 3) {image = attackTNTLeft[2];}
					if(spriteAttack1 == 4) {image = attackTNTLeft[3];}
					if(spriteAttack1 == 5) {image = attackTNTLeft[4];}
					if(spriteAttack1 == 6) {image = attackTNTLeft[5];}
					if(spriteAttack1 == 7) {image = attackTNTLeft[6];}	break;
				case "ataskanan":
					if(spriteAttack1 == 1) {image = attackTNTRight[0];}
					if(spriteAttack1 == 2) {image = attackTNTRight[1];}
					if(spriteAttack1 == 3) {image = attackTNTRight[2];}
					if(spriteAttack1 == 4) {image = attackTNTRight[3];}
					if(spriteAttack1 == 5) {image = attackTNTRight[4];}
					if(spriteAttack1 == 6) {image = attackTNTRight[5];}
					if(spriteAttack1 == 7) {image = attackTNTRight[6];}	break;
				case "bawahkiri":
					if(spriteAttack1 == 1) {image = attackTNTLeft[0];}
					if(spriteAttack1 == 2) {image = attackTNTLeft[1];}
					if(spriteAttack1 == 3) {image = attackTNTLeft[2];}
					if(spriteAttack1 == 4) {image = attackTNTLeft[3];}
					if(spriteAttack1 == 5) {image = attackTNTLeft[4];}
					if(spriteAttack1 == 6) {image = attackTNTLeft[5];}
					if(spriteAttack1 == 7) {image = attackTNTLeft[6];}	break;
				case "bawahkanan":
					if(spriteAttack1 == 1) {image = attackTNTRight[0];}
					if(spriteAttack1 == 2) {image = attackTNTRight[1];}
					if(spriteAttack1 == 3) {image = attackTNTRight[2];}
					if(spriteAttack1 == 4) {image = attackTNTRight[3];}
					if(spriteAttack1 == 5) {image = attackTNTRight[4];}
					if(spriteAttack1 == 6) {image = attackTNTRight[5];}
					if(spriteAttack1 == 7) {image = attackTNTRight[6];}	break;
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
			
			runUp[0] = im.scaledImage(ss.grabImage(1, 4, 192, 192), 192,192);
			runUp[1] = im.scaledImage(ss.grabImage(2, 4, 192, 192), 192,192);
			runUp[2] = im.scaledImage(ss.grabImage(3, 4, 192, 192), 192,192);
			runUp[3] = im.scaledImage(ss.grabImage(4, 4, 192, 192), 192,192);
			runUp[4] = im.scaledImage(ss.grabImage(5, 4, 192, 192), 192,192);
			runUp[5] = im.scaledImage(ss.grabImage(6, 4, 192, 192), 192,192);
			
			runRight[0] = im.scaledImage(ss.grabImage(1, 2, 192, 192), 192,192);
			runRight[1] = im.scaledImage(ss.grabImage(2, 2, 192, 192), 192,192);
			runRight[2] = im.scaledImage(ss.grabImage(3, 2, 192, 192), 192,192);
			runRight[3] = im.scaledImage(ss.grabImage(4, 2, 192, 192), 192,192);
			runRight[4] = im.scaledImage(ss.grabImage(5, 2, 192, 192), 192,192);
			runRight[5] = im.scaledImage(ss.grabImage(6, 2, 192, 192), 192,192);
			
			runLeft[0] = im.scaledImage(ss.grabImage(1, 4, 192, 192), 192,192);
			runLeft[1] = im.scaledImage(ss.grabImage(2, 4, 192, 192), 192,192);
			runLeft[2] = im.scaledImage(ss.grabImage(3, 4, 192, 192), 192,192);
			runLeft[3] = im.scaledImage(ss.grabImage(4, 4, 192, 192), 192,192);
			runLeft[4] = im.scaledImage(ss.grabImage(5, 4, 192, 192), 192,192);
			runLeft[5] = im.scaledImage(ss.grabImage(6, 4, 192, 192), 192,192);
						
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
			
			attackTNTUp[0] = im.scaledImage(ss.grabImage(7, 3, 192, 192), 192,192);
			attackTNTUp[1] = im.scaledImage(ss.grabImage(6, 3, 192, 192), 192,192);
			attackTNTUp[2] = im.scaledImage(ss.grabImage(5, 3, 192, 192), 192,192);
			attackTNTUp[3] = im.scaledImage(ss.grabImage(4, 3, 192, 192), 192,192);
			attackTNTUp[4] = im.scaledImage(ss.grabImage(3, 3, 192, 192), 192,192);
			attackTNTUp[5] = im.scaledImage(ss.grabImage(2, 3, 192, 192), 192,192);
			attackTNTUp[6] = im.scaledImage(ss.grabImage(1, 3, 192, 192), 192,192);
			
			attackTNTDown[0] = im.scaledImage(ss.grabImage(1, 5, 192, 192), 192,192);
			attackTNTDown[1] = im.scaledImage(ss.grabImage(2, 5, 192, 192), 192,192);
			attackTNTDown[2] = im.scaledImage(ss.grabImage(3, 5, 192, 192), 192,192);
			attackTNTDown[3] = im.scaledImage(ss.grabImage(4, 5, 192, 192), 192,192);
			attackTNTDown[4] = im.scaledImage(ss.grabImage(5, 5, 192, 192), 192,192);
			attackTNTDown[5] = im.scaledImage(ss.grabImage(6, 5, 192, 192), 192,192);
			attackTNTDown[6] = im.scaledImage(ss.grabImage(7, 5, 192, 192), 192,192);
			
			attackTNTLeft[0] = im.scaledImage(ss.grabImage(7, 3, 192, 192), 192,192);
			attackTNTLeft[1] = im.scaledImage(ss.grabImage(6, 3, 192, 192), 192,192);
			attackTNTLeft[2] = im.scaledImage(ss.grabImage(5, 3, 192, 192), 192,192);
			attackTNTLeft[3] = im.scaledImage(ss.grabImage(4, 3, 192, 192), 192,192);
			attackTNTLeft[4] = im.scaledImage(ss.grabImage(3, 3, 192, 192), 192,192);
			attackTNTLeft[5] = im.scaledImage(ss.grabImage(2, 3, 192, 192), 192,192);
			attackTNTLeft[6] = im.scaledImage(ss.grabImage(1, 3, 192, 192), 192,192);
			
			attackTNTRight[0] = im.scaledImage(ss.grabImage(1, 5, 192, 192), 192,192);
			attackTNTRight[1] = im.scaledImage(ss.grabImage(2, 5, 192, 192), 192,192);
			attackTNTRight[2] = im.scaledImage(ss.grabImage(3, 5, 192, 192), 192,192);
			attackTNTRight[3] = im.scaledImage(ss.grabImage(4, 5, 192, 192), 192,192);
			attackTNTRight[4] = im.scaledImage(ss.grabImage(5, 5, 192, 192), 192,192);
			attackTNTRight[5] = im.scaledImage(ss.grabImage(6, 5, 192, 192), 192,192);
			attackTNTRight[6] = im.scaledImage(ss.grabImage(7, 5, 192, 192), 192,192);
			
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
		return new Rectangle(x+80, y +80, 32, 40 );
	}

	public Rectangle renderOrder() {
		return new Rectangle(x+80, y +80, 32, 40 );
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
				if(spriteCounter > 6) {
					if(spriteNum == 1) {spriteNum =2;}
					else if(spriteNum ==2) {spriteNum =3;}
					else if(spriteNum ==3) {spriteNum =4;}
					else if(spriteNum ==4) {spriteNum =5;}
					else if(spriteNum ==5) {spriteNum =6;}
					else if(spriteNum ==6) {spriteNum =1;	setAction();}
					spriteCounter =0;
				}
			}
			
			else if(attack1 == true) {
				//Counter Attack1
				if(spriteCounter > 6) {
					if(spriteAttack1 == 1) {spriteAttack1 =2;}
					else if(spriteAttack1 ==2) {spriteAttack1 =3;
					throwTNT();
					}
					else if(spriteAttack1 ==3) {spriteAttack1 =4;}
					else if(spriteAttack1 ==4) {spriteAttack1 =5;}
					else if(spriteAttack1 ==5) {spriteAttack1 =6;}
					else if(spriteAttack1 ==6) {spriteAttack1 =7;}
					else if(spriteAttack1 ==7) {spriteAttack1 =1;
						attack1 = false;
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
				else if(spriteDead == 13) {spriteDead = 14; game.tryWorld.entity.remove(this);}
				spriteCounter =0;
			}
		}
	}
	
	public void attackTNT() {
		
		float px = (float)((game.tryWorld.player.getX()+game.tryWorld.player.getSize().getWidth()/2) - (x + getSize().getWidth()/2));
		float py = (float)((game.tryWorld.player.getY()+game.tryWorld.player.getSize().getHeight()/2) - (y + getSize().getHeight()/2));
		
		angle = (float) Math.toDegrees(Math.atan2(py, px));

	    if(angle < 0){
	        angle += 360;
	    }
	    if(angle > 337.5 && angle <= 360 || angle > 0 && angle <= 22.5) {
	    	arahAttack = "kiri";
	    }else if(angle > 22.5 && angle <= 67.5) {
	    	arahAttack = "bawahkiri";
	    }else if(angle > 67.5 && angle <= 112.5) {
	    	arahAttack = "bawah";
	    }else if(angle > 112.5 && angle <= 157.5) {
	    	arahAttack = "bawahkanan";
	    }else if(angle > 157.5 && angle <= 202.5) {
	    	arahAttack = "kanan";
	    }else if(angle > 202.5 && angle <= 247.5) {
	    	arahAttack = "ataskanan";
	    }else if(angle > 247.5 && angle <= 292.5) {
	    	arahAttack = "atas";
	    }else {
	    	arahAttack = "ataskiri";
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
	
	public void throwTNT(){
		game.tryWorld.qt.insert(new QuadNode(new Point(this.getX()+image.getWidth()/2, this.getY()+image.getHeight()/2), new TNTProjectile(this.getX()+image.getWidth()/2, this.getY()+image.getHeight()/2, ID.Entity, BlockType.Projectile, game, (int)game.tryWorld.player.getX() + (int)game.tryWorld.player.getSize().getWidth()/2, (int)game.tryWorld.player.getY() + (int)game.tryWorld.player.getSize().getHeight()/2, angle, arahAttack)), game.tryWorld.entity, null);
	}

}
