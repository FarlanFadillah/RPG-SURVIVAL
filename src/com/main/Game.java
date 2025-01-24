package com.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import com.ai.Pathfinder;
import com.id.ID;
import com.input.Camera;
import com.input.KeyInput;
import com.input.MouseInput;
import com.input.MouseWheelHandler;
import com.map.Island;
import com.obj.Entity;
import com.thread.SecondThread;
import com.ui.GUI;

public class Game extends Canvas implements Runnable{
    
	private static final long serialVersionUID = 1L;
	
	public static final int HEIGHT = 480; // ukuran frame
    public static final int WIDTH = HEIGHT * 16/9;

    private boolean running;
    public KeyInput key;
    public MouseInput mouse;
    public MouseWheelHandler mouseWheel;
    //Coba Coba
    public Island tryWorld = new Island(this);
    public Pathfinder pFinder = new Pathfinder(this);

    //Game State Section
    public int gameState = 0;

	public Frame frame;

    
    //Camera
    public Camera camera = new Camera(0, 0);

    public GUI gui = new GUI(this);

    //Game time
    public int second = 0;

    //GUI FPS
    public double Guifps = 0;


    public SecondThread thread;
    public Thread twoThread;
    public Game(){
    	
        gameState = ID.PLAY_STATE;
		frame = new Frame(WIDTH, HEIGHT, "RPG SURVIVAL", this);
		key = new KeyInput(this);
        key.player = getPlayerObject();
        mouse = new MouseInput(this);
        mouseWheel = new MouseWheelHandler(this);
        addKeyListener(key);
		this.addMouseListener(mouse);
		addMouseMotionListener(mouse);
        addMouseWheelListener(mouseWheel);
        setFocusTraversalKeysEnabled(false);
		// Memuat gambar kursor kustom ke BufferedImage
        // BufferedImage customCursorImage = new SpriteSheet("/assets/GUI/Pointers/01.png").image;

        // Membuat objek Cursor kustom
        // Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(customCursorImage,new java.awt.Point(0, 0), "Custom Cursor");

        // Mengatur kursor kustom untuk JFrame
        // frame.frame.setCursor(customCursor);

        thread = new SecondThread(this);
        twoThread = new Thread(thread);
        twoThread.start();
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
        
        g2d.translate(-camera.getX(), -camera.getY());
        tryWorld.draw(g2d, camera.getX(), camera.getY());
        gui.drawToTerrain(g2d);
        g2d.translate(camera.getX(), camera.getY());
            
        //////////////////////////////////////
        gui.draw(g2d);
        g.dispose();
        bs.show();
    }

    private void tick() {
        if(gameState == ID.PLAY_STATE){
            camera.tick(this);
            tryWorld.tick(camera.getX(), camera.getY());
        }else{
            getPlayerObject().stopMove();
        }
        
        gui.tick();
    }
//    @Override
//    public void run() {
//        this.requestFocus();
//		double draw = 1000000000/60;
//		long time = System.nanoTime();
//		long curentTime=0;
//		
//		double fps=0;
//		long timer=0;
//        double nextdraw = System.nanoTime() + draw;
//		
//		while(running) {
//			curentTime = System.nanoTime();
//			
//			timer +=curentTime - time;
//			time = curentTime;
//			tick();
//            render();
//            fps++;
//            try {
//            double remain = nextdraw - System.nanoTime();
//                remain = remain / 1000000;
//
//                if(remain <0){
//                    remain = 0;
//                }
//                Thread.sleep((long) remain);
//
//                nextdraw += draw;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//			
//
//			if(timer >= 1000000000) {
//                second++;
//                this.Guifps = fps;
//				fps = 0;
//				timer=0;
//			}
//		}
//		stop();
//    }
    
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
                //System.out.println("tick : " + tick + ", fps : " + fps);
                this.Guifps = fps;
                tick = 0;
				fps = 0;
				timer=0;
			}
		}
		stop();
    }
    public Entity getPlayerObject(){
        return tryWorld.player;
    }
    private boolean showBounds = false;
	public boolean showBounds() {
		return showBounds;
	}
	
	public void setShowBounds(boolean bool)
	{
		showBounds = bool;
	}
	
	private boolean showGrid = false;
	public boolean showGrid() {
		return showGrid;
	}
	
	public void setShowGrid(boolean bool)
	{
		showGrid = bool;
	}
	
}
