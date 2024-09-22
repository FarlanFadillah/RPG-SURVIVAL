package com.ui;

import com.buildingMechanics.BlueprintGUI;
import com.buildingMechanics.TileInteractionGui;
import com.id.ID;
import com.main.Game;
import com.map.MapHandler;
import com.obj.Entity;

import java.awt.Graphics2D;

public class GUI {
    //Mouse Moving check
    public boolean mouseIsMoving = false;

    public PlayerStats ps = new PlayerStats();
    public InventoryGUI inv;
    public TileInteractionGui tileMec;
    public BlueprintGUI blueprintGUI;
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
        tileMec = new TileInteractionGui(game);
        blueprintGUI = new BlueprintGUI(game);
        resetMap();
    }
    public void init(){
        ps.checkPlayer(game);
    }
    public void tick(){
        sm.tick();
        inv.tick(game.gameState == ID.INVENT_STATE);
        skillUi.tick(game.gameState == ID.SKILLTAB_STATE);
        blueprintGUI.tick();
        tileMec.hoverTilesIfPlayerMoving();
    }
    
    public void draw(Graphics2D g2d){
        if(game.gameState == ID.MAP_STATE){
            mh.drawMap(g2d, mapX, mapY, currentScale);
        }else{

            g2d.setFont(ps.f1);
            String word = "FPS :" +String.valueOf(game.Guifps);
            g2d.drawString(word, Game.WIDTH-inv.getWidthString(g2d, word)-16, 32);
            ps.drawPlayerIcon(g2d);
            ps.drawPlayerStats(g2d);
            // mh.drawMiniMap(g2d, (int)game.camera.getX(), (int)game.camera.getY());
            if(sm.messages.size() > 0){
                sm.showMessage(g2d, 128);
            }
            skillUi.drawSkillSlot(g2d);
            inv.drawInventory(g2d);  
            blueprintGUI.drawWindow(g2d);
            blueprintGUI.drawBlueprints(g2d);

            if(lastXMouseMove != currentXMouseMove || lastYMouseMove != currentYMouseMove){
                mouseIsMoving = true;
                lastXMouseMove = currentXMouseMove;
                lastYMouseMove = currentYMouseMove;
            }else{
                mouseIsMoving = false;
            }
        }

    }

    public void drawToTerrain(Graphics2D g2d){
        tileMec.drawBluprintBuilding(g2d);
    }

    public void resetMap() {
        // TODO Auto-generated method stub
        mapX = 0;
        mapY = 0;
        currentScale = 0.1406408618241001;
    }
    public int lastXMouseMove = 0, lastYMouseMove = 0;
    public int currentXMouseMove = 0, currentYMouseMove = 0;
    public void getCurrentMousePost(int x, int y){
        currentXMouseMove = x;
        currentYMouseMove = y;
    }
    
    

    
}
