package com.main;

import java.awt.Canvas;

public class Game extends Canvas implements Runnable{
    
	private static final long serialVersionUID = 1L;
	
	public static final int HEIGHT = 720; // ukuran frame
    public static final int WIDTH = HEIGHT * 16/9;

    private boolean running;

    //Game State Section
    public int gameState = 0;
    public int playState = 1;
    public int pauseState = 2;
    public int menuState = 3;

	public Frame frame;


    public Game(){
		frame = new Frame(WIDTH, HEIGHT, "RPG SURVIVAL", this);
        start();
    }
    public static void main(String[] args) {
        new Game();
    }

    public void start(){
        running = true;
        new Thread(this).start();
    }
    private void stop() {
        running = false;
    }

    private void render() {
		
    }

    private void tick() {
    }

    @Override
    public void run() {
        this.requestFocus();
		double draw = 1000000000;
		long time = System.nanoTime();
		double delta=0;
		long curentTime=0;
		
		double fps=0;
        double tick=0;
		long timer=0;
		
		while(running) {
			curentTime = System.nanoTime();
			delta += (curentTime - time) / (draw/60);
			
			timer +=curentTime - time;
			time = curentTime;
			
			if(delta >= 1) {
                tick();
                tick++;
                delta--;
			}
			
            render();
            fps++;
			if(timer >= 1000000000) {
                System.out.println("tick : " + tick + ", fps : " + fps);
                tick = 0;
				fps = 0;
				timer=0;
			}
		}
		stop();
    }
	
}
