package com.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ai.AINode;
import com.anim.AnimationHandler;
import com.main.Game;
import com.obj.GameObject;
import com.quadTree.Quad;
import com.tile.ObjectManager;
import com.tile.TileManager;
import com.tile.TileMap;

public abstract class Biome {
    Game game;
    public String mapPath; // directori map.tmp
    public String spriteSheetDir;// directory spritesheet.png
    AnimationHandler animHandler = new AnimationHandler();

    //World Map Image
    public BufferedImage worldMap;
    Graphics2D g2dMap;

    //Boolean for render map once
    boolean initiateMap = true;

    public Biome(Game game){
        this.game = game;
    }

    public abstract void tick(double xx, double yy);
    public abstract void draw(Graphics2D g2d, double xx, double yy);
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
        if(initiateMap){
            tilem.draw(g2, xx, yy, tileSet, layer, g2dMap);
        }else{
            tilem.draw(g2, xx, yy, tileSet, layer, null);
        }
    }

    //menammbahkan object layer
    // addObjectLayer("Object Layer 1") menammbahkan object yang ada di Object layer 1 ke dalam ObjectLayer (list)
    public void addObjectLayer(String path, String layerKey, ObjectManager bm, Quad qt, List<GameObject> objects, AINode[][] gameObject){
        bm.TMXFileReaderObject(path, layerKey, qt, objects, gameObject, null);
    }
    //render Object Layer
    // drawObjectLayer(g, objectLayer.get(0), xx, yy) render objectLayer pertama
    //dst (Tergantung berapa layer object yang anda butuhkan)
    public void drawObjectLayer(Graphics2D g, List<GameObject> objects){
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
            objects.get(i).render(g);           
        }
        
    }

    public void addBlockLayer(){

    }
    public void drawBlockLayer(){

    }

    //Menambahkan transparant block yang memiliki collision
    // index -> index yang menunjukkan letak transparen block anda
    public void addSolidLayer(String layerName, ObjectManager bm, int index, Quad qt, List<GameObject> objects, AINode[][] gameObject){
        bm.TMXFileReaderObject(mapPath, layerName, qt, objects, gameObject, null);
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
