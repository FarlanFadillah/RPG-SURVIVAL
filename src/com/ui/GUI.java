package com.ui;

import com.main.Game;
import com.map.MapHandler;
import com.obj.Entity;

import java.awt.Graphics2D;

public class GUI {
    public PlayerStats ps = new PlayerStats();
    public InventoryGUI inv;
    Game game;
    Entity player;
    public SkillUi skillUi;
    public ScrollingMessages sm;
    public MapHandler mh;

    public int mapX, mapY;
    public double currentScale;
    public GUI(Game game){
        this.game = game;
        inv = new InventoryGUI(game);
        player = game.getPlayerObject();
        skillUi = new SkillUi(game);
        init();
        sm = new ScrollingMessages(game);

        mh = new MapHandler(game, game.tryWorld.tilem.WIDTHMAP*64, game.tryWorld.tilem.HEIGHTMAP*64);
        
        resetMap();
    }
    public void init(){
        ps.checkPlayer(game);
    }
    public void tick(){
        sm.tick();
        inv.tick(game.gameState == game.InventoryState);
        skillUi.tick(game.gameState == game.skillTabState);
    }
    
    public void draw(Graphics2D g2d){
        if(game.gameState == game.mapState){
            mh.drawMap(g2d, mapX, mapY, currentScale);
        }else{

            g2d.setFont(ps.f1);
            String word = "FPS :" +String.valueOf(game.Guifps);
            g2d.drawString(word, Game.WIDTH-inv.getWidthString(g2d, word)-16, 32);
            ps.drawPlayerIcon(g2d);
            ps.drawPlayerStats(g2d);
            mh.drawMiniMap(g2d, (int)game.camera.getX(), (int)game.camera.getY());
            if(sm.messages.size() > 0){
                sm.showMessage(g2d, 128);
            }
            skillUi.drawSkillSlot(g2d);
            inv.drawInventory(g2d);  
        }

    }
    public void resetMap() {
        // TODO Auto-generated method stub
        mapX = 0;
        mapY = 0;
        currentScale = 0.1406408618241001;
    }
    

    
}
