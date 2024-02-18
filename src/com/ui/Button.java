package com.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.filehandler.SpriteSheet;

public class Button {
	BufferedImage pressedIcon, unpressedIcon;
	BufferedImage image;
	public boolean pressed = false;
	public int x, y;
	public String path, path2;
	Font f1 = new Font("DialogInput", Font.BOLD, 15);
	public String direction;
	public Button(String path, String path2) {
		unpressedIcon = new SpriteSheet(path).image;
		pressedIcon= new SpriteSheet(path2).image;
		image = unpressedIcon;
	}
	public Button(String string, int i, int j, int k, int l, int size) {
		unpressedIcon = new SpriteSheet(string).grabImage(i, j, size, size);
		pressedIcon = new SpriteSheet(string).grabImage(k, l, size, size);
    }
    public void drawButton(Graphics2D g2d, int x, int y) {
		this.x = x;
		this.y = y;
		if(pressed) {
			image = pressedIcon;
		}else {
			image = unpressedIcon;
		}
		g2d.drawImage(image, x, y, null);
		g2d.setColor(Color.black);
		g2d.setFont(f1);
		g2d.drawString(direction, x+image.getWidth(), y+image.getHeight()/2);
	}
	public Rectangle getBound() {
		return new Rectangle(x+7, y, 50, 52);
	}
}
