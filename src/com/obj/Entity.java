package com.obj;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.blockList.Chest;
import com.blockList.Tree;
import com.filehandler.SpriteSheet;
import com.gameMechanics.PlayerEquipment;
import com.gameMechanics.PlayerInventory;
import com.gameMechanics.Skills;
import com.gameMechanics.Skilltree;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;

public abstract class Entity extends GameObject {
    //Equipment being held
	public String holdingTools = "hand";
	public String hands = "hand";
	public String weapon = "weapon";
	public String axe = "axe";
	
	public int speed;
	public boolean isOpeningChest = false;
    public boolean up = false, down = false, right= false, left = false;
    public boolean dead = false;
    public String name;
    public int level,mana, xp, skillPoint = 2;
	public float stamina;
    public float velX=0, velY=0;
    public EntityType et;
    public Game game;
	protected EntityClass ec;

	public PlayerInventory playerInventory;
	public PlayerEquipment temporaryPlayerEquipment;
	public PlayerEquipment playerEquipment;
	public Skills skills;
	public Skilltree skillTree;
	
	public SpriteSheet ss;
	public String arah; //tanda arah
	public String arahAttack; //tanda arah attack
	public String arahTree; //tanda arah tree
	
	public BufferedImage[] death = new BufferedImage[14];
	
	public BufferedImage[] runDown = new BufferedImage[6];
	public BufferedImage[] runUp = new BufferedImage[6];
	public BufferedImage[] runRight = new BufferedImage[6];
	public BufferedImage[] runLeft = new BufferedImage[6];

	public BufferedImage[] idleDown = new BufferedImage[6];
	public BufferedImage[] idleUp = new BufferedImage[6];
	public BufferedImage[] idleRight = new BufferedImage[6];
	public BufferedImage[] idleLeft = new BufferedImage[6];
	
	public BufferedImage[] idle2Down = new BufferedImage[6];
	public BufferedImage[] idle2Up = new BufferedImage[6];
	public BufferedImage[] idle2Right = new BufferedImage[6];
	public BufferedImage[] idle2Left = new BufferedImage[6];
	
	public BufferedImage[] idletransitionIn = new BufferedImage[6];
	public BufferedImage[] idletransitionOut = new BufferedImage[6];
	
	public BufferedImage[] attack1Down = new BufferedImage[6];
	public BufferedImage[] attack1Up = new BufferedImage[6];
	public BufferedImage[] attack1Right = new BufferedImage[6];
	public BufferedImage[] attack1Left = new BufferedImage[6];
	
	public BufferedImage[] attack1UpRight = new BufferedImage[6];
	public BufferedImage[] attack1UpLeft = new BufferedImage[6];
	public BufferedImage[] attack1DownRight = new BufferedImage[6];
	public BufferedImage[] attack1DownLeft = new BufferedImage[6];
	
	public BufferedImage[] attack2Down = new BufferedImage[6];
	public BufferedImage[] attack2Up = new BufferedImage[6];
	public BufferedImage[] attack2Right = new BufferedImage[6];
	public BufferedImage[] attack2Left = new BufferedImage[6];
	
	public BufferedImage[] treeUp = new BufferedImage[6];
	public BufferedImage[] treeDown = new BufferedImage[6];
	public BufferedImage[] treeRight = new BufferedImage[6];
	public BufferedImage[] treeLeft = new BufferedImage[6];
	
	public BufferedImage[] attackTNTDown = new BufferedImage[7];
	public BufferedImage[] attackTNTUp = new BufferedImage[7];
	public BufferedImage[] attackTNTRight = new BufferedImage[7];
	public BufferedImage[] attackTNTLeft = new BufferedImage[7];
	
	public BufferedImage[] attackBarrel = new BufferedImage[3];
	
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public Rectangle rangeArea = new Rectangle(0, 0, 0, 0);


	public int spriteNum = 1 ; // Counter Animation
	public int spriteCounter = 0;
	public int spriteDead = 1;
	public boolean attack1 = false;
	public boolean attack2 = false;
	public boolean cutTree = false;
	
	public boolean onPath = false;

    public Entity(int x, int y, ID id, EntityType et, EntityClass ec, Game game) {
        super(x, y, id);
        this.et = et;
		this.ec = ec;
        this.game = game;
    }
    
    public void setAction() {
    	
    }

    public void tick(){
		spriteCounter();
	}
    public abstract void render(Graphics g);
    public abstract void Collision();
	public abstract void getImage();
	public abstract void attacking1(MouseEvent e);
	public abstract void attacking2(MouseEvent e);
	public abstract void checkEquipment(MouseEvent e);
	public void changeEquipment(String EquipmentNum){
		holdingTools = EquipmentNum;
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

    public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}

    public EntityType getEntityType(){
        return et;
    }
    
    public EntityClass getEntityClass(){
        return ec;
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
				}
				
				spriteCounter =0;
			}
			
	}

	public void stopMove() {
		setUp(false);
        setDown(false);
        setRight(false);
        setLeft(false);
	}
	
	public Block hit = null;
	
	public abstract void hitTree();
	public abstract void checkTree(MouseEvent e, boolean hitTree);
	
	public abstract void attack2();
    
	public void searchPath(int goalCol, int goalRow) {
		
		int startCol = x/64;
		int startRow = y/64;
		
		game.pFinder.setAINodes(startCol, startRow, goalCol, goalRow, this);
		
		if(game.pFinder.search() == true) {
			
			// Next worldX & worldY
			int nextX = game.pFinder.pathList.get(0).col * 64;
			int nextY = game.pFinder.pathList.get(0).row * 64;
			
			// Entity's Solid Area postion
			int enLeftX = x;
			int enRightX = x + getBound().width;
			int enTopY = y;
			int enBottomY = y + getBound().height;
			
			if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + 64) {
				arah = "atas";
				System.out.println(arah);
			}
			else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + 64) {
				arah = "bawah";
				System.out.println(arah);
			}
			else if(enTopY >= nextY && enBottomY < nextY + 64) {
				// left or right
				if(enLeftX > nextX) {
					arah = "kiri";
					System.out.println(arah);
				}
				if(enLeftX < nextX) {
					arah = "kanan";
					System.out.println(arah);
				}
			}
			
			//int nextCol = game.pFinder.pathList.get(0).col;
			//int nextRow = game.pFinder.pathList.get(0).row;
			
			//if(nextCol == goalCol && nextRow == goalRow) {
			//	onPath = false;
			//}
			
		}
		
	}
	public void automationEquipment(MouseEvent e){
		Rectangle mouse = new Rectangle(e.getX()+(int)game.camera.getX(), e.getY()+(int)game.camera.getY(), 1, 1);
		for (int i = 0; i < game.tryWorld.objects.size(); i++) {
			GameObject temp = game.tryWorld.objects.get(i);
			if(temp.getBound().contains(mouse) && temp.getClass().getSimpleName().equals(Tree.class.getSimpleName())){
				holdingTools = axe;
				return;
			}else if(temp.getBound().contains(mouse) && temp.getClass().getSimpleName().equals(Chest.class.getSimpleName())){
				holdingTools = hands;
				return;
			}else{
				if(holdingTools == axe) holdingTools = hands;
			}
		}	
		for (int i = 0; i < game.tryWorld.entity.size(); i++) {
			GameObject temp = game.tryWorld.entity.get(i);
			if(this!= temp && temp.getBound().contains(mouse)){
				holdingTools = weapon;
				return;
			}
		}
	}

    public abstract void openChest(MouseEvent e);

	public void checkMouseHoverOnObject(Rectangle mouse){
		for (int i = 0; i < game.tryWorld.objects.size(); i++) {
			GameObject temp = game.tryWorld.objects.get(i);
			if(temp.getBound().contains(mouse)){
				temp.hover = true;
				return;
			}else{
				temp.hover = false;
			}
		}
		for (int i = 0; i < game.tryWorld.entity.size(); i++) {
			GameObject temp = game.tryWorld.entity.get(i);
			if(temp.getBound().contains(mouse)){
				temp.hover = true;
				return;
			}else{
				temp.hover = false;
			}
		}
	}
}
