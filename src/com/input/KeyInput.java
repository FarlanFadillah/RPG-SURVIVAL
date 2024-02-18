package com.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.id.ItemType;
import com.main.Game;
import com.obj.Entity;

public class KeyInput implements KeyListener{
	Game game;
    public Entity player;
    
    //Key Feature
    public boolean holdCtrl = false;
    
    public KeyInput(Game game){
		this.game = game;
	}
    @Override
    public void keyTyped(KeyEvent e) {
        int key = e.getKeyChar();
        if(key == 'c' || key == 'C'){
            if(game.gameState == game.playState){
                game.gameState = game.InventoryState;
                game.getPlayerObject().stopMove();
            }else if(game.gameState == game.InventoryState){
                game.gameState = game.playState;
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
        	
        }else if(game.gameState == game.InventoryState) {
        	if(key == KeyEvent.VK_CONTROL) {
        		holdCtrl = true;
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
        	}
        }
    }


}
