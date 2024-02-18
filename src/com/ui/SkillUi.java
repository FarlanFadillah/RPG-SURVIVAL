package com.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.filehandler.SpriteSheet;
import com.main.Game;
import com.tile.ImageManager;

public class SkillUi {
    public Game game;
    BufferedImage image;
    ImageManager im = new ImageManager();
    SpriteSheet skillSlot = new SpriteSheet("/assets/GUI/Banners/skillSlotTransparent.png");
    public SkillUi(Game game){
        this.game = game;
        image = im.scaledImage(skillSlot.image, 48*10, 48);
    }

    public void drawSkillSlot(Graphics2D g2d){
        g2d.drawImage(image, Game.WIDTH/2-image.getWidth()/2, Game.HEIGHT-85, null);
    }
    public int getCenterX(String text, Graphics2D g2d) {
		
		int x = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
		int y = Game.WIDTH/2 - x/2;
		return y;
	}
}
