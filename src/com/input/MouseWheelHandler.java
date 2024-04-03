package com.input;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.main.Game;

public class MouseWheelHandler implements MouseWheelListener{
    Game game;
    public MouseWheelHandler(Game game){
        this.game = game;
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        if(game.gameState == game.mapState){
            double scale;
            if (e.getWheelRotation() < 0)
            {
                scale = 1.1;
            }
            else
            {
                scale = 0.9;
            }
            if( game.gui.currentScale * scale >= 0.3 || game.gui.currentScale *scale <= 0.1) return;
            

            double mouseRelativeToImageX = e.getX() - game.gui.mapX;
            double mouseRelativeToImageY = e.getY() - game.gui.mapY;


            double newMouseRelativeToImageX = mouseRelativeToImageX * scale;
            double newMouseRelativeToImageY = mouseRelativeToImageY * scale;

            double deltaX = newMouseRelativeToImageX - mouseRelativeToImageX;
            double deltaY = newMouseRelativeToImageY - mouseRelativeToImageY;

            if(game.gui.mapX - deltaX < 0 && game.gui.mapX+(int)(game.gui.mh.worldMap.getWidth()*game.gui.currentScale)-deltaX > Game.WIDTH){
				game.gui.mapX -= deltaX;
			}

			if(game.gui.mapY - deltaY < 0 && game.gui.mapY+(int)(game.gui.mh.worldMap.getHeight()*game.gui.currentScale)-deltaY > Game.HEIGHT){
				game.gui.mapY -= deltaY;
			}

            if(game.gui.mapX+(int)(game.gui.mh.worldMap.getWidth()*game.gui.currentScale*scale) > Game.WIDTH && 
            game.gui.mapY+(int)(game.gui.mh.worldMap.getHeight()*game.gui.currentScale*scale) > Game.HEIGHT){
                game.gui.currentScale *= scale;
            }
            

        }
    }
    
}
