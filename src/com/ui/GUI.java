package com.ui;

import com.main.Game;
import com.obj.Entity;

import java.awt.Color;
import java.awt.Graphics2D;

public class GUI {
    public PlayerStats ps = new PlayerStats();
    public InventoryGUI inv;
    Game game;
    Entity player;
    
    public GUI(Game game){
        this.game = game;
        inv = new InventoryGUI(game);
        player = game.getPlayerObject();
        init();
    }
    public void init(){
        ps.checkPlayer(game);
    }
    public void tick(){
        inv.tick(game.gameState == game.InventoryState);
    }

    public void draw(Graphics2D g2d){
            ps.drawPlayerIcon(g2d);
            ps.drawPlayerStats(g2d);
            drawGuide(g2d);
            inv.drawInventory(g2d);   
    }
    public void drawGuide(Graphics2D g2d) {
    	g2d.drawString("c : open inventory", 0, Game.HEIGHT-64);
    }

    
}
