package com.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.filehandler.SpriteSheet;
import com.gameMechanics.Slot;
import com.id.ItemType;
import com.main.Game;
import com.obj.Entity;

public class InventoryGUI {
    SpriteSheet inventoryBox = new SpriteSheet("/assets/GUI/Banners/Carved_36Slides_WithFrame.png");
    SpriteSheet itemSheet = new SpriteSheet("/assets/GUI/Buttons/Button_Blue_Pressed.png");
    BufferedImage[] itemImages = new BufferedImage[100];
    int xstop = 16;
    int xstart = -434;
    int x = xstart;
    int y = 8;
    public int mx = 0;
	public int my = 0;
    Font f1 = new Font("DialogInput", Font.BOLD, 12);
    public float opacity = 0f;
    Game game;
    public Entity player;
    public Slot[] slot;
    public ItemType itemType = ItemType.ingredient;
    public EquipmentGUI equipment;
    
    //Buttons
    public Button usedTab, consumeTab, ingredientTab;
    public ArrayList<Button> buttons = new ArrayList<>();
    public InventoryGUI(Game game){
        this.game = game;
        player = game.getPlayerObject();
        equipment = new EquipmentGUI(game);
        getItemsImage();
        setTabButton();
        
        
       
    }
    private void setTabButton() {
    	usedTab = new Button("/assets/GUI/Buttons/Button_Blue.png", "/assets/GUI/Buttons/Button_Blue_Pressed.png");
    	usedTab.direction = "used";
        consumeTab = new Button("/assets/GUI/Buttons/Button_Red.png", "/assets/GUI/Buttons/Button_Red_Pressed.png");
        consumeTab.direction = "consume";
        ingredientTab = new Button("/assets/GUI/Buttons/Button_Blue.png", "/assets/GUI/Buttons/Button_Blue_Pressed.png");
        ingredientTab.pressed = true;
        ingredientTab.direction = "ingredient";
	}
	public void tick(boolean openInventory){
        equipment.tick(openInventory);
        if(openInventory){
            if(x < xstop){
                x += 50;
            }
            if(opacity < 0.5f){
                opacity += 0.1f;
            }
        }else{
            if(x > xstart){
                x -= 50;
            }
            if(opacity > 0.1f){
                opacity -= 0.05f;
            }
        }
    }
    public void drawInventory(Graphics2D g2d){
        if(x > xstart){
            g2d.setColor(Color.BLACK);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g2d.drawImage(inventoryBox.image, x, y, null);
            equipment.drawEquipmentSlot(g2d);
            drawButtons(g2d, x, y);
            drawItemStored(g2d, x, y);
            drawDraggedItem(g2d);
        }

    }

    public void drawItemStored(Graphics2D g2d, int x, int y){
    	if(itemType == ItemType.Consume) slot = game.getPlayerObject().playerInventory.consumeSlot;
    	else if(itemType == ItemType.Used) slot = game.getPlayerObject().playerInventory.usedSlot;
    	else slot = game.getPlayerObject().playerInventory.ingredientSlot;
        for (int i = 0; i < slot.length; i++) {
            if(slot[i].type != null){
                g2d.drawImage(slot[i].icon, x+(slot[i].col*64), y+ (slot[i].row*64), null);
                g2d.setColor(Color.white);
                g2d.setFont(f1);
                g2d.drawString(String.valueOf(slot[i].items.size()), x+(slot[i].col*64)+44, y+ (slot[i].row*64)+55);
            }
        }
        
    }
    public int[][] getItem(){
        return new int[1][1];
    }

    public void getItemsImage(){
        itemImages[0] = itemSheet.grabImage(1, 1, 64, 64);
    }
    
    public void drawDraggedItem(Graphics2D g2d) {
    	if(game.gameState == game.InventoryState){
			if(player.playerInventory.dragged){
				g2d.drawImage(player.playerInventory.dragItem, mx-player.playerInventory.dragItem.getWidth()/2, my-player.playerInventory.dragItem.getHeight()/2, null);
				g2d.setFont(f1);
				g2d.setColor(Color.white);
				g2d.drawString(String.valueOf(player.playerInventory.draggedSlotNum), (mx+player.playerInventory.dragItem.getWidth()/2)-19, (my+player.playerInventory.dragItem.getHeight()/2)-10);
			}
		}
    }
    
    public Rectangle getBound() {
		return new Rectangle(x, y, inventoryBox.image.getWidth(), inventoryBox.image.getHeight());
	}
    
    public void drawButtons(Graphics2D g2d, int x, int y) {
    	usedTab.drawButton(g2d, x+inventoryBox.image.getWidth(), y);
    	consumeTab.drawButton(g2d, x+inventoryBox.image.getWidth(), y+64);
    	ingredientTab.drawButton(g2d, x+inventoryBox.image.getWidth(), y+128);
    }
	public void checkButton(MouseEvent e) {
		if(usedTab.getBound().contains(e.getPoint())) {
			usedTab.pressed = true;
			consumeTab.pressed = false;
			ingredientTab.pressed = false;
			itemType = ItemType.Used;
		}else if(consumeTab.getBound().contains(e.getPoint())) {
			consumeTab.pressed = true;
			usedTab.pressed = false;
			ingredientTab.pressed = false;
			itemType = ItemType.Consume;
		}else if(ingredientTab.getBound().contains(e.getPoint())) {
			ingredientTab.pressed = true;
			usedTab.pressed = false;
			consumeTab.pressed = false;
			itemType = ItemType.ingredient;
		}
	}

}
