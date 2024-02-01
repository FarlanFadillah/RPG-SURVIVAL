package com.main;

import java.awt.Canvas;

public class Game extends Canvas implements Runnable{
    
	private static final long serialVersionUID = 1L;
	
	public static final int HEIGHT = 240;
	public static final int WIDTH = HEIGHT * 16/9;
	
	public Game() {
		new Frame(HEIGHT, WIDTH, "RPG-SURVIVAL", this);
		
	}
	
	public void run() {
		
	}
	
	public static void main(String args[]) {
		new Game();
	}
	
}
