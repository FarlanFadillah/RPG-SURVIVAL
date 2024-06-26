package com.buildingMechanics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.blockList.Castle;
import com.blockList.Chest;
import com.blockList.House;
import com.blockList.Tower;
import com.id.BlockType;
import com.id.ID;
import com.main.Game;
import com.obj.BluePrint;
import com.obj.GameObject;
import com.obj.Item;
import com.quadTree.Point;
import com.quadTree.QuadNode;
import com.ui.Message;

public class TileInteractionGui {

    public int tileX, tileY, tileClickX, tileClickY;
    public int mx, my;
    Game game;
    public boolean showBluePrint = false, canBuild = false;
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
            int x = ((tileX - (bluprintBuilding.building.image.getWidth()/3))/64)*64;
            int y = ((tileY - (bluprintBuilding.building.image.getHeight()/3))/64)*64;
            if(!canBuild) {
            	System.out.println("cant build"); 
            	return;
            }
            if(game.getPlayerObject().playerInventory.checkIngredient(bluprintBuilding)){
                build(bluprintBuilding.building.x, bluprintBuilding.building.y);
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

    public int xbp = 0, ybp = 0;
    public void drawBluprintBuilding(Graphics2D g2d){
        
        if(showBluePrint && game.getPlayerObject().holdingTools == game.getPlayerObject().building){
            if(bluprintBuilding.building.image.getWidth()> 64){
                xbp = ((tileX - (bluprintBuilding.building.image.getWidth()/3))/64)*64;
                ybp = ((tileY - (bluprintBuilding.building.image.getHeight()/3))/64)*64;
            }else {
                xbp = tileX;
                ybp = tileY;
            }
            bluprintBuilding.building.x = xbp;
            bluprintBuilding.building.y = ybp;
            notCollision();
            g2d.drawImage(bluprintBuilding.building.image, xbp, ybp, null);
        }
    }


    public void build(int x, int y){
        if(game.getPlayerObject().holdingTools == game.getPlayerObject().building){
            if(bluprintBuilding.name == "BlueHouse"){
                game.tryWorld.qt.insert(new QuadNode(new Point(x, y), new House(x, y, ID.Block, BlockType.unDestroyAble, game)), game.tryWorld.objects, null);
                bluprintBuilding = null;
                showBluePrint = false;
                game.getPlayerObject().changeEquipment(game.getPlayerObject().hammer);
            }else if(bluprintBuilding.name == "Castle"){
                game.tryWorld.qt.insert(new QuadNode(new Point(x, y), new Castle(x, y, ID.Block, BlockType.unDestroyAble, game)), game.tryWorld.objects, null);
                bluprintBuilding = null;
                showBluePrint = false;
                game.getPlayerObject().changeEquipment(game.getPlayerObject().hammer);
            }else if(bluprintBuilding.name == "Tower"){
                game.tryWorld.qt.insert(new QuadNode(new Point(x, y), new Tower(x, y, ID.Block, BlockType.unDestroyAble, game)), game.tryWorld.objects, null);
                bluprintBuilding = null;
                showBluePrint = false;
                game.getPlayerObject().changeEquipment(game.getPlayerObject().hammer);
            }else if(bluprintBuilding.name == "Chest"){
                game.tryWorld.qt.insert(new QuadNode(new Point(x, y), new Chest(x, y, ID.Block, BlockType.unDestroyAble, game)), game.tryWorld.objects, null);
                bluprintBuilding = null;
                showBluePrint = false;
                game.getPlayerObject().changeEquipment(game.getPlayerObject().hammer);
            }
        }
    }


    private void notCollision() {
        for (int i = 0; i < game.tryWorld.objects.size(); i++) {
			GameObject temp = game.tryWorld.objects.get(i);
			if(bluprintBuilding.building.getBound().intersects(temp.getBound())){
				System.out.println("not");
				canBuild = false;
				return;
			}
			
		}
        canBuild = true;
    }
    
}
