package com.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.List;

import java.util.Collections;
import java.util.Comparator;

import com.main.Game;
import com.obj.Entity;
import com.tile.ImageManager;
import com.ui.MapObjects;


public class MapHandler {
    Game game;
    //World Map Image
    public BufferedImage worldMap;
    public Graphics2D g2dMap;
    public List<MapObjects> objects;
    ImageManager im = new ImageManager();

    boolean firstGenerateMiniMap = true;
    public boolean mapDone = false;
    public MapHandler(Game game, int WIDTH, int HEIGHT){
        this.game = game;
        worldMap = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2dMap = (Graphics2D) worldMap.createGraphics();
        objects = game.tryWorld.bm.allMapObjects;

        minimapImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2dMiniMap = minimapImage.createGraphics();


        
    }

    public void drawMapTerrain(int[][] maptile, int WIDTHMAP, int HEIGHTMAP, int pixels) {
		int col=0;
		int row=0;
		while(col<WIDTHMAP && row<HEIGHTMAP) {
			while(col<WIDTHMAP) {
				int tile = maptile[col][row];
                g2dMap.drawImage(game.tryWorld.tileSet[tile].image, col*pixels, row*pixels, null);
                g2dMiniMap.setColor(getCenterColor(game.tryWorld.tileSet[tile].image));
                g2dMiniMap.fillRect(col*pixels, row*pixels, 64, 64);
			    col++;
			}
			if(col==WIDTHMAP) {
				col=0;
				row++;
			}
		}
		
	}

    public void pixelatedObject(){
        for (int i = 0; i < objects.size(); i++) {
            if(objects.get(i).type.equals("inAnimated")) pixelatedImage(10, objects.get(i).image);
        }
        this.mapDone = true;
    }

    public void drawMap(Graphics2D g2d, int mapX, int mapY, double currentScale) {
        // TODO Auto-generated method stub
        g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g2d.drawImage(worldMap, mapX, mapY, (int)(worldMap.getWidth() * currentScale), (int)(worldMap.getHeight() * currentScale), null);

        try {
            Collections.sort(objects, new Comparator<MapObjects>() {
    
                @Override
                public int compare(MapObjects o1, MapObjects o2) {
                    int y1 = (int) o1.y;
                    int y2 = (int) o2.y;
                    return Integer.compare(y1, y2);
                }
                
            });
        } catch (Exception e) {
            // TODO: handle exception
        }

        for (int i = 0; i < objects.size(); i++) {
            MapObjects temp = objects.get(i);
            if(temp.type.equals("inAnimated")){
                g2d.drawImage(temp.image, mapX+(int)(temp.x*currentScale),mapY+(int) (temp.y*currentScale),(int)(temp.image.getWidth()*currentScale),(int) (temp.image.getHeight()*currentScale), null);         
            }else{
                g2d.drawImage(temp.image, mapX+(int)(temp.x*currentScale),mapY+(int) (temp.y*currentScale),(int)(temp.image.getWidth()*currentScale*1.5),(int) (temp.image.getHeight()*currentScale*1.5), null);         
            }
        }
    }
    BufferedImage miniMap;
    public void drawMiniMap(Graphics2D g2d, int x, int y){
        Entity player = game.getPlayerObject();
        int offs = 400;
        double scale = 0.3;
        int xx = x - offs, yy = y- offs, w = Game.WIDTH+(offs*2), h = Game.HEIGHT+(offs*2);

        int xp = Game.WIDTH-(int)(Game.WIDTH*scale)-16, yp = 32;
        if(xx < 0){
            xx = 0;
            xp--;
        }

        if(xx >= worldMap.getWidth() - Game.WIDTH){
            xx = worldMap.getWidth() - Game.WIDTH;
        }

        if(yy < 0){
            yy =0;
            yp--;
        }

        if(yy >= worldMap.getHeight() - Game.HEIGHT){
            yy = worldMap.getHeight() - Game.HEIGHT;
        }
        miniMap = minimapImage.getSubimage(xx, yy, w, h);

        g2d.drawImage(miniMap, Game.WIDTH-(int)(Game.WIDTH*scale)-16, 32, (int)(Game.WIDTH*scale), (int)(Game.HEIGHT*scale), null);
        g2d.drawImage(player.idleDown[0], xp, yp,(int)(player.idleDown[0].getWidth()*scale), (int)(player.idleDown[0].getHeight()*scale), null);
    }

    public BufferedImage currentMiniMap;
    
    public BufferedImage minimapImage;
    Graphics2D g2dMiniMap;

    public Color getCenterColor(BufferedImage image) {
        
        return new Color(image.getRGB(image.getWidth()/2, image.getHeight()/2));
    }

    public void pixelatedImage(int pixel, BufferedImage image){
        // How big should the pixelations be?
        final int PIX_SIZE = pixel;

        // Read the file as an Image

        // Get the raster data (array of pixels)
        Raster src = image.getData();

        // Create an identically-sized output raster
        WritableRaster dest = src.createCompatibleWritableRaster();

        // Loop through every PIX_SIZE pixels, in both x and y directions
        for(int y = 0; y < src.getHeight(); y += PIX_SIZE) {
            for(int x = 0; x < src.getWidth(); x += PIX_SIZE) {

                // Copy the pixel
                double[] pixels = new double[4];
                pixels = src.getPixel(x, y, pixels);

                // "Paste" the pixel onto the surrounding PIX_SIZE by PIX_SIZE neighbors
                // Also make sure that our loop never goes outside the bounds of the image
                for(int yd = y; (yd < y + PIX_SIZE) && (yd < dest.getHeight()); yd++) {
                    for(int xd = x; (xd < x + PIX_SIZE) && (xd < dest.getWidth()); xd++) {
                        dest.setPixel(xd, yd, pixels);
                    }
                }
            }
        }

        // Save the raster back to the Image
        image.setData(dest);
    }
}
