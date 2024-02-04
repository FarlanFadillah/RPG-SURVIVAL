package com.anim;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class AnimationHandler {
    int spriteCounter = 0;
    int spriteNum = 1;
    

    public void drawAnimation(Graphics2D g2d, BufferedImage image, int[][] mapTile, int imageCode, int size, int offset){
        int col = 0;
        int row = 0;

		while(col<mapTile.length && row<mapTile[0].length) {
			while(col<mapTile.length) {
				int tile = mapTile[col][row];
                if(tile == imageCode){
                    g2d.drawImage(image, col*size -offset, row*size -offset, null);
                }
			    col++;
			}
			if(col==mapTile.length) {
				col=0;
				row++;
			}
		}
    }

    public void spriteCounter8Frame(){

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
                spriteNum =8;
            }else if(spriteNum ==8) {
                spriteNum =1;
            }
            
            spriteCounter =0;
        }

    }
    public BufferedImage animatedSprite8Frame(BufferedImage image, BufferedImage[] images){
        if(spriteNum == 1) {
            return images[0];
        }
        if(spriteNum == 2) {
            return images[1];
        }
        if(spriteNum == 3) {
            return images[2];
        }
        if(spriteNum == 4) {
            return images[3];
        }
        if(spriteNum == 5) {
            return images[4];
        }
        if(spriteNum == 6) {
            return images[5];
        }
        if(spriteNum == 7) {
            return images[6];
        }
        if(spriteNum == 8) {
            return images[7];
        }
        return image;
    }
}
