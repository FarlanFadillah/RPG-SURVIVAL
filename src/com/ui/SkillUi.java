package com.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.filehandler.SpriteSheet;
import com.gameMechanics.Skills;
import com.gameMechanics.Slot;
import com.id.ItemType;
import com.main.Game;
import com.obj.Skill;
import com.tile.ImageManager;

public class SkillUi {
    public Game game;
    BufferedImage image;
    ImageManager im = new ImageManager();
    SpriteSheet skillSlot = new SpriteSheet("/assets/GUI/Banners/skillSlotTransparent.png");
    SpriteSheet skillTab = new SpriteSheet("/assets/GUI/Banners/SkillTree.png");
    Skills skills;
    int x;
    int y;

    int xstop = 16;
    int xstart = -434;
    int xtab = xstart;
    int ytab = 8;

    float opacity;

    Rectangle hoverColor;
    private boolean hover;
    public boolean openSkillTab;
    public SkillUi(Game game){
        this.game = game;
        image = im.scaledImage(skillSlot.image, 48*10, 48);
        this.x = Game.WIDTH/2-image.getWidth()/2;
        this.y = Game.HEIGHT-85;
        skills = game.getPlayerObject().skills;
    }

    public void tick(boolean openSkilltab){
        this.openSkillTab = openSkilltab;
        if(openSkilltab){
            if(xtab < xstop){
                xtab += 50;
            }
            if(opacity < 0.5f){
                opacity += 0.1f;
            }
        }else{
            if(xtab > xstart){
                xtab -= 50;
            }
            if(opacity > 0.1f){
                opacity -= 0.05f;
            }
        }
    }

    public void drawSkillSlot(Graphics2D g2d){
        g2d.drawImage(image, x, y, null);
        if(openSkillTab){
            g2d.drawImage(skillTab.image, xtab, ytab, null);
            drawSkillIcon(g2d);
        }
        if(hover){
            g2d.setColor(Color.white);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g2d.fillRect(hoverColor.x, hoverColor.y, 48, 48);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g2d.setColor(Color.black);
        }
    }
    private void drawSkillIcon(Graphics2D g2d) {
        // TODO Auto-generated method stub
    }

    public int getCenterX(String text, Graphics2D g2d) {
		
		int x = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
		int y = Game.WIDTH/2 - x/2;
		return y;
	}

    public void checkSlot(MouseEvent e) {
        // TODO Auto-generated method stub
        for (int i = 0; i < skills.skillSlots.length; i++) {
            Slot<Skill> temp = skills.skillSlots[i];
            if(temp.getBound(x, y).contains(e.getPoint())){
                System.out.println(temp.col + " " + temp.row);
                return;
            }
        }
    }

    public void slotHover(MouseEvent e){
        for (int i = 0; i < skills.skillSlots.length; i++) {
            Slot<Skill> temp = skills.skillSlots[i];
            if(temp.getBound(x, y).contains(e.getPoint())){
                hover = true;
                hoverColor = temp.getBound(x,y);
                return;
            }else{
                hover = false;
            }
        }
    }


}
