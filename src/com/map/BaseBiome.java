package com.map;

import java.awt.Graphics2D;

import com.main.Game;
import com.tile.TileManager;
import com.tile.TileMap;

public class BaseBiome {
    public TileManager tilem;
    Game game;
    TileMap[] layer1;
    int[][] mapTile = new int[0][0];
    int[][] mapTile2 = new int[0][0];
    public BaseBiome(Game game){
        tilem = new TileManager(game);
        this.game = game;
        layer1 = new TileMap[1000];
        mapTile = tilem.TMXFileReader("/assets/Terrain/Base.tmx", "Tile Layer 1", mapTile);
        mapTile2 = tilem.TMXFileReader("/assets/Terrain/Base.tmx", "Tile Layer 2", mapTile2);
		tilem.getTiles("/assets/Terrain/Tilemap_Flat.png", layer1, 64);
    }

    public void draw(Graphics2D g2d, double xx, double yy){
        tilem.draw(g2d, xx, yy, layer1, mapTile);
        tilem.draw(g2d, xx, yy, layer1, mapTile2);
    }

    
}
