package com.map;

public abstract class Biome {
    public int spriteCounter = 1;
    public int spriteNum = 0;

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
