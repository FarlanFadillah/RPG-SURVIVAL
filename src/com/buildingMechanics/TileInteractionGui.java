package com.buildingMechanics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import com.blockList.House;
import com.id.BlockType;
import com.id.ID;
import com.main.Game;
import com.obj.Block;
import com.obj.BluePrint;
import com.quadTree.Point;
import com.quadTree.QuadNode;

public class TileInteractionGui {

    public int tileX, tileY, tileClickX, tileClickY;
    public int mx, my;
    Game game;
    public boolean showBluePrint = false;
    public BluePrint bluprintBuilding;

    public TileInteractionGui(Game game){
        this.game = game;
    }
    public void hoverTiles(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
        tileX = (int)((mx + game.camera.getX())/64)*64;
        tileY = (int)((my + game.camera.getY())/64)*64;

        if(game.getPlayerObject().holdingTools == game.getPlayerObject().building){
            showBluePrint = true;
        }
    }

    public void hoverTilesIfPlayerMoving(){
        if(game.getPlayerObject().isMoving && !game.gui.mouseIsMoving){

            mx += game.getPlayerObject().lastXPost - game.getPlayerObject().currentXPost;
            my += game.getPlayerObject().lastYPost - game.getPlayerObject().currentYPost;

            tileX = (int)((mx + game.camera.getX())/64)*64;
            tileY = (int)((my + game.camera.getY())/64)*64;
        }

    }

    public void tileClick(MouseEvent e){
        tileClickX = (int)((e.getX() + game.camera.getX())/64);
        tileClickY = (int)((e.getY() + game.camera.getY())/64);
        if(game.getPlayerObject().holdingTools == game.getPlayerObject().hammer){
            System.out.println(tileClickX + " " + tileClickY);
        }else if(game.getPlayerObject().holdingTools == game.getPlayerObject().building){
            int x = ((tileX - (bluprintBuilding.buildingImage.getWidth()/3))/64)*64;
            int y = ((tileY - (bluprintBuilding.buildingImage.getHeight()/3))/64)*64;
            if(game.getPlayerObject().playerInventory.checkIngredient(bluprintBuilding)){
                build(x, y);
            }
        }
    }

    public void drawHoverTile(int i, int j, Graphics2D g2) {
        if(game.getPlayerObject().holdingTools == game.getPlayerObject().hammer 
        && game.gui.tileMec.tileX == i
        && game.gui.tileMec.tileY == j){
            g2.setColor(Color.white);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g2.fillRect(i, j, 64, 64);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g2.setColor(Color.black);
        }

        
    }

    public void drawBluprintBuilding(Graphics2D g2d){
        
        if(showBluePrint && game.getPlayerObject().holdingTools == game.getPlayerObject().building){
            int x = ((tileX - (bluprintBuilding.buildingImage.getWidth()/3))/64)*64;
            int y = ((tileY - (bluprintBuilding.buildingImage.getHeight()/3))/64)*64;
            g2d.drawImage(bluprintBuilding.buildingImage, x, y, null);
        }
    }


    public void build(int x, int y){
        if(game.getPlayerObject().holdingTools == game.getPlayerObject().building){
            if(bluprintBuilding.name == "BlueHouse"){
                game.tryWorld.qt.insert(new QuadNode(new Point(x, y), new House(x, y, ID.Block, BlockType.unDestroyAble, game)), game.tryWorld.objects, null);
                bluprintBuilding = null;
                showBluePrint = false;
                game.getPlayerObject().changeEquipment(game.getPlayerObject().hammer);
            }
        }
    }
    
}
