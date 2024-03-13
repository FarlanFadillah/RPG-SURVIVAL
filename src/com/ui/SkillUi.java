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
import com.main.Game;
import com.obj.Entity;
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
    public Entity player;

    public boolean skillDraged = false;
    public SkillUi(Game game){
        this.game = game;
        image = im.scaledImage(skillSlot.image, 48*10, 48);
        this.x = Game.WIDTH/2-image.getWidth()/2;
        this.y = Game.HEIGHT-85;
        skills = game.getPlayerObject().skills;
        player = game.getPlayerObject();
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
        drawSkillIcon(g2d);
        if(openSkillTab){
            g2d.drawImage(skillTab.image, xtab, ytab, null);
            drawSkillTreeIcon(g2d);
        }
        if(hover){
            g2d.setColor(Color.white);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g2d.fillRect(hoverColor.x, hoverColor.y, 48, 48);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g2d.setColor(Color.black);
        }
    }
    private void drawSkillTreeIcon(Graphics2D g2d) {
        try {
            for (int i = 0; i < game.getPlayerObject().skillTree.skillSlots.length; i++) {
                Slot<Skill> temp = game.getPlayerObject().skillTree.skillSlots[i];
                g2d.drawImage(temp.icon, xtab+temp.x, ytab+temp.y, null);

                if(temp.lock){
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
                    g2d.fillRect(xtab+temp.x, ytab+temp.y, temp.width, temp.height);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void drawSkillIcon(Graphics2D g2d) {
        for (int i = 0; i < player.skills.skillSlots.length; i++) {
            Slot<Skill> temp = player.skills.skillSlots[i];
            g2d.drawImage(temp.icon, x+temp.x, y+temp.y, null);
        }
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

    public void checkSkillTree(MouseEvent e){
        for (int i = 0; i < player.skillTree.skillSlots.length; i++) {
            Slot<Skill> temp = player.skillTree.skillSlots[i];
            if(temp.getBound(xtab, ytab).contains(e.getPoint()) && !temp.lock){
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
