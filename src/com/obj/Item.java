package com.obj;


import java.awt.image.BufferedImage;

import com.id.ID;
import com.id.ItemType;

public abstract class Item extends GameObject{
    public boolean spawn = false;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public BufferedImage spawnImage[] = new BufferedImage[7];
    public BufferedImage icon;
    public Item(int x, int y, ID id, ItemType it) {
        super(x, y, id);
    }

    public abstract void spawn();

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
                spriteNum =7;
            }else if(spriteNum ==7) {
                spriteNum =1;
            }
            
            spriteCounter =0;
        }
        
}

	public void reSpawn(int x, int y) {
		this.x = x;
		this.y = y;
		spawn = false;
		spriteNum = 1;
		spawn();
		
	}
}
