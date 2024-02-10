package com.ui;

import com.main.Game;

import java.awt.Graphics2D;

public class GUI {
    public PlayerStats ps = new PlayerStats();
    public InventoryGUI inv;
    Game game;
    public GUI(Game game){
        this.game = game;
        inv = new InventoryGUI(game);
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
            inv.drawInventory(g2d);
    }

    
}
