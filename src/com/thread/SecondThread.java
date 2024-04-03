package com.thread;

import com.main.Game;

public class SecondThread extends Thread{
    Game game;
    public SecondThread(Game game){
        this.game = game;
    }
    @Override
    public void run() {

        game.gui.mh.drawMapTerrain(game.tryWorld.terrainLayer.get(0), game.tryWorld.tilem.WIDTHMAP, game.tryWorld.tilem.HEIGHTMAP, 64);
        game.gui.mh.drawMapTerrain(game.tryWorld.terrainLayer.get(1), game.tryWorld.tilem.WIDTHMAP, game.tryWorld.tilem.HEIGHTMAP, 64);
        game.gui.mh.pixelatedImage(10, game.gui.mh.worldMap);
        game.gui.mh.pixelatedObject(); 
    }
    
}
