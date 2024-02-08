package com.ui;

import com.main.Game;

import java.awt.Graphics2D;

public class GUI {
    PlayerStats ps = new PlayerStats();
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
        }
    }

    
}
