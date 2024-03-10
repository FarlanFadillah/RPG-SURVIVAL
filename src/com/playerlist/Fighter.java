package com.playerlist;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.filehandler.SpriteSheet;
import com.gameMechanics.PlayerEquipment;
import com.gameMechanics.PlayerInventory;
import com.id.BlockType;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Block;
import com.obj.Entity;
import com.obj.GameObject;
import com.obj.Item;
import com.quadTree.Point;
import com.tile.ImageManager;


public class Fighter extends Entity{
	
	public SpriteSheet ss = new SpriteSheet("/assets/assetsentity/Fighter.png");
	public SpriteSheet ss2 = new SpriteSheet("/assets/assetsentity/Pawn_Blue.png");
	public SpriteSheet ssdead = new SpriteSheet("/assets/assetsentity/Dead.png");
	
	ImageManager im = new ImageManager();
	
	private int spriteAttack1 = 1;
	private int spriteAttack2 = 1;
	private int spriteTree = 1;
	
	public Fighter(int x, int y, ID id, EntityType et, EntityClass ec, Game game) {
		super(x, y, id, et, ec, game);
		
		playerInventory = new PlayerInventory(game);
		playerEquipment = new PlayerEquipment(game);
		
		hp = 100;
		mana = 100;
		stamina = 100;
		speed = 4;

		getImage();
		image = idleRight[0];
		arah = "kanan";
		arahAttack = "kanan";
		arahTree = "kanan";
		
	}

	public void tick() {
		if(attack1 == false && attack2 == false) {
			speed = 4;
		}else if(attack1 == true || attack2 == true){
			speed = 0;
		}
		
		x += velX;
		y += velY;
		Collision();
		playerControl();
		spriteCounter();
		attackCollision();
		
		if(hp <= 0) {
			dead = true;
		}
		
	}

	public void render(Graphics g) {
		animatedSprite();
		g.drawImage(image, x, y, null);
	}

	public Rectangle getBound() {
		return new Rectangle(x+80, y +112, 32, 16 );
		
	}

	public Rectangle renderOrder() {
		return new Rectangle(x+80, y +112, 32, 16 );
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
			
			if(getBound().intersects(temp.getBound()) && temp.getID() == ID.Item){
				game.tryWorld.objects.remove(temp);
				Item getItem = (Item) temp;
				playerInventory.addItem(getItem);
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
	
	public void animatedSprite(){
		if(dead == false) {
			if(attack1 == false && attack2 == false && cutTree == false) {
				if(isUp() || isDown()|| isRight() || isLeft()) {
					//Move animations
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
				
			} else if(attack1 == true && attack2 == false && cutTree == false){
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
			
			} else if(attack1 == false && attack2 == true && cutTree == false) {
					//Attack 2 Animations
					switch (arahAttack) {
					case "atas": 
						if(spriteAttack2 == 1) {image = attack1Up[0];}
						if(spriteAttack2 == 2) {image = attack1Up[1];}
						if(spriteAttack2 == 3) {image = attack1Up[2];}
						if(spriteAttack2 == 4) {image = attack1Up[3];}
						if(spriteAttack2 == 5) {image = attack1Up[4];}
						if(spriteAttack2 == 6) {image = attack1Up[5];}
						if(spriteAttack2 == 7) {image = attack2Up[0];}
						if(spriteAttack2 == 8) {image = attack2Up[1];}
						if(spriteAttack2 == 9) {image = attack2Up[2];}
						if(spriteAttack2 == 10) {image = attack2Up[3];}
						if(spriteAttack2 == 11) {image = attack2Up[4];}
						if(spriteAttack2 == 12) {image = attack2Up[5];}	break;
					case "bawah":
						if(spriteAttack2 == 1) {image = attack1Down[0];}
						if(spriteAttack2 == 2) {image = attack1Down[1];}
						if(spriteAttack2 == 3) {image = attack1Down[2];}
						if(spriteAttack2 == 4) {image = attack1Down[3];}
						if(spriteAttack2 == 5) {image = attack1Down[4];}
						if(spriteAttack2 == 6) {image = attack1Down[5];}
						if(spriteAttack2 == 7) {image = attack2Down[0];}
						if(spriteAttack2 == 8) {image = attack2Down[1];}
						if(spriteAttack2 == 9) {image = attack2Down[2];}
						if(spriteAttack2 == 10) {image = attack2Down[3];}
						if(spriteAttack2 == 11) {image = attack2Down[4];}
						if(spriteAttack2 == 12) {image = attack2Down[5];}	break;
					case "kanan":
						if(spriteAttack2 == 1) {image = attack1Right[0];}
						if(spriteAttack2 == 2) {image = attack1Right[1];}
						if(spriteAttack2 == 3) {image = attack1Right[2];}
						if(spriteAttack2 == 4) {image = attack1Right[3];}
						if(spriteAttack2 == 5) {image = attack1Right[4];}
						if(spriteAttack2 == 6) {image = attack1Right[5];}
						if(spriteAttack2 == 7) {image = attack2Right[0];}
						if(spriteAttack2 == 8) {image = attack2Right[1];}
						if(spriteAttack2 == 9) {image = attack2Right[2];}
						if(spriteAttack2 == 10) {image = attack2Right[3];}
						if(spriteAttack2 == 11) {image = attack2Right[4];}
						if(spriteAttack2 == 12) {image = attack2Right[5];}	break;
					case "kiri":
						if(spriteAttack2 == 1) {image = attack1Left[0];}
						if(spriteAttack2 == 2) {image = attack1Left[1];}
						if(spriteAttack2 == 3) {image = attack1Left[2];}
						if(spriteAttack2 == 4) {image = attack1Left[3];}
						if(spriteAttack2 == 5) {image = attack1Left[4];}
						if(spriteAttack2 == 6) {image = attack1Left[5];}
						if(spriteAttack2 == 7) {image = attack2Left[0];}
						if(spriteAttack2 == 8) {image = attack2Left[1];}
						if(spriteAttack2 == 9) {image = attack2Left[2];}
						if(spriteAttack2 == 10) {image = attack2Left[3];}
						if(spriteAttack2 == 11) {image = attack2Left[4];}
						if(spriteAttack2 == 12) {image = attack2Left[5];}	break;
					}
			} else if(attack1 == false && attack2 == false && cutTree == true) {
					//Cut Tree Animations
					switch (arahTree) {
					case "atas": 
						if(spriteTree == 1) {image = treeUp[0];}
						if(spriteTree == 2) {image = treeUp[1];}
						if(spriteTree == 3) {image = treeUp[2];}
						if(spriteTree == 4) {image = treeUp[3];}
						if(spriteTree == 5) {image = treeUp[4];}
						if(spriteTree == 6) {image = treeUp[5];}	break;
					case "bawah":
						if(spriteTree == 1) {image = treeDown[0];}
						if(spriteTree == 2) {image = treeDown[1];}
						if(spriteTree == 3) {image = treeDown[2];}
						if(spriteTree == 4) {image = treeDown[3];}
						if(spriteTree == 5) {image = treeDown[4];}
						if(spriteTree == 6) {image = treeDown[5];}	break;
					case "kanan":
						if(spriteTree == 1) {image = treeRight[0];}
						if(spriteTree == 2) {image = treeRight[1];}
						if(spriteTree == 3) {image = treeRight[2];}
						if(spriteTree == 4) {image = treeRight[3];}
						if(spriteTree == 5) {image = treeRight[4];}
						if(spriteTree == 6) {image = treeRight[5];}	break;
					case "kiri":
						if(spriteTree == 1) {image = treeLeft[0];}
						if(spriteTree == 2) {image = treeLeft[1];}
						if(spriteTree == 3) {image = treeLeft[2];}
						if(spriteTree == 4) {image = treeLeft[3];}
						if(spriteTree == 5) {image = treeLeft[4];}
						if(spriteTree == 6) {image = treeLeft[5];} break;
					}
			}
		}else {
			//Dead Animations
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
			
			runDown[0] = im.scaledImage(ss.grabImage(1, 2, 192, 192), 192, 192);
			runDown[1] = im.scaledImage(ss.grabImage(2, 2, 192, 192), 192, 192);
			runDown[2] = im.scaledImage(ss.grabImage(3, 2, 192, 192), 192, 192);
			runDown[3] = im.scaledImage(ss.grabImage(4, 2, 192, 192), 192,192);
			runDown[4] = im.scaledImage(ss.grabImage(5, 2, 192, 192), 192,192);
			runDown[5] = im.scaledImage(ss.grabImage(6, 2, 192, 192), 192,192);
			
			runUp[0] = im.scaledImage(ss.grabImage(1, 9, 192, 192), 192,192);
			runUp[1] = im.scaledImage(ss.grabImage(2, 9, 192, 192), 192,192);
			runUp[2] = im.scaledImage(ss.grabImage(3, 9, 192, 192), 192,192);
			runUp[3] = im.scaledImage(ss.grabImage(4, 9, 192, 192), 192,192);
			runUp[4] = im.scaledImage(ss.grabImage(5, 9, 192, 192), 192,192);
			runUp[5] = im.scaledImage(ss.grabImage(6, 9, 192, 192), 192,192);
			
			runRight[0] = im.scaledImage(ss.grabImage(1, 2, 192, 192), 192,192);
			runRight[1] = im.scaledImage(ss.grabImage(2, 2, 192, 192), 192,192);
			runRight[2] = im.scaledImage(ss.grabImage(3, 2, 192, 192), 192,192);
			runRight[3] = im.scaledImage(ss.grabImage(4, 2, 192, 192), 192,192);
			runRight[4] = im.scaledImage(ss.grabImage(5, 2, 192, 192), 192,192);
			runRight[5] = im.scaledImage(ss.grabImage(6, 2, 192, 192), 192,192);
			
			runLeft[0] = im.scaledImage(ss.grabImage(1, 9, 192, 192), 192,192);
			runLeft[1] = im.scaledImage(ss.grabImage(2, 9, 192, 192), 192,192);
			runLeft[2] = im.scaledImage(ss.grabImage(3, 9, 192, 192), 192,192);
			runLeft[3] = im.scaledImage(ss.grabImage(4, 9, 192, 192), 192,192);
			runLeft[4] = im.scaledImage(ss.grabImage(5, 9, 192, 192), 192,192);
			runLeft[5] = im.scaledImage(ss.grabImage(6, 9, 192, 192), 192,192);
			
			idleUp[0] = im.scaledImage(ss.grabImage(1, 1, 192, 192), 192,192);
			idleUp[1] = im.scaledImage(ss.grabImage(2, 1, 192, 192), 192,192);
			idleUp[2] = im.scaledImage(ss.grabImage(3, 1, 192, 192), 192,192);
			idleUp[3] = im.scaledImage(ss.grabImage(4, 1, 192, 192), 192,192);
			idleUp[4] = im.scaledImage(ss.grabImage(5, 1, 192, 192), 192,192);
			idleUp[5] = im.scaledImage(ss.grabImage(6, 1, 192, 192), 192,192);
			
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
			
			attack1Up[0] = im.scaledImage(ss.grabImage(1, 7, 192, 192), 192,192);
			attack1Up[1] = im.scaledImage(ss.grabImage(2, 7, 192, 192), 192,192);
			attack1Up[2] = im.scaledImage(ss.grabImage(3, 7, 192, 192), 192,192);
			attack1Up[3] = im.scaledImage(ss.grabImage(4, 7, 192, 192), 192,192);
			attack1Up[4] = im.scaledImage(ss.grabImage(5, 7, 192, 192), 192,192);
			attack1Up[5] = im.scaledImage(ss.grabImage(6, 7, 192, 192), 192,192);
			
			attack1Down[0] = im.scaledImage(ss.grabImage(1, 5, 192, 192), 192,192);
			attack1Down[1] = im.scaledImage(ss.grabImage(2, 5, 192, 192), 192,192);
			attack1Down[2] = im.scaledImage(ss.grabImage(3, 5, 192, 192), 192,192);
			attack1Down[3] = im.scaledImage(ss.grabImage(4, 5, 192, 192), 192,192);
			attack1Down[4] = im.scaledImage(ss.grabImage(5, 5, 192, 192), 192,192);
			attack1Down[5] = im.scaledImage(ss.grabImage(6, 5, 192, 192), 192,192);
			
			attack1Left[0] = im.scaledImage(ss.grabImage(6, 10, 192, 192), 192,192);
			attack1Left[1] = im.scaledImage(ss.grabImage(5, 10, 192, 192), 192,192);
			attack1Left[2] = im.scaledImage(ss.grabImage(4, 10, 192, 192), 192,192);
			attack1Left[3] = im.scaledImage(ss.grabImage(3, 10, 192, 192), 192,192);
			attack1Left[4] = im.scaledImage(ss.grabImage(2, 10, 192, 192), 192,192);
			attack1Left[5] = im.scaledImage(ss.grabImage(1, 10, 192, 192), 192,192);
			
			attack1Right[0] = im.scaledImage(ss.grabImage(1, 3, 192, 192), 192,192);
			attack1Right[1] = im.scaledImage(ss.grabImage(2, 3, 192, 192), 192,192);
			attack1Right[2] = im.scaledImage(ss.grabImage(3, 3, 192, 192), 192,192);
			attack1Right[3] = im.scaledImage(ss.grabImage(4, 3, 192, 192), 192,192);
			attack1Right[4] = im.scaledImage(ss.grabImage(5, 3, 192, 192), 192,192);
			attack1Right[5] = im.scaledImage(ss.grabImage(6, 3, 192, 192), 192,192);
			
			attack2Up[0] = im.scaledImage(ss.grabImage(1, 8, 192, 192), 192,192);
			attack2Up[1] = im.scaledImage(ss.grabImage(2, 8, 192, 192), 192,192);
			attack2Up[2] = im.scaledImage(ss.grabImage(3, 8, 192, 192), 192,192);
			attack2Up[3] = im.scaledImage(ss.grabImage(4, 8, 192, 192), 192,192);
			attack2Up[4] = im.scaledImage(ss.grabImage(5, 8, 192, 192), 192,192);
			attack2Up[5] = im.scaledImage(ss.grabImage(6, 8, 192, 192), 192,192);
			
			attack2Down[0] = im.scaledImage(ss.grabImage(1, 6, 192, 192), 192,192);
			attack2Down[1] = im.scaledImage(ss.grabImage(2, 6, 192, 192), 192,192);
			attack2Down[2] = im.scaledImage(ss.grabImage(3, 6, 192, 192), 192,192);
			attack2Down[3] = im.scaledImage(ss.grabImage(4, 6, 192, 192), 192,192);
			attack2Down[4] = im.scaledImage(ss.grabImage(5, 6, 192, 192), 192,192);
			attack2Down[5] = im.scaledImage(ss.grabImage(6, 6, 192, 192), 192,192);
			
			attack2Left[0] = im.scaledImage(ss.grabImage(6, 11, 192, 192), 192,192);
			attack2Left[1] = im.scaledImage(ss.grabImage(5, 11, 192, 192), 192,192);
			attack2Left[2] = im.scaledImage(ss.grabImage(4, 11, 192, 192), 192,192);
			attack2Left[3] = im.scaledImage(ss.grabImage(3, 11, 192, 192), 192,192);
			attack2Left[4] = im.scaledImage(ss.grabImage(2, 11, 192, 192), 192,192);
			attack2Left[5] = im.scaledImage(ss.grabImage(1, 11, 192, 192), 192,192);
			
			attack2Right[0] = im.scaledImage(ss.grabImage(1, 4, 192, 192), 192,192);
			attack2Right[1] = im.scaledImage(ss.grabImage(2, 4, 192, 192), 192,192);
			attack2Right[2] = im.scaledImage(ss.grabImage(3, 4, 192, 192), 192,192);
			attack2Right[3] = im.scaledImage(ss.grabImage(4, 4, 192, 192), 192,192);
			attack2Right[4] = im.scaledImage(ss.grabImage(5, 4, 192, 192), 192,192);
			attack2Right[5] = im.scaledImage(ss.grabImage(6, 4, 192, 192), 192,192);
			
			treeUp[0] = im.scaledImage(ss2.grabImage(1, 4, 192, 192), 192,192);
			treeUp[1] = im.scaledImage(ss2.grabImage(2, 4, 192, 192), 192,192);
			treeUp[2] = im.scaledImage(ss2.grabImage(3, 4, 192, 192), 192,192);
			treeUp[3] = im.scaledImage(ss2.grabImage(4, 4, 192, 192), 192,192);
			treeUp[4] = im.scaledImage(ss2.grabImage(5, 4, 192, 192), 192,192);
			treeUp[5] = im.scaledImage(ss2.grabImage(6, 4, 192, 192), 192,192);
			
			treeDown[0] = im.scaledImage(ss2.grabImage(1, 4, 192, 192), 192,192);
			treeDown[1] = im.scaledImage(ss2.grabImage(2, 4, 192, 192), 192,192);
			treeDown[2] = im.scaledImage(ss2.grabImage(3, 4, 192, 192), 192,192);
			treeDown[3] = im.scaledImage(ss2.grabImage(4, 4, 192, 192), 192,192);
			treeDown[4] = im.scaledImage(ss2.grabImage(5, 4, 192, 192), 192,192);
			treeDown[5] = im.scaledImage(ss2.grabImage(6, 4, 192, 192), 192,192);
			
			treeRight[0] = im.scaledImage(ss2.grabImage(1, 4, 192, 192), 192,192);
			treeRight[1] = im.scaledImage(ss2.grabImage(2, 4, 192, 192), 192,192);
			treeRight[2] = im.scaledImage(ss2.grabImage(3, 4, 192, 192), 192,192);
			treeRight[3] = im.scaledImage(ss2.grabImage(4, 4, 192, 192), 192,192);
			treeRight[4] = im.scaledImage(ss2.grabImage(5, 4, 192, 192), 192,192);
			treeRight[5] = im.scaledImage(ss2.grabImage(6, 4, 192, 192), 192,192);
			
			treeLeft[0] = im.scaledImage(ss2.grabImage(1, 4, 192, 192), 192,192);
			treeLeft[1] = im.scaledImage(ss2.grabImage(2, 4, 192, 192), 192,192);
			treeLeft[2] = im.scaledImage(ss2.grabImage(3, 4, 192, 192), 192,192);
			treeLeft[3] = im.scaledImage(ss2.grabImage(4, 4, 192, 192), 192,192);
			treeLeft[4] = im.scaledImage(ss2.grabImage(5, 4, 192, 192), 192,192);
			treeLeft[5] = im.scaledImage(ss2.grabImage(6, 4, 192, 192), 192,192);
			
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
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Rectangle getSize() {
		return new Rectangle(x, y, image.getWidth(), image.getHeight());
	}

	@Override
	public void hit() {
		throw new UnsupportedOperationException("Unimplemented method 'hit'");
	}
	
	public void spriteCounter(){
		spriteCounter++;
		if(dead == false) {
			if(attack1 == false && attack2 == false && cutTree == false) {
				//Counter Move and Idle
				if(spriteCounter > 6) {
					if(spriteNum == 1) {spriteNum =2;}
					else if(spriteNum ==2) {spriteNum =3;}
					else if(spriteNum ==3) {spriteNum =4;}
					else if(spriteNum ==4) {spriteNum =5;}
					else if(spriteNum ==5) {spriteNum =6;}
					else if(spriteNum ==6) {spriteNum =1;}
					spriteCounter =0;
				}
			}else if(attack1 == true && attack2 == false && cutTree == false) {
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
			}else if(attack1 == false && attack2 == true && cutTree == false) {
				//Counter Attack2
				if(spriteCounter > 6) {
					if(spriteAttack2 == 1) {spriteAttack2 =2;}
					else if(spriteAttack2 ==2) {spriteAttack2 =3;}
					else if(spriteAttack2 ==3) {spriteAttack2 =4;}
					else if(spriteAttack2 ==4) {spriteAttack2 =5;
						
						attackArea.x = x+64;
						attackArea.y = y+64;
						
						switch(arahAttack) {
						case "atas": attackArea.x = x+48; attackArea.y  = y+32; attackArea.width  = 128; attackArea.height = 40; break;
						case "bawah": attackArea.x = x+48; attackArea.y  = y+128; attackArea.width = 128; attackArea.height = 40; break;
						case "kanan": attackArea.x = x+144; attackArea.y  = y+64; attackArea.width = 40; attackArea.height = 72; break;
						case "kiri": attackArea.x = x+8; attackArea.y  = y+64; attackArea.width = 40; attackArea.height = 72; break;
						}
						
					}else if(spriteAttack2 ==5) {
						spriteAttack2 =6;
					}else if(spriteAttack2 ==6) {
						spriteAttack2 =7;
					}else if(spriteAttack2 ==7) {
						spriteAttack2 =8;
					}else if(spriteAttack2 ==8) {
						spriteAttack2 =9;
						
						attackArea.x = x+64;
						attackArea.y = y+64;
						
						switch(arahAttack) {
						case "atas": attackArea.x = x+48; attackArea.y  = y+32; attackArea.width  = 128; attackArea.height = 40; break;
						case "bawah": attackArea.x = x+48; attackArea.y  = y+128; attackArea.width = 128; attackArea.height = 40; break;
						case "kanan": attackArea.x = x+144; attackArea.y  = y+64; attackArea.width = 40; attackArea.height = 72; break;
						case "kiri": attackArea.x = x+8; attackArea.y  = y+64; attackArea.width = 40; attackArea.height = 72; break;
						}
					}
					else if(spriteAttack2 ==9) {spriteAttack2 =10;}
					else if(spriteAttack2 ==10) {spriteAttack2 =11;}
					else if(spriteAttack2 ==11) {spriteAttack2 =12;}
					else if(spriteAttack2 ==12) {spriteAttack2 =1;}
					spriteCounter =0;
				}
			}else if(attack1 == false && attack2 == false && cutTree == true) {
				//Counter Cut Tree
				if(spriteCounter > 6) {
					if(spriteTree == 1) {spriteTree =2;}
					else if(spriteTree ==2) {spriteTree =3;}
					else if(spriteTree ==3) {spriteTree =4;	hitTree();}
					else if(spriteTree ==4) {spriteTree =5;}
					else if(spriteTree ==5) {spriteTree =6;}
					else if(spriteTree ==6) {spriteTree =1;	cutTree = false;}
					spriteCounter =0;
				}
			}
		}
		else if(dead == true) {
			//Counter Dead
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
				else if(spriteDead == 13) {spriteDead = 14; game.tryWorld.qt.remove(game.tryWorld.qt.search(new Point(this.x, this.y))); game.tryWorld.entity.remove(this);}
				spriteCounter =0;
			}
		}
	}
	
	public void attacking1 (MouseEvent e) {
		attack1 = true;
		speed = 0;
		float px = (float)((e.getX() + game.camera.getX()) - (x + getSize().getWidth()/2));
		float py = (float)((e.getY() + game.camera.getY()) - (y + getSize().getHeight()/2));
		
		float angle = (float) Math.toDegrees(Math.atan2(py, px));

	    if(angle < 0){
	        angle += 360;
	    }
	    if(angle > 315 && angle <= 360 || angle > 0 && angle <= 45) {
	    	arahAttack = "kanan";
	    }else if(angle > 45 && angle <= 135) {
	    	arahAttack = "bawah";
	    }else if(angle > 135 && angle <= 225) {
	    	arahAttack = "kiri";
	    }else {
	    	arahAttack = "atas";
	    }
	}
	
	public void attacking2 (MouseEvent e) {
		attack2 = true;
		speed = 0;
		float px = (float)((e.getX() + game.camera.getX()) - (x + getSize().getWidth()/2));
		float py = (float)((e.getY() + game.camera.getY()) - (y + getSize().getHeight()/2));
		
		float angle = (float) Math.toDegrees(Math.atan2(py, px));

	    if(angle < 0){
	        angle += 360;
	    }
	    if(angle > 315 && angle <= 360 || angle > 0 && angle <= 45) {
	    	arahAttack = "kanan";
	    }else if(angle > 45 && angle <= 135) {
	    	arahAttack = "bawah";
	    }else if(angle > 135 && angle <= 225) {
	    	arahAttack = "kiri";
	    }else {
	    	arahAttack = "atas";
	    }
	}

	@Override
	public void hitTree(){
		
		try {
			hit.getHit = true;
			System.out.println("hit");
		} catch (Exception ex) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void checkTree(MouseEvent e, boolean hitTree){
		Rectangle key = new Rectangle((e.getX() + (int)game.camera.getX())-5, (e.getY() + (int) game.camera.getY())-5, 10, 10);
		for(int i = 0; i < game.tryWorld.objects.size(); i++) {
			GameObject tempObject = game.tryWorld.objects.get(i);
			if(tempObject.getBound().intersects(key.getBounds())&&tempObject.getID() == ID.Block){
				Block tempBlock = (Block) tempObject;
				if(tempBlock.getBlockType() == BlockType.DestroyAble){
					hit = (Block)tempBlock;
					attack1 = false;
					attack2 = false;
					cutTree = true;
					System.out.println("get");
					break;
				}
			}	
		}
		
	}
	
	public void attack2() {
		attack2 = false;
		attackArea = new Rectangle(0, 0, 0, 0);
	}

}
