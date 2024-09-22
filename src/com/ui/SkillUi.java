package com.ui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.filehandler.SpriteSheet;
import com.gameMechanics.Skills;
import com.gameMechanics.Slot;
import com.id.ID;
import com.main.Game;
import com.obj.Entity;
import com.obj.Skill;
import com.tile.ImageManager;

public class SkillUi {
    public Game game;
    BufferedImage image;
    ImageManager im = new ImageManager();
    SpriteSheet skillSlot = new SpriteSheet("/assets/GUI/Banners/skillSlotTransparent.png");
    SpriteSheet skillTab = new SpriteSheet("/assets/GUI/Banners/SkillTree2.png");
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

    //Item info
	SpriteSheet infoCarved = new SpriteSheet("/assets/GUI/Banners/infoCarved.png");

    //Button for Skill Tree
    public Button plusButton[] = new Button[8];

    public SkillUi(Game game){
        this.game = game;
        image = im.scaledImage(skillSlot.image, 48*10, 48);
        this.x = Game.WIDTH/2-image.getWidth()/2;
        this.y = Game.HEIGHT-85;
        skills = game.getPlayerObject().skills;
        player = game.getPlayerObject();
        setPlusButton();
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
            skillTreeHover = false;
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
        if(skillTreeHover && !skillSelect && skillHovered.skill != null){
            g2d.setColor(Color.CYAN);
            String word = skillHovered.type.toUpperCase();
            if(mouseY+infoCarved.image.getHeight()< Game.HEIGHT){
                g2d.drawImage(infoCarved.image, mouseX, mouseY, null);
                g2d.drawString(word, (mouseX + infoCarved.image.getWidth()/2)-getWidthString(g2d, word)/2, mouseY+16);
            }else{
                g2d.drawImage(infoCarved.image, mouseX, mouseY-infoCarved.image.getHeight(), null);
                g2d.drawString(word, (mouseX + infoCarved.image.getWidth()/2)-getWidthString(g2d, word)/2, mouseY+16-infoCarved.image.getHeight());
            }
            g2d.setColor(Color.white);
        }
        drawSkillDragged(g2d);
    }
    private void drawSkillDragged(Graphics2D g2d) {
        // TODO Auto-generated method stub
        if(skillSelect && selectSkill.icon != null && game.gameState == ID.SKILLTAB_STATE){
            g2d.drawImage(selectSkill.icon, mx-(selectSkill.icon.getWidth()/2), my-(selectSkill.icon.getHeight()/2), 64,64,null);
        }
    }

    private void drawSkillTreeIcon(Graphics2D g2d) {
        try {
            for (int i = 0; i < game.getPlayerObject().skillTree.skillSlots.length; i++) {
                Slot<Skill> temp = game.getPlayerObject().skillTree.skillSlots[i];
                g2d.drawImage(temp.icon, xtab+temp.x, ytab+temp.y, null);
                if(temp.skill != null){
                    if(game.getPlayerObject().level >= temp.skill.requiredLevel && game.getPlayerObject().skillPoint > 0 && temp.lock){
                        plusButton[i].drawButton(g2d, xtab+temp.x+64, ytab+temp.y+17);
                    }
                }
                if(temp.lock){
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
                    g2d.fillRect(xtab+temp.x, ytab+temp.y, temp.width, temp.height);
                }
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                if(temp.select && temp.type != null){
                    g2d.setColor(Color.white);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawRect(xtab+temp.x, ytab+temp.y, temp.width, temp.height);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setColor(Color.black);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void drawSkillIcon(Graphics2D g2d) {
        for (int i = 0; i < game.getPlayerObject().skills.skillSlots.length; i++) {
            Slot<Skill> temp = game.getPlayerObject().skills.skillSlots[i];
            if(temp.type != null){
                g2d.drawImage(temp.icon, x+temp.x, y+temp.y, null);
                // g2d.fillRect(x+temp.x, y+temp.y, 48,48);
            }
        }
    }

    public int getCenterX(String text, Graphics2D g2d) {
		
		int x = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
		int y = Game.WIDTH/2 - x/2;
		return y;
	}

    public void checkSlot(MouseEvent e) {
        // TODO Auto-generated method stub
        if(!new Rectangle(x, y, image.getWidth(), image.getHeight()).contains(e.getPoint())) {
            return;
        }
        for (int i = 0; i < game.getPlayerObject().skills.skillSlots.length; i++) {
            Slot<Skill> temp = game.getPlayerObject().skills.skillSlots[i];
            if(temp.getBound(x, y).contains(e.getPoint()) && skillSelect == true){
                temp.fillSkill(selectSkill);
                System.out.println(temp.skill + " " + temp.type + " " + temp.icon.getWidth());
                skillSelect = false;
                selectSkill.select = false;
                selectSkill = null;
                return;
            }else if(temp.getBound(x, y).contains(e.getPoint()) && skillSelect == false && game.gameState == ID.SKILLTAB_STATE && temp.skill != null){
                selectSkill = temp.CopySkillSlot();
                temp.emptySlot(1);
                skillSelect = true;
                return;
            }else if(temp.getBound(x, y).contains(e.getPoint()) && skillSelect == false && game.gameState == ID.PLAY_STATE){
                
            }
        }
    }
    public Slot<Skill> selectSkill;
    public boolean skillSelect = false;
    public void checkSkillTree(MouseEvent e){
        if(!new Rectangle(x, y, image.getWidth(), image.getHeight()).contains(e.getPoint()) && !new Rectangle(xtab, ytab, skillTab.image.getWidth(), skillTab.image.getHeight()).contains(e.getPoint())){
            skillSelect = false;
            selectSkill = null;
            return;
        }
        if(!new Rectangle(xtab, ytab, skillTab.image.getWidth(), skillTab.image.getHeight()).contains(e.getPoint())) {
            return;
        }
        for (int i = 0; i < game.getPlayerObject().skillTree.skillSlots.length; i++) {
            Slot<Skill> temp = game.getPlayerObject().skillTree.skillSlots[i];
            if(temp.getBound(xtab, ytab).contains(e.getPoint()) && !temp.lock && skillSelect == false && temp.skill != null){
                skillSelect = true;
                selectSkill = temp;
                temp.select = true;
                return;
            }else if(temp.getBound(xtab, ytab).contains(e.getPoint()) && !temp.lock && skillSelect == true){
                selectSkill.select = false;
                selectSkill = null;
                skillSelect = false;
                temp.select = false;
                return;
            }else{
                temp.select = false;
            }
        }
    }

    

    public void slotHover(MouseEvent e){
        for (int i = 0; i < game.getPlayerObject().skills.skillSlots.length; i++) {
            Slot<Skill> temp = game.getPlayerObject().skills.skillSlots[i];
            if(temp.getBound(x, y).contains(e.getPoint())){
                hover = true;
                hoverColor = temp.getBound(x,y);
                return;
            }else{
                hover = false;
            }
        }
    }
    int mx, my;
    public void mousePos(MouseEvent e) {
        this.mx = e.getX();
        this.my = e.getY();
    }

    public void setPlusButton(){
        for (int i = 0; i < plusButton.length; i++) {
            plusButton[i] = new Button("/assets/GUI/Buttons/greenPlusButton.png", 1,1,2,1,32);
        }
    }

    public void checkPlusButton(MouseEvent e, boolean hold){
        Entity player = game.getPlayerObject();
        for (int i = 0; i < plusButton.length; i++) {
            if(plusButton[i].getBoundButton().contains(e.getPoint())){
                if(hold){
                    plusButton[i].pressed = true;
                }else{
                    if(player.skillTree.skillSlots[i].lock && player.skillPoint > 0){
                        player.skillTree.skillSlots[i].lock = false;
                        player.skillPoint--;
                        return;
                    }
                    plusButton[i].pressed = false;
                    return;
                }
            }
        }
    }

    public void checkPlusButtonHover(MouseEvent e){
        for (int i = 0; i < plusButton.length; i++) {
            if(!plusButton[i].getBoundButton().contains(e.getPoint())){
                plusButton[i].pressed = false;
            }

        }
    }
    boolean skillTreeHover = false;
    int mouseX, mouseY;
    Slot<Skill> skillHovered;
    public void skillTreeHover(MouseEvent e) {
        for (int i = 0; i < game.getPlayerObject().skillTree.skillSlots.length; i++) {
            Slot<Skill> temp = game.getPlayerObject().skillTree.skillSlots[i];
            if(temp.getBound(xtab, ytab).contains(e.getPoint())){
                skillTreeHover = true;
                mouseX = e.getX();
                mouseY = e.getY();
                skillHovered = temp;
                return;
            }else{
                skillTreeHover = false;
            }
        }
    }

    public int getWidthString(Graphics2D g2, String text) {
		int x = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return x;
	}

}
