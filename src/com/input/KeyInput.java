package com.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.main.Game;
import com.obj.Entity;
import com.ui.Message;

public class KeyInput implements KeyListener{
	Game game;
    public Entity player;
    
    //Key Feature
    public boolean holdCtrl = false;
    public boolean holdShift = false;
    
    public KeyInput(Game game){
        player = game.getPlayerObject();
		this.game = game;
	}
    
    @Override
    public void keyTyped(KeyEvent e) {
        int key = e.getKeyChar();
        if(key == KeyEvent.VK_TAB){
            // game.gui.inv.getBlurBackground();
            if(game.gameState == game.playState || game.gameState == game.skillTabState){
                game.gameState = game.InventoryState;
            }else if(game.gameState == game.InventoryState){
                game.gameState = game.playState;
                game.getPlayerObject().isOpeningChest = false;
            }
        }else if(key == 'q' || key == 'Q'){
            if(game.gameState == game.playState || game.gameState == game.InventoryState){
                game.gameState = game.skillTabState;
            }else if(game.gameState == game.skillTabState){
                game.gameState = game.playState;
                game.getPlayerObject().isOpeningChest = false;
            }
        }else if(key == 'e' || key == 'E'){
            if(game.gameState == game.playState){
                System.out.println("ss");
                game.tryWorld.entity.remove(game.tryWorld.player);
                if(game.tryWorld.player.equals(game.tryWorld.fighter)){
                    game.tryWorld.player = game.tryWorld.archer;
                }else {
                    game.tryWorld.player = game.tryWorld.fighter;
                }
                game.tryWorld.entity.add(game.tryWorld.player);
                game.gui.ps.checkPlayer(game);
                player = game.tryWorld.player;
            }
        }else if(key == 'm' || key == 'M'){
            if(game.gameState == game.playState && game.gui.mh.mapDone){
                game.gameState = game.mapState;
            }else if(game.gameState == game.playState && !game.gui.mh.mapDone){
                game.gui.sm.addMessage(new Message("Generating Map, please wait!", game.second, 2));
            }else{
                game.gameState = game.playState;
                game.gui.resetMap();
            }
        }
        if(game.gameState == game.playState){
            char keyChar = e.getKeyChar();
            if (Character.isDigit(keyChar)) {
                int x = Integer.parseInt(String.valueOf(keyChar));
                if(x>0)System.out.println(game.getPlayerObject().skills.skillSlots[x-1].skill);
                else System.out.println(game.getPlayerObject().skills.skillSlots[9].skill);
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(game.gameState == game.playState) {
        	if(key == KeyEvent.VK_W) player.setUp(true);
        	if(key == KeyEvent.VK_S) player.setDown(true);
        	if(key == KeyEvent.VK_D) player.setRight(true);
        	if(key == KeyEvent.VK_A) player.setLeft(true);

            Entity player = game.getPlayerObject();
            if(e.getKeyCode() == KeyEvent.VK_T){
                if(player.holdingTools != player.hammer){
                   player.changeEquipment(player.hammer);
                }else{
                    player.changeEquipment(player.hands);
                }
            }
        	
        }else if(game.gameState == game.InventoryState) {
        	if(key == KeyEvent.VK_CONTROL) {
        		holdCtrl = true;
        	}else if(e.getKeyCode() == KeyEvent.VK_SHIFT){
                holdShift = true;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(game.gameState == game.playState) {
        	if(key == KeyEvent.VK_W) player.setUp(false);
        	if(key == KeyEvent.VK_S) player.setDown(false);
        	if(key == KeyEvent.VK_D) player.setRight(false);
        	if(key == KeyEvent.VK_A) player.setLeft(false);
        	
        }else if(game.gameState == game.InventoryState) {
        	if(key == KeyEvent.VK_CONTROL) {
        		holdCtrl = false;
        	}else if(e.getKeyCode() == KeyEvent.VK_SHIFT){
                holdShift = false;
            }
        }
    }


}
