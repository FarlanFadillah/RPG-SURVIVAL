package com.playerlist;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.filehandler.SpriteSheet;
import com.gameMechanics.PlayerInventory;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Entity;
import com.obj.GameObject;
import com.obj.Item;
import com.tile.ImageManager;

public class Fighter extends Entity{
	
	public SpriteSheet ss = new SpriteSheet("/assets/assetsentity/Fighter.png");
	ImageManager im = new ImageManager();
	
	private int spriteAttack1 = 1;
	private int spriteAttack2 = 1;
	
	public Fighter(int x, int y, ID id, EntityType et, EntityClass ec, Game game) {
		super(x, y, id, et, ec, game);
		playerInventory = new PlayerInventory(game);
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
		return new Rectangle(x+80, y +112, 32, 16 );
		
	}

	public Rectangle renderOrder() {
		return new Rectangle(x+80, y +112, 32, 16 );
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
		if(attack1 == false && attack2 == false) {
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
		} else if(attack1 == true && attack2 == false){
				switch (arah) {
				case "atas": 
					if(spriteAttack1 == 1) {
						image = attack1Up[0];
					}
					if(spriteAttack1 == 2) {
						image = attack1Up[1];
					}
					if(spriteAttack1 == 3) {
						image = attack1Up[2];
					}
					if(spriteAttack1 == 4) {
						image = attack1Up[3];
					}
					if(spriteAttack1 == 5) {
						image = attack1Up[4];
					}
					if(spriteAttack1 == 6) {
						image = attack1Up[5];
					}

					break;
					
				case "bawah":
					if(spriteAttack1 == 1) {
						image = attack1Down[0];
					}
					if(spriteAttack1 == 2) {
						image = attack1Down[1];
					}
					if(spriteAttack1 == 3) {
						image = attack1Down[2];
					}
					if(spriteAttack1 ==4) {
						image = attack1Down[3];
					}
					if(spriteAttack1 ==5) {
						image = attack1Down[4];
					}
					if(spriteAttack1 ==6) {
						image = attack1Down[5];
					}

					break;
				case "kanan":
					if(spriteAttack1 == 1) {
						image = attack1Right[0];
					}
					if(spriteAttack1 == 2) {
						image = attack1Right[1];
					}
					if(spriteAttack1 == 3) {
						image = attack1Right[2];
					}
					if(spriteAttack1 ==4) {
						image = attack1Right[3];
					}
					if(spriteAttack1 ==5) {
						image = attack1Right[4];
					}
					if(spriteAttack1 ==6) {
						image = attack1Right[5];
					}

					
					break;
				case "kiri":
					if(spriteAttack1 == 1) {
						image = attack1Left[0];
					}
					if(spriteAttack1 == 2) {
						image = attack1Left[1];
					}
					if(spriteAttack1 == 3) {
						image = attack1Left[2];
					}
					if(spriteAttack1 ==4) {
						image = attack1Left[3];
					}
					if(spriteAttack1 ==5) {
						image = attack1Left[4];
					}
					if(spriteAttack1 ==65) {
						image = attack1Left[5];
					}
					break;
				}
		} else if(attack1 == false && attack2 == true) {
				switch (arah) {
				case "atas": 
					if(spriteAttack2 == 1) {
						image = attack1Up[0];
					}
					if(spriteAttack2 == 2) {
						image = attack1Up[1];
					}
					if(spriteAttack2 == 3) {
						image = attack1Up[2];
					}
					if(spriteAttack2 == 4) {
						image = attack1Up[3];
					}
					if(spriteAttack2 == 5) {
						image = attack1Up[4];
					}
					if(spriteAttack2 == 6) {
						image = attack1Up[5];
					}
					if(spriteAttack2 == 7) {
						image = attack2Up[0];
					}
					if(spriteAttack2 == 8) {
						image = attack2Up[1];
					}
					if(spriteAttack2 == 9) {
						image = attack2Up[2];
					}
					if(spriteAttack2 == 10) {
						image = attack2Up[3];
					}
					if(spriteAttack2 == 11) {
						image = attack2Up[4];
					}
					if(spriteAttack2 == 12) {
						image = attack2Up[5];
					}

					break;
					
				case "bawah":
					if(spriteAttack2 == 1) {
						image = attack1Down[0];
					}
					if(spriteAttack2 == 2) {
						image = attack1Down[1];
					}
					if(spriteAttack2 == 3) {
						image = attack1Down[2];
					}
					if(spriteAttack2 == 4) {
						image = attack1Down[3];
					}
					if(spriteAttack2 == 5) {
						image = attack1Down[4];
					}
					if(spriteAttack2 == 6) {
						image = attack1Down[5];
					}
					if(spriteAttack2 == 7) {
						image = attack2Down[0];
					}
					if(spriteAttack2 == 8) {
						image = attack2Down[1];
					}
					if(spriteAttack2 == 9) {
						image = attack2Down[2];
					}
					if(spriteAttack2 == 10) {
						image = attack2Down[3];
					}
					if(spriteAttack2 == 11) {
						image = attack2Down[4];
					}
					if(spriteAttack2 == 12) {
						image = attack2Down[5];
					}

					break;
				case "kanan":
					if(spriteAttack2 == 1) {
						image = attack1Right[0];
					}
					if(spriteAttack2 == 2) {
						image = attack1Right[1];
					}
					if(spriteAttack2 == 3) {
						image = attack1Right[2];
					}
					if(spriteAttack2 == 4) {
						image = attack1Right[3];
					}
					if(spriteAttack2 == 5) {
						image = attack1Right[4];
					}
					if(spriteAttack2 == 6) {
						image = attack1Right[5];
					}
					if(spriteAttack2 == 7) {
						image = attack2Right[0];
					}
					if(spriteAttack2 == 8) {
						image = attack2Right[1];
					}
					if(spriteAttack2 == 9) {
						image = attack2Right[2];
					}
					if(spriteAttack2 == 10) {
						image = attack2Right[3];
					}
					if(spriteAttack2 == 11) {
						image = attack2Right[4];
					}
					if(spriteAttack2 == 12) {
						image = attack2Right[5];
					}

					
					break;
				case "kiri":
					if(spriteAttack2 == 1) {
						image = attack1Left[0];
					}
					if(spriteAttack2 == 2) {
						image = attack1Left[1];
					}
					if(spriteAttack2 == 3) {
						image = attack1Left[2];
					}
					if(spriteAttack2 == 4) {
						image = attack1Left[3];
					}
					if(spriteAttack2 == 5) {
						image = attack1Left[4];
					}
					if(spriteAttack2 == 6) {
						image = attack1Left[5];
					}
					if(spriteAttack2 == 7) {
						image = attack2Left[0];
					}
					if(spriteAttack2 == 8) {
						image = attack2Left[1];
					}
					if(spriteAttack2 == 9) {
						image = attack2Left[2];
					}
					if(spriteAttack2 == 10) {
						image = attack2Left[3];
					}
					if(spriteAttack2 == 11) {
						image = attack2Left[4];
					}
					if(spriteAttack2 == 12) {
						image = attack2Left[5];
					}
					
					break;
				}
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
		if(attack1 == false && attack2 == false) {
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
				}
				spriteCounter =0;
			}
		}else if(attack1 == true && attack2 == false) {
			if(spriteCounter > 6) {
				if(spriteAttack1 == 1) {
					spriteAttack1 =2;
				}else if(spriteAttack1 ==2) {
					spriteAttack1 =3;
				}else if(spriteAttack1 ==3) {
					spriteAttack1 =4;
				}else if(spriteAttack1 ==4) {
					spriteAttack1 =5;
				}else if(spriteAttack1 ==5) {
					spriteAttack1 =6;
				}else if(spriteAttack1 ==6) {
					spriteAttack1 =1;
					attack1 = false;
				}
				spriteCounter =0;
			}
		}else if(attack1 == false && attack2 == true) {
			if(spriteCounter > 6) {
				if(spriteAttack2 == 1) {
					spriteAttack2 =2;
				}else if(spriteAttack2 ==2) {
					spriteAttack2 =3;
				}else if(spriteAttack2 ==3) {
					spriteAttack2 =4;
				}else if(spriteAttack2 ==4) {
					spriteAttack2 =5;
				}else if(spriteAttack2 ==5) {
					spriteAttack2 =6;
				}else if(spriteAttack2 ==6) {
					spriteAttack2 =7;
				}else if(spriteAttack2 ==7) {
					spriteAttack2 =8;
				}else if(spriteAttack2 ==8) {
					spriteAttack2 =9;
				}else if(spriteAttack2 ==9) {
					spriteAttack2 =10;
				}else if(spriteAttack2 ==10) {
					spriteAttack2 =11;
				}else if(spriteAttack2 ==11) {
					spriteAttack2 =12;
				}else if(spriteAttack2 ==12) {
					spriteAttack2 =1;
				}
				spriteCounter =0;
			}
		}
		
	}
	
	public void attacking1 (MouseEvent e) {
		if(attack1 == false && attack2 == false) {
			attack2 = false;
			attack1 = true;
			float px = (float)((e.getX() + game.camera.getX()) - (x + getSize().getWidth()/2));
			float py = (float)((e.getY() + game.camera.getY()) - (y + getSize().getHeight()/2));
			
			float angle = (float) Math.toDegrees(Math.atan2(py, px));

		    if(angle < 0){
		        angle += 360;
		    }
		    if(angle > 315 && angle <= 360 || angle > 0 && angle <= 45) {
		    	arah = "kanan";
		    }else if(angle > 45 && angle <= 135) {
		    	arah = "bawah";
		    }else if(angle > 135 && angle <= 225) {
		    	arah = "kiri";
		    }else {
		    	arah = "atas";
		    }
		    speed = 0;
		}
	}
	
	public void attacking2 (MouseEvent e) {
		if(attack1 == false && attack2 == false) {
			attack1 = false;
			attack2 = true;
			speed = 0;
			float px = (float)((e.getX() + game.camera.getX()) - (x + getSize().getWidth()/2));
			float py = (float)((e.getY() + game.camera.getY()) - (y + getSize().getHeight()/2));
			
			float angle = (float) Math.toDegrees(Math.atan2(py, px));

		    if(angle < 0){
		        angle += 360;
		    }
		    if(angle > 315 && angle <= 360 || angle > 0 && angle <= 45) {
		    	arah = "kanan";
		    }else if(angle > 45 && angle <= 135) {
		    	arah = "bawah";
		    }else if(angle > 135 && angle <= 225) {
		    	arah = "kiri";
		    }else {
		    	arah = "atas";
		    }
		}
	}

}
