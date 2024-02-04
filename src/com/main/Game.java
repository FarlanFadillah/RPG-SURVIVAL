package com.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import com.input.Camera;
import com.input.KeyInput;
import com.input.MouseInput;
import com.map.TryWorld;

public class Game extends Canvas implements Runnable{
    
	private static final long serialVersionUID = 1L;
	
	public static final int HEIGHT = 480; // ukuran frame
    public static final int WIDTH = HEIGHT * 16/9;

    private boolean running;
    public KeyInput key = new KeyInput(this);


    //Game State Section
    public int gameState = 0;
    public int playState = 1;
    public int pauseState = 2;
    public int menuState = 3;

	public Frame frame;

    
    //Coba Coba
    public TryWorld tryWorld = new TryWorld(this);
    //Camera
    public Camera camera = new Camera(0, 0);

    public Game(){
		frame = new Frame(WIDTH, HEIGHT, "RPG SURVIVAL", this);
        key.getPlayerObject(tryWorld.objectLayer.get(0));
        addKeyListener(key);
		this.addMouseListener(new MouseInput(this));
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
		BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            this.requestFocus();
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        /////////////////////////////////////////
        g.fillRect(0, 0, getWidth(), getHeight());
        g2d.translate(-camera.getX(), -camera.getY());

        
        tryWorld.draw(g, g2d, camera.getX(), camera.getY());

        g2d.translate(camera.getX(), camera.getY());
        
        //////////////////////////////////////
        g.dispose();
        bs.show();
    }

    private void tick() {
        camera.tick(this);
        tryWorld.tick();
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
