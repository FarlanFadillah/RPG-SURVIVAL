package com.map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.anim.Foam;
import com.main.Game;
import com.tile.BlockManager;
import com.tile.TileManager;
import com.tile.TileMap;

public class BaseBiome {
    public TileManager tilem;
    public BlockManager bm;
    Game game;
    public TileMap[] layer1;
    int[][] mapTile = new int[0][0];
    int[][] mapTile2 = new int[0][0];
    int[][] mapTile3 = new int[0][0];

    int[][] blockTile = new int[0][0];
    Foam foams = new Foam();
    public int spriteCounter = 0;
    public int spriteNum = 1;
    BufferedImage foam;
    private String mapPath = "/assets/Terrain/Base.tmx";
    private String imagePath = "/assets/Terrain/Tilemap_Flat.png";

    public BaseBiome(Game game){
        this.game = game;
        tilem = new TileManager(game);
        bm = new BlockManager(game);
        layer1 = new TileMap[1000];
        foam = foams.idle[0];
        loadTerrain();
        loadBlock();
        solidLayer();
    }

    public void loadBlock(){
        bm.TMXFileReaderObject(mapPath, "Object Layer 1");

        blockTile = bm.TMXFileReaderBlock(mapPath, "Tile Layer 3", blockTile);
        bm.loadBlock(blockTile, layer1);
    }

    public void loadTerrain(){
        //Tile Render
        mapTile = tilem.TMXFileReader(mapPath, "Tile Layer 1", mapTile);
        mapTile2 = tilem.TMXFileReader(mapPath, "Tile Layer 2", mapTile2);
        mapTile3 = tilem.TMXFileReader(mapPath, "Tile Layer 4", mapTile3);
		tilem.getTiles(imagePath, layer1, 64);
    }
    public void tick(){
        spriteCounter();
    }

    public void draw(Graphics2D g2d, Graphics g, double xx, double yy){
        tilem.draw(g2d, xx, yy, layer1, mapTile);//base layer
        animatedSprite();
        drawFoam(g2d);
        tilem.draw(g2d, xx, yy, layer1, mapTile2);// second layer
    }
    public void drawUpperLayer(Graphics2D g2d, Graphics g, double xx, double yy){
        tilem.draw(g2d, xx, yy, layer1, mapTile3);
    }

    public void solidLayer(){
        bm.TMXFileReaderObject(mapPath, "solidLayer");
    }

    public void drawFoam(Graphics2D g2d){
        int col = 0;
        int row = 0;

		while(col<mapTile.length && row<mapTile[0].length) {
			while(col<mapTile.length) {
				int tile = mapTile[col][row];
                if(tile == 42){
                    g2d.drawImage(foam, col*64 -32, row*64 -32, null);
                }
			    col++;
			}
			if(col==mapTile.length) {
				col=0;
				row++;
			}
		}
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
                spriteNum =7;
            }else if(spriteNum ==7) {
                spriteNum =8;
            }else if(spriteNum ==8) {
                spriteNum =1;
            }
            
            spriteCounter =0;
        }

    }
    public void animatedSprite(){
        if(spriteNum == 1) {
            foam = foams.idle[0];
        }
        if(spriteNum == 2) {
            foam = foams.idle[1];
        }
        if(spriteNum == 3) {
            foam = foams.idle[2];
        }
        if(spriteNum == 4) {
            foam = foams.idle[3];
        }
        if(spriteNum == 5) {
            foam = foams.idle[4];
        }
        if(spriteNum == 6) {
            foam = foams.idle[5];
        }
        if(spriteNum == 7) {
            foam = foams.idle[6];
        }
        if(spriteNum == 8) {
            foam = foams.idle[7];
        }
    }

    

    
}
