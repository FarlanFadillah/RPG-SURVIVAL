package com.obj;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.filehandler.SpriteSheet;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;

public abstract class Entity extends GameObject {
    public int speed;
    public boolean up = false, down = false, right= false, left = false;
    public String name;
    public int mana, xp;
	public float stamina;
    public float velX=0, velY=0;
    public EntityType et;
    public Game game;
	protected EntityClass ec;


	public SpriteSheet ss;
	public String arah; //tanda arah
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
	
	public BufferedImage[] attack2Down = new BufferedImage[6];
	public BufferedImage[] attack2Up = new BufferedImage[6];
	public BufferedImage[] attack2Right = new BufferedImage[6];
	public BufferedImage[] attack2Left = new BufferedImage[6];
	


	public int spriteNum = 1 ; // Counter Animation
	public int spriteCounter = 0;
	public boolean attack1 = false;
	public boolean attack2 = false;

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
    
}
