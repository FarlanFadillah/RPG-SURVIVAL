package com.ui;

import com.main.Game;

import java.awt.Graphics2D;

public class GUI {
    PlayerStats ps = new PlayerStats();
    Inventory inv = new Inventory();
    public boolean openInventory = false;
    Game game;
    public GUI(Game game){
        this.game = game;
        init();
    }
    public void init(){
        ps.checkPlayer(game);
    }

    public void draw(Graphics2D g2d){
        if(game.gameState == game.playState){
            ps.drawPlayerIcon(g2d);
            ps.drawPlayerStats(g2d);
            if(openInventory){
                inv.drawInventory(g2d, 16, 16);
            }
        }
    }

    
}
