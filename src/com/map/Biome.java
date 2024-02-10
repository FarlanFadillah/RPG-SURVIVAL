package com.map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.anim.AnimationHandler;
import com.main.Game;
import com.obj.GameObject;
import com.tile.ObjectManager;
import com.tile.TileManager;
import com.tile.TileMap;

public abstract class Biome {
    Game game;
    public String mapPath; // directori map.tmp
    public String spriteSheetDir;// directory spritesheet.png
    AnimationHandler animHandler = new AnimationHandler();
    public Biome(Game game){
        this.game = game;
    }

    public abstract void tick(double xx, double yy);
    public abstract void draw(Graphics g, Graphics2D g2d, double xx, double yy);
    public abstract void init();


    // menambahkan terrain layer ke dalam terrainLayer(list)
    // terrainLayer.get(0) untuk mengambil layer paling dasar
    // terrainLayer.get(1) mengambil layer kedua
    // dst
    public void addTerrainLayer(String path, String layerKey, ArrayList<int[][]> terrainLayer, TileManager tilem){
        terrainLayer.add(tilem.TMXFileReader(path, layerKey));
    }

    //render terrainlayer ke canvas
    //drawTerrainLayer(g2, xx, yy, tileset, terrainLayer.get(0)) untuk render layer paling dasar
    //drawTerrainLayer(g2, xx, yy, tileset, terainLayer.get(2)) untuk render layer ke2
    //dst
    //xx, dan yy, adalah variable dari camera.
    //tile set merupakan kumpulan spritesheet untuk terrain
    public void drawTerrainLayer(Graphics2D g2, double xx, double yy, TileMap[] tileSet, int[][] layer, TileManager tilem){
        tilem.draw(g2, xx, yy, tileSet, layer);
    }

    //menammbahkan object layer
    // addObjectLayer("Object Layer 1") menammbahkan object yang ada di Object layer 1 ke dalam ObjectLayer (list)
    public void addObjectLayer(String path, String layerKey,ArrayList<ArrayList<GameObject>> objectLayer, ObjectManager bm){
        ArrayList<GameObject> objects = new ArrayList<>();
        bm.TMXFileReaderObject(path, layerKey, objects);
        objectLayer.add(objects);
    }
    //render Object Layer
    // drawObjectLayer(g, objectLayer.get(0), xx, yy) render objectLayer pertama
    //dst (Tergantung berapa layer object yang anda butuhkan)
    public void drawObjectLayer(Graphics g, ArrayList<GameObject> objects, double xx, double yy){
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
            int x1 = objects.get(i).getX();
            int y1 = objects.get(i).getY();
                if( x1 < xx+ Game.WIDTH && x1 > xx - objects.get(i).getSize().getWidth() && y1 <yy+Game.HEIGHT && y1 > yy - objects.get(i).getSize().getHeight()){
                    objects.get(i).render(g);           
                }
        }
        
    }

    public void addBlockLayer(){

    }
    public void drawBlockLayer(){

    }

    //Menambahkan transparant block yang memiliki collision
    // index -> index yang menunjukkan letak transparen block anda
    public void addSolidLayer(ArrayList<ArrayList<GameObject>> objectLayer, String layerName, ObjectManager bm, int index){
        bm.TMXFileReaderObject(mapPath, layerName, objectLayer.get(index));
    }

    //membaca sekaligus membagi spriteSheet dengan grid 64 pixel
    public void getTileSet(TileMap[] tileSet, TileManager tilem){
        tilem.getTiles(spriteSheetDir, tileSet, 64);
    }

    //Render animation
    //maptile = array yang akan dibaca sehingga didapat posisi tile yang akan dianimasikan
    //tile num nomor tile yang akan dianimasikan
    // offset (optional) digunakan jika tile img kurang akurat sehinga perlu digeser (+10) jika ingin digeser ke kiri 10 pixel
    // (-10) jika ingin digeser ke kanan 10 pixel
    public void drawAnimation(Graphics2D g2d, BufferedImage image, int[][] maptile, int tilenum, int size, int offset, double xx, double yy){
        animHandler.drawAnimation(g2d, image, maptile, tilenum, size, offset, xx, yy);
    }

    
}
