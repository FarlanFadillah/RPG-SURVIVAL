package com.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.id.EntityType;
import com.main.Game;
import com.obj.Entity;

public class KeyInput implements KeyListener{
	Game game;
    public Entity player;
    public Entity player2;
    public KeyInput(Game game){
		this.game = game;
        getPlayerObject();
        getPlayer2();
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

        if(key == KeyEvent.VK_UP) player2.setUp(true);
		if(key == KeyEvent.VK_DOWN) player2.setDown(true);
		if(key == KeyEvent.VK_RIGHT) player2.setRight(true);
		if(key == KeyEvent.VK_LEFT) player2.setLeft(true);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
		if(key == KeyEvent.VK_W) player.setUp(false);
		if(key == KeyEvent.VK_S) player.setDown(false);
		if(key == KeyEvent.VK_D) player.setRight(false);
		if(key == KeyEvent.VK_A) player.setLeft(false);

        if(key == KeyEvent.VK_UP) player2.setUp(false);
		if(key == KeyEvent.VK_DOWN) player2.setDown(false);
		if(key == KeyEvent.VK_RIGHT) player2.setRight(false);
		if(key == KeyEvent.VK_LEFT) player2.setLeft(false);
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

    public void getPlayer2(){
        for (int i = 0; i < game.handler.objects.size(); i++) {
            Entity temp = (Entity) game.handler.objects.get(i);
            if(temp.getEntityType() == EntityType.PasifNPC){
                player2 = temp;
                break;
            }
        }
    }

}
