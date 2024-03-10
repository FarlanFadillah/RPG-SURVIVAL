package com.map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.anim.AnimationHandler;
import com.anim.Foam;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.obj.Entity;
import com.obj.GameObject;
import com.playerlist.*;
import com.quadTree.Point;
import com.quadTree.Quad;
import com.quadTree.QuadNode;
import com.tile.ObjectManager;
import com.tile.TileManager;
import com.tile.TileMap;
import com.ai.AINode;

public class Island extends Biome{

    public ArrayList<int[][]> terrainLayer = new ArrayList<>();
    public TileMap[] tileSet = new TileMap[300];
    
    public List<GameObject> gameObject = new ArrayList<>();
    public AINode[][] AINode;

    public TileManager tilem;
    public ObjectManager bm;

    Foam foams = new Foam();
    BufferedImage foam;

    AnimationHandler animHandler = new AnimationHandler();

    public Quad qt;
    public Entity player;
    public Archer archer;
    public Fighter fighter;
    public List<GameObject> objects = new ArrayList<>();
    public List<GameObject> entity = new ArrayList<>();
    public Island(Game game) {
        super(game);
        archer = new Archer(832, 1152, ID.Entity, EntityType.Player, EntityClass.Archer, game);
        fighter = new Fighter(832, 1152, ID.Entity, EntityType.Player, EntityClass.Fighter, game);
        player = fighter;
        bm = new ObjectManager(game);
        tilem = new TileManager(game);
        this.mapPath = "/assets/Terrain/Islands.tmx";
        this.spriteSheetDir = "/assets/Terrain/Tilemap_Flat.png";
        foam = foams.idle[0];
        init();
    }

    @Override
    public void tick(double xx, double yy) {
        // TODO Auto-generated method stub
        animHandler.spriteCounter8Frame();
        objects.addAll(entity);
        objects = qt.GetObject(new Rectangle((int)game.camera.getX()-320, (int)game.camera.getY()-256, Game.WIDTH+320, Game.HEIGHT+256), qt, objects);

        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).tick();
        }
        // qt.tick(new Rectangle((int)player.x-(Game.WIDTH/2)-320, (int)player.y-(Game.HEIGHT/2)-256, Game.WIDTH+320, Game.HEIGHT+256), qt);
    }

    @Override
    public void draw(Graphics g, Graphics2D g2d, double xx, double yy) {
        // TODO Auto-generated method stub
        drawTerrainLayer(g2d, xx, yy, tileSet, terrainLayer.get(0), tilem);
        foam = animHandler.animatedSprite8Frame(foam, foams.idle);
        drawAnimation(g2d, foam,terrainLayer.get(0), 58, 64, 32, xx, yy);
        drawTerrainLayer(g2d, xx, yy, tileSet, terrainLayer.get(1), tilem);
        drawObjectLayer(g2d, objects);
        if(game.gameState == game.playState){
            objects.clear();
        }

    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        getTileSet(tileSet, tilem);
        addTerrainLayer(mapPath, "Tile Layer 1", terrainLayer, tilem);
        AINode = new AINode[tilem.WIDTHMAP][tilem.HEIGHTMAP];
        System.out.println(tilem.WIDTHMAP + " " + tilem.HEIGHTMAP);
        qt = new Quad(new Point(0,0), new Point(tilem.WIDTHMAP*64, tilem.HEIGHTMAP*64), game);
        qt.insert(new QuadNode(new Point(player.x, player.y), player), entity, AINode);
        addTerrainLayer(mapPath, "Tile Layer 2", terrainLayer, tilem);
        addObjectLayer(mapPath, "Object Layer 1", bm, qt, entity, AINode);
        addSolidLayer("SolidLayer", bm, 0, qt, entity, AINode);
    }
    
}
