package com.ui;

import com.main.Game;
import com.obj.Entity;

import java.awt.Graphics2D;

public class GUI {
    public PlayerStats ps = new PlayerStats();
    public InventoryGUI inv;
    Game game;
    Entity player;
    public SkillUi skillUi;
    public GUI(Game game){
        this.game = game;
        inv = new InventoryGUI(game);
        player = game.getPlayerObject();
        skillUi = new SkillUi(game);
        init();
    }
    public void init(){
        ps.checkPlayer(game);
    }
    public void tick(){
        inv.tick(game.gameState == game.InventoryState);
        skillUi.tick(game.gameState == game.skillTabState);
    }

    public void draw(Graphics2D g2d){
        if(game.gameState == game.mapState){
            g2d.drawImage(game.tryWorld.worldMap, 0, 0, Game.WIDTH, Game.HEIGHT, null);
        }else{
            g2d.setFont(ps.f1);
            String word = "FPS :" +String.valueOf(game.Guifps);
            g2d.drawString(word, Game.WIDTH-inv.getWidthString(g2d, word)-16, 32);
            ps.drawPlayerIcon(g2d);
            ps.drawPlayerStats(g2d);
            skillUi.drawSkillSlot(g2d);
            inv.drawInventory(g2d);  
        }
    }
    

    
}
