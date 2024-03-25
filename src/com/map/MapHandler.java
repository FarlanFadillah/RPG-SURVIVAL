package com.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import com.main.Game;
import com.obj.GameObject;
import com.tile.ImageManager;


public class MapHandler {
    Game game;
    //World Map Image
    public BufferedImage worldMap;
    public Graphics2D g2dMap;
    public List<GameObject> objects;
    ImageManager im = new ImageManager();
    public MapHandler(Game game, int WIDTH, int HEIGHT){
        this.game = game;
        worldMap = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2dMap = (Graphics2D) worldMap.createGraphics();
        objects = game.tryWorld.bm.allGameObjects;
    }

    public void drawMapTerrain(int[][] maptile, int WIDTHMAP, int HEIGHTMAP, int pixels) {
		int col=0;
		int row=0;
		while(col<WIDTHMAP && row<HEIGHTMAP) {
			while(col<WIDTHMAP) {
				int tile = maptile[col][row];
                g2dMap.drawImage(game.tryWorld.tileSet[tile].image, col*pixels, row*pixels, null);
			    col++;
			}
			if(col==WIDTHMAP) {
				col=0;
				row++;
			}
		}
		
	}

    public void drawMap(Graphics2D g2d, int mapX, int mapY, double currentScale) {
        // TODO Auto-generated method stub
        g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g2d.drawImage(worldMap, mapX, mapY, (int)(worldMap.getWidth() * currentScale), (int)(worldMap.getHeight() * currentScale), null);

        try {
            Collections.sort(objects, new Comparator<GameObject>() {
    
                @Override
                public int compare(GameObject o1, GameObject o2) {
                    int y1 = (int) o1.renderOrder().getY();
                    int y2 = (int) o2.renderOrder().getY();
                    return Integer.compare(y1, y2);
                }
                
            });
        } catch (Exception e) {
            // TODO: handle exception
        }

        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            g2d.drawImage(temp.image, mapX+(int)(temp.x*currentScale),mapY+(int) (temp.y*currentScale),(int)(temp.image.getWidth()*currentScale),(int) (temp.image.getHeight()*currentScale), null);         
        }
    }
}
