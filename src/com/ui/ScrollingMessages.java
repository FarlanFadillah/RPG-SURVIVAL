package com.ui;

import java.util.Queue;

import com.main.Game;

import java.util.Iterator;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class ScrollingMessages {
    Queue<Message> messages = new LinkedList<>();
    Font f1 = new Font("DialogInput", Font.BOLD, 18);
    Game game;
    int start, current;
    public ScrollingMessages(Game game){
        this.game = game;
    }
    public void tick(){
        current = game.second;
        // if(stop - start >= 3 && messages.size() > 0 ){
        //     messages.remove();
        //     start = stop;
        // }
    }
    public void showMessage(Graphics2D g2d, int y){
        int row = 0;
        Iterator<Message> iterator = messages.iterator();
        try {
            while (iterator.hasNext()) {
                Message message = iterator.next();
                g2d.setFont(f1);
                g2d.setColor(Color.white);
                g2d.drawString(message.text, 10, (row*getHeightString(g2d, message.text))+y);
                g2d.setColor(Color.BLACK);
                if(current - message.start >= message.delay){
                    messages.remove(message);
                }
                row++;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void addMessage(Message message){
        messages.add(message);
        start = game.second;
    }

    public void removeMesage(Message message){
        messages.remove(message);
    }

    public int getHeightString(Graphics2D g2, String text) {
		int x = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
		return x;
	}
}
