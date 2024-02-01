package com.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.id.EntityType;
import com.main.Game;
import com.obj.Entity;

public class KeyInput implements KeyListener{
	Game game;
    public Entity player;
    public KeyInput(Game game){
		this.game = game;
        getPlayerObject();
	}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
		if(key == KeyEvent.VK_W) player.setUp(true);
		if(key == KeyEvent.VK_S) player.setDown(true);
		if(key == KeyEvent.VK_D) player.setRight(true);
		if(key == KeyEvent.VK_A) player.setLeft(true);
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
		if(key == KeyEvent.VK_W) player.setUp(false);
		if(key == KeyEvent.VK_S) player.setDown(false);
		if(key == KeyEvent.VK_D) player.setRight(false);
		if(key == KeyEvent.VK_A) player.setLeft(false);
    }

    public void getPlayerObject(){
        for (int i = 0; i < game.handler.objects.size(); i++) {
            Entity temp = (Entity) game.handler.objects.get(i);
            if(temp.getEntityType() == EntityType.Player){
                player = temp;
                break;
            }
        }
    }

}
