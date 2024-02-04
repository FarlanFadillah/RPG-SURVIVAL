package com.map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.anim.AnimationHandler;
import com.anim.Foam;
import com.main.Game;
import com.obj.GameObject;
import com.tile.ObjectManager;
import com.tile.TileManager;
import com.tile.TileMap;

public class TryWorld extends Biome{
    
    Game game;

    ArrayList<int[][]> terrainLayer = new ArrayList<>();
    public ArrayList<ArrayList<GameObject>> objectLayer = new ArrayList<>();
    TileMap[] tileSet = new TileMap[1000];

    public TileManager tilem;
    public ObjectManager bm;

    Foam foams = new Foam();
    BufferedImage foam;

    AnimationHandler animHandler = new AnimationHandler();
   
    public TryWorld(Game game) {
        super(game);
        bm = new ObjectManager(game);
        tilem = new TileManager(game);
        this.mapPath = "/assets/Terrain/Base.tmx";
        this.spriteSheetDir = "/assets/Terrain/Tilemap_Flat.png";
        //TODO Auto-generated constructor stub
        foam = foams.idle[0];
        init();
    }
    @Override
    public void tick() {
        // TODO Auto-generated method stub
        animHandler.spriteCounter8Frame();
        for (int i = 0; i < objectLayer.get(0).size(); i++) {
            objectLayer.get(0).get(i).tick();
        }
    }

    @Override
    public void draw(Graphics g, Graphics2D g2d, double xx, double yy) {
        // TODO Auto-generated method stub
        drawTerrainLayer(g2d, xx, yy, tileSet, terrainLayer.get(0), tilem);
        foam = animHandler.animatedSprite8Frame(foam, foams.idle);
        drawAnimation(g2d, foam,terrainLayer.get(0), 42, 64, 32);
        drawTerrainLayer(g2d, xx, yy, tileSet, terrainLayer.get(1), tilem);
        drawObjectLayer(g2d, objectLayer.get(0), xx, yy);
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        getTileSet(tileSet, tilem);
        addTerrainLayer(mapPath, "Tile Layer 1", terrainLayer, tilem); // layer pertama air
        addTerrainLayer(mapPath, "Tile Layer 2", terrainLayer, tilem); // layer kedua tanah
        addObjectLayer(mapPath, "Object Layer 1", objectLayer, bm); // object layer pertama
        addSolidLayer(objectLayer,"solidLayer" , bm, 0);// transparan blok collision
    }
}
