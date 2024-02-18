package com.ui;

import com.gameMechanics.PlayerEquipment;
import com.main.Game;
import com.obj.Entity;

import java.awt.Graphics2D;

public class GUI {
    public PlayerStats ps = new PlayerStats();
    public InventoryGUI inv;
    Game game;
    Entity player;
    SkillUi skillUi;
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
    }

    public void draw(Graphics2D g2d){
            ps.drawPlayerIcon(g2d);
            ps.drawPlayerStats(g2d);
            skillUi.drawSkillSlot(g2d);
            inv.drawInventory(g2d); 
    }
    

    
}
