package com.buildingMechanics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import com.blockList.House;
import com.filehandler.SpriteSheet;
import com.gameMechanics.BluePrintMech;
import com.gameMechanics.Slot;
import com.id.BlockType;
import com.id.ID;
import com.main.Game;
import com.obj.BluePrint;

public class BlueprintGUI {
    SpriteSheet ss = new SpriteSheet("/assets/GUI/Banners/BlueprintWindow.png");
    BluePrintMech bpm;
    Game game;
    int ystart, ystop = 32;
    int y = 0, x = 0;
    public BlueprintGUI(Game game){
        this.game = game;
        bpm = game.getPlayerObject().bluePrintMech;
        ystart = -ss.image.getHeight();
        y = ystart;
        x = Game.WIDTH/2 - (ss.image.getWidth()/2);
    }

    public void tick(){
        if(game.gameState == game.BlueprintWindow){
            if(y < ystop){
                y += 32;
            }
        }else{
            if(y > ystart){
                y -= 32;
            }
        }
    }

    public void drawWindow(Graphics2D g2d){
        g2d.drawImage(ss.image, x ,y, null);
    }

    public void drawBlueprints(Graphics2D g2d){
        if(y == ystart) return;
        for (int i = 0; i < bpm.blueprints.length; i++) {
            Slot<BluePrint> temp = bpm.blueprints[i];
            if(temp.type != null){
                g2d.drawImage(temp.icon, x + temp.x, y + temp.y, null);
            }

            if(temp.select){
                g2d.setColor(Color.white);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRect(x + temp.x, y + temp.y, 74, 74);
                g2d.setColor(Color.black);
                g2d.setStroke(new BasicStroke(1));
            }
        }
    }
    public void checkBlueprintClick(MouseEvent e){
        for (int i = 0; i < bpm.blueprints.length; i++) {
            Slot<BluePrint> temp = bpm.blueprints[i];
            if(temp.getBound(x, y).contains(e.getPoint())){
                temp.select = true;
                game.gameState = game.playState;
                game.getPlayerObject().holdingTools = game.getPlayerObject().building;

                if(temp.bluePrintStored.name == "BlueHouse"){
                    temp.bluePrintStored.buildingImage = new House(i, i, null, null, game).image;
                }
                game.gui.tileMec.bluprintBuilding = temp.bluePrintStored;
            }else{
                temp.select = false;
            }
        }
    }
}
