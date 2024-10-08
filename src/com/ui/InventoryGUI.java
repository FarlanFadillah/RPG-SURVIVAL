package com.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;

import com.filehandler.SpriteSheet;
import com.gameMechanics.Slot;
import com.id.ID;
import com.id.ItemType;
import com.main.Game;
import com.obj.Entity;
import com.obj.Item;
import com.quadTree.Point;
import com.quadTree.QuadNode;

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
    public Slot<Item>[] slot;
    public ItemType itemType = ItemType.ingredient;

    //dragged item element
    public int draggedSlotNum;
    public boolean dragged = false;
    public Slot<Item> slotDragged;
    public BufferedImage dragItem;
	
	//Blured Background
	public BufferedImage bluredBackground;
    
    //Buttons
    public Button usedTab, consumeTab, ingredientTab, closeTab;
    public ArrayList<Button> buttons = new ArrayList<>();

    //Equipment Inventory
    public EquipmentGUI equipment;
	public boolean hover;

	//Chest Inventory
	public ChestInventory chestInventory;
	//Item info
	SpriteSheet infoCarved = new SpriteSheet("/assets/GUI/Banners/infoCarved.png");
	int mousex, mousey;
	Slot<Item> slotHover;
    public InventoryGUI(Game game){
        this.game = game;
        player = game.getPlayerObject();
        equipment = new EquipmentGUI(game);
		chestInventory = new ChestInventory(game);
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

		closeTab = new Button("/assets/GUI/Icons/IconSpriteSheet.png", 2, 1, 3, 1, 32);
		closeTab.pressed = false;
		closeTab.direction = "closeTab";

	}
	public void tick(boolean openInventory){
        equipment.tick(itemType == ItemType.Used && openInventory && !game.getPlayerObject().isOpeningChest);
		chestInventory.tick(game.getPlayerObject().isOpeningChest);
        if(openInventory){
            if(x < xstop){
                x += 50;
            }
            if(opacity < 0.5f){
                opacity += 0.1f;
            }
        }else{
			hover = false;
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

			// g2d.drawImage(bluredBackground, 0,0, null);


            g2d.drawImage(inventoryBox.image, x, y, null);
            equipment.drawEquipmentSlot(g2d);
			chestInventory.drawInventorytSlot(g2d);
			if(game.getPlayerObject().isOpeningChest){
				chestInventory.drawSlot(g2d);
			}
			if(itemType == ItemType.Used){
				equipment.drawEpuipment(g2d);
			}
            drawButtons(g2d, x, y);
            drawItemStored(g2d, x, y);
            drawDraggedItem(g2d);
			if(hover && !dragged){
				
					g2d.setColor(Color.CYAN);
					String word = slotHover.type.toUpperCase();
					if(mousey+infoCarved.image.getHeight()< Game.HEIGHT){
						g2d.drawImage(infoCarved.image, mousex, mousey, null);
						g2d.drawString(word, (mousex + infoCarved.image.getWidth()/2)-getWidthString(g2d, word)/2, mousey+16);
					}else{
						g2d.drawImage(infoCarved.image, mousex, mousey-infoCarved.image.getHeight(), null);
						g2d.drawString(word, (mousex + infoCarved.image.getWidth()/2)-getWidthString(g2d, word)/2, mousey+16-infoCarved.image.getHeight());
					}
					g2d.setColor(Color.white);
				
			}
        }

    }

    public void dragItem(MouseEvent e, ItemType itemType) {
		Slot<Item>[] slot;
		if(itemType == ItemType.Consume) slot = game.getPlayerObject().playerInventory.consumeSlot;
		else if(itemType == ItemType.Used) slot = game.getPlayerObject().playerInventory.usedSlot;
		else slot = game.getPlayerObject().playerInventory.ingredientSlot;
		for (int i = 0; i < slot.length; i++) {
			Slot<Item> temp = slot[i];
			//Check collision on each slot
			if(temp.getBound().contains(e.getPoint())) {
				//drag item from slots
				if(temp.items.size() > 0 && dragged == false && game.key.holdCtrl == false && game.key.holdShift == false){
					slotDragged = temp.Copy();
					temp.emptySlot();
					dragged = true;
					dragItem = slotDragged.icon;
					draggedSlotNum = slotDragged.items.size();
					return;
				//split and drag item from slot
				}else if(temp.items.size() > 0 && dragged == false && game.key.holdCtrl == true && game.key.holdShift == false &&  temp.items.size() > 1){
					slotDragged = temp.splitCopy();
					dragged = true;
					dragItem = slotDragged.icon;
					draggedSlotNum = slotDragged.items.size();
					return;
				//put dragged item to empty selected slot
				}else if(temp.items.size() <= 0 && dragged == true && slotDragged.items.get(0).getItemType() == itemType){
					temp.fill(slotDragged);
					slotDragged.emptySlot();
					dragged = false;
					return;
				//put dragged item to slot that already has the same type of item (fail if the target slot already full)
				}else if(temp.items.size() > 0 && dragged == true && temp.type.equals(slotDragged.type) && temp.full == false && slotDragged.items.size()+temp.items.size() > temp.MAX == false && slotDragged.items.get(0).getItemType() == itemType){
					temp.fill(slotDragged);
					slotDragged.emptySlot();
					dragged = false;
					return;
				/*
				 * Place the drawn item into a slot that has the same type of item, 
				 * but the number of drawn items is only placed until the slot is full.
				 * */
				}else if(temp.items.size() > 0 && dragged == true && temp.type.equals(slotDragged.type) && temp.full == false && slotDragged.items.size()+temp.items.size() > temp.MAX == true && slotDragged.items.get(0).getItemType() == itemType) {
					temp.fillUntilFull(slotDragged, temp.MAX - temp.items.size());
					System.out.println("ss");
					draggedSlotNum = slotDragged.items.size();
					return;
				}else if(temp.items.size() > 0 && dragged == false && game.key.holdShift == true && game.getPlayerObject().isOpeningChest){
					boolean succes = chestInventory.chestOpen.addItem(temp);
					if(succes) temp.emptySlot();
					return;
				}
			}
		}
	}

    public void drawItemStored(Graphics2D g2d, int x, int y){
    	if(itemType == ItemType.Consume) slot = game.getPlayerObject().playerInventory.consumeSlot;
    	else if(itemType == ItemType.Used) slot = game.getPlayerObject().playerInventory.usedSlot;
    	else slot = game.getPlayerObject().playerInventory.ingredientSlot;
		
        for (int i = 0; i < slot.length; i++) {
            if(slot[i].type != null && !slot[i].equipment){
				// g2d.fillRect(slot[i].getBound().x, slot[i].getBound().y, slot[i].getBound().width, slot[i].getBound().height);
				g2d.drawImage(slot[i].icon, x+(slot[i].col*64), y+ (slot[i].row*64), null);
                g2d.setColor(Color.white);
                g2d.setFont(f1);
				g2d.drawString(String.valueOf(slot[i].items.size()), x+(slot[i].col*64)+44, y+ (slot[i].row*64)+55);
            }
        }
        
    }
    
    public void drawDraggedItem(Graphics2D g2d) {
    	if(game.gameState == ID.INVENT_STATE && dragged){
			g2d.drawImage(dragItem, mx-dragItem.getWidth()/2, my-dragItem.getHeight()/2, null);
			g2d.setFont(f1);
			g2d.setColor(Color.white);
			g2d.drawString(String.valueOf(draggedSlotNum), (mx+dragItem.getWidth()/2)-19, (my+dragItem.getHeight()/2)-10);
		}
    }
    
    public Rectangle getBound() {
		return new Rectangle(x, y, inventoryBox.image.getWidth(), inventoryBox.image.getHeight());
	}
    
    public void drawButtons(Graphics2D g2d, int x, int y) {
    	usedTab.drawButton(g2d, x+inventoryBox.image.getWidth(), y+64);
    	consumeTab.drawButton(g2d, x+inventoryBox.image.getWidth(), (int)usedTab.getBound().getY()+64);
    	ingredientTab.drawButton(g2d, x+inventoryBox.image.getWidth(), (int)consumeTab.getBound().getY()+64);
		closeTab.drawButton(g2d, x+inventoryBox.image.getWidth(), y);
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
		}else if(closeTab.getBoundButton().contains(e.getPoint())){
			game.gameState = ID.PLAY_STATE;
		}
	}

    public void dropItem() {
		System.out.println(slotDragged.items.size());
		int off = 0;
		for(int i = 0; i < slotDragged.items.size(); i++) {
			System.out.println(i);
			Item temp = slotDragged.items.get(i);
			temp.reSpawn(game.getPlayerObject().getX()+32+off, game.getPlayerObject().getY()+64+off);
			game.tryWorld.qt.insert(new QuadNode(new Point(slotDragged.items.get(i).x, slotDragged.items.get(i).y), slotDragged.items.get(i)), game.tryWorld.entity, null);
			off+=2;
		}
		slotDragged.emptySlot();
		dragged = false;
	}

	public void hoverSlotInv(MouseEvent e){
		Slot<Item>[] slot;
		if(itemType == ItemType.Consume) slot = game.getPlayerObject().playerInventory.consumeSlot;
		else if(itemType == ItemType.Used) slot = game.getPlayerObject().playerInventory.usedSlot;
		else slot = game.getPlayerObject().playerInventory.ingredientSlot;
		for (int i = 0; i < slot.length; i++) {
			Slot<Item> temp = slot[i];
			if(temp.getBound().contains(e.getPoint()) && temp.type != null) {
				hover = true;
				mousex = e.getX();
				mousey = e.getY();
				slotHover = temp;
				return;
			}else{
				hover = false;
			}
		}
	}
	public int getWidthString(Graphics2D g2, String text) {
		int x = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return x;
	}
	public BufferedImage blurImage(BufferedImage image, int radius) {
        int size = radius * 2 + 1;
        float[] data = new float[size * size];

        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        int center = size / 2;
        float total = 0;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int dx = x - center;
                int dy = y - center;
                float distance = dx * dx + dy * dy;
                data[y * size + x] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
                total += data[y * size + x];
            }
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }

        Kernel kernel = new Kernel(size, size, data);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage blurredImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        op.filter(image, blurredImage);

        return blurredImage;
    }

	public void getBlurBackground(){
		bluredBackground = blurImage(game.tryWorld.tilem.screen, 3);
	}
	
}
