package com.tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.filehandler.SpriteSheet;
import com.main.Game;

public class TileManager {
    SpriteSheet ss;
    
    //Size of a Map
	public int WIDTHMAP, HEIGHTMAP;
    public int pixels = 64;
    int tile_w = 64;
	int tile_h = 64;
	public Game game;
	ImageManager imageManager = new ImageManager();

	public BufferedImage screen;
	public Graphics2D g2dScreen;
	


    public TileManager(Game game){
		this.game = game;

		screen = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		g2dScreen = (Graphics2D) screen.createGraphics();
    }
    public void getTiles(String path, TileMap[] map, int size) {
        ss = new SpriteSheet(path);
		System.out.println("Getting Tiles!!");
		map[0] = new TileMap();
		map[0].image = ss.grabImage(5, 4, size, size);
		int c = 1;
		int x = 1;
		int y = 1;
		while(x<ss.image.getWidth()/size && y<ss.image.getHeight()/size +1 ) {
			
			while(x<=ss.image.getWidth()/size) {
				
				try {
					map[c] = new TileMap();
					
					map[c].image = imageManager.scaledImage(ss.grabImage(x, y, size, size), size, size);
					//System.out.println(x +" "+y+" "+c);
					c++;
					x++;
					if(c==ss.image.getWidth()/size*ss.image.getHeight()/size) {
						break;
					}
				}catch (Exception e) {
					System.out.println(e);
				}
				
			}
			if(x>ss.image.getWidth()/size) {
				x=1;
				y++;
			}
		}
	}

    public void draw(Graphics2D g2, double xx, double yy, TileMap[] map, int[][] maptile) {
		int col=0;
		int row=0;

		while(col<WIDTHMAP && row<HEIGHTMAP) {
			while(col<WIDTHMAP) {
				int tile = maptile[col][row];
				if(col*pixels< xx +Game.WIDTH && col*pixels> xx - pixels && row*pixels< yy + Game.HEIGHT&& row*pixels > yy - pixels) {
					if(tile == 42){
					}else{
						g2.drawImage(map[tile].image, col*pixels, row*pixels, null);
						//For Blured Image pending
						// g2dScreen.drawImage(map[tile].image, colScreen*pixels, rowScreen*pixels, null);
						game.gui.tileMec.drawHoverTile(col*pixels, row*pixels, g2);
						// colScreen++;

						// if(colScreen*pixels > Game.WIDTH){
						// 	colScreen = 0;
						// 	rowScreen++;
						// }
						
						if(game.showGrid())
						{
							String text = "" + col + ", " + row;
							g2.setColor(Color.white);
							g2.drawRect(col*pixels, row*pixels, pixels,pixels);
							g2.drawString(text, col*pixels, (row*pixels)+pixels);
							g2.setColor(Color.black);
						}
					}
				}	
			    col++;
			}
			if(col==WIDTHMAP) {
				col=0;
				row++;
			}
		}
		
	}

    
	public int[][] TMXFileReader(String path, String keyword){
		int[][] maptile = new int[0][0];
		System.out.println("Read Map");
		InputStream in = getClass().getResourceAsStream(path);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line;
            boolean keywordFound = false;

            while ((line = br.readLine()) != null) {
                if (line.contains(keyword)) {
                    WIDTHMAP =  extractValue(line, "width");
					HEIGHTMAP = extractValue(line, "height");
					maptile = new int[WIDTHMAP][HEIGHTMAP];
                    keywordFound = true;
                    break;
                }
            }
			br.readLine();
            if (keywordFound) {
                // Kata kunci ditemukan, lanjutkan membaca 100 baris ke bawah
                int col=0;
				int row=0;

				while(col<WIDTHMAP && row<HEIGHTMAP) {
					
					String a = br.readLine();
					while(col<WIDTHMAP) {
						String[] num = a.split(",");
						int number = Integer.parseInt(num[col]);
						maptile[col][row] = number;
						col++;
					}
					if(col==WIDTHMAP) {
						col=0;
						row++;
					}
				}
            } else {
                System.out.println("Kata kunci tidak ditemukan dalam file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return maptile;
	}
	public int extractValue(String input, String attribute) {
        // Temukan posisi awal atribut
        int start = input.indexOf(attribute + "=\"");
        if (start == -1) {
            // Atribut tidak ditemukan
            return -1; // atau throw exception, tergantung kebutuhan Anda
        }

        // Hitung posisi akhir atribut
        int end = input.indexOf("\"", start + attribute.length() + 2);
        if (end == -1) {
            // Tanda kutip penutup tidak ditemukan
            return -1; // atau throw exception, tergantung kebutuhan Anda
        }

        // Ekstrak nilai atribut sebagai string
        String value = input.substring(start + attribute.length() + 2, end);

        // Konversi nilai string menjadi integer
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // Penanganan jika konversi gagal
            e.printStackTrace();
            return -1; // atau throw exception, tergantung kebutuhan Anda
        }
    }
}
